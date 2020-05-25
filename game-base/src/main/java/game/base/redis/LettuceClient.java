package game.base.redis;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

/**
 * @author yzz
 * 2020/3/19 10:03
 */
public class LettuceClient {
    private StatefulRedisConnection<String, String> connection;

    private LettucePool lettucePool;


    public LettuceClient(StatefulRedisConnection<String, String> connect) {
        this.connection = connect;
    }

    /*************************** cmd *************************/

    public void set(String key, String val) {

        RedisCommands<String, String> sync = connection.sync();
        sync.set(key, val);
    }

    public String get(String key) {
        RedisCommands<String, String> sync = connection.sync();
        return sync.get(key);
    }

    public boolean ping() {
        RedisCommands<String, String> sync = connection.sync();
        return "pong".equalsIgnoreCase(sync.ping());
    }

    public void select(int db) {
        RedisCommands<String, String> sync = connection.sync();
        sync.select(db);
    }

    /*************************** cmd end *************************/
    public void close() {
        lettucePool.ret(this);
    }

    public void destroy() {
        connection.close();
    }

    public void setLettucePool(LettucePool lettucePool) {
        this.lettucePool = lettucePool;
    }
}
