package com.aurora.entity.home;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: Category.java 
 * @Package com.aurora.entity.home
 * @Description: 商城首页导航栏类目
 * @author BYG 
 * @date 2018年4月27日 
 * @version 2.0
 */
public class Category implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -4272702048150121183L;
	/**
	 * 类目id
	 */
	private Integer categoryID;
	/**
	 * 类目名称
	 */
	private String categoryName;
	/**
	 * 类目级别
	 */
	private Integer categoryLevel;
	/**
	 * 类目父级类目id
	 */
	private Integer categoryParentID;
	/**
	 * 位置
	 */
	private Integer locationSort;
	
	/**
	 * 是否标红，1标红；2不标红；默认2
	 */
	private Integer red;
	/**
	 * 子类目
	 */
	private List<Category> subcategory;
	/**
	 * 操作者
	 */
	private String operator;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 返回  categoryID
	 *
	 * @return the categoryID
	 */
	public Integer getCategoryID() {
		return categoryID;
	}
	/**
	 * 设置  categoryID
	 *
	 * @param categoryID the categoryID to set
	 */
	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}
	/**
	 * 返回  categoryName
	 *
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置  categoryName
	 *
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 返回  categoryLevel
	 *
	 * @return the categoryLevel
	 */
	public Integer getCategoryLevel() {
		return categoryLevel;
	}
	/**
	 * 设置  categoryLevel
	 *
	 * @param categoryLevel the categoryLevel to set
	 */
	public void setCategoryLevel(Integer categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
	/**
	 * 返回  categoryParentID
	 *
	 * @return the categoryParentID
	 */
	public Integer getCategoryParentID() {
		return categoryParentID;
	}
	/**
	 * 设置  categoryParentID
	 *
	 * @param categoryParentID the categoryParentID to set
	 */
	public void setCategoryParentID(Integer categoryParentID) {
		this.categoryParentID = categoryParentID;
	}
	/**
	 * 返回  locationSort
	 *
	 * @return the locationSort
	 */
	public Integer getLocationSort() {
		return locationSort;
	}
	/**
	 * 设置  locationSort
	 *
	 * @param locationSort the locationSort to set
	 */
	public void setLocationSort(Integer locationSort) {
		this.locationSort = locationSort;
	}
	/**
	 * 返回  red
	 *
	 * @return the red
	 */
	public Integer getRed() {
		return red;
	}
	/**
	 * 设置  red
	 *
	 * @param red the red to set
	 */
	public void setRed(Integer red) {
		this.red = red;
	}
	/**
	 * 返回  subcategory
	 *
	 * @return the subcategory
	 */
	public List<Category> getSubcategory() {
		return subcategory;
	}
	/**
	 * 设置  subcategory
	 *
	 * @param subcategory the subcategory to set
	 */
	public void setSubcategory(List<Category> subcategory) {
		this.subcategory = subcategory;
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
		return "Category [categoryID=" + categoryID + ", categoryName=" + categoryName + ", categoryLevel="
				+ categoryLevel + ", categoryParentID=" + categoryParentID + ", locationSort=" + locationSort + ", red="
				+ red + ", subcategory=" + subcategory + ", operator=" + operator + ", updateTime=" + updateTime
				+ ", getCategoryID()=" + getCategoryID() + ", getCategoryName()=" + getCategoryName()
				+ ", getCategoryLevel()=" + getCategoryLevel() + ", getCategoryParentID()=" + getCategoryParentID()
				+ ", getLocationSort()=" + getLocationSort() + ", getRed()=" + getRed() + ", getSubcategory()="
				+ getSubcategory() + ", getOperator()=" + getOperator() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

}
