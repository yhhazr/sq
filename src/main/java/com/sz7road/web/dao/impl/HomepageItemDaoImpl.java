package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.HomepageItemDao;
import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.pagination.PageInfo;

public class HomepageItemDaoImpl implements HomepageItemDao {

	private static final String INSERT_ITEM_SQL = "INSERT INTO ddt_homepage_manage (name,url,type_id,content,position,create_date) values(?,?,?,?,?,?)";

	private static final String SELECT_ALL_ITEM_SQL = "SELECT * FROM ddt_homepage_manage WHERE type_id = ? ";

	private static final String DELETE_ITEM_SQL = "DELETE FROM ddt_homepage_manage WHERE id = ?";
	
	private static final String SELECT_ITEM_SQL = "SELECT * FROM ddt_homepage_manage WHERE id = ?";
	
	private static final String UPDATE_ITEM_SQL = "UPDATE ddt_homepage_manage SET name = ?,url = ?,content = ?,position = ? WHERE id = ?";
	
	private static final String SELECT_ITEM_BASE_SQL = "SELECT * FROM ddt_homepage_manage WHERE 1 ";
	
//	private static final String SELECT_LEFT_RIGHT_SQL = "select * from ddt_homepage_manage where type_id=? and position=? order by create_date DESC";
	
	private static final String SELECT_HOMEPAGE_BY_TYPE = "select * from ddt_homepage_manage where type_id=? and position=? order by create_date DESC limit 0,?";
	
	private static HomepageItemDaoImpl _instance = new HomepageItemDaoImpl();

	private HomepageItemDaoImpl() {}
	  
