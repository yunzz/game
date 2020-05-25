package game.base.net.http;

/**
 * @author yzz
 * 2020/3/23 17:08
 */
public interface JsonHandler<R,Q> {

    Q parse(R o);
}
