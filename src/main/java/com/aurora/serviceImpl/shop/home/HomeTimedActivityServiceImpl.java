package com.aurora.serviceImpl.shop.home;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.aurora.dao.DAO;
import com.aurora.entity.GoodsManage;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.home.TimedActivity;
import com.aurora.entity.home.TimedGoods;
import com.aurora.service.shop.home.HomeTimedActivityService;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.Tools;

/**
 * @Title: SsyHomeManageServiceImpl.java 
 * @Package com.aurora.serviceImpl 
 * @Description: 首页限时抢购活动---
 * 				添加商品时,保存商品原价信息至活动商品表中;
 * 				当活动开始时,将原价写入商品管理表中,更改商品现价,标记该商品价格为活动价格;
 *  	                 当活动结束,恢复原价;
 * @author SSY  
 * @date 2018年4月24日 上午11:33:56 
 * @version V1.0
 */
@Service
public class HomeTimedActivityServiceImpl implements HomeTimedActivityService{
	
	@Autowired
	private DAO daoSupport;

	
	/**
	 * @Title: getActivityList 
	 * @Description: 分页条件查询活动列表;
	 * @param    
	 * @return List<TimedActivity>  
	 * @author SSY
	 * @date 2018年4月24日 下午7:00:44
	 */
	@SuppressWarnings("unchecked")
	public List<TimedActivity> getActivityList(Page<TimedActivity> page) throws Exception{
		List<Integer> activityIDList = (List<Integer>) daoSupport.findForList("HomeTimedActivityReadMapper.getActivityIDList", page);
		List<TimedActivity> activityList = (List<TimedActivity>) daoSupport.findForList("HomeTimedActivityReadMapper.getActivityList", activityIDList);
		return activityList;
	}
	
