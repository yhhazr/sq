package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.common.listener.DirtyFilter;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.MsgDao;
import com.sz7road.web.model.messagemanage.MsgInfo;
import com.sz7road.web.model.messagemanage.MsgInfoEx;
import com.sz7road.web.model.messagemanage.MsgOpType;
import com.sz7road.web.model.messagemanage.MsgState;
import com.sz7road.web.model.messagemanage.RelationMapping;
import com.sz7road.web.model.onlineUser.OnlineUser;
import com.sz7road.web.model.pagination.PageInfo;

public class MsgDaoImpl implements MsgDao{
	
	private static final String SELECT_MSG_SQL = "  select msg.*,(select opt.type_name from ddt_optype opt where opt.type_id=msg.type_id) type_name,(select sta.state_name from ddt_news_state sta where sta.state_id=msg.state_id) state_name from ddt_message msg";
	private static final String SELECT_MSG_COUNT_SQL = "select count(msg_id) count from ddt_message";
	private static final String SELECT_OPT_TYPE_SQL = "select * from ddt_optype";
	private static final String SELECT_STATE_SQL = "select * from ddt_news_state";
	private static final String UPDATE_MSG_SQL = "update ddt_message set state_id=?,msg_content=?,ip_address=?,support=? where msg_id=?";
	private static final String DELETE_MSG_SQL = "delete from ddt_message where msg_id=?";
	
	private static final String SELECT_ONLINEUSER_BY_ID = "select * from ddt_online_user where id=?";
	
	private static final String CREATE_NEW_MSG = "insert into ddt_message(type_id,title_id,state_id,user_id,msg_content,ip_address,create_date,support) values(?,?,?,?,?,?,?,?)";
	private static final String SELECT_COUNT_BY_TITLEID = "select count(id) count from ddt_message";
	private static final String SELECT_MSGLIST_BY_TITLE = "select * from ddt_message where type_id =? and title_id =?";
	
	private static MsgDaoImpl _instance = new MsgDaoImpl();
	
	private MsgDaoImpl(){
		
	}
	
	public static MsgDaoImpl getInstance(){
		return _instance;
	}
	
