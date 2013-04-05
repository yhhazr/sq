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

import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.VideoDao;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.vediomanage.VideoInfo;
import com.sz7road.web.model.vediomanage.VideoState;

public class VideoDaoImpl implements VideoDao{

	private static final String UPLOAD_VIDEO_SQL = "insert into ddt_video(state_id,video_title,video_name,video_pic_name,video_type,enable_flag,create_date,l_upd_date) values(?,?,?,?,?,?,?,?)";
	private static final String DELETE_VIDEO_SQL = "delete from ddt_video where video_id=?";
	private static final String SELECT_VIDEO_BYID_SQL = "select * from ddt_video where video_id=?";
	private static final String FIND_ALL_SQL = "select * from ddt_video";
	private static final String FIND_STATE_SQL = "select state_id,state_name from ddt_video_state";
	private static final String GET_VIDEO_COUNT = "select count(video_id) num from ddt_video";
	private static final String MODIFY_VIDEO_BY_ID_SQL = "update ddt_video set video_type=?,video_title=?,video_name=?,enable_flag=?,state_id=?,video_pic_name=?,l_upd_date=? where video_id=?";
	
	private static VideoDaoImpl _instance = new VideoDaoImpl();
	
	public VideoDaoImpl(){
		
	}
	
	public static VideoDaoImpl getInstance() {
		return _instance;
	}
	
