package com.xuren.game.common.net.udp;


import com.mgame.net.ProtoManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import proto.RoleProto;

import java.net.InetSocketAddress;

public class UDPClient {
    public void run(int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new ProtoHandler());
            Channel ch = b.bind(0).sync().channel();
            //向网段内的所有机器广播
            RoleProto.LoginReq_1001001.Builder builder = RoleProto.LoginReq_1001001.newBuilder();
            builder.setAccount("xuren");
            builder.setPassword("123456");
            ByteBuf buf1 = ProtoManager.wrapReqBuffer(builder.build());

            ch.writeAndFlush(new DatagramPacket(buf1, new InetSocketAddress("127.0.0.1", 8888)));
            //客户端等待15s用于接收服务端的应答消息，然后退出并释放资源
//            if (!ch.closeFuture().await(15000)) {
//                System.out.println("查询超时！");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8888;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        new UDPClient().run(port);
    }
}

