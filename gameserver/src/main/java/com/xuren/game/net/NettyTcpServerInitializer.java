package com.xuren.game.net;

import com.xuren.game.common.net.tcp.codec.newgame.ProtoDecoder3;
import com.xuren.game.common.net.tcp.codec.newgame.ProtoEncoder3;
import com.xuren.game.common.net.tcp.codec.newgame.Server3Handler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyTcpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("decoder", new ProtoDecoder3())
                .addLast("encoder", new ProtoEncoder3())
                .addLast("idleHandler", new IdleStateHandler(35 * 5, 0, 0, TimeUnit.SECONDS))
                .addLast("server-handler", new Server3Handler())
                .addLast("business-handler", new BusinessHandler());
    }
}
