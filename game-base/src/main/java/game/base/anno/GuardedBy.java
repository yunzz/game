package game.base.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * 表示线程安全
 *
 * @author Yunzhe.Jin
 * 2020/4/3 11:12
 */
@Target({TYPE, FIELD, METHOD})
@Retention(CLASS)
public @interface GuardedBy {
    String value();
}
