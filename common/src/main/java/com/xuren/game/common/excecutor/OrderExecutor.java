package com.xuren.game.common.excecutor;

import com.xuren.game.common.log.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 * @author xuren
 */
public class OrderExecutor implements Executor {
    private ExecutorService executorService;
    private Map<String, CompletableFuture<?>> resultMap = new HashMap<>();

    public OrderExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void execute(Runnable command) {
        executorService.execute(command);
    }

    public void submit(String orderKey, Supplier<CompletableFuture<?>> supplier) {
//        CompletableFuture.supplyAsync();
    }
}
