package com.xuren.game.common.net.tcp.codec.oldgame;

import com.alibaba.fastjson.JSON;
import com.xuren.game.common.proto.MsgBase;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xuren
 */
public class ProtoEncoder2 extends MessageToByteEncoder<MsgBase> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MsgBase msg, ByteBuf out) throws Exception {
        byte[] msgBodyBytes = JSON.toJSONString(msg).getBytes();
        byte[] nameBytes = MsgBase.EncodeName(msg);
        out.writeInt(msgBodyBytes.length + nameBytes.length);
        out.writeBytes(nameBytes);
        out.writeBytes(msgBodyBytes);
    }
}
