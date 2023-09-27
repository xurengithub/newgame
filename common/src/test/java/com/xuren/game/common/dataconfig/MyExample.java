package com.xuren.game.common.dataconfig;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.xuren.game.common.dataconfig.NumericItem;
import java.beans.ConstructorProperties;
import com.alibaba.fastjson.JSON;

public class MyExample extends NumericItem {
    /**
     * 索引
     *
     */
    public final String tag;
    /**
     * 包含档位
     *
     */
    public final String tree;
    /**
     * 随机显示人数
     *
     */
    public final int personNum;
    /**
     * 随机显示人数
     *
     */
    public final long long1;
    /**
     * 随机显示人数
     *
     */
    public final List<Long> longarr;
    /**
     * 随机显示人数
     *
     */
    public final float floatP;
    /**
     * 随机显示人数
     *
     */
    public final List<Float> floatArr;
    /**
     * 随机显示人数
     *
     */
    public final double doubleP;
    /**
     * 随机显示人数
     *
     */
    public final List<Double> doubleArr;
    /**
     * 随机显示人数
     *
     */
    public final List<Integer> intP;
    /**
     * 随机显示人数
     *
     */
    public final JSONObject jsonObj;
    /**
     * 随机显示人数
     *
     */
    public final JSONArray aaaN;
    /**
     * 随机显示人数
     *
     */
    @JSONField(serialize = false)
    public final List<MyExample.AaaN> aaaNList;

    @ConstructorProperties({"tag","tree","personNum","long1","longarr","floatP","floatArr","doubleP","doubleArr","intP","jsonObj","aaaN"})
    public MyExample(String tag, String tree, int personNum, long long1, List<Long> longarr, float floatP, List<Float> floatArr, double doubleP, List<Double> doubleArr, List<Integer> intP, JSONObject jsonObj, JSONArray aaaN) {
        this.tag=tag;
        this.tree=tree;
        this.personNum=personNum;
        this.long1=long1;
        this.longarr=longarr;
        this.floatP=floatP;
        this.floatArr=floatArr;
        this.doubleP=doubleP;
        this.doubleArr=doubleArr;
        this.intP=intP;
        this.jsonObj=jsonObj;
        this.aaaN=aaaN;
        this.aaaNList = JSON.parseArray(Objects.requireNonNullElse(aaaN, new JSONArray()).toString(), MyExample.AaaN.class);
    }

    @Override
    public String toString() {
        return (MyExample.class.getSimpleName()+"."+this.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.tag);
    }

    @Override
    public boolean equals(Object o) {
        return ((o instanceof MyExample)&&this.tag.equals(((MyExample) o).tag));
    }

    public static class AaaN {
        /**
         * 随机显示人数
         *
         */
        public final int a;
        /**
         * 随机显示人数
         *
         */
        public final float b;

        @ConstructorProperties({"a","b"})
        public AaaN(int a, float b) {
            this.a=a;
            this.b=b;
        }
    }
}