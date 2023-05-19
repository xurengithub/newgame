package com.xuren.game.common.server;

/**
 * 可以有不同服务，业务，战斗，实时等等
 * @author xuren
 */
public interface Server extends AutoCloseable{
    /**
     * 服务初始化
     */
    void init();

    /**
     * 服务启动
     */
    void start();
}
