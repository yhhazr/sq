package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;
import org.pureart.persistement.database.easydb.Transaction;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.transaction.JdbcTransaction;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.PhotoDao;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.photomanage.Photo;
import com.sz7road.web.model.photomanage.PhotoCat;


public class PhotoDaoImpl implements PhotoDao {

	private static final String INSERT_PHOTO_SQL = "INSERT INTO ddt_photo (cat_id, photo_name, photo_show, photo_title) values(?,?,?,?)";

//	private static final String SELECT_PHOTO_BY_CATID_SQL = "SELECT photo_id,photo_name,cat_id FROM ddt_photo WHERE cat_id = ?";

	private static final String INSERT_PHOTOCAT_SQL = "INSERT INTO ddt_photo_category (cat_name,cat_desc,homepage_show,image_url,create_date) values(?,?,?,?,?)";

	private static final String SELECT_ALL_PHOTOCAT_SQL = "SELECT cat_id,cat_name,cat_desc,create_date,homepage_show,image_url FROM ddt_photo_category";

	private static final String DELETE_PHOTOCAT_SQL = "DELETE FROM ddt_photo_category WHERE cat_id = ?";

	private static final String DELETE_PHOTO_BY_CATID_SQL = "DELETE FROM ddt_photo WHERE cat_id = ?";

	private static final String DELETE_PHOTO_BY_PHOTOID_SQL = "DELETE FROM ddt_photo WHERE photo_id = ?";

	private static final String SELECT_PHOTOCAT_SQL = "SELECT * FROM ddt_photo_category WHERE cat_id = ? ";
	
	private static final String SELECT_ALL_PHOTOCAT = "SELECT * FROM ddt_photo_category";
	
	private static final String SELECT_PHOTOCAT_BY_NAME = "SELECT * FROM ddt_photo_category WHERE cat_name = ?";

	private static final String UPDATE_PHOTOCAT_SQL = "UPDATE ddt_photo_category  SET cat_name = ?,cat_desc = ?,homepage_show = ?,image_url = ? WHERE cat_id = ? ";

	private static final String SELECT_PHOTO_COUNT = "SELECT COUNT(1) count FROM ddt_photo WHERE 1 ";
	
	private static final String SELECT_PHOTO_BY_CATID = "SELECT * FROM ddt_photo WHERE cat_id = ? order by photo_id DESC";

	private static final String UPDATE_PHOTO = "UPDATE ddt_photo SET cat_id = ?, photo_name = ?, photo_show = ?,thumbnail = ?,photo_title=? WHERE photo_id = ?";
	
//	private static final String SELECT_PHOTO_BY_PHOTOID = "SELECT * FROM ddt_photo WHERE photo_id = ?";
	
//	private static final String SELECT_PHOTO_BASE_SQL = "SELECT p.photo_id, p.cat_id, p.photo_name, p.photo_show, p.thumbnail, p.photo_title, c.cat_name, c.cat_desc, c.homepage_show, c.image_url, c.create_date FROM ddt_photo p, ddt_photo_category c WHERE 1 ";

	private static final String SELECT_PHOTO_BASE_SQL = "SELECT * FROM ddt_photo p LEFT JOIN ddt_photo_category c ON p.cat_id = c.cat_id WHERE 1 ";
	

	private static PhotoDaoImpl _instance = new PhotoDaoImpl();

	private PhotoDaoImpl() {
	}

	public static PhotoDaoImpl getInstance() {
		return _instance;
	}

