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
import com.aurora.service.HomeManageService;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 热搜--海外直邮保税仓热卖管理
 * 
 * @author SSY 2017/8/4
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/homeHbHotSell")
public class HomeHbHotSellController extends BaseController {

	String menuUrl = "homeHbHotSell.do"; // 菜单地址(权限用)

	@Resource(name = "homeManageServiceImpl")
	private HomeManageService homeManageServiceImpl;
	@Resource(name="redisUtil")
	private RedisUtil redisUtil;

	/**
	 * 跳到海外直邮/保税仓热卖管理页面;
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView goHbHotSellEntry() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String shipTypeStr = pd.getString("shipType") != null ? pd.getString("shipType").trim() : null;
		if (shipTypeStr != null) {
			try {
				int shipType = Integer.valueOf(shipTypeStr);
				if (shipType == 1) {
					mv.setViewName("system/homeManager/warehouseHotSell");
				} else if (shipType == 2) {
					mv.setViewName("system/homeManager/overseasHotSell");
				} else {
					throw new NumberFormatException("自定义异常信息:邮寄的方式参数有误!");
				}
				pd.put("shipType", shipType);

			} catch (NumberFormatException nException) {
				nException.printStackTrace();
				msg = "CHHHSC: 系统可能走神了,刷新重试或联系络驿吴彦祖!";
				logger.info("【CHHHSC: 参数错误!保税仓/海外直邮热卖---页面跳转---参数错误!】");
				throw new Exception(msg);
			} catch (Exception e) {
				e.printStackTrace();
				msg = "CHHHSC: 系统可能走神了,刷新重试或联系后端管理员!";
				logger.info("【CHHHSC: 系统异常!保税仓/海外直邮热卖---页面跳转---系统异常!】");
				throw new Exception(msg);
			}
		} else {
			msg = "CHHHSC: 系统可能走神了,刷新重试或联系络驿吴彦祖!";
			logger.info("【CHHHSC: 参数错误!保税仓/海外直邮热卖---页面跳转---参数错误!】");
			throw new Exception(msg);
		}
		List<Integer> nullList = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			nullList.add(i);
		}
		mv.addObject("nullList", nullList);
		mv.addObject("pd", pd);
		mv.addObject("msg", msg);
		return mv;
	}

	/**
	 * 返回根据发货方式返回保税仓或者海外直邮商品信息;
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getHbHotSell", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getHbHotSell() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		String shipTypeStr = Tools.notEmptys(pd.getString("shipType")) ? pd.getString("shipType").trim() : null;
		List<PageData> HbHotSellList = null;
		if (Tools.isInteger(shipTypeStr)) {
			try {
				int shipType = Integer.valueOf(shipTypeStr);
				if (shipType != 1 && shipType != 2) {
					throw new NumberFormatException("自定义异常信息:邮寄的方式参数有误!");
				}
				pd.put("shipType", shipType);
				HbHotSellList = homeManageServiceImpl.getHbHotSell(pd);// 按发货方式查询热卖;
				result = "success";

			} catch (NumberFormatException nException) {
				nException.printStackTrace();
				result = "failed";
				msg = "CHHHSC: 操作失败!重试或联系络驿吴彦祖!";
				logger.info("【CHHHSC: 参数错误!保税仓/海外直邮热卖---数据回显---参数错误!】");
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CHHHSC: 操作失败!重试或联系后端管理员!";
				logger.info("【CHHHSC: 系统异常!保税仓/海外直邮热卖--数据回显--系统异常!】");
			}
		} else {
			result = "failed";
			msg = "CHHHSC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHHHSC: 参数错误!保税仓/海外直邮热卖---数据回显---参数错误!】");
		}
		map.put("HbHotSellList", HbHotSellList);
		map.put("pd", pd);
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 根据商品id回显单商品信息
	 */
	@RequestMapping(value = "/getGoodsById", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getGoodsById() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		String shipTypeStr = Tools.notEmptys(pd.getString("shipType")) ? pd.getString("shipType").trim() : null;
		String goodsId = Tools.notEmptys(pd.getString("goodsId")) ? pd.getString("goodsId").trim() : null;
		if (Tools.isInteger(shipTypeStr) && Tools.isInteger(goodsId)) {
			pd.put("shipType", shipTypeStr);
			pd.put("goodsId", goodsId);
			try {
				pd = homeManageServiceImpl.getGoods(pd);// 按品牌id的信息;
				result = null!=pd ? "success" : "failed";
				msg = null!=pd ? "" : "在保税仓/海外直邮中没有此商品！";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CHHHSC: 操作失败!重试或联系后端管理员!";
				logger.info("【CHHHSC: 系统异常!保税仓/海外直邮热卖--新增热卖商品数据回显--系统异常!】");
			}
		} else {
			result = "failed";
			msg = "CHHHSC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHHHSC: 参数错误!保税仓/海外直邮热卖--新增热卖商品数据回显--参数错误!】");
		}
		map.put("msg", msg);
		map.put("pd", pd);
		map.put("result", result);
		return map;
	}

	/**
	 * 保存保税仓/海外直邮热卖设置 (goodId,shipType,locationSort)
	 */
	@RequestMapping(value = "/saveHbHotSell", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object saveHbHotSell() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		String locationSort = Tools.notEmptys(pd.getString("locationSort")) ? pd.getString("locationSort").trim()
				: null;
		String goodsId = Tools.notEmptys(pd.getString("goodsId")) ? pd.getString("goodsId").trim() : null;
		String shipType = Tools.notEmptys(pd.getString("shipType")) ? pd.getString("shipType").trim() : null;
		String monthSales = pd.getString("monthSales") != null && !"".equals(pd.getString("monthSales").trim()) ? pd.getString("monthSales").trim() : null;
		String goodsNameNew = pd.getString("goodsNameNew") != null && !"".equals(pd.getString("goodsNameNew").trim()) ? pd.getString("goodsNameNew").trim() : null;
		if (Tools.isInteger(locationSort) && goodsId != null && Tools.isInteger(shipType)) {

			if (shipType.equals("1")) {// 保税仓热卖
				pd.put("homeLocation", 3);
			} else if (shipType.equals("2")) {
				pd.put("homeLocation", 4);
			} else {
				result = "failed";
				msg = "CHHHSC: 操作失败!重试或联系络驿吴彦祖!";
				logger.info("【CHHHSC: 参数错误!保税仓/海外直邮热卖--新增热卖商品--参数错误!】");
				map.put("result", result);
				map.put("msg", msg);
				map.put("pd", pd);
				return map;
			}
			pd.put("locationSort", locationSort);
			pd.put("goodsId", goodsId);
			pd.put("shipType", shipType);
			pd.put("goodsNameNew", goodsNameNew);
			pd.put("monthSales", monthSales);
			Session session = Jurisdiction.getSession();
			String userEmail = (String) session.getAttribute(Const.SESSION_USER_EMAIL);
			pd.put("operator", userEmail);// 操作者
			pd.put("operateTime", DateUtil.getDay());
			try {
				homeManageServiceImpl.saveOrUpdateHbHotSell(pd);// 保存或修改保税仓或者德国直邮热卖设置;
				//更新后删除redis中相应缓存数据
				if (shipType.equals("1")) {
					if (redisUtil.hasKey("shHomeBHotSell")) {
						redisUtil.remove("shHomeBHotSell");
					}
				} else if (shipType.equals("2")) {
					if (redisUtil.hasKey("shHomeHHotSell")) {
						redisUtil.remove("shHomeHHotSell");
					}
				}
				
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CHHHSC: 操作失败!重试或联系后端管理员!";
				logger.info("【CHHHSC: 系统异常!保税仓/海外直邮热卖--新增热卖商品--系统异常!】");
			}
		} else {
			result = "failed";
			msg = "CHHHSC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHHHSC: 参数错误!保税仓/海外直邮热卖--新增热卖商品--参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return map;
	}

}
