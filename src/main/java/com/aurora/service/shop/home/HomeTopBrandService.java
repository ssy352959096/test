package com.aurora.service.shop.home;

import java.util.List;

import com.aurora.entity.home.HomeTopBrand;

/**
 * @Title: HomeTopBrandService 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  热门品牌维护页面接口
 * @author BYG
 * @date 2018年5月4日 
 * @version V2.0
 */
public interface HomeTopBrandService {

	
	
	/**
	 * @Title: getBrandsByPageNumber 
	 * @Description:  热门品牌维护页面数据获取
	 * @param     Integer pageNumber
	 * @return    List<HomeTopBrand>
	 * @author BYG
	 * @date 2018年5月4日 
	 */
	List<HomeTopBrand> getBrandsByPageNumber(Integer pageNumber) throws Exception;

	/**
	 * @Title: updateBatchBrands 
	 * @Description:  批量更新品牌
	 * @param    List<HomeTopBrand> topBrandList
	 * @return   void
	 * @author BYG
	 * @date 2018年5月4日 
	 */
	void updateBatchBrands(List<HomeTopBrand> topBrandList) throws Exception;
	
	
}
