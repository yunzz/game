package util;

import game.base.utils.JsonUtil;
import game.base.utils.YamlUtils;
import org.junit.Test;

import java.util.Map;

/**
 * @author Yunzhe.Jin
 * 2020/4/6 14:09
 */
public class YamlUtilsTest {


    @Test
    public void test1() throws Exception {
        Map<String, Object> load = YamlUtils.load("test.yaml");

        System.out.println(JsonUtil.toJsonString(load));
    }
}
