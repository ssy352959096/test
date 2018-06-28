package com.aurora.entity.dcc;

import java.io.Serializable;

/**
 * @Title: BrandRulesSetting.java 
 * @Package  com.aurora.entity.dcc.brand
 * @Description:  品名规则设定实体类
 * @author KJH  
 * @date 2018年6月21日 下午6:37:35 
 * @version V1.0
 */
public class BrandRulesSetting implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8782271347534166973L;
	/**
	 * 品牌设定id
	 */
	private Integer brandId;
	/**
	 * 品牌编号
	 */
	private String brandNo;
	/**
	 * 中文名称
	 */
	private String brandCname;
	/**
	 * 外文名称
	 */
	private String brandEname;
	/**
	 * 品牌状态
	 */
	private Integer brandState;
	/**
	 * 创建时间
	 */
	private String updateTime;
	/**
	 * 修改人
	 */
	private String updateName;

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandCname() {
		return brandCname;
	}

	public void setBrandCname(String brandCname) {
		this.brandCname = brandCname;
	}

	public String getBrandEname() {
		return brandEname;
	}

	public void setBrandEname(String brandEname) {
		this.brandEname = brandEname;
	}

	public Integer getBrandState() {
		return brandState;
	}

	public void setBrandState(Integer brandState) {
		this.brandState = brandState;
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
		return "BrandRulesSetting [brandId=" + brandId + ", brandNo=" + brandNo + ", brandCname=" + brandCname
				+ ", brandEname=" + brandEname + "brandState=" + brandState + "updateTime=" + updateTime + "updateName="
				+ updateName + ", toString()=" + super.toString() + "]";
		
	}
}
