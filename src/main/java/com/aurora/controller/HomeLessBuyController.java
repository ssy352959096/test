package com.aurora.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.redis.RedisUtil;
import com.aurora.service.BrandManageService;
import com.aurora.service.HomeManageService;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 小额批发管理
 * 
 * @author SSY 2017/8/7
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/homeLessBuy")
public class HomeLessBuyController extends BaseController {

	String menuUrl = "homeLessBuy.do"; // 菜单地址(权限用)

	@Resource(name = "homeManageServiceImpl")
	private HomeManageService homeManageServiceImpl;
	@Resource(name = "brandManageServiceImpl")
	private BrandManageService brandManageServiceImpl;
	@Resource(name="redisUtil")
	private RedisUtil redisUtil;

	/**
	 * 跳转到小額批发管理页面; 返回一级类目集合 根据默认位置一品牌/数据库类目表默认第一个/查询小额批发商品;
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView gohomeLessBuy() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		List<PageData> getHomeLessBuyList = null;
		List<PageData> category1 = null;
		pd.put("blocationSort", 1);
		try {
			// 获取一级类目;
			category1 = homeManageServiceImpl.getCategory1();
			getHomeLessBuyList = homeManageServiceImpl.getHomeLessBuy(pd);// 按品牌位置;
		} catch (Exception e) {
			msg = "CHLBC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CHLBC: 系统异常!小额批发--页面列表查询系统执行异常!】");
			throw new Exception(msg);
		}
		List<Integer> nullList = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			nullList.add(i);
		}
		mv.addObject("nullList", nullList);
		mv.addObject("LessBuyList", getHomeLessBuyList);
		mv.addObject("category1", category1);

		mv.addObject("pd", pd);
		mv.addObject("msg", msg);
		mv.setViewName("system/homeManager/lessBuy");
		return mv;
	}

	/**
	 * 获取品牌列表;
	 */
	@RequestMapping(value = "/getBrand", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getBrand() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		List<PageData> brand = null;
		try {
			brand = brandManageServiceImpl.getBrand();
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			msg = "CHLBC: 操作失败!重试或联系后端管理员!";
			logger.info("【CHLBC: 系统异常!小额批发--品牌列表回显系统执行异常!!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("brand", brand);
		return map;
	}

	/**
	 * 根据品牌位置BS(String blocationSort)和商品类目C(String category1ID),查询对应的小额批发商品
	 */
	@RequestMapping(value = "/getLessBuyByBSC", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getLessBuyByBSC() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";

		List<PageData> getHomeLessBuyList = null;
		if (Tools.notEmptys(pd.getString("blocationSort")) && Tools.notEmptys(pd.getString("category1ID"))) {
			pd.put("blocationSort", pd.getString("blocationSort").trim());
			pd.put("category1ID", pd.getString("category1ID").trim());

			try {
				getHomeLessBuyList = homeManageServiceImpl.getHomeLessBuy(pd);// 按品牌位置的小额批发商品信息;
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CHLBC: 操作失败!重试或联系后端管理员!";
				logger.info("【CHLBC: 系统异常!小额批发--条件回显小额批发商品列表--系统执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CHLBC: 操作失败!重试或联系络驿吴彦祖!!";
			logger.info("【CHLBC: 参数错误!小额批发--条件回显小额批发商品列表--参数错误!】");
		}
		map.put("pd", pd);
		map.put("LessBuyList", getHomeLessBuyList);
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 根据品牌B(String brandId)/类目C(String category1ID)/商品GId (String
	 * GoodsId)查询商品的小额批发信息;
	 * 
	 */
	@RequestMapping(value = "/getGoodsByBCGId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getGoodsByBCGId() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		String brandId = "";
		String brandName = "";
		if (Tools.notEmptys(pd.getString("brandId")) && Tools.notEmptys(pd.getString("category1ID"))
				&& Tools.notEmptys(pd.getString("GoodsId"))) {
			brandId = pd.getString("brandId").trim();
			brandName = pd.getString("brandName").trim();
			pd.put("brandId", brandId);
			pd.put("category1ID", pd.getString("category1ID").trim());
			pd.put("GoodsId", pd.getString("GoodsId").trim());
			try {
				pd = homeManageServiceImpl.getGoodsByBCGId(pd);// 按品牌位置的小额批发商品信息;
				if (null==pd) {
					msg = "该类目、品牌下没有此商品";
					result = "failed";
				}else{
					result = "success";
					pd.put("brandName", brandName);
					pd.put("brandId", brandId);
				}
			} catch (Exception e) {
				msg = "CHLBC: 操作失败!重试或联系后端管理员!";
				result = "error";
				logger.info("【CHLBC: 系统异常!小额批发--新增商品回显系统执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CHLBC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHLBC: 参数错误,小额批发--新增商品回显失败--参数错误!】");
		}
		 
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return map;
	}

	/**
	 * 保存小额批发设置 (商品goodsId, 品牌brandId,品牌名 brandName,一级类目category1ID,
	 * 品牌位置blocationSort,商品位置glocationSort)
	 */
	@RequestMapping(value = "/saveLessBuy", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object saveLessBuy() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		String goodsId = Tools.notEmptys(pd.getString("goodsId")) ? pd.getString("goodsId").trim() : null;
		String brandId = Tools.notEmptys(pd.getString("brandId")) ? pd.getString("brandId").trim() : null;
		String blocationSort = Tools.notEmptys(pd.getString("blocationSort")) ? pd.getString("blocationSort").trim()
				: null;
		String glocationSort = Tools.notEmptys(pd.getString("glocationSort")) ? pd.getString("glocationSort").trim()
				: null;
		String category1ID = Tools.notEmptys(pd.getString("category1ID")) ? pd.getString("category1ID").trim() : null;
		String brandName = Tools.notEmptys(pd.getString("brandName")) ? pd.getString("brandName").trim() : null;
		String goodsNameNew = pd.getString("goodsNameNew") != null && !"".equals(pd.getString("goodsNameNew").trim()) ? pd.getString("goodsNameNew").trim() : null;
		if (Tools.isInteger(goodsId) && Tools.isInteger(brandId) && Tools.isInteger(blocationSort)
				&& Tools.isInteger(glocationSort) && Tools.isInteger(category1ID) && brandName != null) {
			pd.put("glocationSort", glocationSort);
			pd.put("goodsId", goodsId);
			pd.put("brandId", brandId);
			pd.put("blocationSort", blocationSort);
			pd.put("category1ID", category1ID);
			pd.put("brandName", brandName);
			pd.put("goodsNameNew", goodsNameNew);
			Session session = Jurisdiction.getSession();
			String userEmail = (String) session.getAttribute(Const.SESSION_USER_EMAIL);
			pd.put("operator", userEmail);// 操作者
			pd.put("operateTime", DateUtil.getDay());
			pd.put("homeLocation", 5);
			try {
				homeManageServiceImpl.saveOrUpdateLessBuy(pd);// 保存或修改保税仓或者德国直邮热卖设置;
				
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
				msg = "CHLBC: 操作失败!重试或联系后端管理员!!";
				logger.info("【CHLBC:更新小额批发商品执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CHLBC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHLBC: 更新小额批发商品参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return map;
	}

}
