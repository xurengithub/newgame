package com.xuren.game.common.net.tcp.codec.newgame;

import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.consts.NetConstants;
import com.xuren.game.common.net.enums.TypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author xuren
 */
public class ProtoDecoder3 extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        // [bodyLength:4][type:1][package:1]\[requestId:4][opCode:4][dataLength:4][ridLength:1][rid:x][data:y]

        int length = byteBuf.readableBytes();
        if (length < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        int bodyLength = byteBuf.readInt();
        if (bodyLength <= 0 || bodyLength > NetConstants.BODY_LENGTH) {
            ctx.close();
            throw new IllegalArgumentException(ctx + " , msg lenth is error:" + bodyLength);
        }
        if (bodyLength > byteBuf.readableBytes()) {
            byteBuf.resetReaderIndex();
            return;
        }

        byte type = byteBuf.readByte();
        TypeEnum typeEnum = TypeEnum.getTypeEnum(type);
        if (typeEnum == null) {
            ctx.close();
            throw new IllegalArgumentException(ctx + " , msg type is error:" + type);
        }

        NetMsg msg = new NetMsg();
        msg.setType(typeEnum);
        byte[] data = new byte[bodyLength - 1];
        byteBuf.readBytes(data);
        msg.setData(data);

        out.add(msg);
    }

}
