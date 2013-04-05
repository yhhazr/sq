package com.sz7road.web.model.rolemanage;

import java.util.Date;

public class RoleInfo {
	private static final long serialVersionUID = 1962375337984068085L;

	private Integer roleId;

	private String roleName;

	private String roleDesc;

	private Date createDate;

	private Date lupdate;

	private boolean enableFlag;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLupdate() {
		return lupdate;
	}

	public void setLupdate(Date lupdate) {
		this.lupdate = lupdate;
	}

	public boolean isEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(boolean enableFlag) {
		this.enableFlag = enableFlag;
	}

}
