package com.aurora.service.shop.home;

import java.util.List;

import com.aurora.entity.Result;
import com.aurora.entity.home.HotSell;

/**
 * @Title: HomeHotSellService 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  商城首页本站热卖维护接口
 * @author BYG
 * @date 2018年4月27日 
 * @version V2.0
 */
public interface HomeHotSellService {

	
	
	/**
	 * @Title: getHotSellListByTitleID 
	 * @Description:  获取商城首页本站热卖商品列表
	 * @param     int titleID
	 * @return    List<HotSell>
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	List<HotSell> getHotSellListByTitleID(int titleID) throws Exception;

	/**
	 * @Title: updateTitleName 
	 * @Description:  修改标题接口
	 * @param    int titleID,String titleName
	 * @return   
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	void updateTitleName(int titleID,String titleName) throws Exception;
	
	/**
	 * @Title: updateGoods 
	 * @Description:  修改商品
	 * @param    HotSell hotsell
	 * @return   
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	void updateGoods(HotSell hotSell) throws Exception;
	
	
}
