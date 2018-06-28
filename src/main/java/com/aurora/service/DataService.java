package com.aurora.service;

import java.util.List;

import com.aurora.util.PageData;

public interface DataService {
	
	/**根据商品ID获取商品详情页行业数据（淘宝售价  京东售价 本站售价 淘宝在售商家）
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getJPriceData(String goodsID)throws Exception;
	public List<PageData> getTPriceData(String goodsID)throws Exception;
	public List<PageData> getTStoreData(String goodsID)throws Exception;
	public List<PageData> getAPriceData(String goodsID)throws Exception;
	
}
