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

import com.aurora.redis.RedisUtil;
import com.aurora.service.HomeManageService;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 热搜--热搜词管理
 * 
 * @author SSY 2017/8/9
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/homeHotKeyWord")
public class HomeHotKeyWordController extends BaseController {

	String menuUrl = "homeHotKeyWord.do"; // 菜单地址(权限用)

	@Resource(name = "homeManageServiceImpl")
	private HomeManageService homeManageServiceImpl;
	@Resource(name="redisUtil")
	private RedisUtil redisUtil;
	
	/**
	 * 跳到热搜词管理页面
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView goHotKeyWord() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		try {
			pd = homeManageServiceImpl.getHotKeyWord(pd);// 按页码热门品牌的信息;
		} catch (Exception e) {
			msg = "CHHKWC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CHHKWC: 系统异常,热搜词管理页面--热搜词回显执行异常!】");
			throw new Exception(msg);
		}
		List<Integer> nullList = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			nullList.add(i);
		}
		mv.addObject("nullList", nullList);
		mv.addObject("pd", pd);
		mv.addObject("msg", msg);
		mv.setViewName("system/homeManager/hotSearchWord");
		return mv;
	}

	/**
	 * 更新热搜词
	 */
	@RequestMapping(value = "/updateHotKeyWord", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object updateHotKeyWord(String hotKeyWord) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		if (Tools.notEmptys(hotKeyWord)) {

			pd.put("hotKeyWord", hotKeyWord);
			Session session = Jurisdiction.getSession();
			String userEmail = (String) session.getAttribute(Const.SESSION_USER_EMAIL);
			pd.put("operator", userEmail);// 操作者
			pd.put("operateTime", DateUtil.getDay());
			try {
				homeManageServiceImpl.updateHotKeyWord(pd);// 更新热搜词;
				if (redisUtil.hasKey("shHomeHotWordList")) {
					redisUtil.remove("shHomeHotWordList");//删除redis中相应缓存
				}
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CHHKWC: 系统可能走神了,刷新重试或联系后端管理员!";
				logger.info("【CHHKWC: 系统异常,热搜词管理更新执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CHHKWC: 操作失败!重试或联系络驿吴彦祖!!";
			logger.info("【CHHKWC: 参数错误!热搜词管理更新参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return map;
	}

}
