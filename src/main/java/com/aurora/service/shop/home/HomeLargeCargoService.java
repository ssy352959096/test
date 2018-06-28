package com.aurora.service.shop.home;

import java.util.List;

import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.home.Category;
import com.aurora.entity.home.HomeLargeCargo;

/**
 * @Title: HomeLargeCargoService 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  商城首页大货集散维护接口
 * @author BYG
 * @date 2018年5月2日 
 * @version V2.0
 */
public interface HomeLargeCargoService {

	
	/**
	 * @Title: getLargeCargoByCategory 
	 * @Description: 分页查询大货集散
	 * @param    
	 * @return List<HomeLargeCargo>  
	 * @author SSY
	 * @date 2018年6月15日 上午10:54:50
	 */
	public List<HomeLargeCargo> getLargeCargoByCategory(Integer categoryID, Page<HomeLargeCargo> page) throws Exception;
	
	
	/**
	 * @Title: getHomeLCOrderByCategory 
	 * @Description: 查询该类目首页大货商品排序
	 * @param    
	 * @return   
	 * @author SSY
	 * @date 2018年6月15日 上午10:54:50
	 */
	public String getHomeLCOrderByCategory(Integer categoryID) throws Exception;
	
	/**
	 * @Title: getLargeCargoListByCategoryID 
	 * @Description:  添加或更新大货商品
	 * @param     HomeLargeCargo largeCargo(Integer id 如果是null或者找不到就是新增,否则修改 )
	 * @return    void 
	 * @author SSY
	 * @date 2018年6月15日 
	 */
	public void updateGoods(HomeLargeCargo largeCargo) throws Exception;

	/**
	 * @Title: deleteLargeCargo 
	 * @Description: 删除大货
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年6月25日 上午11:24:23
	 */
	public Result<Object> deleteLargeCargo(Integer id) throws Exception;
	
	/**
	 * @Title: updateHomeLCOrder 
	 * @Description: 更新大货集散首页展示排序
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年6月15日 下午7:26:21
	 */
	public Result<Object> updateHomeLCOrder(Integer categoryID, String homeLCOrder) throws Exception;

	

	
}
