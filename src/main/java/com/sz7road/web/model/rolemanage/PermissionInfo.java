package com.sz7road.web.model.rolemanage;

import java.util.ArrayList;
import java.util.List;

public class PermissionInfo {

	private static final long serialVersionUID = -2844710168806048953L;

	private Integer permId;

	private Integer parentPermId;
	
	private Integer orderId;

	private String permName;

	private String permDesc;

	private String permUrl;
	
	private String menuFlag;

	private String restrictedFlag;
	
	private List<PermissionInfo> childPermissions = new ArrayList<PermissionInfo>();

	/**
	 * Compare to another object.If the two permission ids are equal,they are equal. 
	 * @param obj the object to be compared
	 * @return boolean true means equal.
	 */
	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				PermissionInfo that = (PermissionInfo) obj;
				if (this.getPermId().equals(that.getPermId()))
					return true;
			}
		}
		return false;
	}

	public Integer getPermId() {
		return permId;
	}

	public Integer getParentPermId() {
		return parentPermId;
	}

	public void setParentPermId(Integer parentPermId) {
		this.parentPermId = parentPermId;
	}

	public void setPermId(Integer permId) {
		this.permId = permId;
	}

	public String getPermName() {
		return permName;
	}

	public void setPermName(String permName) {
		this.permName = permName;
	}

	public String getPermDesc() {
		return permDesc;
	}

	public void setPermDesc(String permDesc) {
		this.permDesc = permDesc;
	}

	public String getPermUrl() {
		return permUrl;
	}

	public void setPermUrl(String permUrl) {
		this.permUrl = permUrl;
	}

	public List<PermissionInfo> getChildPermissions() {
		return childPermissions;
	}

	public void setChildPermissions(List<PermissionInfo> childPermissions) {
		this.childPermissions = childPermissions;
	}
	
	public String getRestrictedFlag() {
		return restrictedFlag;
	}

	public void setRestrictedFlag(String restrictedFlag) {
		if("false".equals(restrictedFlag)){
			this.restrictedFlag = "N";
		}else{
			this.restrictedFlag = restrictedFlag;
		}
	}

	public String getMenuFlag() {
		return menuFlag;
	}

	public void setMenuFlag(String menuFlag) {
		if("false".equals(menuFlag)){
			this.menuFlag = "N";
		}else{
			this.menuFlag = menuFlag;
		}
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	

}
