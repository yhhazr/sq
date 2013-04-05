package com.sz7road.web.model.rolemanage;

import java.util.Date;

public class UserRoleInfo {
	private static final long serialVersionUID = 238875551807176045L;

	private int roleId;

	private int userId;

	private Date createDate;


	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Get the role created date
	 * @return Date,created date.
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Set the role created date.
	 * @param createDate created date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
