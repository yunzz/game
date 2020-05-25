package game.base.net0;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * @author yzz
 * 2020/3/20 10:34
 */
public class NetConfig {
    private Duration timeout;
    private boolean keepAlive;
    private boolean tcpNoDelay;

    private String host;
    private int port;
    private Supplier<NetHandler> HandlerSupply;
    private ClientResources clientResources;

    public Supplier<NetHandler> getHandlerSupply() {
        return HandlerSupply;
    }

    public void setHandlerSupply(Supplier<NetHandler> handlerSupply) {
        HandlerSupply = handlerSupply;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Supplier<NetHandler> getHandler() {
        return HandlerSupply;
    }

    public Duration getConnectTimeout() {
        return timeout;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public ClientResources getClientResources() {
        return clientResources;
    }

    public void setClientResources(ClientResources clientResources) {
        this.clientResources = clientResources;
    }
}
