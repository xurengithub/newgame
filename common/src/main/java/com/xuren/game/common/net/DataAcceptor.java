package com.xuren.game.common.net;

import io.netty.buffer.ByteBuf;

/**
 * @author xuren
 */
public interface DataAcceptor<T> {
    T acceptData(ByteBuf byteBuf);
}
