package com.sz7road.web.model.rolemanage;

public class Permission {
	
	private Integer permId;

	private Integer parentPermId;
	
	private String orderId;

	private String permName;

	private String permDesc;

	private String permUrl;
	
	private boolean menuFlag;
	
	private boolean restrictedFlag;

	public Integer getPermId() {
		return permId;
	}

	public void setPermId(Integer permId) {
		this.permId = permId;
	}

	public Integer getParentPermId() {
		return parentPermId;
	}

	public void setParentPermId(Integer parentPermId) {
		this.parentPermId = parentPermId;
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

	public boolean isMenuFlag() {
		return menuFlag;
	}

	public void setMenuFlag(boolean menuFlag) {
		this.menuFlag = menuFlag;
	}

	public boolean isRestrictedFlag() {
		return restrictedFlag;
	}

	public void setRestrictedFlag(boolean restrictedFlag) {
		this.restrictedFlag = restrictedFlag;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
