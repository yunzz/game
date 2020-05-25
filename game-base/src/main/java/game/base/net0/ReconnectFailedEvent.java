package game.base.net0;


import java.net.SocketAddress;

/**
 * 2020/3/20 14:50
 */
public class ReconnectFailedEvent extends ConnectionEventSupport {

    private final Throwable cause;
    private final int attempt;

    public ReconnectFailedEvent(SocketAddress local, SocketAddress remote, Throwable cause, int attempt) {
        super(local, remote);
        this.cause = cause;
        this.attempt = attempt;
    }


    public Throwable getCause() {
        return cause;
    }

    public int getAttempt() {
        return attempt;
    }
}
