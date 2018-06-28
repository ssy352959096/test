package com.aurora.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aurora.controller.BaseController;
import com.aurora.entity.User;
import com.aurora.service.InquiryService;
import com.aurora.service.OrderManageService;
import com.aurora.service.UserService;
import com.aurora.service.shop.home.HomeTimedActivityService;
import com.aurora.service.statistics.StatisticsWebsiteService;
import com.aurora.util.AliyunSMS;
import com.aurora.util.AppUtil;
import com.aurora.util.DateUtil;
import com.aurora.util.Logger;
import com.aurora.util.PageData;
import com.aurora.util.RedisConst;
import com.aurora.util.Tools;


/**
 * redis cache 工具类
 *
 * @author BYG 2017-11-13
 * @version 1.0
 */
@Component("myTask")
public class MyTask extends BaseController{

	protected static Logger logger = Logger.getLogger(AppUtil.class);
	
	
	
	 /**
	  * @Title: updateOrderAutoShut 
	  * @Description: 自动关闭两小时未付款订单
	  * @param    
	  * @return void  
	  * @author SSY
	  * @date 2018年2月22日 下午4:49:25
	  */
	@Scheduled(cron = "0 0 * * * ?")//秒域必须写0，若写*系统会默认分域为秒域
   public void updateOrderAutoShut(){
		try {
			int num = orderManageServiceImpl.updateOrderAutoShut();
			logger.info("【定时任务】 本次关闭了"+num+"单超过两小时未付款订单! ");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【ERROR】 定时关闭超两小时订单执行异常! ");
		}
	}
	
	
	/**接单提醒
	 * 15分钟未接订单，发短信给订单对应的销售。定时任务每15分钟执行一次
	 * 公池客户发送给值班运营人员
	 */ 
	@Scheduled(cron = "0 0/15 8-23 * * ?")//秒域必须写0，若写*系统会默认分域为秒域
    public void missedOrderRemind(){
		System.out.println(DateUtil.getTime());
		String salesRoleID = "6";//销售角色ID
		String watchRoleID = "15";//值班角色ID
		try {
			List<PageData> salesUser = userServiceImpl.getUserIDBySalesRoleID(salesRoleID);
			if (!salesUser.isEmpty()) {
				for (PageData user : salesUser) {
					String userID = user.getString("user_id");
					int	orderNum = orderManageServiceImpl.get30MMissedOrderNum(userID);
					if (orderNum > 0) {
						String userMobile = user.getString("user_mobile");
						AliyunSMS.sendSMS("SMS_113660592", userMobile, String.valueOf(orderNum));
					}
				}
			}
			User watchUser= userServiceImpl.getWatchUser(watchRoleID);
			if (watchUser != null && Tools.notEmpty(watchUser.getUserMobile())) {
				int	orderNum = orderManageServiceImpl.get30MMissedOrderNum("888");//公池客户默认销售经理ID：888
				if (orderNum > 0) {
					AliyunSMS.sendSMS("SMS_113660592", watchUser.getUserMobile(), String.valueOf(orderNum));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【ERROR定时任务】接单短信提醒 执行异常! ");
		}
    }

	
	 /**
	  * @Title: updateModuleClickTimes 
	  * @Description: 询价短信提醒,一小时一次,公池客户发送给运营人员
	  * @param    
	  * @return void  
	  * @author SSY
	  * @date 2018年2月22日 下午4:49:25
	  */
	@Scheduled(cron = "0 0 9-23 * * ?")//秒域必须写0，若写*系统会默认分域为秒域
    public void inquriyRemind(){
		try {
			List<PageData> noQuotationRemainInquiry = inquiryServiceImpl.getNoQuotationRemain();
			for (PageData pageData : noQuotationRemainInquiry) {
				//String salesManager = pageData.getString("sales_manager");//客户专属id,公池客户则发送至虚拟销售代表设置的手机号
				String userMobile = pageData.getString("user_mobile");//销售代表手机号
				String num = pageData.getString("num");//未报价询价单数量
				AliyunSMS.sendSMS("SMS_126360223", userMobile, num);
				logger.info("【定时任务】 本次向"+userMobile+"发送了询价提醒! ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【ERROR定时任务】询价短信提醒 执行异常! ");
		}
	}
	
	
	/**
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓数据统计pv/uv/首页热点图↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓	
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	 */

	/**
	 * @Title: updateStatistics 
	 * @Description: 定时更新数据统计---pv ;一天更新一次;凌晨点更新昨日即可;防止出现最后一小时无法统计
	 * @param    
	 * @return void  
	 * @author SSY
	 * @date 2018年3月26日 下午4:59:57
	 */
	@Scheduled(cron = "0 0 2 * * ?")//秒  分 小时  天 周 年
	public void updateYestdayPV(){
		try {
			statisticsWebsiteServiceImpl.updatePV(-1);//-1更新昨天 0更新今天
			logger.info( "【定时任务】本次定时更新了PV! ");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【ERROR定时任务】定时更新 PV 执行异常! ");
		}
	}
	 
	/**
	 * @Title: updateStatistics 
	 * @Description: 定时更新数据统计---pv ;一小时更新一次
	 * @param    
	 * @return void  
	 * @author SSY
	 * @date 2018年3月26日 下午4:59:57
	 */
	@Scheduled(cron = "0 15 0-23 * * ?")//秒  分 小时  天 周 年
	public void updatePV(){
		try {
			statisticsWebsiteServiceImpl.updatePV(0);//-1更新昨天 0更新今天
			logger.info( "【定时任务】本次定时更新了PV! ");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【ERROR定时任务】定时更新 PV 执行异常! ");
		}
	}
	
	/**
	 * @Title: updateStatistics 
	 * @Description: 定时更新数据统计---uv ;一天更新一次;凌晨点更新昨日即可;防止出现最后一小时无法统计
	 * @param    
	 * @return void  
	 * @author SSY
	 * @date 2018年3月26日 下午4:59:57
	 */
	@Scheduled(cron = "0 0 2 * * ?")//秒  分 小时  天 周 年
	public void updateYestdayUV(){
		try {
			statisticsWebsiteServiceImpl.updateUV(-1);//-1更新昨天 0更新今天
			logger.info( "【定时任务】本次定时更新了UV! ");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【ERROR定时任务】定时更新 UV执行异常! ");
		}
	}
	
	 
	
	 
		/**
		 * @Title: updateStatistics 
		 * @Description: 定时更新数据统计---uv ;一小时更新一次
		 * @param    
		 * @return void  
		 * @author SSY
		 * @date 2018年3月26日 下午4:59:57
		 */
		@Scheduled(cron = "0 15 0-23 * * ?")//秒  分 小时  天 周 年
		public void updateUV(){
			try {
				statisticsWebsiteServiceImpl.updateUV(0);//-1更新昨天 0更新今天
				logger.info( "【定时任务】本次定时更新了UV! ");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("【ERROR定时任务】定时更新 UV执行异常! ");
			}
		}
		
	 /**
	  * @Title: updateModuleClickTimes 
	  * @Description:  更新 首页热点图  ;凌晨2点更新昨日即可;防止出现最后一小时无法统计; 更新两次,第一次新增,第二次更新;
	  * @param    
	  * @return void  
	  * @author SSY
	  * @date 2018年2月22日 下午4:49:25
	  */
	@Scheduled(cron = "0 0 2 * * ?")//秒  分 小时  天 周 年  ;秒域必须写0，若写*系统会默认分域为秒域
    public void updateHomeYestdayClick(){
		try {
			statisticsWebsiteServiceImpl.updateHomeClick(-1);//-1更新昨天 0更新今天
			logger.info( "【定时任务】本次定时更新了首页热点图! ");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【ERROR定时任务】定时更新 首页热点图图执行异常! ");
		}
	}
	
	 /**
	  * @Title: updateModuleClickTimes 
	  * @Description:  更新 首页热点图  ; 一小时更新一次
	  * @param    
	  * @return void  
	  * @author SSY  
	  * @date 2018年2月22日 下午4:49:25
	  */
	@Scheduled(cron = "0 15 0-23 * * ?")//秒  分 小时  天 周 年  ;秒域必须写0，若写*系统会默认分域为秒域
    public void updateHomeClick(){
		try {
			statisticsWebsiteServiceImpl.updateHomeClick(0);//-1更新昨天 0更新今天
			logger.info( "【定时任务】本次定时更新了首页热点图! ");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【ERROR定时任务】定时更新 首页热点图执行异常! ");
		}
	}
	
	
	/**
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓定时活动商品↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓	
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	 */
	 /**
	  * @Title: checkTimedActivity 
	  * @Description: 定时更新显示折扣活动
	  * @param    
	  * @return void  
	  * @author SSY  
	  * @date 2018年2月22日 下午4:49:25
	  */
	@Scheduled(cron = "0 0 * * * ?")//秒  分 小时  天 周 年  ;秒域必须写0，若写*系统会默认分域为秒域
  	public void checkTimedActivity(){
		try {
			homeTimedActivityServiceImpl.updateCheckTimedActivity();//检查并更新活动的有效性;
			if (redisUtil.hasKey(RedisConst.HOME_TIMED_ACTIVITY)) {//清除首页活动缓存
				redisUtil.remove(RedisConst.HOME_TIMED_ACTIVITY);
			}
			logger.info( "【定时任务】本次定时更新了限时折扣! ");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【ERROR定时任务】定时更新限时折扣执行异常! ");
		}
	}
	
	
	/**
	 * 每天零时定时处理微仓免仓期
	 */
	@Scheduled(cron = "0 0 0 * * ?")//秒  分 小时  天 周 年  ;秒域必须写0，若写*系统会默认分域为秒域
  	public void updateFreeDemurrage(){
		try {
			int num = microCapsuleServiceImpl.updateFreeDemurrage();//检查并更新活动的有效性;
			logger.info("【定时任务】本次定时 处理了"+num+"个微仓商品微仓免仓期! ");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【ERROR定时任务】本次定时定时处理微仓免仓期执行异常! ");
		}
	}
	
	
}
