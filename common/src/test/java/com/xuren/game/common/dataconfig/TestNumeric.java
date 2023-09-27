package com.xuren.game.common.dataconfig;

import com.alibaba.fastjson.JSON;

/**
 * @author xuren
 */
public class TestNumeric {
    public static void main(String[] args) {
        NumericService.init("/Users/topjoy/pythontest/", NumericItem.class.getPackageName());
        System.out.println(JSON.toJSONString(NumericService.getByTag(MyExample.class, "WealthyTree_12")));;
    }
}
