package com.xuren.game.common.net.udp;

import com.mgame.net.Packet;
import com.mgame.net.ProtoManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = msg.content();

        byte head = buf.readByte();
        short length = buf.readShort();

        int cmd = buf.readInt();

        byte[] bytes = new byte[length - 4];
        buf.readBytes(bytes);
        Packet packet = new Packet(head, cmd, bytes);
        ProtoManager.handleProto(packet, ctx.channel());
    }
}
