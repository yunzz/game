package game.login.base;

import game.base.LifeCycle;
import game.base.redis.RedisManager;

/**
 * @author yzz
 * 2020/3/23 16:44
 */
public class Global implements LifeCycle {

    private RedisManager redisManager = new RedisManager();

    public RedisManager getRedisManager() {
        return redisManager;
    }

    @Override
    public void start() {
        redisManager.start();
    }

    @Override
    public void stop() {

    }


}
