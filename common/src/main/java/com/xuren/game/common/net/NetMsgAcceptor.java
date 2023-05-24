package com.xuren.game.common.net;

import io.netty.buffer.ByteBuf;

/**
 * @author xuren
 */
public class NetMsgAcceptor implements DataAcceptor<NetMsg>{
    @Override
    public NetMsg acceptData(ByteBuf byteBuf) {
        return null;
    }
}
