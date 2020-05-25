package game.base.core;

import game.base.config.AppDataConfig;
import game.base.core.work.WorkManager;
import game.base.excel.ExcelManager;
import game.base.net.client.TcpClientManager;
import io.netty.channel.Channel;
import proto.Message;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import game.base.net.server.IServer;

/**
 * 全局对象
 *
 * @author Yunzhe.Jin
 * 2020/3/28 10:41
 */
public final class Game {
    private static AtomicLong pushSeq = new AtomicLong();

    public static IServer server;
    public static ExcelManager excelManager;
    public static WorkManager workManager;
    private static PushMessageRoute pushMessageRoute;

    private static ConnectionEvents connectionEvents = new ConnectionEvents();
    private static ExecutorManager executorManager;
    private static TcpClientManager tcpClientManager = new TcpClientManager();

    private static ConcurrentHashMap<Long, PlayerChannel> playerWork = new ConcurrentHashMap<>(1000);

    public static void init(AppDataConfig config) {
        excelManager = new ExcelManager();
        workManager = new WorkManager(config.getWorkCount(), config.getWorkBuffSize());
        executorManager = new ExecutorManager(config);
    }

    public static void send(Message msg, Channel channel) {

        workManager.send(msg, channel);
    }

    /**
     * 注册玩家的channel，方便全局调用
     *
     * @param playerId
     * @param channel
     * @return
     */
    public static PlayerChannel addPlayer(Long playerId, Channel channel) {
        return playerWork.computeIfAbsent(playerId, uid -> new PlayerChannel(channel));
    }

    public static PlayerChannel getPlayer(Long playerId) {
        return playerWork.get(playerId);
    }

    public static PlayerChannel removePlayer(Long playerId) {
        return playerWork.remove(playerId);
    }

    /**
     * 向一批玩家广播消息
     *
     * @param message
     */
    public static void pushMessage(Message message) {
        pushMessageRoute.push(message);
    }

    public static void pushAndFlushMessage(Message message) {
        pushMessageRoute.pushAndFlush(message);
    }

    public static PushMessageRoute getPushMessageRoute() {
        return pushMessageRoute;
    }

    public static void setPushMessageRoute(PushMessageRoute pushMessageRoute) {
        Game.pushMessageRoute = pushMessageRoute;
    }

    public static void addConnectionEvent(ConnectionEventHandler handler) {
        connectionEvents.addListener(handler);
    }

    public static void removeConnectionEvent(ConnectionEventHandler handler) {
        connectionEvents.removeListener(handler);
    }

    public static ConnectionEvents getConnectionEvents() {
        return connectionEvents;
    }

    /**
     * 推送消息的seq，网关可能会收到相同seq
     *
     * @return
     */
    public static long nextPushSeq() {
        return pushSeq.addAndGet(1);
    }

    public static <U> CompletableFuture<U> slowProcess(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier, executorManager.getSlowExecutor());
    }

    public static TcpClientManager getTcpClientManager() {
        return tcpClientManager;
    }

    public static void setTcpClientManager(TcpClientManager tcpClientManager) {
        Game.tcpClientManager = tcpClientManager;
    }

    public static ExcelManager getExcelManager() {
        return excelManager;
    }

    public static ExecutorManager getExecutorManager() {
        return executorManager;
    }

    public static void send(Message msg, Channel channel, Object p) {
        workManager.send(msg, channel, p);
    }

    public static WorkManager getWorkManager() {
        return workManager;
    }
}
