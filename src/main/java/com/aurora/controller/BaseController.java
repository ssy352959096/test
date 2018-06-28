package com.aurora.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.entity.Page;
import com.aurora.redis.RedisUtil;
import com.aurora.service.BrandManageService;
import com.aurora.service.GoodsManageService;
import com.aurora.service.InquiryService;
import com.aurora.service.MicroCapsuleService;
import com.aurora.service.OrderManageService;
import com.aurora.service.UserService;
import com.aurora.service.shop.home.GoodsCategoryService;
import com.aurora.service.shop.home.HomeBondedService;
import com.aurora.service.shop.home.HomeFloorService;
import com.aurora.service.shop.home.HomeKeywordService;
import com.aurora.service.shop.home.HomeLargeCargoService;
import com.aurora.service.shop.home.HomeSpecialService;
import com.aurora.service.shop.home.HomeTimedActivityService;
import com.aurora.service.statistics.StatisticsWebsiteService;
import com.aurora.service.supply.intention.SupplyIntentionService;
import com.aurora.util.PageData;
import com.aurora.util.UuidUtil;

/**
 * 描述:BaseController
 * 创建: BYG 2017/5/24
 * 修改:
 * @version 1.0
 */
public class BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 6357869213649815390L;
	
	@Autowired
	protected RedisUtil redisUtil;
	@Autowired
	protected HomeTimedActivityService homeTimedActivityServiceImpl;
	@Autowired
	protected HomeSpecialService homeSpecialServiceImpl;
	@Autowired
	protected HomeBondedService homeBondedServiceImpl;
	@Autowired
	protected HomeKeywordService homeKeywordServiceImpl;
	@Autowired
	protected HomeFloorService homeFloorServiceImpl;
	@Autowired
	protected SupplyIntentionService supplyIntentionServiceImpl;
	@Autowired 
	protected GoodsCategoryService goodsCategoryServiceImpl;
	@Autowired
	protected BrandManageService brandManageServiceImpl;
	
	@Autowired 
	protected HomeLargeCargoService homeLargeCargoServiceImpl;
	@Autowired 
	protected GoodsCategoryService GoodsCategoryServiceImpl;
	@Autowired 
	protected GoodsManageService GoodsManageServiceImpl;
	@Autowired 
	protected RedisUtil RedisUtil;
	
	@Autowired 
	protected OrderManageService orderManageServiceImpl;
	@Autowired 
	protected StatisticsWebsiteService statisticsWebsiteServiceImpl;
	@Autowired 
	protected UserService userServiceImpl;
	@Autowired 
	protected InquiryService inquiryServiceImpl;
	@Autowired
	protected MicroCapsuleService microCapsuleServiceImpl;
	
	
	
	/**new PageData对象
	 * @return
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**得到ModelAndView
	 * @return
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**得到request
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	 

	/**得到32位uuid
	 *@return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}
	
	/**得到分页列表信息
	 * @return
	 */
	public Page getPage(){
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
}
