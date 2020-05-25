package game.base.core.work;

import com.lmax.disruptor.EventTranslatorThreeArg;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.dsl.Disruptor;
import game.base.core.Messages;
import game.base.core.error.ErrorEnum;
import game.base.log.Logs;
import io.netty.channel.Channel;
import proto.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class Work {
    private Disruptor<WorkEvent> disruptor;
    private static EventTranslatorThreeArg<WorkEvent, Message, Channel, Object> workEventTranslator
            = (event, sequence, arg0, arg1, arg2) -> event.setMessage(arg0).setChannel(arg1).setObject(arg2);
    private AtomicBoolean stopped = new AtomicBoolean(true);
    private ExecutorService executor;

    public Work(int bufferSize, WorkHandler handler, ExecutorService executor) {
        this.executor = executor;
        disruptor = new Disruptor<>(WorkEvent::new, bufferSize, executor);
        disruptor.handleEventsWith(handler);
    }

    public long remainingCapacity() {
        return disruptor.getRingBuffer().remainingCapacity();
    }

    public boolean send(Message o, Channel c, Object o1) {
        if (stopped.get()) {
            return false;
        }
        if (remainingCapacity() == 0) {
            c.write(Messages.ERROR_MSG.apply(o.getSeq(), ErrorEnum.ERR_BUSY.getId()));
            Logs.common.warn("系统繁忙:容量{}", disruptor.getRingBuffer().getBufferSize());
            return false;
        }

        return disruptor.getRingBuffer().tryPublishEvent(workEventTranslator, o, c, o1);
    }

    public void start() {
        disruptor.start();
        stopped.set(false);
    }

    public void stop() {
        stopped.set(true);

        try {
            int i = 10;
            while (i-- > 0) {
                try {
                    disruptor.shutdown(1, TimeUnit.MINUTES);
                    return;
                } catch (TimeoutException e) {
                    System.out.println("超时一分钟，继续等待退出");
                }
            }
            System.out.println("超时10分钟，直接推出");
            System.out.println(disruptor.getCursor());
        } finally {
            executor.shutdown();
        }
    }

}
