package game.base.core.queue;

import com.lmax.disruptor.*;
import game.base.log.Logs;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author Yunzhe.Jin
 * 2020/4/2 15:30
 */
public class RingBuffQueue<T> {

    private final SequenceBarrier barrier;
    private RingBuffer<ObjectWrap<T>> ringBuffer;
    private EventTranslatorOneArg<ObjectWrap<T>, T> arg;
    private final Sequence sequence = new Sequence(Sequencer.INITIAL_CURSOR_VALUE);

    public RingBuffQueue(int size, EventFactory<ObjectWrap<T>> factory, WaitStrategy waitStrategy, EventTranslatorOneArg<ObjectWrap<T>, T> arg) {
        this.arg = arg;
        ringBuffer = RingBuffer.createMultiProducer(factory, size, waitStrategy);
        barrier = ringBuffer.newBarrier();
        ringBuffer.addGatingSequences(sequence);

    }


    public boolean offer(T t) {
        return ringBuffer.tryPublishEvent(arg, t);
    }

    public void add(T t) {
        ringBuffer.publishEvent(arg, t);
    }

    public T take() {
        long nextSequence = sequence.get() + 1L;
        try {
            final long availableSequence = barrier.waitFor(nextSequence);
            if (nextSequence <= availableSequence) {
                ObjectWrap<T> t = ringBuffer.get(nextSequence);
                sequence.set(nextSequence);
                return t.getO();
            }

        } catch (final TimeoutException | AlertException e) {
            Logs.common.warn(e.getMessage());
            return null;
        } catch (final Throwable ex) {
            Logs.common.error("", ex);
            sequence.set(nextSequence);
        }

        return null;
    }

    public boolean isEmpty() {
        return ringBuffer.remainingCapacity() == ringBuffer.getBufferSize();
    }

    public void forEach(Consumer<T> consumer) {
        long nextSequence = sequence.get() + 1L;
        final long availableSequence;
        try {
            availableSequence = barrier.waitFor(nextSequence);
            if (nextSequence <= availableSequence) {
                ObjectWrap<T> t = ringBuffer.get(nextSequence);
                consumer.accept(t.getO());
                nextSequence++;
            }
            sequence.set(++nextSequence);
        } catch (AlertException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
