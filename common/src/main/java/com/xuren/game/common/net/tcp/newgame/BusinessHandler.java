package com.xuren.game.common.net.tcp.newgame;

import com.xuren.game.common.net.NetChannel;
import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.enums.TypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xuren
 */
public class BusinessHandler extends SimpleChannelInboundHandler<NetMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NetMsg msg) throws Exception {
        NetChannel netChannel = NetChannel.findNetChannel(ctx);
        if (msg.getType() == TypeEnum.DATA) {
            execute(netChannel, msg);
        }
    }

    private void execute(NetChannel netChannel, NetMsg msg) {
        netChannel.sendMsg(msg);
    }
}
