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
 * 淘宝京东热卖
 * 
 * @author SSY 2017/8/8
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/homeTbHotSell")
public class HomeTbHotSellController extends BaseController {

	String menuUrl = "homeTbHotSell.do"; // 菜单地址(权限用)

	@Resource(name = "homeManageServiceImpl")
	private HomeManageService homeManageServiceImpl;
	@Resource(name="redisUtil")
	private RedisUtil redisUtil;

	/**
	 * 跳转到淘宝京东热卖管理页面;
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView gohomeTbHotSell() throws Exception {
		ModelAndView mv = this.getModelAndView();
		List<Integer> nullList = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			nullList.add(i);
		}
		mv.addObject("nullList", nullList);
		mv.setViewName("/system/homeManager/tjSellHotSell");
		return mv;
	}

	/**
	 * tj_type
	 * 根据String tjType ,查询淘宝京东热卖
	 */
	@RequestMapping(value = "/getTbHotSell", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getTbHotSell(String tjType) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		List<PageData>  tbHotSellList = null;
		tjType = Tools.notEmptys(tjType) ? tjType.trim() : null;
		if (tjType == null || (!tjType.equals("t") && !tjType.equals("j"))) {
			tjType = "t";// 默认淘宝;
		}
		try {
			tbHotSellList = homeManageServiceImpl.gethomeTbHotSellList(tjType);// 默认回显数据库第一个类目下的大额采购商品;
			result = "success";
		} catch (Exception e) {
			result = "error";
			msg= "CHTBHSC: 操作失败!重试或联系后端管理员!";
			logger.info("【CHTBHSC: 系统异常!淘宝京东热卖--热卖商品回显系统执行异常!】");
		}
		map.put("tbHotSellList", tbHotSellList);
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 根据 商品Id (String GoodsId)查询商品;
	 */
	@RequestMapping(value = "/getGoodsById", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getGoodsById(String GoodsId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
	    PageData pd = new PageData();
	    pd = this.getPageData();
		if (Tools.notEmptys(GoodsId) && Tools.isInteger(GoodsId)) {
			pd.put("goodsId", GoodsId.trim());
			try {
				pd = homeManageServiceImpl.getGoods(pd);// 按类目商品ID查询商品信息;
				result = "success";
				if (pd == null) {
					result = "failed";
					msg = "没有此商品,请检查是否输入错误!";
				}
			} catch (Exception e) {
				result = "error";
				msg = "CHTBHSC: 操作失败!重试或联系后端管理员!！";
				logger.info("【CHTBHSC: 系统异常!淘宝京东热卖--新增热卖商品回显信息--系统执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CHTBHSC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHTBHSC: 参数错误!淘宝京东热卖--新增热卖商品回显信息--参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return map;
	}

	/**
	 * 保存京东淘宝热卖设置 (商品goodsId,商品goodsName,京东淘宝标记String tjType,位置locationSort,价格走势String priceSign,价格走势百分比,String priceIndex,
	 * 销量走势String saleSign,价格走势百分比,String saleIndex,时间段 String timePriod)
	 */
	@RequestMapping(value = "/saveTbHotSell", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object saveTbHotSell() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		String goodsId = Tools.notEmptys(pd.getString("goodsId")) ? pd.getString("goodsId").trim() : null;
		String goodsName = Tools.notEmptys(pd.getString("goodsName")) ? pd.getString("goodsName").trim() : null;
		String tjType = Tools.notEmptys(pd.getString("tjType")) ? pd.getString("tjType").trim() : null;
		String locationSort = Tools.notEmptys(pd.getString("locationSort")) ? pd.getString("locationSort").trim() : null;
		String priceSign = Tools.notEmptys(pd.getString("priceSign")) ? pd.getString("priceSign").trim() : null;
		String priceIndex = Tools.notEmptys(pd.getString("priceIndex")) ? pd.getString("priceIndex").trim() : null;
		String saleSign = Tools.notEmptys(pd.getString("saleSign")) ? pd.getString("saleSign").trim() : null;
		String saleIndex = Tools.notEmptys(pd.getString("saleIndex")) ? pd.getString("saleIndex").trim() : null;
		String timePriod = Tools.notEmptys(pd.getString("timePriod")) ? pd.getString("timePriod").trim() : null;
		String goodsNameNew = pd.getString("goodsNameNew") != null && !"".equals(pd.getString("goodsNameNew").trim()) ? pd.getString("goodsNameNew").trim() : null;
		if (Tools.isInteger(goodsId) && Tools.isInteger(priceSign) && Tools.isInteger(saleSign) && Tools.isInteger(locationSort)) {
			pd.put("goodsId", goodsId);
			pd.put("goodsName", goodsName);
			pd.put("tjType", tjType);
			pd.put("locationSort", locationSort);
			pd.put("priceSign", priceSign);
			pd.put("priceIndex", priceIndex);
			pd.put("saleSign", saleSign);
			pd.put("saleIndex", saleIndex);
			pd.put("timePriod", timePriod);
			pd.put("goodsNameNew", goodsNameNew);
			Session session = Jurisdiction.getSession();
			String userEmail = (String) session.getAttribute(Const.SESSION_USER_EMAIL);
			pd.put("operator", userEmail);// 操作者
			pd.put("operateTime", DateUtil.getDay());
			pd.put("homeLocation", 7);
			try {
				homeManageServiceImpl.saveOrUpdateTbHotSell(pd);// 保存或修改淘宝京东热卖设置;
				if (tjType.equals("t")) {
					if (redisUtil.hasKey("shtHotSell")) {
						redisUtil.remove("shtHotSell");//删除redis中相应缓存
					}
				} else if(tjType.equals("j")){
					if (redisUtil.hasKey("shjHotSell")) {
						redisUtil.remove("shjHotSell");//删除redis中相应缓存
					}
				}
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CHTBHSC: 操作失败!重试或联系后端管理员!";
				logger.info("【CHTBHSC: 系统异常!淘宝京东热卖---更新热卖商品信息--系统执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CHTBHSC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHTBHSC: 参数错误!淘宝京东热卖---更新热卖商品信息--参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return map;
	}

}
