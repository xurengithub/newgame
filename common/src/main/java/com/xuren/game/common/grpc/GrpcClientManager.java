package com.xuren.game.common.grpc;

import com.google.common.reflect.Invokable;
import io.grpc.CallOptions;
import io.grpc.Deadline;
import io.grpc.MethodDescriptor;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.ClientCalls;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xuren
 */
public class GrpcClientManager {
    public static void main(String[] args) {
        System.out.println(get(IService.class).say("hahaha"));;
//        ClientCalls.blockingUnaryCall(channel, );
    }

    public static <T> T get(Class<T> clazz) {
        String ip = "127.0.0.1";
        int port = 9090;
        var channel = NettyChannelBuilder.forAddress(ip, port).usePlaintext().maxInboundMessageSize(1024 * 1024 * 10).build();
        var methodMaps = getMethodsMap(clazz);
        ProxyFactory proxyFactory = new ProxyFactory();
        if (clazz.isInterface()) {
            proxyFactory.setInterfaces(new Class[]{clazz});
        } else {
            proxyFactory.setSuperclass(clazz);
        }
        proxyFactory.setHandler(new MethodHandler() {
            @Override
            public Object invoke(Object o, Method method, Method method1, Object[] objects) throws Throwable {
                var descriptor = methodMaps.get(method);
                return ClientCalls.blockingUnaryCall(channel, descriptor, CallOptions.DEFAULT.withDeadline(Deadline.after(100, TimeUnit.SECONDS)), objects);
            }
        });
        try {
            return (T) proxyFactory.createClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
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
