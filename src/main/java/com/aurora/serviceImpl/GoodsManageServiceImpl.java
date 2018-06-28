package com.aurora.serviceImpl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.GoodsManage;
import com.aurora.entity.Page;
import com.aurora.entity.home.GoodsSolr;
import com.aurora.service.GoodsManageService;
import com.aurora.util.CustomException;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 描述:客户登录注册ServiceImpl 创建:BYG 2017/5/25 修改:
 * 
 * @version 1.0
 */

@Service("goodsManageServiceImpl")
public class GoodsManageServiceImpl implements GoodsManageService {

	@Resource(name = "daoSupport")
	private DAO daoSupport;

	/**
	 * 获取商品一级类目信息
	 * 
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getGoodsCategory(String categoryID) throws Exception {
		return (List<PageData>) daoSupport.findForList("GoodsReadMapper.getGoodsCategory", categoryID);
	}

	/**
	 * 根据商品二级类目ID获取商品对应属性
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getGoodsColumns(PageData pd) throws Exception {
		return (List<PageData>) daoSupport.findForList("GoodsReadMapper.getGoodsColumns", pd);
	}

	/**
	 * 新增商品
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void addGoods(PageData pd) throws Exception {
		daoSupport.save("GoodsWriteMapper.addGoodsToGD", pd);
		daoSupport.save("GoodsWriteMapper.addGoodsToGM", pd);
		daoSupport.save("GoodsWriteMapper.addGoodsToGC", pd);
	}

	 
	/**
	 * 未上架商品条件查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getGoodsByS5(Page page) throws Exception {
		return (List<PageData>) daoSupport.findForList("GoodsReadMapper.getGoodsByS5", page);
	}

	/**
	 * 未上架商品条件查询结果商品总数
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getGoodsNum(Page page) throws Exception {
		return (PageData) daoSupport.findForObject("GoodsReadMapper.getGoodsNum", page);
	}

	/**
	 * 根据商品状态得到商品数量
	 * 
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getGoodsNumByState(String state) throws Exception {
		return (int) daoSupport.findForObject("GoodsReadMapper.getGoodsNumByState", state);
	}

	/**
	 * 修改单个商品状态
	 * goodsState = 6 下架,   goodsState= 2 上架
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public String uptadeOGState(PageData pd) throws Exception {
		pd.put("time", DateUtil.getTime());
//		if ("6".equals(pd.getString("goodsState"))) {// 下架---先判断该商品是否存在热搜表中;
//			@SuppressWarnings("unchecked")
//			List<PageData> findForList = (List<PageData>) daoSupport.findForList("HomeManageReadMapper.getHomeGoods", pd);
//			if (findForList != null && findForList.size() > 0) {
//				return "下架失败！该商品已被录入首页热搜中！";
//			}
//		}
		daoSupport.update("GoodsWriteMapper.uptadeOGState", pd);
		return "success";
	}

	/**
	 * 批量更新商品状态
	 * 
	 * @param pd ArrayGoodsID
	 * @return
	 * @throws Exception
	 */
	@Override
	public String uptadeBatchGState(PageData pd) throws Exception {
		pd.put("time", DateUtil.getTime());
//		if ("6".equals(pd.getString("goodsState"))) {// 批量下架---先判断该商品是否存在热搜表中;
//			 List<PageData> existGoods = batchCheckExistGoods(pd);
//			if (!existGoods.isEmpty()) {
//				return "有商品存在热搜管理中，无法下架";
//			} 
//		}
		 daoSupport.update("GoodsWriteMapper.uptadeBatchGState", pd);
		return "success";
	}

