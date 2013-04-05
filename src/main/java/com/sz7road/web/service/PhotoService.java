package com.sz7road.web.service;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.photomanage.Photo;
import com.sz7road.web.model.photomanage.PhotoCat;

public interface PhotoService {

	public boolean insertPhoto(final Photo photo) throws Exception;
	
	public List<Photo> getPhotoList(final int catId) throws Exception;
	
	public boolean insertPhotoCat(final PhotoCat photoCat) throws Exception;
	
	public PaginationResult<PhotoCat> getPhotoCatList(PageInfo pageInfo) throws Exception;
	
	public boolean deletePhotoCat(final int photoCatId) throws Exception;
	
	public PhotoCat getPhotoCatById(final int catId) throws Exception;
	
	public boolean updatePhotoCat(final PhotoCat photoCat) throws Exception;
	
	public boolean deletePhotoByCatId(final int photoCat) throws Exception;
	
	public boolean deletePhotoByPhotoId(final int photoId) throws Exception;

	public PaginationResult<Photo> getPhotoList(PageInfo pageInfo, int catId) throws Exception;

	public boolean editPhoto(Photo photo) throws Exception;

	public Photo getPhotoById(int photoId) throws Exception;

	public List<PhotoCat> getPhotoCat() throws Exception;

	public PhotoCat getPhotoCatByName(String photoName) throws Exception;
}
