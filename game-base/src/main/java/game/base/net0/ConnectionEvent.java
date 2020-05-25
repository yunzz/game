package game.base.net0;

import java.net.SocketAddress;

/**
 * @author yzz
 * 2020/3/20 11:36
 */
public interface ConnectionEvent extends Event {

    SocketAddress getLocal();

    SocketAddress getRemote();
}
