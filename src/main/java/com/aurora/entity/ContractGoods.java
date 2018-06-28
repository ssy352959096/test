package com.aurora.entity;

import java.math.BigDecimal;

/**
 * 合同商品实体类
 * @author SSY 2018-1-9
 */
public class ContractGoods {

	/**
	 * 合同商品id
	 */
	private Integer contractGoodsID;
	/**
	 * 合同id
	 */
	private String contractID;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 *  商品条码
	 */
	private String goodsCode;
	/**
	 *  商品品牌
	 */
	private String goodsBrand;
	/**
	 *  商品规格
	 */
	private String goodsNorm;
	/**
	 *  合同商品数量
	 */
	private BigDecimal goodsNum;
	/**
	 *   商品主图
	 */
	private String goodsMap;
	/**
	 * 交货国家
	 */
	private String deliverCountry;
	/**
	 * 交货城市
	 */
	private String deliverCity;
	/**
	 * 贸易方式：1CIF；2FCA；3EXW ; 4.FOB;
	 */
	private Integer tradeType;
	/**
	 * 备货天数
	 */
	private Integer readyTime;
	/**
	 * 物流方式1.海运;2.陆运;3.空运
	 */
	private Integer logisticsType;
	/**
	 * 该商品总体积
	 */
	private BigDecimal volume;
	/**
	 * 一托盘体积
	 */
	private BigDecimal palletVolume;
	/**
	 * 一坨运费
	 */
	private BigDecimal palletPrice;
	/**
	 * 商品邮费
	 */
	private BigDecimal postage;
	/**
	 * 商品单价
	 */
	private BigDecimal goodsPrice;
	/**
	 * 总供货价
	 */
	private BigDecimal supplyPrice;
	/**
	 * 货币符号
	 */
	private String currencySign;
	/**
	 * 货币名称
	 */
	private String currencyName;
	/**
	 * 合同有效期
	 */
	private String validTime;
	public String getValidTime() {
		return validTime;
	}
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}
	/**
	 * 获取: contractGoodsID
	 * @return the contractGoodsID
	 */
	public Integer getContractGoodsID() {
		return contractGoodsID;
	}
	/**
	 * 设置: contractGoodsID
	 * @param contractGoodsID the contractGoodsID to set
	 */
	public void setContractGoodsID(Integer contractGoodsID) {
		this.contractGoodsID = contractGoodsID;
	}
	/**
	 * 获取: contractID
	 * @return the contractID
	 */
	public String getContractID() {
		return contractID;
	}
	/**
	 * 设置: contractID
	 * @param contractID the contractID to set
	 */
	public void setContractID(String contractID) {
		this.contractID = contractID;
	}
	/**
	 * 获取: goodsName
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 设置: goodsName
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 获取: goodsCode
	 * @return the goodsCode
	 */
	public String getGoodsCode() {
		return goodsCode;
	}
	/**
	 * 设置: goodsCode
	 * @param goodsCode the goodsCode to set
	 */
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	/**
	 * 获取: goodsBrand
	 * @return the goodsBrand
	 */
	public String getGoodsBrand() {
		return goodsBrand;
	}
	/**
	 * 设置: goodsBrand
	 * @param goodsBrand the goodsBrand to set
	 */
	public void setGoodsBrand(String goodsBrand) {
		this.goodsBrand = goodsBrand;
	}
	/**
	 * 获取: goodsNorm
	 * @return the goodsNorm
	 */
	public String getGoodsNorm() {
		return goodsNorm;
	}
	/**
	 * 设置: goodsNorm
	 * @param goodsNorm the goodsNorm to set
	 */
	public void setGoodsNorm(String goodsNorm) {
		this.goodsNorm = goodsNorm;
	}
	/**
	 * 获取: goodsNum
	 * @return the goodsNum
	 */
	public BigDecimal getGoodsNum() {
		return goodsNum;
	}
	/**
	 * 设置: goodsNum
	 * @param goodsNum the goodsNum to set
	 */
	public void setGoodsNum(BigDecimal goodsNum) {
		this.goodsNum = goodsNum;
	}
	/**
	 * 获取: goodsMap
	 * @return the goodsMap
	 */
	public String getGoodsMap() {
		return goodsMap;
	}
	/**
	 * 设置: goodsMap
	 * @param goodsMap the goodsMap to set
	 */
	public void setGoodsMap(String goodsMap) {
		this.goodsMap = goodsMap;
	}
	/**
	 * 获取: deliverCountry
	 * @return the deliverCountry
	 */
	public String getDeliverCountry() {
		return deliverCountry;
	}
	/**
	 * 设置: deliverCountry
	 * @param deliverCountry the deliverCountry to set
	 */
	public void setDeliverCountry(String deliverCountry) {
		this.deliverCountry = deliverCountry;
	}
	/**
	 * 获取: deliverCity
	 * @return the deliverCity
	 */
	public String getDeliverCity() {
		return deliverCity;
	}
	/**
	 * 设置: deliverCity
	 * @param deliverCity the deliverCity to set
	 */
	public void setDeliverCity(String deliverCity) {
		this.deliverCity = deliverCity;
	}
	/**
	 * 获取: tradeType
	 * @return the tradeType
	 */
	public Integer getTradeType() {
		return tradeType;
	}
	/**
	 * 设置: tradeType
	 * @param tradeType the tradeType to set
	 */
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
	/**
	 * 获取: readyTime
	 * @return the readyTime
	 */
	public Integer getReadyTime() {
		return readyTime;
	}
	/**
	 * 设置: readyTime
	 * @param readyTime the readyTime to set
	 */
	public void setReadyTime(Integer readyTime) {
		this.readyTime = readyTime;
	}
	/**
	 * 获取: logisticsType
	 * @return the logisticsType
	 */
	public Integer getLogisticsType() {
		return logisticsType;
	}
	/**
	 * 设置: logisticsType
	 * @param logisticsType the logisticsType to set
	 */
	public void setLogisticsType(Integer logisticsType) {
		this.logisticsType = logisticsType;
	}
	/**
	 * 获取: volume
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	/**
	 * 设置: volume
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	/**
	 * 获取: palletVolume
	 * @return the palletVolume
	 */
	public BigDecimal getPalletVolume() {
		return palletVolume;
	}
	/**
	 * 设置: palletVolume
	 * @param palletVolume the palletVolume to set
	 */
	public void setPalletVolume(BigDecimal palletVolume) {
		this.palletVolume = palletVolume;
	}
	/**
	 * 获取: palletPrice
	 * @return the palletPrice
	 */
	public BigDecimal getPalletPrice() {
		return palletPrice;
	}
	/**
	 * 设置: palletPrice
	 * @param palletPrice the palletPrice to set
	 */
	public void setPalletPrice(BigDecimal palletPrice) {
		this.palletPrice = palletPrice;
	}
	/**
	 * 获取: postage
	 * @return the postage
	 */
	public BigDecimal getPostage() {
		return postage;
	}
	/**
	 * 设置: postage
	 * @param postage the postage to set
	 */
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	/**
	 * 获取: goodsPrice
	 * @return the goodsPrice
	 */
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	/**
	 * 设置: goodsPrice
	 * @param goodsPrice the goodsPrice to set
	 */
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	/**
	 * 获取: supplyPrice
	 * @return the supplyPrice
	 */
	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}
	/**
	 * 设置: supplyPrice
	 * @param supplyPrice the supplyPrice to set
	 */
	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	/**
	 * 获取: currencySign
	 * @return the currencySign
	 */
	public String getCurrencySign() {
		return currencySign;
	}
	/**
	 * 设置: currencySign
	 * @param currencySign the currencySign to set
	 */
	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}
	/**
	 * 获取: currencyName
	 * @return the currencyName
	 */
	public String getCurrencyName() {
		return currencyName;
	}
	/**
	 * 设置: currencyName
	 * @param currencyName the currencyName to set
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ContractGoods [contractGoodsID=" + contractGoodsID + ", contractID=" + contractID + ", goodsName="
				+ goodsName + ", goodsCode=" + goodsCode
				+ ", goodsBrand=" + goodsBrand + ", goodsNorm=" + goodsNorm + ", goodsNum=" + goodsNum + ", goodsMap="
				+ goodsMap + ", deliverCountry=" + deliverCountry + ", deliverCity=" + deliverCity + ", tradeType="
				+ tradeType + ", readyTime=" + readyTime + ", logisticsType=" + logisticsType + ", volume=" + volume
				+ ", palletVolume=" + palletVolume + ", palletPrice=" + palletPrice + ", postage=" + postage
				+ ", goodsPrice=" + goodsPrice + ", supplyPrice=" + supplyPrice + ", currencySign=" + currencySign
				+ ", currencyName=" + currencyName + ", getContractGoodsID()=" + getContractGoodsID()
				+ ", getContractID()=" + getContractID() + ", getGoodsName()=" + getGoodsName()
				+ ", getGoodsCode()=" + getGoodsCode()
				+ ", getGoodsBrand()=" + getGoodsBrand() + ", getGoodsNorm()=" + getGoodsNorm() + ", getGoodsNum()="
				+ getGoodsNum() + ", getGoodsMap()=" + getGoodsMap() + ", getDeliverCountry()=" + getDeliverCountry()
				+ ", getDeliverCity()=" + getDeliverCity() + ", getTradeType()=" + getTradeType() + ", getReadyTime()="
				+ getReadyTime() + ", getLogisticsType()=" + getLogisticsType() + ", getVolume()=" + getVolume()
				+ ", getPalletVolume()=" + getPalletVolume() + ", getPalletPrice()=" + getPalletPrice()
				+ ", getPostage()=" + getPostage() + ", getGoodsPrice()=" + getGoodsPrice() + ", getSupplyPrice()="
				+ getSupplyPrice() + ", getCurrencySign()=" + getCurrencySign() + ", getCurrencyName()="
				+ getCurrencyName() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
 
	
	
	
	 
	
}
