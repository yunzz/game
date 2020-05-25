package game.base.excel;

import game.base.LifeCycle;
import game.base.anno.NeedThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author Yunzhe.Jin
 * 2020/3/28 14:11
 */
@NeedThreadSafe
public class ExcelManager implements LifeCycle {

    private ConcurrentHashMap<String, Supplier<ExcelConfig>> configSupplyMap = new ConcurrentHashMap<>();
    private volatile ConcurrentHashMap<String, ExcelConfig> configMap = new ConcurrentHashMap<>();

    public void register(String name, Supplier<ExcelConfig> config) {
        Supplier<ExcelConfig> exist = configSupplyMap.put(name, config);
        if (exist != null) {
            throw new IllegalStateException("名称重复:" + name);
        }
    }


    public void start() {
        ConcurrentHashMap<String, ExcelConfig> map = new ConcurrentHashMap<>();
        for (Map.Entry<String, Supplier<ExcelConfig>> e : configSupplyMap.entrySet()) {
            String key = e.getKey();
            ExcelConfig excelConfig = e.getValue().get();
            excelConfig.load();
            excelConfig.afterLoad();
            map.put(key, excelConfig);
        }

        for (ExcelConfig value : map.values()) {
            value.afterAllLoad();
        }

        this.configMap = map;
    }


    public <T extends ExcelConfig> T findConfig(String config) {

        return (T) configMap.get(config);
    }

    @Override
    public void stop() {

    }
}
