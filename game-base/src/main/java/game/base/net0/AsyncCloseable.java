package game.base.net0;

import java.util.concurrent.CompletableFuture;

/**
 * @author Yunzhe.Jin
 * 2020/3/27 14:57
 */
public interface AsyncCloseable {
    CompletableFuture<Void> closeAsync();
}
