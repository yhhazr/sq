package com.sz7road.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.transaction.JdbcTransaction;
import com.sz7road.web.dao.PhotoDao;
import com.sz7road.web.dao.impl.PhotoDaoImpl;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.photomanage.Photo;
import com.sz7road.web.model.photomanage.PhotoCat;
import com.sz7road.web.service.PhotoService;

/**
 * 
 * @author hai.yuan
 *
 */
public class PhotoServiceImpl implements PhotoService {

	private static PhotoServiceImpl _this;
	
	private static PhotoDao photoDao;
	
	private PhotoServiceImpl() {
		photoDao = PhotoDaoImpl.getInstance();
	}
	
	public synchronized static PhotoServiceImpl getInstance() {
		if (_this == null)
			_this = new PhotoServiceImpl();
		return _this;
	}
	
	@Override
	public boolean insertPhoto(Photo photo) throws Exception {

		return photoDao.insertPhoto(photo);
	}

	@Override
	public List<Photo> getPhotoList(int catId) throws Exception {
		
		return photoDao.getPhotoList(catId);
	}

	@Override
	public boolean insertPhotoCat(PhotoCat photoCat) throws Exception {
		
		return photoDao.insertPhotoCat(photoCat);
	}

	@Override
	public PaginationResult<PhotoCat> getPhotoCatList(PageInfo pageInfo) throws Exception {
		int total = photoDao.getPhotoCatCount();
		List<PhotoCat> photoCatList = photoDao.getPhotoCatList(pageInfo);
		PaginationResult<PhotoCat> pageResult = new PaginationResult<PhotoCat>(total, photoCatList);
		return pageResult;
	}

	@Override
	public boolean deletePhotoCat(int photoCatId) throws Exception {
		boolean result = false;
		JdbcTransaction tran = JdbcTransaction.getInstance();
		tran.getAndBeginTransaction();
		boolean deletePhotoCatResult = photoDao.deletePhotoCat(photoCatId);
		boolean deletePhotoResult = photoDao.deletePhotoByCatId(photoCatId);
		tran.commit();
		if (deletePhotoCatResult && deletePhotoResult) {
			result = true;
		}
		return result;
	}

	@Override
	public PhotoCat getPhotoCatById(int catId) throws Exception {
		
		return photoDao.getPhotoCatById(catId);
	}

	@Override
	public boolean updatePhotoCat(PhotoCat photoCat) throws Exception {
		
		return photoDao.updatePhotoCat(photoCat);
	}

	@Override
	public boolean deletePhotoByCatId(int photoCatId) throws Exception {
		
		return photoDao.deletePhotoByCatId(photoCatId);
	}

	@Override
	public boolean deletePhotoByPhotoId(int photoId) throws Exception {
		
		return photoDao.deletePhotoByPhotoId(photoId);
	}

	@Override
	public PaginationResult<Photo> getPhotoList(PageInfo pageInfo, int catId) throws Exception {
		int total = photoDao.getPhotoCount(catId);
		List<Photo> photoList = photoDao.getPhotoList(pageInfo, catId);
		PhotoCat photoCat= photoDao.getPhotoCatById(catId);
		for (Photo photo : photoList) {
			photo.setCatName(photoCat.getCatName());
		}
		PaginationResult<Photo> pageResult = new PaginationResult<Photo>(total, photoList);
		return pageResult;
	}

	@Override
	public boolean editPhoto(Photo photo) throws Exception {
		return photoDao.editPhoto(photo);
	}

	@Override
	public Photo getPhotoById(int photoId) throws Exception {
		return  photoDao.getPhotoById(photoId);
	}

	@Override
	public List<PhotoCat> getPhotoCat() throws Exception {
		return photoDao.getPhotoCat();
	}

	@Override
	public PhotoCat getPhotoCatByName(String photoName) throws Exception {
		return photoDao.getPhotoCatByName(photoName);
	}

}
