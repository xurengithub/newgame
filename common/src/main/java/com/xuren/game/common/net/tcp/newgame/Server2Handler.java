package com.xuren.game.common.net.tcp.newgame;

import com.xuren.game.common.net.INetChannel;
import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.TcpNetChannel;
import com.xuren.game.common.net.enums.PackageTypeEnum;
import com.xuren.game.common.net.enums.TypeEnum;
import com.xuren.game.common.net.tcp.ServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.xuren.game.common.net.consts.NetConstants.KEY_PLAYER_CHANNEL;

/**
 * @author xuren
 */
public class Server2Handler extends SimpleChannelInboundHandler<NetMsg> {
    private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);


    private final ConcurrentMap<Channel, Channel> ref = new ConcurrentHashMap<Channel, Channel>();

    public Server2Handler() {

    }

//    @Override
//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
////        super.channelRegistered(ctx);
//        ctx.fireChannelRegistered(); // 用这个？
//    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        TcpNetChannel tcpNetChannel = new TcpNetChannel(ctx);

        log.info("[{}] connected", ctx.channel().remoteAddress());
        ref.put(ctx.channel(), ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NetMsg msg) throws Exception {
        INetChannel netChannel = findChannel(ctx);
        if (netChannel == null) {
            log.info("netChannel is null");
            ctx.close();
            return;
        }

        decode(msg);
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        if (log.isDebugEnabled())
            log.debug("["+ctx.channel().remoteAddress()+"] disconnected");
        Channel channel = ref.remove(ctx.channel());
        if (channel != null)
            channel.close();
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        if (ctx.channel().isWritable()) {
            Channel channel = ref.get(ctx.channel());
            if (log.isDebugEnabled())
                log.debug("connection["+channel+"] is available, flush the queue of connection");
            if (channel != null)
                channel.flush();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("handle packet from ["+ctx.channel().id().asLongText()+"] failed!", cause);
        Channel channel = ref.get(ctx.channel());
        if (channel != null) {
            channel.close();
        } else {
            log.error("connection["+ctx.channel().id().asLongText()+"] not found in manager");
            ctx.channel().close();
        }
    }

    private void decode(NetMsg msg) {

        TypeEnum typeEnum = msg.getType();
        switch (typeEnum) {
            case DATA: data(msg);
                break;
            case INIT: init(msg);
                break;
            default:throw new IllegalStateException("type error:" + typeEnum.name());
        }
    }

    private void init(NetMsg msg) {

    }

    private INetChannel findChannel(ChannelHandlerContext context) {
        return context.channel().attr(KEY_PLAYER_CHANNEL).get();
    }

    private void data(NetMsg msg) {
        ByteBuf bf = Unpooled.copiedBuffer(msg.getData());
        byte type = bf.readByte();
        PackageTypeEnum packageTypeEnum = PackageTypeEnum.getPackageEnum(type);
        if (packageTypeEnum == null) {
            return;
        }
        msg.setPackageTypeEnum(packageTypeEnum);
        switch (packageTypeEnum) {
            case REQUEST: request(msg, bf);
                break;
            case SCENE_EVENT: request(msg, bf);
                break;
        }
    }

    private void request(NetMsg msg, ByteBuf bf) {
        int requestId = bf.readInt();
        int opCode = bf.readInt();
        int dataLength = bf.readInt();

        msg.setRequestId(requestId);
        msg.setMsgCode(opCode);

        // 版本号
        int versionLength = bf.readByte();
        byte[] versionData = new byte[versionLength];
        bf.readBytes(versionData);
        String version = new String(versionData);
        msg.setVersion(version);

        // rid
        int ridLength = bf.readByte();
        byte[] ridData = new byte[ridLength];
        bf.readBytes(ridData);
        String rid = new String(ridData);
        msg.setRid(rid);

        int surplusDataLength = dataLength - 1 - ridLength - 1 - versionLength;
        byte[] d = new byte[surplusDataLength];
        bf.readBytes(d);
        msg.setData(d);
    }
}
