package com.xuren.game.common.excecutor;

import java.util.concurrent.ForkJoinPool;

/**
 * @author xuren
 */
public class LogicExecutors {
    public static OrderExecutor orderExecutor;

    static {
        orderExecutor = new OrderExecutor(ForkJoinPool.commonPool());
    }
}
