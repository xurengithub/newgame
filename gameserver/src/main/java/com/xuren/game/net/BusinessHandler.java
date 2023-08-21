package com.xuren.game.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xuren.game.cache.PlayerCache;
import com.xuren.game.common.excecutor.LogicExecutors;
import com.xuren.game.common.log.Log;
import com.xuren.game.common.net.channel.NetChannel;
import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.NetUtils;
import com.xuren.game.common.net.enums.PackageTypeEnum;
import com.xuren.game.common.net.enums.TypeEnum;
import com.xuren.game.common.proto.MethodHandler;
import com.xuren.game.common.proto.ProtoHandlerManager;
import com.xuren.game.common.redis.LettuceRedis;
import com.xuren.game.consts.RedisConsts;
import com.xuren.game.logic.scene.SceneManager;
import com.xuren.game.logic.scene.events.SceneEvent;
import com.xuren.game.logic.scene.options.Operation;
import com.xuren.game.logic.scene.options.OperationManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * @author xuren
 */
public class BusinessHandler extends SimpleChannelInboundHandler<NetMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NetMsg msg) throws Exception {
        NetChannel netChannel = NetChannel.findNetChannel(ctx);
        if (msg.getType() == TypeEnum.DATA) {
            if (!StringUtils.hasText(msg.getRid())) {
                throw new IllegalStateException("netMsg has not rid");
            }
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
        // todo  登陆了消息才有用
        Class<?> operationClass = OperationManager.findOperationClass(msg.getMsgCode());
        if (operationClass == null) {
            Log.data.error("operationType:{} dont exists", msg.getMsgCode());
            return;
        }
        // 查看角色在哪个场景
        // 根据msgCode解析出场景事件
        // 将事件加入到场景的队列中
        // 进入场景怎么办，事件里有进入场景的id
        PlayerCache.getAsync(msg.getRid())
            .thenAccept(playerEntity -> {
                if (playerEntity != null) {
                    Operation operationObj = (Operation) JSON.parseObject(new String(msg.getData()), operationClass);
                    operationObj.setOperationType(msg.getMsgCode());
                    SceneEvent sceneEvent = new SceneEvent();
                    sceneEvent.setRid(playerEntity.getRid());
                    sceneEvent.setOperations(List.of(operationObj));
                    if (!StringUtils.hasText(playerEntity.getSceneId())) {
                        SceneManager.enterDefaultScene(playerEntity);
                    } else {
                        if (!SceneManager.inScene(playerEntity)) {
                            SceneManager.getScene(playerEntity.getSceneId()).enter(playerEntity);
                        }
                    }
                    SceneManager.getScene(playerEntity.getSceneId()).addSceneEvent(sceneEvent);
                } else {
                    Log.data.error("no player:{} cache, can not sync scene", msg.getRid());
                }
            });

    }

    private void execute(NetChannel netChannel, NetMsg msg) {
        // 如果是登陆游戏
        // 如果角色有场景则加入场景指定地点，如果角色没有过场景则加入初始场景@
        int moduleCode = msg.getMsgCode() / 10000;
        Object handler = ProtoHandlerManager.getHandler(moduleCode);
        MethodHandler methodHandler = ProtoHandlerManager.getInterface(msg.getMsgCode());
        var playerFuture = PlayerCache.getAsync(msg.getRid());
        LogicExecutors.orderExecutor.compose(msg.getRid(), () -> {
            // todo checktoken
//            Object returnValue = methodHandler.getMethodAccess().invoke(handler, methodHandler.getMethod().getName(), msg.getRid(), JSONObject.parseObject(new String(msg.getData()), methodHandler.getParamType()));
            CompletionStage<Object> future;
            long t1 = System.currentTimeMillis();
            if (CompletionStage.class.isAssignableFrom(methodHandler.getMethod().getReturnType())) {
                future = playerFuture.thenCompose((playerEntity -> {
                    Object returnValue = methodHandler.getMethodAccess().invoke(handler, methodHandler.getMethod().getName(), playerEntity, JSONObject.parseObject(new String(msg.getData()), methodHandler.getParamType()));
                    return (CompletionStage<Object>) returnValue;
                }));
            } else {
                future = playerFuture.thenApply(playerEntity -> methodHandler.getMethodAccess().invoke(handler, methodHandler.getMethod().getName(), playerEntity, JSONObject.parseObject(new String(msg.getData()), methodHandler.getParamType())));
            }
            return future.thenAccept(obj -> {
                // todo  存储，并且将响应发到端
                NetMsg responseNetMsg = NetUtils.buildResponseMsg(msg.getMsgCode(), msg.getRequestId(), encode(obj));
                long t2 = System.currentTimeMillis();
                responseNetMsg.setSystemTime(t2);
                responseNetMsg.setProcessTime((int) (t2 - t1));
                netChannel.sendMsg(responseNetMsg);
            }).toCompletableFuture();
        });
    }

    private void checkToken(String uid, String token) {
        if (!LettuceRedis.sync().get(RedisConsts.TOKEN + ":" + uid).equals(token)) {
            throw new RuntimeException("token error");
        }
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