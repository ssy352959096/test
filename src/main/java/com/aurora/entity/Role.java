package com.aurora.entity;

import java.io.Serializable;

/**
 * 描述:角色
 * 创建: BYG 2017/5/25
 * 修改: 
 * @version 1.0
 */
public class Role implements Serializable{
	private int roleID;
	private String roleName;
	private String roleRights;    	//角色拥有的权限，是menuID经过2的权的和处理后的集合。
	private int roleParentID;  		//父角色的ID
	private String roleRemark;    	//角色备注
	private int roleCreateUserID;	//创建角色用户ID
	private String roleAddRights;   //当前角色新增菜单、按钮权限
	private String roleDeleteRights;//当前角色删除菜单、按钮权限
	private String roleEditRights;	//当前角色编辑菜单、按钮权限
	private String roleSeeRights;	//当前角色查看菜单、按钮权限
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleRights() {
		return roleRights;
	}
	public void setRoleRights(String roleRights) {
		this.roleRights = roleRights;
	}
	public int getRoleParentID() {
		return roleParentID;
	}
	public void setRoleParentID(int roleParentID) {
		this.roleParentID = roleParentID;
	}
	public String getRoleRemark() {
		return roleRemark;
	}
	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}
	public int getRoleCreateUserID() {
		return roleCreateUserID;
	}
	public void setRoleCreateUserID(int roleCreateUserID) {
		this.roleCreateUserID = roleCreateUserID;
	}
	public String getRoleAddRights() {
		return roleAddRights;
	}
	public void setRoleAddRights(String roleAddRights) {
		this.roleAddRights = roleAddRights;
	}
	public String getRoleDeleteRights() {
		return roleDeleteRights;
	}
	public void setRoleDeleteRights(String roleDeleteRights) {
		this.roleDeleteRights = roleDeleteRights;
	}
	public String getRoleEditRights() {
		return roleEditRights;
	}
	public void setRoleEditRights(String roleEditRights) {
		this.roleEditRights = roleEditRights;
	}
	public String getRoleSeeRights() {
		return roleSeeRights;
	}
	public void setRoleSeeRights(String roleSeeRights) {
		this.roleSeeRights = roleSeeRights;
	}
	
	@Override
	public String toString() {
		return "Role [roleID=" + roleID + ", roleName=" + roleName + ", roleRights=" + roleRights + ", roleParentID="
				+ roleParentID + ", roleRemark=" + roleRemark + ", roleCreateUserID=" + roleCreateUserID
				+ ", roleAddRights=" + roleAddRights + ", roleDeleteRights=" + roleDeleteRights + ", roleEditRights="
				+ roleEditRights + ", roleSeeRights=" + roleSeeRights + ", getRoleID()=" + getRoleID()
				+ ", getRoleName()=" + getRoleName() + ", getRoleRights()=" + getRoleRights() + ", getRoleParentID()="
				+ getRoleParentID() + ", getRoleRemark()=" + getRoleRemark() + ", getRoleCreateUserID()="
				+ getRoleCreateUserID() + ", getRoleAddRights()=" + getRoleAddRights() + ", getRoleDeleteRights()="
				+ getRoleDeleteRights() + ", getRoleEditRights()=" + getRoleEditRights() + ", getRoleSeeRights()="
				+ getRoleSeeRights() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
