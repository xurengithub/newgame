package com.xuren.game.common.net.tcp.oldgame;

import com.xuren.game.common.proto.MsgBase;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author xuren
 */
public class ProtoDecoder2 extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 8) {
            return;
        }
        in.markReaderIndex();
        int len = in.readInt();
        int nameLen = in.readInt();
        if (in.readableBytes() < len - 4) {
            return;
        }
        byte[] nameBytes = new byte[nameLen];
        String name = String.valueOf(in.readBytes(nameBytes));
        byte[] msgBodyBytes = new byte[len - 4 - nameLen];
        in.readBytes(msgBodyBytes);
        out.add(MsgBase.decode(msgBodyBytes));
    }
}
