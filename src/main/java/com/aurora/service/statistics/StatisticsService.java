package com.aurora.service.statistics;

import java.util.Map;

public interface StatisticsService {

	 
	/**
	 *	 查询截止至某个时间注册的总会员数,需要传入日期;模糊左匹配;
	 * 例如2017 表示查询2017全年的注册会员数,2017-08表示查询2017年8月注册的会员数;
	 * @param afterDayDate
	 * @return
	 * @throws Exception
	 */
	public int getRegisterCustomerNum(String date) throws Exception;

	/**
	 * 根据时间统计订单成交数量以及成交金额;
	 * @return
	 * @throws Exception
	 */
	public int getSOrderNumByDate(String date) throws Exception;
	
	/**
	 * 根据时间统计订单成交金额money(应付款 +邮费);
	 * @return
	 * @throws Exception
	 */
	public String getSOrderMuchByDate(String date) throws Exception;
	
	/**
	 * 根据时间统计网站浏览次数;
	 * @return
	 * @throws Exception
	 */
	public int getDayPV(String date) throws Exception;
	
	/**
	 * 根据时间统计询价数量;
	 * @return
	 * @throws Exception
	 */
	public int getInquiryNumByDate(String date) throws Exception;
  
	/**
	 * 根据时间统计所有状态合同数量;
	 * @return
	 * @throws Exception
	 */
	public int getContractNumByDate(String date) throws Exception;
	
	/**
	 * 根据时间统计所有状态合同数量;
	 * @return
	 * @throws Exception
	 */
	public int getContractNumByDatePay(String date) throws Exception;
	
	/**
	 * 根据时间统计利润;(应付款-成本-退款);
	 */
	public String getProfitByDate(String date) throws Exception;
	
	
	/**
	 * 根据时间统计所有商品合计浏览量GPV(日/周/月);
	 */
	public int getGPVByDate(String date) throws Exception;
	
	/**
	 * 时间,贸易类型trade_type统计下单商品单数;
	 */
	public int getGoodsNumByDT(String date,String tradeType) throws Exception;
	
	/**
	 * 数据概况基本数据展示;
	 */
	public Map<String, Object> getStatisticsInfo() throws Exception;

	/**
	 * 访客数;
	 */
	public int getUV() throws Exception;

	
	
	
	
}
