package com.xuren.game.common.utils;

import java.util.Random;

public class TestUtils {
    public static void main(String[] args) {
//            System.out.println(DBUtils.preDeleteSql("user", new String[]{"id", "aaa"}));
//            System.out.println(DBUtils.preInsertSql("user", new String[]{"userName","password"}));
//            System.out.println(DBUtils.preSelectSql("user", new String[]{"userName","password"}));
//            System.out.println(DBUtils.preUpdateSql("user", new String[]{"userName","password"},new String[]{"userName","password"}));
//
//        MyRand rand = new MyRand(123);
//        System.out.println(rand.next());
//        System.out.println(rand.next());
//        System.out.println(rand.next());

        SRandom s = new SRandom(100);
//        System.out.println(s.next());
//        System.out.println(s.next());
//        System.out.println(s.next());
        int[] arr = new int[21];
        for (int i=0;i<1000;i++){
            arr[s.range(1,21)]++;
        }
        for(int a:arr){
            System.out.println(a);
        }
//        System.out.println(0.1%1);
        Random random = new Random();
        random.nextInt();
        random.doubles();
        random.getClass().getClassLoader();
//        Class<SRandom> s;
//        s.getDeclaredFields();
    }
}
