package game.base.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * @author Yunzhe.Jin
 * 2020/4/4 13:59
 */
public class ReflectionUtils0 {

    public static Method getPublicMethod(Class<?> clazz, Set<Class<?>> exceptReturnType) {

        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getModifiers() == Modifier.PUBLIC && !exceptReturnType.contains(m.getReturnType())) {
                return m;
            }
        }

        return null;

    }
}