	public static HomepageItemDaoImpl getInstance() {
		return _instance;
	}
	@Override
	public boolean insertItem(final HomepageItem item) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(INSERT_ITEM_SQL,new IUStH() {
			
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
					ps.setString(1, item.getName());
					ps.setString(2, item.getUrl());
					ps.setInt(3, item.getTypeId());
					ps.setString(4, item.getContent());
					ps.setString(5, item.getPosition());
					ps.setTimestamp(6, DateUtil.getCurrentTimestamp());
					ps.executeUpdate();
			}
		});
		return result;
	}


	@Override
	public List<HomepageItem> getItemList(PageInfo pageInfo, final int typeId) throws Exception {
		final List<HomepageItem> itemList = new ArrayList<HomepageItem>();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_ALL_ITEM_SQL);
		sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
		
		return this.findItemByTypeIdForPage(sql.toString(), typeId);
	}

	@Override
	public List<HomepageItem> getItemListOrderByParam(PageInfo pageInfo, final int typeId, String orderParam, boolean isDesc) throws Exception {
		final List<HomepageItem> itemList = new ArrayList<HomepageItem>();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_ALL_ITEM_SQL);
		sql.append("order by content ");
		if(isDesc){
			sql.append(" desc ");
		}
		sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
		
		return this.findItemByTypeIdForPage(sql.toString(), typeId);
	}
	
	//分页查询基础方法
	private List<HomepageItem> findItemByTypeIdForPage(String sql, final int typeId) throws Exception{
		final List<HomepageItem> itemList = new ArrayList<HomepageItem>();
		DB.select(sql, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					HomepageItem info = new HomepageItem();
					info.setCreateDate(rs.getTimestamp("create_date"));
					info.setId(rs.getInt("id"));
					info.setName(rs.getString("name"));
					info.setPosition(rs.getString("position"));
					info.setContent(rs.getString("content"));
					info.setTypeId(rs.getInt("type_id"));
					info.setUrl(rs.getString("url"));
					itemList.add(info);
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, typeId);
			}
		});
		
		return itemList;
	}
	
	@Override
	public List<HomepageItem> findItemListByTypeId(final int typeId) throws Exception {
		final List<HomepageItem> itemList = new ArrayList<HomepageItem>();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_ALL_ITEM_SQL);
		sql.append(" order by content asc ");
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					HomepageItem info = new HomepageItem();
					info.setCreateDate(rs.getTimestamp("create_date"));
					info.setId(rs.getInt("id"));
					info.setName(rs.getString("name"));
					info.setPosition(rs.getString("position"));
					info.setContent(rs.getString("content"));
					info.setTypeId(rs.getInt("type_id"));
					info.setUrl(rs.getString("url"));
					itemList.add(info);
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, typeId);
			}
		});
		return itemList;
	}

	@Override
	public int getItemCount(final int typeId) throws Exception {
		final int[] count = { 0 };
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) count from ddt_homepage_manage WHERE type_id = ?");
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					count[0] = rs.getInt("count");
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, typeId);
			}
		});
		return count[0];
	}

	@Override
	public boolean deleteItem(final int id) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(DELETE_ITEM_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
				ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public HomepageItem getItemById(final int id) throws Exception {
		final HomepageItem info = new HomepageItem();
		DB.select(SELECT_ITEM_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setId(rs.getInt("id"));
					info.setContent(rs.getString("content"));
					info.setName(rs.getString("name"));
					info.setPosition(rs.getString("position"));
					info.setTypeId(rs.getInt("type_id"));
					info.setUrl(rs.getString("url"));					
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, id);
			}
		});

		return info;
	}
	
	@Override
	public HomepageItem getItemByPosition(final String position) throws Exception {
		final HomepageItem info = new HomepageItem();
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_ITEM_BASE_SQL);
		sql.append(" where position=? ");
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setId(rs.getInt("id"));
					info.setContent(rs.getString("content"));
					info.setName(rs.getString("name"));
					info.setPosition(rs.getString("position"));
					info.setTypeId(rs.getInt("type_id"));
					info.setUrl(rs.getString("url"));					
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setString(1, position);
			}
		});
		
		return info;
	}

	@Override
	public boolean updateItem(final HomepageItem item) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(UPDATE_ITEM_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setString(1, item.getName());
				ps.setString(2,item.getUrl());
				ps.setString(3, item.getContent());
				ps.setString(4, item.getPosition());
				ps.setInt(5, item.getId());
				ps.executeUpdate();
			}
		});
		return result;
	}
	
	
	//根据type和position降序查询
	@Override
	public List<HomepageItem> selectItemByTypeIdAndPosition(final int typeId, final String position, final int size) throws Exception {
		StringBuffer sql = new StringBuffer(SELECT_ITEM_BASE_SQL);
		sql.append(" AND type_id=? and position=? ")
			.append(" order by create_date DESC ")
			.append(" limit 0,?");
		
		final List<HomepageItem> list = new ArrayList<HomepageItem>();
		DB.select(sql.toString(), new ParamReadStH() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					HomepageItem item = new HomepageItem();
					item.setId(rs.getInt("id"));
					item.setName(rs.getString("name"));
					item.setUrl(rs.getString("url"));
					item.setTypeId(typeId);
					item.setPosition(position);
					item.setContent(rs.getString("content"));
					list.add(item);
				}
			}

			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, typeId);
				ps.setString(2, position);
				ps.setInt(3, size);
			}

		});
		return list;
	}

	//根据position查询
	@Override
	public List<HomepageItem> selectItemByPosition(final String position) throws Exception {
		StringBuffer sql = new StringBuffer(SELECT_ITEM_BASE_SQL);
		sql.append(" AND position=? ");
		
		final List<HomepageItem> list = new ArrayList<HomepageItem>();
		DB.select(sql.toString(), new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					HomepageItem item = new HomepageItem();
					item.setId(rs.getInt("id"));
					item.setName(rs.getString("name"));
					item.setUrl(rs.getString("url"));
					item.setContent(rs.getString("content"));
					item.setTypeId(Integer.parseInt(rs.getString("type_id")));
					item.setPosition(position);
					list.add(item);
				}
			}
			
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setString(1, position);
			}
			
		});
		return list;
	}
	
}
