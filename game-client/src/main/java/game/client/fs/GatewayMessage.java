package game.client.fs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 网关消息
 */
public class GatewayMessage {
    public static final int MSG_LENGTH = 6;
    public static final byte VERSION = 1;

    /**
     * 消息体长度
     **/
    private int length;
    /**
     * 消息版本
     */
    private byte version;
    /**
     * 消息号
     **/
    private int msgNo;
    /**
     * 消息状态
     */
    private byte code;
    /**
     * 消息内容
     */
    private byte[] msgBytes;
    /**
     * 原始数据
     */
    private byte[] allBytes;

    /**
     * 消息内容
     */
    private ByteBuffer msgByteBuffer;

    /**
     * RouteMsg 转 用户msg
     * 有msgByteBuffer
     * 无 msgBytes
     * @param version  消息版本
     * @param msgNo    消息号
     * @param msgByteBuffer 可以传空
     */
    public GatewayMessage(byte version, int msgNo, byte code, ByteBuffer msgByteBuffer) {
        int length;
        if (msgByteBuffer != null && msgByteBuffer.hasRemaining()) {
            length = msgByteBuffer.remaining() + MSG_LENGTH;
        } else {
            length = MSG_LENGTH;
        }
        convertParam(length, version, msgNo, code, null);

        int arrLength = length + 4;

        ByteBuf byteBuf = Unpooled.buffer(arrLength, arrLength);
        if (msgByteBuffer != null && msgByteBuffer.hasRemaining()) {
            byteBuf.writeInt(length);
        } else {
            byteBuf.writeInt(MSG_LENGTH);
        }
        byteBuf.writeByte(this.version);
        byteBuf.writeInt(this.msgNo);
        byteBuf.writeByte(this.code);
        if (msgByteBuffer != null && msgByteBuffer.hasRemaining()) {
            byteBuf.writeBytes(msgByteBuffer);
        }
        this.allBytes = byteBuf.array();
    }

    /**
     * 解码使用
     * 有 msgBytes
     * 无 msgByteBuffer
     * @param byteBuf
     */
    public GatewayMessage(ByteBuf byteBuf) {
        int length = byteBuf.readInt();
        byte version = byteBuf.readByte();
        int msgNo = byteBuf.readInt();
        byte code = byteBuf.readByte();
        msgBytes = ByteBufUtil.getBytes(byteBuf);
        convertParam(length, version, msgNo, code, msgBytes);
    }

    private void convertParam(int length, byte version, int msgNo, byte code, byte[] msgBytes) {
        this.length = length;
        this.version = version;
        this.msgNo = msgNo;
        this.code = code;
        if (msgBytes != null) {
            this.msgBytes = msgBytes;
        }
    }

    public int getLength() {
        return length;
    }

    public byte getVersion() {
        return version;
    }

    public int getMsgNo() {
        return msgNo;
    }

    public byte[] getMsgBytes() {
        return msgBytes;
    }

    public byte[] getAllBytes() {
        return allBytes;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "GatewayMessage{" +
                "length=" + length +
                ", version=" + version +
                ", msgNo=" + msgNo +
                ", code=" + code +
                ", msgBytes=" + Arrays.toString(msgBytes) +
                ", allBytes=" + Arrays.toString(allBytes) +
                '}';
    }

}
