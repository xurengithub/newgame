package com.xuren.game.logic;

import com.xuren.game.common.log.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class SceneMgr {
    private static final SceneMgr instance = new SceneMgr();
    private long prevTimeMs = 0;
    private static long deltaTimeMs = 0;
    private static long deltaTime = 0;

    private void gameLoop() {
        long curMs = System.currentTimeMillis();
        SceneMgr.deltaTimeMs = curMs - this.prevTimeMs;
        SceneMgr.deltaTime = (long) (SceneMgr.deltaTimeMs / 1000.0f);
        onLogicUpdate();
    }

    private void onLogicUpdate() {
        Log.data.info("logic update");
        // 1.处理事件
        // 2.处理迭代任务实体系统
        // 3.迭代怪物系统
    }

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "game-loop-pool");
            }
        });
        executor.scheduleAtFixedRate(SceneMgr.instance::gameLoop,0,  33, TimeUnit.MILLISECONDS);
    }
}
