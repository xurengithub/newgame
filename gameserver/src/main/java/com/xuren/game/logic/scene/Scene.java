package com.xuren.game.logic.scene;

import com.google.api.client.util.Lists;
import com.xuren.game.common.log.Log;
import com.xuren.game.logic.scene.components.AStarComponent;
import com.xuren.game.logic.scene.components.JoystickComponent;
import com.xuren.game.logic.scene.entities.MonsterEntity;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.events.SceneEvent;
import com.xuren.game.logic.scene.nav.Easy3dNav;
import com.xuren.game.logic.scene.options.FindWayOption;
import com.xuren.game.logic.scene.options.JoystickOption;
import com.xuren.game.logic.scene.options.Option;
import com.xuren.game.logic.scene.options.OptionType;
import com.xuren.game.logic.scene.systems.action.JoystickSystem;
import com.xuren.game.logic.scene.systems.nav.AStarNavSystem;
import org.recast4j.detour.extras.Vector3f;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scene {
    private String id;
    private long prevTimeMs = 0;
    public long deltaTimeMs = 0;
    public float deltaTime = 0;
    private final List<SceneEvent> queue = Lists.newArrayList();

    private Map<String, PlayerEntity> onlinePlayerMap = Maps.newHashMap();

    private Map<Integer, MonsterEntity> monsterMap = Maps.newHashMap();

    private Easy3dNav easy3dNav;

    public void init(String id, Easy3dNav easy3dNav) {
        this.id = id;
        this.easy3dNav = easy3dNav;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, r -> new Thread(r, "game-loop-pool"));
        executor.scheduleAtFixedRate(this::gameLoop,0,  50, TimeUnit.MILLISECONDS);
    }

    public void addPlayer(PlayerEntity playerEntity) {
        onlinePlayerMap.put(playerEntity.getRid(), playerEntity);
    }

    public void addSceneEvent(SceneEvent event) {
        synchronized (queue) {
            queue.add(event);
        }
    }

    private void gameLoop() {
        long curMs = System.currentTimeMillis();
        this.deltaTimeMs = curMs - this.prevTimeMs;
        this.deltaTime = (this.deltaTimeMs / 1000.0f);
        this.prevTimeMs = curMs;
        onLogicUpdate();
    }

    private void onLogicUpdate() {
        Log.data.info("logic update deltaTime:{} deltaTimeMs:{}", deltaTime, deltaTimeMs);
        synchronized (queue) {
            for (SceneEvent event : queue) {
                var playerEntity = getOnlinePlayer(event.getRid());
                processEvent(playerEntity, event);
            }
            queue.clear();
        }

        // 迭代摇杆操作
        for (PlayerEntity playerEntity : onlinePlayers()) {
            if (playerEntity.getState() == SceneState.DEATH) {
                continue;
            }

            if (Objects.nonNull(playerEntity.getJoystickComponent()) && Objects.nonNull(playerEntity.getTransformComponent()) && Objects.nonNull(playerEntity.getHealthComponent())) {
                JoystickSystem.update(playerEntity);
            }

            if (Objects.nonNull(playerEntity.getaStarComponent()) && Objects.nonNull(playerEntity.getTransformComponent())) {
                AStarNavSystem.update(playerEntity, this);
            }
        }
        // 1.处理事件
        // 2.处理迭代任务实体系统
        // 3.迭代怪物系统
    }

    private void processEvent(PlayerEntity playerEntity, SceneEvent event) {
        for (Option opt : event.getOptions()) {
            if (opt.getOptionType() == OptionType.JOYSTICK.getType()) {
                JoystickOption joyStickOption = (JoystickOption) opt;
                if (Objects.nonNull(playerEntity.getJoystickComponent())) {
                    JoystickComponent joystickComponent = playerEntity.getJoystickComponent();
                    joystickComponent.setDegree(joyStickOption.getDegree());
                    joystickComponent.setOpen(joyStickOption.isOpen());
                }
            }

            if (opt.getOptionType() == OptionType.FIND_WAY.getType()) {
                FindWayOption findWayOption = (FindWayOption) opt;
                if (Objects.nonNull(playerEntity.getTransformComponent())) {
                    processFindWay(playerEntity, easy3dNav, findWayOption.getPoint());
                }
            }
        }
    }

    private void processFindWay(PlayerEntity playerEntity, Easy3dNav easy3dNav, Vector3f point) {
        if (playerEntity.getState() != SceneState.IDLE && playerEntity.getState() != SceneState.RUN) {
            return;
        }
        if (!AStarNavSystem.initAStarComponent(playerEntity, easy3dNav, point)) {
            return;
        }

        // todo 广播给其他AOI玩家状态变化,操作opt，位移组件，状态同步太其他玩家
        playerEntity.setState(SceneState.RUN);
    }

    private List<PlayerEntity> onlinePlayers() {
        return Lists.newArrayList(onlinePlayerMap.values());
    }

    private PlayerEntity getOnlinePlayer(String rid) {
        return onlinePlayerMap.get(rid);
    }

    public Map<String, PlayerEntity> getOnlinePlayerMap() {
        return onlinePlayerMap;
    }
}
