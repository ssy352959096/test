package com.aurora.controller.statistics;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.aurora.controller.BaseController;
import com.aurora.service.statistics.StatisticsWebsiteService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 数据统计---网站数据分析
 * @author SSY 2017/11/18
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statisticsWebsite")
public class StatisticsWebsiteController extends BaseController {

	String menuUrl = "statisticsWebsite.do"; // 菜单地址(权限用)

	@Resource(name = "statisticsWebsiteServiceImpl")
	private StatisticsWebsiteService statisticsWebsiteServiceImpl;

	/**
	 * 跳转到数据统计 -- 网站分析;
	 * @param  
	 * @return
	 */
	@RequestMapping
	public ModelAndView goStatisticsWebsitePage() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		try {
			Map<String, Object> websiteStatisticsData = statisticsWebsiteServiceImpl.getWebsiteStatisticsData();//网站分析基本数据
			mv.addObject("websiteStatisticsData", JSON.toJSON(websiteStatisticsData));
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CSGC:    系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CSGC: 系统异常!数据统计--商品数据统计系统执行异常！】");
			throw new Exception(msg);
		}
		mv.setViewName("system/statistics/websiteStatistics");
		return mv;
	}

	 
	/**
	 * 统计数据--网站分析-- PV--24小时时间段分布;
	 * @param customerID(用户id)
	 * @return
	 */
	@RequestMapping(value = "/getPVDistribution")
	@ResponseBody
	public Object getPVDistribution() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		String result = "";
		String msg = "";
		try {
				map = statisticsWebsiteServiceImpl.getPVDistribution();
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CSCC: 操作失败!重试或联系后端管理员!";
				logger.info("【CSCC: 系统异常!数据统计---网站PV24小时趋势数据统计系统执行异常!】");
			}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
	
	
	/**
	 * 统计数据--网站分析-- PV--近X日变化趋势;
	 * @param customerID(用户id)
	 * @return
	 */
	@RequestMapping(value = "/getPVTrend")
	@ResponseBody
	public Object getPVTrend(Integer day) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		String result = "";
		String msg = "";
		day = day != null && day > 0 ? day : 6;
		try {
				map = statisticsWebsiteServiceImpl.getPVTrend(day);
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CSCC: 操作失败!重试或联系后端管理员!";
				logger.info("【CSCC: 系统异常!数据统计---网站PV近X日变化趋势数据统计系统执行异常!】");
			}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
	
	
	
	/**
	 * 统计数据--网站分析-- 销售额--近X日变化趋势;
	 * @param customerID(用户id)
	 * @return
	 */
	@RequestMapping(value = "/getSaleTrend")
	@ResponseBody
	public Object getSaleTrend(Integer day) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		String result = "";
		String msg = "";
		day = day != null && day > 0 ? day : 6;
		try {
				map = statisticsWebsiteServiceImpl.getSalesTrend(day);
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CSCC: 操作失败!重试或联系后端管理员!";
				logger.info("【CSCC: 系统异常!数据统计---网站PV近X日变化趋势数据统计系统执行异常!】");
			}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
	
//	/**
//	 * @Title: getHomeClick 
//	 * @Description: 首页热点图;
//	 * @param    Integer day
//	 * @return  moduleClickTimes
//	 * @author SSY
//	 * @date 2018年3月28日 下午3:52:40
//	 */
//	@RequestMapping(value = "/getHomeClick")
//	@ResponseBody
//	public Object getHomeClick(Integer day) throws Exception {
//		Map<String, Object> map = new HashMap<String, Object>();
//		String result = "";
//		String msg = "";
//		day = day!=null&&day>0?day:0;
//		String startDate = DateUtil.getAfterDay(-day);
//		try {
//				PageData moduleClickTimes = statisticsWebsiteServiceImpl.getHomeClick(startDate);
//				map.put("moduleClickTimes", moduleClickTimes);
//				result = "success";
//			} catch (Exception e) {
//				result = "error";
//				msg = "CSCC: 操作失败!重试或联系后端管理员!";
//				logger.info("【CSCC: 系统异常!数据统计---首页热点图查询系统执行异常!】");
//			}
//		map.put("result", result);
//		map.put("msg", msg);
//		return map;
//	}
	
	
}
