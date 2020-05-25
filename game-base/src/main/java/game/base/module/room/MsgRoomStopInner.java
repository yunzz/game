package game.base.module.room;

/**
 * @author Yunzhe.Jin
 * 2020/4/2 10:09
 */
class MsgRoomStopInner implements RoomMsg {

    public final int roomId;

    public MsgRoomStopInner(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public int roomId() {
        return roomId;
    }
}
