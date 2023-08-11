package com.xuren.game.common.utils;

public class SRandom
{
    public static int count = 0;

    private long randSeed = 1;
    public SRandom(long seed)
    {
        randSeed = seed;
    }

    public int next()
    {
        randSeed = randSeed * 1103515245 + 12345;
        return (int)(randSeed / 65536);
    }


    public int next(int max)
    {
        return Math.abs(next() % max);
    }

    public int range(int min, int max)
    {
        count++;

        if (min > max)
            throw new IllegalArgumentException("minValue", new Throwable(String.format("%d cannot be greater than %d", min, max)));

        int num = max - min;

        return next(num) + min;
    }

}