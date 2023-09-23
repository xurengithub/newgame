package com.xuren.game.common.grpc;

import com.google.common.base.Preconditions;
import com.google.common.reflect.Invokable;
import com.xuren.game.common.ServerType;
import com.xuren.game.common.cache.NodeCache;
import com.xuren.game.common.utils.RandomUtils;
import io.grpc.CallOptions;
import io.grpc.Deadline;
import io.grpc.MethodDescriptor;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.ClientCalls;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xuren
 */
public class GrpcClientManager {
    public static <T> T global(Class<T> clazz) {
        return get(clazz, ServerType.GLOBAL);
    }

    public static <T> T get(Class<T> clazz, ServerType serverType) {
        var size = NodeCache.nodes(serverType).size();
        Preconditions.checkState(size > 0, "serverType:{} server not exists", serverType);
        var index = RandomUtils.nextInt(0, size);
        var node = NodeCache.globals().get(index);
        return get(clazz, node.getIp(), node.getRpcPort());
    }

    public static <T> T get(Class<T> clazz, String ip, int port) {
        var channel = NettyChannelBuilder.forAddress(ip, port).usePlaintext().maxInboundMessageSize(1024 * 1024 * 10).build();
        var methodMaps = getMethodsMap(clazz);
        ProxyFactory proxyFactory = new ProxyFactory();
        if (clazz.isInterface()) {
            proxyFactory.setInterfaces(new Class[]{clazz});
        } else {
            proxyFactory.setSuperclass(clazz);
        }
        proxyFactory.setHandler((o, method, method1, objects) -> {
            var descriptor = methodMaps.get(method);
            return ClientCalls.blockingUnaryCall(channel, descriptor, CallOptions.DEFAULT.withDeadline(Deadline.after(100, TimeUnit.SECONDS)), objects);
        });
        try {
            return clazz.cast(proxyFactory.createClass().getDeclaredConstructor(new Class[] {}).newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<Method, MethodDescriptor<Object[], Object>> getMethodsMap(Class<?> clazz) {
        return Stream.of(clazz.getMethods())
            .filter(method -> {
                var invoke = Invokable.from(method);
                return invoke.isPublic() && !invoke.isStatic() && !method.getDeclaringClass().equals(Object.class);
            })
            .flatMap(method -> Stream.of(Map.entry(method, GrpcServerManager.methodDescriptor(clazz, method))))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
