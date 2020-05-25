package game.base.config;

import game.base.log.Logs;
import game.base.net.http.HttpPost;
import game.base.utils.JsonUtil;
import game.base.utils.ResourceUtils;
import game.base.utils.YamlUtils;

import java.net.URL;
import java.util.*;

/**
 * @author yzz
 * 2020/3/18 19:09
 */
public class AppDataConfig {

    public final static int processCount = Runtime.getRuntime().availableProcessors();

    /**
     * 应用名
     */
    public final static String APP_NAME = "app.name";

    /**
     * 应用端口
     */
    public final static String APP_PORT = "app.port";

    /**
     * 使用的配置文件
     */
    public final static String APP_PROFILE = "app.profile";

    /**
     * 配置文件默认名
     */
    public final static String CONFIG_NAME = "app";

    /**
     * http 端口
     */
    public final static String HTTP_PORT = "app.http.port";


    public final static String DEFAULT_PROFILE = "default";

    private int workCount;
    private int workBuffSize;

    private Map<String, Object> configMap;

    public Map<String, Object> getConfigMap() {
        return configMap;
    }


    public void load() throws Exception {
        String profile = System.getProperty(AppDataConfig.APP_PROFILE, AppDataConfig.DEFAULT_PROFILE);

        Set<URL> resourceAll = new LinkedHashSet<>(16);
        if (!profile.equals(AppDataConfig.DEFAULT_PROFILE)) {
            Logs.common.info("生效profile：{}", profile);
            Set<URL> resource0 = ResourceUtils.resource(String.format("%s-%s.yaml", AppDataConfig.CONFIG_NAME, profile));
            resourceAll.addAll(resource0);
        }
        resourceAll.addAll(ResourceUtils.resource(AppDataConfig.CONFIG_NAME + ".yaml"));


        Map<String, Object> configMap = new LinkedHashMap<>();
        for (URL url : resourceAll) {
            Logs.common.info("加载配置文件：{}", url.toString());
            Map<String, Object> load = YamlUtils.load(url);
            for (Map.Entry<String, Object> en : load.entrySet()) {
                configMap.putIfAbsent(en.getKey(), en.getValue());
            }
        }
        Logs.common.info("加载的配置项：\n{}", JsonUtil.toJsonPrettyString(configMap));

        this.setConfigMap(configMap);
    }

    public int getHttpPort() {
        return (int) configMap.get(HTTP_PORT);
    }

    public String getConfig(String key) {
        Object v = configMap.getOrDefault(key, null);
        return Objects.toString(v);
    }

    public void setConfigMap(Map<String, Object> configMap) {
        this.configMap = configMap;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int workCount) {
        this.workCount = workCount;
    }

    public int getWorkBuffSize() {
        return workBuffSize;
    }

    public void setWorkBuffSize(int workBuffSize) {
        this.workBuffSize = workBuffSize;
    }
}