	/**
	 * 删除单个商品前check商品（排除表home_hb_sell、home_hot_sell、home_large_buy、home_less_buy、home_new_goods 、home_tj_sell）
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> checkExistGoods(PageData pd) throws Exception {
		return (List<PageData>) daoSupport.findForList("GoodsReadMapper.checkExistGoods", pd);
	}

	/**
	 * 删除单个商品
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deleteOGoods(PageData pd) throws Exception {
		daoSupport.delete("GoodsWriteMapper.deleteOGoods", pd);
	}

	/**
	 * 批量删除商品前check商品（排除表home_hb_sell、home_hot_sell、home_large_buy、home_less_buy、home_new_goods 、home_tj_sell）
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> batchCheckExistGoods(PageData pd) throws Exception {
		return (List<PageData>) daoSupport.findForList("GoodsReadMapper.batchCheckExistGoods", pd);
	}

	/**
	 * 批量删除商品
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deleteBatchGoods(PageData pd) throws Exception {
		daoSupport.delete("GoodsWriteMapper.deleteBatchGoods", pd);
	}

	/**
	 * 修改单个商品库存
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public void uptadeOGStock(PageData pd) throws Exception {
		daoSupport.update("GoodsWriteMapper.uptadeOGStock", pd);
	}

	/**
	 * 已上架商品条件查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getShelvesGByS5(Page page) throws Exception {
		return (List<PageData>) daoSupport.findForList("GoodsReadMapper.getShelvesGByS5", page);
	}

	/**
	 * 已上架商品条件查询结果商品总数
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getShelvesGNum(Page page) throws Exception {
		return (PageData) daoSupport.findForObject("GoodsReadMapper.getShelvesGNum", page);
	}

	/**
	 * 根据商品ID获取商品信息
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getGoodsByID(String goodsID) throws Exception {
		return (List<PageData>) daoSupport.findForList("GoodsReadMapper.getGoodsByID", goodsID);

	}

	/**
	 * 修改单个商品信息保存
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateGoodsByID(PageData pd) throws Exception {
		daoSupport.update("GoodsWriteMapper.updateGoodsByID", pd);
	}

	/**
	 * 审核单个商品信息保存
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateReviewGoodsByID(PageData pd) throws Exception {
		PageData goodsByID = getGoodsByID(pd.getString("goodsID")).get(0);
		String shipType = goodsByID.getString("ship_type");
		if ("1".equals(shipType)&&"4".equals(pd.getString("goodsState"))) {//保税仓,并且要求审核通过?
			// 验证是否有库存;
			List<PageData> gcById = this.getGCById(pd.getString("goodsID"));
			if (gcById != null && gcById.size() > 0) {
				int stocks = 0;
				for (Iterator iterator = gcById.iterator(); iterator.hasNext();) {
					PageData pageData = (PageData) iterator.next();
					int stock = Integer.valueOf(pageData.getString("stock"));
					stocks += stock;
				}
				if (stocks == 0 || stocks< Integer.valueOf(pd.getString("rnum1"))) {//没有库存或者库存不足,审核不通过
					throw new CustomException(); 
				}
			} else{
				throw new CustomException();// 没有库存,不能上架,审核不通过
			}
		}
		//海外直邮,国内现货,接单录入成本;
		daoSupport.update("GoodsWriteMapper.updateReviewGoodsByID", pd);
	}

	@Override
	public GoodsManage getGoodsDM(String goodsID) throws Exception {
		return (GoodsManage) daoSupport.findForObject("GoodsReadMapper.getGoodsDM", goodsID);
	}

	/**
	 * 根据二级类目查询该类目下的商品 属性;
	 * 
	 * @param category2ID
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getGoodsProperties(String category2ID) throws Exception {
		return (List<PageData>) daoSupport.findForList("GoodsReadMapper.getGoodsProperties", category2ID);
	}

	/**
	 * 根据二级类目id和商品属性字符串保存商品属性;
	 * 
	 * @param pd
	 */
	@Override
	public void saveGoodsProperties(PageData pd) throws Exception {

		daoSupport.update("GoodsWriteMapper.saveGoodsProperties", pd);

	}

	/**
	 * 商品属性条件查询数量;
	 * 
	 * @param page
	 */
	@Override
	public int getAllPropertiesNum(Page page) throws Exception {

		return (int) daoSupport.findForObject("GoodsReadMapper.getAllPropertiesNum", page);

	}

	/**
	 * 商品属性条件查询;
	 * 
	 * @param page
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getAllProperties(Page page) throws Exception {

		return (List<PageData>) daoSupport.findForList("GoodsReadMapper.getAllProperties", page);

	}

	/**
	 * 关联商品id
	 * 
	 * @param goodsID,relateID1,relateID2
	 */
	@Override
	public String updateRelateID(String goodsID, String relateID1, String relateID2) throws Exception {

		List<PageData> goods = getGoodsByID(goodsID);
		if (goods == null || goods.size() == 0 || goods.get(0).getString("goods_state") == null
				|| !goods.get(0).getString("goods_state").equals("4")) {
			return "操作失败!商品不存在或未上架!";
		}
		// 清除之前所有关联关系;
		cancelrelateGoodsID(goodsID);// 清除所有与此关联的商品id;
		if (Tools.notEmptys(relateID1)) {
			List<PageData> goods1 = getGoodsByID(relateID1.trim());
			if (goods1 == null || goods1.size() == 0 || goods1.get(0).getString("goods_state") == null
					|| !goods1.get(0).getString("goods_state").equals("4")) {
				return "操作失败!关联的商品" + relateID1 + "不存在或未上架!";
			}
			cancelrelateGoodsID(relateID1);// 如果该id存在,清除所有与此关联的商品id;
		}
		if (Tools.notEmptys(relateID2)) {
			List<PageData> goods2 = getGoodsByID(relateID2.trim());
			if (goods2 == null || goods2.size() == 0 || goods2.get(0).getString("goods_state") == null
					|| !goods2.get(0).getString("goods_state").equals("4")) {
				return "操作失败!关联的商品" + relateID2 + "不存在或未上架!";
			}
			cancelrelateGoodsID(relateID2);// 如果该id存在,清除所有与此关联的商品id;
		}

		// 建立新的关联关系;
		PageData pd = new PageData();
		pd.put("goodsID", goodsID);
		pd.put("relateID1", relateID1);
		pd.put("relateID2", relateID2);// 先前与goodsID关联的商品先取消与其关联;
		daoSupport.update("GoodsWriteMapper.saveGoodsRelateID", pd);
		if (Tools.notEmptys(relateID1)) {
			pd.put("goodsID", relateID1);
			pd.put("relateID1", goodsID);
			pd.put("relateID2", relateID2);
			daoSupport.update("GoodsWriteMapper.saveGoodsRelateID", pd);
		}
		if (Tools.notEmptys(relateID2)) {
			pd.put("goodsID", relateID2);
			pd.put("relateID1", goodsID);
			pd.put("relateID2", relateID1);
			daoSupport.update("GoodsWriteMapper.saveGoodsRelateID", pd);
		}
		return "success";
	}