	@Override
	public int getMsgInfoCount(int typeId, int stateId) throws Exception {
		final int[] count = {0};
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_MSG_COUNT_SQL);
		if(typeId != 0 && stateId !=0){
			sql.append(" where type_id=").append(typeId).append(" and state_id=").append(stateId);
		}else if(typeId != 0 && stateId ==0){
			sql.append(" where type_id=").append(typeId);
		}else if(stateId != 0 && typeId ==0){
			sql.append(" where state_id=").append(stateId);
		}
		DB.select(sql.toString(), new ParamReadStH(){
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					count[0] = rs.getInt("count");
				}
			}
			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {}
		});
		return count[0];
	}
	
	@Override
	public List<MsgOpType> getMsgOpTypeList() throws Exception {
		final List<MsgOpType> list = new ArrayList<MsgOpType>();
		DB.select(SELECT_OPT_TYPE_SQL, new ParamReadStH(){

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					MsgOpType msg = new MsgOpType();
					msg.setTypeId(rs.getInt("type_id"));
					msg.setTypeName(rs.getString("type_name"));
					list.add(msg);
				}
			}

			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {}
		});
		return list;
	}

	@Override
	public List<MsgState> getMsgStateList() throws Exception {
		final List<MsgState> list = new ArrayList<MsgState>();
		DB.select(SELECT_STATE_SQL, new ParamReadStH(){

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					MsgState msg = new MsgState();
					msg.setStateId(rs.getInt("state_id"));
					msg.setStateName(rs.getString("state_name"));
					list.add(msg);
				}
			}
			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {}
			});
		return list;
	}
	//查询所有留言信息
	@Override
	public List<MsgInfoEx> getMsgInfoList(PageInfo pageInfo,final int typeId,final int stateId) throws Exception {
		final List<MsgInfoEx> list = new ArrayList<MsgInfoEx>();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_MSG_SQL);
		if(typeId != 0 && stateId !=0){
			sql.append(" where msg.type_id=").append(typeId).append(" and msg.state_id=").append(stateId);
		}else if(typeId != 0 && stateId ==0){
			sql.append(" where msg.type_id=").append(typeId);
		}else if(stateId != 0 && typeId ==0){
			sql.append(" where msg.state_id=").append(stateId);
		}
		sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
		DB.select(sql.toString(), new ParamReadStH(){
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					MsgInfoEx info = new MsgInfoEx();
					info.setMsgId(rs.getInt("msg_id"));
					info.setTypeId(rs.getInt("type_id"));//类型id
					info.setTitleId(rs.getInt("title_id"));//
					info.setStateId(rs.getInt("state_id"));//
					//info.setMsgTitle(rs.getString("msg_title"));
					info.setMsgContent(rs.getString("msg_content"));
					info.setIpAddress(rs.getString("ip_address"));
					info.setCreateDate(rs.getDate("create_date"));
					info.setSupport(String.valueOf(rs.getInt("support")));
					info.setTypeName(rs.getString("type_name"));
					info.setStateName(rs.getString("state_name"));
					try {
						info.setTitleName(MsgDaoImpl.getTitleName(rs.getInt("type_id"),rs.getInt("title_id")));
					} catch (Exception e) {
						e.printStackTrace();
					}
					info.setUserId(rs.getInt("user_id"));
					try{
						info.setUserNickName(getOnlineUserById(rs.getInt("user_id")).getUserName());
					}catch(Exception e){
						e.printStackTrace();
					}
					list.add(info);
				}
			}
			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {}});
		return list;
	}
	
	//获得对应主题的标题内容
	public static String getTitleName(int typeId,final int titleId) throws Exception{
		final String[] titleName = {""};
		final RelationMapping relation = MsgDaoImpl.getRelationMapping(typeId);
		final StringBuilder relationSql = new StringBuilder();
		relationSql.append("select ").append(relation.getMapTitleColName()).append(" from ").append(relation.getMapTableName());
		relationSql.append(" where ").append(relation.getMapIdColName()).append(" = ").append(titleId);
		//String relationSql = "select ? from ? where ?=?";
		 DB.select(relationSql.toString(), new ParamReadStH(){
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while(rs.next()){
						titleName[0] = rs.getString(1);
					}
				}
				@Override
				public void setParams(PreparedStatement ps) throws SQLException {
					//ps.setString(1, relation.getMapTitleColName());
					//ps.setString(2, relation.getMapTableName());
					//ps.setString(3, relation.getMapIdColName());
					//ps.setString(4, String.valueOf(titleId));
				}
			});
		 //System.out.println(relationSql.toString());
		return titleName[0];
		
	}
	
	//提供数据表和表中相应列名的映射，用来查找留言信息中每条留言对应信息的标题
	//每条信息都有自己唯一的typeId和stateId
	public static RelationMapping getRelationMapping(final int typeId) throws Exception{
		final RelationMapping relation = new RelationMapping();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_OPT_TYPE_SQL);
		sql.append(" where type_id=?");
		DB.select(sql.toString(), new ParamReadStH(){
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					relation.setMapTableName(rs.getString("map_table_name"));
					relation.setMapTitleColName(rs.getString("map_title_col_name"));
					relation.setMapIdColName(rs.getString("map_id_col_name"));
				}
				
			}
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, typeId);
			}
		});
		return relation;
	}
	//获得当前留言用户的信息
	public static OnlineUser getOnlineUserById(final int id) throws Exception{
		final OnlineUser user = new OnlineUser();
//		DB.select(SELECT_ONLINEUSER_BY_ID, new ParamReadStH(){
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while(rs.next()){
//					user.setUserId(id);
//					user.setUserName(rs.getString("nick_name"));
//					user.setEmail(rs.getString("email"));
//				}
//				
//			}
//			@Override
//			public void setParams(PreparedStatement ps) throws SQLException {
//				ps.setInt(1, id);
//			}
//		});
		
		return user;
	}
	
	@Override
	public MsgInfoEx getMsgInfoById(final int msgId) throws Exception {
		final MsgInfoEx info = new MsgInfoEx();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_MSG_SQL);
		sql.append(" where msg_id = ?");
		DB.select(sql.toString(), new ParamReadStH(){

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					info.setMsgId(msgId);
					info.setTypeId(rs.getInt("type_id"));//类型id
					info.setTitleId(rs.getInt("title_id"));//
					info.setStateId(rs.getInt("state_id"));//
					//info.setMsgTitle(rs.getString("msg_title"));
					info.setMsgContent(rs.getString("msg_content"));
					info.setIpAddress(rs.getString("ip_address"));
					info.setCreateDate(rs.getDate("create_date"));
					info.setSupport(String.valueOf(rs.getInt("support")));
					info.setTypeName(rs.getString("type_name"));
					info.setStateName(rs.getString("state_name"));
					try {
						info.setTitleName(MsgDaoImpl.getTitleName(rs.getInt("type_id"),rs.getInt("title_id")));
					} catch (Exception e) {
						e.printStackTrace();
					}
					info.setUserId(rs.getInt("user_id"));
					try{
						info.setUserNickName(getOnlineUserById(rs.getInt("user_id")).getUserName());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, msgId);
			}
		});
		return info;
	}

	@Override
	public boolean updateMsgInfo(final MsgInfoEx info) throws Exception {
		boolean flag = false;
		flag = DB.insertUpdate(UPDATE_MSG_SQL, new IUStH(){
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, info.getStateId());
				//ps.setString(2, info.getMsgTitle());
				ps.setString(2, info.getMsgContent());
				ps.setString(3, info.getIpAddress());
				ps.setInt(4, Integer.parseInt(info.getSupport()));
				ps.setInt(5, info.getMsgId());
				ps.executeUpdate();
			}
			
		});
		return flag;
	}

	@Override
	public boolean deleteMsgInfo(final List<Integer> idList) throws Exception {
		boolean flag = false;
		final Iterator<Integer> it = idList.iterator();
		while(it.hasNext()){
			flag = DB.insertUpdate(DELETE_MSG_SQL, new IUStH(){
				@Override
				public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
					ps.setInt(1, it.next());
					ps.executeUpdate();
				}
			});
		}
		return flag;
	}

	@Override
	public boolean createNewMsg(final MsgInfo info) throws Exception {
		boolean flag = false;
		flag = DB.insertUpdate(CREATE_NEW_MSG, new IUStH(){
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, info.getTypeId());
				ps.setInt(2, info.getTitleId());
				ps.setInt(3, info.getStateId());//
				ps.setInt(4, info.getUserId());
				try{
					ps.setString(5, DirtyFilter.getFilter(info.getMsgContent()));
				}catch(Exception e){
					e.printStackTrace();
				}
				ps.setString(6, info.getIpAddress());
				ps.setTimestamp(7, DateUtil.getCurrentTimestamp());
				if(info.getSupport() == null){
					ps.setInt(8, 0);
				}else{
					ps.setInt(8, Integer.parseInt(info.getSupport()));
				}
				ps.executeUpdate();
			}
			
		});
		return flag;
	}

	@Override
	public int getCountByTitleId(final int titleId, final int typeId) throws Exception {
		final int[] count = {0};
		StringBuilder sb = new StringBuilder();
		sb.append(SELECT_COUNT_BY_TITLEID);
		sb.append(" where ").append("title_id=? ").append("type_id=?");
		DB.select(sb.toString(), new ParamReadStH(){
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					count[0] = rs.getInt("count");
				}
			}
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, titleId);
				ps.setInt(2, typeId);
			}
		});
		return count[0];
	}

	@Override
	public List<MsgInfoEx> getMsgInfoByTitleId(final int titleId, final int typeId) throws Exception {
		final List<MsgInfoEx> list = new ArrayList<MsgInfoEx>();
		DB.select(SELECT_MSGLIST_BY_TITLE, new ParamReadStH(){
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					MsgInfoEx info = new MsgInfoEx();
					info.setMsgId(rs.getInt("msg_id"));
					info.setCreateDate(rs.getDate("create_date"));
					info.setMsgContent(rs.getString("msg_content"));
					info.setSupport(String.valueOf(rs.getInt("support")));
					list.add(info);
				}
			}
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, typeId);
				ps.setInt(2, titleId);
			}
		});

		return list;
	}

}
