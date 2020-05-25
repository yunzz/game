package pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author yzz
 * 2020/3/18 20:57
 */
public class ItemFactory implements PooledObjectFactory<Item> {
    @Override
    public PooledObject<Item> makeObject() throws Exception {
        System.out.println("create");
        return new DefaultPooledObject(new Item());
    }

    @Override
    public void destroyObject(PooledObject<Item> p) throws Exception {
        System.out.println("destroyObject");

    }

    @Override
    public boolean validateObject(PooledObject<Item> p) {
        System.out.println("validateObject");

        return true;
    }

    @Override
    public void activateObject(PooledObject<Item> p) throws Exception {
        System.out.println("activateObject");

    }

    @Override
    public void passivateObject(PooledObject<Item> p) throws Exception {
        System.out.println("passivateObject");

    }
}
