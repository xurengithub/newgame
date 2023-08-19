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
    private final ExecutorService executorService;
    private static final Map<String, CompletableFuture<?>> resultMap = new HashMap<>();

    public OrderExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void execute(Runnable command) {
        executorService.execute(command);
    }

    public <T> CompletableFuture<T> compose(String key, Supplier<CompletableFuture<T>> supplier) {
        var future = resultMap.compute(key, (k, f) -> f == null ? CompletableFuture.runAsync(() -> {}).thenCompose(v -> supplier.get()) : f.thenCompose(v -> supplier.get()));
        if (future == null) {
            var newFuture = CompletableFuture.runAsync(() -> {}).thenCompose(v -> supplier.get());
            resultMap.put(key, newFuture);
            return newFuture;
        } else {
            var newFuture = future.thenCompose(v -> supplier.get());
            resultMap.put(key, newFuture);
            return newFuture;
        }
    }
}
