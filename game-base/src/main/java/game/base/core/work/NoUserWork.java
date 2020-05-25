package game.base.core.work;

import com.lmax.disruptor.EventTranslatorThreeArg;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.dsl.Disruptor;
import game.base.core.Messages;
import game.base.core.error.ErrorEnum;
import game.base.core.work.Work;
import game.base.core.work.WorkEvent;
import game.base.core.work.WorkHandler;
import game.base.log.Logs;
import io.netty.channel.Channel;
import io.netty.util.concurrent.DefaultThreadFactory;
import proto.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 非用户消息
 */
public class NoUserWork extends Work {

    public NoUserWork(int bufferSize) {
        super(bufferSize, new NoUserWorkHandler(),Executors.newSingleThreadExecutor(new DefaultThreadFactory("非用户业务")));
    }


}