	@Override
	public boolean insertPhoto(final Photo photo) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(INSERT_PHOTO_SQL, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setInt(1, photo.getCatId());
				ps.setString(2, photo.getPhotoName());
				ps.setString(3, photo.getPhotoShow());
				ps.setString(4, photo.getTitle());
				ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public List<Photo> getPhotoList(final int catId) throws Exception {
		StringBuffer sql = new StringBuffer(SELECT_PHOTO_BASE_SQL);
		sql.append(" AND p.cat_id=? ");
		final List<Photo> photoList = new ArrayList<Photo>();
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Photo info = new Photo();
					info.setPhotoId(rs.getInt("photo_id"));
					info.setCatId(rs.getInt("cat_id"));
					info.setPhotoName(SystemConfig.getProperty("admin.photo.url") + rs.getString("photo_name"));
					info.setPhotoId(rs.getInt("photo_id"));
					info.setCatId(rs.getInt("cat_id"));
					info.setCatName(rs.getString("cat_name"));
//					info.setPhotoName(rs.getString("photo_name"));
					info.setPhotoShow(rs.getString("photo_show"));
					info.setThumbnail(rs.getString("thumbnail"));
					info.setTitle(rs.getString("photo_title"));
					
					photoList.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, catId);
			}
		});
		return photoList;
	}

	//前台
	@Override
	public List<Photo> getPhotoListOnline(final int catId) throws Exception {
		StringBuffer sql = new StringBuffer(SELECT_PHOTO_BASE_SQL);
		sql.append(" AND p.cat_id=? ");
		final List<Photo> photoList = new ArrayList<Photo>();
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Photo info = new Photo();
					info.setPhotoId(rs.getInt("photo_id"));
					info.setCatId(rs.getInt("cat_id"));
					info.setPhotoId(rs.getInt("photo_id"));
					info.setCatId(rs.getInt("cat_id"));
					info.setCatName(rs.getString("cat_name"));
					info.setPhotoName(rs.getString("photo_name"));
					info.setPhotoShow(rs.getString("photo_show"));
					info.setThumbnail(rs.getString("thumbnail"));
					info.setTitle(rs.getString("photo_title"));
					
					photoList.add(info);
				}
			}
			
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, catId);
			}
		});
		return photoList;
	}

	@Override
	public boolean insertPhotoCat(final PhotoCat photoCat) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(INSERT_PHOTOCAT_SQL, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setString(1, photoCat.getCatName());
				ps.setString(2, photoCat.getCatDesc());
				ps.setBoolean(3, photoCat.isHomepageShow());
				ps.setString(4, photoCat.getImageUrl());
				ps.setTimestamp(5, DateUtil.getCurrentTimestamp());
				ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public List<PhotoCat> getPhotoCatList(PageInfo pageInfo) throws Exception {
		final List<PhotoCat> photoCatList = new ArrayList<PhotoCat>();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_ALL_PHOTOCAT_SQL);
		sql.append(" limit ").append(pageInfo.getStartRow()).append(",")
				.append(pageInfo.getPageSize());
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					PhotoCat info = new PhotoCat();
					info.setCatDesc(rs.getString("cat_desc"));
					info.setCatId(rs.getInt("cat_id"));
					info.setCatName(rs.getString("cat_name"));
					info.setHomepageShow(rs.getBoolean("homepage_show"));
					info.setImageUrl(rs.getString("image_url"));
					info.setCreateDate(rs.getTimestamp("create_date"));
					photoCatList.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
			}
		});
		return photoCatList;
	}

	@Override
	public int getPhotoCatCount() throws Exception {
		final int[] count = { 0 };
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) count from (");
		sql.append(SELECT_ALL_PHOTOCAT_SQL);
		sql.append(") a ");
		DB.select(sql.toString(), new ParamReadStH() {
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
	public boolean deletePhotoCat(final int photoCatId) throws Exception {
		final boolean[] deleteResult = { false };
		Transaction tran = JdbcTransaction.getInstance()
				.getAndBeginTransaction();
		tran.insertUpdate(DELETE_PHOTOCAT_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setInt(1, photoCatId);
				int result = ps.executeUpdate();
				if (result > 0) {
					deleteResult[0] = true;
				}
			}
		});
		return deleteResult[0];
	}

	@Override
	public PhotoCat getPhotoCatById(final int catId) throws Exception {
		final PhotoCat info = new PhotoCat();
		DB.select(SELECT_PHOTOCAT_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setCatDesc(rs.getString("cat_desc"));
					info.setCatId(rs.getInt("cat_id"));
					info.setCatName(rs.getString("cat_name"));
					info.setHomepageShow(rs.getBoolean("homepage_show"));
					info.setImageUrl(rs.getString("image_url"));
					info.setCatId(rs.getInt("cat_id"));
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, catId);
			}
		});

		return info;
	}

	@Override
	public boolean updatePhotoCat(final PhotoCat photoCat) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(UPDATE_PHOTOCAT_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setString(1, photoCat.getCatName());
				ps.setString(2, photoCat.getCatDesc());
				ps.setBoolean(3, photoCat.isHomepageShow());
				ps.setString(4, photoCat.getImageUrl());
				ps.setInt(5, photoCat.getCatId());
				ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public boolean deletePhotoByCatId(final int photoCatId) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(DELETE_PHOTO_BY_CATID_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setInt(1, photoCatId);
				ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public boolean deletePhotoByPhotoId(final int photoId) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(DELETE_PHOTO_BY_PHOTOID_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setInt(1, photoId);
				ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public int getPhotoCount(final int catId) throws Exception {
		StringBuffer sql = new StringBuffer(SELECT_PHOTO_COUNT);
		sql.append(" AND cat_id = ? ");
		final int[] count = new int[1];
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					count[0] = rs.getInt("count");
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, catId);
			}
		});
		return count[0];
	}

	@Override
	public int getPhotoCountByCatOnline(final int catId) throws Exception {
		StringBuffer sql = new StringBuffer(SELECT_PHOTO_COUNT);
		sql.append(" AND cat_id=? ");
		final int[] count = new int[1];
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					count[0] = rs.getInt("count");
				}
			}
			
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, catId);
			}
		});
		return count[0];
	}

	@Override
	public List<Photo> getPhotoList(PageInfo pageInfo, final int catId)
			throws Exception {
		final List<Photo> photoList = new ArrayList<Photo>();
		StringBuilder sql = new StringBuilder(SELECT_PHOTO_BASE_SQL);
		sql.append(" AND p.cat_id=? ");
		sql.append(" limit ")
				.append(pageInfo.getStartRow()).append(",")
				.append(pageInfo.getPageSize());
		DB.select(sql.toString(), new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				Photo photo = null;
				while(rs.next()){
					photo = new Photo();
					photo.setPhotoId(rs.getInt("photo_id"));
					photo.setCatId(rs.getInt("cat_id"));
					photo.setCatName(rs.getString("cat_name"));
					photo.setPhotoName(rs.getString("photo_name"));
					photo.setPhotoShow(rs.getString("photo_show"));
					photo.setTitle(rs.getString("photo_title"));
					photo.setTitle(rs.getString("thumbnail"));
					photoList.add(photo);
				}
			}
			
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, catId);
			}
		});
		return photoList;
	}

	@Override
	public List<Photo> getPhotoListOnline() throws Exception {
		final List<Photo> photoList = new ArrayList<Photo>();
		StringBuilder sb = new StringBuilder(SELECT_PHOTO_BASE_SQL);
		sb.append(" AND photo_show = 'true' ORDER BY photo_id DESC ");
		DB.select(sb.toString(), new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				Photo photo = null;
				while(rs.next()){
					photo = new Photo();
					photo.setPhotoId(rs.getInt("photo_id"));
					photo.setCatId(rs.getInt("cat_id"));
					photo.setCatName(rs.getString("cat_name"));
					photo.setPhotoName(rs.getString("photo_name"));
					photo.setPhotoShow(rs.getString("photo_show"));
					photo.setThumbnail(rs.getString("thumbnail"));
					photo.setTitle(rs.getString("photo_title"));
					photoList.add(photo);
				}
			}
			
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
			}
		});
		return photoList;
	}

	@Override
	public boolean editPhoto(final Photo photo) throws SQLException {
		boolean result = false;
		result = DB.insertUpdate(UPDATE_PHOTO, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setInt(1, photo.getCatId());
				ps.setString(2, photo.getPhotoName());
				ps.setString(3, photo.getPhotoShow());
				ps.setString(4, photo.getThumbnail());
				ps.setString(5, photo.getTitle());
				ps.setInt(6, photo.getPhotoId());
				ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public Photo getPhotoById(final int photoId) throws Exception {
		StringBuffer sql = new StringBuffer(SELECT_PHOTO_BASE_SQL);
		sql.append(" AND photo_id=? ");
		final Photo photo = new Photo();
		DB.select(sql.toString(), new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					photo.setCatId(rs.getInt("cat_id"));
					photo.setPhotoId(rs.getInt("photo_id"));
					photo.setPhotoName(rs.getString("photo_name"));
					photo.setPhotoShow(rs.getString("photo_show"));
					photo.setThumbnail(rs.getString("thumbnail"));
					photo.setTitle(rs.getString("photo_title"));
				}
			}
			
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, photoId);
			}
		});
		return photo;
	}

	@Override
	public List<PhotoCat> getPhotoCat() throws Exception {
		final List<PhotoCat> photoCats = new ArrayList<PhotoCat>();
		DB.select(SELECT_ALL_PHOTOCAT, new ParamReadStH() {
			PhotoCat photoCat = null;
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					photoCat = new PhotoCat();
					photoCat.setCatDesc(rs.getString("cat_desc"));
					photoCat.setCatId(rs.getInt("cat_id"));
					photoCat.setCatName(rs.getString("cat_name"));
					photoCat.setHomepageShow(rs.getBoolean("homepage_show"));
					photoCat.setImageUrl(rs.getString("image_url"));
					photoCat.setCatId(rs.getInt("cat_id"));
					photoCats.add(photoCat);
				}
			}
			
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
			}
		});
		return photoCats;
	}

	@Override
	public PhotoCat getPhotoCatByName(final String photoName) throws Exception {
		final PhotoCat photoCat = new PhotoCat();
		DB.select(SELECT_PHOTOCAT_BY_NAME, new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					photoCat.setCatDesc(rs.getString("cat_desc"));
					photoCat.setCatId(rs.getInt("cat_id"));
					photoCat.setCatName(rs.getString("cat_name"));
					photoCat.setHomepageShow(rs.getBoolean("homepage_show"));
					photoCat.setImageUrl(rs.getString("image_url"));
					photoCat.setCatId(rs.getInt("cat_id"));
				}
			}
			
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setString(1, photoName);
				
			}
		});
		return photoCat;
	}
	
