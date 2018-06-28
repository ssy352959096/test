package com.aurora.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.entity.Page;
import com.aurora.entity.Role;
import com.aurora.service.RoleService;
import com.aurora.service.UserService;
import com.aurora.util.AppUtil;
import com.aurora.util.PageData;

@Controller
@RequestMapping(value="/systemUser")
public class UserController extends BaseController{
	
	String menuUrl = "systemUser.do"; //菜单地址(权限用)
	@Resource(name="userServiceImpl")
	private UserService userServiceImpl;
	@Resource(name="roleServiceImpl")
	private RoleService roleServiceImpl;
		
	/**条件查询显示用户列表
	 * @param page
	 * @return  
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView userList(Page page, String keywords, String userStatus, String roleName)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(userStatus!= null && !"".equals(userStatus)){       //0全部；1在线；2离线；4禁用
			pd.put("userStatus", userStatus);
		}
		if(roleName != null && !"".equals(roleName)){
			pd.put("roleName", roleName);
		} 
		page.setPd(pd);
		page.setPageSize(10);
		try {
			List<PageData>	userListToTable = userServiceImpl.getUserListByCondition(page);	//列出用户列表
			List<Role> allRoleListToSelect = roleServiceImpl.getAllRole();//列出所有系统用户角色
			int totalRecord =Integer.parseInt(userServiceImpl.getUserTotalRecordByCondition(page).get("userCount").toString());
			page.setTotalRecord(totalRecord);
			mv.addObject("userListToTable", userListToTable);
			mv.addObject("allRoleListToSelect", allRoleListToSelect);
		} catch (Exception e) {
			msg = "查询显示用户列表执行异常!异常码:S40079";
			logger.info("【error:条件查询显示用户列表！】");
			throw new Exception(msg);
		}
		mv.addObject("page", page);
		mv.addObject("msg", msg);
		mv.addObject("pd", pd);
		mv.setViewName("system/manager/userList");
		return mv;
	}
	
	/**去新增用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddUser")
	@ResponseBody
	public Object goAddUser() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		
		info = "success";	
		map.put("result", info);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}

	/**新增保存用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveNewUser",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object saveNewUser() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		String msg = "";
		pd = this.getPageData();
		try {
			String userRealName = pd.getString("userRealName") != null && !"".equals(pd.getString("userRealName")) ? pd.getString("userRealName") : null;
			String userMobile = pd.getString("userMobile") != null && !"".equals(pd.getString("userMobile")) ? pd.getString("userMobile") : null;
			String userEmail = pd.getString("userEmail") != null && !"".equals(pd.getString("userEmail")) ? pd.getString("userEmail") : null;
			String userRoleID = pd.getString("userRoleID") != null && !"".equals(pd.getString("userRoleID")) ? pd.getString("userRoleID") : null;
			String userName = pd.getString("userName") != null && !"".equals(pd.getString("userName")) ? pd.getString("userName") : null;
			String userPassword = pd.getString("userPassword") != null && !"".equals(pd.getString("userPassword")) ? pd.getString("userPassword") : null;
			pd.put("userEmail",userEmail);
			if(null == userServiceImpl.findByUserEmail(pd)){	//判断用户名是否存在
				if(null != userPassword && null != userEmail){
					pd.put("userPassword",new SimpleHash("SHA-1", pd.getString("userEmail"), pd.getString("userPassword")).toString());
				}else{
					pd.put("userPassword",new SimpleHash("SHA-1", pd.getString("userEmail"), "123456")).toString();
				}
				pd.put("userName",userName);
				pd.put("userRealName",userRealName);
				pd.put("userMobile",userMobile);
				pd.put("userRoleID",userRoleID);
				pd.put("userStatus","0");
				userServiceImpl.saveNewUser(pd); 					//执行保存
				info = "success";
			}else{
				info = "failed";
				msg = "U20059";
				logger.info("【failed:该邮箱已经存在,新增失败！】");
			}
		} catch (Exception e) {
			info = "error";
			msg = "S40080";
			logger.info("【error:新增保存用户执行异常！】");
		}
		map.put("result", info);
		map.put("pd", pd);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
	/**删除用户
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteOneUser",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteOneUser() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		String msg = "";
		pd = this.getPageData();
		String userID = pd.getString("userID");
		try {
			if(null != userID && !"".equals(userID)){
				pd.put("userID", Integer.valueOf(userID));
				userServiceImpl.deleteOneUser(pd);
				info = "success";	
			}else{
				info = "failed";
				msg = "U20060";
				logger.info("【failed:删除用户参数不正确！】");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			info = "error";
			msg = "S40081";
			logger.info("【error:删除用户执行异常！】");
		}
		map.put("result", info);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 批量删除
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteSomeUser",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteSomeUser() throws Exception {
		//logBefore(logger, Jurisdiction.getUserName()+"批量删除user");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		String info = "";
		String msg = "";
		String userIDs = pd.getString("userIDs");
		try {
			if(null != userIDs && !"".equals(userIDs)){
				String ArrayUserID[] = userIDs.split(",");
				userServiceImpl.deleteSomeUser(ArrayUserID);
				info = "success";
			}else{
				info = "failed";
				msg = "U20060";
				logger.info("【failed:删除用户参数不正确！】");
			}
		} catch (Exception e) {
			info = "error";
			msg = "S40082";
			logger.info("【error:批量删除用户执行异常！】");
		}
		map.put("result", info);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**去修改用户页面(系统用户列表修改)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditSystemUser",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object goEditSystemUser() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String info = "";
		String msg = "";
		pd = this.getPageData();
		try {
			if(null != pd && !"".equals(pd)){
				pd = userServiceImpl.getUserByUserID(pd);//pd为带入修改页面用户信息
				map.put("pd", pd);
				info = "success";	
			}else{
				info = "failed";
				msg = "U20061";
				logger.info("【failed:修改后台系统用户回显参数不正确！】");
			}
		} catch (Exception e) {
			info = "error";
			msg = "S40083";
			logger.info("【error:修改用户页面通过用户ID获取执行异常！】");
		}
		map.put("result", info);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 管理员修改系统用户保存
	 */
	@RequestMapping(value="/saveEditSystemUser",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object saveEditSystemUser() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
 		String info = "";
		String msg = "";
		pd = this.getPageData();
		String userID = pd.getString("userID") != null && !"".equals(pd.getString("userID")) ? pd.getString("userID") : null;
		String userRealName = pd.getString("userRealName") != null && !"".equals(pd.getString("userRealName")) ? pd.getString("userRealName") : null;
		try {
			if(null != userID && null != userRealName){
				String userName = pd.getString("userName") != null && !"".equals(pd.getString("userName")) ? pd.getString("userName") : null;
				String userMobile = pd.getString("userMobile") != null && !"".equals(pd.getString("userMobile")) ? pd.getString("userMobile") : null;
				String userEmail = pd.getString("userEmail") != null && !"".equals(pd.getString("userEmail")) ? pd.getString("userEmail") : null;
				String userRoleID = pd.getString("userRoleID") != null && !"".equals(pd.getString("userRoleID")) ? pd.getString("userRoleID") : null;
				String userStatus = pd.getString("userStatus") != null && !"".equals(pd.getString("userStatus")) ? pd.getString("userStatus") : "4";//默认禁用
				String userPassword = pd.getString("userPassword") != null && !"".equals(pd.getString("userPassword")) ? pd.getString("userPassword") : null;
				if(null != userPassword){
					pd.put("userPassword",new SimpleHash("SHA-1", pd.getString("userEmail"), pd.getString("userPassword")).toString());
				}else{//如果密码为空,则默认是123456;
					pd.put("userPassword",new SimpleHash("SHA-1", pd.getString("userEmail"), "123456").toString());
				} //
				pd.put("userName",userName);
				pd.put("userRealName",userRealName);
				pd.put("userMobile",userMobile);
				pd.put("userEmail",userEmail);
				pd.put("userRoleID",userRoleID);
				pd.put("userStatus",userStatus);
				userServiceImpl.saveEditSystemUser(pd); 					//执行保存
				info = "success";
				//如果修改在自己的密码要重新登陆?
				
			}else{
				info = "failed";
				msg = "U20062";
				logger.info("【failed:修改后台系统用户执行保存异常！】");
			}
		} catch (Exception e) {
			e.printStackTrace();
			info = "error";
			msg = "S40084";
			logger.info("【error:保存修改后的用户信息执行异常！】");
		}
		map.put("result", info);
		map.put("pd", pd);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	

}
