package com.aurora.service.statistics;

import java.util.Map;

import com.aurora.util.PageData;

public interface StatisticsWebsiteService {

	/**
	 * 查询网站统计数据
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getWebsiteStatisticsData() throws Exception;



	/**
	 * PV--24小时时间段分布
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPVDistribution() throws Exception;

	
	/**
	 * PV-近X日变化趋势;
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPVTrend(int days) throws Exception;

	/**
	 * 网站销售额--近X日销售额走势;
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSalesTrend(int days) throws Exception;

	/** 
	 * @Title: updatePV 
	 * @Description: TODO 
	 * @param    
	 * @return void  
	 * @author SSY
	 * @date 2018年3月28日 下午3:09:28 
	 */
	public void updatePV(int days) throws Exception;

	/** 
	 * @Title: updateUV 
	 * @Description: TODO 
	 * @param    
	 * @return void  
	 * @author SSY
	 * @date 2018年3月28日 下午5:05:40 
	 */
	public void updateUV(int days) throws Exception;
//	
//	/**
//	 * @Title: getHomeClick 
//	 * @Description: 首页热点图;
//	 * @param    String startDate
//	 * @return Map<String,Object>  
//	 * @author SSY
//	 * @date 2018年3月28日 下午3:52:40
//	 */
//	public PageData getHomeClick(String startDate) throws Exception;
	
	
	/**
	 * 从百度统计更新首页热力图 --- 更新获取转化事件累计次数;
	 */
	public void updateHomeClick(int days) throws Exception;
}
