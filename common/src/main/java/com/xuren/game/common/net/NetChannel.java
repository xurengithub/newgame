package com.xuren.game.common.net;

import com.xuren.game.common.net.consts.NetConstants;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xuren
 */
public abstract class NetChannel implements INetChannel{
    public static NetChannel findNetChannel(ChannelHandlerContext context) {
        return context.channel().attr(NetConstants.KEY_PLAYER_CHANNEL).get();
    }
}
