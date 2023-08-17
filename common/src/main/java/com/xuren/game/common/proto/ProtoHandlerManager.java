package com.xuren.game.common.proto;

import com.xuren.game.common.log.Log;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xuren
 */
public class ProtoHandlerManager {
    private static final Map<Integer, Object> handlerMap = new HashMap<>();
    private static final Map<Integer, Method> interfaceMap = new HashMap<>();
    public static void init(String... packages) {
        Set<Class<?>> classSet = new HashSet<>();
        for (String packageName : packages) {
            Reflections reflections = new Reflections(packageName);
            classSet.containsAll(reflections.getTypesAnnotatedWith(ProtoHandler.class));
        }

        for (Class<?> clazz : classSet) {
            ProtoHandler protoHandler = clazz.getAnnotation(ProtoHandler.class);
            int moduleCode = protoHandler.module();
            if (handlerMap.containsKey(moduleCode)) {
                throw new RuntimeException("repeat processor with moduleCode: " + moduleCode + ", processor: " + clazz.getName());
            }
            try {
                handlerMap.put(protoHandler.module(), clazz.newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            Set<Method> methods = ReflectionUtils.getAllMethods(clazz,
                ReflectionUtils.withAnnotation(Interface.class));
            for (Method method : methods) {
                Interface methodAnnotation = method.getAnnotation(Interface.class);
                interfaceMap.put(methodAnnotation.code(), method);
            }
//            for (Method method : clazz.getDeclaredMethods()) {
//                Interface annotation = method.getAnnotation(Interface.class);
//                if (Modifier.isPublic(method.getModifiers()) && annotation != null) {
//                    interfaceMap.put(annotation.code(), method);
//                }
//            }
        }
    }

    public static Object getHandler(int moduleCode) {
        return handlerMap.get(moduleCode);
    }
    public static Method getInterface(int msgCode) {
        return interfaceMap.get(msgCode);
    }
}
