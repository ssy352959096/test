package com.aurora.service.statistics;

import java.util.List;
import java.util.Map;

import com.aurora.util.PageData;

public interface StatisticsCategoryService {

	/**
	 * 查询数据统计 -- 类目分析数据
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getCategoryStatisticsData() throws Exception;
	
	/**
	 * 查询数据统计 -- 查询订单中各类目订单量比例,按照时间,类目筛选;
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getCategoryOrderChart(PageData pd) throws Exception;
	
	/**
	 * 查询数据统计 --  查询订单中各类目累计销售件数比例 ;按照时间,二级类目筛选
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getCategorySaleChart(PageData pd) throws Exception;
 
	
	/**
	 * 查询数据统计 --   查询价格段销量比,按照时间,类目筛选
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getPriceSaleChart(PageData pd) throws Exception;

	/**
	 * 时间月份查询关键字统计月份累计数据
	 */
	public List<PageData> getKeyWordStatisticsData(Integer monthNum) throws Exception;
	
	
}
