package com.xuren.game.common.net;

import io.netty.buffer.ByteBuf;

/**
 * @author xuren
 */
public interface DataSender {
    void send(ByteBuf byteBuf);
}
