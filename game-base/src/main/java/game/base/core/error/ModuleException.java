package game.base.core.error;

import java.util.Arrays;

/**
 * @author Yunzhe.Jin
 * 2020/3/31 16:50
 */
public class ModuleException extends RuntimeException {

    private Object[] args;
    private ModuleErrorNoResolve errorNo;

    public ModuleException(ModuleErrorNoResolve errorNo) {
        super(errorNo.display());
        this.errorNo = errorNo;
    }

    public ModuleException(ModuleErrorNoResolve errorNo, Object[] args) {
        this(errorNo);
        this.args = args;
    }

    public ModuleErrorNoResolve getErrorNo() {
        return errorNo;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "ModuleException{" +
                "args=" + Arrays.toString(args) +
                ", errorNo=" + errorNo +
                '}';
    }
}