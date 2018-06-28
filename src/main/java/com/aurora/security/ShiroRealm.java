package com.aurora.security;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aurora.entity.User;
import com.aurora.service.UserService;
import com.aurora.util.Const;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;

/**
 * 描述:登录信息和用户验证信息验证 创建:BYG 2017/5/25 修改:
 * 
 * @version 1.0
 */
public class ShiroRealm extends AuthorizingRealm {

	@Resource(name = "userServiceImpl")
	private UserService userServiceImpl;

	/*
	 * 登录信息和用户验证信息验证(non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.
	 * apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userEmail = (String) token.getPrincipal(); // 得到用户名
		String userPassword = new String((char[]) token.getCredentials()); // 得到密码

		PageData pd = new PageData();
		pd = new PageData(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		pd.put("userEmail", userEmail);
		pd.put("userPassword", userPassword);

		User user = null;
		try {
			user = userServiceImpl.findByUserEmail(pd);
		}catch (TooManyResultsException manyResult) { 
			throw new AuthenticationException("【error:请求登陆账号不唯一！】");// 认证异常
		} 
		catch (Exception e) {
			throw new AuthenticationException(e);// 认证异常
		} // 根据用户邮箱和密码去读取用户信息
		if (user == null) {
			throw new UnknownAccountException();
		}
		if (!user.getUserPassword().equals(userPassword)) {
			throw new IncorrectCredentialsException();
		}
		if (user.getUserStatus()==null||user.getUserStatus().equals("3")) {//账号被禁用；
			throw new LockedAccountException();
		}
		Session session = Jurisdiction.getSession();
		session.setAttribute(Const.SESSION_USER, user); // 把用户信息放session中
		session.setAttribute(Const.SESSION_USER_EMAIL, user.getUserEmail());
		session.setAttribute("userEmail", user.getUserEmail());
		session.setAttribute(Const.SESSION_USER_ID, user.getUserID());
		return new SimpleAuthenticationInfo(userEmail, userPassword, getName());

	}

	/*
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache
	 * .shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		SimpleAuthorizationInfo authorize =  new SimpleAuthorizationInfo();
		String userID = Jurisdiction.getUserID();
		try {
			User user = userServiceImpl.getUserAndRoleById(Integer.valueOf(userID));
			String roleID = user.getUserRole().getRoleID()+"";
			Set<String> roleSet = new HashSet<String>();
			roleSet.add(roleID);
			authorize.setRoles(roleSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authorize;
	}

}
