package com.aurora.controller.statistics;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.controller.BaseController;
import com.aurora.service.statistics.StatisticsService;

/**
 * 数据统计---数据概况
 * @author SSY 2017/9/18
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statisticsFinanceInfo")
public class StatisticsFinanceInfoController extends BaseController {

	String menuUrl = "statisticsFinanceInfo.do"; // 菜单地址(权限用)

	@Resource(name = "statisticsServiceImpl")
	private StatisticsService statisticsServiceImpl;

	/**
	 * 跳转到数据概况 -- 数据概况页面;
	 * @param  
	 * @return
	 */
	@RequestMapping
	public ModelAndView goStatisticsInfoPage() throws Exception {
		ModelAndView mv = this.getModelAndView();
		String msg = "";
		try {
			Map<String, Object> map = statisticsServiceImpl.getStatisticsInfo();
			mv.addObject("map", map);
		} catch (Exception e) {
			e.printStackTrace();
			msg = " ";
			logger.info("【error: ！】");
			throw new Exception(msg);
		}
		mv.setViewName("system/statistics/statisticsFinanceInfo");
		return mv;
	}

}
