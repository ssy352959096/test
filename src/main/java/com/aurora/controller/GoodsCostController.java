package com.aurora.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.entity.Page;
import com.aurora.service.GoodsManageService;
import com.aurora.util.AppUtil;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/** 商品属性
 * @author SSY 2017-09-25
 * @version 1.0
 */
@Controller
@RequestMapping(value="/goodsCost")
public class GoodsCostController extends BaseController{

	String menuUrl = "goodsCost.do"; //菜单地址(权限用)
	
	@Resource(name="goodsManageServiceImpl")
	private GoodsManageService goodsManageServiceImpl;
	
	/**
	 * 商品成本录入页面
	 * @param searchType(1订单,2商品名称),stock(1.库存为不足或者未录入,2已录),shipType(1保税仓；2海外直邮；3国内现货)
	 */
	@RequestMapping
	public ModelAndView goodsCostPage(Page page,String searchType,String stock, String shipType,String keyword,String beginTime, String endTime)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		try {
			pd.put("keyword", Tools.notEmptys(keyword)?keyword.trim():null);
			pd.put("beginTime", Tools.notEmptys(beginTime)?beginTime.trim():null);
			pd.put("endTime", Tools.notEmptys(endTime)?endTime.trim():null);
			pd.put("searchType", Tools.notEmptys(searchType)?searchType.trim():"1");
			pd.put("stock", Tools.notEmptys(stock)?stock.trim():null);
			pd.put("shipType", Tools.notEmptys(shipType)?shipType.trim():null);
			page.setPd(pd);
			page.setPageSize(20);
			int num = goodsManageServiceImpl.getGoodsCostNum(page);//所有商品总数;
			page.setTotalRecord(num);
			List<PageData> goodsList = goodsManageServiceImpl.getGoodsCostList(page);
			int allGoodsNum = goodsManageServiceImpl.getAllGoodsNum();
			int costINGoodsNum = goodsManageServiceImpl.getGoodsNumByCostIn();//已录
			mv.addObject("goodsCostList",goodsList);//阶段商品成本列表;
			mv.addObject("allGoodsNum",allGoodsNum);//所有商品总数;
			mv.addObject("costINGoodsNum",costINGoodsNum);//已录成本的商品总数;
			mv.addObject("noCostGoodsNum",allGoodsNum-costINGoodsNum);//未录成本的商品总数;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CGCC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CGCC:商品成本列表--查询执行异常】");
			throw new Exception(msg);
		}
		mv.addObject("msg",msg);
		mv.setViewName("system/goodsManager/goodsCost");
		mv.addObject("pd",pd);
		return mv;
	}

	/**
	 * 保存新增成本;
	 * @param goodsID,costPrice,buyNum
	 */
	@RequestMapping(value = "/saveGoodsCost", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object saveGoodsCost(String goodsID,String costPrice,String buyNum) throws Exception {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		if (Tools.notEmptys(goodsID)&&Tools.notEmptys(costPrice)&&Tools.notEmptys(buyNum)) {
			try {
				pd.put("goodsID", goodsID.trim());
				pd.put("costPrice", costPrice.trim());
				pd.put("buyNum", buyNum.trim());
				pd.put("time", DateUtil.getTime());
				pd.put("operator", Jurisdiction.getUserEmail());
				goodsManageServiceImpl.saveGoodsCost(pd);
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CGCC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGCC:商品成本新增--保存执行异常】");
			}
		}else{
			result = "failed";
			msg = "CGCC: 操作失败!重试或联系络驿吴彦祖!";
		}
		map.put("msg", msg);
		map.put("result", result);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}

	
	
}
