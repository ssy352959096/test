package com.aurora.controller.shop.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aurora.controller.BaseController;
import com.aurora.entity.Result;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.RedisConst;

/**
 * @Title: HomeKeywordController.java 
 * @Package com.aurora.controller.shop.home 
 * @Description: 首页热搜词管理(搜索框)
 * @author SSY  
 * @date 2018年5月2日 下午2:50:20 
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/homeKeyword")
public class HomeKeywordController extends BaseController {

	/**
	 * @Title: getHomeSearchKeyword 
	 * @Description: 查询搜索框下热搜词
	 * @param    
	 * @return homeSearchKeyword.homeSearchKeyword.word  
	 * @author SSY
	 * @date 2018年5月2日 下午2:50:55
	 */
	@RequestMapping
	public String getHomeSearchKeyword(ModelMap map) throws Exception {
		try {
			PageData homeSearchKeyword = homeKeywordServiceImpl.getHomeKeyword(Const.KEYWORD_SEARCH);// 按页码热门品牌的信息;
			map.put("homeSearchKeyword", JSON.toJSON(homeSearchKeyword));
		} catch (Exception e) {
			e.printStackTrace(); 
			logger.info("【ERROR】"+DateUtil.getTime()+"首页搜索框搜索关键字查询执行异常! ");
		}
		return "system/homeManager/hotSearchWord";
	}

	/**
	 * @Title: updateHomeKeyword 
	 * @Description: 更新首页关键词
	 * @param    
	 * @return Object  
	 * @author SSY
	 * @date 2018年5月2日 下午3:33:49
	 */
	@RequestMapping(value = "/updateHomeKeyword", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object updateHomeKeyword(String homeKeyword) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			result = homeKeywordServiceImpl.updateHomeKeyword(Const.KEYWORD_SEARCH,homeKeyword);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"更新/新增首页关键词执行异常! ");
		}
		return result;
	}

	
	/**
	 * @Title: clearHomeSearchCache
	 * @Description: 清除首页搜索框热搜词缓存
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月8日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteRedisKey")
	@ResponseBody
	public  Result<Object> clearHomeSearchCache(){
		Result<Object> result = new Result<Object>();
		try {
			if (redisUtil.hasKey(RedisConst.HOME_SEARCH)) {
				redisUtil.remove(RedisConst.HOME_SEARCH);
			}
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"清除首页搜索框热搜词缓存执行异常! ");
		}
		return result;
	}
	
	
	
}
