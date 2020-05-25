package game.base.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author yzz
 * 2020/3/18 20:41
 */
public class LettucePoolConfig extends GenericObjectPoolConfig<LettuceClient> {

    public LettucePoolConfig() {

        setMaxIdle(10);
        setMaxTotal(50);
        setMinIdle(1);
        setTimeBetweenEvictionRunsMillis(5 * 1000);
    }

}
