package com.aurora.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.entity.User;
import com.aurora.service.HomeManageService;
import com.aurora.service.InquiryService;
import com.aurora.service.UserService;
import com.aurora.util.PageData;


/**
 * 描述:客户登录注册ServiceImpl
 * 创建:BYG 2017/5/25
 * 修改:
 * @version 1.0
 */

@Service("homeManageServiceImpl")
public class HomeManageServiceImpl implements HomeManageService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;

	/**获取一级类目
	 * @author BYG
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getCategory1() throws Exception {
		return (List<PageData>)daoSupport.findForList("HomeManageReadMapper.getCategory1");
	}
	
	/**获取一级类目和特殊奶粉类目
	 * @author BYG
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getCategory14() throws Exception {
		return (List<PageData>)daoSupport.findForList("HomeManageReadMapper.getCategory14");
	}

	/**根据 商品ID/一级类目ID 搜索商品
	 * @author BYG
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> serchGoods(PageData pd) throws Exception {
		return (List<PageData>)daoSupport.findForList("HomeManageReadMapper.serchGoods", pd);
	}

	/**根据 页码/一级类目ID 获取本站热卖商品
	 * @author BYG
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getHotSellList(PageData pd) throws Exception {
		return (List<PageData>)daoSupport.findForList("HomeManageReadMapper.getHotSellList",pd);
	}

	/**BYG 根据 页码/位置排序/一级类目ID 获取本站热卖商品ID
	 * @author BYG
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getHomeHotSellID(PageData pd) throws Exception {
		return (PageData)daoSupport.findForObject("HomeManageReadMapper.getHomeHotSellID",pd);
	}
	
	/**根据 页码/位置排序/一级类目ID 更新首页热卖商品
	 * @author BYG
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void updateHomeHotSell(PageData pd) throws Exception {
		daoSupport.update("HomeManageWriteMapper.updateHomeHotSell", pd);
	}

	/**添加商品到首页热卖
	 * @author BYG
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void addHomeHotSell(PageData pd) throws Exception {
		daoSupport.save("HomeManageWriteMapper.addHomeHotSell", pd);
	}

	/** 
	 * 获取所有热门品牌的信息
	 * @param page
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getHotBrand(int pageNum) throws Exception {
		 
		return (List<PageData>)daoSupport.findForList("HomeManageReadMapper.getHotBrand", pageNum);
		
	}
	
	/**
	 * 根据品牌Id查询
	 * @param brandId
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageData getBrand(String brandId) throws Exception{
		 
		return ( PageData)daoSupport.findForObject("HomeManageReadMapper.getBrand", brandId);
	}
	
	/**
	 * 保存热门品牌;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	@Override
	public void saveOrUpdateHotBrand(PageData pd) throws Exception{
		//如果该位置数据存在就修改,否则新增;
		int count = (int)daoSupport.findForObject("HomeManageReadMapper.getHotBrandByLoction", pd);
		if (count<=0) {//该位置为空,新增操作;
			daoSupport.save("HomeManageWriteMapper.addHotBrand", pd);
		}else{
			daoSupport.update("HomeManageWriteMapper.updateHotBrand", pd);
		}
	}
	
	/**
	 * 按页码获取新货推荐(新货id/页码/位置/商品id/商品名称/上传时间/图片/价格/条码)
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getNewGoods( int pageNum) throws Exception{
		return (List<PageData>)daoSupport.findForList("HomeManageReadMapper.getNewGoods", pageNum);
	}
	 
	/**
	 * 根据商品id---> 新货推荐预览---> 查询商品信息
	 * (商品id/商品名称/价格/图片/条码)
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageData getGoods(PageData pd) throws Exception{
		return (PageData)daoSupport.findForObject("HomeManageReadMapper.getGoods", pd);
	}
	
	/**
	 * 修改或者保存新货推荐;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	@Override
	public void saveOrUpdateNewGoods(PageData pd) throws Exception{
		daoSupport.save("HomeManageWriteMapper.saveOrUpdateNewGoods", pd);
	}
	
	/**
	 * 按类型获取海外直邮/保税区热卖设置
	 * @throws Exception 
	 * @author SSY
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getHbHotSell( PageData pd) throws Exception{
		return (List<PageData>)daoSupport.findForList("HomeManageReadMapper.getHbHotSell", pd);
	}
	
	
	/**
	 * 修改或者保存海外直邮/保税区热卖设置;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	@Override
	public void saveOrUpdateHbHotSell(PageData pd) throws Exception{
		daoSupport.save("HomeManageWriteMapper.saveOrUpdateHbHotSell", pd);
	}
	
	/**
	 * 根据品牌位置/类目ID,如果为空数据库类目表默认第一个类目/查询小额批发;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getHomeLessBuy(PageData pd) throws Exception{
		return (List<PageData>)daoSupport.findForList("HomeManageReadMapper.getHomeLessBuy", pd);
	}
	
	/**
	 * 根据品牌B(String brandId)/类目C(String category1ID)/商品GId (String GoodsId)查询商品的小额批发信息;
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageData getGoodsByBCGId(PageData pd) throws Exception{
		return ( PageData) daoSupport.findForObject("HomeManageReadMapper.getGoodsByBCGId", pd);
	}
	
	/**
	 * 修改或者保存小额批发设置;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	@Override
	public void saveOrUpdateLessBuy(PageData pd) throws Exception{
		daoSupport.save("HomeManageWriteMapper.saveOrUpdateLessBuy", pd);
	}
	
	/**
	 *  查询大额采购商品列表;
	 * @param page
	 * @throws Exception
	 * @author SSY
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getHomeLargeBuy(Page page) throws Exception{
		return (List<PageData>)daoSupport.findForList("HomeManageReadMapper.getHomeLargeBuy",page);
	}
	/**
	 * 查询大额采购总数;
	 * @param page
	 * @throws Exception
	 * @author SSY
	 */
	public int getNumHLB(Page page) throws Exception{
		return (int)daoSupport.findForObject("HomeManageReadMapper.getNumHLB",page);
	}
	/**
	 * 根据类目C(String category1ID)/商品GId (String GoodsId)查询商品的大额采购信息;
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getGoodsByCGId(PageData pd) throws Exception{
		return ( PageData) daoSupport.findForObject("HomeManageReadMapper.getGoodsByCGId", pd);
	}
	
	/**
	 *  保存大额采购宣传信息设置;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	@Override
	public void saveOrUpdateLargeBuy(PageData pd) throws Exception{
		daoSupport.save("HomeManageWriteMapper.saveOrUpdateLargeBuy", pd);
	}
	
	/**
	 * 根据(String tjType)查询淘宝&京东热卖信息;
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> gethomeTbHotSellList(String tjType) throws Exception{
		return (List<PageData>)daoSupport.findForList("HomeManageReadMapper.gethomeTbHotSell",tjType);
	}
	
	/**
	 * 修改或者保存大额采购设置;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	public void saveOrUpdateTbHotSell(PageData pd) throws Exception{
		daoSupport.save("HomeManageWriteMapper.saveOrUpdateTbHotSell", pd);
	}
	
	/**
	 * 查询热搜词 ;
	 * @throws Exception
	 * @author SSY
	 */
	@Override
	public PageData getHotKeyWord(PageData pd) throws Exception{
		return (PageData)daoSupport.findForObject("HomeManageReadMapper.getHotKeyWord",pd);
	}
	
	/**
	 * 更新热搜词设置;
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	@Override
	public void updateHotKeyWord(PageData pd) throws Exception{
		daoSupport.save("HomeManageWriteMapper.updateHotKeyWord", pd);
	}
	
	
}
