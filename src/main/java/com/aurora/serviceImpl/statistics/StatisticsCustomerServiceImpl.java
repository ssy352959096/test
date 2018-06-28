package com.aurora.serviceImpl.statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.service.statistics.StatisticsCustomerService;
import com.aurora.util.PageData;

/**
 * 描述:数据统计--用户数据统计--ServiceImpl
 * 创建:SSY 2017/11/16
 * 修改:
 * @version 1.0
 */
 
@Service("statisticsCustomerServiceImpl")
public class StatisticsCustomerServiceImpl implements StatisticsCustomerService{

	@Resource(name = "daoSupport")
	private DAO daoSupport;

	/**
	 * 条件展示统计会员列表;
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getCustomerList(Page page) throws Exception{
		return (List<PageData>) daoSupport.findForList("StatisticsCustomerReadMapper.getCustomerList", page); 
	}

	/**
	 * 查询符合条件的会员数量;
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getCustomerNum(Page page) throws Exception{
		 Object findForObject = daoSupport.findForObject("StatisticsCustomerReadMapper.getCustomerNum", page); 
		 if (findForObject!= null) {
			 return (int)findForObject;
		}else{
			return 0;
		}
	}
		
	/**
	 * 查询截止至某个时间注册的总会员数,需要传入日期;模糊左匹配;
	 * 例如2017 表示查询2017全年的注册会员数,2017-08表示查询2017年8月注册的会员数;
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getRegisterCustomerNum(String date) throws Exception{
		 Object findForObject = daoSupport.findForObject("StatisticsCustomerReadMapper.getCustomerNumByDate", date); 
		 if (findForObject!= null) {
			 return (int)findForObject;
			}else{
				return 0;
			}
	}
	
	
	/**
	 *  查询该用户的微仓周转时间,点击比例, 购买比例,登录时间分布;
	 *  @param customerID
	 *  @exception
	 *  @return customerBehaviorData
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> getCustomerBehaviorData(String customerID) throws Exception{
		Map<String,Object> customerBehaviorData = new HashMap<String,Object>();
		
		//登陆时间分布;
		List<PageData> loginDistribution = (List<PageData>)daoSupport.findForList("StatisticsCustomerReadMapper.getLoginDistribution", customerID); 
		customerBehaviorData.put("loginDistribution", loginDistribution);
		//点击类目比例;
		PageData clickDistribution = (PageData)daoSupport.findForObject("StatisticsCustomerReadMapper.getClickDistribution", customerID); 
		customerBehaviorData.put("clickDistribution", clickDistribution);
		//购买类目比例;
		List<PageData> buyDistribution = (List<PageData>)daoSupport.findForList("StatisticsCustomerReadMapper.getBuyDistribution", customerID); 
		customerBehaviorData.put("buyDistribution", buyDistribution);
		//微仓周转时间;
		PageData oldTurnover = (PageData)daoSupport.findForObject("StatisticsCustomerReadMapper.getOldTurnoverTime", customerID); //历史微仓周转时间以及已经发出微仓商品总数
		PageData remainTurnover = (PageData)daoSupport.findForObject("StatisticsCustomerReadMapper.getNewTurnoverTime", customerID);//剩余微仓货物总数,以及从进入微仓至当前时间的剩余商品的微仓周转时间;
		if (oldTurnover==null&&remainTurnover!=null) {
			BigDecimal remainTurnoverDays = new BigDecimal(remainTurnover.getString("turnoverDays"));//截止至当前时间剩余商品的周转时间;
			BigDecimal remainNum = new BigDecimal(remainTurnover.getString("remainNum"));
			BigDecimal turnoverTime = new BigDecimal("0.00");
			turnoverTime = remainTurnoverDays.divide(remainNum, 2, RoundingMode.HALF_UP);
			customerBehaviorData.put("turnoverTime", turnoverTime); 
		} else if (oldTurnover!=null&&remainTurnover==null) {
			BigDecimal oldTurnoverDays = new BigDecimal(oldTurnover.getString("dayNum"));//旧的周转时间
			BigDecimal oldSendNum = new BigDecimal(oldTurnover.getString("sendNum"));//已经发送的微仓数量
			BigDecimal turnoverTime = new BigDecimal("0.00");
			turnoverTime = oldTurnoverDays.divide(oldSendNum, 2, RoundingMode.HALF_UP);
			customerBehaviorData.put("turnoverTime", turnoverTime); 
		} else if (oldTurnover!=null&&remainTurnover!=null) {
			BigDecimal oldTurnoverDays = new BigDecimal(oldTurnover.getString("dayNum"));//旧的周转时间
			BigDecimal oldSendNum = new BigDecimal(oldTurnover.getString("sendNum"));//已经发送的微仓数量
			BigDecimal remainTurnoverDays = new BigDecimal(remainTurnover.getString("turnoverDays"));//截止至当前时间剩余商品的周转时间;
			BigDecimal remainNum = new BigDecimal(remainTurnover.getString("remainNum"));
			BigDecimal turnoverTime = new BigDecimal("0.00");
			BigDecimal newTurnoverDays = oldTurnoverDays.add(remainTurnoverDays);//截止目前总的周转天数
			BigDecimal newNum = oldSendNum.add(remainNum);//截止目前总的微仓商品数
			turnoverTime = newTurnoverDays.divide(newNum, 2, RoundingMode.HALF_UP);
			customerBehaviorData.put("turnoverTime", turnoverTime); 
		} else{
			customerBehaviorData.put("turnoverTime", 0); 
		}
		return customerBehaviorData;
	}
	
}
