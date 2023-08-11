package com.xuren.game.common.utils;

public class MyRand {
    static long rand_seed;
    public MyRand(){
        rand_seed = System.currentTimeMillis();
    }
    public MyRand(long seed){
        rand_seed = seed;
    }
    public long next(){
        rand_seed = (rand_seed * 16807) % ((1 << 31) - 1);
        return rand_seed;
    }


}
