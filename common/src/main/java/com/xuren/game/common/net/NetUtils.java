package com.xuren.game.common.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author xuren
 */
public class NetUtils {
    public static ByteBuf buildRequest(NetMsg msg) {
        byte[] ridBytes = msg.getRid().getBytes();
        int ridLength = ridBytes.length;
        int surplusDataLength = msg.getData().length;
        int dataLength = 1 + ridLength + surplusDataLength;

        int dataPoolSize = 4 + 4 + 4 + 1 + msg.getRid().getBytes().length + msg.getData().length;
        int allPoolSize = 4 + 1 + 1 + dataPoolSize;
        int bodyLength = dataPoolSize + 2;

        ByteBuf bf = Unpooled.buffer(allPoolSize);
        bf.writeInt(bodyLength);
        bf.writeByte(msg.getType().value());
        bf.writeByte(msg.getPackageTypeEnum().value());
        bf.writeInt(msg.getRequestId());
        bf.writeInt(msg.getMsgCode());
        bf.writeInt(dataLength);
        bf.writeByte(ridLength);
        bf.writeBytes(ridBytes);
        bf.writeBytes(msg.getData());
        return bf;
    }
}
