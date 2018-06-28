package com.aurora.controller.dcc;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.entity.Page;
import com.aurora.service.dcc.BrandRulesSettingService;
import com.aurora.service.dcc.BrandSmallClassSettingService;
import com.aurora.service.dcc.CommodityEntryService;
import com.aurora.service.dcc.EnterpriseService;
import com.aurora.util.PageData;
import com.aurora.util.UuidUtil;

/**
 * @Title: BaseController.java 
 * @Package  com.aurora.controller.dcc
 * @Description:  dcc下Service层接口总集
 * @author KJH  
 * @date 2018年6月27日 下午2:34:49 
 * @version V1.0
 */
public class BaseController{

	protected Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 6870193854124984661L;
	
	@Autowired
	protected BrandRulesSettingService brandRulesSettingService;
	@Autowired
	protected BrandSmallClassSettingService brandSmallClassSettingService;
	@Autowired
	protected EnterpriseService enterpriseService;
	
	@Autowired
	protected CommodityEntryService commodityEntryService;
	 
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
