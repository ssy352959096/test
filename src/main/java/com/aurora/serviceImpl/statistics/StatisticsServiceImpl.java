package com.aurora.serviceImpl.statistics;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.service.statistics.StatisticsService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;

/**
 * 描述:数据统计ServiceImpl
 * 创建:SSY 2017/9/18
 * 修改:
 * @version 1.0
 */
 
@Service("statisticsServiceImpl")
public class StatisticsServiceImpl implements StatisticsService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;

	 
		
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
	 * 根据时间统计订单成交数量num; 天  月 年
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getSOrderNumByDate(String date) throws Exception{
		PageData findForObject = (PageData)daoSupport.findForObject("StatisticsOrderReadMapper.getDayOrderNum", date);
		if (findForObject!= null) {
			 return Integer.valueOf(findForObject.getString("order_num"));
			}else{
				return 0;
			}
	}
	
	/**
	 * 根据时间统计订单成交金额money(应付款 +邮费);
	 * @return   pay_money
	 * @throws Exception
	 */
	@Override
	public String getSOrderMuchByDate(String date) throws Exception{
		PageData findForObject = (PageData)daoSupport.findForObject("StatisticsOrderReadMapper.getDaySales", date);
		if (findForObject!= null) {
			 return findForObject.getString("pay_money");
			}else{
				return "0.00";
			}
	}
	
	/**
	 * 统计网站指定日期浏览次数;
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getDayPV(String date) throws Exception{
		 PageData findForObject = (PageData)daoSupport.findForObject("StatisticsWebsiteReadMapper.getDayPV", date);
		 if (findForObject!= null) {
			 return Integer.valueOf(findForObject.getString("day_num"));
			}else{
				return 0;
			}
	}
	
	/**
	 * 查询指定日期以来
	 * @return
	 * @throws Exception
	 */
	public int getTotalPV(String date) throws Exception{
		 Object findForObject = daoSupport.findForObject("StatisticsWebsiteReadMapper.getTotalPV", date);
		 if (findForObject!= null) {
			 return (int)findForObject;
			}else{
				return 0;
			}
	}
	
	/**
	 * 根据时间统计询价数量;
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getInquiryNumByDate(String date) throws Exception{
		Object findForObject = daoSupport.findForObject("StatisticsReadMapper.getInquiryNumByDate", date);
		 if (findForObject!= null) {
			 return (int)findForObject;
			}else{
				return 0;
			}
	}
	
	/**
	 * 根据时间统计所有状态合同数量;
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getContractNumByDate(String date) throws Exception{
		Object findForObject = daoSupport.findForObject("StatisticsReadMapper.getContractNumByDate", date);
		 if (findForObject!= null) {
			 return (int)findForObject;
			}else{
				return 0;
			}
	}
	
	/**
	 * 根据时间统计已经付款完成的合同数量;
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getContractNumByDatePay(String date) throws Exception{
		Object findForObject = daoSupport.findForObject("StatisticsReadMapper.getContractNumByDatePay", date);
		 if (findForObject!= null) {
			 return (int)findForObject;
			}else{
				return 0;
			}
	}

	/**
	 * 根据时间统计利润;(应付款(不含邮费)-成本-退款);
	 */
	@Override
	public String getProfitByDate(String date) throws Exception{
		Object findForObject = daoSupport.findForObject("StatisticsReadMapper.getProfitByDate", date);
		if (findForObject!=null) {
			return (String) findForObject;
		}else{
			return "0.00";
		}
	}
	
	/**
	 * 根据时间统计所有商品合计浏览量GPV(月份);date = month1格式;
	 */
	@Override
	public int getGPVByDate(String month) throws Exception{
		PageData pd = new PageData();
		pd.put("month", month);
		PageData findForObject = (PageData)daoSupport.findForObject("StatisticsGoodsReadMapper.getMonthClickDistribution", pd);
		return Integer.valueOf(findForObject.getString("total_click_num"));
	}
	
	/**
	 * 时间,贸易类型trade_type统计下单商品单数;
	 */
	@Override
	public int getGoodsNumByDT(String date,String tradeType) throws Exception{
		PageData pd = new PageData();
		pd.put("date",date);
		pd.put("tradeType",tradeType);
		Object findForObject = daoSupport.findForObject("StatisticsReadMapper.getGoodsNumByDT", pd);
		if (findForObject!=null) {
			return (int)findForObject;
		}else{
			return 0;
		}
	}
 
	/**
	 * 数据概况基本数据展示;
	 */
	@Override
	public Map<String, Object> getStatisticsInfo() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int wpvNumToday = getDayPV(DateUtil.getDay());//今日网站浏览次数;
		int wpvNumYesterday = getDayPV(DateUtil.getAfterDay(-1));//昨日网站浏览次数;
		int orderNumToday = getSOrderNumByDate(DateUtil.getDay());//今日成交订单数量;
		int orderNumYesterday = getSOrderNumByDate(DateUtil.getAfterDay(-1));//昨日成交订单数量;
		String orderMoneyToday = getSOrderMuchByDate(DateUtil.getDay());//今日成交订单成交金额money(包含运费);
		String orderMoneyYesterday = getSOrderMuchByDate(DateUtil.getAfterDay(-1));//昨日成交订单成交金额money(包含运费);
		int inquiryNumToday = getInquiryNumByDate(DateUtil.getDay());//今日询价数量;
		int inquiryNumYesterday = getInquiryNumByDate(DateUtil.getAfterDay(-1));//昨日询价数量;
	
		int customerNumThisMonth = getRegisterCustomerNum(DateUtil.getMonthMM());//本月增加会员数;
		int customerNumLastMonth = getRegisterCustomerNum(DateUtil.getAfterMonth(-1));//上月增加会员数;
		int customerNumTotal = getRegisterCustomerNum("20");//截止至本月总会员数;
		int wpvNumThisMonth = getTotalPV(DateUtil.getMonthMM()+"-01");//本月网页浏览总次数;
		int gpvNumThisMonth = getGPVByDate(DateUtil.getMonthMM());//本月商品浏览次数;
		int goodsNumByOHW = getGoodsNumByDT(DateUtil.getMonthMM(),"2");//本月海外直邮下单数;
		int goodsNumByOBS = getGoodsNumByDT(DateUtil.getMonthMM(),"1");//本月保税仓下单数;
		int goodsNumByOGN = getGoodsNumByDT(DateUtil.getMonthMM(),"3");//本月国内现货下单数;
		int orderNumThisMonth = getSOrderNumByDate(DateUtil.getMonthMM());//本月总下单数;
		String orderMoneyThisMonth = getSOrderMuchByDate(DateUtil.getMonthMM());//本月成交订单成交金额money(包含运费);
		int inquiryNumThisMonth = getInquiryNumByDate(DateUtil.getMonthMM());//本月询价数量;
		int contractNumThisMonth = getContractNumByDate(DateUtil.getMonthMM());//本月发出合同数量;
		int contractNumFinishThisMonth = getContractNumByDatePay(DateUtil.getMonthMM());//本月合同成交数量;
		String profitThisMonth = getProfitByDate(DateUtil.getMonthMM());//本月利润;
		String profitLastMonth = getProfitByDate(DateUtil.getAfterMonth(-1));//上月利润;
		//利润月份集合;
		
		
		map.put("wpvNumToday", wpvNumToday);
		map.put("wpvNumYesterday", wpvNumYesterday);
		map.put("orderNumToday", orderNumToday);
		map.put("orderNumYesterday", orderNumYesterday);
		map.put("orderMoneyToday", orderMoneyToday);
		map.put("orderMoneyYesterday", orderMoneyYesterday);
		map.put("inquiryNumToday", inquiryNumToday);
		map.put("inquiryNumYesterday", inquiryNumYesterday);
		map.put("customerNumThisMonth", customerNumThisMonth);
		map.put("customerNumLastMonth", customerNumLastMonth);
		map.put("customerNumTotal", customerNumTotal);
		map.put("wpvNumThisMonth", wpvNumThisMonth);
		map.put("gpvNumThisMonth", gpvNumThisMonth);
		map.put("goodsNumByOHW", goodsNumByOHW);
		map.put("goodsNumByOBS", goodsNumByOBS);
		map.put("goodsNumByOGN", goodsNumByOGN);
		map.put("orderNumThisMonth", orderNumThisMonth);
		map.put("orderMoneyThisMonth", orderMoneyThisMonth);
		map.put("inquiryNumThisMonth", inquiryNumThisMonth);
		map.put("contractNumThisMonth", contractNumThisMonth);
		map.put("contractNumFinishThisMonth", contractNumFinishThisMonth);
		map.put("profitThisMonth", profitThisMonth);
		map.put("profitLastMonth", profitLastMonth);
		return map;
	}
	 
	/**
	 * 访客数;
	 */
	@Override
	public int getUV() throws Exception{
		return  (int) daoSupport.findForObject("StatisticsReadMapper.getUV");
	}  
	
	
	
	
}
