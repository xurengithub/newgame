package com.xuren.game.model.player;

import core.DB.Template.AbstractDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PlayerDao extends AbstractDao<PlayerEntity> {
    private Logger logger = LoggerFactory.getLogger(PlayerDao.class);
    public final static String tableName = "players";

    public PlayerEntity findByPlayerId(long playerId){
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("playerId", playerId);
        List<PlayerEntity> list = super.find(tableName, map);
        return list.size()>0?list.get(0):null;
    }

    public PlayerEntity findByPlayerName(String playerName){
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("playerName", playerName);
        List<PlayerEntity> list = super.find(tableName, map);
        return list.size()>0?list.get(0):null;
    }

    public int save(PlayerEntity playerEntity){
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("playerId", playerEntity.getPlayerId());
        map.put("userId", playerEntity.getUserId());
        map.put("coin", playerEntity.getCoin());
        map.put("gem", playerEntity.getGem());
        map.put("exp", playerEntity.getExp());
        map.put("level", playerEntity.getLevel());
        map.put("playerName", playerEntity.getPlayerName());
        map.put("scene", playerEntity.getScene());
        map.put("areaId", playerEntity.getAreaId());
        map.put("x", playerEntity.getX());
        map.put("y", playerEntity.getY());
        map.put("z", playerEntity.getZ());
        map.put("ex", playerEntity.getEx());
        map.put("ey", playerEntity.getEy());
        map.put("ez", playerEntity.getEz());
        map.put("hp", playerEntity.getHp());
        map.put("mp", playerEntity.getMp());
        map.put("maxHp", playerEntity.getMaxHp());
        map.put("maxMp", playerEntity.getMaxMp());

        return super.insert(tableName, map);
    }

    public int AddPlayer(long userId, String playerName){
        this.logger.debug("userId:{} playerName:{}",userId,playerName);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("userId", userId);
        map.put("playerName",playerName);
        return super.insert(tableName, map);
    }



    public List<PlayerEntity> findPlayersInfoByUserId(long userId){
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("userId", userId);
        return super.find(tableName, map);
    }

    public int update(PlayerEntity playerEntity){
        Map<Object, Object> map = new HashMap<Object, Object>();

//        map.put("userId", playerEntity.getPlayerId());
        map.put("coin", playerEntity.getCoin());
        map.put("gem", playerEntity.getGem());
        map.put("exp", playerEntity.getExp());
        map.put("level", playerEntity.getLevel());
        map.put("playerName", playerEntity.getPlayerName());
        map.put("scene", playerEntity.getScene());
        map.put("areaId", playerEntity.getAreaId());
        map.put("x", playerEntity.getX());
        map.put("y", playerEntity.getY());
        map.put("z", playerEntity.getZ());
        map.put("ex", playerEntity.getEx());
        map.put("ey", playerEntity.getEy());
        map.put("ez", playerEntity.getEz());
        map.put("hp", playerEntity.getHp());
        map.put("mp", playerEntity.getMp());
        map.put("maxHp", playerEntity.getMaxHp());
        map.put("maxMp", playerEntity.getMaxMp());

        Map<Object,Object> whereMap = new HashMap<Object, Object>();

        whereMap.put("playerId",playerEntity.getPlayerId());
        return super.update(tableName,map,whereMap);
    }

    @Override
    protected PlayerEntity rowMapper(ResultSet rs) throws SQLException {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerName(rs.getString("playerName"));
        playerEntity.setPlayerId(rs.getLong("playerId"));
        playerEntity.setUserId(rs.getLong("userId"));
        playerEntity.setCoin(rs.getLong("coin"));
        playerEntity.setGem(rs.getLong("gem"));
        playerEntity.setExp(rs.getLong("exp"));
        playerEntity.setLevel(rs.getInt("level"));

        playerEntity.setScene(rs.getString("scene"));
        playerEntity.setAreaId(rs.getInt("areaId"));
        playerEntity.setX(rs.getFloat("x"));
        playerEntity.setY(rs.getFloat("y"));
        playerEntity.setZ(rs.getFloat("z"));
        playerEntity.setEx(rs.getFloat("ex"));
        playerEntity.setEy(rs.getFloat("ey"));
        playerEntity.setEz(rs.getFloat("ez"));
        playerEntity.setHp(rs.getLong("hp"));
        playerEntity.setMp(rs.getLong("mp"));
        playerEntity.setMaxHp(rs.getLong("maxHp"));
        playerEntity.setMaxMp(rs.getLong("maxMp"));
        return playerEntity;
    }
}
