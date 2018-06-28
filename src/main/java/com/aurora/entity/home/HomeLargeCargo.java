package com.aurora.entity.home;

import java.io.Serializable;

/**
 * @Title: Category.java 
 * @Package com.aurora.entity.home
 * @Description: 商城首页导航栏类目
 * @author BYG 
 * @date 2018年4月27日 
 * @version 2.0
 */
public class HomeLargeCargo implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 7867657705762070798L;

	/**
	 * 表记录id
	 */
	private Integer id;
	/**
	 * 类目id
	 */
	private Integer categoryID;
	/**
	 * 类目id
	 */
	private String categoryName;
	/**
	 * 商品ID
	 */
	private String goodsID;
	/**
	 * 商品名称
	 */
	private String goodsShowName;
	
	/**
	 * 商品条码
	 */
	private String goodsCode;
	
	/**
	 * 商品货币符号
	 */
	private String currency;
	
	/**
	 * 提货价
	 */
	private String price;
	/**
	 * 规格
	 */
	private String norm;
	/**
	 * 起订量
	 */
	private Integer minNum;
	
	/**
	 * 有效期
	 */
	private String period;
	/**
	 * 交货地址
	 */
	private String deliveryAddress;
	/**
	 * 是否加HOT标志，1是；2否；默认2
	 */
	private Integer hot;
	/**
	 * 供应商
	 */
	private String supplier;
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
	 * 返回  goodsID
	 *
	 * @return the goodsID
	 */
	public String getGoodsID() {
		return goodsID;
	}
	/**
	 * 设置  goodsID
	 *
	 * @param goodsID the goodsID to set
	 */
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	/**
	 * 返回  goodsShowName
	 *
	 * @return the goodsShowName
	 */
	public String getGoodsShowName() {
		return goodsShowName;
	}
	/**
	 * 设置  goodsShowName
	 *
	 * @param goodsShowName the goodsShowName to set
	 */
	public void setGoodsShowName(String goodsShowName) {
		this.goodsShowName = goodsShowName;
	}
	/**
	 * 返回  goodsCode
	 *
	 * @return the goodsCode
	 */
	public String getGoodsCode() {
		return goodsCode;
	}
	/**
	 * 设置  goodsCode
	 *
	 * @param goodsCode the goodsCode to set
	 */
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	/**
	 * 返回  price
	 *
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * 设置  price
	 *
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * 返回  norm
	 *
	 * @return the norm
	 */
	public String getNorm() {
		return norm;
	}
	/**
	 * 设置  norm
	 *
	 * @param norm the norm to set
	 */
	public void setNorm(String norm) {
		this.norm = norm;
	}
	/**
	 * 返回  minNum
	 *
	 * @return the minNum
	 */
	public Integer getMinNum() {
		return minNum;
	}
	/**
	 * 设置  minNum
	 *
	 * @param minNum the minNum to set
	 */
	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}
	/**
	 * 返回  period
	 *
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}
	/**
	 * 设置  period
	 *
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}
	/**
	 * 返回  deliveryAddress
	 *
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	/**
	 * 设置  deliveryAddress
	 *
	 * @param deliveryAddress the deliveryAddress to set
	 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	/**
	 * 返回  hot
	 *
	 * @return the hot
	 */
	public Integer getHot() {
		return hot;
	}
	/**
	 * 设置  hot
	 *
	 * @param hot the hot to set
	 */
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	/**
	 * 返回  supplier
	 *
	 * @return the supplier
	 */
	public String getSupplier() {
		return supplier;
	}
	/**
	 * 设置  supplier
	 *
	 * @param supplier the supplier to set
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
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
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HomeLargeCargo [id=" + id + ", categoryID=" + categoryID + ", goodsID=" + goodsID
				+ ", goodsShowName=" + goodsShowName + ", goodsCode=" + goodsCode + ", price=" + price + ", norm="
				+ norm + ", minNum=" + minNum + ", period=" + period + ", deliveryAddress=" + deliveryAddress + ", hot="
				+ hot + ", supplier=" + supplier + ", operator=" + operator + ", updateTime=" + updateTime
				+ ", getId()=" + getId() + ", getCategoryID()=" + getCategoryID()
				+ ", getGoodsID()=" + getGoodsID() + ", getGoodsShowName()=" + getGoodsShowName() + ", getGoodsCode()="
				+ getGoodsCode() + ", getPrice()=" + getPrice() + ", getNorm()=" + getNorm() + ", getMinNum()="
				+ getMinNum() + ", getPeriod()=" + getPeriod() + ", getDeliveryAddress()=" + getDeliveryAddress()
				+ ", getHot()=" + getHot() + ", getSupplier()=" + getSupplier() + ", getOperator()=" + getOperator()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
