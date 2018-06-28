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
import com.aurora.service.GoodsManageService;
import com.aurora.util.AppUtil;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/** 商品属性
 * @author BYG 2017/5/25
 * @version 1.0
 */
@Controller
@RequestMapping(value="/goodsProperties")
public class GoodsPropertiesController extends BaseController{

	String menuUrl = "goodsProperties.do"; //菜单地址(权限用)
	
	@Resource(name="goodsManageServiceImpl")
	private GoodsManageService goodsManageServiceImpl;
	@Resource(name="brandManageServiceImpl")
	private BrandManageService brandManageServiceImpl;
	
	
	/**
	 * 跳到商品属性管理页面
	 * 查询所有的商品属性列表
	 * 查询出所有的一级类目
	 */
	@RequestMapping
	public ModelAndView goodsProperties(Page page,String category1ID,String category2ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		category1ID = Tools.notEmptys(category1ID)?category1ID.trim():null;
		category2ID = Tools.notEmptys(category2ID)?category2ID.trim():null;
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		pd.put("category1ID", category1ID);
		pd.put("category2ID", category2ID);
		page.setPd(pd);
		page.setPageSize(20);

		List<PageData> goodsCategory1 = null;    //所有的一级类目
		 
		List<PageData> allProperties = null;	//二级类目商品属性
		
		try {
			goodsCategory1 = goodsManageServiceImpl.getGoodsCategory("0");//所有一级类目列表;
				
			int num = goodsManageServiceImpl.getAllPropertiesNum(page);
			page.setTotalRecord(num);
			
			allProperties = goodsManageServiceImpl.getAllProperties(page);
				 
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CGPC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CGPC:系统异常!商品属性列表--查询执行异常!】");
			throw new Exception(msg);
		}
		mv.addObject("msg",msg);
		mv.addObject("goodsCategory1",goodsCategory1);
		mv.addObject("goodsProperties",allProperties); 
		mv.setViewName("system/goodsManager/goodsProperties");
		mv.addObject("pd",pd);
		return mv;
	}
 

	 
	
	/**
	 * 根据一级类目,查询二级类目; 
	 * @param category1ID
	 * @return
	 */
	@RequestMapping(value="/getCategoryBy1ID",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object getCategoryBy1ID(String category1ID)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		List<PageData> goodsCategory2 = null;	//根据一级类目查询出所有的二级类目;
		if (Tools.notEmptys(category1ID)) {
			try {
				goodsCategory2 = goodsManageServiceImpl.getGoodsCategory(category1ID);
				result = "success";
			}catch (Exception e) {
				result = "error";
				msg = "CGPC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGPC:系统错误!根据一级类目,查询二级类目执行错误!】");
			}
		}else{
			result = "failed";
			msg = "CGPC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGPC:参数错误!一级类目ID参数错误!】");
		}
		map.put("goodsCategory2",goodsCategory2);//二级类目商品属性
		map.put("result", result);
		map.put("msg", msg);
		
		return map;
	} 
	
	
	/**
	 * 根据商品二级类目,查询该类目下的商品属性; 
	 * @param category2ID
	 * @return
	 */
	@RequestMapping(value="/getGoodsProperties",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object getGoodsProperties(String category2ID)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
//		category2ID = "10100";//测试
		List<PageData> properties = null;		//要查询的二级类目商品属性;单个
			
		if (Tools.notEmptys(category2ID)) {
			 
			try {
				properties = goodsManageServiceImpl.getGoodsProperties(category2ID);	//商品一级类目
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CGPC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGPC:系统异常!根据商品类目id查询商品属性系统异常!】");
			}
		 }else{
			result = "failed";
			msg = "CGPC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGPC:参数错误!商品二级类目ID参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("properties", properties);
		return map;
	}
		
	/**
	 * 保存修改的类目商品属性
	 * @param category2ID二级类目id,category2Name,属性字符串properties,
	 * @return
	 */
	@RequestMapping(value="/saveGoodsProperties",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object saveGoodsProperties(String category2ID,String category2Name,String properties) throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		
//		category2ID ="10100";
//		category2Name = "hehe";
//		properties = "重量,长度";//测试
				
		if (Tools.notEmpty(properties)&&Tools.notEmptys(category2ID)) {
			pd.put("properties", properties);
			pd.put("category2ID", category2ID);
			pd.put("category2Name", category2Name);
			try {
				goodsManageServiceImpl.saveGoodsProperties(pd);	 //新增或修改
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CGPC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGPC:系统异常!商品属性保存--系统异常！】");
			}
		}else {
			result = "failed";
			msg = "CGPC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGPC:参数错误!商品属性保存--参数错误！】");
		}
		map.put("result", result);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
	 
}
