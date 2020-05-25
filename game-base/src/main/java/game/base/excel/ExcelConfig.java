package game.base.excel;

import game.base.anno.NeedThreadSafe;

import java.io.File;

/**
 * 一个配置文件
 * 子类应当是线程安全的，最好使用不可变类
 *
 * @author Yunzhe.Jin
 * 2020/3/28 14:01
 */
@NeedThreadSafe
public abstract class ExcelConfig {

    protected ExcelFile excel;

    public ExcelConfig(File f) {
        excel = new ExcelFile(f);
    }

    public ExcelConfig(String path) {
        excel = new ExcelFile(path);
    }

    public abstract void load();


    public final void afterLoad() {
        try {
            doAfterLoad();
        } finally {
            excel.close();
        }
    }

    protected void doAfterLoad() {

    }

    public void afterAllLoad() {

    }
}
