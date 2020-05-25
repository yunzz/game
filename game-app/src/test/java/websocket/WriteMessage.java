package websocket;

import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;


/**
 * @author Yunzhe.Jin
 * 2020/5/14 20:26
 */
public class WriteMessage {
    private MessageLite messageLite;
    private byte version;
    private int msgNo;
    private byte code;


    public void setMessageLite(MessageLite messageLite) {
        this.messageLite = messageLite;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public void setMsgNo(int msgNo) {
        this.msgNo = msgNo;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public byte[] getMessage() {
        byte[] bytes = messageLite.toByteArray();
        int len = bytes.length + 4 + 1 + 4 + 1;

        ByteBuf byteBuf = Unpooled.buffer(len, len);
        byteBuf.writeInt(len);
        byteBuf.writeByte(version);
        byteBuf.writeInt(msgNo);
        byteBuf.writeByte(code);
        byteBuf.writeBytes(bytes);

        return ByteBufUtil.getBytes(byteBuf);
    }
}
