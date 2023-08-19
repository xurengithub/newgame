package com.xuren.game.common.proto;

import com.esotericsoftware.reflectasm.MethodAccess;
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
    private static final Map<Integer, MethodHandler> interfaceMap = new HashMap<>();
    public static void init(String... packages) {
        Set<Class<?>> classSet = new HashSet<>();
        for (String packageName : packages) {
            Reflections reflections = new Reflections(packageName);
            classSet.addAll(reflections.getTypesAnnotatedWith(ProtoHandler.class));
        }

        for (Class<?> clazz : classSet) {
            ProtoHandler protoHandler = clazz.getAnnotation(ProtoHandler.class);
            int moduleCode = protoHandler.module();
            if (handlerMap.containsKey(moduleCode)) {
                throw new RuntimeException("repeat processor with moduleCode: " + moduleCode + ", processor: " + clazz.getName());
            }
            try {
                handlerMap.put(protoHandler.module()/10000, clazz.newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            MethodAccess access = MethodAccess.get(clazz);
            Set<Method> methods = ReflectionUtils.getAllMethods(clazz,
                ReflectionUtils.withAnnotation(Interface.class));
            for (Method method : methods) {
                Interface methodAnnotation = method.getAnnotation(Interface.class);
                MethodHandler methodHandler = new MethodHandler();
                methodHandler.setMethodAccess(access);
                methodHandler.setMethod(method);
                // todo 可以check一下参数类型，还有是否存在
                methodHandler.setParamType(method.getParameterTypes()[1]);
                if (interfaceMap.containsKey(methodAnnotation.code())) {
                    throw new RuntimeException("repeat processor with msgCode: " + methodAnnotation.code() + ", processor: " + clazz.getName() + ", method: " + method.getName());
                }
                interfaceMap.put(methodAnnotation.code(), methodHandler);
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
    public static MethodHandler getInterface(int msgCode) {
        return interfaceMap.get(msgCode);
    }
}
