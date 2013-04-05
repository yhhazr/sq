package com.sz7road.web.model.rolemanage;

import java.util.ArrayList;
import java.util.List;

public class MenuInfo {
	private static final long serialVersionUID = 1962375337984068085L;

	private Integer menuId;

	private Integer parentMenuId;

	private Integer orderId;
	
	private String leaves;

	private String menuName;

	private String menuDesc;

	private String menuUrl;
	
	private List<MenuInfo> childMenus = new ArrayList<MenuInfo>();

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Integer parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public List<MenuInfo> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<MenuInfo> childMenus) {
		this.childMenus = childMenus;
	}

	public String getLeaves() {
		return leaves;
	}

	public void setLeaves(String leaves) {
		this.leaves = leaves;
	}
	
	

}
