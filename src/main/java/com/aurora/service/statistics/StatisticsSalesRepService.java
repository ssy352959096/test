package com.aurora.service.statistics;

import java.util.Map;

import com.aurora.util.PageData;

/**
 * 描述:销售员业绩统计Service
 * 创建:SSY 2017/11-28
 * 修改:
 * @version 1.0
 */
public interface StatisticsSalesRepService {

	/**
	 * 统计该销售累计时间业绩;
	 * @param pd
	 * @return
	 */
	Map<String, String> getTotalSales(PageData pd) throws Exception;

	  
	
}
