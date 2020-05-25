package game.base.module.room;

/**
 * @author Yunzhe.Jin
 * 2020/4/2 10:20
 */
class MsgRoomCreateInner implements RoomMsg{
    private Room room;

    public MsgRoomCreateInner(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public int roomId() {
        return room.getRoomId();
    }
}
