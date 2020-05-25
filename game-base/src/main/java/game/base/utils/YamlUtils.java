package game.base.utils;

import org.yaml.snakeyaml.Yaml;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzz
 * 2020/3/18 19:11
 */
public class YamlUtils {

    public static <T> T parse(String path, Class<T> clazz) throws Exception {
        Yaml yaml = new Yaml();
        return yaml.loadAs(IoUtils.readAll(path), clazz);
    }

    public static Map<String, Object> load(URL path) throws Exception {
        Yaml yaml = new Yaml();

        String yaml1 = IoUtils.readAll(path);
        Map map = yaml.loadAs(yaml1, Map.class);

        return makeKey("", map);
    }

    public static Map<String, Object> load(String path) throws Exception {
        Yaml yaml = new Yaml();

        Map map = yaml.loadAs(IoUtils.readAll(path), Map.class);

        return makeKey("", map);
    }

    private static Map<String, Object> makeKey(String prefix, Map<String, Object> maps) {

        Map<String, Object> temp = new HashMap<>();

        for (Map.Entry<String, Object> entry : maps.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                Map<String, Object> stringObjectMap = makeKey(key + ".", (Map<String, Object>) value);
                for (Map.Entry<String, Object> objectEntry : stringObjectMap.entrySet()) {
                    temp.put(prefix + objectEntry.getKey(), objectEntry.getValue());
                }
            } else {
                temp.put(prefix + key, value);
            }
        }


        return temp;
    }


}
