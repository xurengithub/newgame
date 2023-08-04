package com.xuren.game.logic;

import com.google.api.client.util.Lists;
import com.xuren.game.common.log.Log;
import com.xuren.game.logic.model.PlayerEntity;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class SceneMgr {
    private static final SceneMgr instance = new SceneMgr();
    private long prevTimeMs = 0;
    private static long deltaTimeMs = 0;
    private static long deltaTime = 0;
    private final List<SceneEvent> queue = Lists.newArrayList();

    private void gameLoop() {
        long curMs = System.currentTimeMillis();
        SceneMgr.deltaTimeMs = curMs - this.prevTimeMs;
        SceneMgr.deltaTime = (long) (SceneMgr.deltaTimeMs / 1000.0f);
        onLogicUpdate();
    }

    private void addScene(SceneEvent event) {
        synchronized (queue) {
            queue.add(event);
        }
    }

    private void onLogicUpdate() {
        Log.data.info("logic update");
        synchronized (queue) {
            for (SceneEvent event : queue) {
                var playerEntity = OnlinePlayerManager.getOnlinePlayer(event.getRid());
                processEvent(playerEntity, event);
            }
            for (PlayerEntity entity : OnlinePlayerManager.onlinePlayers()) {

            }
            queue.clear();
        }
        // 1.处理事件
        // 2.处理迭代任务实体系统
        // 3.迭代怪物系统
    }

    private void processEvent(PlayerEntity playerEntity, SceneEvent event) {
        if (event.getEventType() == SceneEventType.JOYSTICK.getType()) {
            if (Objects.nonNull(playerEntity.getTransformComponent())) {

            }
        }
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
