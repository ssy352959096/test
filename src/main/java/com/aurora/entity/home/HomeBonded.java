package com.aurora.entity.home;

import java.io.Serializable;

/**
 * @Title: HomeBonded.java 
 * @Package com.aurora.entity.home 
 * @Description: 首页保税仓 
 * @author SSY  
 * @date 2018年5月2日 上午10:06:49 
 * @version V1.0
 */
public class HomeBonded implements Serializable{
 
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4792021073869437864L;
	
	/**
	 * 活动id
	 */
	private Integer id;
	/**
	 * 商品id
	 */
	private String goodsID;
	/**
	 * 首页宣传名称
	 */
	private String goodsNewName;
	
	/**
	 * 位置
	 */
	private Integer location;
	/**
	 * 白底图,首页白底图
	 */
	private String homeMap;
	 
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 更新者
	 */
	private String updator;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsNewName() {
		return goodsNewName;
	}
	public void setGoodsNewName(String goodsNewName) {
		this.goodsNewName = goodsNewName;
	}
	public Integer getLocation() {
		return location;
	}
	public void setLocation(Integer location) {
		this.location = location;
	}
	public String getHomeMap() {
		return homeMap;
	}
	public void setHomeMap(String homeMap) {
		this.homeMap = homeMap;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}
	@Override
	public String toString() {
		return "HomeBonded [id=" + id + ", goodsID=" + goodsID + ", goodsNewName=" + goodsNewName + ", location="
				+ location + ", homeMap=" + homeMap + ", updateTime=" + updateTime + ", updator=" + updator + "]";
	}
	 
	
}
