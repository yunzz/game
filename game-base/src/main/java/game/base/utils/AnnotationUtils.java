package game.base.utils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Yunzhe.Jin
 * 2020/4/11 15:28
 */
public class AnnotationUtils {

    public static <T extends Annotation> T findAnnotation(Class<?> clazz, Class<T> anno) {
        return clazz.getDeclaredAnnotation(anno);
    }

    public static Annotation findFirstAnnotation(Class<?> clazz, List<Class<? extends Annotation>> anno) {
        for (Annotation declaredAnnotation : clazz.getDeclaredAnnotations()) {
            if (anno.contains(declaredAnnotation)) {
                return declaredAnnotation;
            }
        }
        return null;
    }
}
