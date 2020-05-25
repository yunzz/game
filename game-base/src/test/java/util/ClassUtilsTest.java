package util;

import game.base.utils.ClassUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

/**
 * @author yzz
 * 2020/3/23 16:28
 */
public class ClassUtilsTest {

    @Test
    public void test1() throws IOException {

        Set<Class<?>> classes = ClassUtils.findClasses("game.base.utils");
        System.out.println(classes.size());
    }
}
