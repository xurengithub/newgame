package com.xuren.game.common.net.tcp.newgame;

import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.NetMsgCodecUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xuren
 */
public class ProtoEncoder3 extends MessageToByteEncoder<NetMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, NetMsg msg, ByteBuf out) throws Exception {
        NetMsgCodecUtils.encodeResponse(msg, out);
    }
}
