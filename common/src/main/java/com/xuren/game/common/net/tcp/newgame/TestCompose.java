package com.xuren.game.common.net.tcp.newgame;

import com.xuren.game.common.log.Log;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * @author xuren
 */
public class TestCompose {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Runnable r1 = () -> {
            Log.data.info("r1");
        };
        Runnable r2 = () -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Log.data.info("r2");
        };
        var f2 = CompletableFuture.runAsync(r2, forkJoinPool);
//        f2.thenCompose(v -> {
//            return CompletableFuture.runAsync(r1);
//        });
        f2.thenComposeAsync(v -> {
            Log.data.info("ddd");
            return CompletableFuture.runAsync(r1, new Executor() {
                @Override
                public void execute(Runnable command) {
                    Thread thread = new Thread(command);
                    thread.start();
                }
            });
        }, new Executor() {
            @Override
            public void execute(Runnable command) {
                Thread thread = new Thread(command);
                thread.start();
            }
        });
//        f2.thenCombine(CompletableFuture.runAsync(r1, forkJoinPool), (v1,v2) -> {
//            Log.data.info("combine");
//            return 1;
//        });


        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
