package com.aurora.service.supply.intention;

import java.util.List;

import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.supply.intention.SupplyGoodsIntention;
import com.aurora.entity.supply.intention.SupplyIntention;

/**
 * @Title: SupplyIntentionService.java 
 * @Package com.aurora.service.dcc 
 * @Description: 全站供货意向service
 * @author SSY  
 * @date 2018年5月4日 上午10:53:02 
 * @version V1.0
 */
public interface SupplyIntentionService {

	/**
	 * @Title: getSupplyIntentionList 
	 * @Description: 查询全站供货意向信息
	 * @param    
	 * @return List<SupplyIntention>  
	 * @author SSY
	 * @date 2018年5月4日 上午10:57:37
	 */
	List<SupplyIntention> getSupplyIntentionList(Page<SupplyIntention> page) throws Exception;
	
	/**
	 * @Title: getSupplyIntentionNum 
	 * @Description: 查询全站供货意向信息数量
	 * @param    
	 * @return List<SupplyIntention>  
	 * @author SSY
	 * @date 2018年5月4日 上午10:57:37
	 */
	public int getSupplyIntentionNum(Page<SupplyIntention> page) throws Exception;

	/**
	 * @Title: deleteSupplyIntention 
	 * @Description: 删除全站供货意向
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月4日 下午4:04:54
	 */
	Result<Object> deleteSupplyIntention(Integer id) throws Exception;

	/**
	 * @Title: getSupplyGoodsIntentionList 
	 * @Description: 查询供货商品意向信息
	 * @param    
	 * @return List<SupplyGoodsIntention>  
	 * @author SSY
	 * @date 2018年5月4日 上午10:57:37
	 */
	List<SupplyGoodsIntention> getSupplyGoodsIntentionList(Page<SupplyGoodsIntention> page) throws Exception;
	
	/**
	 * @Title: getSupplyGoodsIntentionNum 
	 * @Description: 查询供货商品意向信息数量
	 * @param    
	 * @return  int
	 * @author SSY
	 * @date 2018年5月4日 上午10:57:37
	 */
	public int getSupplyGoodsIntentionNum(Page<SupplyGoodsIntention> page) throws Exception;
	
	/**
	 * @Title: deleteSupplyGoodsIntention 
	 * @Description: 删除供货商品意向货意向
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月4日 下午4:04:54
	 */
	Result<Object> deleteSupplyGoodsIntention(Integer id) throws Exception;
	
	
}
