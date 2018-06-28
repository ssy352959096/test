package com.aurora.serviceImpl.statistics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.service.statistics.StatisticsWebsiteService;
import com.aurora.util.BaiduTongJi;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;

/**
 * 描述:数据统计--网站数据分析统计ServiceImpl
 * 创建:SSY 2017/11-18
 * 修改:
 * @version 1.0
 */
 
@Service("statisticsWebsiteServiceImpl")
public class StatisticsWebsiteServiceImpl implements StatisticsWebsiteService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;

	/**
	 * 查询网站统计数据----
	 * 		1.PV:累计和24小时分布访问量PV走势(今日,最近7天,最近30天)
	 * 		2.UV累计(今日,最近7天,最近30天)
	 * 		3.今日订单数据(今日订单数,今日新增客户,今日销售额)
	 * 		4.ip地址统计(前28名城市ip:城市名,ip个数,ip累计次数),按照ip个数排序
	 * 		5.网站首页热力图 (累计点击次数排序)
	 * 		6.销售额走势(最近7天,最近30天)
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getWebsiteStatisticsData() throws Exception{
		Map<String, Object> websiteStatisticsData = new HashMap<String, Object>();
//		1.ip地址统计;
//		List<PageData> ipDate = (List<PageData>)daoSupport.findForList("StatisticsWebsiteReadMapper.getIPDistribution");
//		websiteStatisticsData.put("ipDate", ipDate);
		String today = DateUtil.getDay();
		String sevenDay = DateUtil.getAfterDay(-6);
		String thirtyDay = DateUtil.getAfterDay(-29);
////		2.首页热点图
		List<PageData> todayHomeClick = (List<PageData>)daoSupport.findForList("StatisticsWebsiteReadMapper.getHomeClick",today);
		websiteStatisticsData.put("todayHomeClick", todayHomeClick);
		List<PageData> sevenHomeClick = (List<PageData>)daoSupport.findForList("StatisticsWebsiteReadMapper.getHomeClick",sevenDay);
		websiteStatisticsData.put("sevenHomeClick", sevenHomeClick);
		List<PageData> thirtyHomeClick = (List<PageData>)daoSupport.findForList("StatisticsWebsiteReadMapper.getHomeClick",thirtyDay);
		websiteStatisticsData.put("thirtyHomeClick", thirtyHomeClick);
		String object = (String)daoSupport.findForObject("DictionaryReadMapper.getHomeModules");
		String[] moduleList = object.split(",");
		websiteStatisticsData.put("moduleList", moduleList);
		
//		5.近X日累计PV总量
		int todayTotalPV = (int)daoSupport.findForObject("StatisticsWebsiteReadMapper.getTotalPV",today);//
		websiteStatisticsData.put("todayTotalPV", todayTotalPV);//今日累计PV;
		int sevenTotalPV = (int)daoSupport.findForObject("StatisticsWebsiteReadMapper.getTotalPV",sevenDay);//
		websiteStatisticsData.put("sevenTotalPV", sevenTotalPV);//7天累计PV;
		int thirtyTotalPV = (int)daoSupport.findForObject("StatisticsWebsiteReadMapper.getTotalPV",thirtyDay);//
		websiteStatisticsData.put("thirtyTotalPV", thirtyTotalPV);//30天累计PV;
		
		
//		6.UV-近X日累计访客数;
		int todayTotalUV = (int)daoSupport.findForObject("StatisticsWebsiteReadMapper.getTotalUV",today);//查询近X日累计的UV
		int sevenTotalUV = (int)daoSupport.findForObject("StatisticsWebsiteReadMapper.getTotalUV",sevenDay);//查询近X日累计的UV
		int thirtyTotalUV = (int)daoSupport.findForObject("StatisticsWebsiteReadMapper.getTotalUV",thirtyDay);//查询近X日累计的UV
		websiteStatisticsData.put("todayTotalUV", todayTotalUV);//今天UV变化;
		websiteStatisticsData.put("sevenTotalUV", sevenTotalUV);//近7天累计UV;
		websiteStatisticsData.put("thirtyTotalUV", thirtyTotalUV);//近30天累计UV;
		
//		8.今日数据--今日订单数,订单销售额,网站转化率,近7/30天订单数
		PageData todaySales = (PageData)daoSupport.findForObject("StatisticsOrderReadMapper.getDaySales",today);//查询指定日期的销售额 
		PageData todayOrderNum = (PageData)daoSupport.findForObject("StatisticsOrderReadMapper.getDayOrderNum",today);//查询指定日期的订单数量(不含微仓订单)
		todaySales.putAll(todayOrderNum);
		websiteStatisticsData.put("todaySales", todaySales);//今日销售情况,订单数和销售额,实付款为准;
		PageData sevenTotalSales = (PageData)daoSupport.findForObject("StatisticsOrderReadMapper.getTotalSales",sevenDay);//查询指定日期的累计销售额 
		PageData sevenTotalOrderNum = (PageData)daoSupport.findForObject("StatisticsOrderReadMapper.getTotalOrderNum",sevenDay);//查询指定日期的累计订单数量(不含微仓订单)
		sevenTotalSales.putAll(sevenTotalOrderNum);
		websiteStatisticsData.put("sevenTotalSales", sevenTotalSales);//近7天销售情况,订单数和销售额,实付款为准;
		PageData thirtyTotalSales = (PageData)daoSupport.findForObject("StatisticsOrderReadMapper.getTotalSales",thirtyDay);//查询指定日期的销售额以及订单数量
		PageData thirtyTotalOrderNum = (PageData)daoSupport.findForObject("StatisticsOrderReadMapper.getTotalOrderNum",thirtyDay);//查询指定日期的累计订单数量(不含微仓订单)
		thirtyTotalSales.putAll(thirtyTotalOrderNum);
		websiteStatisticsData.put("thirtyTotalSales", thirtyTotalSales);//近30天销售情况,订单数和销售额,实付款为准;
		int newCustomeNum = (int)daoSupport.findForObject("StatisticsCustomerReadMapper.getCustomerNumByDate", today); 
		websiteStatisticsData.put("newCustomeNum", newCustomeNum);//今日新增用户数;
		
		return websiteStatisticsData;
	}
	
	
	
	/**
	 * PV--24小时时间段分布
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getPVDistribution() throws Exception{
		Map<String, Object> websiteStatisticsData = new HashMap<String, Object>();
		List<PageData> totalPVDistribution = (List<PageData>)daoSupport.findForList("StatisticsWebsiteReadMapper.getDayPVDistribution");
		String date = DateUtil.getDay();
		List<PageData> todayPVDistribution = (List<PageData>)daoSupport.findForList("StatisticsWebsiteReadMapper.getDayPVDistribution",date);
		websiteStatisticsData.put("todayPVDistribution", todayPVDistribution);//今天的24小时时间段PV分布
		websiteStatisticsData.put("totalPVDistribution", totalPVDistribution);//累计的24小时时间段PV分布
		return websiteStatisticsData;
	}
	
	
	
	
	/**
	 * PV-近X日变化趋势;
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getPVTrend(int days) throws Exception{
		Map<String, Object> websiteStatisticsData = new HashMap<String, Object>();

		List<String> dayList = DateUtil.iteratorAfterDay(-days);
		List<PageData> pVTrend = new ArrayList<PageData>();
		for (Iterator iterator = dayList.iterator(); iterator.hasNext();) {
			String date = (String) iterator.next();
			PageData dayPV = (PageData)daoSupport.findForObject("StatisticsWebsiteReadMapper.getDayPV",date);//查询指定日期的pv
			pVTrend.add(dayPV);
		}
		websiteStatisticsData.put("pVTrend", pVTrend);//近X天PV变化;
		
		return websiteStatisticsData;
	}
 
	
	/**
	 * 网站销售额--近X日销售额走势;
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getSalesTrend(int days) throws Exception {
		Map<String, Object> websiteStatisticsData = new HashMap<String, Object>();
		List<String> dayList = DateUtil.iteratorAfterDay(-days);
		List<PageData> sevenSalesTrend = new ArrayList<PageData>();
		for (Iterator iterator = dayList.iterator(); iterator.hasNext();) {
			String date = (String) iterator.next();
			PageData daySales = (PageData) daoSupport.findForObject("StatisticsOrderReadMapper.getDaySales", date);// 查询指定日期的销售额以及订单数量
			sevenSalesTrend.add(daySales);
		}
		websiteStatisticsData.put("sevenSalesTrend", sevenSalesTrend);// 近X天销售额走势变化;

		return websiteStatisticsData;
	}
	
//	/**
//	 * @Title: getHomeClick 
//	 * @Description: 首页热点图;
//	 * @param    String startDate
//	 * @return Map<String,Object>  
//	 * @author SSY
//	 * @date 2018年3月28日 下午3:52:40
//	 */
//	@Override
//	public PageData getHomeClick(String startDate) throws Exception {
//		PageData moduleClickTimes = (PageData)daoSupport.findForObject("StatisticsWebsiteReadMapper.getHomeClick",startDate);
//		return moduleClickTimes;
//	}
	
	
	/**
	 * 定时任务操作; 抓取百度统计数据更新首页热力图 --- 一天一次;
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateHomeClick(int days) throws Exception{
		try {
			String startDate = DateUtil.getAfterDay(days);
			String endDate = startDate;
			String metrics = BaiduTongJi.METRICS_EVENT_COUNT;
			String method = BaiduTongJi.METHOD_EVENT_TRACK;
			List<Object> data = BaiduTongJi.getBaiduTongjiDate(startDate,endDate,method,metrics);
			List<List> moduleList = (List<List>)data.get(0);
			List<List> clickTimesList = (List<List>)data.get(1);
			PageData pd = new PageData();
			pd.put("date", startDate);
			for (int i = 0; i < clickTimesList.size(); i++) {
				Map<String, Object> module = (Map<String, Object>)moduleList.get(i).get(0);
				String moduleCode = (String) module.get("c");
				Object clickTimes = clickTimesList.get(i).get(0).equals("--")?0:clickTimesList.get(i).get(0);
				pd.put(moduleCode, clickTimes);
			}
			daoSupport.update("StatisticsWebsiteWriteMapper.updateHomeClick", pd);
		} catch (Exception e) {
			e.printStackTrace();
			// 什么也不做
		}
	}
	
	/** 
	 * @Title: updatePV
	 * @Description: 【定时任务】从百度统计更新pv; 一小时更新一次;
	 * @param    
	 * @return void  
	 * @author SSY
	 * @date 2018年3月28日 下午5:05:40 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Test
	public void updatePV(int days){
		try {
			String startDate = DateUtil.getAfterDay(days);
			String endDate = startDate;
			String metrics = BaiduTongJi.METRICS_PV;
			String method = BaiduTongJi.METHOD_TIME_TREND;
			List<Object> data = BaiduTongJi.getBaiduTongjiDate(startDate,endDate,method,metrics);
			List<List> pvDate = (List<List>)data.get(1);
			PageData pd = new PageData();
			int sum= 0;
			for (int i = 0; i < pvDate.size(); i++) {
				Object object = pvDate.get(i).get(0);
				int pv = object.equals("--")?0:(int)object;
				pd.put("hour"+i, pv);
				sum += pv;
			}
			pd.put("date", startDate);
			pd.put("sum", sum);
			daoSupport.update("StatisticsWebsiteWriteMapper.updatePV", pd);
		} catch (Exception e) {
			e.printStackTrace();
			// 什么也不做 
		}
	}
	
	/** 
	 * @Title: updateUV 
	 * @Description: 【定时任务】从百度统计更新uv; 一天更新一次;
	 * @param    
	 * @return void  
	 * @author SSY
	 * @date 2018年3月28日 下午5:05:40 
	 */
	@Override
	public void updateUV(int days){
		try {
			String startDate = DateUtil.getAfterDay(days);
			String endDate = startDate;
			String metrics = BaiduTongJi.METRICS_UV;
			String method = BaiduTongJi.METHOD_TIME_TREND;
			List<Object> data = BaiduTongJi.getBaiduTongjiDate(startDate,endDate,method,metrics);
			List<List> uvDate = (List<List>)data.get(1);
			PageData pd = new PageData();
			int sum= 0;
			for (int i = 0; i < uvDate.size(); i++) {
				Object object = uvDate.get(i).get(0);
				int uv = object.equals("--")?0:(int)object;
				pd.put("hour"+i, uv);
				sum += uv;
			}
			pd.put("date", startDate);
			pd.put("sum", sum);
			daoSupport.update("StatisticsWebsiteWriteMapper.updateUV", pd);
		} catch (Exception e) {
			e.printStackTrace();
			// 什么也不做 
		}
	}
	
	 

	

	
	
	
	
	
}
