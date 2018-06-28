package com.aurora.service.shop.home;

import java.util.List;

import com.aurora.entity.home.HomeCountriesGoods;
import com.aurora.util.PageData;

/**
 * @Title: HomeCountriesGoodsService 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  商城首页各国好货维护接口
 * @author BYG
 * @date 2018年5月2日 
 * @version V2.0
 */
public interface HomeCountriesGoodsService {
	
	/**
	 * @Title: getBannerByTitleID 
	 * @Description:  根据标题ID获取banner图
	 * @param     String titleID
	 * @return    String banner
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	HomeCountriesGoods getBannerAndTitleByTitleID(String titleID) throws Exception;
	
	/**
	 * @Title: getGoodsListByTitleID 
	 * @Description:  根据标题ID获取商品
	 * @param     String titleID
	 * @return    List<HomeCountriesGoods>
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	List<HomeCountriesGoods> getGoodsListByTitleID(String titleID) throws Exception;
	
	/**
	 * @Title: updateTitle 
	 * @Description:  更新标题
	 * @param     PageData pd(String titleID, String titleName)
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	void updateTitle(HomeCountriesGoods homeCountriesGoods) throws Exception;
	
	/**
	 * @Title: updateBanner
	 * @Description: 新增或更新banner图
	 * @param   HomeDirectPost homeDirectPost(String banner, String keywords)
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	void updateBanner(HomeCountriesGoods homeCountriesGoods) throws Exception;
	
	/**
	 * @Title: updateGoods 
	 * @Description: 更新或新增商品
	 * @param   HomeDirectPost homeDirectPost(Integer titleID,String titleName;Integer seat;String goodsID;
	 * 											String goodsShowName;String goodsShowMap;)
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	void updateGoods(HomeCountriesGoods homeCountriesGoods) throws Exception;
	
	/**
	 * @Title: deleteGoods
	 * @Description: 删除商品
	 * @param   int id
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	void deleteGoods(int id) throws Exception;
	
}
