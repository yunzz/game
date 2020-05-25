package game.world.core;

import game.base.core.error.ErrorEnum;
import game.base.core.error.ModuleAssert;
import game.base.module.room.Room;
import game.base.module.room.RoomRef;
import game.world.WorldServer;
import reactor.netty.internal.shaded.reactor.pool.Pool;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * 房间管理
 *
 * @author Yunzhe.Jin
 * 2020/4/3 17:33
 */
public class PlayerRoomManager {
    private Lock lock = new ReentrantLock();

    private ConcurrentHashMap<Long, RoomRef> uidMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, RoomRef> roomsIdMap = new ConcurrentHashMap<>();
    private HashMap<Integer, RoomRef> waitingRoomsIdMap = new HashMap<>();

    public RoomRef getRoomById(Integer id) {
        return roomsIdMap.get(id);
    }

    public RoomRef getPlayerRoom(Long uid) {
        return uidMap.get(uid);
    }

    public RoomRef joinRoom(Long uid, Integer roomId) {

        RoomRef roomRef = roomsIdMap.get(roomId);
        if (roomRef != null) {
            uidMap.put(uid, roomRef);
        }
        return roomRef;
    }

    public RoomRef findRoom(Long uid, Supplier<RoomRef> room) {
        lock.lock();
        RoomRef poll = null;
        try {
            RoomRef roomRef1 = uidMap.get(uid);
            ModuleAssert.isNull(roomRef1, ErrorEnum.ROOM_ALREADY_IN);

            Optional<Map.Entry<Integer, RoomRef>> first = waitingRoomsIdMap.entrySet().stream().findFirst();

            if (first.isPresent()) {
                poll = first.get().getValue();
                waitingRoomsIdMap.remove(first.get().getKey());
            }
            if (poll == null) {
                poll = room.get();
                roomsIdMap.put(poll.roomId(), poll);
                waitingRoomsIdMap.put(poll.roomId(), poll);
            }

            uidMap.put(uid, poll);
            return poll;
        } finally {
            lock.unlock();
        }
    }

    public RoomRef createRoom(Room room) {
        RoomRef roomRef = WorldServer.getRoomManager().create(room);
        roomsIdMap.put(roomRef.roomId(), roomRef);
        return roomRef;
    }
}
