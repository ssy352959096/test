package com.aurora.entity;

import java.io.Serializable;

public class User implements Serializable{
	
	/**
	 * 描述: 客户实体类
	 * 创建: BYG 2017/5/25
	 * 修改:
	 * @version 1.0
	 */
	private static final long serialVersionUID = 1L;
		private int userID;						//用户ID
		private String userRealName;			//用户姓名
		private String userName;				//用户名
		private String userMobile;				//手机号
		private String userPassword;	 		//密码
		private String userEmail;				//邮箱
		private String userRights;				//权限
		private String userIP;					//用户登陆IPַ
		private String userStatus;				//1在线；2离线；4禁用̬
		private String userLastLoginTime;		//最后登陆时间
		private String createTime;				//创建时间
		private int userRoleID;					//角色ID
		private Role userRole;					//角色对象
		private Page page;						//分页对象
		
		public int getUserID() {
			return userID;
		}
		public void setUserID(int userID) {
			this.userID = userID;
		}
		public String getUserRealName() {
			return userRealName;
		}
		public void setUserRealName(String userRealName) {
			this.userRealName = userRealName;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUserMobile() {
			return userMobile;
		}
		public void setUserMobile(String userMobile) {
			this.userMobile = userMobile;
		}
		public String getUserPassword() {
			return userPassword;
		}
		public void setUserPassword(String userPassword) {
			this.userPassword = userPassword;
		}
		public String getUserEmail() {
			return userEmail;
		}
		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}
		public String getUserRights() {
			return userRights;
		}
		public void setUserRights(String userRights) {
			this.userRights = userRights;
		}
		public String getUserIP() {
			return userIP;
		}
		public void setUserIP(String userIP) {
			this.userIP = userIP;
		}
		public String getUserStatus() {
			return userStatus;
		}
		public void setUserStatus(String userStatus) {
			this.userStatus = userStatus;
		}
		public String getUserLastLoginTime() {
			return userLastLoginTime;
		}
		public void setUserLastLoginTime(String userLastLoginTime) {
			this.userLastLoginTime = userLastLoginTime;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public int getUserRoleID() {
			return userRoleID;
		}
		public void setUserRoleID(int userRoleID) {
			this.userRoleID = userRoleID;
		}
		public Role getUserRole() {
			return userRole;
		}
		public void setUserRole(Role userRole) {
			this.userRole = userRole;
		}
		public Page getPage() {
			return page;
		}
		public void setPage(Page page) {
			this.page = page;
		}
		@Override
		public String toString() {
			return "User [userID=" + userID + ", userRealName=" + userRealName + ", userName=" + userName
					+ ", userMobile=" + userMobile + ", userPassword=" + userPassword + ", userEmail=" + userEmail
					+ ", userRights=" + userRights + ", userIP=" + userIP + ", userStatus=" + userStatus
					+ ", userLastLoginTime=" + userLastLoginTime + ", createTime=" + createTime + ", userRoleID="
					+ userRoleID + ", userRole=" + userRole + ", page=" + page + ", getUserID()=" + getUserID()
					+ ", getUserRealName()=" + getUserRealName() + ", getUserName()=" + getUserName()
					+ ", getUserMobile()=" + getUserMobile() + ", getUserPassword()=" + getUserPassword()
					+ ", getUserEmail()=" + getUserEmail() + ", getUserRights()=" + getUserRights() + ", getUserIP()="
					+ getUserIP() + ", getUserStatus()=" + getUserStatus() + ", getUserLastLoginTime()="
					+ getUserLastLoginTime() + ", getCreateTime()=" + getCreateTime() + ", getUserRoleID()="
					+ getUserRoleID() + ", getUserRole()=" + getUserRole() + ", getPage()=" + getPage()
					+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
					+ "]";
		}

		
}
