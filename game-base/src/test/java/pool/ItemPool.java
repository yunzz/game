package pool;

import io.lettuce.core.RedisClient;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author yzz
 * 2020/3/18 20:57
 */
public class ItemPool {

    protected GenericObjectPool<Item> internalPool;

    public ItemPool(GenericObjectPoolConfig<Item> poolConfig, PooledObjectFactory<Item> factory) {
        this.internalPool = new GenericObjectPool<>(factory, poolConfig);
    }

    public Item get() throws Exception {
        return internalPool.borrowObject();
    }

    public void ret(Item client) {
        internalPool.returnObject(client);
    }
}
