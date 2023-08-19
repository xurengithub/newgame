package com.xuren.game.common.net.channel;

import io.netty.buffer.PooledByteBufAllocator;
import org.beykery.jkcp.KcpOnUdp;

import java.nio.charset.StandardCharsets;

/**
 * @author xuren
 */
public class KcpNetChannel extends NetChannel{
    private KcpOnUdp kcpOnUdp;
    public KcpNetChannel(KcpOnUdp kcpOnUdp) {
        this.kcpOnUdp = kcpOnUdp;
    }
    @Override
    public void sendMsg(Object msg) {
        // [bodyLength:4][type:1][package:1]\[requestId:4][opCode:4][dataLength:4][ridLength:1][rid:x][data:y]
        var data = msg.toString().getBytes(StandardCharsets.UTF_8);
        var rid = "10001_1";
        var ridData = rid.getBytes(StandardCharsets.UTF_8);
        var buf = PooledByteBufAllocator.DEFAULT.buffer();
        buf.writeBytes(data);
        buf.writeBytes(ridData);
        buf.writeByte(ridData.length);
        buf.writeInt(data.length + ridData.length + 1);
        buf.writeInt(100001);
        buf.writeInt(1);
        buf.writeByte(1);
        buf.writeByte(1);
        buf.writeInt(buf.readableBytes() + 1);

        kcpOnUdp.send(buf);
    }
}
