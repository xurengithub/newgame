package com.xuren.game.common.proto;

import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xuren
 */
public class ProtoHandlerManager {
    public static void init(String... packages) {
        Set<Class<?>> classSet = new HashSet<>();
        for (String packageName : packages) {
            Reflections reflections = new Reflections(packageName);
//            reflections.getTypesAnnotatedWith(null);
        }
    }
}
