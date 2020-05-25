package game.base.core.error;

import com.google.common.collect.ImmutableMap;

import java.util.HashSet;
import java.util.Set;

/**
 * 定义错误消息
 * 消息编号都要正数
 *
 * @author Yunzhe.Jin
 * 2020/3/31 16:47
 */
public enum ErrorEnum implements ModuleErrorNoResolve {
    // 1 ~ 100 系统使用
    ERR_1(1, "系统异常"),
    ERR_TYPE(2, "消息类型错误"),
    ERR_404(3, "Handler not found"),
    ERR_BUSY(4, "系统繁忙"),
    LOGIN_TOKEN_ERROR(5, "token错误"),
    ERR_USER_NOT_LOGIN(11, "用户未登录"),
    ERR_USER_LOGIN(12, "用户已登录"),
    ERR_USER_IN_LOGIN(13, "用户正在登录中"),
    ERR_USER_NOT_FOUND(14, "用户没找到"),
    ERR_NOT_PUSH_MSG(21, "消息不是推送消息"),

    ROOM_NOT_EXIST(101,"房间不存在"),
    ROOM_FULL(102,"房间已满"),
    ROOM_RE_ENTER(103,"重复进入房间"),
    ROOM_ALREADY_IN(104,"已在房间"),
    ROOM_CLOSE(105,"房间关闭"),

    ;

    private final int id;
    private final String module;

    ErrorEnum(int id, String module) {

        this.id = id;
        this.module = module;
    }

    private static ImmutableMap<Integer, ModuleErrorNoResolve> map;

    static {
        ImmutableMap.Builder<Integer, ModuleErrorNoResolve> builder = ImmutableMap.builder();
        HashSet<Integer> ids = new HashSet<>();

        for (ErrorEnum value : ErrorEnum.values()) {
            if (!ids.add(value.id)) {
                throw new IllegalStateException("id重复:" + value.id);
            }
            builder.put(value.id, value);
        }

        map = builder.build();
    }

    public int getId() {
        return id;
    }

    public String getModule() {
        return module;
    }

    public static ModuleErrorNoResolve errStr(int errNo) {
        return map.get(errNo);
    }

    public static Set<Integer> allErrNo() {
        return map.keySet();
    }

    @Override
    public int errorNo() {
        return id;
    }

    @Override
    public String display() {
        return toString();
    }

    @Override
    public String toString() {
        return "ErrorEnum{" +
                "id=" + id +
                ", module='" + module + '\'' +
                '}';
    }
}