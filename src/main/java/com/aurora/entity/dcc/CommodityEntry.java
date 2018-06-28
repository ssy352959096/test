package com.aurora.entity.dcc;

import java.io.Serializable;

/**
 * @Title: CommodityEntry.java 
 * @Package  com.aurora.entity.dcc.commodity
 * @Description:  商品录入实体类
 * @author KJH  
 * @date 2018年6月26日 下午2:19:54 
 * @version V1.0
 */
public class CommodityEntry implements Serializable{

	/**
	 *序列id
	 */
	private static final long serialVersionUID = -8816496390230722957L;
	private Integer commodityId;
	/**
	 * 品牌名字
	 */
	private String brandName;
	/**
	 * 	小类名字
	 */
	private String smallClassName;
	/**
	 * 商品名字
	 */
	private String commodityName;
	/**
	 * 单位
	 */
	private String company;
	/**
	 * 原产地
	 */
	private String provenance;
	/**
	 * EAN
	 */
	private String EAN;
	/**
	 * SPEC
	 */
	private String SPEC;
	/**
	 * 主要供应商
	 */
	private String majorSupplier;
	/**
	 * 类目归属
	 */
	private String categoryAttribution;
	/**
	 * 籍规
	 */
	private String regulations;
	/**
	 * 托规
	 */
	private String gage;
	/**
	 * 图片url1
	 */
	private String phoneUrl1;
	/**
	 * 图片url2
	 */
	private String phoneUrl2;
	/**
	 * 图片url3
	 */
	private String phoneUrl3;
	/**
	 * 图片url4
	 */
	private String phoneUrl4;
	/**
	 * 图片url5
	 */
	private String phoneUrl5;
	/**
	 * 维护人
	 */
	private String maintainUser;
	/**
	 * 修改人
	 */
	private String updateName;
	/**
	 * 修改时间
	 */
	private String updateTime;
	
	
	public Integer getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getSmallClassName() {
		return smallClassName;
	}
	public void setSmallClassName(String smallClassName) {
		this.smallClassName = smallClassName;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getProvenance() {
		return provenance;
	}
	public void setProvenance(String provenance) {
		this.provenance = provenance;
	}
	public String getEAN() {
		return EAN;
	}
	public void setEAN(String eAN) {
		EAN = eAN;
	}
	public String getSPEC() {
		return SPEC;
	}
	public void setSPEC(String sPEC) {
		SPEC = sPEC;
	}
	public String getMajorSupplier() {
		return majorSupplier;
	}
	public void setMajorSupplier(String majorSupplier) {
		this.majorSupplier = majorSupplier;
	}
	public String getCategoryAttribution() {
		return categoryAttribution;
	}
	public void setCategoryAttribution(String categoryAttribution) {
		this.categoryAttribution = categoryAttribution;
	}
	public String getRegulations() {
		return regulations;
	}
	public void setRegulations(String regulations) {
		this.regulations = regulations;
	}
	public String getGage() {
		return gage;
	}
	public void setGage(String gage) {
		this.gage = gage;
	}
	public String getPhoneUrl1() {
		return phoneUrl1;
	}
	public void setPhoneUrl1(String phoneUrl1) {
		this.phoneUrl1 = phoneUrl1;
	}
	public String getPhoneUrl2() {
		return phoneUrl2;
	}
	public void setPhoneUrl2(String phoneUrl2) {
		this.phoneUrl2 = phoneUrl2;
	}
	public String getPhoneUrl3() {
		return phoneUrl3;
	}
	public void setPhoneUrl3(String phoneUrl3) {
		this.phoneUrl3 = phoneUrl3;
	}
	public String getPhoneUrl4() {
		return phoneUrl4;
	}
	public void setPhoneUrl4(String phoneUrl4) {
		this.phoneUrl4 = phoneUrl4;
	}
	public String getPhoneUrl5() {
		return phoneUrl5;
	}
	public void setPhoneUrl5(String phoneUrl5) {
		this.phoneUrl5 = phoneUrl5;
	}
	public String getMaintainUser() {
		return maintainUser;
	}
	public void setMaintainUser(String maintainUser) {
		this.maintainUser = maintainUser;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CommodityEntry [commodityId=" + commodityId + ", brandName=" + brandName + ", smallClassName="
				+ smallClassName + ", commodityName=" + commodityName + ", company=" + company + ", provenance="
				+ provenance + ", EAN=" + EAN + ", SPEC=" + SPEC + ", majorSupplier=" + majorSupplier
				+ ", categoryAttribution=" + categoryAttribution + ", regulations=" + regulations + ", gage=" + gage
				+ ", phoneUrl1=" + phoneUrl1 + ", phoneUrl2=" + phoneUrl2 + ", phoneUrl3=" + phoneUrl3 + ", phoneUrl4="
				+ phoneUrl4 + ", phoneUrl5=" + phoneUrl5 + ", maintainUser=" + maintainUser + ", updateName="
				+ updateName + ", updateTime=" + updateTime + "]";
	}
	
	
	
}
