package game.base;

/**
 * @author yzz
 * 2020/3/17 3:42 下午
 */
public interface Named {
    default String name(){
        return getClass().getName();
    }
}
