package com.aurora.controller.statistics;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.controller.BaseController;
import com.aurora.entity.Page;
import com.aurora.service.statistics.StatisticsSupplyChainService;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 数据统计---网站数据分析
 * @author SSY 2017/11/18
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statisticsSupplyChain")
public class StatisticsSupplyChainController extends BaseController {

	String menuUrl = "statisticsSupplyChain.do"; // 菜单地址(权限用)

	@Resource(name = "statisticsSupplyChainServiceImpl")
	private StatisticsSupplyChainService statisticsSupplyChainServiceImpl;

	/**
	 * 跳转到数据统计 -- 商品分析页面--商品列表;
	 * @param  page,hasStock(1.无真实库存,2真实库存),shipType(1保税仓；2海外直邮；3国内现货) ,
	 * 				goodsName (商品名称),goodsID (商品id),orderBY(排序1.无真实库存,2真实库存),orderAD(默认"DESC","ASC")
	 * @return
	 */
	@RequestMapping
	public ModelAndView goStatisticsSupplyChainPage(Page page,String goodsID,String goodsName,String shipType,String orderBY, String orderAD) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		pd.put("goodsID", Tools.notEmptys(goodsID)?goodsID:null);
		pd.put("goodsName", Tools.notEmptys(goodsName)?goodsName:null);
		pd.put("shipType", Tools.notEmptys(shipType)?shipType:null);
		pd.put("orderBY", Tools.notEmptys(orderBY)?orderBY:null);
		pd.put("orderAD", Tools.notEmptys(orderAD)?orderAD:"DESC");
		page.setPageSize(20);
		page.setPd(pd);
		try {
			Map<String, Object>  supplyChainData = statisticsSupplyChainServiceImpl.getSupplyChainData(page);//网站分析基本数据
			int totalRecord = statisticsSupplyChainServiceImpl.getGoodsNum(page);
			page.setTotalRecord(totalRecord);
			mv.addObject("supplyChainData", supplyChainData);
			mv.addObject("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CSSCC:    系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CSSCC: 系统异常!数据统计--供应链数据统计系统执行异常！】");
			throw new Exception(msg);
		}
		mv.setViewName("system/statistics/statisticsSupplyChain");
		return mv;
	}
	 
	
	
	
}
