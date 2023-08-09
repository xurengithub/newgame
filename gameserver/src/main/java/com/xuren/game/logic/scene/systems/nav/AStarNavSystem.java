package com.xuren.game.logic.scene.systems.nav;

import com.alibaba.fastjson.JSON;
import com.xuren.game.common.log.Log;
import com.xuren.game.logic.scene.Scene;
import com.xuren.game.logic.scene.SceneState;
import com.xuren.game.logic.scene.components.AStarComponent;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.nav.Easy3dNav;
import com.xuren.game.logic.scene.utils.VectorUtils;
import org.recast4j.detour.extras.Vector3f;
import org.springframework.util.CollectionUtils;

import java.util.List;

public abstract class AStarNavSystem {
    public static boolean initAStarComponent(PlayerEntity playerEntity, Easy3dNav easy3dNav, Vector3f point) {
        Vector3f start = VectorUtils.cloneVector(playerEntity.getTransformComponent().getPosition());
        List<Vector3f> roads = easy3dNav.find(start, point);
        Log.data.info("astar points:{}", JSON.toJSONString(roads));
        if (CollectionUtils.isEmpty(roads)) {
            return false;
        }
        AStarComponent aStarComponent = new AStarComponent();
        aStarComponent.setRoadPoints(roads);
        aStarComponent.setNextStep(1);
        playerEntity.setaStarComponent(aStarComponent);
        walkToNext(playerEntity);
        return true;
    }

    private static void walkToNext(PlayerEntity playerEntity) {
        AStarComponent aStarComponent = playerEntity.getaStarComponent();
        if (aStarComponent.getNextStep() >= aStarComponent.getRoadPoints().size()) {
            playerEntity.setaStarComponent(null);
            playerEntity.setState(SceneState.IDLE);
            // todo 设置角色状态未空闲 并推送
            return;
        }
        Vector3f currentPoint = playerEntity.getTransformComponent().getPosition();
        Vector3f nextPoint = playerEntity.getaStarComponent().getRoadPoints().get(playerEntity.getaStarComponent().getNextStep());
        Vector3f dir = VectorUtils.sub(nextPoint, currentPoint);



        float distance = (float) VectorUtils.distance(currentPoint, nextPoint);
        if (distance <= 0) {
            aStarComponent.setNextStep(aStarComponent.getNextStep() + 1);
            walkToNext(playerEntity);
            return;
        }

        aStarComponent.setNowTime(0);
        aStarComponent.setTotalTime(distance / playerEntity.getTransformComponent().getSpeed());
        aStarComponent.setVx(playerEntity.getTransformComponent().getSpeed() * dir.x / distance);
        aStarComponent.setVy(playerEntity.getTransformComponent().getSpeed() * dir.y / distance);
        aStarComponent.setVz(playerEntity.getTransformComponent().getSpeed() * dir.z / distance);

        Log.data.info("walkNext aStarComponent:{}", JSON.toJSONString(aStarComponent));

        float degree = (float) (Math.atan2(dir.z, dir.x) * 180 / Math.PI);
        degree = 360 - degree;
        playerEntity.getTransformComponent().setEulerY(degree + 90);
    }

    public static void update(PlayerEntity playerEntity, Scene scene) {
        AStarComponent aStarComponent = playerEntity.getaStarComponent();
        float dt = scene.deltaTime;
        aStarComponent.setNowTime(aStarComponent.getNowTime() + dt);
        if (aStarComponent.getNowTime() > aStarComponent.getTotalTime()) {
            dt -= (aStarComponent.getNowTime() - aStarComponent.getTotalTime());
        }
        Vector3f pos = playerEntity.getTransformComponent().getPosition();
        pos.x += aStarComponent.getVx() * dt;
        pos.y += aStarComponent.getVy() * dt;
        pos.z += aStarComponent.getVz() * dt;

        Log.data.info("playerComponent:{}", JSON.toJSONString(pos));
        if (aStarComponent.getNowTime() >= aStarComponent.getTotalTime()) {
            aStarComponent.setNextStep(aStarComponent.getNextStep() + 1);
            walkToNext(playerEntity);
        }
    }
}
