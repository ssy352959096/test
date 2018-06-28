package com.aurora.serviceImpl.statistics;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.service.statistics.StatisticsSalesRepService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;

/**
 * 描述:销售员业绩统计ServiceImpl
 * 创建:SSY 2017/11-28
 * 修改:
 * @version 1.0
 */
 
@Service("statisticsSalesRepServiceImpl")
public class StatisticsSalesRepServiceImpl implements StatisticsSalesRepService {
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;

	/**
	 * 统计该销售累计时间业绩;
	 * @param pd
	 * @return
	 */
	public Map<String, String> getTotalSales(PageData pd) throws Exception{
		Map<String, String> totalSales = new HashMap<String, String>();
		pd.put("queryDate", DateUtil.getDay());
		PageData todayTotalSales = (PageData)daoSupport.findForObject("StatisticsSalesRepReadMapper.getSalesRepTotalSales",pd);//今日销售代表业绩;
		totalSales.put("todayTotalSales", todayTotalSales.getString("pay_money"));
		pd.put("queryDate", DateUtil.getThisWeekMonday());
		PageData thisWeekTotalSales = (PageData)daoSupport.findForObject("StatisticsSalesRepReadMapper.getSalesRepXDayTotalSales",pd);//本周销售代表业绩;
		totalSales.put("thisWeekTotalSales", thisWeekTotalSales.getString("pay_money"));
		pd.put("queryDate", DateUtil.getMonthMM());
		PageData thisMonthTotalSales = (PageData)daoSupport.findForObject("StatisticsSalesRepReadMapper.getSalesRepXDayTotalSales",pd);//本月销售代表业绩;
		totalSales.put("thisMonthTotalSales", thisMonthTotalSales.getString("pay_money"));
		pd.put("queryDate", DateUtil.getYear());
		PageData thisYearTotalSales = (PageData)daoSupport.findForObject("StatisticsSalesRepReadMapper.getSalesRepXDayTotalSales",pd);//本月销售代表业绩;
		totalSales.put("thisYearTotalSales", thisYearTotalSales.getString("pay_money"));
		return totalSales;
	}
	
	
	
	
	
}
