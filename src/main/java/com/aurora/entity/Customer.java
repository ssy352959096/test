package com.aurora.entity;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable{
	
	/** 客户实体类
	 * @author BYG 2017-7-21
	 * @version 1.0
	 */
	private static final long serialVersionUID = 1L;
		private String customerID;					//客户ID 而特务
		private String customerName;				//客户名
		private String customerSex;					//性别
		private String customerMobile;				//手机号
		transient private String customerEmail;		//邮箱
		transient private String customerPassword;	//密码
		private Date customerBirthday;				//生日
		private int customerQQ;						//QQ
		private String customerWeChat;				//微信
		private String customerIP;					//IPַ
		private String customerStatus;				//1可用；4禁用̬
		private Date customerLastLoginTime;			//最后登陆时间
		private Date customerRegisterTime;			//创建时间
		public String getCustomerID() {
			return customerID;
		}
		public void setCustomerID(String customerID) {
			this.customerID = customerID;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getCustomerSex() {
			return customerSex;
		}
		public void setCustomerSex(String customerSex) {
			this.customerSex = customerSex;
		}
		public String getCustomerMobile() {
			return customerMobile;
		}
		public void setCustomerMobile(String customerMobile) {
			this.customerMobile = customerMobile;
		}
		public String getCustomerPassword() {
			return customerPassword;
		}
		public void setCustomerPassword(String customerPassword) {
			this.customerPassword = customerPassword;
		}
		public String getCustomerEmail() {
			return customerEmail;
		}
		public void setCustomerEmail(String customerEmail) {
			this.customerEmail = customerEmail;
		}
		public Date getCustomerBirthday() {
			return customerBirthday;
		}
		public void setCustomerBirthday(Date customerBirthday) {
			this.customerBirthday = customerBirthday;
		}
		public int getCustomerQQ() {
			return customerQQ;
		}
		public void setCustomerQQ(int customerQQ) {
			this.customerQQ = customerQQ;
		}
		public String getCustomerWeChat() {
			return customerWeChat;
		}
		public void setCustomerWeChat(String customerWeChat) {
			this.customerWeChat = customerWeChat;
		}
		public String getCustomerIP() {
			return customerIP;
		}
		public void setCustomerIP(String customerIP) {
			this.customerIP = customerIP;
		}
		public String getCustomerStatus() {
			return customerStatus;
		}
		public void setCustomerStatus(String customerStatus) {
			this.customerStatus = customerStatus;
		}
		public Date getCustomerLastLoginTime() {
			return customerLastLoginTime;
		}
		public void setCustomerLastLoginTime(Date customerLastLoginTime) {
			this.customerLastLoginTime = customerLastLoginTime;
		}
		public Date getCustomerRegisterTime() {
			return customerRegisterTime;
		}
		public void setCustomerRegisterTime(Date customerRegisterTime) {
			this.customerRegisterTime = customerRegisterTime;
		}
		
		@Override
		public String toString() {
			return "Customer [customerID=" + customerID + ", customerName=" + customerName + ", customerSex="
					+ customerSex + ", customerMobile=" + customerMobile + ", customerPassword=" + customerPassword
					+ ", customerEmail=" + customerEmail + ", customerBirthday=" + customerBirthday + ", customerQQ="
					+ customerQQ + ", customerWeChat=" + customerWeChat + ", customerIP=" + customerIP
					+ ", customerStatus=" + customerStatus + ", customerLastLoginTime=" + customerLastLoginTime
					+ ", customerRegisterTime=" + customerRegisterTime + ", getCustomerID()=" + getCustomerID()
					+ ", getCustomerName()=" + getCustomerName() + ", getCustomerSex()=" + getCustomerSex()
					+ ", getCustomerMobile()=" + getCustomerMobile() + ", getCustomerPassword()="
					+ getCustomerPassword() + ", getCustomerEmail()=" + getCustomerEmail() + ", getCustomerBirthday()="
					+ getCustomerBirthday() + ", getCustomerQQ()=" + getCustomerQQ() + ", getCustomerWeChat()="
					+ getCustomerWeChat() + ", getCustomerIP()=" + getCustomerIP() + ", getCustomerStatus()="
					+ getCustomerStatus() + ", getCustomerLastLoginTime()=" + getCustomerLastLoginTime()
					+ ", getCustomerRegisterTime()=" + getCustomerRegisterTime() + ", getClass()=" + getClass()
					+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
		}
	
		
}
