package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.DownloadCenterDao;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.photomanage.Photo;
import com.sz7road.web.model.photomanage.PhotoCat;
import com.sz7road.web.model.vediomanage.VideoInfo;

public class DownloadCenterDaoImpl implements DownloadCenterDao {
	
	private static final String GET_PHOTO = "select * from ddt_photo where cat_id=?";
	private static final String GET_CATS = "select * from ddt_photo_category";
	private static final String GET_VIDEO = "select * from ddt_video";
	private static final String GET_COUNT = "select count(1) from ddt_photo where cat_id=?";
	
	private static DownloadCenterDaoImpl _instence = new DownloadCenterDaoImpl();
	public static DownloadCenterDaoImpl getInstence(){
		return _instence;
	}

	@Override
	public List<Photo> getPhotos(PageInfo page, final int id) throws Exception {
		final List<Photo> list = new ArrayList<Photo>();
		StringBuilder str = new StringBuilder();
		str.append(GET_PHOTO);
		str.append(" order by photo_id DESC ");
		str.append(" limit ").append(page.getStartRow()).append(",").append(page.getPageSize());
		DB.select(str.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				Photo photo = null;
				while(rs.next()){
					photo = new Photo();
					photo.setCatId(rs.getInt("cat_id"));
					photo.setPhotoId(rs.getInt("photo_id"));
					photo.setPhotoName(rs.getString("photo_name"));
					photo.setThumbnail(rs.getString("thumbnail"));
					list.add(photo);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, id);
			}
			
		});
		return list;
	}

	@Override
	public List<PhotoCat> getCats() throws Exception {
		final List<PhotoCat> list = new ArrayList<PhotoCat>();
		StringBuilder str = new StringBuilder();
		str.append(GET_CATS);
		DB.select(str.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				PhotoCat p = null;
				while(rs.next()){
					p = new PhotoCat();
					p.setCatDesc(rs.getString("cat_desc"));
					p.setCatId(rs.getInt("cat_id"));
					p.setCatName(rs.getString("cat_name"));
					list.add(p);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
			}
			
		});
		return list;
	}

	@Override
	public List<VideoInfo> getVideos(PageInfo page) throws Exception {
		final List<VideoInfo> list = new ArrayList<VideoInfo>();
		StringBuilder str = new StringBuilder();
		str.append(GET_VIDEO);
		str.append(" limit ").append(page.getStartRow()).append(",").append(page.getPageSize());
		DB.select(str.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				VideoInfo v = null;
				while(rs.next()){
					v = new VideoInfo();
					v.setVideoId(rs.getInt("video_id"));
					v.setVideoLink(rs.getString("video_name"));
					v.setVideoPicName(rs.getString("video_pic_name"));
					v.setVideoTitle(rs.getString("video_title"));
					list.add(v);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
			}
			
		});
		return list;
	}

	@Override
	public int getCount(final int id) throws Exception {
		final int[] count = {0};
		StringBuilder str = new StringBuilder();
		str.append(GET_COUNT);
		DB.select(str.toString(), new ParamReadStH() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					count[0] = rs.getInt(1);
				}
				
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, id);
			}
			
			
		});
		return count[0];
	}

}
