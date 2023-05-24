package com.xuren.game.common.net;

import io.netty.buffer.ByteBuf;
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
        msg.toString().getBytes(StandardCharsets.UTF_8);

        PooledByteBufAllocator.DEFAULT.buffer();
//        kcpOnUdp.send();
    }
}
