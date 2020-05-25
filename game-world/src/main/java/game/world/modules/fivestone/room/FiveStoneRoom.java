package game.world.modules.fivestone.room;

import com.google.common.collect.Lists;
import game.base.core.Game;
import game.base.core.error.ErrorEnum;
import game.base.core.error.ModuleAssert;
import game.base.log.Logs;
import game.base.module.room.Room;
import game.base.module.room.RoomRef;
import game.base.module.room.UserMessage;
import game.world.core.msg.Tick;
import proto.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 五子棋
 *
 * @author Yunzhe.Jin
 * 2020/4/20 13:58
 */
public class FiveStoneRoom extends Room {
    private Scene scene = new Scene();
    private boolean end = false;
    private static final long TIMEOUT = TimeUnit.MILLISECONDS.convert(30, TimeUnit.SECONDS);

    @Override
    public void onStart() {
        Logs.common.debug("开始房间：{}", roomId);
        RoomRef roomRef = getRoomRef();
        roomRef.newTimeout(timeout -> {
            roomRef.sendMsg(new Tick());
        }, 2, TimeUnit.SECONDS);
    }

    @Override
    public void onMessage(Object msg) {
        if (end) {
            return;
        }
        // 心跳
        if (msg instanceof Tick) {

            log.debug("tick-----------");
            RoomRef roomRef = getRoomRef();
            if (scene.isTimeout()) {
                endGame(false);
                log.debug("超时，结束游戏");
                return;
            }
            roomRef.newTimeout(timeout -> {
                roomRef.sendMsg(new Tick());
            }, 1, TimeUnit.SECONDS);
        }
    }

    public void onMessage(Object msg, CompletableFuture<Object> complete) {

        if (end) {
            complete.completeExceptionally(ModuleAssert.getError(ErrorEnum.ROOM_CLOSE));
            return;
        }

        UserMessage message = (UserMessage) msg;
        msg = message.msg;

        if (msg instanceof FindRoomReq) {// 查找房间
            Logs.common.debug("进入房间:{}", message.uid);
            findRoom(complete, message);
        } else if (msg instanceof PutKeyFvstReq) {// 落子
            putKey((PutKeyFvstReq) msg, complete);
        } else if (msg instanceof ExitRoomReq) {// 离开房间
            exitRoom(message.uid);
        } else if (msg instanceof StartGameReq) {// 开始游戏
            startGame(message.uid);
        } else {
            complete.complete(null);
        }

    }

    /**
     * 开始游戏
     * 一般由前端自动发送，不需要手动点开始
     *
     * @param uid
     */
    private void startGame(long uid) {

    }

    /**
     * 主动离开房间
     *
     * @param uid
     */
    private void exitRoom(long uid) {
        endGame(false);
    }

    /**
     * 查找房间
     *
     * @param complete
     * @param message
     */
    private void findRoom(CompletableFuture<Object> complete, UserMessage message) {
        if (scene.isFull()) {
            complete.completeExceptionally(ModuleAssert.getError(ErrorEnum.ROOM_FULL));
            return;
        }

        if (scene.hasUser(message.uid)) {
            complete.completeExceptionally(ModuleAssert.getError(ErrorEnum.ROOM_RE_ENTER));
            return;
        }
        FiveStoneSide enter = scene.enter(message);
        if (enter == FiveStoneSide.BLACK) {
            complete.complete(FindRoomRes.newBuilder().setRoomId(roomId).setSide(1).build());
        } else {
            complete.complete(FindRoomRes.newBuilder().setRoomId(roomId).setSide(2).build());
        }

        if (scene.isFull()) {
            scene.startGame();
        }
    }

    /**
     * 落子
     *
     * @param msg
     * @param complete
     * @return
     */
    private void putKey(PutKeyFvstReq msg, CompletableFuture<Object> complete) {
        PlayerFight playerFight = scene.getCurrentUser();
        PutKeyFvstReq msg1 = msg;
        boolean win = scene.put(playerFight.side, msg1.getX(), msg1.getY());
        if (win) {
            Logs.common.debug("游戏胜利：{}", scene.getCurrentUid());
            complete.complete(PutKeyFvstRes.newBuilder().setWin(true).build());
            endGame(true);
        }

        scene.changeSide();
        scene.getCurrentUser().broadCast(Message.newBuilder().setPutKeyFvstMsg(
                PutKeyFvstMsg.newBuilder()
                        .setX(msg1.getX())
                        .setY(msg1.getY())
        ).build());

        complete.complete(PutKeyFvstRes.newBuilder().setWin(false).build());
    }

    private void endGame(boolean win) {
        end = true;
        scene.getCurrentUser().broadCast(Message.newBuilder().setGameEndFvstMsg(GameEndFvstMsg.newBuilder().setWin(win)).build());
        scene.opposite().broadCast(Message.newBuilder().setGameEndFvstMsg(GameEndFvstMsg.newBuilder().setWin(!win)).build());
    }

    @Override
    public boolean onError(Object msg, Throwable cause, boolean onEnd) {
        Logs.common.error("", cause);

        return false;
    }

    @Override
    public void onStop() {
        Logs.common.debug("结束房间：{}", roomId);
    }

    /**
     * 用户状态信息
     */
    private static class PlayerFight {
        /**
         * 总时间
         */
        public long totalTime;
        /**
         * 哪一方
         */
        public FiveStoneSide side;
        public long uid;
        public String name;

        public PlayerFight(long uid, FiveStoneSide side) {
            this.uid = uid;
            this.side = side;
        }

