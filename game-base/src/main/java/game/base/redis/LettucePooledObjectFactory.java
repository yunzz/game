package game.base.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author yzz
 * 2020/3/18 20:31
 */
public class LettucePooledObjectFactory implements PooledObjectFactory<LettuceClient> {
    private static Logger log = LoggerFactory.getLogger(LettucePooledObjectFactory.class.getName());
    private LettucePoolConfig config;
    private RedisURI redisUri;
    private RedisConfig redisConfig;

    public LettucePooledObjectFactory(LettucePoolConfig poolConfig, RedisConfig config) {
        this.config = poolConfig;
        this.redisConfig = config;
        RedisURI.Builder builder = RedisURI.builder()
                .withHost(config.getHost())
                .withPort(config.getPort())
                .withDatabase(config.getDb())
                .withTimeout(Duration.ofNanos( TimeUnit.MILLISECONDS.toNanos(config.getTimeout())));
        if (StringUtils.isNoneEmpty(config.getPassport())) {
            builder.withPassword(config.getPassport());
        }
        redisUri = builder.build();
    }

    @Override
    public PooledObject<LettuceClient> makeObject() throws Exception {
        log.info("创建连接");
        RedisClient redisClient = RedisClient.create(redisUri);
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        LettuceClient lettuceClient = new LettuceClient(connect);
        return new DefaultPooledObject<>(lettuceClient);
    }

    @Override
    public void destroyObject(PooledObject<LettuceClient> p) throws Exception {
        log.info("销毁连接");
        p.getObject().destroy();
    }

    @Override
    public boolean validateObject(PooledObject<LettuceClient> p) {
        log.info("验证连接");
        LettuceClient object = p.getObject();
        return object.ping();
    }

    @Override
    public void activateObject(PooledObject<LettuceClient> p) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<LettuceClient> p) throws Exception {

    }
}
