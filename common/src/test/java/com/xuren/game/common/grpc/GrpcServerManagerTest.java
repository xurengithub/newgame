package com.xuren.game.common.grpc;

import com.google.api.client.util.Lists;
import io.grpc.BindableService;

import java.util.List;


/**
 * @author xuren
 */
public class GrpcServerManagerTest {
    public static void main(String[] args) {
        var definition = GrpcServerManager.wrap(IService.class, new MyService());

        List<BindableService> bindableServices = Lists.newArrayList();
        bindableServices.add(() -> definition);
        GrpcServerManager.start(9090, bindableServices);
    }
}