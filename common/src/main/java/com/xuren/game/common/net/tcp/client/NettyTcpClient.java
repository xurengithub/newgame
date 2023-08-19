package com.xuren.game.common.net.tcp.client;

import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.tcp.codec.newgame.ProtoDecoder3;
import com.xuren.game.common.net.tcp.codec.newgame.ProtoEncoder3;
import com.xuren.game.common.net.tcp.codec.oldgame.ProtoDecoder2;
import com.xuren.game.common.net.tcp.codec.oldgame.ProtoEncoder2;
import com.xuren.game.common.proto.MsgBase;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;


public class NettyTcpClient {
	private static NettyTcpClient ins = null;
	private static Channel channel;
	
	public static NettyTcpClient instance(){
		if(ins == null){
			ins = new NettyTcpClient();
		}
		return ins;
	}
	
	public void conect(String host, int port){
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("decoder", new ProtoDecoder3());
				pipeline.addLast("encoder", new ClientEncoder3());
				pipeline.addLast("serverHandler", new ClientHandler());
			}
		});

		ChannelFuture future;
		try {
			future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
			System.out.println("----channel:"+future.channel());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//future.channel().closeFuture().awaitUninterruptibly();
	}
	
	public void send(NetMsg msg) {
		if (channel == null || msg == null || !channel.isWritable()) {
			return;
		}
//		Packet packet = new Packet(Packet.HEAD_TCP, cmd, msg.toByteArray());
//		channel.writeAndFlush(packet);
		channel.writeAndFlush(msg);
	}
	
	public void setChannel(Channel ch){
		channel = ch;
	}
	
	public Channel getChannel(){
		return channel;
	}
}
