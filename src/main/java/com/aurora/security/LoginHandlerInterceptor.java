package com.aurora.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.PaddingScheme;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aurora.entity.User;
import com.aurora.util.Const;
import com.aurora.util.Jurisdiction;

/**
 * 描述:登录过滤，权限验证
 * 创建:BYG 2017/5/25
 * 修改:
 * @version 1.0
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH1)){
			return true;
		}else{
			User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if(user!=null){
				if(path.matches(Const.NO_INTERCEPTOR_PATH2)){
					return true;
				}else{
					path = path.substring(1, path.length());
					if (path.contains("/")) {
						int a = path.indexOf("/");
						path = path.substring(0, a);
					}
					boolean b = Jurisdiction.hasJurisdiction(path);													 //访问权限校验
					if(!b){
						response.sendRedirect(request.getContextPath() + "/index.jsp");
					}
					return b;
				}
			}else{
				response.sendRedirect(request.getContextPath() + "/index.jsp");									//登陆过滤
				return false;		
			}
		}
	}
	
}
