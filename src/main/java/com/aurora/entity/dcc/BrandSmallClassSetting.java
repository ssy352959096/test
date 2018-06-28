package com.aurora.entity.dcc;

import java.io.Serializable;

/**
 * @Title: BrandSmallClassSetting.java
 * @Package com.aurora.entity.dcc.brand
 * @Description:
 * @author KJH
 * @date 2018年6月21日 下午6:36:59
 * @version V1.0
 */
public class BrandSmallClassSetting implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2107525035310701508L;	
	/**
	 * 商品小类id
	 */
	private Integer smallClassId;
	/**
	 * 商品小类状态
	 */
	private Integer smallClassState;
	/**
	 * 商品品牌id
	 */
	private Integer brandId;
	/**
	 * 商品小类编号
	 */
	private String smallClassNo;
	/**
	 * 商品小类编号
	 */
	private String smallClassName;
	/**
	 * 品牌名称
	 */
	private String commodityName;
	/**
	 * 创建时间
	 */
	private String updateTime;
	/**
	 * 修改人
	 */
	private String updateName;
	
	public Integer getSmallClassId() {
		return smallClassId;
	}
	public void setSmallClassId(Integer smallClassId) {
		this.smallClassId = smallClassId;
	}
	public Integer getSmallClassState() {
		return smallClassState;
	}
	public void setSmallClassState(Integer smallClassState) {
		this.smallClassState = smallClassState;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getSmallClassNo() {
		return smallClassNo;
	}
	public void setSmallClassNo(String smallClassNo) {
		this.smallClassNo = smallClassNo;
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
		return "BrandSmallClassSetting [smallClassId=" + smallClassId + ", smallClassState=" + smallClassState
				+ ", brandId=" + brandId + ", smallClassNo=" + smallClassNo + ", smallClassName=" + smallClassName
				+ ", commodityName=" + commodityName + ", updateTime=" + updateTime + ", updateName=" + updateName
				+ "]";
	}
	
	
}
