package com.aurora.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.entity.Menu;
import com.aurora.entity.Role;
import com.aurora.entity.User;
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

/**
 * 描述:总入口 创建:BYG 2017/5/25 修改:
 * 
 * @version 1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Resource(name = "userServiceImpl")
	private UserService userServiceImpl;
	@Resource(name = "menuServiceImpl")
	private MenuService menuServiceImpl;
	@Resource(name = "roleServiceImpl")
	private RoleService roleServiceImpl;

	/**
	 * 修改密码页面
	 */
	@RequestMapping(value = "/myzone/passwordPage", produces = "application/json;charset=UTF-8")
	public ModelAndView passwordPage() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/myzone/password");
		return mv;
	}

	/**
	 * 修改密码
	 * 
	 * @param oldPassword,newPassword
	 */
	@RequestMapping(value = "/myzone/changePassword")
	@ResponseBody
	public Object changePassword(String oldPassword, String newPassword) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		if (Tools.notEmptys(oldPassword) && Tools.notEmptys(newPassword)) { // 判断登录验证码
			try {
				String userPassword = new SimpleHash("SHA-1", Jurisdiction.getUserEmail(), oldPassword).toString(); // 密码加密
				pd.put("userEmail", Jurisdiction.getUserEmail());
				pd.put("userPassword", userPassword);

				User user = userServiceImpl.getUserByEmailAndPwd(pd); // 根据用户邮箱和密码去读取用户信息
				if (user != null) {// 验证成功!

					newPassword = new SimpleHash("SHA-1", Jurisdiction.getUserEmail(), newPassword).toString(); // 密码加密
					pd.put("newPassword", newPassword);
					userServiceImpl.updateMyUser(pd);

					Session session = Jurisdiction.getSession();
					session.removeAttribute(Const.SESSION_USER);
					session.removeAttribute("roleMenuList");
					session.removeAttribute("userEmail");
					Subject subject = SecurityUtils.getSubject();
					subject.logout();
					result = "success";
				} else {// 验证失败!
					result = "failed";
					msg = "旧密码错误！密码修改失败！";
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CLC: 操作失败!重试或联系后端管理员!";
				logger.info("【CLC: 系统异常!个人密码修改--系统执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CLC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CLC: 参数错误!新旧密码为空!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 修改个人资料页面 回显个人信息
	 */
	@RequestMapping(value = "/myzone/goPersonalInfo", produces = "application/json;charset=UTF-8")
	public ModelAndView getPersonalInfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		pd.put("userEmail", Jurisdiction.getUserEmail());
		User user = null;
		try {
			user = userServiceImpl.findByUserEmail(pd);

			pd.put("roleID", user.getUserRoleID());

			pd = roleServiceImpl.getRoleByRoleID(pd);

		} catch (Exception e) {
			e.printStackTrace();
			msg = "CLC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CLC: 系统异常!修改个人资料---数据回显执行异常!】");
			throw new Exception(msg);
		}
		mv.setViewName("system/myzone/userInfo");
		mv.addObject("msg", msg);
		mv.addObject("pd", pd);
		mv.addObject("user", user);
		return mv;

	}

	/**
	 * 修改个人资料 验证邮箱是否重复
	 * 
	 * @param userRealName
	 *            ,userName,userMobile,userEmail.
	 */
	@RequestMapping(value = "/myzone/updatePersonInfo")
	@ResponseBody
	public Object updatePersonInfo() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";

		String userRealName = Tools.notEmptys(pd.getString("userRealName")) ? pd.getString("userRealName").trim()
				: null;
		String userName = Tools.notEmptys(pd.getString("userName")) ? pd.getString("userName").trim() : null;
		String userMobile = Tools.notEmptys(pd.getString("userMobile")) ? pd.getString("userMobile").trim() : null;
		if (userRealName == null || userName == null || userMobile == null) {
			result = "failed";
			msg = "CLC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CLC: 参数错误!更新个人资料参数错误!!】");
		} else {
			try {
				pd.put("userRealName", userRealName);
				pd.put("userName", userName);
				pd.put("userMobile", userMobile);
				pd.put("userEmail", Jurisdiction.getUserEmail());
				userServiceImpl.updateMyUser(pd);
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CLC: 操作失败!重试或联系后端管理员!";
				logger.info("【CLC: 系统异常!修改个人资料--更新个人资料执行异常!】");
			}
		}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 访问登录页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toLogin")
	public ModelAndView toLogin() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/index/login");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 请求登录，验证用户
	 * 
	 * @return result=paramerror,codeerror,usererror,passworderror,userlock,error
	 * @throws Exception
	 */
	@RequestMapping(value = "/requestLogin", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object login() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		String KEYDATA[] = pd.getString("KEYDATA").split(":");
		if (null == KEYDATA || KEYDATA.length != 3) {
			result = "paramerror"; // 参数不正确
			msg = "请填写登陆信息";
			map.put("result", result);
			map.put("msg", msg);
			return AppUtil.returnObject(pd, map);
		}
		Session session = Jurisdiction.getSession();
		String sessionCode = (String) session.getAttribute(Const.SESSION_VERIFICATION_CODE); // 获取session中的验证码
		String code = KEYDATA[2];
		if (Tools.isEmpty(code) || Tools.isEmpty(sessionCode) || !sessionCode.equalsIgnoreCase(code)) { // 判断效验码
			result = "codeerror"; // 效验码不正确
			
			logger.info("sessionCode:"+ sessionCode + ";code:"+ code +";KEYDATA:" + pd.getString("KEYDATA"));
			session.removeAttribute(Const.SESSION_VERIFICATION_CODE);//验证码错误，就删除session中验证码，前端请求新的验证码
			msg = "验证码有误";
			map.put("result", result);
			map.put("msg", msg);
			return AppUtil.returnObject(pd, map);
		}
		String userEmail = KEYDATA[0]; // 登录过来的用户名
		String password = KEYDATA[1]; // 登录过来的密码
		if (Tools.isEmpty(password) || Tools.isEmpty(userEmail)) {
			result = "paramerror"; // 账号密码为空！
			msg = "";
			// logger.info("【failed：用户登陆参数不足！】");
			map.put("result", result);
			map.put("msg", msg);
			return AppUtil.returnObject(pd, map);
		}
		String userPassword = new SimpleHash("SHA-1", userEmail, password).toString(); // 密码加密
		Subject subject = SecurityUtils.getSubject(); // shiro身份验证
		UsernamePasswordToken token = new UsernamePasswordToken(userEmail, userPassword);
		try {
			subject.login(token);
			result = "success";
			session.removeAttribute(Const.SESSION_VERIFICATION_CODE);//登陆成功，就删除session中验证码，前端请求新的验证码
		} catch (UnknownAccountException uae) {
			result = "usererror";
			msg = "U20040";
			logger.info("【failed：用户登陆账号不存在！】");
		} catch (IncorrectCredentialsException iae) {
			result = "passworderror";
			msg = "U20041";
			logger.info("【failed：用户登陆密码不正确！】");
		} catch (LockedAccountException lae) {
			result = "userlock";
			msg = "U20042";
			logger.info("【failed：该用户账号被禁用！】");
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			result = "error";
			msg = "S40057";
			logger.info("【error：用户登陆信息验证执行异常！】");
		}
		map.put("msg", msg);
		map.put("result", result);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 访问系统首页
	 * 
	 * @param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/main")
	public ModelAndView pageHome() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		try {
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER); // 读取session中的用户信息(单独用户信息)
			if (user != null) {
				int userID = user.getUserID();
				pd.put("userID", userID);
				String userEmail = user.getUserEmail();
				User userRole = (User) session.getAttribute(Const.SESSION_USER_ROLE); // 读取session中的用户信息(含角色信息)

				if (null == userRole) {
					user = userServiceImpl.getUserAndRoleById(user.getUserID()); // 通过用户ID读取用户信息和角色信息
					session.setAttribute(Const.SESSION_USER_ROLE, user); // 存入session
				} else {
					user = userRole;
				}
				Role role = user.getUserRole(); // 获取用户角色
				String roleRights = role != null ? role.getRoleRights() : ""; // 角色权限(菜单权限)
				List<Menu> roleMenuList = new ArrayList<Menu>();
				roleMenuList = this.getRoleMenu(session, userEmail, roleRights); // 获取角色所属菜单

				this.updateUserIP(userEmail); // 更新登录IP以及登陆时间
				pd.put("userStatus", 1);
				userServiceImpl.updateUserStatus(pd);
				mv.setViewName("system/index/default");
				mv.addObject("user", user);
				mv.addObject("roleMenuList", roleMenuList);
			} else {
				mv.setViewName("system/index/login"); // session失效后跳转登录页面
			}
		} catch (Exception e) {
			mv.setViewName("system/index/login");
			msg = "S40058：角色菜单权限获取执行异常!";
			logger.info("【error：角色菜单权限获取执行异常!】");
			throw new Exception(msg);
		}
		String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getServletPath();
		path = path.substring(1, path.length());
		System.out.println(path);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 角色所属全部菜单缓存
	 * 
	 * @param session
	 * @param userName
	 * @param roleRights
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getRoleMenu(Session session, String userEmail, String roleRights) throws Exception {
		List<Menu> roleMenuList = new ArrayList<Menu>();
		List<Menu> allmenuList = new ArrayList<Menu>();
		if (null == session.getAttribute("roleMenuList")) {
			allmenuList = menuServiceImpl.getAllMenuList(0); // 获取系统所有菜单
			if (Tools.notEmpty(roleRights)) {
				roleMenuList = this.readMenu(allmenuList, roleRights); // 根据角色权限获取本角色的菜单列表
			}
			session.setAttribute("roleMenuList", roleMenuList);// 菜单权限放入session中
		} else {
			roleMenuList = (List<Menu>) session.getAttribute("roleMenuList");
		}
		return roleMenuList;
	}

	/**
	 * 根据角色权限获取本权限的菜单列表(递归处理)
	 * 
	 * @param allMenuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> allmenuList, String roleRights) {
		for (int i = 0; i < allmenuList.size(); i++) {
			allmenuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, allmenuList.get(i).getMenuID()));
			if (allmenuList.get(i).isHasMenu()) { // 判断是否有此菜单权限
				this.readMenu(allmenuList.get(i).getSubMenu(), roleRights);// 是：继续排查其子菜单
			}
		}
		return allmenuList;
	}

	/**
	 * 进入tab标签
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tab")
	public String tab() {
		return "system/index/tab";
	}

	/**
	 * 进入首页后的默认页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/defaultPage")
	public ModelAndView defaultPage() throws Exception {
		System.out.println("/defaultPage");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("userCount", Integer.parseInt(userServiceImpl.getUserCount("").get("userCount").toString()) - 1); // 系统用户数
		mv.addObject("pd", pd);
		mv.setViewName("system/index/default");
		return mv;
	}

	/**
	 * 用户注销 如果用户被禁用,注销不能修改状态,即在线不能禁用用户;
	 * 
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public Object logout() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String reslut = "";
		Session session = Jurisdiction.getSession();
		String userID = session.getAttribute(Const.SESSION_USER_ID).toString();
		logger.info(userID + ":退出系统");
		session.removeAttribute(Const.SESSION_USER); // 以下清除session缓存
		session.removeAttribute(Const.SESSION_USER_ROLE);
		session.removeAttribute("roleMenuList");
		session.removeAttribute("userEmail");
		session.removeAttribute("Const.SESSION_USER_EMAIL");
		Subject subject = SecurityUtils.getSubject();
		subject.logout(); // shiro销毁登录,此处其实已经让session==null,上面多此一举,不过我不想删
		reslut = "success";
		map.put("result", reslut);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 更新登录用户的IP
	 * 
	 * @param userName
	 * @throws Exception
	 */
	public void updateUserIP(String userEmail) throws Exception {
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		pd.put("userEmail", userEmail);
		pd.put("userIP", ip);
		pd.put("time", DateUtil.getTime());
		userServiceImpl.saveIP(pd);
	}

}
