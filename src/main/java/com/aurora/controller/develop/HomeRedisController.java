package com.aurora.controller.develop;

import java.io.Serializable;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aurora.controller.BaseController;
import com.aurora.util.DateUtil;

/**
 * @Title: HomeRedisController.java 
 * @Package com.aurora.controller.develop 
 * @Description: 首页缓存管理
 * @author SSY  
 * @date 2018年5月5日 下午3:24:28 
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/homeRedis")
public class HomeRedisController extends BaseController {

	@RequestMapping
	public String getHomeRedisList(ModelMap map) throws Exception {
		try {
			Set<Serializable> allKey = redisUtil.getAllKey("home");
			
			System.out.println(allKey);
		} catch (Exception e) {
			e.printStackTrace(); 
			logger.info("【ERROR】"+DateUtil.getTime()+"首页缓存查询执行异常! ");
		}
		return "system/develop/homeRedis";
	}



}
