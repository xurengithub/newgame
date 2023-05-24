package com.xuren.game.common.net.consts;

import com.xuren.game.common.net.KcpNetChannel;
import io.netty.util.AttributeKey;

/**
 * @author xuren
 */
public abstract class NetConstants {
    public static final int BODY_LENGTH = 1024 * 1024;

    public static final AttributeKey<KcpNetChannel> KEY_PLAYER_CHANNEL = AttributeKey
        .valueOf("netty.channel");
}
