package com.xuren.game.logic.buff;

/**
 * @author xuren
 */
public interface Buff {
    /**
     * 未加到容器之前
     */
    void onBuffAwake();

    /**
     * 开始生效时
     */
    void onBuffStart();

    /**
     * 刷新
     */
    void onBuffRefresh();

    /**
     * 移除之前
     */
    void onBuffRemove();

    /**
     * 销毁后
     */
    void onBuffDestroy();

    void startIntervalThink(float interval);

    void onIntervalThink();
}
