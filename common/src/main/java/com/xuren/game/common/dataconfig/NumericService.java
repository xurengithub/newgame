package com.xuren.game.common.dataconfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.xuren.game.common.exception.BaseException;
import com.xuren.game.common.exception.enums.ServerErrorEnum;
import org.reflections.Reflections;
import org.testng.collections.Maps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xuren
 */
public class NumericService {
    private static String innerNumericPath;
    private static String[] innerPackageNames;
    private static final Map<Class<? extends NumericItem>, ImmutableMap<String, NumericItem>> numericMap = Maps.newHashMap();

    public static void init(String numericPath, String... packageNames) {
        innerNumericPath = numericPath;
        innerPackageNames = packageNames;
        Reflections reflections = new Reflections((Object) packageNames);
        Path path = Path.of(numericPath);
        var classSet = reflections.getSubTypesOf(NumericItem.class);
        for (Class<? extends NumericItem> clazz : classSet) {
            List<String> lines = readLines(path.resolve(numericPath + clazz.getSimpleName() + ".json"));
            ImmutableMap<String, NumericItem> itemMap = resolve(clazz, lines);
            numericMap.put(clazz, itemMap);
        }
    }

    public static void reload() {
        init(innerNumericPath, innerPackageNames);
    }

    public static <T> T getByTag(Class<T> clazz, String tag) {
        var itemMap = getStringNumericItemImmutableMap(clazz);
        var item = itemMap.get(tag);
        if (item == null) {
            throw new BaseException(ServerErrorEnum.NUMERIC_NOT_FOUND, "className:{} tag:{} not found", clazz.getSimpleName(), tag);
        }
        return clazz.cast(item);
    }

    /**
     * 不保证数值顺序
     */
    public static <T> Stream<T> stream(Class<T> clazz) {
        ImmutableMap<String, NumericItem> itemMap = getStringNumericItemImmutableMap(clazz);
        return itemMap.values().stream().map(clazz::cast);
    }

    /**
     * 不保证数值顺序
     */
    public static <T> List<T> list(Class<T> clazz) {
        return stream(clazz).collect(Collectors.toList());
    }

    private static <T> ImmutableMap<String, NumericItem> getStringNumericItemImmutableMap(Class<T> clazz) {
        var itemMap = numericMap.get(clazz);
        if (itemMap == null) {
            throw new BaseException(ServerErrorEnum.NUMERIC_NOT_FOUND, "className:{} not found", clazz.getSimpleName());
        }
        return itemMap;
    }

    private static ImmutableMap<String, NumericItem> resolve(Class<? extends NumericItem> clazz, List<String> lines) {
        Map<String, NumericItem> itemMaps = Maps.newHashMap();
        for (String line : lines) {
            var jsonObj = JSONObject.parseObject(line);
            var tag = jsonObj.getString("tag");
            var item = JSON.parseObject(line, clazz);
            itemMaps.put(tag, item);
        }
        ImmutableMap.Builder<String, NumericItem> builder = ImmutableMap.builder();
        builder.putAll(itemMaps);
        return builder.build();
    }

    private static List<String> readLines(Path filePath) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
