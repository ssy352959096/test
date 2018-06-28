package com.aurora.entity.home;

import java.io.Serializable;

/**
 * @Title: HomeDirectPost.java 
 * @Package com.aurora.entity.home
 * @Description: 商城首页海外直邮实体类
 * @author BYG 
 * @date 2018年4月27日 
 * @version 2.0
 */
public class HomeDirectPost implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 4077220333740643010L;
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 标题唯一标识符
	 */
	private Integer titleID;
	/**
	 * 标题名称
	 */
	private String titleName;
	/**
	 * 类型：1-banner；2-商品
	 */
	private Integer type;
	/**
	 * banner图
	 */
	private String banner;
	/**
	 * 位置
	 */
	private Integer seat;
	/**
	 * 商品ID
	 */
	private String goodsID;
	
	/**
	 * 商品展示名称
	 */
	private String goodsShowName;
	/**
	 * 商品图
	 */
	private String goodsShowMap;
	/**
	 * 关键词
	 */
	private String keywords;
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
	 * 返回  titleID
	 *
	 * @return the titleID
	 */
	public Integer getTitleID() {
		return titleID;
	}
	/**
	 * 设置  titleID
	 *
	 * @param titleID the titleID to set
	 */
	public void setTitleID(Integer titleID) {
		this.titleID = titleID;
	}
	/**
	 * 返回  titleName
	 *
	 * @return the titleName
	 */
	public String getTitleName() {
		return titleName;
	}
	/**
	 * 设置  titleName
	 *
	 * @param titleName the titleName to set
	 */
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	/**
	 * 返回  type
	 *
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置  type
	 *
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 返回  banner
	 *
	 * @return the banner
	 */
	public String getBanner() {
		return banner;
	}
	/**
	 * 设置  banner
	 *
	 * @param banner the banner to set
	 */
	public void setBanner(String banner) {
		this.banner = banner;
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
	 * 返回  goodsShowMap
	 *
	 * @return the goodsShowMap
	 */
	public String getGoodsShowMap() {
		return goodsShowMap;
	}
	/**
	 * 设置  goodsShowMap
	 *
	 * @param goodsShowMap the goodsShowMap to set
	 */
	public void setGoodsShowMap(String goodsShowMap) {
		this.goodsShowMap = goodsShowMap;
	}
	/**
	 * 返回  keywords
	 *
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * 设置  keywords
	 *
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
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
		return "HomeDirectPost [id=" + id + ", titleID=" + titleID + ", titleName=" + titleName + ", type=" + type
				+ ", banner=" + banner + ", seat=" + seat + ", goodsID=" + goodsID + ", goodsShowName=" + goodsShowName
				+ ", goodsShowMap=" + goodsShowMap + ", keywords=" + keywords + ", operator=" + operator
				+ ", updateTime=" + updateTime + ", getId()=" + getId() + ", getTitleID()=" + getTitleID()
				+ ", getTitleName()=" + getTitleName() + ", getType()=" + getType() + ", getBanner()=" + getBanner()
				+ ", getSeat()=" + getSeat() + ", getGoodsID()=" + getGoodsID() + ", getGoodsShowName()="
				+ getGoodsShowName() + ", getGoodsShowMap()=" + getGoodsShowMap() + ", getKeywords()=" + getKeywords()
				+ ", getOperator()=" + getOperator() + ", getUpdateTime()=" + getUpdateTime() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
