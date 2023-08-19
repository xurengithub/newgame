package com.xuren.game.common.net;

import io.netty.buffer.ByteBuf;

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
        bf.writeLong(1);
        bf.writeInt(1);
        bf.writeInt(msg.getData().length);
        bf.writeBytes(msg.getData());
    }
}
