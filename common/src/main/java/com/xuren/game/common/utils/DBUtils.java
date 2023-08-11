package com.xuren.game.common.utils;


public class DBUtils {

    public static String preSelectSql(String tableName,String[] wheres) {

        String sql = "select * from `"+tableName+"`";
        if(wheres.length > 0){
            sql += " where `";
            for (int i = 0; i<wheres.length; i++){
                if(i > 0){
                    sql += " and `";
                }
                sql += wheres[i] + "`= ?";
            }
        }
        return sql;
    }

    public static String preUpdateSql(String tableName, String[] sets,String[] wheres) {

        String sql = "update `"+tableName+"` set `";
        if(sets.length > 0){
            for (int i = 0; i<sets.length; i++){
                if(i > 0){
                    sql += ", `";
                }
                sql += sets[i] + "`= ?";
            }
        }
        if(wheres.length > 0){
            sql += " where `";
            for (int i = 0; i<wheres.length; i++){
                if(i > 0){
                    sql += " and ";
                }
                sql += wheres[i] + "`= ?";
            }
        }
        return sql;
    }

    public static String preDeleteSql(String tableName, String[] wheres){
        String sql = "delete from `"+tableName+"`";
        if(wheres.length > 0){
            sql += " where `";
            for (int i = 0; i<wheres.length; i++){
                if(i > 0){
                    sql += " and `";
                }
                sql += wheres[i] + "`= ?";
            }
        }
        return sql;
    }

    public static String preInsertSql(String tableName, String[] intoKeys){

        String sql = "insert into `"+tableName+"`";
        if(intoKeys.length > 0){
            sql += "(`";
            for (int i = 0; i<intoKeys.length; i++){
                sql += intoKeys[i];
                if(i != intoKeys.length - 1){
                    sql +="`,`";
                }

            }
            sql += "`) values(";
            for (int i = 0; i<intoKeys.length; i++){
                sql += "?";
                if(i != intoKeys.length - 1){
                    sql += ",";
                }
            }
            sql += ")";
        }
        return sql;
    }

    public static String preInsertSql2(String tableName, String[] intoKeys){

        String sql = "insert into `"+tableName+"`";
        if(intoKeys.length > 0){
            sql += "(`";
            for (int i = 0; i<intoKeys.length; i++){
                sql += intoKeys[i];
                if(i != intoKeys.length - 1){
                    sql +="`,`";
                }

            }
            sql += "`) values(";
            for (int i = 0; i<intoKeys.length; i++){
                sql += "?";
                if(i != intoKeys.length - 1){
                    sql += ",";
                }
            }
            sql += ")";

            sql += " ON DUPLICATE KEY UPDATE ";
            for(int i = 0; i<intoKeys.length; i++){
                if(i != 0){
                    sql += ",";
                }
                sql += "`"+intoKeys[i]+"`=VALUES(`"+intoKeys[i]+"`)";
            }
        }
        return sql;
    }
}
