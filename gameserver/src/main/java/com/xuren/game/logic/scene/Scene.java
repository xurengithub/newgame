package com.xuren.game.logic.scene;

import com.google.api.client.util.Lists;
import com.xuren.game.common.log.Log;
import com.xuren.game.logic.scene.components.JoystickComponent;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.events.SceneEvent;
import com.xuren.game.logic.scene.nav.Easy3dNav;
import com.xuren.game.logic.scene.options.*;
import com.xuren.game.logic.scene.systems.action.JoystickSystem;
import com.xuren.game.logic.scene.systems.aoi.AOISystem;
import com.xuren.game.logic.scene.systems.aoi.GridManager;
import com.xuren.game.logic.scene.systems.nav.AStarNavSystem;
import com.xuren.game.logic.scene.utils.VectorUtils;
import com.xuren.game.net.NetMsgSendUtils;
import org.recast4j.detour.extras.Vector3f;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Scene {
    private String id;
    private long prevTimeMs = 0;
    public long deltaTimeMs = 0;
    public float deltaTime = 0;
    private final List<SceneEvent> queue = Lists.newArrayList();

    private Map<String, PlayerEntity> scenePlayerMap = Maps.newConcurrentMap();

//    private Map<Integer, MonsterEntity> monsterMap = Maps.newHashMap();

    private Easy3dNav easy3dNav;
    private GridManager gridManager;
    private Vector3f initPos;
    private float initEulerY;

    public void init(String id, Easy3dNav easy3dNav, GridManager gridManager) {
        this.id = id;
        this.easy3dNav = easy3dNav;
        this.gridManager = gridManager;
        this.initPos = new Vector3f(92.07f, 4.1f, 67.81f);
        this.initEulerY = 45;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, r -> new Thread(r, "game-loop-pool"));
        executor.scheduleAtFixedRate(this::gameLoop,0,  50, TimeUnit.MILLISECONDS);
    }

    public void enter(PlayerEntity playerEntity) {
        Log.data.debug("player:{} enter scene:{}", playerEntity.getRid(), id);
        playerEntity.setSceneId(id);
        playerEntity.getTransformComponent().setPosition(VectorUtils.cloneVector(initPos));
        playerEntity.getTransformComponent().setEulerY(initEulerY);
        playerEntity.setState(SceneState.IDLE);
        gridManager.addObj(playerEntity);
        scenePlayerMap.put(playerEntity.getRid(), playerEntity);

        // 进入场景消息
        NetMsgSendUtils.sendEnterSceneMsg(this, playerEntity);
        // 通知这些人自己进来了
        NetMsgSendUtils.broadcastTransformSyncMsg2Interesting(this, playerEntity);
    }

    public boolean inScene(String rid) {
        return scenePlayerMap.containsKey(rid);
    }

    public void leave(String rid) {
        Log.data.debug("player:{} leave scene:{}", rid, id);
        // todo   通知这些人自己离开了
        var observerPlayers = gridManager.getCurrObserverPlayers(scenePlayerMap.get(rid)).stream().map(PlayerEntity::getRid).collect(Collectors.toList());
        gridManager.removeObj(scenePlayerMap.get(rid));
        scenePlayerMap.remove(rid);

        NetMsgSendUtils.broadcastLeaveGridMsg(observerPlayers, List.of(rid));
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
//        Log.data.info("gameLoop time:{}", System.currentTimeMillis() - this.prevTimeMs);
    }

    private void onLogicUpdate() {
        // 1.处理事件
        // 2.处理迭代任务实体系统
        // 3.迭代怪物系统
//        Log.data.info("logic update deltaTime:{} deltaTimeMs:{}", deltaTime, deltaTimeMs);
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

            if (Objects.nonNull(playerEntity.getJoystickComponent()) && playerEntity.getJoystickComponent().isOpen() && Objects.nonNull(playerEntity.getTransformComponent())) {
                JoystickSystem.update(playerEntity, this, easy3dNav);
            }

            if (Objects.nonNull(playerEntity.getaStarComponent()) && Objects.nonNull(playerEntity.getTransformComponent())) {
                AStarNavSystem.update(playerEntity, this);
            }

            if (Objects.nonNull(playerEntity.getTransformComponent())) {
                AOISystem.update(playerEntity, this);
            }
        }
    }

    private void processEvent(PlayerEntity playerEntity, SceneEvent event) {
        for (Operation opt : event.getOperations()) {
            if (opt.getOperationType() == OperationType.FIND_WAY.getType()) {
                FindWayOption findWayOption = (FindWayOption) opt;
                if (Objects.nonNull(playerEntity.getTransformComponent())) {
                    processFindWay(playerEntity, easy3dNav, findWayOption.getPoint());
                }
            }

            if (opt.getOperationType() == OperationType.JOYSTICK.getType()) {
                JoystickOption joyStickOption = (JoystickOption) opt;
                if (Objects.nonNull(playerEntity.getTransformComponent())) {
                    processJoystick(playerEntity, joyStickOption);
                }
            }

            if (opt.getOperationType() == OperationType.SWITCH_SCENE.getType()) {
                SwitchSceneOption switchSceneOption = (SwitchSceneOption) opt;
                SceneManager.getScene(playerEntity.getSceneId()).leave(playerEntity.getRid());
                SceneManager.getScene(switchSceneOption.getSceneId()).enter(playerEntity);
            }
        }
    }

    private void processJoystick(PlayerEntity playerEntity, JoystickOption joystickOption) {
        JoystickComponent joystickComponent;
        if (Objects.nonNull(playerEntity.getJoystickComponent())) {
            joystickComponent = playerEntity.getJoystickComponent();
        } else {
            joystickComponent = JoystickComponent.create();
            playerEntity.setJoystickComponent(joystickComponent);
        }
        joystickComponent.setOpen(joystickOption.isOpen());
        playerEntity.getTransformComponent().setEulerY(joystickOption.getCameraEulerY() + joystickOption.getDegree());
        // 取消寻路
        playerEntity.setaStarComponent(null);
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
        return Lists.newArrayList(scenePlayerMap.values());
    }

    private PlayerEntity getOnlinePlayer(String rid) {
        return scenePlayerMap.get(rid);
    }

    public Map<String, PlayerEntity> getScenePlayerMap() {
        return scenePlayerMap;
    }

    public String getId() {
        return id;
    }

    public GridManager getGridManager() {
        return gridManager;
    }
}
