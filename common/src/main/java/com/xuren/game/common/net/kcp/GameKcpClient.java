package com.xuren.game.common.net.kcp;

import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.NetUtils;
import com.xuren.game.common.net.consts.NetConstants;
import com.xuren.game.common.net.enums.PackageTypeEnum;
import com.xuren.game.common.net.enums.TypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.ResourceLeakDetector;
import org.beykery.jkcp.Kcp;
import org.beykery.jkcp.KcpClient;
import org.beykery.jkcp.KcpOnUdp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class GameKcpClient extends KcpClient {
    @Override
    public void handleReceive(ByteBuf byteBuf, KcpOnUdp kcpOnUdp) {

    }

    @Override
    public void handleException(Throwable throwable, KcpOnUdp kcpOnUdp) {
        System.out.println(throwable);
    }

    @Override
    public void handleClose(KcpOnUdp kcp) {
        super.handleClose(kcp);
        System.out.println("服务器离开:" + kcp);
        System.out.println("waitSnd:" + kcp.getKcp().waitSnd());
    }

    @Override
    public void out(ByteBuf msg, Kcp kcp, Object user) {
        super.out(msg, kcp, user);
    }

    public static void main(String[] args) {
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
        GameKcpClient tc = new GameKcpClient();
        tc.noDelay(1, 20, 2, 1);
        tc.setMinRto(10);
        tc.wndSize(32, 32);
        tc.setTimeout(10 * 1000);
        tc.setMtu(512);
        // tc.setConv(121106);//默认conv随机

        tc.connect(new InetSocketAddress("localhost", 2222));
        tc.start();

        NetMsg msg = new NetMsg();
        msg.setMsgCode(10001);
        msg.setRid("10001");
        msg.setData("hahahah".getBytes());
        msg.setType(TypeEnum.INIT);
        msg.setPackageTypeEnum(PackageTypeEnum.REQUEST);
        msg.setRequestId(1);
        msg.setVersion("1");
        tc.send(NetUtils.buildRequest(msg));


        NetMsg msg2 = new NetMsg();
        msg2.setMsgCode(10001);
        msg2.setRid("10001");
        msg2.setData("hahahah".getBytes());
        msg2.setType(TypeEnum.DATA);
        msg2.setPackageTypeEnum(PackageTypeEnum.REQUEST);
        msg2.setRequestId(1);
        msg2.setVersion("1");
        tc.send(NetUtils.buildRequest(msg2));

    }

}
