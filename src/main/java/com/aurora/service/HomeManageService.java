package com.aurora.service;

import java.util.List;

import com.aurora.entity.Menu;
import com.aurora.entity.Page;
import com.aurora.util.PageData;

public interface HomeManageService {
	
	/**获取一级类目
	 * @author BYG
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getCategory1()throws Exception;
	
	/**获取一级类目和特殊奶粉类目
	 * @author BYG
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getCategory14()throws Exception;
	
	/**根据 商品ID/一级类目ID 搜索商品
	 * @author BYG
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> serchGoods(PageData pd)throws Exception;
	
	/**BYG 根据 页码/一级类目ID 获取本站热卖商品
	 * @author BYG
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getHotSellList(PageData pd)throws Exception;
	
	/**BYG 根据 页码/位置排序/一级类目ID 获取本站热卖商品ID
	 * @author BYG
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getHomeHotSellID(PageData pd)throws Exception;
	
	/**根据 页码/位置排序/一级类目ID 更新首页热卖商品
	 * @author BYG
	 * @param pd
	 * @throws Exception
	 */
	public void updateHomeHotSell(PageData pd)throws Exception;
	
	/**添加商品到首页热卖
	 * @author BYG
	 * @param pd
	 * @throws Exception
	 */
	public void addHomeHotSell(PageData pd)throws Exception;
	
	/** 
	 * 获取所有热门品牌的信息
	 * @param page
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	public List<PageData> getHotBrand(int pageNum) throws Exception;
	
	/**
	 * 根据品牌Id查询
	 * @param brandId
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	public PageData getBrand(String brandId) throws Exception;
	
	/**
	 * 保存热门品牌;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	public void saveOrUpdateHotBrand(PageData pd) throws Exception;
	
	/**
	 * 按页码获取新货推荐
	 * @throws Exception 
	 */
	public List<PageData> getNewGoods( int pageNum) throws Exception;
	
	/**
	 * 根据商品id---> 新货推荐预览---> 查询商品信息
	 * (商品id/商品名称/价格/图片/)
	 * @throws Exception 
	 */
	public PageData getGoods( PageData pd) throws Exception;
	
	/**
	 * 修改或者保存新货推荐;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	public void saveOrUpdateNewGoods(PageData pd) throws Exception;

	/**
	 * 按类型获取海外直邮/保税区热卖设置;
	 * @throws Exception 
	 */
	public List<PageData> getHbHotSell( PageData pd) throws Exception;
	
	/**
	 * 修改或者保存海外直邮/保税区热卖设置;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	public void saveOrUpdateHbHotSell(PageData pd) throws Exception;
	/**
	 * 根据品牌位置/类目ID,如果为空数据库类目表默认第一个类目/查询小额批发;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	public List<PageData> getHomeLessBuy(PageData pd) throws Exception;
	
	/**
	 * 根据品牌B(String brandId)/类目C(String category1ID)/商品GId (String GoodsId)查询商品的小额批发信息;
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getGoodsByBCGId(PageData pd) throws Exception;
	
	/**
	 * 修改或者保存小额批发设置;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	public void saveOrUpdateLessBuy(PageData pd) throws Exception;
	
	/**
	 * 查询大额采购商品列表;
	 * @param page
	 * @throws Exception
	 * @author SSY
	 */
	public List<PageData> getHomeLargeBuy(Page page) throws Exception;
	
	/**
	 * 查询大额采购总数;
	 * @param page
	 * @throws Exception
	 * @author SSY
	 */
	public int getNumHLB(Page page) throws Exception;
	
	
	/**
	 * 根据类目C(String category1ID)/商品GId (String GoodsId)查询商品的大额采购信息;
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getGoodsByCGId(PageData pd) throws Exception;
	
	/**
	 *  保存大额采购宣传信息设置;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	public void saveOrUpdateLargeBuy(PageData pd) throws Exception;
	
	/**
	 * 根据(String tjType)查询淘宝&京东热卖信息;
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> gethomeTbHotSellList(String tjType) throws Exception;
	
	/**
	 * 修改或者保存大额采购设置;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	public void saveOrUpdateTbHotSell(PageData pd) throws Exception;
	
	/**
	 * 查询热搜词 ;
	 * @throws Exception
	 * @author SSY
	 */
	public PageData getHotKeyWord(PageData pd) throws Exception;
	
	/**
	 * 更新热搜词设置;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	public void updateHotKeyWord(PageData pd) throws Exception;

	
}
