package com.aurora.service.shop.home;

import java.util.List;

import com.aurora.entity.home.HomeDirectPost;

/**
 * @Title: HomeDirectPostService 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  商城首页海外直邮维护接口
 * @author BYG
 * @date 2018年5月2日 
 * @version V2.0
 */
public interface HomeDirectPostService {
	
	/**
	 * @Title: getTitleBannerKeywordsByTitleID    
	 * @Description:  根据标题ID获取banner图和关键词
	 * @param     String titleID
	 * @return    PageData 
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	HomeDirectPost getTitleBannerKeywordsByTitleID(String titleID) throws Exception;
	
	/**
	 * @Title: getGoodsListByTitleID 
	 * @Description:  根据标题ID获取商品
	 * @param     String titleID
	 * @return    List<PageData> 
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	List<HomeDirectPost> getGoodsListByTitleID(String titleID) throws Exception;
	
	/**
	 * @Title: updateTitle 
	 * @Description:  更新标题
	 * @param     PageData pd(String titleID, String titleName)
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	void updateTitle(HomeDirectPost homeDirectPost) throws Exception;
	
	/**
	 * @Title: updateBannerOrKeywords 
	 * @Description: 更新banner图或者关键词(二选一)
	 * @param   HomeDirectPost homeDirectPost(String banner, String keywords)
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	void updateBannerOrKeywords(HomeDirectPost homeDirectPost) throws Exception;
	
	/**
	 * @Title: updateGoods 
	 * @Description: 更新商品
	 * @param   HomeDirectPost homeDirectPost(Integer titleID,String titleName;Integer seat;String goodsID;
	 * 											String goodsShowName;String goodsShowMap;)
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	void updateGoods(HomeDirectPost homeDirectPost) throws Exception;
	
	
}
