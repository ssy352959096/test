package com.aurora.service.shop.home;

import java.util.List;

import com.aurora.entity.Result;
import com.aurora.entity.home.HomeFloorBrand;
import com.aurora.entity.home.HomeFloorGoods;

/**
 * @Title: HomeFloorService.java 
 * @Package com.aurora.service.shop.home 
 * @Description: 首页楼层类目商品管理
 * @author SSY  
 * @date 2018年5月2日 下午4:37:03 
 * @version V1.0
 */
public interface HomeFloorService {

	/**
	 * @Title: getHomeFloor 
	 * @Description:  按楼层类目id查询首页楼层展示商品
	 * @param    Integer category1ID
	 * @return     List<HomeFloorGoods>  
	 * @author SSY
	 * @date 2018年5月2日 下午4:53:16
	 */
	List<HomeFloorGoods> getHomeFloor(Integer category1ID) throws Exception;

	/**
	 * @Title: updateHomeFloorGoods 
	 * @Description: 修改首页各类目商品
	 * @param    Integer id(null新增), String goodsID, String goodsNewName, Integer category1ID,String homeMap
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月3日 上午9:55:22
	 */
	Result<Object> updateHomeFloorGoods(Integer id, String goodsID, String goodsNewName, Integer category1ID,String homeMap) throws Exception;
	
	/**
	 * @Title: deleteHomeFloorGoods 
	 * @Description: 删除首页楼层商品设置
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月3日 上午9:16:09
	 */
	Result<Object> deleteHomeFloorGoods(Integer id) throws Exception;

	
	/**
	 * @Title: getHomeFloorBrand 
	 * @Description: 查询首页各类目楼层热门品牌设置
	 * @param    
	 * @return  List<HomeFloorBrand>
	 * @author SSY
	 * @date 2018年5月4日 下午19:16:09
	 */
	public List<HomeFloorBrand> getHomeFloorBrand(Integer floor) throws Exception;
	
	/**
	 * @Title: updateHomeFloorBrand 
	 * @Description: 新增或者修改首页各类目楼层热门品牌设置
	 * @param    Integer id
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月4日 下午19:16:09
	 */
	public Result<Object> updateHomeFloorBrand(Integer floor,Integer location, Integer brandID, String brandName) throws Exception;
	
	
	
	
	
	
	
}
