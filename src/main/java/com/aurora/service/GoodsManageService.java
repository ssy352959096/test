package com.aurora.service;

import java.util.List;

import com.aurora.entity.GoodsManage;
import com.aurora.entity.Page;
import com.aurora.entity.home.GoodsSolr;
import com.aurora.util.PageData;

public interface GoodsManageService {

	/**获取商品一级类目信息
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getGoodsCategory(String categoryID)throws Exception;
	
	/**根据商品二级类目ID获取商品对应属性
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getGoodsColumns(PageData pd)throws Exception;
	
	/**新增商品
	 * @param pd
	 * @throws Exception
	 */
	public void addGoods(PageData pd)throws Exception;
	
	/**未上架商品条件查询
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getGoodsByS5(Page page)throws Exception;
	
	/**未上架商品条件查询结果商品总数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData getGoodsNum(Page page)throws Exception;
	
	/**根据商品状态得到商品数量
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public int getGoodsNumByState(String state)throws Exception;
	
	/**修改单个商品状态
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public String uptadeOGState(PageData pd)throws Exception;
	
	/**批量更新商品状态
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public String uptadeBatchGState(PageData pd)throws Exception;
	
	/**删除单个商品前check商品（排除表home_hb_sell、home_hot_sell、home_large_buy、home_less_buy、home_new_goods、home_promo、home_tj_sell）
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> checkExistGoods(PageData pd)throws Exception;
	
	/**删除单个商品(表 goods_manage goods_common goods_details shop_cart)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void deleteOGoods(PageData pd)throws Exception;
		
	/**批量删除商品前check商品（排除表home_hb_sell、home_hot_sell、home_large_buy、home_less_buy、home_new_goods、home_promo、home_tj_sell）
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> batchCheckExistGoods(PageData pd)throws Exception;
	
	/**批量删除商品
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void deleteBatchGoods(PageData pd)throws Exception;
	
	/**已上架商品条件查询
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getShelvesGByS5(Page page)throws Exception;
	
	/**已上架商品条件查询结果商品总数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData getShelvesGNum(Page page)throws Exception;
	
	/**修改单个商品库存
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public void uptadeOGStock(PageData pd)throws Exception;
	
	/**根据商品ID获取商品信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getGoodsByID(String goodsID)throws Exception;
	
	/**修改单个商品信息保存
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public void updateGoodsByID(PageData pd)throws Exception;
	
	/**审核单个商品信息保存
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public void updateReviewGoodsByID(PageData pd)throws Exception;
	
	/**通过商品ID获取商品管理信息和详情（审核页）
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public GoodsManage getGoodsDM(String goodsID)throws Exception;
	
	/**
	 * 根据二级类目查询该类目下的商品 属性;
	 * @param category2ID
	 */
	public List<PageData> getGoodsProperties(String category2ID) throws Exception;
	
	/**
	 * 根据二级类目id和商品属性字符串保存商品属性;
	 * @param pd
	 */
	public void saveGoodsProperties( PageData pd) throws Exception;
	
	/**
	 * 商品属性条件查询数量;
	 * @param page
	 */
	public int getAllPropertiesNum(Page page) throws Exception;
	
	/**
	 * 商品属性条件查询;
	 * @param page
	 */
	public  List<PageData> getAllProperties(Page page) throws Exception;
	
	
	/**
	 * 关联商品id
	 * @param goodsID,relateID1,relateID2
	 */
	public String updateRelateID (String goodsID,String relateID1,String relateID2) throws Exception;

	/**
	 * 条件查询所有商品数量;
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public int getGoodsCostNum(Page page) throws Exception;

	/**
	 * 条件查询录入成本商品;
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getGoodsCostList(Page page) throws Exception;
 
	/**
	 *  查询所有商品总数;
	 * @return
	 * @throws Exception
	 */
	public int getAllGoodsNum() throws Exception;
	
	/**
	 * 查询录入成本的商品数量
	 * @return
	 * @throws Exception
	 */
	public int getGoodsNumByCostIn() throws Exception;
	
	/**
	 * 保存新增的商品阶段库存;
	 * @param pd
	 */
	public void saveGoodsCost(PageData pd) throws Exception;
	
	/**
	 * 根据商品id查询商品的阶段成本;
	 */
	public List<PageData> getGCById(String goodsID) throws Exception;
	
	/**
	 * @Title:  getGoodsInfoByID
	 * @Description:  根据商品ID获取商品名称（商品表）
	 * @param     String goodsID
	 * @return   
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	PageData getGoodsInfoByID(String goodsID) throws Exception;
	
	/**
	 * 根据商品ID获取商品信息，仅用于solr中商品信息管理
	 * @author BYG 2017-12-13
	 */
	public List<GoodsSolr> getGoodsForSolr(String[] goodsIDs) throws Exception;
	
	/**
	 * 获取全部已上架商品信息，仅用于solr中商品信息管理
	 * @author BYG 2018-3-22
	 */
	public List<GoodsSolr> getAllShelvesGoodsForSolr() throws Exception;
	
	/**
	 * 清空goods_solr表，仅用于solr中商品信息管理
	 * @author BYG 2018-3-22
	 */
	public void deleteGoodsSolrAll() throws Exception;
	
	/**商品上架后，同步到 goods_solr表中
	 * @param goodsSolr
	 * @throws Exception
	 */
	public void addGoodsToGoodsSolr(List<GoodsSolr> goodsSolr)throws Exception;
	
	/**
	 * 商品下架，同步删除 goods_solr表中商品
	 * 支持批量操作
	 * @param goodsIDs
	 * @return
	 * @throws Exception
	 */
	public void deleteSolrGoods(String[] goodsIDs)throws Exception;
	
	
}
