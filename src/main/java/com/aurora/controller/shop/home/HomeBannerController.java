package com.aurora.controller.shop.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aurora.controller.BaseController;
import com.aurora.entity.Result;
import com.aurora.entity.home.HomeSpecial;
import com.aurora.redis.RedisUtil;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.RedisConst;

/**
 * @Title: HomeBannerController.java 
 * @Package com.aurora.controller.home 
 * @Description: 首页Banner专题管理
 * @author SSY  
 * @date 2018年4月23日 下午2:07:20 
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/homeBanner")
public class HomeBannerController extends BaseController {


	/**
	 * @Title:   
	 * @Description: 查询Special列表
	 * @param    
	 * @return    homeSpecialList
	 * @author SSY
	 * @date 2018年4月28日 下午6:58:38
	 */
	@RequestMapping
	public String getSpecialList(ModelMap map) throws Exception {
		try {
			Integer module = Const.SPECIAL_BANNER_MODULE;
			List<HomeSpecial> homeSpecialList = homeSpecialServiceImpl.getHomeSpecialList(module);
			map.put("homeSpecialList", JSON.toJSON(homeSpecialList));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【ERROR】"+DateUtil.getTime()+"限时折扣活动列表查询执行异常! ");
		}
		return "system/homeManager/homeBanner";
	}
	
	
	/**
	 * @Title: updateHomeBanner
	 * @Description: 新增或者修改 首页BannerSpecial
	 * @param    Integer id(如果是null则是新增,否则就是修改), String url, String specialName, String specialMap, String specialBackColour(背景色), String specialBackground
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月28日 下午8:19:41
	 */
	@RequestMapping(value = "/updateHomeBanner")
	@ResponseBody
	public Result<Object> updateHomeBanner( Integer id, String url, String specialName, String specialMap, String specialBackColour, String specialBackground) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			Integer module = Const.SPECIAL_BANNER_MODULE;
			result = homeSpecialServiceImpl.updateHomeSpecial(module, id, url,specialName, specialMap,specialBackColour,specialBackground);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"新增限时折扣活动执行异常! ");
		}
		return result;
		
	}
	 
	
	/**
	 * @Title: deleteHomeBanner
	 * @Description: 删除首页 HomeBanner
	 * @param    Integer id
	 * @return Result<TimedActivity>  
	 * @author SSY
	 * @date 2018年4月28日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteHomeBanner")
	@ResponseBody
	public  Result<Object> deleteHomeBanner(Integer id){
		Result<Object> result = new Result<Object>();
		try {
			result = homeSpecialServiceImpl.deleteHomeSpecial(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除限时折扣活动执行异常! ");
		}
		return result;
	}
	
 
	/**
	 * @Title: clearHomeBannerCache
	 * @Description: 清除首页banner缓存
	 * @param     
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月8日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteRedisKey")
	@ResponseBody
	public  Result<Object> clearHomeBannerCache(){
		Result<Object> result = new Result<Object>();
		try {
			if (redisUtil.hasKey(RedisConst.HOME_BANNER)) {
				redisUtil.remove(RedisConst.HOME_BANNER);
			}
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"清除首页banner缓存执行异常! ");
		}
		return result;
	}
	
 
	
}
