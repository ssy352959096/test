package com.aurora.entity.dcc;

import java.io.Serializable;

/**
 * @Title: Contacts.java 
 * @Package  com.aurora.entity.dcc.supplier
 * @Description:  企业联系人实体类
 * @author KJH  
 * @date 2018年6月25日 下午2:59:46 
 * @version V1.0
 */
public class Contacts implements Serializable{

	/**
	 * @Title: long
	 * @Description:
	 * @param  
	 * @return
	 * @author KJH
	 * @date 2018年6月25日 下午3:11:57
	 */
	
	private static final long serialVersionUID = -4726045331710371527L;

	/**
	 * 联系人id
	 */
	private Integer contactsId;
	/**
	 * 企业id
	 */
	private Integer enterpriseId;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 电话
	 */
	private String telephone;
	/**
	 * 职务
	 */
	private String post;
	/**
	 * 邮箱
	 */
	private String e_mail;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 微信
	 */
	private String weixin;
	/**
	 * QQ
	 */
	private String qq;
	/**
	 * 网站
	 */
	private String enterpriseWebsite;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 创建时间
	 */
	private String updateTime;
	/**
	 * 修改人
	 */
	private String updateName;
	
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public Integer getContactsId() {
		return contactsId;
	}
	public void setContactsId(Integer contactsId) {
		this.contactsId = contactsId;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEnterpriseWebsite() {
		return enterpriseWebsite;
	}
	public void setEnterpriseWebsite(String enterpriseWebsite) {
		this.enterpriseWebsite = enterpriseWebsite;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	@Override
	public String toString() {
		return "Contacts [contactsId=" + contactsId + ", enterpriseId=" + enterpriseId + ", contacts=" + contacts
				+ ", post=" + post + ", e_mail=" + e_mail + ", fax=" + fax + ", weixin=" + weixin + ", qq=" + qq
				+ ", enterpriseWebsite=" + enterpriseWebsite + ", remarks=" + remarks + ", updateTime=" + updateTime
				+ ", updateName=" + updateName + "]";
	}
	
}
