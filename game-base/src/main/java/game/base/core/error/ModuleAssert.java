package game.base.core.error;

import game.base.utils.Nullable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Yunzhe.Jin
 * 2020/3/31 16:50
 */
public class ModuleAssert {

    public static ModuleException getError(ModuleErrorNoResolve error) {
        return new ModuleException(error);
    }

    // is true
    public static void isTrue(boolean condition, ModuleErrorNoResolve error, Object... args) {
        if (!condition) {
            throw new ModuleException(error, args);
        }
    }

    public static void isTrue(boolean condition, Object... args) {
        isTrue(condition, ErrorEnum.ERR_1, (Object) null);
    }

    public static void isTrue(boolean condition, int error) {
        isTrue(condition, error, null);
    }

    public static void isTrue(boolean condition, ModuleErrorNoResolve error) {
        isTrue(condition, error.errorNo());
    }

    public static void isTrue(boolean condition) {
        isTrue(condition, ErrorEnum.ERR_1);
    }

    // is false
    public static void isFalse(boolean condition, ModuleErrorNoResolve error, Object... args) {
        if (condition) {
            throw new ModuleException(error, args);
        }
    }

    public static void isFalse(boolean condition, ModuleErrorNoResolve error) {
        isFalse(condition, error, (Object) null);
    }

    public static void isFalse(boolean condition) {
        isFalse(condition, ErrorEnum.ERR_1);
    }

    // not null
    public static void notNull(@Nullable Object o, ModuleErrorNoResolve error, Object... args) {
        if (o == null) {
            throw new ModuleException(error, args);
        }
    }

    public static void notNull(@Nullable Object o, ModuleErrorNoResolve error) {
        notNull(o, error, (Object) null);
    }

    public static void notNull(@Nullable Object o) {
        notNull(o, ErrorEnum.ERR_1, (Object) null);
    }

    // is null
    public static void isNull(@Nullable Object o, ModuleErrorNoResolve error, Object... args) {
        if (Objects.nonNull(o)) {
            throw new ModuleException(error, args);
        }
    }

    public static void isNull(@Nullable Object o, ModuleErrorNoResolve error) {
        isNull(o, error, (Object) null);
    }


    // error
    public static void error(ModuleErrorNoResolve resolve, Object... args) {
        throw new ModuleException(resolve, args);
    }

    public static void error(ModuleErrorNoResolve resolve) {
        error(resolve, (Object) null);
    }


    // Not empty
    public static void notEmpty(Collection<?> collection, ModuleErrorNoResolve error, Object... args) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ModuleException(error, args);
        }
    }

    public static void notEmpty(Collection<?> o, ModuleErrorNoResolve error) {
        notEmpty(o, error, (Object) null);
    }

    //isEmpty
    public static void isEmpty(Collection<?> collection, ModuleErrorNoResolve error, Object... args) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new ModuleException(error, args);
        }
    }

    public static void isEmpty(Collection<?> o, ModuleErrorNoResolve error) {
        isEmpty(o, error, (Object) null);
    }

    // Not blank
    public static void notBlank(CharSequence str, ModuleErrorNoResolve error, Object... args) {
        if (StringUtils.isBlank(str)) {
            throw new ModuleException(error, args);
        }
    }

    public static void notBlank(CharSequence o, ModuleErrorNoResolve error) {
        notBlank(o, error, (Object) null);
    }
}