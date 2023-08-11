package com.xuren.game.model.equipment;

import core.DB.Template.AbstractDao;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ItemDao extends AbstractDao<ItemEntity> {
    public static  String tableName = "items";

    public List<ItemEntity> findItemsByPlayerId(long playerId){
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("playerId", playerId);
        return super.find(tableName, map);
    }

    public int deleteItems(long playerId, long id){
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("playerId", playerId);
        map.put("id", id);
        return super.delete(tableName, map);
    }

    public List<ItemEntity> findItemsByItemId(int itemId){
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("itemId", itemId);
        return super.find(tableName, map);
    }


    public int update(ItemEntity itemEntity){
        Map<Object,Object> setMap = new HashMap<Object, Object>();
        setMap.put("playerId", itemEntity.getPlayerId());
        setMap.put("itemId", itemEntity.getItemId());
        setMap.put("num", itemEntity.getNum());
        setMap.put("attribute", itemEntity.getAttribute());

        Map<Object,Object> whereMap = new HashMap<Object, Object>();

        whereMap.put("id",itemEntity.getId());
        return super.update(tableName,setMap,whereMap);
    }


    public int save(ItemEntity itemEntity){
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("id", itemEntity.getId());
        map.put("playerId", itemEntity.getPlayerId());
        map.put("itemId", itemEntity.getItemId());
        map.put("num", itemEntity.getNum());
        map.put("attribute", itemEntity.getAttribute());
        return super.insert(tableName, map);
    }
    @Override
    protected ItemEntity rowMapper(ResultSet rs) throws SQLException {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(rs.getLong("id"));
        itemEntity.setPlayerId(rs.getLong("playerId"));
        itemEntity.setItemId(rs.getInt("itemId"));
        itemEntity.setNum(rs.getInt("num"));
        itemEntity.setAttribute(rs.getString("attribute"));
        return itemEntity;
    }
}
