package com.sz7road.web.dao;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.model.rolemanage.RoleInfo;
import com.sz7road.web.model.rolemanage.UserRoleInfo;

public interface RoleDao {

	/**
	 * Insert role information into table ROLE.
	 * @param role,<code>RoleDTO</code> object
	 * @return Long,role id
	 */
	public boolean insertRole(final RoleInfo role) throws Exception;

	/**
	 * Update the role information to table ROLE.
	 * @param role,<code>RoleDTO</code> object
	 */
	public boolean updateRole(final RoleInfo role) throws Exception;

	/**
	 * Update the user's role information to table USER_ROLE.
	 * @param role,<code>UserRoleDTO</code> object
	 */
	public boolean updateUserRole(final UserRoleInfo role) throws Exception;

	/**
	 * Insert user role into table USER_ROLE.
	 * @param role UserRole object
	 * @return roleid The Id of the role that created.
	 */
	public boolean insertUserRole(final UserRoleInfo role) throws Exception;

	/**
	 * Delete the role information from table ROLE.
	 * @param roleId role's id
	 */
	public boolean deleteRole(final Integer roleId) throws Exception;

	/**
	 * Delete the userRole permission information from table ROLE_PERMISSION.
	 * @param roleId role's id
	 */
	public boolean deleteRolePermission(final Integer roleId) throws Exception;

	/**
	 * Revoke user role from table USER_ROLE by user id.
	 * @param userId user's id
	 */
	public boolean delUserRoleByUserId(final Integer userId) throws Exception;

	/**
	 * Delete the userRole information from table USER_ROLE by role id.
	 * @param roleId role's id
	 */
	public boolean delUserRoleByRoleId(final Integer roleId) throws Exception;

	/**
	 * Get the role id  from from table USER_ROLE by user id and pubcode.
	 * @param roleId role's id
	 * @param pubCode publication code
	 * @return Long,role id
	 */
	public UserRoleInfo getUserRoleByUserId(final Integer userId) throws Exception;

	/**
	 * Get the <code>UserRoleDTO</code> information from table USER_ROLE by role id.
	 * @param roleId role's id
	 * @return <code>UserRoleDTO</code> object
	 */
	public UserRoleInfo getUserRoleByRoleId(final Integer roleId) throws Exception;

	/**
	 * Get the <code>UserRoleDTO</code> information from table USER_ROLE by role id and user id.
	 * @param userId user's id
	 * @param roleId role's id
	 * @return <code>UserRoleDTO</code> object
	 */
	public UserRoleInfo getUserRole(final Integer userId, final Integer roleId) throws SQLException;

	/**
	 * Get the role information from table ROLE by role id.
	 * @param roleId role's id
	 * @return <code>RoleDTO</code> object
	 */
	public RoleInfo getRole(final Integer roleId) throws Exception;

	/**
	 * get the roles information from DB.
	 * @param roleId role's id
	 * @return list roles
	 */
	public List<RoleInfo> getRoles() throws Exception;

}