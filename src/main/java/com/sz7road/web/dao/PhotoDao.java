package com.sz7road.web.dao;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.photomanage.Photo;
import com.sz7road.web.model.photomanage.PhotoCat;

public interface PhotoDao {

	public boolean insertPhoto(final Photo photo) throws Exception;
	
	public List<Photo> getPhotoList(final int catId) throws Exception; 
	
	public boolean insertPhotoCat(final PhotoCat photoCat) throws Exception;
	
	public List<PhotoCat> getPhotoCatList(PageInfo pageInfo) throws Exception;
	
	public int getPhotoCatCount() throws Exception;
	
	public boolean deletePhotoCat(final int photoCatId) throws Exception;
	
	public PhotoCat getPhotoCatById(final int catId) throws Exception;
	
	public boolean updatePhotoCat(final PhotoCat photoCat) throws Exception; 
	
	public boolean deletePhotoByCatId(final int photoCatId) throws Exception;
	
	public boolean deletePhotoByPhotoId(final int photoId) throws Exception;

	public int getPhotoCount(int catId) throws Exception;

	public List<Photo> getPhotoList(PageInfo pageInfo, int catId) throws Exception;

	public boolean editPhoto(Photo photo) throws Exception;

	public Photo getPhotoById(int photoId) throws Exception;

	public List<PhotoCat> getPhotoCat() throws Exception;

	public PhotoCat getPhotoCatByName(String photoName) throws Exception;
	
//	public List<Photo> selectPhotoByType(final int typeId) throws Exception;
	
	public List<Photo> getPhotoByCatOnline(final int catId, PageInfo pageInfo) throws Exception;
	
	public int getPhotoCountByCatOnline(final int catId) throws Exception;
	
	public List<Photo> getPhotoListOnline() throws Exception;
	
	public List<Photo> getPhotoListOnline(final int catId) throws Exception;
	
	public List<Photo> getPhotoByCatOnHomepage(final int catId, PageInfo pageInfo) throws Exception;
	
}