	@Override
	public int uploadVideo(final VideoInfo video) throws Exception {
		final int[] userIdArray = {0};
		PreparedStatement prep = DB.prepareStatement(UPLOAD_VIDEO_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
		DB.insertUpdate(prep, new IUStH(){

			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, video.getStateId());
				ps.setString(2, video.getVideoTitle());
				ps.setString(3, video.getVideoLink());
				ps.setString(4, video.getVideoPicName());
				ps.setBoolean(5, video.isVideoType());
				ps.setBoolean(6, video.isEnableFlag());
				ps.setTimestamp(7, DateUtil.getCurrentTimestamp());
				ps.setTimestamp(8, DateUtil.getCurrentTimestamp());
				ps.executeUpdate();
				
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()){
					userIdArray[0] = rs.getInt(1);
				}
			}
			
		});
		return userIdArray[0];
	}

	@Override
	public boolean deleteVideo(final List<Integer> IdList) throws Exception {
		boolean result = false;
		final Iterator<Integer> it = IdList.iterator();
		while(it.hasNext()){
			result = DB.insertUpdate(DELETE_VIDEO_SQL, new IUStH(){
				@Override
				public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
					ps.setInt(1, (Integer) it.next());
					ps.executeUpdate();
				}
			});
		}
		
		return result;
	}

	@Override
	public VideoInfo findVideo(final int video_id) throws Exception {
		final VideoInfo info = new VideoInfo();
		DB.select(SELECT_VIDEO_BYID_SQL, new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					info.setVideoId(rs.getInt("video_id"));
					info.setStateId(rs.getInt("state_id"));
					info.setVideoLink(rs.getString("video_name"));
					info.setVideoTitle(rs.getString("video_title"));
					info.setVideoType(rs.getBoolean("video_type"));
					info.setEnableFlag(rs.getBoolean("enable_flag"));
					info.setVideoPicName(rs.getString("video_pic_name"));
				}
			}
			
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, video_id);
				//ps.execute();
			}
		});
		return info;
	}

	@Override
	public List<VideoInfo> findAllVideo(PageInfo pageInfo) throws Exception {
		final List<VideoInfo> list = new ArrayList<VideoInfo>();
		StringBuilder sql = new StringBuilder();
		sql.append(FIND_ALL_SQL);
		sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
		DB.select(sql.toString(), new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					VideoInfo info = new VideoInfo();
					info.setStateId(rs.getInt("state_id"));
					info.setVideoId(rs.getInt("video_id"));
					info.setVideoLink(rs.getString("video_name"));
					info.setVideoPicName(rs.getString("video_pic_name"));
					info.setVideoTitle(rs.getString("video_title"));
					info.setEnableFlag(rs.getBoolean("enable_flag"));
					info.setCreateDate(rs.getDate("create_date"));
					info.setLastUpdDate(rs.getDate("l_upd_date"));
					info.setVideoType(rs.getBoolean("video_type"));
					list.add(info);
				}
			}
			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {}
		});
		return list;
	}

	@Override
	public List<VideoInfo> selectAllVideoInfo() throws Exception {
		final List<VideoInfo> list = new ArrayList<VideoInfo>();
		StringBuilder sql = new StringBuilder();
		sql.append(FIND_ALL_SQL);
		DB.select(sql.toString(), new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					VideoInfo info = new VideoInfo();
					info.setStateId(rs.getInt("state_id"));
					info.setVideoId(rs.getInt("video_id"));
					info.setVideoLink(rs.getString("video_name"));
					info.setVideoPicName(rs.getString("video_pic_name"));
					info.setVideoTitle(rs.getString("video_title"));
					info.setEnableFlag(rs.getBoolean("enable_flag"));
					info.setCreateDate(rs.getDate("create_date"));
					info.setLastUpdDate(rs.getDate("l_upd_date"));
					info.setVideoType(rs.getBoolean("video_type"));
					list.add(info);
				}
			}
			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {}
		});
		return list;
	}


	@Override
	public int saveVideo(final VideoInfo video) throws Exception {
		final int[] userIdArray = {0};
		PreparedStatement prep = DB.prepareStatement(UPLOAD_VIDEO_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
		DB.insertUpdate(prep, new IUStH(){

			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, video.getStateId());
				ps.setString(2, video.getVideoTitle());
				ps.setString(3, video.getVideoLink());
				//ps.setString(4, video.getVideo_pic_name());
				ps.setBoolean(5, video.isVideoType());
				ps.setBoolean(6, video.isEnableFlag());
				//ps.setTimestamp(7, DateUtil.getCurrentTimestamp());
				ps.setTimestamp(8, DateUtil.getCurrentTimestamp());
				ps.executeUpdate();
				
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()){
					userIdArray[0] = rs.getInt(1);
				}
			}
			
		});
		return userIdArray[0];
	}

	@Override
	public List<VideoState> findVideoState() throws Exception {
		final List<VideoState> list = new ArrayList<VideoState>();
		DB.select(FIND_STATE_SQL, new ParamReadStH() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					VideoState vs = new VideoState();
					vs.setStateId(rs.getInt(1));
					vs.setStateName(rs.getString(2));
					list.add(vs);
				}
			}

			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {
				
			}
			
		});
		return list;
	}

	@Override
	public int getVideoInfoCount() throws Exception {
		final int[] count = { 0 };
		DB.select(GET_VIDEO_COUNT, new ParamReadStH(){

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					count[0] = rs.getInt("num");
				}
			}

			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {
				
			}
			
		});
		return count[0];
	}

	@Override
	public int editVideoInfoById(final VideoInfo videoInfo) throws Exception {
		final int[] userIdArray = {0};
		PreparedStatement prep = DB.prepareStatement(MODIFY_VIDEO_BY_ID_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
		DB.insertUpdate(prep, new IUStH(){

			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, videoInfo.isVideoType());
				ps.setString(2, videoInfo.getVideoTitle());
				ps.setString(3, videoInfo.getVideoLink());
				ps.setBoolean(4, videoInfo.isEnableFlag());
				ps.setInt(5, videoInfo.getStateId());
				ps.setString(6, videoInfo.getVideoPicName());
				ps.setTimestamp(7, DateUtil.getCurrentTimestamp());
				ps.setInt(8, videoInfo.getVideoId());
				ps.executeUpdate();
				
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()){
					userIdArray[0] = rs.getInt(1);
				}
			}
			
		});
		return userIdArray[0];
	}

}
