package com.xuren.game.common.net;

import com.xuren.game.common.net.enums.PackageTypeEnum;
import com.xuren.game.common.net.enums.TypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static com.xuren.game.common.net.consts.NetConstants.*;

/**
 * @author xuren
 */
public class NetUtils {
    public static ByteBuf buildRequest(NetMsg msg) {
        byte[] versionBytes = msg.getVersion().getBytes();
        int versionLength = versionBytes.length;

        byte[] ridBytes = msg.getRid().getBytes();
        int ridLength = ridBytes.length;
        int surplusDataLength = msg.getData().length;
        int dataLength = RID_LEN_BYTE + ridLength + VERSION_LEN_BYTE + versionLength + surplusDataLength;

        int dataPoolSize = REQUEST_ID_INT + OP_CODE_INT + DATA_LEN_INT + RID_LEN_BYTE + ridLength + VERSION_LEN_BYTE + versionLength + msg.getData().length;
        int allPoolSize = BODY_LEN_INT + TYPE_LEN_BYTE + PACKAGE_LEN_BYTE + dataPoolSize;
        int bodyLength = dataPoolSize + TYPE_LEN_BYTE + PACKAGE_LEN_BYTE;

        ByteBuf bf = Unpooled.buffer(allPoolSize);
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
        return bf;
    }

    public static void writeNetMsg(NetMsg msg, ByteBuf bf) {
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

    public static NetMsg buildResponseMsg(int msgCode, int requestId, byte[] data) {
        NetMsg netMsg = new NetMsg();
        netMsg.setRequestId(requestId);
        netMsg.setMsgCode(msgCode);
        netMsg.setType(TypeEnum.DATA);
        netMsg.setPackageTypeEnum(PackageTypeEnum.RESPONSE);
        netMsg.setData(data);
        return netMsg;
    }
}
