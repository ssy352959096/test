package com.aurora.listener;  
  
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aurora.serviceImpl.UserServiceImpl;
import com.aurora.util.Const;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;  
  
public class MySessionListener implements HttpSessionListener {  
	
	
	/* Session创建事件 */  
	public void sessionCreated(HttpSessionEvent se) {}; 
	
	/* Session失效事件  ———— 用户直接关闭浏览器后状态不改变，通过session失效事件改变用户状态*/  
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		Object attribute = session.getAttribute(Const.SESSION_USER_ROLE);
		if (attribute==null) {//如果是用户主动退出,此时Const.SESSION_USER_ROLE为空;
			return;
		}
		String userID = attribute.toString();
		PageData pd = new PageData();
		pd.put("userID", userID);
		pd.put("userStatus", 2);
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
	    UserServiceImpl userServiceImpl = (UserServiceImpl)ac.getBean("userServiceImpl");
		try {
			userServiceImpl.updateUserStatus(pd);	// 更新用户状态2:离线
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} 
	}  
}  