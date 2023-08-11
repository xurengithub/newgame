package com.xuren.game.model.equipment;

import game.metadata.ItemData;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemsManager {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private ItemData itemData;

    private Logger logger = LoggerFactory.getLogger(ItemsManager.class);

    public ItemEntity addItemByDefId(long playerId,int itemId){

        List<ItemEntity> itemEntityList = itemDao.findItemsByItemId(itemId);
        JSONObject jsonObject = itemData.findJsonObjById(itemId);
        int stackMax = jsonObject.getInt("stackMax");
        ItemEntity itemEntity = null;
        boolean isAdd = false;
        for(int i = 0;i<itemEntityList.size();i++){
            itemEntity = itemEntityList.get(i);
            if(itemEntity.getNum() < stackMax){
                itemEntity.setNum(itemEntity.getNum()+1);
                itemDao.update(itemEntity);
                isAdd = true;
                break;
            }
        }
        if(!isAdd){
            itemEntity = new ItemEntity();
            itemEntity.setPlayerId(playerId);
            itemEntity.setNum(1);
            itemEntity.setItemId(itemId);
            itemEntity.setAttribute(getAttributeJsonStr(jsonObject));
            itemDao.save(itemEntity);
        }

        return itemEntity;
    }

    private String getAttributeJsonStr(JSONObject jsonObject){
        JSONObject attributeJson = new JSONObject();
        String type = jsonObject.getString("type");
        if(type.equals("Equipment")){
            int strength = jsonObject.getInt("strength");
            int itellect = jsonObject.getInt("intellect");
            int agility = jsonObject.getInt("agility");
            int stamina = jsonObject.getInt("stamina");
            attributeJson.put("strength",strength);
            attributeJson.put("intellect",itellect);
            attributeJson.put("agility",agility);
            attributeJson.put("stamina",stamina);
        }else if(type.equals("Weapon")){
            int damage = jsonObject.getInt("damage");
            attributeJson.put("damage",damage);
        }else if(type.equals("Material")){

        }else if(type.equals("Consumable")){
            int hp = jsonObject.getInt("hp");
            int mp = jsonObject.getInt("mp");
            attributeJson.put("hp",hp);
            attributeJson.put("mp",mp);
        }

        return attributeJson.toString();
    }

    public List<ItemEntity> findItemsByPlayerId(long playerId){
        return itemDao.findItemsByPlayerId(playerId);
    }

    public int save(ItemEntity itemEntity){
        return itemDao.save(itemEntity);
    }

    public int saveAll(List<ItemEntity> list){
        int num = 0;
        for (ItemEntity entity:list){
            num += save(entity);
        }
        return num;
    }


}
