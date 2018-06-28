package com.aurora.entity.dcc;

import java.io.Serializable;

/**
 * @Title: Enterprise.java
 * @Package com.aurora.entity.dcc.supplier
 * @Description: 企业相关信息实体类
 * @author KJH
 * @date 2018年6月25日 下午2:58:23
 * @version V1.0
 */
public class Enterprise implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7562595633899455563L;
	/**
	 * 企业id
	 */
	private Integer enterpriseId;
	/**
	 * 优势品牌
	 */
	private String advantageBrand;
	/**
	 * 企业名称
	 */
	private String enterpriseName;
	/**
	 * 产品小类
	 */
	private String productSubclass;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 地区
	 */
	private String region;
	/**
	 * 街道
	 */
	private String street;
	/**
	 * 采购人
	 */
	private String purchaseUser;
	/**
	 * 账期开始时间
	 */
	private String accountPeriodStar;
	/**
	 * 账期结束时间
	 */
	private String accountPeriodEnd;
	/**
	 * 货币
	 */
	private Integer currency;
	/**
	 * 贸易方式
	 */
	private Integer trandMode;
	/**
	 * 合作程度
	 */
	private Integer cooperationDegree;
	/**
	 * 创建时间
	 */
	private String updateTime;
	/**
	 * 修改人
	 */
	private String updateName;

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getAdvantageBrand() {
		return advantageBrand;
	}

	public void setAdvantageBrand(String advantageBrand) {
		this.advantageBrand = advantageBrand;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getProductSubclass() {
		return productSubclass;
	}

	public void setProductSubclass(String productSubclass) {
		this.productSubclass = productSubclass;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPurchaseUser() {
		return purchaseUser;
	}

	public void setPurchaseUser(String purchaseUser) {
		this.purchaseUser = purchaseUser;
	}

	public Integer getCurrency() {
		return currency;
	}

	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	public Integer getTrandMode() {
		return trandMode;
	}

	public void setTrandMode(Integer trandMode) {
		this.trandMode = trandMode;
	}

	public Integer getCooperationDegree() {
		return cooperationDegree;
	}

	public void setCooperationDegree(Integer cooperationDegree) {
		this.cooperationDegree = cooperationDegree;
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

	public String getAccountPeriodStar() {
		return accountPeriodStar;
	}

	public void setAccountPeriodStar(String accountPeriodStar) {
		this.accountPeriodStar = accountPeriodStar;
	}

	public String getAccountPeriodEnd() {
		return accountPeriodEnd;
	}

	public void setAccountPeriodEnd(String accountPeriodEnd) {
		this.accountPeriodEnd = accountPeriodEnd;
	}

	@Override
	public String toString() {
		return "Enterprise [enterpriseId=" + enterpriseId + ", advantageBrand=" + advantageBrand + ", enterpriseName="
				+ enterpriseName + ", productSubclass=" + productSubclass + ", country=" + country + ", region="
				+ region + ", street=" + street + ", purchaseUser=" + purchaseUser + ", accountPeriodStar="
				+ accountPeriodStar + ", accountPeriodEnd=" + accountPeriodEnd + ", currency=" + currency
				+ ", trandMode=" + trandMode + ", cooperationDegree=" + cooperationDegree + ", updateTime=" + updateTime
				+ ", updateName=" + updateName + "]";
	}

}
