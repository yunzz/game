package game.base.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yzz
 * 2020/3/18 19:23
 */
public class RedisManager {
    private LettucePool lettucePool;
    private static Logger log = LoggerFactory.getLogger(RedisManager.class.getName());
    private LettucePoolConfig config;
    private RedisConfig redisConfig;

    public RedisManager(LettucePoolConfig config, RedisConfig redisConfig) {
        this.config = config;
        this.redisConfig = redisConfig;
    }

    public RedisManager() {
        this.config = new LettucePoolConfig();
        this.redisConfig = new RedisConfig();

    }

    public void start() {
        lettucePool = new LettucePool(config, new LettucePooledObjectFactory(config, redisConfig));
    }

    public LettuceClient getClient() {
        try {
            return lettucePool.get();
        } catch (Exception e) {
            log.error("getClient error", e);
        }
        return null;
    }
}
