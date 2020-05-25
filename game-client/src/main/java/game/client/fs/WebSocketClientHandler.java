package game.client.fs;

/**
 * @author yunzz
 */

import com.google.protobuf.MessageLite;
import game.base.core.Player;
import game.base.uitools.event.EventManager;
import game.base.uitools.event.events.ConsoleEvent;
import game.base.uitools.ui.AlertUtil;
import game.base.utils.JsonUtil;
import game.client.ClientMessageHandler;
import game.client.MainUi;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import proto.*;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static game.client.controller.MainUiController.gameScene;

@ChannelHandler.Sharable
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    private final WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;

    public WebSocketClientHandler(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
        System.out.println("active");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("WebSocket Client disconnected!");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            try {
                handshaker.finishHandshake(ch, (FullHttpResponse) msg);
                System.out.println("WebSocket Client connected!");
                handshakeFuture.setSuccess();
            } catch (WebSocketHandshakeException e) {
                System.out.println("WebSocket Client failed to connect");
                handshakeFuture.setFailure(e);
            }
            return;
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException();
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            System.out.println("WebSocket Client received message: " + textFrame.text());
        } else if (frame instanceof PongWebSocketFrame) {
            System.out.println("WebSocket Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
            System.out.println("WebSocket Client received closing");
            ch.close();
        } else if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame f = (BinaryWebSocketFrame) frame;
            ByteBuf content = f.content();

            GatewayMessage gatewayMessage = new GatewayMessage(content);

            int msgNo = gatewayMessage.getMsgNo();
            msgNo %= 1000;
            if (gatewayMessage.getCode() != 0) {
                AlertUtil.showErrorAlert("发生错误：" + gatewayMessage.getCode());
                return;
            }
            if (msgNo == -3) {
                // 心跳

            } else {
                System.out.println("<------------------ receive: " + gatewayMessage);
                MessageLite messageLite = null;

                Class<?> aClass = pushMasNo.get(msgNo);
                if (aClass != null) {
                    messageLite = (MessageLite) aClass.getMethod("parseFrom", byte[].class).invoke(null, gatewayMessage.getMsgBytes());
                }

                if (messageLite != null) {
                    EventManager.fireEvent(new ConsoleEvent(messageLite.getClass().getName() + "\n" + messageLite.toString()));
                    ClientMessageHandler<?> clientMessageHandler = handlerConcurrentHashMap.get(msgNo);
                    if (clientMessageHandler != null) {
                        Player player = new Player();
                        player.setId(WebSocketClient.Instance.uid);

                        MessageLite finalMessageLite = messageLite;
                        Platform.runLater(() -> {
                            clientMessageHandler.handle(player, finalMessageLite);
                        });
                    }
                }

                System.out.println(gatewayMessage);
                System.out.println(messageLite);
            }
        }
    }

    private static ConcurrentHashMap<Integer, Class<?>> pushMasNo = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, ClientMessageHandler<?>> handlerConcurrentHashMap = new ConcurrentHashMap<>();

    static {
        // 推送
        pushMasNo.put(-201, FsPutPush.class);
        pushMasNo.put(-202, FsGameStartPush.class);
        pushMasNo.put(-203, FsRevertPush.class);
        pushMasNo.put(-204, FsRevertResultPush.class);
        pushMasNo.put(-205, FsGameOverPush.class);

        // 反馈
        pushMasNo.put(-101, FsPutRes.class);


        // handler
        handlerConcurrentHashMap.put(-201, (player, messageLite) -> {
            FsPutPush msg = (FsPutPush) messageLite;
            MainUi.controller.drawPos(msg.getX(), msg.getY());

        });
        // 开始
        handlerConcurrentHashMap.put(-202, (player, msg) -> {
            MainUi.controller.startGame((FsGameStartPush) msg);
        });

        // 结束
        handlerConcurrentHashMap.put(-205, (player, msg) -> {
            MainUi.controller.end((FsGameOverPush) msg);
        });

        // revert
        handlerConcurrentHashMap.put(-203, (p, m) -> {
            if (gameScene.isMyTurn()) {
                gameScene.stopTime();
            }
            Alert test = AlertUtil.buildConfirmationAlert("是否同意悔棋");
            test.setX(MainUi.controller.getPrimaryStage().getX() + 300);
            test.setY(MainUi.controller.getPrimaryStage().getY() + 300);
            Optional<ButtonType> buttonType = test.showAndWait();
            if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                // agree revert
                MainUi.controller.client.send(103, FsRevertAgreeReq.newBuilder().setAgree(true).build());
            } else {
                MainUi.controller.client.send(103, FsRevertAgreeReq.newBuilder().setAgree(false).build());
            }

        });

        // revert push
        handlerConcurrentHashMap.put(-204, (p, m) -> {
            FsRevertResultPush push = (FsRevertResultPush) m;
            boolean agree = push.getAgree();

            if (agree) {
                EventManager.fireEvent(new ConsoleEvent("同意悔棋"));
                System.out.println(JsonUtil.toJsonString(gameScene.getGrid()));
                gameScene.removeKey(push.getPosList());
                MainUi.controller.redrawBoard();

                if (gameScene.isRevert()) {
                    System.out.println("设置为我方回合");
                    MainUi.controller.changeToMyTurn();
                    gameScene.resumeTime();
                } else {
                    MainUi.controller.changeToOpposite();
                }


            } else {
                if (gameScene.isMyTurn()) {
                    gameScene.resumeTime();
                }
                EventManager.fireEvent(new ConsoleEvent("拒绝悔棋"));
            }

            gameScene.setRevert(false);

        });

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }
}