        private void broadCast(Message msg) {
            Message build = Message.newBuilder(msg).addAllBroadcastUid(Lists.newArrayList(uid)).build();
            Game.getExecutorManager().getCommonSingleExecutor().submit(() -> {
                Game.pushAndFlushMessage(build);
            });
        }

    }

    static final int maxX = 15;
    static final int maxY = 15;

    /**
     * 棋盘内容
     */
    private static class Scene {
        /**
         * 玩家操作倒计时
         */
        private long timeout;
        /**
         * 当前操作用户
         */
        private long currentUid;
        private PlayerFight p1;
        private PlayerFight p2;

        private Pos[][] grid = new Pos[maxX][maxY];

        public boolean isTimeout() {
            return timeout < System.currentTimeMillis();
        }

        public void changeSide() {
            if (currentUid == p1.uid) {
                currentUid = p2.uid;
            } else {
                currentUid = p1.uid;
            }

            timeout = System.currentTimeMillis() + TIMEOUT;
        }

        public PlayerFight opposite() {
            if (currentUid == p1.uid) {
                return p2;
            }
            return p1;
        }

        public FiveStoneSide enter(UserMessage u) {
            if (p1 == null) {
                p1 = new PlayerFight(u.uid, FiveStoneSide.BLACK);
                p1.name = u.name;
                return FiveStoneSide.BLACK;
            } else {
                p2 = new PlayerFight(u.uid, FiveStoneSide.WHITE);
                p2.name = u.name;
                return FiveStoneSide.WHITE;
            }
        }

        public void startGame() {
            currentUid = p1.uid;
            timeout = TIMEOUT;
            Message msg1 = Message.newBuilder().setStartFvstMsg(StartFvstMsg.newBuilder()
                    .setName(p1.name)
                    .build()).build();
            Message msg2 = Message.newBuilder().setStartFvstMsg(StartFvstMsg.newBuilder()
                    .setName(p2.name)
                    .build()).build();
            p1.broadCast(msg2);
            p2.broadCast(msg1);
        }

        public boolean isFull() {
            return p1 != null && p2 != null;
        }

        // todo 棋盘内容， 胜负判断


        public Scene() {
            for (int i = 0; i < maxX; i++) {
                for (int j = 0; j < maxY; j++) {
                    grid[i][j] = Pos.empty;
                }
            }
        }

        public PlayerFight getCurrentUser() {
            if (p1.uid == currentUid) {
                return p1;
            }
            return p2;
        }

        /**
         * @return true 赢得胜利
         */
        public boolean put(FiveStoneSide stoneSide, int x, int y) {

            if (!canPut(x, y)) {
                Logs.common.error("不能放置，外挂行为:{},{}", x, y);
                return false;
            }

            Pos pos = new Pos(stoneSide, x, y);
            grid[x][y] = pos;
            return pos.checkWin(grid);
        }

        public boolean canPut(int x, int y) {
            if (x < maxX && y < maxY && x >= 0 && y >= 0 && grid[x][y] == Pos.empty) {
                return true;
            }

            return false;
        }

        public boolean hasUser(long uid) {
            return p1 != null && uid == p1.uid || p2 != null && uid == p2.uid;
        }

        public PlayerFight getP1() {
            return p1;
        }

        public void setP1(PlayerFight p1) {
            this.p1 = p1;
        }

        public PlayerFight getP2() {
            return p2;
        }

        public void setP2(PlayerFight p2) {
            this.p2 = p2;
        }

        public long getCurrentUid() {
            return currentUid;
        }

        public void setCurrentUid(long currentUid) {
            this.currentUid = currentUid;
        }

    }

    public static class Pos {
        public final static Pos empty = new Pos(FiveStoneSide.NONE, 0, 0);
        public final int x;
        public final int y;
        private FiveStoneSide side;

        public Pos(FiveStoneSide side, int x, int y) {
            this.side = side;
            this.x = x;
            this.y = y;
        }

        public boolean checkWin(Pos[][] grid) {

            int count = 0;

            // -
            for (int i = -4; i < 5; i++) {
                int nx = x + i;
                if (nx < 0) {
                    continue;
                }
                if (nx >= maxX) {
                    break;
                }

                Pos pos = grid[nx][y];
                if (pos.side == this.side) {
                    count++;
                } else {
                    count = 0;
                }
                if (count > 4) {
                    return true;
                }
            }
            count = 0;

            // |
            for (int i = -4; i < 5; i++) {
                int ny = y + i;
                if (ny < 0) {
                    continue;
                }
                if (ny >= maxX) {
                    break;
                }

                Pos pos = grid[x][ny];
                if (pos.side == this.side) {
                    count++;
                } else {
                    count = 0;
                }
                if (count > 4) {
                    return true;
                }
            }
            count = 0;
            // /
            for (int i = -4, j = 4; i < 5; i++, j--) {
                int nx = x + i;
                int ny = y + j;
                if (ny < 0 || nx < 0) {
                    continue;
                }
                if (ny >= maxY || nx >= maxX) {
                    break;
                }

                Pos pos = grid[nx][ny];
                if (pos.side == this.side) {
                    count++;
                } else {
                    count = 0;
                }
                if (count > 4) {
                    return true;
                }
            }

            count = 0;

            // \
            for (int i = -4, j = -4; i < 5; i++, j++) {
                int nx = x + i;
                int ny = y + j;
                if (ny < 0 || nx < 0) {
                    continue;
                }
                if (ny >= maxY || nx >= maxX) {
                    break;
                }

                Pos pos = grid[nx][ny];
                if (pos.side == this.side) {
                    count++;
                } else {
                    count = 0;
                }
                if (count > 4) {
                    return true;
                }
            }

            return false;

        }
    }

}
