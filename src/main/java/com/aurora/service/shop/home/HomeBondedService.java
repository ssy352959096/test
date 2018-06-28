package com.aurora.service.shop.home;

import java.util.List;

import com.aurora.entity.Result;
import com.aurora.entity.home.HomeBonded;

/**
 * @Title: HomeBannerService.java 
 * @Package com.aurora.service.shop.home 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author SSY  
 * @date 2018年4月28日 下午2:34:11 
 * @version V1.0
 */
public interface HomeBondedService {

	/**
	 * @Title: getHomeBondedList 
	 * @Description: 查询首页保税仓热卖产品列表
	 * @param    
	 * @return List<HomeBonded>  
	 * @author SSY
	 * @date 2018年4月28日 下午5:45:36
	 */
	List<HomeBonded> getHomeBondedList() throws Exception;

	/**
	 * @Title: deleteHomeBonded 
	 * @Description: 删除保税仓热卖
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月2日 上午9:32:22
	 */
	Result<Object> deleteHomeBonded(Integer id) throws Exception;

	/**
	 * @Title: updateHomeBonded 
	 * @Description: 修改保税仓热卖
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月2日 上午9:32:56
	 */
	Result<Object> updateHomeBonded(Integer id, String goodsID, String goodsNewName, Integer location, String homeMap) throws Exception;

	 
	
}
