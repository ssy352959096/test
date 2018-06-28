package com.aurora.entity.home;

import java.io.Serializable;

/**
 * @Title: HotSell.java 
 * @Package com.aurora.entity.home
 * @Description: 商城首页本站热卖表实体类
 * @author BYG 
 * @date 2018年4月27日 
 * @version 2.0
 */
public class HotSell implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1579594619085977934L;

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
		return "HotSell [id=" + id + ", titleID=" + titleID + ", titleName=" + titleName + ", seat=" + seat
				+ ", goodsID=" + goodsID + ", goodsShowName=" + goodsShowName + ", goodsShowMap=" + goodsShowMap
				+ ", operator=" + operator + ", updateTime=" + updateTime + ", getId()=" + getId() + ", getTitleID()="
				+ getTitleID() + ", getTitleName()=" + getTitleName() + ", getSeat()=" + getSeat() + ", getGoodsID()="
				+ getGoodsID() + ", getGoodsShowName()=" + getGoodsShowName() + ", getGoodsShowMap()="
				+ getGoodsShowMap() + ", getOperator()=" + getOperator() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
