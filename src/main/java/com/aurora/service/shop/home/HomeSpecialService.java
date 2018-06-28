package com.aurora.service.shop.home;

import java.util.List;

import com.aurora.entity.Result;
import com.aurora.entity.home.HomeSpecial;

/**
 * @Title: HomeBannerService.java 
 * @Package com.aurora.service.shop.home 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author SSY  
 * @date 2018年4月28日 下午2:34:11 
 * @version V1.0
 */
public interface HomeSpecialService {

	/**
	 * @Title: getHomeSpecialList 
	 * @Description: 查询 专题列表
	 * @param    
	 * @return List<HomeBanner>  
	 * @author SSY
	 * @date 2018年4月28日 下午2:41:46
	 */
	List<HomeSpecial> getHomeSpecialList(Integer module) throws Exception;

	/**
	 * @Title: updateHomeSpecial 
	 * @Description: 更新 专题
	 * @param    Integer module, Integer id, String url,String specialName,  String specialMap ,String specialBackColour, String specialBackground
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月28日 下午3:07:00
	 */
	Result<Object> updateHomeSpecial(Integer module, Integer id, String url,String specialName,  String specialMap, String specialBackColour, String specialBackground) throws Exception;

	/**
	 * @Title: deleteHomeSpecial 
	 * @Description: 删除 专题
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月28日 下午3:37:47
	 */
	Result<Object> deleteHomeSpecial(Integer id) throws Exception;

	
}
