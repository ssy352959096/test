package com.aurora.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.entity.Menu;
import com.aurora.entity.Page;
import com.aurora.entity.Role;
import com.aurora.service.MenuService;
import com.aurora.service.RoleService;
import com.aurora.service.UserService;
import com.aurora.util.AppUtil;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.RightsHelper;
import com.aurora.util.Tools;

@Controller
@RequestMapping(value="/systemRole")
public class RoleController extends BaseController{
	String menuUrl = "systemRole.do"; //菜单地址(权限用)

	@Resource(name="roleServiceImpl")
	private RoleService roleServiceImpl;
	@Resource(name="menuServiceImpl")
	private MenuService menuServiceImpl;
	@Resource(name="userServiceImpl")
	private UserService userServiceImpl;

	/** 条件查询角色列表
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView roleList(Page page, String keywords, String roleParentID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(null != roleParentID && !"".equals(roleParentID)){
			pd.put("roleParentID", roleParentID.trim());
		}
		page.setPd(pd);
		page.setPageSize(10);
		try {
			List<PageData>	roleList = roleServiceImpl.getRoleListByCondition(page);   //列出角色列表
			List<Role>	allRoleListToSelect = roleServiceImpl.getAllRole();	           //列出所有角色列表,首页、增加、修改下拉列表用。
			int totalRecord =Integer.parseInt(roleServiceImpl.getRoleTotalRecordByCondition(page).get("roleCount").toString());
			page.setTotalRecord(totalRecord);
			mv.addObject("roleList", roleList);
			mv.addObject("allRoleListToSelect", allRoleListToSelect);
		} catch (Exception e) {
			msg = "S40073";
			logger.info("【error:条件查询角色列表执行异常！】");
			throw new Exception(msg);
		}
		mv.addObject("page", page);
		mv.addObject("msg", msg);
		mv.addObject("pd", pd);
		mv.setViewName("system/manager/roleList");
		return mv;
	}
	
	/**去新增角色页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddRole")
	@ResponseBody
	public Object goAddRole() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		String msg = "";
		pd = this.getPageData();
		List<Menu> allmenuList = null;
		try {
			allmenuList = menuServiceImpl.getAllMenuList(0);//获取全部菜单(多元数组)
//			allmenuList = menuServiceImpl.getAllMenu(pd);
			map.put("allmenuList", allmenuList);
			info = "success";	
		} catch (Exception e) {
			info = "error";
			msg = "S40074";
			logger.info("【error:新增角色页面查询出所有菜单执行异常！】");
		}
		map.put("result", info);
		map.put("msg", msg);
		map.put("allmenuList", allmenuList);
		return AppUtil.returnObject(pd, map);
	}

	/**保存新增角色及权限
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveNewRole",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object saveNewRole() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		String msg = "";
		pd = this.getPageData();
		try {
			String menuIDs = pd.getString("roleRights") != null && !"".equals(pd.getString("roleRights")) ? pd.getString("roleRights") : null;
			if(null != menuIDs && !"".equals(menuIDs.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIDs));//用菜单ID做权处理
				pd.put("roleRights",rights.toString());
			}else{
				pd.put("roleRights",menuIDs);
			}
			String roleName = pd.getString("roleName") != null && !"".equals(pd.getString("roleName")) ? pd.getString("roleName") : null;
			String roleParentID = pd.getString("roleParentID") != null && !"".equals(pd.getString("roleParentID")) ? pd.getString("roleParentID") : null;
			String roleRemark = pd.getString("roleRemark") != null && !"".equals(pd.getString("roleRemark")) ? pd.getString("roleRemark") : null;
			pd.put("roleName", roleName);
			pd.put("roleParentID", roleParentID);
			pd.put("roleRemark", roleRemark);
			pd.put("time", DateUtil.getTime());
			pd.put("createUserID", Jurisdiction.getSession().getAttribute("userID"));
			roleServiceImpl.saveNewRole(pd);
			info = "success";
		} catch (Exception e) {
			info = "error";
			msg = "S40075";
			logger.info("【error:保存新增角色执行异常！】");
		}
		map.put("result", info);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**去修改角色页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditRole",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object goEditRole() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String roleID = pd.getString("roleID");
			if(null != roleID && !"".equals(roleID)){
				pd= roleServiceImpl.getRoleByRoleID(pd);            	 //获取本角色信息（包含权限信息）
				List<Menu> allmenuList = menuServiceImpl.getAllMenuList(0);//获取全部菜单(三维数组)
				List<PageData> menuList = menuServiceImpl.getAllMenu(pd); //获取全部菜单(一维数组)
				//String roleRights = pd.getString("roleRights");
				String roleRights = pd.getString("role_rights");
				List<Integer> roleMenuIDList = new ArrayList<Integer>();		//本角色权限内菜单ID
				if(Tools.notEmpty(roleRights)){
					List<Menu> roleMenuList = new ArrayList<Menu>();
					roleMenuList = this.readMenu(allmenuList, roleRights);				//根据角色权限获取本角色的菜单列表
					if (roleMenuList.size() > 0 ) {
						for(Menu r1:roleMenuList){
							if (r1.isHasMenu()) {
								roleMenuIDList.add(r1.getMenuID());
							}
							List<Menu> subMenuList2 = r1.getSubMenu();
							if (subMenuList2.size() > 0) {
								for(Menu sub2:subMenuList2){
									if (sub2.isHasMenu()) {
										roleMenuIDList.add(sub2.getMenuID());
									}									
									List<Menu> subMenuList3 = sub2.getSubMenu();
									if (subMenuList3.size() > 0) {
										for(Menu sub3:subMenuList3){
											if (sub3.isHasMenu()) {
												roleMenuIDList.add(sub3.getMenuID());
											}
										}
									}
								}
							}
					    }	
					map.put("roleMenuIDList", roleMenuIDList);
					}
					map.put("allmenuList", allmenuList);
					map.put("menuList", menuList);
					map.put("pd", pd);
					result = "success";	
				}else{
					result = "failed";
				}
			}else{
				result = "failed";
				msg = "角色id为空";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			msg = "S40076";
			logger.info("【error:去修改角色页面执行异常！】");
		}
		map.put("result", result);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
	/**根据角色权限获取本权限的菜单列表(递归处理)
	 * @param allMenuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> allmenuList,String roleRights){
		for(int i=0;i<allmenuList.size();i++){
			allmenuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, allmenuList.get(i).getMenuID()));
			if(allmenuList.get(i).isHasMenu()){		//判断是否有此菜单权限
				this.readMenu(allmenuList.get(i).getSubMenu(), roleRights);//是：继续排查其子菜单
			}
		}
		return allmenuList;
	}
	
	/**保存修改角色及权限
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveEditRole",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object saveEditRole() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		String msg = "";
		pd = this.getPageData();
		try {
			String roleID = pd.getString("roleID") != null && !"".equals(pd.getString("roleID")) ? pd.getString("roleID") : null;
			if(null != roleID && !"".equals(roleID)){
				String menuIDs = pd.getString("roleRights") != null && !"".equals(pd.getString("roleRights")) ? pd.getString("roleRights") : null;//menuIDs是菜单ID用,相隔拼成的字符串。
				if(null != menuIDs && !"".equals(menuIDs.trim())){
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIDs));//用菜单ID做权处理
					pd.put("roleRights",rights.toString());
				}else{
				pd.put("roleRights",menuIDs);
				}
				String roleName = pd.getString("roleName") != null && !"".equals(pd.getString("roleName")) ? pd.getString("roleName") : null;
				String roleParentID = pd.getString("roleParentID") != null && !"".equals(pd.getString("roleParentID")) ? pd.getString("roleParentID") : null;
				String roleRemark = pd.getString("roleRemark") != null && !"".equals(pd.getString("roleRemark")) ? pd.getString("roleRemark") : null;
				pd.put("roleName", roleName);
				pd.put("roleParentID", roleParentID);
				pd.put("roleRemark", roleRemark);
				roleServiceImpl.saveEditRole(pd);
				info = "success";
			}else{
				
				info = "failed";
			}
		} catch (Exception e) {
			info = "error";
			msg = "S40077";
			logger.info("【error:保存修改角色及权限执行异常！】");
		}
		map.put("result", info);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**删除角色
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteOneRole",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteOneRole() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		String msg = "";
		pd = this.getPageData();
		String roleID = pd.getString("roleID");
		try {
			if(Tools.notEmpty(roleID)){
				pd.put("roleID", Integer.valueOf(roleID));
				List<Role> subRoleList = roleServiceImpl.getRoleListByRoleParentID(pd);	//根据角色父级ID查询子角色
				if(subRoleList.size() > 0){
					info = "failed";													//角色ID有下级角色，删除失败
				}else{
					List<PageData> userlist = userServiceImpl.getUserListByRoldID(pd);	//根据角色ID查询角色所属用户
					if(userlist.size() > 0){	
						msg = "角色ID有下级角色不可删除！";
						info = "failed";												//角色有所属用户不可删除
					}else{
						roleServiceImpl.deleteRoleByRoleID(Integer.valueOf(roleID));						//执行删除
						msg = "角色有所属用户不可删除！";
						info = "success";
					}
				}
			}else{
				info = "failed";
				msg = "删除角色ID为空";
			}	
		} catch (Exception e) {
			e.printStackTrace();
			info = "error";
			msg = "删除角色执行异常";
			logger.info("【error:删除角色执行异常！】");
		}
		map.put("result", info);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
}
