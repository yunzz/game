package game.base.redis;

import io.lettuce.core.RedisClient;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author yzz
 * 2020/3/18 20:48
 */
public class LettucePool {

    protected GenericObjectPool<LettuceClient> internalPool;

    public LettucePool(GenericObjectPoolConfig<LettuceClient> poolConfig, PooledObjectFactory<LettuceClient> factory) {
        this.internalPool = new GenericObjectPool<>(factory, poolConfig);

    }


    public LettuceClient get() throws Exception {
        LettuceClient lettuceClient = internalPool.borrowObject();
        lettuceClient.setLettucePool(this);
        return lettuceClient;
    }

    public void ret(LettuceClient client) {
        internalPool.returnObject(client);
    }

}
