package com.xuren.game.common.net.tcp;

import com.xuren.game.common.proto.MsgBase;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xuren
 */
public class Server2Handler extends SimpleChannelInboundHandler<MsgBase> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgBase msg) throws Exception {

    }
}
