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
import com.aurora.service.HomeManageService;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 *  热搜-- 新货推荐
 * @author SSY 2017/8/4
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/homeNewGoods")
public class HomeNewGoodsController extends BaseController {

	String menuUrl = "homeNewGoods.do"; // 菜单地址(权限用)

	@Resource(name = "homeManageServiceImpl")
	private HomeManageService homeManageServiceImpl;

	 
	/**
	 * 跳转到新货推荐管理页面,回显第一页新货;
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView goNewGoodsEntry() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		List<PageData> newGoodsList =null;
		try {
			newGoodsList = homeManageServiceImpl.getNewGoods(1);// 按页码新货推荐的信息;
		} catch (Exception e) {
			 msg = "CHNGC: 系统可能走神了,刷新重试或联系后端管理员!";
			 logger.info("【CHNGC: 新货推荐--数据回显系统执行异常！】");
			 throw new Exception(msg);
		}
		 
		List<Integer> nullList = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			nullList.add(i);
		}
		mv.addObject("nullList", nullList);
		mv.addObject("newGoods", newGoodsList);
		mv.addObject("pd", pd);
		mv.setViewName("system/homeManager/newGoods");
		return mv;
	}

	/**
	 * 下拉框选择页码,新货推荐回显;
	 */
	@RequestMapping(value = "/getNewGoodsByPage", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getNewGoodsByPage(Integer pageNum) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		List<PageData> newGoodsList = null;
		if (pageNum != null) {
			try {
				 newGoodsList = homeManageServiceImpl.getNewGoods(pageNum);// 按页码新货推荐的信息;
				 result = "success";
			} catch (Exception e) {
				result = "error";
				 msg = "CHNGC: 操作失败!重试或联系后端管理员!！";
				 logger.info("【CHNGC: 新货推荐--页码切换系统执行异常！】");
			}
		} else {
			result = "failed";
			 msg = "CHNGC: 操作失败!重试或联系络驿吴彦祖!";
			 logger.info("【CHNGC: 参数错误,新货推荐--页码切换-参数错误！】");
		}
		map.put("newGoods", newGoodsList);
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
		String goodsId = Tools.notEmptys(pd.getString("goodsId"))? pd.getString("goodsId").trim() : null;
		if (Tools.isInteger(goodsId) ) {
			pd.put("goodsId", goodsId.trim());
			try {
				pd = homeManageServiceImpl.getGoods(pd);// 按品牌id的信息;
				result = "success";
				if (pd==null) {
					result = "failed";
					msg = "没有此商品,请检查是否输入错误!";
				}else{
					pd.put("goods_name_new", "");
				}
			} catch (Exception e) {
				result = "error";
				msg = "CHNGC: 操作失败!重试或联系后端管理员!";
				logger.info("【CHNGC: 系统异常,新货推荐--新增新货--商品回显系统执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CHNGC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CHNGC: 参数错误,新货推荐--新增新货商品--商品回显参数错误!】");
		}
		map.put("msg", msg);
		map.put("pd", pd);
		map.put("result", result);
		return map;
	}

	/**
	 * 保存新货推荐商品 (goodId,pageNum,locationSort)
	 */
	@RequestMapping(value = "/saveNewGoods", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object saveNewGoods() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		String pageNum = pd.getString("pageNum") != null && !"".equals(pd.getString("pageNum").trim())
				? pd.getString("pageNum").trim() : null;
		String locationSort = pd.getString("locationSort") != null && !"".equals(pd.getString("locationSort").trim())
				? pd.getString("locationSort").trim() : null;
		String goodsId = pd.getString("goodsId") != null && !"".equals(pd.getString("goodsId").trim())
				? pd.getString("goodsId").trim() : null;
		String goodsName = pd.getString("goodsName") != null && !"".equals(pd.getString("goodsName").trim()) ? pd.getString("goodsName").trim() : null;
		String goodsNameNew = pd.getString("goodsNameNew") != null && !"".equals(pd.getString("goodsNameNew").trim()) ? pd.getString("goodsNameNew").trim() : null;
		if (pageNum != null && goodsNameNew != null && goodsName!=null && locationSort != null && goodsId != null) {
			pd.put("pageNum", pageNum);
			pd.put("locationSort", locationSort);
			pd.put("goodsId", goodsId); 
			pd.put("goodsName", goodsName);
			Session session = Jurisdiction.getSession();
			String userEmail = (String) session.getAttribute(Const.SESSION_USER_EMAIL);
			pd.put("operator", userEmail);// 操作者
			pd.put("operateTime", DateUtil.getDay());
			pd.put("homeLocation", 2);
			try {
				homeManageServiceImpl.saveOrUpdateNewGoods(pd);// 保存或修改新货推荐;
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CHNGC: 操作失败!重试或联系后端管理员！";
				logger.info("【CHNGC: 系统异常!新货推荐--更新新货推荐系统执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CHNGC: 操作失败!重试或联系络驿吴彦祖！";
			logger.info("【CHNGC: 参数错误!新货推荐--更新新货推荐系统--参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return map;
	}

}
