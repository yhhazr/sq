package com.sz7road.web.service.impl;

import java.util.List;

import com.sz7road.web.dao.HomepageItemDao;
import com.sz7road.web.dao.impl.HomepageItemDaoImpl;
import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.HomepageService;

public class HomepageServiceImpl implements HomepageService {
	
	private static HomepageServiceImpl _this;
	
	private static HomepageItemDao homepageItemDao;

	private HomepageServiceImpl() {
		homepageItemDao = HomepageItemDaoImpl.getInstance();
	}

	
	public synchronized static HomepageServiceImpl getInstance() {
		if (_this == null)
			_this = new HomepageServiceImpl();
		return _this;
	}
	

	
	
	/*
	 * 后台service
	 */
	
	//后台根据type排序查询
	@Override
	public PaginationResult<HomepageItem> getItemListOrderByParam(PageInfo pageInfo,int typeId, String orderParam, boolean isDesc) throws Exception {
		int total = homepageItemDao.getItemCount(typeId);
		List<HomepageItem> itemList = homepageItemDao.getItemListOrderByParam(pageInfo, typeId, orderParam, isDesc);
		PaginationResult<HomepageItem> pageResult = new PaginationResult<HomepageItem>(total, itemList);

		return pageResult;
	}
	
	//后台分页查询
	@Override
	public PaginationResult<HomepageItem> getItemList(PageInfo pageInfo,int typeId)
			throws Exception {
		int total = homepageItemDao.getItemCount(typeId);
		List<HomepageItem> itemList = homepageItemDao.getItemList(pageInfo,typeId);
		PaginationResult<HomepageItem> pageResult = new PaginationResult<HomepageItem>(total, itemList);

		return pageResult;
	}
	
	//后台删除
	@Override
	public boolean deleteItem(int id) throws Exception {
		
		return homepageItemDao.deleteItem(id);
	}
	
	//后台新增
	@Override
	public boolean insertItem(HomepageItem item) throws Exception {
		
		return homepageItemDao.insertItem(item);
	}

	//后台修改
	@Override
	public boolean update(HomepageItem item) throws Exception {
		
		return homepageItemDao.updateItem(item);
	}
	
	//后台根据ID查询
	@Override
	public HomepageItem getItem(int id) throws Exception {
		
		return homepageItemDao.getItemById(id);
	}

//	@Override
//	public HomepageItem getFlashByPosition(String position) throws Exception {
//		
//		return homepageItemDao.getItemByPosition(position);
//	}
//
//	@Override
//	public HomepageItem getImageByPosition(String position) throws Exception {
//		
//		return homepageItemDao.getItemByPosition(position);
//	}


}
