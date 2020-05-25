package game.base.net.client;

import proto.Message;

import java.util.concurrent.CompletableFuture;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 10:43
 */
public class WriteMessage {
    private Message message;
    private CompletableFuture<Message> future;

    public WriteMessage(Message message) {
        this.message = message;
        future = new CompletableFuture<>();
    }

    public Message getMessage() {
        return message;
    }

    public CompletableFuture<Message> getFuture() {
        return future;
    }
}
