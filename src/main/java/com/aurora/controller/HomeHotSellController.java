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
import com.aurora.util.AppUtil;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;

/** 商城首页热搜管理之本站热卖
 * @author BYG 2017/5/25
 * @version 1.0
 */
@Controller
@RequestMapping(value="/homeHotSell")
public class HomeHotSellController extends BaseController{

	String menuUrl = "homeHotSell.do"; //菜单地址(权限用)
	
	@Resource(name="homeManageServiceImpl")
	private HomeManageService homeManageServiceImpl;
	@Resource(name="redisUtil")
	private RedisUtil redisUtil;
	
	/**跳转到首页热搜管理本站热卖维护页面
	 * @param Page
	 * @return
	 */
	@RequestMapping
	public ModelAndView goHomeHotSell()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		pd.put("category1ID", 1000);
		List<PageData> category1List = null;
		try {
//不删		category1List = homeManageServiceImpl.getCategory1();
			category1List = homeManageServiceImpl.getCategory14();//增加爱他美+喜宝后变更
		} catch (Exception e) {
			msg = "CHHSC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CHHSC: 系统异常！ 本站热卖管理---一级类目获取执行异常!】");
			throw new Exception(msg);
		}
	
		List<Integer> nullList = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			nullList.add(i);
		}
		mv.addObject("category1List", category1List);
		mv.addObject("nullList", nullList);
		mv.addObject("msg", msg);
		mv.setViewName("system/homeManager/homeHotSell");
		return mv;
	}
	
	/**跳转到首页热搜管理本站热卖维护页面
	 * @param Page
	 * @return
	 */
	@RequestMapping(value="/getHomeHotSell",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object getHomeHotSell(String category1ID )throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		category1ID = category1ID != null && !"".equals(category1ID.trim()) ? category1ID.trim() : null;
		pd.put("category1ID", category1ID);
		if (category1ID != null) {
			try {
				List<PageData> hotSellList = homeManageServiceImpl.getHotSellList(pd);      //根据 页码/一级类目ID 获取本站热卖商品
				map.put("hotSellList", hotSellList);
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CHHSC: 操作失败!重试或联系后端管理员!";
				logger.info("【CHHSC: 系统异常,本站热卖管理--条件查询本站热卖列表--系统执行异常!】");
			}
		}else {
			result = "failed";
			msg = "CHHSC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHHSC: 参数错误,本站热卖管理--条件查询本站热卖列表--参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**根据 商品ID/一级类目ID 搜索商品
	 * @param Page
	 * @return
	 */
	@RequestMapping(value="/serchGoods",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object serchGoods() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String category1ID = pd.getString("category1ID") != null && !"".equals(pd.getString("category1ID").trim()) ? pd.getString("category1ID").trim() : null;
		String goodsID = pd.getString("goodsID") != null && !"".equals(pd.getString("goodsID").trim()) ? pd.getString("goodsID").trim() : null;
		if (category1ID != null && goodsID != null) {
			//专为增加爱他美+喜宝用
			if(category1ID.equals("1000") || category1ID.equals("2000")){
				category1ID = "10000";
			}
			
			pd.put("category1ID", category1ID);
			pd.put("goodsID", goodsID);
			List<PageData> goods = new ArrayList<PageData>();
			try {
				goods = homeManageServiceImpl.serchGoods(pd); //根据搜索条件获取结果集商品
				map.put("goods", goods);
				result = !goods.isEmpty()? "success" : "failed";
				msg = !goods.isEmpty()? "" : "该类目下没有此商品";
			} catch (Exception e) {
				result = "error";
				msg = "CHHSC: 操作失败!重试或联系后端管理员!";
				logger.info("【CHHSC：系统异常!本站热卖管理--新增本站热卖商品回显--系统异常！】");
			}
		}else {
			result= "failed";
			msg = "CHHSC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHHSC: 参数错误!本站热卖管理--新增本站热卖商品回显--参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**根据 页码/位置排序/一级类目ID 更新首页热卖商品
	 * @param category1ID  pageNum locationSort goodsID advertiseMap
	 * @return
	 */
	@RequestMapping(value="/updateHomeHotSell",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object updateHomeHotSell() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String category1ID = pd.getString("category1ID") != null && !"".equals(pd.getString("category1ID").trim()) ? pd.getString("category1ID").trim() : null;
		String locationSort = pd.getString("locationSort") != null && !"".equals(pd.getString("locationSort").trim()) ? pd.getString("locationSort").trim() : null;
		String goodsID = pd.getString("goodsID") != null && !"".equals(pd.getString("goodsID").trim()) ? pd.getString("goodsID").trim() : null;
		String advertiseMap = pd.getString("advertiseMap") != null && !"".equals(pd.getString("advertiseMap").trim()) ? pd.getString("advertiseMap").trim() : null;
		String goodsNameNew = pd.getString("goodsNameNew") != null && !"".equals(pd.getString("goodsNameNew").trim()) ? pd.getString("goodsNameNew").trim() : null;
		if (category1ID != null &&goodsNameNew!=null && locationSort != null && goodsID != null && advertiseMap != null) {
			pd.put("category1ID", category1ID);
			pd.put("locationSort", locationSort);
			pd.put("goodsId", goodsID);
			pd.put("advertiseMap", advertiseMap);
			pd.put("goodsName", goodsNameNew);
			Session session = Jurisdiction.getSession();
			String operator = (String) session.getAttribute(Const.SESSION_USER_EMAIL);
			pd.put("operator", operator);
			pd.put("operateTime", DateUtil.getTime());
			pd.put("homeLocation", 1);
			try {
				PageData homeHotSellID = homeManageServiceImpl.getHomeHotSellID(pd);
				if (homeHotSellID == null) {
					homeManageServiceImpl.addHomeHotSell(pd);     	   //添加商品到首页热卖
				}else{
					homeManageServiceImpl.updateHomeHotSell(pd);       //更新首页热卖商品
				}
				
				if (redisUtil.hasKey("shHotSellList")) {
					redisUtil.remove("shHotSellList");//删除redis中相应缓存
				}
				if (redisUtil.hasKey("shHotSellCategory1")) {
					redisUtil.remove("shHotSellCategory1");
				}
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CHHSC: 操作失败!重试或联系后端管理员!！";
				logger.info("【CHHSC: 系统异常!本站热卖管理--更新本站热卖商品--系统执行异常!】");
			}
		}else {
			result= "failed";
			msg = "CHHSC: 操作失败!重试或联系络驿吴彦祖!！";
			logger.info("【CHHSC: 参数错误!本站热卖管理--更新本站热卖商品--参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	
}
