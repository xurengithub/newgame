package com.xuren.game.common.net.tcp.client;

import com.alibaba.fastjson.JSON;
import com.xuren.game.common.log.Log;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class ClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Log.data.info("client receive :{}", JSON.toJSONString(msg));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.channel().close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if (NettyTcpClient.instance().getChannel() != null) {
			NettyTcpClient.instance().getChannel().disconnect();
		}
		NettyTcpClient.instance().setChannel(ctx.channel());
		System.out.println("登录  " + ctx.channel().remoteAddress().toString().replace("/", "") + "  成功");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("\n断开  " + ctx.channel().remoteAddress().toString().replace("/", "") + "  连接");
	}

}
