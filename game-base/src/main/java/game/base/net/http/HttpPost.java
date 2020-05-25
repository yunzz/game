package game.base.net.http;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 表示线程安全
 *
 * @author Yunzhe.Jin
 * 2020/4/3 11:12
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface HttpPost {
    String value();
}
