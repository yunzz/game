package game.client.controller;

import game.base.log.Logs;
import game.base.uitools.event.*;
import game.base.uitools.event.events.ConsoleEvent;
import game.base.uitools.ui.AlertUtil;
import game.client.MainUi;
import game.client.fs.WebSocketClient;
import io.netty.channel.ChannelFuture;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import proto.FsGameOverPush;
import proto.FsGameStartPush;
import proto.FsRevertReq;
import proto.FsSurrenderReq;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Yunzhe.Jin
 * 2020/4/7 14:23
 */
public class MainUiController implements Initializable {
    public final static GameScene gameScene = new GameScene();
    private Stage primaryStage;
    private boolean connected = false;
    public final WebSocketClient client = WebSocketClient.Instance;

    @FXML
    private TextField address;
    @FXML
    private Label status;
    @FXML
    private TextArea console;

    @FXML
    private TextField p1;
    @FXML
    private TextField p2;
    @FXML
    private TextField p3;
    @FXML
    private TextField p4;

    private int seq = 1;
    @FXML
    private Button loginBtn;
    @FXML
    private Button surrender;
    @FXML
    public Button revert;
    @FXML
    public Button cleanBtn;
    @FXML
    public Button match;
    @FXML
    public Button join;

    @FXML
    public Canvas canvas;

    @FXML
    public Label endReason;
    @FXML
    public Label roomNo;
    @FXML
    public Label time;
    @FXML
    public Label round;
    @FXML
    public Label chess;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        address.setText(WebSocketClient.Instance.URL);
        drawGrid();
        EventManager.addEventHandler(EventEnum.CONSOLE, (ConsoleEvent event) -> {
            console.appendText(LocalDateTime.now() + ":" + event.getText() + "\n");
        });
        gameScene.setTimeUpdate(aLong -> {
            time.setText("" + aLong);
        });


        // 开始游戏
        loginBtn.setOnAction(event -> {
            MainUi.controller.client.startGame();
        });

        // 认输
        surrender.setOnAction(event -> {
            MainUi.controller.client.send(104, FsSurrenderReq.getDefaultInstance());
        });

