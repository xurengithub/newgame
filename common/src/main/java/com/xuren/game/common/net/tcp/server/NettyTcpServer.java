package com.xuren.game.common.net.tcp.server;

import com.xuren.game.common.net.tcp.codec.newgame.BusinessHandler;
import com.xuren.game.common.net.tcp.codec.newgame.ProtoDecoder3;
import com.xuren.game.common.net.tcp.codec.newgame.ProtoEncoder3;
import com.xuren.game.common.net.tcp.codec.newgame.Server2Handler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class NettyTcpServer {
    private static final Logger log = LoggerFactory.getLogger(NettyTcpServer.class);

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final ServerBootstrap bootstrap;

    private int upLimit = 2048;
    private int downLimit = 5120;

    public NettyTcpServer() {
	    bossGroup = new NioEventLoopGroup();
	    workerGroup = new NioEventLoopGroup(4);
	    bootstrap = new ServerBootstrap();
	    bootstrap.group(bossGroup, workerGroup)
			    .channel(NioServerSocketChannel.class)
			    .option(ChannelOption.SO_BACKLOG, 100)
			    .childOption(ChannelOption.TCP_NODELAY, true);
    }

    public void bind(String ip, int port) {
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("decoder", new ProtoDecoder3())
                        .addLast("encoder", new ProtoEncoder3())
                        .addLast("idleHandler", new IdleStateHandler(35 * 5, 0, 0, TimeUnit.SECONDS))
                        .addLast("server-handler", new Server2Handler())
                        .addLast("business-handler", new BusinessHandler());
            }
        });
        InetSocketAddress address = new InetSocketAddress(ip, port);
        try {
            ChannelFuture future = bootstrap.bind(address).sync();
            log.info("Netty Tcp Server started..");

            future.channel().closeFuture().sync();
            log.info("Netty Tcp Server closed..");
        } catch (InterruptedException e) {
            log.error("bind "+ip+":"+port+" failed", e);
            shutdown();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void start() {
    	log.info("Netty Tcp Server started..");
    }

    public void shutdown() {
        try {
            bossGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            log.error("shutdown boss group failed", e);
        }
        try {
            workerGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            log.error("shutdown worker group failed", e);
        }
    }
}