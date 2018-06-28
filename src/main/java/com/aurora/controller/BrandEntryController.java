package com.aurora.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.aurora.service.BrandManageService;
import com.aurora.util.AppUtil;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/** 商品录入/审核/上架/下架/查询
 * @author BYG 2017/5/25
 * @version 1.0
 */
@Controller
@RequestMapping(value="/goodsBrand")
public class BrandEntryController extends BaseController{

	String menuUrl = "goodsBrand.do"; //菜单地址(权限用)
	
	@Resource(name="brandManageServiceImpl")
	private BrandManageService brandManageServiceImpl;
	
	/**
	 * 跳转到品牌录入
	 * 增加品牌类目
	 * @param Page
	 * @return
	 */
	@RequestMapping
	public ModelAndView goBrandEntry()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		//返回所有品牌类目;
		List<PageData> brandCategory = null;
		List<PageData> brandCountry= null;
		try {
			brandCategory = brandManageServiceImpl.getBrandCategory();
			brandCountry= brandManageServiceImpl.getBrandCountry();
		} catch (Exception e) {
			msg = "CBEC: 系统可能走神了,刷新重试或联系后端管理员";
			logger.info("【CBEC:品牌录入--获取品牌目录和品牌国家集合系统执行异常！】");
			throw new Exception(msg);
		}
		mv.addObject("brandCountry", brandCountry);
		mv.addObject("brandCategoryList", brandCategory);
		mv.addObject("pd", pd);
		mv.addObject("msg", msg);
		mv.setViewName("system/goodsManager/brandEntry");
		return mv;
	}
	
	/**添加品牌
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addBrand",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object addBrand() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String brandName = Tools.notEmptys(pd.getString("brandName"))?pd.getString("brandName").trim():null;
		String brandCId = Tools.notEmptys(pd.getString("brandCId")) ? pd.getString("brandCId").trim() : null;//品牌国家id
		String brandMap = Tools.notEmptys(pd.getString("brandMap"))?pd.getString("brandMap").trim():null;
		String brandCategoryId = Tools.notEmptys(pd.getString("brandCategoryId"))?pd.getString("brandCategoryId").trim():null;
		String advertiseMap = Tools.notEmptys(pd.getString("advertiseMap"))?pd.getString("advertiseMap").trim():null;
		String recommendMap = Tools.notEmptys(pd.getString("recommendMap"))?pd.getString("recommendMap").trim():null;
		String nationalFlag = Tools.notEmptys(pd.getString("nationalFlag"))?pd.getString("nationalFlag").trim():null;
		String countryEName = Tools.notEmptys(pd.getString("countryEName"))?pd.getString("countryEName").trim():null;
		String countryCName = Tools.notEmptys(pd.getString("countryCName"))?pd.getString("countryCName").trim():null;
		String brandDescribe1 = Tools.notEmptys(pd.getString("brandDescribe1"))?pd.getString("brandDescribe1").trim():null;
		String brandDescribe2 = Tools.notEmptys(pd.getString("brandDescribe2"))?pd.getString("brandDescribe2").trim():null;
		if (brandName!=null&&brandCId!=null&&brandMap!=null&&brandCategoryId!=null&&advertiseMap!=null&&nationalFlag!=null&&
				countryEName!=null&&countryCName!=null&&brandDescribe1!=null&&brandDescribe2!=null&&recommendMap!=null) {
		pd.put("brandName", brandName);
		pd.put("brandCId", brandCId);
		pd.put("brandCategoryId", brandCategoryId);
		pd.put("brandMap", brandMap);
		pd.put("advertiseMap", advertiseMap);
		pd.put("recommendMap", recommendMap);
		pd.put("nationalFlag", nationalFlag);
		pd.put("countryEName", countryEName);
		pd.put("countryCName", countryCName);
		pd.put("brandDescribe1", brandDescribe1);
		pd.put("brandDescribe2", brandDescribe2);
		pd.put("time", DateUtil.getTime());
		pd.put("operator", Jurisdiction.getUserEmail());
			try {
				brandManageServiceImpl.addBrand(pd);
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CBEC: 操作失败!重试或联系后端管理员!";
				logger.info("【CBEC:新增品牌--系统执行异常！】");
			}
		}else{
			result = "failed";
			msg = "CBEC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CBEC:新增品牌--参数验证未通过！】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	

}