        // revert
        revert.setOnAction(event -> {
            Alert test = AlertUtil.buildConfirmationAlert("进行悔棋");
            test.setX(primaryStage.getX() + 300);
            test.setY(primaryStage.getY() + 300);

            Optional<ButtonType> buttonType = test.showAndWait();
            if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                // do revert
                gameScene.setRevert(true);
                MainUi.controller.client.send(102, FsRevertReq.getDefaultInstance());
                gameScene.stopTime();
            }
        });

        cleanBtn.setOnAction(event -> {
            cleanBoard(canvas.getGraphicsContext2D());
        });

    }


    private void drawGrid() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.SADDLEBROWN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.YELLOW);

        // grid
        cleanBoard(gc);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (!gameScene.isStart()) {
                return;
            }

            if (!gameScene.isMyTurn()) {
                return;
            }
            Pos pos = findPos(event.getX(), event.getY());
            Logs.common.debug("{},{},pos:{}", event.getX(), event.getY(), pos);
            if (pos.getIx() < 0 || pos.getIx() >= 15 || pos.getIy() < 0 || pos.getIy() >= 15) {
                AlertUtil.showErrorAlert("位置错误：" + pos);
                return;
            }

            pos.setSide(gameScene.getSide());
            if (!gameScene.putAndSend(pos)) {
                AlertUtil.showErrorAlert("已经有棋子：" + pos);
                return;
            }

            if (gameScene.getSide() == 1) {
                gc.setFill(Color.BLACK);
            } else {
                gc.setFill(Color.WHITE);
            }
            gc.fillOval(pos.getX(), pos.getY(), 30, 30);

            changeToOpposite();


        });


        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {

        });
    }

    /**
     * 改变到对方回合
     */
    public void changeToOpposite() {
        gameScene.setMyTurn(false);
        gameScene.setTime(30);
        round.setText("对方回合");


    }

    /**
     * 改变到我的回合
     */
    public void changeToMyTurn() {
        gameScene.setMyTurn(true);
        gameScene.setTime(30);
        round.setText("我方回合");

    }

    private void cleanBoard(GraphicsContext gc) {
        gc.setFill(Color.SADDLEBROWN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.YELLOW);

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                double x1 = 30 + i * 30;
                double y1 = 30;
                double x2 = x1;
                double y2 = 30 + 14 * 30;
                gc.strokeLine(x1, y1, x2, y2);

                x1 = 30;
                y1 = 30 + i * 30;
                x2 = 30 + 14 * 30;
                y2 = y1;
                gc.strokeLine(x1, y1, x2, y2);
            }
        }
    }

    public void redrawBoard() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        cleanBoard(gc);

        Pos[][] grid = gameScene.getGrid();

        for (Pos[] p : grid) {
            for (Pos pos : p) {
                if (pos == null) {
                    continue;
                }
                System.out.println(pos);
                if (pos.getSide() == 1) {
                    gc.setFill(Color.BLACK);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillOval(pos.getX(), pos.getY(), 30, 30);
            }
        }
    }

    public void drawPos(int x, int y) {
        GraphicsContext g = canvas.getGraphicsContext2D();

        int side = 1;
        // 画对方的棋子
        if (gameScene.getSide() == 1) {
            g.setFill(Color.WHITE);
            side = 2;
        } else {
            side = 1;
            g.setFill(Color.BLACK);
        }

        Pos pos = new Pos((x + 1) * 30 - 15, (y + 1) * 30 - 15, x, y);
        pos.setSide(side);

        g.fillOval(pos.getX(), pos.getY(), 30, 30);

        gameScene.put(pos);

        changeToMyTurn();
    }

    private Pos findPos(double x, double y) {
        int x0, y0;
        int ix = (int) (Math.round(x / 30));
        x0 = ix * 30 - 15;
        int iy = (int) (Math.round(y / 30));
        y0 = iy * 30 - 15;

        return new Pos(x0, y0, ix - 1, iy - 1);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setOnCloseRequest(event -> {
//            client.stop();
        });
    }

    @FXML
    public void connect(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        if (connected) {//断开连接

            client.stop();
            EventManager.fireEvent(new ConsoleEvent("关闭客户端"));
            connected = false;
            address.setDisable(false);
            button.setText("连接");
            status.setText("断开连接");

        } else {// 连接

            String text = address.getText();
            System.out.println(text);
            try {

                client.connect(Long.parseLong(p1.getText()));
                address.setText(client.URL);

                ChannelFuture connect = client.getConnect();

                connect.addListener(future -> {
                    Platform.runLater(() -> {
                        if (!future.isSuccess()) {
                            Logs.common.error("", future.cause());
                            EventManager.fireEvent(new ConsoleEvent(future.cause().getMessage()));
                            status.setText(future.cause().getMessage());
                        } else {
                            Logs.common.info("连接成功");
                            address.setDisable(true);
                            button.setText("断开连接");
                            status.setText("连接成功");
                            EventManager.fireEvent(new ConsoleEvent("连接成功:" + client.getCh()));
                            connected = true;
                        }
                    });
                });
            } catch (Exception e) {
                e.printStackTrace();
                EventManager.fireEvent(new ConsoleEvent("发生错误：" + e.getMessage()));
            }

        }

    }

    public void startGame(FsGameStartPush push) {
        gameScene.setSide(push.getSideValue());
        gameScene.start();
        if (gameScene.isMyTurn()) {
            round.setText("我的回合");
        }
        clean();
        if (gameScene.getSide() == 1) {
            chess.setText("黑子");
        } else {
            chess.setText("白子");
        }


    }

    public void clean() {

        cleanBoard(canvas.getGraphicsContext2D());
    }

    public void end(FsGameOverPush msg) {

        gameScene.stop();

        if (msg.getWin()) {
            round.setText("胜利");
        } else {
            round.setText("失败");
        }
        String step = msg.getStep();

        EventManager.fireEvent(new ConsoleEvent(step));


    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
