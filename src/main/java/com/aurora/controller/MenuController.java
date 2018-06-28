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
import com.aurora.service.MenuService;
import com.aurora.util.AppUtil;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 描述:系统菜单管理
 * 创建:BYG 2017/5/25
 * 修改:
 * @version 1.0
 */
@Controller
@RequestMapping(value="/systemMenu")
public class MenuController extends BaseController{

	String menuUrl = "systemMenu.do"; //菜单地址(权限用)
	@Resource(name="menuServiceImpl")
	private MenuService menuServiceImpl;
		
	/**
	 * 条件查询菜单列表
	 * @param Page
	 * @return
	 */
	@RequestMapping
	public ModelAndView menuList(Page page, String keywords, String menuLevel)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		try{
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			if(menuLevel != null && !"".equals(menuLevel)){
				pd.put("menuLevel", menuLevel);
			}
			page.setPd(pd);
			page.setPageSize(10);
			List<PageData> menuListToTable = menuServiceImpl.getMenuListByCondition(page);
			List<PageData> menuListToSelect = menuServiceImpl.getAllMenu(pd); //查询出所有菜单，增加、修改画面下拉显示父级菜单ID
			mv.addObject("menuListToTable", menuListToTable);
			mv.addObject("menuListToSelect", menuListToSelect);
			int totalRecord =Integer.parseInt(menuServiceImpl.getMenuTotalRecordByCondition(page).get("menuCount").toString());
			page.setTotalRecord(totalRecord);
			mv.addObject("page", page);
			mv.addObject("pd", pd);
			mv.setViewName("system/manager/menuList");
		} catch(Exception e){
			msg = "CMC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CMC: 系统异常!菜单管理--查询菜单列表系统执行异常!】");
			throw new Exception(msg);
		}
		mv.addObject("msg", msg);
		return mv;
	}
	
	/**
	 * 请求新增菜单页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/goAddMenu")
	@ResponseBody
	public Object goAddMenu() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		String msg = "";
		pd = this.getPageData();
		try {
			List<PageData> menuList = menuServiceImpl.getAllMenu(pd); //查询出所有菜单，画面下拉显示父级菜单ID
			map.put("menuList", menuList);
			info = "success";
		} catch (Exception e) {
			info = "error";
			msg = "CMC: 操作失败!重试或联系后端管理员!";
			logger.info("【CMC: 系统异常!菜单管理--新增菜单--回显所有菜单系统执行异常!】");
		}
		map.put("result", info);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 保存新增菜单信息
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/saveNewMenu",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object saveNewMenu() throws Exception {
		//logBefore(logger, Jurisdiction.getUserName()+"保存新增菜单");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		String msg = "";
		pd = this.getPageData();
		try {
			String menuName = pd.getString("menuName") != null && !"".equals(pd.getString("menuName")) ? pd.getString("menuName") : null;
			String menuParentID = pd.getString("menuParentID") != null && !"".equals(pd.getString("menuParentID")) ? pd.getString("menuParentID") : null;
			String menuURL = pd.getString("menuURL") != null && !"".equals(pd.getString("menuURL")) ? pd.getString("menuURL") : null;
			String menuState = pd.getString("menuState") != null && !"".equals(pd.getString("menuState")) ? pd.getString("menuState") : null;
			String menuLevel = pd.getString("menuLevel") != null && !"".equals(pd.getString("menuLevel")) ? pd.getString("menuLevel") : null;
			String menuOrder = pd.getString("menuOrder") != null && !"".equals(pd.getString("menuOrder")) ? pd.getString("menuOrder") : null;
			pd.put("menuName", menuName);
			pd.put("menuParentID", menuParentID);
			pd.put("menuURL", menuURL);
			pd.put("menuState", menuState);
			pd.put("menuLevel", menuLevel);			
			pd.put("menuOrder", menuOrder);
			menuServiceImpl.saveNewMenu(pd); //保存菜单
			info = "success";
		} catch (Exception e) {
			info = "error";
			msg = "CMC: 操作失败!重试或联系后端管理员!";
			logger.info("【CMC: 系统异常,新增菜单--系统执行异常!】");
		}
		map.put("result", info);
		map.put("pd", pd);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 请求修改菜单页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/goEditMenu",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object goEditMenu()throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		String msg = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		try{	
			if(null != pd && !"".equals(pd)){
				pd = menuServiceImpl.getMenuByMenuID(pd);	//读取此ID的菜单数据
				List<PageData> menuList = menuServiceImpl.getAllMenu(pd); //查询出所有菜单，画面下拉显示父级菜单ID
				map.put("pd", pd);
				map.put("menuList", menuList);
				info = "success";
			}else{
				info = "failed";
				msg = "CMC: 操作失败!重试或联系后端管理员!!";
				logger.info("【CMC: 系统异常!菜单修改---菜单回显系统执行异常！】");
			}
		} catch(Exception e){
			info = "error";
			msg = "CMC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CMC: 参数错误!菜单修改--菜单回显查询参数错误!】");
		}
		map.put("result", info);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 保存修改菜单信息
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/saveEditMenu",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object saveEditMenu() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String menuID = pd.getString("menuID") != null && !"".equals(pd.getString("menuID")) ? pd.getString("menuID") : null;
		try {
			if(null != menuID){
				String menuName = pd.getString("menuName") != null && !"".equals(pd.getString("menuName")) ? pd.getString("menuName") : null;
				String menuParentID = pd.getString("menuParentID") != null && !"".equals(pd.getString("menuParentID")) ? pd.getString("menuParentID") : null;
				String menuURL = pd.getString("menuURL") != null && !"".equals(pd.getString("menuURL")) ? pd.getString("menuURL") : null;
				String menuState = pd.getString("menuState") != null && !"".equals(pd.getString("menuState")) ? pd.getString("menuState") : null;
				String menuLevel = pd.getString("menuLevel") != null && !"".equals(pd.getString("menuLevel")) ? pd.getString("menuLevel") : null;
				String menuOrder = pd.getString("menuOrder") != null && !"".equals(pd.getString("menuOrder")) ? pd.getString("menuOrder") : null;
				pd.put("menuName", menuName);
				pd.put("menuParentID", menuParentID);
				pd.put("menuURL", menuURL);
				pd.put("menuState", menuState);
				pd.put("menuLevel", menuLevel);			
				pd.put("menuOrder", menuOrder);
				menuServiceImpl.saveEditMenu(pd); //保存菜单
				result = "success";
			}else{
				result = "failed";
				msg = "CMC: 操作失败!重试或联系络驿吴彦祖!";
				logger.info("【CMC: 参数错误!更新菜单--参数错误!】");
			}
		} catch (Exception e) {
			result = "error";
			msg = "CMC: 操作失败!重试或联系后端管理员!";
			logger.info("【CMC: 系统异常!更新菜单--执行保存系统执行异常!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 通过菜单ID删除菜单
	 * @param menuID
	 * @return map 
	 */
	@RequestMapping(value="/deleteOneMenu",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteOneMenu()throws Exception{
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		String msg = "";
		pd = this.getPageData();
		String menuIDS = pd.getString("menuID");
		try{
			if(Tools.notEmptys(menuIDS)){
				int menuID = Integer.valueOf(menuIDS);
				if(menuServiceImpl.getSubMenuListByMenuParentID(menuID).size() > 0){//判断是否有子菜单，是：不允许删除
					info = "failed";
				}else{
					menuServiceImpl.deleteMenuByMenuID(menuID);
					info = "success";
				}
			}else{
				info = "failed";
				msg = "CMC: 操作失败!重试或联系络驿吴彦祖!";
				logger.info("【CMC: 参数错误!删除菜单--参数错误！】");
			}	
		} catch(Exception e){
			info = "error";
			msg = "CMC: 操作失败!重试或联系后端管理员!";
			logger.info("【CMC: 系统异常!删除菜单--系统执行异常！】");
		}
		map.put("result", info);
		map.put("msg", msg);
		return AppUtil.returnObject(new PageData(), map);
	}
	
}
