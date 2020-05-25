package game.base.module.room;

import java.util.concurrent.CompletableFuture;

/**
 * 消息处理
 *
 * @author Yunzhe.Jin
 * 2020/4/24 16:40
 */
public interface OnMessage<M, T extends Room> {

    public void onMessage(T room, M message, CompletableFuture<Object> completableFuture);
}
