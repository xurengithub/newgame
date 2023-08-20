package com.xuren.game.common.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static com.xuren.game.common.net.consts.NetConstants.*;
import static com.xuren.game.common.net.consts.NetConstants.PACKAGE_LEN_BYTE;

/**
 * @author xuren
 */
public abstract class NetMsgCodecUtils {
    public static void encodeResponse(NetMsg msg, ByteBuf bf) {
        // todo 长度，类型data、包类型response、请求id、同步时间、处理时间、长度、数据
//        ByteBuf bf = Unpooled.buffer(1 + 1 + 4 + 8 + 4 + 4 + data.length);
        bf.writeInt(1 + 1 + 4 + 8 + 4 + 4 + msg.getData().length);
        bf.writeByte(msg.getType().value());
        bf.writeByte(msg.getPackageTypeEnum().value());
        bf.writeInt(msg.getRequestId());
        bf.writeLong(msg.getSystemTime());
        bf.writeInt(msg.getProcessTime());
        bf.writeInt(msg.getData().length);
        bf.writeBytes(msg.getData());
    }

    public static void encodeRequest(NetMsg msg, ByteBuf bf) {
        byte[] versionBytes = msg.getVersion().getBytes();
        int versionLength = versionBytes.length;

        byte[] ridBytes = msg.getRid().getBytes();
        int ridLength = ridBytes.length;
        int surplusDataLength = msg.getData().length;
        int dataLength = RID_LEN_BYTE + ridLength + VERSION_LEN_BYTE + versionLength + surplusDataLength;

        int dataPoolSize = REQUEST_ID_INT + OP_CODE_INT + DATA_LEN_INT + RID_LEN_BYTE + ridLength + VERSION_LEN_BYTE + versionLength + msg.getData().length;
        int bodyLength = dataPoolSize + TYPE_LEN_BYTE + PACKAGE_LEN_BYTE;

        bf.writeInt(bodyLength);
        bf.writeByte(msg.getType().value());
        bf.writeByte(msg.getPackageTypeEnum().value());
        bf.writeInt(msg.getRequestId());
        bf.writeInt(msg.getMsgCode());
        bf.writeInt(dataLength);
        bf.writeByte(versionLength);
        bf.writeBytes(versionBytes);
        bf.writeByte(ridLength);
        bf.writeBytes(ridBytes);
        bf.writeBytes(msg.getData());
    }
}
