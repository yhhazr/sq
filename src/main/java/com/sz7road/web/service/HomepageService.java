package com.sz7road.web.service;


import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;

public interface HomepageService {

	public boolean insertItem(final HomepageItem item) throws Exception;
	
	public PaginationResult<HomepageItem> getItemList(PageInfo pageInfo, int typeId) throws Exception;
	
	public PaginationResult<HomepageItem> getItemListOrderByParam(PageInfo pageInfo,int typeId, String param, boolean isDesc) throws Exception;
	
	public boolean deleteItem(final int id) throws Exception;
	
	public HomepageItem getItem(final int id) throws Exception;
	
	public boolean update(final HomepageItem item) throws Exception;
	
//	public HomepageItem getFlashByPosition(String position) throws Exception;

//	public HomepageItem getImageByPosition(String position) throws Exception;
	


}
