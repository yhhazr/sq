package com.sz7road.web.model.rolemanage;

import java.util.Date;

public class RolePermissionInfo  {
	private static final long serialVersionUID = -6829691441228786271L;

	private Integer roleId;

	private Integer permId;

	private Date createDate;
	/**
	 * Get cretaed date.
	 * @return Date
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * Set created date.
	 * @param createDate  created date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getPermId() {
		return permId;
	}
	public void setPermId(Integer permId) {
		this.permId = permId;
	}
	
}
