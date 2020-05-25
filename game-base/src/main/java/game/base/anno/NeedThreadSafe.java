package game.base.anno;

import java.lang.annotation.*;

/**
 * 表示需要线程安全
 * @author Yunzhe.Jin
 * 2020/3/28 14:31
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface NeedThreadSafe {
}
