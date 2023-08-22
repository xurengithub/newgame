package com.xuren.game.common.net.tcp.codec.newgame;

import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.NetMsgCodecUtils;
import com.xuren.game.common.net.enums.PackageTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xuren
 */
public class ProtoEncoder3 extends MessageToByteEncoder<NetMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, NetMsg msg, ByteBuf out) throws Exception {
        if (msg.getPackageTypeEnum() == PackageTypeEnum.RESPONSE) {
            NetMsgCodecUtils.encodeResponse(msg, out);
        } else if (msg.getPackageTypeEnum() == PackageTypeEnum.SCENE_SYNC) {
            NetMsgCodecUtils.encodeSceneSyncMsg(msg, out);
        }
    }
}
