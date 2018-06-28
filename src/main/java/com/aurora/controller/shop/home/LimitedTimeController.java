package com.aurora.controller.shop.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aurora.controller.BaseController;
import com.aurora.entity.GoodsManage;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.home.TimedActivity;
import com.aurora.service.shop.home.HomeTimedActivityService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.RedisConst;
import com.aurora.util.Tools;

/**
 * @Title: LimitedTimeController.java 
 * @Package com.aurora.controller.home 
 * @Description: 限时活动管理 
 * @author SSY  
 * @date 2018年4月23日 下午2:07:20 
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/timedActivity")
public class LimitedTimeController extends BaseController {

//	@Autowired
//	private RedisUtil redisUtil;

	/**
	 * @Title: getActivityList 
	 * @Description: 查询活动列表
	 * @param    
	 * @return    activityList
	 * @author SSY
	 * @date 2018年4月23日 下午6:58:38
	 */
	@RequestMapping
	public String getActivityList(ModelMap map, Page<TimedActivity> page) throws Exception {
		try {
			page.setPageSize(20);
			TimedActivity timedActivity = new TimedActivity();
			page.setT(timedActivity);
			List<TimedActivity> activityList = homeTimedActivityServiceImpl.getActivityList(page);
			int activityNum = homeTimedActivityServiceImpl.getActivityNum(page);
			page.setTotalRecord(activityNum);
			map.put("activityList", JSON.toJSON(activityList));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【ERROR】"+DateUtil.getTime()+"限时折扣活动列表查询执行异常! ");
		}
		return "system/homeManager/timedActivity";
	}
	
	
	/**
	 * @Title: updateTimedActivity 
	 * @Description: 新增或者修改活动
	 * @param   timedActivityJson:{"activityID(为空的话就是新增)":"","beginTime":"","endTime":"","timedGoodsList":[{"id(为空的话新增,不为空修改)":"","goodsID":"","goodsNewName":"","discountPrice":"","availableSellNum":""},...]}
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月23日 下午8:19:41
	 */
	@RequestMapping(value = "/updateTimedActivity")
	@ResponseBody
	public Result<Object> updateTimedActivity(String timedActivityJson) throws Exception {
		PageData pd = this.getPageData();
		System.out.println(pd);
		Result<Object> result = new Result<Object>();
		try {
			result = homeTimedActivityServiceImpl.updateTimedActivity(timedActivityJson);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"新增限时折扣活动执行异常! ");
		}
		return result;
		
	}
	 
	
	/**
	 * @Title: deleteTimedActivity 
	 * @Description: 删除活动
	 * @param    Integer activityID
	 * @return Result<TimedActivity>  
	 * @author SSY
	 * @date 2018年4月23日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteTimedActivity")
	@ResponseBody
	public  Result<Object> deleteTimedActivity(Integer activityID){
		Result<Object> result = new Result<Object>();
		try {
			result = homeTimedActivityServiceImpl.deleteTimedActivity(activityID);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除限时折扣活动执行异常! ");
		}
		return result;
	}
	
	/**
	 * @Title: deleteTimedActivity 
	 * @Description: 删除活动商品
	 * @param    Integer id
	 * @return Result<TimedActivity>  
	 * @author SSY
	 * @date 2018年4月23日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteTimedGoods")
	@ResponseBody
	public  Result<Object> deleteTimedGoods(Integer id){
		Result<Object> result = new Result<Object>();
		try {
			result = homeTimedActivityServiceImpl.deleteTimedGoods(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除限时折扣活动商品查询执行异常! ");
		}
		return result;
	}
	
	/**
	 * @Title: searchGoods 
	 * @Description: 搜索商品
	 * @param    
	 * @return Result<GoodsManage>  
	 * @author SSY
	 * @date 2018年4月26日 下午4:04:06
	 */
	@RequestMapping(value = "/searchGoods")
	@ResponseBody
	public  Result<GoodsManage> searchGoods(String goodsID){
		Result<GoodsManage> result = new Result<GoodsManage>();
		try {
			result = homeTimedActivityServiceImpl.searchGoods(goodsID);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"商品查询执行异常! ");
		}
		return result;
	}
	
	
	/**
	 * @Title: clearTimedActivityCache
	 * @Description: 清除首页限时折扣活动缓存
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月8日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteRedisKey")
	@ResponseBody
	public  Result<Object> clearTimedActivityCache(){
		Result<Object> result = new Result<Object>();
		try {
			if (redisUtil.hasKey(RedisConst.HOME_TIMED_ACTIVITY)) {
				redisUtil.remove(RedisConst.HOME_TIMED_ACTIVITY);
			}
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"清除首页限时折扣活动缓存执行异常! ");
		}
		return result;
	}
	
 
	
}
