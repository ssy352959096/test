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
import com.aurora.service.BrandManageService;
import com.aurora.util.AppUtil;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 品牌管理
 * 
 * @author SSY 2017/8/8
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/brandManage")
public class BrandMangeController extends BaseController {

	String menuUrl = "brandManage.do"; // 菜单地址(权限用)

	@Resource(name = "brandManageServiceImpl")
	private BrandManageService brandManageServiceImpl;
 
	/**
	 * 根据
	 * 页码
	 * 分类
	 * 品牌名模糊查询;
	 * @return
	 * @throws Exception
	 */
	@RequestMapping 
	public ModelAndView getBrandByMh(Page page,String brandName, String bcategoryId) throws Exception {
		PageData pd = new PageData();
		ModelAndView mv = this.getModelAndView();
		String msg = "";
		pd = this.getPageData();
		brandName = Tools.notEmptys(brandName) ? brandName.trim() : null;
		bcategoryId = Tools.notEmptys(bcategoryId)&&Tools.isInteger(bcategoryId)? bcategoryId.trim() : null;
		// 当前页码的品牌列表
		List<PageData> brandAllInfo = null;
		// 品牌类目列表
		List<PageData> brandCategory = null;
		brandName = Tools.notEmptys(brandName)? brandName.trim() : null;
		bcategoryId = Tools.notEmptys(bcategoryId)? bcategoryId.trim() : null;
		pd.put("brandName", brandName);
		pd.put("bcategoryId", bcategoryId);
		page.setPd(pd);
		page.setPageSize(20);
			try {
				brandCategory = brandManageServiceImpl.getBrandCategory();
				brandAllInfo = brandManageServiceImpl.getBrandAllInfo(page);
				// 品牌总数
				int totalRecord = Integer.parseInt(brandManageServiceImpl.getBrandNum(page).get("num").toString());
				page.setTotalRecord(totalRecord);
			}catch (Exception e) {
				e.printStackTrace();
			    msg = "CBMC: 系统可能走神了,刷新重试或联系后端管理员";
			    logger.info("【CBMC:品牌管理--品牌列表查询系统异常!】");
			    throw new Exception(msg);
			}
		mv.addObject("brandInfoList", brandAllInfo);
		mv.addObject("bCategoryList", brandCategory);
		mv.addObject("msg", msg);
		mv.addObject("pd", pd);
		mv.addObject("page", page);
		mv.setViewName("system/goodsManager/brandList");
		return mv;
	}

	/**
	 * 修改品牌页面,根据品牌id回显
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBrand", produces = "application/json;charset=UTF-8")
	public ModelAndView getBrandByID(Integer brandID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String msg = "";
		pd = this.getPageData();
		List<PageData> brandCountry = null;
		List<PageData> brandByID = null;
		// 品牌类目列表
		List<PageData> brandCategory = null;
		try {
			//回显品牌信息
			brandByID = brandManageServiceImpl.getBrandByID(brandID);
			//回显所有品牌国家信息
			brandCountry = brandManageServiceImpl.getBrandCountry();
			brandCategory = brandManageServiceImpl.getBrandCategory();
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CBMC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CBMC:品牌回显--品牌查询系统执行异常】");
			throw new Exception(msg);
		}
		mv.addObject("brandByID", brandByID);
		mv.addObject("brandCountry", brandCountry);
		mv.addObject("brandCategory", brandCategory);
		mv.addObject("pd", pd);
		mv.addObject("msg", msg);
		mv.setViewName("system/goodsManager/brandUpdate");
		return mv;
	}

	/**
	 * 保存修改后的品牌
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBrand", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object updateBrand() throws Exception {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String brandId = Tools.notEmptys(pd.getString("brandId")) ? pd.getString("brandId").trim() : null;
		String brandCId = Tools.notEmptys(pd.getString("brandCId")) ? pd.getString("brandCId").trim() : null;
		String brandName = Tools.notEmptys(pd.getString("brandName")) ? pd.getString("brandName").trim() : null;
		String brandCategoryId = Tools.notEmptys(pd.getString("brandCategoryId")) ? pd.getString("brandCategoryId").trim() : null;
		String brandMap = Tools.notEmptys(pd.getString("brandMap")) ? pd.getString("brandMap").trim() : null;
		String advertiseMap = Tools.notEmptys(pd.getString("advertiseMap")) ? pd.getString("advertiseMap").trim() : null;
		String recommendMap = Tools.notEmptys(pd.getString("recommendMap")) ? pd.getString("recommendMap").trim() : null;
		String nationalFlag = Tools.notEmptys(pd.getString("nationalFlag")) ? pd.getString("nationalFlag").trim() : null;
		String countryEName = Tools.notEmptys(pd.getString("countryEName")) ? pd.getString("countryEName").trim() : null;
		String countryCName = Tools.notEmptys(pd.getString("countryCName")) ? pd.getString("countryCName").trim() : null;
		String brandDescribe1 = Tools.notEmptys(pd.getString("brandDescribe1")) ? pd.getString("brandDescribe1").trim()	: null;
		String brandDescribe2 = Tools.notEmptys(pd.getString("brandDescribe2")) ? pd.getString("brandDescribe2").trim()	: null;
		if (brandId != null&&brandCId != null && brandName != null && countryCName != null && brandDescribe1 != null
				&& brandDescribe2 != null&& recommendMap != null) {
			pd.put("brandCId", brandCId);
			pd.put("brandCategoryId", brandCategoryId);
			pd.put("brandId", brandId);
			pd.put("brandName", brandName);
			pd.put("brandMap", brandMap);
			pd.put("advertiseMap", advertiseMap);
			pd.put("recommendMap", recommendMap);
			pd.put("nationalFlag", nationalFlag);
			pd.put("countryEName", countryEName);
			pd.put("countryCName", countryCName);
			pd.put("brandDescribe1", brandDescribe1);
			pd.put("brandDescribe2", brandDescribe2);
			try {
				pd.put("time", DateUtil.getTime());
				pd.put("operator", Jurisdiction.getUserEmail());
				brandManageServiceImpl.updateBrand(pd);
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CBMC: 操作失败!重试或联系后端管理员!";
				logger.info("【CBMC:品牌修改--系统执行异常】");
			}
		} else {
			result = "failed";
			msg = "CBMC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CBMC:品牌修改--参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}

}
