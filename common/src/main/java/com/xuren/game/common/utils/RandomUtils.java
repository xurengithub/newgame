package com.xuren.game.common.utils;

import java.util.Random;

/**
 * @author xuren
 */
public class RandomUtils {
    private static final Random random = new Random();

    public static int nextInt(int startInclusive, int endExclusive) {
        return startInclusive + random.nextInt(endExclusive - startInclusive);
    }
}
