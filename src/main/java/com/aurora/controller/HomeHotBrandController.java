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
import com.aurora.service.BrandManageService;
import com.aurora.service.HomeManageService;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;

/**
 * 热搜--热门品牌管理
 * 
 * @author SSY 2017/8/4
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/homeHotBrand")
public class HomeHotBrandController extends BaseController {

	String menuUrl = "homeHotBrand.do"; // 菜单地址(权限用)

	@Resource(name = "homeManageServiceImpl")
	private HomeManageService homeManageServiceImpl;

	@Resource(name = "brandManageServiceImpl")
	private BrandManageService brandManageServiceImpl;

	/**
	 * 跳转到热门品牌录入页面
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView goHotBrandEntry() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		List<PageData> brandList = null;// 获取所有品牌id和名称;

		List<PageData> hotBrandList = null;// 按页码热门品牌的信息;
		try {
			brandList = brandManageServiceImpl.getBrand(); 
			hotBrandList = homeManageServiceImpl.getHotBrand(1); 
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CHHBC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CHHBC: 系统异常!热门品牌--页面跳转--列表查询执行异常！】");
			throw new Exception(msg);
		}
		
		List<Integer> nullList = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			nullList.add(i);
		}
		mv.addObject("nullList", nullList);
		mv.addObject("brand", brandList);
		mv.addObject("hotBrand", hotBrandList);
		mv.addObject("pd", pd);
		mv.setViewName("system/homeManager/hotBrand");
		return mv;
	}

	/**
	 * 下拉框选择页码,热门品牌回显;
	 */
	@RequestMapping(value = "/getHotBrandByPage", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getHotBrandByPage(Integer pageNum) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
	 
		List<PageData> hotBrandList = null;
		if (pageNum != null) {
			try {
				hotBrandList = homeManageServiceImpl.getHotBrand(pageNum);// 按页码热门品牌的信息;
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CHHBC: 操作失败!刷新重试或联系后端管理员!";
				logger.info("【CHHBC: 系统异常,热门品牌--页码切换查询系统异常！】");
			}
		} else {
			result = "failed";
			msg = "CHHBC: 操作失败!刷新重试或联系络驿吴彦祖!";
			logger.info("【CHHBC: 热门品牌--页码切换查询参数错误！】");
		}
		List<Integer> nullList = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			nullList.add(i);
		}
		map.put("nullList", nullList);
		map.put("hotBrandList", hotBrandList);
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 回显单品牌信息
	 */
	@RequestMapping(value = "/getBrandById", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getBrandById(String brandId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";

		if (brandId != null && !"".equals(brandId.trim())) {
			try {
				pd = homeManageServiceImpl.getBrand(brandId.trim());// 按品牌id的信息;
				result = null != pd ? "success" : "failed";
				msg = null != pd ? "" : "该品牌不存在!";
			} catch (Exception e) {
				result = "error";
				msg = "CHHBC: 操作失败!刷新重试或联系后端管理员!";
				logger.info("【CHHBC: 系统异常!新增热门品牌--品牌回显查询系统执行异常！】");
			}
		} else {
			result = "failed";
			msg = "CHHBC: 操作失败!刷新重试或联系络驿吴彦祖!";
			logger.info("【CHHBC: 参数错误!新增热门品牌--品牌回显--参数错误！】");
		}
		map.put("pd", pd);
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 保存热门品牌
	 */
	@RequestMapping(value = "/saveHotBrand", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object saveHotBrand() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		String msg = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		String pageNum = pd.getString("pageNum") != null && !"".equals(pd.getString("pageNum").trim())
				? pd.getString("pageNum").trim() : null;
		String locationSort = pd.getString("locationSort") != null && !"".equals(pd.getString("locationSort").trim())
				? pd.getString("locationSort").trim() : null;
		String brandId = pd.getString("brandId") != null && !"".equals(pd.getString("brandId").trim())
				? pd.getString("brandId").trim() : null;
		String brandName = pd.getString("brandName") != null && !"".equals(pd.getString("brandName").trim())
				? pd.getString("brandName").trim() : null;
		if (pageNum != null && locationSort != null && brandId != null&&brandName!=null) {
			pd.put("pageNum", pageNum);
			pd.put("locationSort", locationSort);
			pd.put("brandId", brandId);
			pd.put("brandName", brandName);
			Session session = Jurisdiction.getSession();
			String userEmail = (String) session.getAttribute(Const.SESSION_USER_EMAIL);
			pd.put("operator", userEmail);// 操作者
			pd.put("operateTime", DateUtil.getDay());
			try {
				homeManageServiceImpl.saveOrUpdateHotBrand(pd);// 保存热门品牌;
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CHHBC: 操作失败!刷新重试或联系后端管理员!";
				logger.info("【CHHBC: 系统异常!新增热门品牌--热门品牌保存--系统执行异常！】");
			}
		} else {
			result = "failed";
			msg = "CHHBC: 操作失败!刷新重试或联系络驿吴彦祖!";
			logger.info("【CHHBC: 参数错误!新增热门品牌--热门品牌保存--参数错误！】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return map;
	}

}