	/**
	 * 取消关联商品id
	 * 
	 * @param goodsID,relateID1,relateID2
	 */
	public void cancelrelateGoodsID(String goodsID) throws Exception {
		daoSupport.update("GoodsWriteMapper.cancelrelateID1", goodsID);
		daoSupport.update("GoodsWriteMapper.cancelrelateID2", goodsID);
	}

	/**
	 * 条件查询阶段成本商品数量;
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getGoodsCostNum(Page page) throws Exception {
		return (int) daoSupport.findForObject("GoodsReadMapper.getGoodsCostNum", page);
	}

	/**
	 * 条件查询录入成本商品;
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getGoodsCostList(Page page) throws Exception {
		List<PageData> goodsList = (List<PageData>) daoSupport.findForList("GoodsReadMapper.getMCGoods", page);
		for (Iterator iterator = goodsList.iterator(); iterator.hasNext();) {
			PageData goodsCost = (PageData) iterator.next();
			List<PageData> goodsCostList = (List<PageData>) daoSupport.findForList("GoodsReadMapper.getGoodsCostList",
					goodsCost.getString("goods_id"));
			goodsCost.put("goodsCostList", goodsCostList);
		}
		return goodsList;
	}

	
	/**
	 * 查询所有商品总数;
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getAllGoodsNum() throws Exception {
		return (int) daoSupport.findForObject("GoodsReadMapper.getAllGoodsNum");
	}

	/**
	 * 查询录入成本的商品数量;
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getGoodsNumByCostIn() throws Exception {
		return (int) daoSupport.findForObject("GoodsReadMapper.getGoodsNumByCostIn");
	}

	/**
	 * 保存新增的商品阶段库存;
	 * 
	 * @param pd
	 */
	public void saveGoodsCost(PageData pd) throws Exception {
		daoSupport.save("GoodsWriteMapper.saveGoodsCost", pd);
		// 同步添加库存至goods_manage
		daoSupport.update("GoodsWriteMapper.addMGStock", pd);
	}

	/**
	 * 根据商品id查询商品的阶段成本以及当前最小起订量;
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getGCById(String goodsID) throws Exception {
		return (List<PageData>) daoSupport.findForList("GoodsReadMapper.getGCById", goodsID);
	}
	
	

	/**
	 * @Title:  getGoodsInfoByID
	 * @Description:  根据商品ID获取商品名称（商品表）
	 * @param     String goodsID
	 * @return    
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	@Override
	public PageData getGoodsInfoByID(String goodsID) throws Exception {
		return (PageData) daoSupport.findForObject("GoodsReadMapper.getGoodsInfoByID", goodsID);
	}
	
	/**
	 * 根据商品ID获取商品信息，仅用于solr中商品信息管理
	 *  @author BYG 2017-12-13
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsSolr> getGoodsForSolr(String[] goodsIDs) throws Exception {
		return (List<GoodsSolr>) daoSupport.findForList("GoodsReadMapper.getGoodsForSolr", goodsIDs);
	}
	
	/**
	 * 获取全部已上架商品信息，仅用于solr中商品信息管理
	 *  @author BYG 2018-3-22
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsSolr> getAllShelvesGoodsForSolr() throws Exception {
		return (List<GoodsSolr>) daoSupport.findForList("GoodsReadMapper.getAllShelvesGoodsForSolr");
	}
	
	/**
	 * 清空goods_solr表，仅用于solr中商品信息管理
	 * @author BYG 2018-3-22
	 */
	public void deleteGoodsSolrAll() throws Exception{
		daoSupport.delete("GoodsWriteMapper.deleteGoodsSolrAll");
	}

	/**商品上架后，同步到 goods_solr表中
	 * @param goodsSolr
	 * @throws Exception
	 * @author BYG 2017-12-13
	 */
	@Override
	public void addGoodsToGoodsSolr(List<GoodsSolr> goodsSolr) throws Exception {
		daoSupport.save("GoodsWriteMapper.addGoodsToGoodsSolr",goodsSolr);
	}

	/**
	 * 商品下架，同步删除 goods_solr表中商品
	 * 支持批量操作
	 * @param goodsIDs
	 * @return
	 * @throws Exception
	 * @author BYG 2017-12-13
	 */
	@Override
	public void deleteSolrGoods(String[] goodsIDs) throws Exception {
		daoSupport.delete("GoodsWriteMapper.deleteSolrGoods", goodsIDs);
	}

}
