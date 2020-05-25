import game.base.utils.ResourceUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * @author Yunzhe.Jin
 * 2020/4/6 14:55
 */
public class ResourceTest {

    @Test
    public void test1() throws IOException {
        Set<URL> resource = ResourceUtils.resource("app.yaml");
        System.out.println(resource);
    }
}
