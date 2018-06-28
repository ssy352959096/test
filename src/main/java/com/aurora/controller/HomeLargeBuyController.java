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
import com.aurora.redis.RedisUtil;
import com.aurora.service.HomeManageService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 大额采购管理
 * 
 * @author SSY 2017/8/7
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/homeLargeBuy")
public class HomeLargeBuyController extends BaseController {

	String menuUrl = "homeLargeBuy.do"; // 菜单地址(权限用)

	@Resource(name = "homeManageServiceImpl")
	private HomeManageService homeManageServiceImpl;
	@Resource(name="redisUtil")
	private RedisUtil redisUtil;


	/**
	 * 跳转到大额采购管理页面 根据商品类目 category1ID ,查询对应的大额采购商品
	 * @param category1ID
	 */
	@RequestMapping 
	public ModelAndView getLargeBuyByC(Page page,String category1ID) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		String msg = "";
		pd.put("category1ID", Tools.notEmptys(category1ID)?category1ID.trim():"10000");
		page.setPageSize(20);
		page.setPd(pd);
			try {
				// 获取商品一级类目;
				List<PageData> category1 = homeManageServiceImpl.getCategory1();
				mv.addObject("category1", category1);
				int totalRecord = homeManageServiceImpl.getNumHLB(page);
				page.setTotalRecord(totalRecord);
				List<PageData> largeBuyList = homeManageServiceImpl.getHomeLargeBuy(page);// 按类目查询商品信息;
				mv.addObject("LargeBuyList", largeBuyList);
			} catch (Exception e) {
				e.printStackTrace();
				msg = "CHLBC: 系统可能走神了,刷新重试或联系后端管理员!";
				logger.info("【CHLBC: 系统异常!大额采购页面--列表查询系统执行异常!】");
				throw new Exception(msg);
			}
			mv.addObject("pd", pd);
			mv.setViewName("/system/homeManager/largeBuy");
			return mv;
	}

	/**
	 * 根据 类目category1ID   ,商品goodsID  查询支持大额采购的商品;
	 */
	@RequestMapping(value = "/getGoodsByCGId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getGoodsByCGId(String goodsID,String category1ID) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		PageData pd = new PageData();
		if (Tools.notEmptys( category1ID ) && Tools.notEmptys( goodsID ) ) {
			pd.put("category1ID",  category1ID.trim());
			pd.put("goodsID",goodsID.trim());
			try {
				pd = homeManageServiceImpl.getGoodsByCGId(pd);// 按类目商品ID查询商品的大额采购信息;
				result = null!=pd? "success":"failed";
				msg = null!=pd? "":"该类目下没有此商品！";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CHLBC: 操作失败!重试或联系后端管理员!";
				logger.info("【CHLBC: 系统异常,大额采购--新增大额采购商品--回显系统执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CHLBC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHLBC: 参数错误,大额采购--新增大额采购商品--数据回显参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return map;
	}

	/**
	 * 保存商品大额采购宣传信息
	 * @param 数据id,商品goodsID,商品品牌brandID一级类目category1ID,宣传名 goodsNameNew, 数量 num,单位norm EXW报价exw, 有效天数validDays
	 */
	@RequestMapping(value = "/saveLargeBuy", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object saveLargeBuy(String id,String goodsID,String brandID, String norm, String category1ID,String goodsNameNew,String num,String exw,String validDays) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		PageData pd = new PageData();
		if (Tools.notEmptys(goodsID) && Tools.notEmptys(brandID) &&Tools.notEmptys(category1ID) && Tools.notEmptys(goodsNameNew)
				&& Tools.notEmptys(num) && Tools.notEmptys(exw) && Tools.notEmptys(validDays)) {
			pd.put("id", id);
			pd.put("goodsID", goodsID);
			pd.put("brandID", brandID);
			pd.put("goodsNameNew", goodsNameNew);
			pd.put("category1ID", category1ID);
			pd.put("exw", exw);
			pd.put("num", num);
			pd.put("norm", norm);
			pd.put("validDays", validDays);
			pd.put("operator", Jurisdiction.getUserEmail());// 操作者
			pd.put("time", DateUtil.getTime());
			try {
				homeManageServiceImpl.saveOrUpdateLargeBuy(pd);// 保存大额采购设置
								
				if (redisUtil.hasKey("shPurchaseCategory1")) {
					redisUtil.remove("shPurchaseCategory1");//删除redis中相应缓存
				}
				if (redisUtil.hasKey("shHomePurchase")) {
					redisUtil.remove("shHomePurchase");//删除redis中相应缓存
				}
				if (redisUtil.hasKey("shLessBuyBrand")) {
					redisUtil.remove("shLessBuyBrand");//删除redis中相应缓存
				}
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CHLBC: 操作失败!重试或联系后端管理员!";
				logger.info("【CHLBC: 系统异常!大额采购--新增大额采购商品--系统执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CHLBC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHLBC: 参数错误!大额采购--新增大额采购商品--参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return map;
	}

	
}
