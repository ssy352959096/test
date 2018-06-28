package com.aurora.controller;

import java.util.*;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.entity.Page;
import com.aurora.service.PushManageService;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 *  信息推送-- 到货提醒
 * @author SSY 2017/10/23
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/pushArrival")
public class PushArrivalController extends BaseController {

	String menuUrl = "pushArrival.do"; // 菜单地址(权限用)

	@Resource(name = "pushManageServiceImpl")
	private PushManageService pushManageServiceImpl;

	 
	/**
	 * 跳转到到货提醒---商品推送管理页面;
	 * @return
	 * @param pushState(1未推送,2已经推送) goodsID(商品id)  category1ID(一级类目id)
	 */
	@RequestMapping
	public ModelAndView pushArrivalPage(Page page,String goodsID,String pushState,String category1ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String msg = "";
		List<PageData> pushArrivalList =null;
		try {
			pd.put("goodsID", Tools.notEmptys(goodsID)?goodsID.trim():null);
			pd.put("pushState", Tools.notEmptys(pushState)?pushState.trim():null);
			pd.put("category1ID", Tools.notEmptys(category1ID)?category1ID.trim():null);
			page.setPd(pd);
			page.setPageSize(20);
			pushArrivalList = pushManageServiceImpl.getPushArrival(page);//条件查询到货提醒信息推送列表;
			int totalRecord = pushManageServiceImpl.getNumPushArrival(page);//条件查询到货提醒商品总数;
			page.setTotalRecord(totalRecord);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "系统出现错误了,联系管理员处理吧!";
			throw new Exception(msg);
		}
		mv.addObject("pushArrivalList", pushArrivalList);
		mv.addObject("pd", pd);
		mv.setViewName("system/pushManage/pushArrival");
		return mv;
	}


	/**
	 * 批量更新到货提醒 状态为已推送-
	 */
	@RequestMapping(value = "/pushInfo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object pushInfo(String ids) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		if (Tools.notEmptys(ids)) {
			String[] idArray = ids.trim().split(",");
			try {
				// 信息推送 -- 批量推送;
				pushManageServiceImpl.updatePushArrival(idArray);// 批量更新到货提醒 --  状态为已推送
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				msg = "系统出现错误了,联系管理员处理吧!";
				result = "error";
			}
		} else {
			msg = "请选择您要推送的商品 !";
			result = "failed";
		}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}

	 
}
