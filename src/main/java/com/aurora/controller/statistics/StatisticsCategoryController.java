package com.aurora.controller.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.controller.BaseController;
import com.aurora.service.statistics.StatisticsCategoryService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 数据统计---类目统计数据分析
 * @author SSY 2017/11/18
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statisticsCategory")
public class StatisticsCategoryController extends BaseController {

	String menuUrl = "statisticsCategory.do"; // 菜单地址(权限用)

	@Resource(name = "statisticsCategoryServiceImpl")
	private StatisticsCategoryService statisticsCategoryServiceImpl;

	/**
	 * 跳转到数据统计 -- 类目分析页面;
	 * @param  
	 * @return
	 */
	@RequestMapping
	public ModelAndView goStatisticsCategoryPage() throws Exception {
		ModelAndView mv = this.getModelAndView();
		String msg = "";
		try {
			Map<String, Object> categoryStatisticsData = statisticsCategoryServiceImpl.getCategoryStatisticsData();	 
			mv.addObject("categoryStatisticsData", categoryStatisticsData);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CSCC:    系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CSCC: 系统异常!数据统计--类目数据统计系统执行异常！】");
			throw new Exception(msg);
		}
		mv.setViewName("system/statistics/categoryStatistics");
		return mv;
	}

	/**
	 * 统计数据--类目分析--查询订单中各类目订单量比例,按照时间,二级类目筛选;;
	 * @param customerID(用户id)
	 * @return
	 */
	@RequestMapping(value = "/getCategoryOrderChart")
	@ResponseBody
	public Object getCategoryOrderChart(String category1ID, Integer day) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		String result = "";
		String msg = "";
		day = day!=null&&day>0?day:6;
		category1ID = Tools.notEmptys(category1ID)?category1ID.trim():null;
		pd.put("category1ID",category1ID);
		pd.put("day",day);
		pd.put("payTime", DateUtil.getAfterDay(-day));
		try {
				List<PageData> categoryOrderChart = statisticsCategoryServiceImpl.getCategoryOrderChart(pd);
				map.put("categoryOrderChart", categoryOrderChart);
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CSCC: 操作失败!重试或联系后端管理员!";
				logger.info("【CSCC: 系统异常!数据统计--类目数据统计系统执行异常!】");
			}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 统计数据--类目分析--查询订单中各类目累计销售件数比例 ;按照时间,二级类目筛选
	 * @param customerID(用户id)
	 * @return
	 */
	@RequestMapping(value = "/getCategorySaleChart")
	@ResponseBody
	public Object getCategorySaleChart(String category1ID, Integer day) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		String result = "";
		String msg = "";
		day = day!=null&&day>0?day:6;
		category1ID = Tools.notEmptys(category1ID)?category1ID.trim():null;
		pd.put("category1ID",category1ID);
		pd.put("day",day);
		pd.put("payTime", DateUtil.getAfterDay(-day));
		try {
				List<PageData> categorySaleChart = statisticsCategoryServiceImpl.getCategorySaleChart(pd);
				map.put("categorySaleChart", categorySaleChart);	 
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CSCC: 操作失败!重试或联系后端管理员!";
				logger.info("【CSCC: 系统异常!数据统计---类目数据统计系统执行异常!】");
			}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 统计数据--类目分析-- 查询价格段销量比,按照时间,类目筛选
	 * @param customerID(用户id)
	 * @return
	 */
	@RequestMapping(value = "/getPriceSaleChart")
	@ResponseBody
	public Object getPriceSaleChart(String category1ID, Integer day) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		String result = "";
		String msg = "";
		day = day!=null&&day>0?day:6;
		category1ID = Tools.notEmptys(category1ID)?category1ID.trim():null;
		pd.put("category1ID",category1ID);
		pd.put("day",day);
		pd.put("payTime", DateUtil.getAfterDay(-day));
		try {
				List<PageData> priceSaleChart = statisticsCategoryServiceImpl.getPriceSaleChart(pd);
				map.put("priceSaleChart", priceSaleChart);	 
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CSCC: 操作失败!重试或联系后端管理员!";
				logger.info("【CSCC: 系统异常!数据统计---类目数据统计系统执行异常!】");
			}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 统计数据--类目分析-- 关键字统计(按照月份统计;)
	 * @param customerID(用户id)
	 * @return
	 */
	@RequestMapping(value = "/getKeyWordData")
	@ResponseBody
	public Object getKeyWordStatisticsData(Integer month) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		String result = "";
		String msg = "";
		try {
			month = month!=null&&month>0&&month<=12?month:DateUtil.getMonth();
			pd.put("month",month);
			List<PageData> keyWordStatisticsData = statisticsCategoryServiceImpl.getKeyWordStatisticsData(month);
			map.put("keyWordStatisticsData", keyWordStatisticsData);	 
			result = "success";
		} catch (Exception e) {
				result = "error";
				msg = "CSCC: 操作失败!重试或联系后端管理员!";
				logger.info("【CSCC: 系统异常!数据统计---关键字统计系统执行异常!】");
			}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
	
	
}
