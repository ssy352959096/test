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
import com.aurora.entity.Page;
import com.aurora.service.GoodsManageService;
import com.aurora.service.statistics.StatisticsGoodsService;
import com.aurora.util.PageData;
import com.aurora.util.Tools;


/**
 * 数据统计---商品数据分析
 * @author SSY 2017/11/17
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statisticsGoods")
public class StatisticsGoodsController extends BaseController {

	String menuUrl = "statisticsGoods.do"; // 菜单地址(权限用)

	@Resource(name = "statisticsGoodsServiceImpl")
	private StatisticsGoodsService statisticsGoodsServiceImpl;
	@Resource(name="goodsManageServiceImpl")
	private GoodsManageService goodsManageServiceImpl;
	/**
	 * 跳转到数据统计 -- 商品分析页面--商品列表;
	 * @param goodsName(商品名称)  goodsID(商品id) category(商品类目10000,20000···), orderAD(排序规则DESC,ASC), page(分页)
	 * @return
	 */
	@RequestMapping
	public ModelAndView goStatisticsCustomerPage(Page page, String goodsName,String goodsID, String orderAD,String category) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		pd.put("goodsName", Tools.notEmptys(goodsName)?goodsName.trim():null);
		pd.put("goodsID", Tools.notEmptys(goodsID)?goodsID.trim():null);
		pd.put("orderAD", Tools.notEmptys(orderAD)?orderAD.trim():"DESC");
		pd.put("category", Tools.notEmptys(category)?category.trim():null);
		 
		page.setPd(pd);
		page.setPageSize(20);
		List<PageData>	goodsList = null;
		List<PageData>	goodsCategory1 = null;
		try {
			goodsCategory1 = goodsManageServiceImpl.getGoodsCategory("0");	//商品一级类目
			goodsList = statisticsGoodsServiceImpl.getGoodsList(page);	//条件列出商品列表
			int totalRecord =statisticsGoodsServiceImpl.getGoodsNum(page); //条件查询商品总数;
			page.setTotalRecord(totalRecord);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CSGC:    系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CSGC: 系统异常!数据统计--商品数据统计系统执行异常！】");
			throw new Exception(msg);
		}
		mv.addObject("goodsList", goodsList);//客户列表
		mv.addObject("goodsCategory1", goodsCategory1);//客户列表
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.setViewName("system/statistics/statisticsGoods");
		return mv;
	}

	/**
	 * 统计数据--商品列表---查看商品数据明细;
	 * @param customerID(用户id)
	 * @return
	 */
	@RequestMapping(value = "/getGoodsStatisticsData")
	@ResponseBody
	public Object getGoodsStatisticsData(String goodsID) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";

		if (Tools.notEmptys(goodsID)) {
			try {
				// 查询该商品今日,本月,近7日,近30天点击总量以及时间分布;
				Map<String, Object> goodsStatisticsData = statisticsGoodsServiceImpl.getGoodsStatisticsData(goodsID);
				map.put("goodsStatisticsData", goodsStatisticsData);
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CSGC: 操作失败!重试或联系后端管理员!";
				logger.info("【CSGC: 系统异常!数据统计--商品分析--查看商品数据明细系统执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CSGC: 操作失败!稍后重试或联系络驿吴彦祖!";
		}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
	
	
}
