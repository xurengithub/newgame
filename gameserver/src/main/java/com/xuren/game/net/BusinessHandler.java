package com.xuren.game.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xuren.game.common.excecutor.LogicExecutors;
import com.xuren.game.common.net.channel.NetChannel;
import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.NetUtils;
import com.xuren.game.common.net.enums.PackageTypeEnum;
import com.xuren.game.common.net.enums.TypeEnum;
import com.xuren.game.common.proto.MethodHandler;
import com.xuren.game.common.proto.ProtoHandlerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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
            } else if (msg.getPackageTypeEnum() == PackageTypeEnum.SCENE_EVENT) {
                scene(netChannel, msg);
            } else {
                throw new IllegalStateException("illegal packageType");
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
        if (!StringUtils.hasText(msg.getRid())) {
            throw new IllegalStateException("netMsg has not rid");
        }
        int moduleCode = msg.getMsgCode() / 10000;
        Object handler = ProtoHandlerManager.getHandler(moduleCode);
        MethodHandler methodHandler = ProtoHandlerManager.getInterface(msg.getMsgCode());
        LogicExecutors.orderExecutor.compose(msg.getRid(), () -> {
            Object returnValue = methodHandler.getMethodAccess().invoke(handler, methodHandler.getMethod().getName(), msg.getRid(), JSONObject.parseObject(new String(msg.getData()), methodHandler.getParamType()));
            CompletionStage<Object> future;
            if (CompletionStage.class.isAssignableFrom(methodHandler.getMethod().getReturnType())) {
                future = (CompletionStage<Object>) returnValue;
            } else {
                future = CompletableFuture.completedStage(returnValue);
            }
            return future.thenAccept(obj -> {
                // todo  存储，并且将响应发到端
                NetMsg responseNetMsg = NetUtils.buildResponseMsg(msg.getMsgCode(), msg.getRequestId(), encode(obj));
                netChannel.sendMsg(responseNetMsg);
            }).toCompletableFuture();
        });
    }

    private static byte[] encode(Object content) {
        if (content instanceof byte[]) {
            return (byte[]) content;
        } else if (content instanceof CharSequence) {
            return ((CharSequence) content).toString().getBytes(StandardCharsets.UTF_8);
        } else {
            return JSON.toJSONString(
                content,
                // 使fastjson序列化逻辑符合json标准
                SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNonStringKeyAsString
            ).getBytes(StandardCharsets.UTF_8);
        }
    }
}
