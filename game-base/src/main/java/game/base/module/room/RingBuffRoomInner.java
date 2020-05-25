package game.base.module.room;

import game.base.anno.AllLife;
import game.base.core.queue.RingBuffQueue;
import game.base.log.Logs;
import game.base.module.room.exception.QueueFullException;
import game.base.module.room.exception.StoppedException;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Yunzhe.Jin
 * 2020/4/1 17:25
 */
@AllLife
class RingBuffRoomInner implements RoomAgent {

    private final EventExecutor executor;
    private RingBuffQueue<RoomMsg> messageQueue;
    private AtomicBoolean stopped = new AtomicBoolean(true);
    private final long pollTimeout = 1000;
    private Promise<Object> closePromise;

    public RingBuffRoomInner(EventExecutor executor, RingBuffQueue queue) {
        this.executor = executor;
        this.executor.execute(new RoomTask());
        closePromise = executor.newPromise();
        this.messageQueue = queue;
    }

    public boolean sendMessage(RoomMsg o) {
        if (stopped.get()) {
            return false;
        }
        return messageQueue.offer(o);
    }

    public <T> CompletableFuture<T> sendAsyncMsg(MsgAsyncInner<T> inner) {
        if (stopped.get()) {
            inner.getComplete().completeExceptionally(new StoppedException());
        } else if (!sendMessage(inner)) {
            inner.getComplete().completeExceptionally(new QueueFullException());
        }
        return inner.getComplete();
    }

    class RoomTask implements Runnable {
        private Map<Integer, Room> rooms = new HashMap<>();

        @Override
        public void run() {

            stopped.set(false);
            Throwable cause = null;

            while (true) {
                try {
                    if (stopped.get()) {
                        break;
                    }

                    RoomMsg msg = messageQueue.take();
                    if (msg == null) {
                        continue;
                    }
                    cause = processMsg(msg);
                    if (cause != null) {
                        // 移除房间
                        rooms.remove(msg.roomId());
                    }

                } catch (Throwable e) {
                    Logs.common.error("", e);
                }
            }

            // 结束处理剩余消息
            if (!messageQueue.isEmpty()) {
                messageQueue.forEach(roomMsg -> {
                    processMsg(roomMsg);
                });
            }

            for (Room room : rooms.values()) {
                stop(room);
            }

            if (cause == null) {
                closePromise.setSuccess(null);
            } else {
                closePromise.setFailure(cause);
            }
        }

        /**
         * @param msg
         * @return 返回非null 则停止房间
         */
        private Throwable processMsg(RoomMsg msg) {
            Throwable cause = null;
            if (msg instanceof MsgRoomCreateInner) {
                MsgRoomCreateInner m = (MsgRoomCreateInner) msg;
                rooms.put(m.getRoom().getRoomId(), m.getRoom());
                Logs.common.debug("创建房间：{}", m.roomId());
                cause = start(m.getRoom());

            } else if (msg instanceof MsgRoomStopInner) {
                Room room = rooms.remove(((MsgRoomStopInner) msg).roomId);
                if (room != null) {
                    cause = stop(room);
                }
            } else if (msg instanceof MsgAsyncInner) {
                Room room = rooms.get(((MsgAsyncInner<?>) msg).roomId);
                if (room != null) {
                    cause = msg(room, msg.msg(), ((MsgAsyncInner) msg).getComplete());
                }
            } else {
                Room room = rooms.get(((MsgRoomInner) msg).roomId);
                if (room != null) {
                    cause = msg(room, msg.msg(), null);
                }
            }
            return cause;
        }

        private Throwable start(Room room) {
            try {
                room.setStopped(false);
                room.onStart();
            } catch (Throwable e) {
                if (room.onError(null, e, false)) {
                    return e;
                }
            }
            return null;
        }

        private Throwable stop(Room room) {

            try {
                room.setStopped(true);
                room.onStop();
            } catch (Throwable e) {
                if (room.onError(null, e, true)) {
                    return e;
                }
            }
            return null;
        }

        private Throwable msg(Room room, Object msg, CompletableFuture<Object> c) {

            try {

                if (c == null) {
                    room.onMessage(msg);
                } else {
                    room.onMessage(msg, c);
                }
            } catch (Throwable e) {
                if (room.onError(msg, e, false)) {
                    return e;
                }
            }
            return null;
        }
    }


    public Promise<Object> getClosePromise() {
        return closePromise;
    }

    @Override
    public void start() {
        stopped.set(false);
    }

    @Override
    public void stop() {
        stopped.set(true);
    }
}
