package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.pagination.PageInfo;

public interface HomepageItemDao {

	public boolean insertItem(final HomepageItem item) throws Exception;
		
	public boolean deleteItem(final int id) throws Exception;
	
	public boolean updateItem(final HomepageItem item) throws Exception;
	
	public List<HomepageItem> getItemList(PageInfo pageInfo, int typeId) throws Exception;
	
	public List<HomepageItem> getItemListOrderByParam(PageInfo pageInfo, final int typeId, String orderParam, boolean isDesc) throws Exception;
	
	public int getItemCount(int typeId) throws Exception;
	
	public HomepageItem getItemById(final int id) throws Exception;
	
	public HomepageItem getItemByPosition(final String position) throws Exception;
	
	public List<HomepageItem> findItemListByTypeId(final int typeId) throws Exception;
	
	public List<HomepageItem> selectItemByTypeIdAndPosition(final int typeId, final String position, final int size) throws Exception;
	
	public List<HomepageItem> selectItemByPosition(final String position) throws Exception;
}
