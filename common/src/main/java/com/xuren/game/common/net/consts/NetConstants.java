package com.xuren.game.common.net.consts;

import com.xuren.game.common.net.INetChannel;
import com.xuren.game.common.net.KcpNetChannel;
import com.xuren.game.common.net.NetChannel;
import com.xuren.game.common.net.TcpNetChannel;
import io.netty.util.AttributeKey;

/**
 * @author xuren
 */
public abstract class NetConstants {
    public static final int BODY_LENGTH = 1024 * 1024;

    public static final AttributeKey<NetChannel> KEY_PLAYER_CHANNEL = AttributeKey
        .valueOf("netty.channel");
}
