package com.xuren.game.common.grpc;

/**
 * @author xuren
 */
public class GrpcClientManagerTest {
    public static void main(String[] args) {
        System.out.println(GrpcClientManager.get(IService.class, "127.0.0.1", 9090).say("hahaha"));
//        ClientCalls.blockingUnaryCall(channel, );
    }
}