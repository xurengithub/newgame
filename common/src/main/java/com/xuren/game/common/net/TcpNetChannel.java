package com.xuren.game.common.net;

import com.xuren.game.common.net.consts.NetConstants;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xuren
 */
public class TcpNetChannel extends NetChannel{
    private ChannelHandlerContext context;
    public TcpNetChannel(ChannelHandlerContext context) {
        this.context = context;
        this.context.channel().attr(NetConstants.KEY_PLAYER_CHANNEL).set(this);
    }
    @Override
    public void sendMsg(Object msg) {
        if (context != null) {
            context.writeAndFlush(msg);
        }
    }

    public static NetChannel findNetChannel(ChannelHandlerContext context) {
        return context.channel().attr(NetConstants.KEY_PLAYER_CHANNEL).get();
    }
}
