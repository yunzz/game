package game.base.module.room;

/**
 * @author Yunzhe.Jin
 * 2020/4/2 10:37
 */
public class MsgRoomInner implements RoomMsg {
    public final int roomId;

    public final Object msg;

    public MsgRoomInner(int roomId, Object msg) {
        this.roomId = roomId;
        this.msg = msg;
    }

    @Override
    public int roomId() {
        return roomId;
    }

    @Override
    public Object msg() {
        return msg;
    }
}
