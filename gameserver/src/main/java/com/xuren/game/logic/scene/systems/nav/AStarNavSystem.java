package com.xuren.game.logic.scene.systems.nav;

import com.xuren.game.logic.scene.components.AStarComponent;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.nav.Easy3dNav;
import com.xuren.game.logic.scene.utils.VectorUtils;
import org.recast4j.detour.extras.Vector3f;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class AStarNavSystem {
    public static boolean initAStarComponent(PlayerEntity playerEntity, Easy3dNav easy3dNav, Vector3f point) {

        Vector3f start = VectorUtils.cloneVector(playerEntity.getTransformComponent().getPosition());
        List<Vector3f> roads = easy3dNav.find(start, point);
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
            // todo 设置角色状态未空闲 并推送
            return;
        }
        Vector3f currentPoint = playerEntity.getTransformComponent().getPosition();
        Vector3f nextPoint = playerEntity.getaStarComponent().getRoadPoints().get(playerEntity.getaStarComponent().getNextStep());

        aStarComponent.setNowTime(0);

        aStarComponent.setTotalTime();

    }

    public static void main(String[] args) {
        System.out.println(Math.sin(30));;
        System.out.println(Math.asin(0.5));
    }
}
