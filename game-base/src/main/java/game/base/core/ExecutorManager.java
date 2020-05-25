package game.base.core;

import game.base.LifeCycle;
import game.base.config.AppDataConfig;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 配置游戏中使用的线程池
 *
 * @author Yunzhe.Jin
 * 2020/4/1 15:29
 */
public class ExecutorManager implements LifeCycle {

    /**
     * 处理慢任务的线程
     */
    private ExecutorService slowExecutor;
    /**
     * 处理一些需要异步的公共处理
     */
    private ExecutorService commonSingleExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("公共线程"));


    public ExecutorManager(AppDataConfig config) {
        slowExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 3, new DefaultThreadFactory("慢处理线程"));
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        slowExecutor.shutdown();
        commonSingleExecutor.shutdown();
    }


    public ExecutorService getSlowExecutor() {
        return slowExecutor;
    }

    public ExecutorService getCommonSingleExecutor() {
        return commonSingleExecutor;
    }
}
