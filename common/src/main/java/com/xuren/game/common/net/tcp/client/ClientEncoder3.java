package com.xuren.game.common.net.tcp.client;

import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.NetMsgCodecUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientEncoder3 extends MessageToByteEncoder<NetMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NetMsg msg, ByteBuf byteBuf) throws Exception {
        NetMsgCodecUtils.encodeRequest(msg, byteBuf);
    }
}
