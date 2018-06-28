package com.aurora.service.shop.home;

import java.math.BigDecimal;
import java.util.List;

import com.aurora.entity.GoodsManage;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.home.TimedActivity;

public interface HomeTimedActivityService {

	
	
	/**
	 * @Title: addTimedActivity 
	 * @Description:  新增或者修改限时折扣活动 
	 * @param     String timedActivityJson
	 * @return    
	 * @author SSY
	 * @date 2018年4月24日 上午11:33:13
	 */
	Result<Object> updateTimedActivity(String timedActivityJson) throws Exception;

	
	/**
	 * @Title: getActivityList 
	 * @Description: 分页条件查询活动列表;
	 * @param    
	 * @return List<TimedActivity>  
	 * @author SSY
	 * @date 2018年4月24日 下午7:00:44
	 */
	List<TimedActivity> getActivityList(Page<TimedActivity> page) throws Exception;

	/**
	 * @Title: getActivityNum 
	 * @Description: 条件查询活动数量
	 * @param    
	 * @return int  
	 * @author SSY
	 * @date 2018年4月24日 下午7:09:27
	 */
	int getActivityNum(Page<TimedActivity> page) throws Exception;

 
	/**
	 * @Title: getTimedActivity 
	 * @Description: 删除活动
	 * @param 
	 * @return TimedActivity  
	 * @author SSY
	 * @date 2018年4月24日 下午5:20:04
	 */
	Result<Object> deleteTimedActivity(Integer activityID) throws Exception;
	
	/**
	 * @Title: deleteTimedGoods 
	 * @Description: 删除活动商品
	 * @param Integer id
	 * @return TimedActivity  
	 * @author SSY
	 * @date 2018年4月24日 下午5:20:04
	 */
	Result<Object> deleteTimedGoods(Integer id) throws Exception;

	/**
	 * @Title: updateCheckTimedActivity 
	 * @Description: 限时折扣活动定时检测
	 * @param    
	 * @return void  
	 * @author SSY
	 * @date 2018年4月26日 下午6:31:32
	 */
	void updateCheckTimedActivity()throws Exception;
	
	/**
	 * @Title: searchGoods 
	 * @Description: 搜索商品
	 * @param    
	 * @return Result<GoodsManage>  
	 * @author SSY
	 * @date 2018年4月26日 下午4:04:06
	 */
	Result<GoodsManage> searchGoods(String goodsID) throws Exception;
	
	
	
//	/**
//	 * @Title: getTimedActivity 
//	 * @Description: 查询限时折扣活动
//	 * @param    String activityID
//	 * @return TimedActivity  
//	 * @author SSY
//	 * @date 2018年4月24日 下午5:20:04
//	 */
//	TimedActivity getTimedActivity(Integer activityID) throws Exception;

	
//	/**
//	 * @Title: updateTimedActivity 
//	 * @Description: 修改活动日期
//	 * @param    Integer activityID, String beginTime, String endTime
//	 * @return Result<Object>  
//	 * @author SSY
//	 * @date 2018年4月24日 下午7:09:27
//	 */
//	Result<Object> updateTimedActivity(Integer activityID, String beginTime, String endTime) throws Exception;


	
	
	
	
	
}
