package com.xuren.game.common.net.tcp.newgame;

import com.xuren.game.common.net.NetChannel;
import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.enums.PackageTypeEnum;
import com.xuren.game.common.net.enums.TypeEnum;
import com.xuren.game.common.proto.MethodHandler;
import com.xuren.game.common.proto.ProtoHandlerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.util.StringUtils;
import org.testng.collections.Maps;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

/**
 * @author xuren
 */
public class BusinessHandler extends SimpleChannelInboundHandler<NetMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NetMsg msg) throws Exception {
        NetChannel netChannel = NetChannel.findNetChannel(ctx);
        if (msg.getType() == TypeEnum.DATA) {
            if (msg.getPackageTypeEnum() == PackageTypeEnum.REQUEST) {
                execute(netChannel, msg);
            } else {
                scene(netChannel, msg);
            }
        }
    }

    private void scene(NetChannel netChannel, NetMsg msg) {
        // 查看角色在哪个场景
        // 根据msgCode解析出场景事件
        // 将事件加入到场景的队列中
        // 进入场景怎么办，事件里有进入场景的id
    }

    private void execute(NetChannel netChannel, NetMsg msg) {
        // 如果是登陆游戏
        // 如果角色有场景则加入场景指定地点，如果角色没有过场景则加入初始场景@
        if (StringUtils.hasText(msg.getRid())) {
            throw new IllegalStateException("netMsg has not rid");
        }
        int moduleCode = msg.getMsgCode() / 10000;
        Object handler = ProtoHandlerManager.getHandler(moduleCode);
        MethodHandler methodHandler = ProtoHandlerManager.getInterface(msg.getMsgCode());
    }
}
