package game.client.controller;

import game.client.fs.WebSocketClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import proto.FsPutReq;
import proto.Message;
import proto.PutKeyFvstReq;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Yunzhe.Jin
 * 2020/4/21 14:05
 */
public class GameScene {


    private Pos beforePos = new Pos(-1, -1, -1, -1);

    Pos[][] grid = new Pos[15][15];
    private boolean start = false;

    private boolean myTurn = false;
    /**
     * 1:黑，2:白
     */
    private int side;

    private long time;

    /**
     * 是否发起悔棋
     */
    private boolean revert;

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        if (time > 0) {
            time = time - 1;
        }
        this.timeUpdate.accept(time);

    }));
    private Consumer<Long> timeUpdate;

    public GameScene() {
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void stopTime() {
        timeline.pause();
    }

    public void resumeTime() {
        timeline.play();
    }

    public void setTimeUpdate(Consumer<Long> timeUpdate) {

        this.timeUpdate = timeUpdate;
    }

    public void resetTime() {
        this.time = 30;
    }

    public boolean putAndSend(Pos pos) {
        if (grid[pos.getIx()][pos.getIy()] != null) {
            return false;
        }

        grid[pos.getIx()][pos.getIy()] = pos;

        if (!pos.eq(this.beforePos)) {
            this.beforePos = pos;
        }
        Message message = Message.newBuilder()
                .setPutKeyFvstReq(PutKeyFvstReq.newBuilder().setX(pos.getIx()).setY(pos.getIy()))
                .build();

        WebSocketClient.Instance.send(101, FsPutReq.newBuilder().setX(pos.getIx()).setY(pos.getIy()).build());

        return true;
    }

    public void put(Pos pos) {
        grid[pos.getIx()][pos.getIy()] = pos;
    }

    public void removeKey(List<proto.Pos> posList) {
        for (proto.Pos pos : posList) {
            grid[pos.getX()][pos.getY()] = null;
        }

    }

    public Pos[][] getGrid() {
        return grid;
    }

    public void start() {
        this.start = true;

        beforePos = new Pos(-1, -1, -1, -1);
        grid = new Pos[15][15];
        if (side == 1) {
            myTurn = true;
        }
        resetTime();
        timeline.play();
    }

    public void stop() {
        this.start = false;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isRevert() {
        return revert;
    }

    public void setRevert(boolean revert) {
        this.revert = revert;
    }

}
