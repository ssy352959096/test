package com.aurora.service.statistics;

import java.util.Map;

import com.aurora.entity.Page;

public interface StatisticsSupplyChainService {

	/**
	 * 获取供应链数据;
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSupplyChainData(Page page) throws Exception;

	/**
	 * 条件查询商品总数
	 * @param page
	 * @return
	 */
	public int getGoodsNum(Page page) throws Exception;

	 
	
	
	
}