//	public List<Photo> selectPhotoByType(final int typeId) throws Exception {
//		StringBuffer sql = new StringBuffer(SELECT_PHOTO_BASE_SQL);
//		sql.append(" AND cat_id=? ");
//		if(typeId == 94){
//			sql.append(" AND photo_show = 'true' ORDER BY photo_id DESC limit 0,4");
//		}else{
//			sql.append(" AND photo_show = 'true' ORDER BY photo_id DESC limit 0,3"); 
//		}
//		final List<Photo> list = new ArrayList<Photo>();
//		DB.select(sql.toString(), new ParamReadStH() {
//
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while (rs.next()) {
//					Photo item = new Photo();
//					item.setPhotoId(rs.getInt("photo_id"));
//					item.setCatId(rs.getInt("cat_id"));
//					item.setPhotoName(rs.getString("photo_name"));
//					item.setThumbnail(rs.getString("thumbnail"));
//					list.add(item);
//				}
//			}
//			@Override
//			public void setParams(PreparedStatement ps) throws SQLException {
//				ps.setInt(1, typeId);
//			}
//
//		});
//		return list;
//	}

	@Override
	public List<Photo> getPhotoByCatOnline(final int catId, PageInfo pageInfo) throws Exception {
		StringBuilder sb = new StringBuilder(SELECT_PHOTO_BASE_SQL);
		sb.append(" AND p.cat_id=? ");
		sb.append(" ORDER BY photo_id DESC ");
		sb.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
		final List<Photo> list = new ArrayList<Photo>();
		DB.select(sb.toString(), new ParamReadStH() {
		Photo item = null;
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					item = new Photo();
					item.setPhotoId(rs.getInt("photo_id"));
					item.setCatId(rs.getInt("cat_id"));
					item.setCatName(rs.getString("cat_name"));
					item.setPhotoName(rs.getString("photo_name"));
					item.setThumbnail(rs.getString("thumbnail"));
					item.setTitle(rs.getString("photo_title"));
					list.add(item);
				}
			}
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, catId);
			}
	
		});
		return list;
	}

	@Override
	public List<Photo> getPhotoByCatOnHomepage(final int catId, PageInfo pageInfo) throws Exception {
		StringBuilder sb = new StringBuilder(SELECT_PHOTO_BASE_SQL);
		sb.append(" AND p.cat_id=? ");
		sb.append(" AND photo_show = 'true' ORDER BY photo_id DESC ");
		sb.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
		final List<Photo> list = new ArrayList<Photo>();
		DB.select(sb.toString(), new ParamReadStH() {
			Photo item = null;
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					item = new Photo();
					item.setPhotoId(rs.getInt("photo_id"));
					item.setCatId(rs.getInt("cat_id"));
					item.setCatName(rs.getString("cat_name"));
					item.setPhotoName(rs.getString("photo_name"));
					item.setThumbnail(rs.getString("thumbnail"));
					item.setTitle(rs.getString("photo_title"));
					list.add(item);
				}
			}
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, catId);
			}
			
		});
		return list;
	}
	
}
