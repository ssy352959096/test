package com.aurora.service;

import java.util.List;
import java.util.Map;

import com.aurora.entity.Menu;
import com.aurora.entity.Page;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;

public interface OrderManageService {
	
	/**
	 *  从数据库中条件查询
	 *  @author SSY
	 *  @param page orderID goodsName
	 *  @return
	 *  @throws Exception
	 */
	public Map<String,Object> queryOrder(Page page, String orderID, String goodsName, String orderType, String tradeType,
			String payType, String orderState, String beginTime, String endTime,String customerName,String customerEmail,String customerMobile,PageData pd) throws Exception ;
	 
	/**根据订单单号获取订单包含商品
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getOrderGoodsByOID(String orderID) throws Exception;
	
	
	/**
	 * 网站管理--订单处理(条件查询)
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	public Map<String, Object> queryOrderByS5(Page page, String orderID, String goodsName, String orderAD, String orderState
			, String beginTime, String endTime, String customerName,PageData pd) throws Exception;
	
	
	
	/**网站管理/订单管理 --根据商品ID等条件获取订单ID
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getOrderIDByS5(Page page) throws Exception;
	
	/**网站管理/订单管理 --根据商品ID等条件获取订单数量
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public int getOrderIDNumByS5(Page page) throws Exception;
	
	/**订单管理/所有订单 --根据商品ID等条件获取订单ID
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getOrderIDByS8(Page page) throws Exception;
	
	/**订单管理/所有订单 --根据商品ID等条件获取订单数量
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public int getOrderIDNumByS8(Page page) throws Exception;
	
	/**根据订单ID获取订单老状态
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	public String getOrderOldState(String orderID) throws Exception;
	
	/**修改单个订单状态
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String updateOrderShut(PageData pd)throws Exception;
	
	/**获取今天下单数量
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public int getTodayONum(String time) throws Exception;
	
	/**根据订单状态获取订单数量
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public int getONumByState(String state) throws Exception;
		
	/**获取今天营业额(只计算正常已付款订单)
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public String getTodayTurnover(String time) throws Exception;
	
	/**根据订单ID更新单个订单备注
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	public void updateRemark(PageData pd)throws Exception;
	
	/**
	 * 接单之前修改海外直邮的总成本
	 * @param goodsCost
	 * @throws Exception
	 */
	public void updateOHGCost(String goodsCost) throws Exception;
	
	/**
	 * 接单操作;
	 * 修改该订单的接单时间;操作者;
	 * 修改该订单的状态order_state
	 * @author SSY
	 */
	public void updateOrderReceiving(String orderID) throws Exception;
	
	/**
	 * 北极光退款操作;
	 * 修改该订单的退款时间;操作者;
	 * 修改该订单的状态order_state
	 * @author SSY
	 */
	public String updateAuroraRefund(PageData pd) throws Exception;
	
	/**根据订单ID获取订单老状态
	 * @param orderID
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	public String getOrderState(String orderID) throws Exception;
	
	/**
	 * 根据订单编号查询订单类型;*(微仓Or非微仓)
	 */
	public int getOrderLevel(String orderID) throws Exception;
	
	/**
	 * 修改物流信息;
	 * 修改订单状态
	 * 物流信息||或者no
	 * @param pd
	 * @param goodsMap
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	public void updateLogistics(PageData pd, List<Map<String, String>> goodsList,String orderState) throws Exception;
	

	/**
	 * 批量退款处理
	 */
	public void batchRefund(PageData pd) throws Exception;
	
	
	/**
	 * 查询回显所有物流公司
	 */
	public List<PageData> getLogisticsCompany() throws Exception;

	/**
	 * 后台手动确定收货,如果该订单中有商品物流信息还未签收状态,就不能确认收货;
	 * @param orderID
	 */
	public String updateConfirmReceipt(String orderID) throws Exception;

	/**
	 * 接单提醒用--30分钟未接单的订单数量
	 * 
	 */
	public int get30MMissedOrderNum(String userID) throws Exception;

	/**
	 * @Title: updateOrderAutoShut 
	 * @Description: 自动关闭两小时未付款订单
	 * @param    
	 * @return int  
	 * @author SSY
	 * @date 2018年6月21日 下午6:38:10
	 */
	public int updateOrderAutoShut() throws Exception;
	
}
