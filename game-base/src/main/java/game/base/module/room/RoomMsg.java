package game.base.module.room;

/**
 * @author Yunzhe.Jin
 * 2020/4/2 11:22
 */
public interface RoomMsg {
    int roomId();
    default Object msg(){
        return null;
    }
}
