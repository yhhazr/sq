/**
 * Copyright  2013-3-4 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午11:22:42
 * 版本号： v1.0
*/

package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.GameDataTypeDao;
import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.pagination.PageInfo;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-4 上午11:22:42
 * 版本号： v1.0
*/
public class GameDataTypeDaoImpl implements GameDataTypeDao {

    private static final String ADD_GAMEDATETYPE = "INSERT INTO ddt_game_date_type(type_name, type_short, parent_id, order_id) VALUES(?, ?, ?, ?)";

    private static final String UPDATE_GAMEDATETYPE = "UPDATE ddt_game_date_type SET type_name = ?, type_short = ?, parent_id = ?, order_id = ? WHERE type_id = ?";

    private static final String DELETE_GAMEDATETYPE = "DELETE FROM ddt_game_date_type WHERE type_id = ?";

    private static final String QUERY_COUNT_BY_TYPENAME = "SELECT COUNT(1) AS count FROM ddt_game_date_type WHERE parent_id = ? AND type_id <> ? AND type_name = ?";

    private static final String QUERY_COUNT_BY_SHORTNAME = "SELECT COUNT(1) AS count FROM ddt_game_date_type WHERE parent_id = ? AND type_id <> ? AND type_short = ?";

    private static final String QUERY_GAMEDATETYPE_BY_TYPEID = "SELECT * FROM ddt_game_date_type WHERE type_id = ?";

    private static final String QYERY_COUNT = "SELECT COUNT(1) AS count FROM ddt_game_date_type WHERE parent_id <> -1";

    private static final String QUERY_GAMEDATETYPE = "select distinct dt.type_name as parent_name,sg.type_id, sg.type_short, sg.type_name,sg.parent_id, sg.order_id  from ddt_game_date_type sg, ddt_game_date_type dt where dt.type_id = sg.parent_id";

    private static final String QUERY_ROOT_TYPE = "SELECT * FROM ddt_game_date_type WHERE parent_id = -1";
    
    private static final String DELETE_GAMEDATA_BY_PARENTID = "DELETE FROM ddt_game_date WHERE type_id = ?";
    
    private static GameDataTypeDaoImpl _instance = new GameDataTypeDaoImpl();

    public static GameDataTypeDao getInstance() {
        return _instance;
    }

    @Override
    public boolean addGameDateType(final GameDateType dataType) throws SQLException {
        boolean result = DB.insertUpdate(ADD_GAMEDATETYPE, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, dataType.getTypeName());
                stmt.setString(2, dataType.getShortName());
                stmt.setInt(3, dataType.getParentId());
                stmt.setInt(4, dataType.getOrderId());
                stmt.executeUpdate();
            }
        });
        return result;
    }

    @Override
    public boolean editGameDateType(final GameDateType dataType) throws SQLException {
        boolean result = DB.insertUpdate(UPDATE_GAMEDATETYPE, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, dataType.getTypeName());
                stmt.setString(2, dataType.getShortName());
                stmt.setInt(3, dataType.getParentId());
                stmt.setInt(4, dataType.getOrderId());
                stmt.setInt(5, dataType.getTypeId());
                stmt.executeUpdate();
            }
        });
        return result;
    }

    @Override
    public boolean deleteGameDateType(final int typeId) throws SQLException {
        boolean result = DB.insertUpdate(DELETE_GAMEDATETYPE, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, typeId);
                stmt.executeUpdate();
            }
        });
        
        result = DB.insertUpdate(DELETE_GAMEDATA_BY_PARENTID, new IUStH() {
            
            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, typeId);
                stmt.executeUpdate();
            }
        });
        return result;
    }

    @Override
    public boolean hasGameDateType(final int parentId, final int typeId, final String typeName) throws SQLException {
        final int[] count = { 0 };
        DB.select(QUERY_COUNT_BY_TYPENAME, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, parentId);
                stmt.setInt(2, typeId);
                stmt.setString(3, typeName);
            }
        });
        if (count[0] != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasTypeShortName(final int parentId, final int typeId, final String shortName) throws SQLException {
        final int[] count = { 0 };
        DB.select(QUERY_COUNT_BY_SHORTNAME, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, parentId);
                stmt.setInt(2, typeId);
                stmt.setString(3, shortName);
            }
        });
        if (count[0] != 0) {
            return true;
        }
        return false;
    }

    @Override
    public GameDateType queryGameDateTypeById(final int typeId) throws SQLException {
        final GameDateType dateType = new GameDateType();
        DB.select(QUERY_GAMEDATETYPE_BY_TYPEID, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    dateType.setTypeId(rs.getInt("type_id"));
                    dateType.setTypeName(rs.getString("type_name"));
                    dateType.setShortName(rs.getString("type_short"));
                    dateType.setParentId(rs.getInt("parent_id"));
                    dateType.setOrderId(rs.getInt("order_id"));
                }

            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, typeId);
            }
        });
        return dateType;
    }

    @Override
    public int getCount() throws SQLException {
        final int[] count = { 0 };
        DB.select(QYERY_COUNT, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });
        return count[0];
    }

    @Override
    public List<GameDateType> queryGameDateTypes(PageInfo pageInfo) throws SQLException {
        final List<GameDateType> dateTypes = new ArrayList<GameDateType>();
        StringBuilder sql = new StringBuilder(QUERY_GAMEDATETYPE);
        sql.append(" order by order_id ");
        sql.append(" limit ").append(pageInfo.getStartRow()).append(" , ").append(pageInfo.getPageSize());
        DB.select(sql.toString(), new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                GameDateType dateType = null;
                while (rs.next()) {
                    dateType = new GameDateType();
                    dateType.setTypeId(rs.getInt("type_id"));
                    dateType.setTypeName(rs.getString("type_name"));
                    dateType.setShortName(rs.getString("type_short"));
                    dateType.setParentId(rs.getInt("parent_id"));
                    dateType.setOrderId(rs.getInt("order_id"));
                    dateType.setParentName(rs.getString("parent_name"));
                    dateTypes.add(dateType);
                }

            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {

            }
        });
        return dateTypes;
    }

    @Override
    public List<GameDateType> queryGameDataParentType() throws SQLException {
        final List<GameDateType> dateTypes = new ArrayList<GameDateType>();
        DB.select(QUERY_ROOT_TYPE, new ParamReadStH() {
            
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                GameDateType dateType = null;
                while (rs.next()) {
                    dateType = new GameDateType();
                    dateType.setTypeId(rs.getInt("type_id"));
                    dateType.setTypeName(rs.getString("type_name"));
                    dateType.setShortName(rs.getString("type_short"));
                    dateType.setParentId(rs.getInt("parent_id"));
                    dateType.setOrderId(rs.getInt("order_id"));
                    dateTypes.add(dateType);
                }
                
            }
            
            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                
            }
        });
        return dateTypes;
    }

}
