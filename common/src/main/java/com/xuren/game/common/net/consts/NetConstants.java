package com.xuren.game.common.net.consts;

import com.xuren.game.common.net.channel.NetChannel;
import io.netty.util.AttributeKey;

/**
 * @author xuren
 */
public abstract class NetConstants {
    public static final int BODY_LENGTH = 1024 * 1024;

    public static final AttributeKey<NetChannel> KEY_PLAYER_CHANNEL = AttributeKey
        .valueOf("netty.channel");

    public static final int BODY_LEN_INT = 4;
    public static final int TYPE_LEN_BYTE = 1;
    public static final int PACKAGE_LEN_BYTE = 1;

    public static final int REQUEST_ID_INT = 4;
    public static final int OP_CODE_INT = 4;
    public static final int DATA_LEN_INT = 4;

    public static final int RID_LEN_BYTE = 1;
    public static final int VERSION_LEN_BYTE = 1;
}
