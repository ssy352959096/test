package com.aurora.service.statistics;

import java.util.List;
import java.util.Map;

import com.aurora.entity.Page;
import com.aurora.util.PageData;

/**
 * 描述:数据统计--用户数据统计--ServiceImpl
 * 创建:SSY 2017/11/16
 * @author SSY
 * @version 1.0
 */
public interface StatisticsCustomerService {

	/**
	 * 条件展示统计会员列表;
	 * @param page
	 * @return
	 */
	public List<PageData> getCustomerList(Page page) throws Exception;

	/**
	 * 查询符合条件的会员数量;
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public int getCustomerNum(Page page) throws Exception;

	/**
	 *	 查询截止至某个时间注册的总会员数,需要传入日期;模糊左匹配;
	 * 例如2017 表示查询2017全年的注册会员数,2017-08表示查询2017年8月注册的会员数;
	 * @param afterDayDate
	 * @return
	 * @throws Exception
	 */
	public int getRegisterCustomerNum(String date) throws Exception;

	 
	/**
	 *  查询该用户的微仓周转时间,点击比例, 购买比例,登录时间分布;
	 *  @param customerID
	 *  @exception
	 *  @return customerBehaviorData
	 */
	public Map<String,Object> getCustomerBehaviorData(String customerID) throws Exception;
	
	
}
