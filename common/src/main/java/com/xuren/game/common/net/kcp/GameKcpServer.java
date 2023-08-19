package com.xuren.game.common.net.kcp;

import com.alibaba.fastjson.JSON;
import com.xuren.game.common.net.INetChannel;
import com.xuren.game.common.net.KcpNetChannel;
import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.consts.NetConstants;
import com.xuren.game.common.net.enums.PackageTypeEnum;
import com.xuren.game.common.net.enums.TypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.beykery.jkcp.KcpOnUdp;
import org.beykery.jkcp.KcpServer;

import static com.xuren.game.common.net.consts.NetConstants.KEY_PLAYER_CHANNEL;

public class GameKcpServer extends KcpServer {
    public GameKcpServer(int port, int workerSize) {
        super(port, workerSize);
    }

    @Override
    public void handleReceive(ByteBuf byteBuf, KcpOnUdp kcpOnUdp) {
        // [bodyLength:4][type:1][package:1]\[requestId:4][opCode:4][dataLength:4][ridLength:1][rid:x][data:y]
        int bodyLength = byteBuf.readInt();
        if (bodyLength <= 0 || bodyLength > NetConstants.BODY_LENGTH) {
            kcpOnUdp.close();
            throw new IllegalStateException(kcpOnUdp + "msg length is error:" + bodyLength);
        }

        byte type = byteBuf.readByte();
        TypeEnum typeEnum = TypeEnum.getTypeEnum(type);
        if (typeEnum == null) {
            kcpOnUdp.close();
            throw new IllegalStateException(kcpOnUdp + ", msg type is error:" + type);
        }

        byte packageType = byteBuf.readByte();
        PackageTypeEnum packageTypeEnum = PackageTypeEnum.getPackageEnum(packageType);


        NetMsg msg = new NetMsg();
        msg.setType(typeEnum);
        msg.setPackageTypeEnum(packageTypeEnum);
        byte[] data = new byte[bodyLength - 2];
        byteBuf.readBytes(data);
        msg.setData(data);

        dispatch(msg, kcpOnUdp);

    }

    private void dispatch(NetMsg msg, KcpOnUdp kcpOnUdp) {
        TypeEnum typeEnum = msg.getType();
        switch (typeEnum) {
            case DATA: data(msg, kcpOnUdp);
            break;
            case INIT: init(msg, kcpOnUdp);
            break;
            default:throw new IllegalStateException("type error:" + typeEnum.name());
        }
    }

    private void init(NetMsg msg, KcpOnUdp kcpOnUdp) {
        KcpNetChannel kcpNetChannel = new KcpNetChannel(kcpOnUdp);
        kcpOnUdp.setSession(KEY_PLAYER_CHANNEL, kcpNetChannel);
    }

    private INetChannel findChannel(KcpOnUdp kcpOnUdp) {
        return (INetChannel) kcpOnUdp.getSession(KEY_PLAYER_CHANNEL);
    }

    private void data(NetMsg msg, KcpOnUdp kcpOnUdp) {
        INetChannel netChannel = findChannel(kcpOnUdp);
        PackageTypeEnum packageTypeEnum = msg.getPackageTypeEnum();
        ByteBuf bf = Unpooled.copiedBuffer(msg.getData());
        switch (packageTypeEnum) {
            case REQUEST: request(netChannel, msg, bf);
            break;
        }
    }

    private void request(INetChannel netChannel, NetMsg msg, ByteBuf bf) {
        int requestId = bf.readInt();
        int opCode = bf.readInt();
        int dataLength = bf.readInt();

        msg.setRequestId(requestId);
        msg.setMsgCode(opCode);

        int versionLength = bf.readByte();
        byte[] versionData = new byte[versionLength];
        bf.readBytes(versionData);
        String version = new String(versionData);
        msg.setVersion(version);

        int ridLength = bf.readByte();
        byte[] ridData = new byte[ridLength];
        bf.readBytes(ridData);
        String rid = new String(ridData);
        msg.setRid(rid);

        int surplusDataLength = dataLength - 1 - ridLength - 1 - versionLength;
        byte[] d = new byte[surplusDataLength];
        bf.readBytes(d);
        msg.setData(d);

        if (msg.getType() == TypeEnum.DATA) {
            execute(netChannel, msg);
        }
    }

    private void execute(INetChannel netChannel, NetMsg msg) {
        int msgCode = msg.getMsgCode();
        System.out.println(JSON.toJSONString(msg));
        System.out.println(new String(msg.getData()));
        netChannel.sendMsg(msg);
    }

    @Override
    public void handleException(Throwable throwable, KcpOnUdp kcpOnUdp) {
        System.out.println(throwable);
    }

    @Override
    public void handleClose(KcpOnUdp kcpOnUdp) {
        System.out.println("客户端离开:" + kcpOnUdp);
        System.out.println("waitSnd:" + kcpOnUdp.getKcp().waitSnd());
    }

    public static void main(String[] args) {
        GameKcpServer s = new GameKcpServer(2222, 1);
        s.noDelay(1, 10, 2, 1);
        s.setMinRto(10);
        s.wndSize(64, 64);
        s.setTimeout(10 * 1000);
        s.setMtu(512);
        s.start();
    }
}
