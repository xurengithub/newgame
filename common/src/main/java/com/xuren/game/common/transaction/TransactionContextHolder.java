package com.xuren.game.common.transaction;

/**
 * @author xuren
 */
public class TransactionContextHolder {
    private static final ThreadLocal<TransactionContext> holder = new ThreadLocal<>();

    public static TransactionContext getCtx() {
        return holder.get();
    }

    public static TransactionContext newCtx() {
        TransactionContext context = new TransactionContext();
        holder.set(context);
        return context;
    }

    public static void remove() {
        holder.remove();
    }
}
