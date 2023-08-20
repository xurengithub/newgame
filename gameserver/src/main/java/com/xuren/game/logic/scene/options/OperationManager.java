package com.xuren.game.logic.scene.options;

import com.xuren.game.common.proto.MethodHandler;
import org.reflections.Reflections;
import org.testng.collections.Maps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xuren
 */
public class OperationManager {
    private static final Map<Integer, Class<?>> operationMap = Maps.newHashMap();

//    public static void init() {
//        operationMap.put(OperationType.JOYSTICK.getType(), JoystickOption.class);
//        operationMap.put(OperationType.FIND_WAY.getType(), FindWayOption.class);
//    }

    public static Class<?> findOperationClass(int optionType) {
        return operationMap.get(optionType);
    }

    private static final Map<Integer, Object> handlerMap = new HashMap<>();
    private static final Map<Integer, MethodHandler> interfaceMap = new HashMap<>();
    public static void init(String... packages) {
        Set<Class<?>> classSet = new HashSet<>();
        for (String packageName : packages) {
            Reflections reflections = new Reflections(packageName);
            classSet.addAll(reflections.getTypesAnnotatedWith(OperationAnnotation.class));
        }

        for (Class<?> clazz : classSet) {
            OperationAnnotation operationAnnotation = clazz.getAnnotation(OperationAnnotation.class);
            if (operationMap.containsKey(operationAnnotation.type().getType())) {
                throw new RuntimeException("repeat operationType : " + operationAnnotation.type() + ", processor: " + clazz.getName());
            }
            operationMap.put(operationAnnotation.type().getType(), clazz);
        }
    }
}
