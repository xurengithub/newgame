package com.xuren.game.common.net.channel;

import com.xuren.game.common.net.channel.NetChannel;
import com.xuren.game.common.net.consts.NetConstants;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

/**
 * @author xuren
 */
public class TcpNetChannel extends NetChannel {
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

    @Override
    public boolean isAlive() {
        return context.channel().isActive();
    }

    @Override
    public void close() {
        context.close();
    }
}
