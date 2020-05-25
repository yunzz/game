package game.base.module.room;

import com.lmax.disruptor.BlockingWaitStrategy;
import game.base.LifeCycle;
import game.base.core.queue.ObjectWrap;
import game.base.core.queue.RingBuffQueue;
import game.base.module.room.exception.CreateRoomFailedException;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.Promise;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * 管理所有房间
 *
 * @author Yunzhe.Jin
 * 2020/4/1 17:14
 */
public class RoomManager implements LifeCycle {
    static AtomicInteger roomId = new AtomicInteger();
    private DefaultEventExecutorGroup eventExecutors;
    private int nThreads;
    private RoomAgent[] inners;
    private Supplier<BlockingQueue<RoomMsg>> supplier;

    public RoomManager(int nThreads, Supplier<BlockingQueue<RoomMsg>> s) {
        eventExecutors = new DefaultEventExecutorGroup(nThreads);
        this.nThreads = nThreads;
        inners = new RoomAgent[nThreads];

        for (int i = 0; i < nThreads; i++) {
            RoomAgent roomInner = createRoomAgent(s);
            inners[i] = roomInner;
        }
    }

    private RoomAgent createRoomAgent(Supplier<BlockingQueue<RoomMsg>> s) {
        return new RoomAgentInner(eventExecutors.next(), s.get());
    }

    private RoomAgent createRingBuffRoomAgent() {
        RingBuffQueue<RoomMsg> queue = new RingBuffQueue<>(1024, ObjectWrap::new, new BlockingWaitStrategy(), (event, sequence, a) -> {
            event.setO(a);
        });
        return new RingBuffRoomInner(eventExecutors.next(), queue);
    }

    public RoomRef create(Room room) {

        RoomAgent roomInner = getRoomInner(room.getRoomId());
        RoomRef roomRef = new RoomRef(roomInner, room.getRoomId());
        room.roomRef = roomRef;
        boolean success = roomInner.sendMessage(new MsgRoomCreateInner(room));
        if (!success) {
            throw new CreateRoomFailedException();
        }

        return roomRef;
    }

    private RoomAgent getRoomInner(int roomId) {
        return inners[roomId % nThreads];
    }

    @Override
    public void start() {

        for (RoomAgent inner : inners) {
            inner.start();
        }
    }

    @Override
    public void stop() {
        for (RoomAgent inner : inners) {
            inner.stop();
            Promise<Object> closePromise = inner.getClosePromise();
            closePromise.awaitUninterruptibly(60, TimeUnit.SECONDS);
        }

        eventExecutors.shutdownGracefully();
    }
}
