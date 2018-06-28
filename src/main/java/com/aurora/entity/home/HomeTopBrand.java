package com.aurora.entity.home;

import java.io.Serializable;

/**
 * @Title: HomeTopBrand.java 
 * @Package com.aurora.entity.home
 * @Description: 商城首页热门品牌
 * @author BYG 
 * @date 2018年5月4日 
 * @version 2.0
 */
public class HomeTopBrand implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -3368812312829672922L;
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 页码
	 */
	private Integer pageNumber;
	/**
	 * 位置
	 */
	private Integer seat;
	/**
	 * 品牌ID
	 */
	private Integer brandID;
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	/**
	 * 品牌展示图（仅当前页第一个有）
	 */
	private String brandShowMap;
	/**
	 * 操作者
	 */
	private String operator;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 返回  id
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置  id
	 *
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 返回  pageNumber
	 *
	 * @return the pageNumber
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}
	/**
	 * 设置  pageNumber
	 *
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * 返回  seat
	 *
	 * @return the seat
	 */
	public Integer getSeat() {
		return seat;
	}
	/**
	 * 设置  seat
	 *
	 * @param seat the seat to set
	 */
	public void setSeat(Integer seat) {
		this.seat = seat;
	}
	/**
	 * 返回  brandID
	 *
	 * @return the brandID
	 */
	public Integer getBrandID() {
		return brandID;
	}
	/**
	 * 设置  brandID
	 *
	 * @param brandID the brandID to set
	 */
	public void setBrandID(Integer brandID) {
		this.brandID = brandID;
	}
	/**
	 * 返回  brandName
	 *
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}
	/**
	 * 设置  brandName
	 *
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	/**
	 * 返回  brandShowMap
	 *
	 * @return the brandShowMap
	 */
	public String getBrandShowMap() {
		return brandShowMap;
	}
	/**
	 * 设置  brandShowMap
	 *
	 * @param brandShowMap the brandShowMap to set
	 */
	public void setBrandShowMap(String brandShowMap) {
		this.brandShowMap = brandShowMap;
	}
	/**
	 * 返回  operator
	 *
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * 设置  operator
	 *
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * 返回  updateTime
	 *
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置  updateTime
	 *
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 返回  serialversionuid
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HomeTopBrand [id=" + id + ", pageNumber=" + pageNumber + ", seat=" + seat + ", brandID=" + brandID
				+ ", brandName=" + brandName + ", brandShowMap=" + brandShowMap + ", operator=" + operator
				+ ", updateTime=" + updateTime + ", getId()=" + getId() + ", getPageNumber()=" + getPageNumber()
				+ ", getSeat()=" + getSeat() + ", getBrandID()=" + getBrandID() + ", getBrandName()=" + getBrandName()
				+ ", getBrandShowMap()=" + getBrandShowMap() + ", getOperator()=" + getOperator() + ", getUpdateTime()="
				+ getUpdateTime() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