	/**
	 * @Title: getActivityNum 
	 * @Description: 条件查询活动数量
	 * @param    Page<TimedActivity> page
	 * @return int  
	 * @author SSY
	 * @date 2018年4月24日 下午7:09:27
	 */
	@Override
	public int getActivityNum(Page<TimedActivity> page) throws Exception{
		Object activityNum =  daoSupport.findForObject("HomeTimedActivityReadMapper.getActivityNum", page);
		return null!=activityNum?(int)activityNum:0;
	}

	
	/**
	 * @Title: updateTimedActivity 
	 * @Description:  新增或者修改活动 
	 * @param   String timedActivityJson
	 * @return    
	 * @author SSY
	 * @date 2018年4月24日 上午11:33:13
	 */
	@Override
	public Result<Object> updateTimedActivity(String timedActivityJson) throws Exception{
		
		Result<Object> result = new Result<Object>();
		TimedActivity timedActivity = null;
		if (Tools.isEmpty(timedActivityJson)) {
			result.setMsg("参数错误! ");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		try {
			timedActivity = JSON.parseObject(timedActivityJson, TimedActivity.class);
		} catch (Exception e) {
			result.setMsg("参数错误! ");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		timedActivity.setCreateTime(DateUtil.getTime());
		timedActivity.setCreator(Jurisdiction.getUserEmail());
		
		if (timedActivity.getActivityID()!=null) {//修改活动
			
			//1.修改活动表
			int updateNum = (int)daoSupport.update("HomeTimedActivityWriteMapper.updateTimedActivity", timedActivity);
			if (updateNum<=0) {
				result.setMsg("参数错误! ");
				result.setState(Result.STATE_ERROR);
				return result;
			}
			//2.修改各活动商品
			for (TimedGoods timedGoods : timedActivity.getTimedGoodsList()) {
				if (Tools.isEmpty(timedGoods.getGoodsID())) {
					continue;
				}
				Result<Object> result2 = new Result<Object>();
				if (timedGoods.getId()==null) {//新增活动商品
					result2 = this.addTimedGoods(timedGoods);
				}else{//修改活动商品
					result2 = this.updateTimedGoods(timedGoods);
				}
				if (!result2.getState().equals(Result.STATE_SUCCESS)) {
					return result2;
				}
			}
			
		}else{//新增活动
			//1.新增活动表
			daoSupport.save("HomeTimedActivityWriteMapper.addTimedActivity", timedActivity);
			int activityID = timedActivity.getActivityID();
			//2.新增活动商品;
			for (TimedGoods timedGoods : timedActivity.getTimedGoodsList()) {
				if (Tools.isEmpty(timedGoods.getGoodsID())) {
					continue;
				}
				timedGoods.setActivityID(activityID);
				Result<Object> result2 = this.addTimedGoods(timedGoods);
				if (!result2.getState().equals(Result.STATE_SUCCESS)) {
					return result2;
				}
			}
		}
		
		result.setState(Result.STATE_SUCCESS);
		return result;
	}
	
	
	/**
	 * @Title: addTimedGoods 
	 * @Description: 新增活动商品
	 * @param    Integer activityID,  String goodsID, String goodsNewName,  BigDecimal discountPrice, Integer availableSellNum
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月26日 下午4:10:29
	 */
	private Result<Object> addTimedGoods( TimedGoods timedGoods) throws Exception{
		Result<Object> result = new Result<Object>();
		  
		//1.取出商品原价;
		String goodsID = timedGoods.getGoodsID();
		GoodsManage goods = (GoodsManage)daoSupport.findForObject("HomeTimedActivityReadMapper.searchGoods", goodsID);
		BigDecimal originalPrice1 = null;
		BigDecimal originalPrice2 = null;
		if (goods.getIsActivity()==Const.GOODS_IS_ACTIVITY) {//现价是折扣价
			 originalPrice1 = goods.getOriginalPrice1();
			 originalPrice2 = goods.getOriginalPrice2();
			 if (originalPrice2==null||originalPrice1==null) {
				 result.setMsg("商品数据错误! 请联系管理员检查商品!  ");
				 result.setState(Result.STATE_ERROR);
				 return result;
			}
		}else{//现价是原价
			 originalPrice1 = goods.getGoodsPrice1();
			 originalPrice2 = goods.getGoodsPrice2();
		}
		timedGoods.setOriginalPrice1(originalPrice1);
		timedGoods.setOriginalPrice2(originalPrice2);
		timedGoods.setCreator(Jurisdiction.getUserEmail());
		timedGoods.setCreateTime(DateUtil.getTime());
		//2.保存活动商品的信息;
		int addNum = (int)daoSupport.save("HomeTimedActivityWriteMapper.addTimedGoods", timedGoods);
		result.setState(addNum>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		result.setMsg(addNum>=1?"操作成功!":"操作失败!");
		return result;
	}
	 
	 
	/**
	 * @Title: updateTimedGoods 
	 * @Description: 修改活动商品,要让原来的商品恢复原价;
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月26日 下午5:05:19
	 */
	private Result<Object> updateTimedGoods(TimedGoods timedGoods) throws Exception{
		Result<Object> result = new Result<Object>();
		 
		//1.取出商品原价;
		String goodsID = timedGoods.getGoodsID();
		GoodsManage goods = (GoodsManage)daoSupport.findForObject("HomeTimedActivityReadMapper.searchGoods", goodsID);
		BigDecimal originalPrice1 = null;
		BigDecimal originalPrice2 = null;
		if (goods.getIsActivity()==Const.GOODS_IS_ACTIVITY) {//现价是折扣价
			 originalPrice1 = goods.getOriginalPrice1();
			 originalPrice2 = goods.getOriginalPrice2();
			 if (originalPrice2==null||originalPrice1==null) {
				 result.setMsg("商品数据错误! 请联系管理员检查商品!  ");
				 result.setState(Result.STATE_ERROR);
				 return result;
			}
		}else{//现价是原价
			 originalPrice1 = goods.getGoodsPrice1();
			 originalPrice2 = goods.getGoodsPrice2();
		}
		timedGoods.setOriginalPrice1(originalPrice1);
		timedGoods.setOriginalPrice2(originalPrice2);
		timedGoods.setGoodsID(goodsID.replace(" ", ""));
		timedGoods.setUpdator(Jurisdiction.getUserEmail());
		timedGoods.setUpdateTime(DateUtil.getTime());
		//2.保存活动商品的信息;
		int updateNum = (int)daoSupport.update("HomeTimedActivityWriteMapper.updateTimedGoods", timedGoods);
		result.setState(updateNum>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		result.setMsg(updateNum>=1?"操作成功!":"操作失败!");
		return result;
	}
	
	/**
	 * @Title: deleteTimedActivity 
	 * @Description: 删除活动
	 * @param 
	 * @return TimedActivity  
	 * @author SSY
	 * @date 2018年4月24日 下午5:20:04
	 */
	@Override
	public Result<Object> deleteTimedActivity(Integer activityID) throws Exception{
		Result<Object> result = new Result<Object>();
		if (null==activityID) {
			result.setMsg("参数错误! ");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		int deleteNum = (int)daoSupport.delete("HomeTimedActivityWriteMapper.deleteTimedActivity", activityID);
		result.setState(deleteNum>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		result.setMsg(deleteNum>=1?"操作成功!":"操作失败!");
		return result;
	}
	
	/**
	 * @Title: deleteTimedGoods 
	 * @Description: 删除活动商品
	 * @param Integer id
	 * @return TimedActivity  
	 * @author SSY
	 * @date 2018年4月24日 下午5:20:04
	 */
	@Override
	public Result<Object> deleteTimedGoods(Integer id) throws Exception{
		Result<Object> result = new Result<Object>();
		if (null==id) {
			result.setMsg("参数错误! ");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		int deleteNum = (int)daoSupport.delete("HomeTimedActivityWriteMapper.deleteTimedGoods", id);
		result.setState(deleteNum>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		result.setMsg(deleteNum>=1?"操作成功!":"操作失败!");
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
	@Override
	public Result<GoodsManage> searchGoods(String goodsID) throws Exception{
		Result<GoodsManage> result = new Result<GoodsManage>();
		if (Tools.isEmpty(goodsID)) {
			result.setMsg("参数错误! ");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		goodsID = goodsID.replace(" ", "");
		GoodsManage goods = (GoodsManage)daoSupport.findForObject("HomeTimedActivityReadMapper.searchGoods", goodsID);
		result.setResult(goods);
		result.setMsg(goods!=null?"":"商品不存在！没点逼数？ ");
		result.setState(goods!=null?Result.STATE_SUCCESS:Result.STATE_ERROR);
		return result;
	}
	

	/**
	 * @Title: updateCheckTimedActivity 
	 * @Description: 限时折扣活动定时检测
	 * @param    
	 * @return void  
	 * @author SSY
	 * @date 2018年4月26日 下午6:31:32
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateCheckTimedActivity()throws Exception{
		
		//1.查询生效中且endTime<Now()
		Page<TimedActivity> page = new Page<TimedActivity>();
		page.setFromIndex(0);
		page.setPageSize(Integer.MAX_VALUE);
		String today = DateUtil.getSdfHour();
		TimedActivity timedActivity = new TimedActivity();
		timedActivity.setActivityState(Const.ACTIVITY_VALID_STATE);//生效中的
		timedActivity.setEndTime(today);
		page.setT(timedActivity);
		List<TimedActivity> activityValidList = (List<TimedActivity>)daoSupport.findForList("HomeTimedActivityReadMapper.getActivityValidList", page);
		
		//2.停掉生效的活动,并且恢复商品现价至原价,标价商品为原价;
		for (Iterator iterator = activityValidList.iterator(); iterator.hasNext();) {
			TimedActivity timedActivity2 = (TimedActivity) iterator.next();
			for (TimedGoods timedGoods : timedActivity2.getTimedGoodsList()) {
				daoSupport.update("HomeTimedActivityWriteMapper.updateGoodsToOriginal", timedGoods);//回到原价;
			}
			daoSupport.update("HomeTimedActivityWriteMapper.updateActivityDown", timedActivity2);//停掉活动;
		}
		
		Thread.sleep(5000); 
		
		//3.查询所有beginTime<=Now()且未生效且endTime()>=NOW的活动;
		timedActivity.setActivityState(Const.ACTIVITY_INVALID_STATE);
		timedActivity.setBeginTime(today);
		timedActivity.setEndTime(today);
		page.setT(timedActivity);
		List<TimedActivity> activityWillValidList = (List<TimedActivity>)daoSupport.findForList("HomeTimedActivityReadMapper.getActivityWillValidList", page);
		
		//4.开启beginTime<=Now()且未生效且endTime()>=NOW的活动,修改商品现价至折扣价,标识商品为活动价
		for (Iterator iterator = activityWillValidList.iterator(); iterator.hasNext();) {
			TimedActivity timedActivity3 = (TimedActivity) iterator.next();
			for (TimedGoods timedGoods : timedActivity3.getTimedGoodsList()) {
				daoSupport.update("HomeTimedActivityWriteMapper.updateGoodsToActivity", timedGoods);//修改活动商品价格至折扣价,并且保存商品原价
			}
			daoSupport.update("HomeTimedActivityWriteMapper.updateActivityOn", timedActivity3);//开启活动;
		}
		
		//5.对活动中所有商品已售数量累加处理;
		daoSupport.update("HomeTimedActivityWriteMapper.updateActivitySoldNum");
		//6.更新活动中商品销售状态1.可售,2已售罄
		daoSupport.update("HomeTimedActivityWriteMapper.updateActivityGoodsState");
		
	}


 
	
//	
//	/**
//	 * @Title: getTimedActivity 
//	 * @Description: 查询限时折扣活动
//	 * @param 
//	 * @return TimedActivity  
//	 * @author SSY
//	 * @date 2018年4月24日 下午5:20:04
//	 */
//	@Override
//	public TimedActivity getTimedActivity(Integer activityID) throws Exception{
//		TimedActivity timedActivity = (TimedActivity)daoSupport.findForObject("HomeTimedActivityReadMapper.getTimedActivity", activityID);
//		return timedActivity;
//	}
//	
	
	
	
	/**
//	 * @Title: updateTimedActivity 
//	 * @Description: 修改活动 
//	 * @param    Integer activityID, String beginTime, String endTime
//	 * @return Result<Object>  
//	 * @author SSY
//	 * @date 2018年4月24日 下午7:09:27
//	 */
//	public Result<Object> updateTimedActivity(Integer activityID, String beginTime, String endTime) throws Exception{
//		Result<Object> result = new Result<Object>();
//		if (activityID==null||Tools.isEmpty(beginTime)||Tools.isEmpty(endTime)) {
//			result.setMsg("参数错误! ");
//			result.setState(Result.STATE_ERROR);
//			return result;
//		}
//		TimedActivity timedActivity = new TimedActivity();
//		timedActivity.setActivityID(activityID);
//		timedActivity.setBeginTime(beginTime.replace(" ", ""));
//		timedActivity.setEndTime(endTime.replace(" ", ""));
//		timedActivity.setUpdateTime(DateUtil.getTime());
//		timedActivity.setUpdator(Jurisdiction.getUserEmail());
//		//修改活动时间;
//		daoSupport.update("HomeTimedActivityWriteMapper.updateTimedActivity", timedActivity);
//		result.setState(Result.STATE_SUCCESS);
//		return result;
//	}
//	
	
	
	

	
}
