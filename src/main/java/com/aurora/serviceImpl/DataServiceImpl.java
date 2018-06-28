package com.aurora.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.service.DataService;
import com.aurora.util.PageData;

/**
 * 描述:客户登录注册ServiceImpl
 * 创建:BYG 2017/5/25
 * 修改:
 * @version 1.0
 */
@Service("dataServiceImpl")
public class DataServiceImpl implements DataService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;

	/**根据商品ID获取商品详情页行业数据（淘宝售价  京东售价 本站售价 淘宝在售商家）
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getJPriceData(String goodsID) throws Exception {
		return (List<PageData>)daoSupport.findForList("DataReadMapper.getJPriceData", goodsID);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> getTPriceData(String goodsID) throws Exception {
		return (List<PageData>)daoSupport.findForList("DataReadMapper.getTPriceData", goodsID);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> getTStoreData(String goodsID) throws Exception {
		return (List<PageData>)daoSupport.findForList("DataReadMapper.getTStoreData", goodsID);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> getAPriceData(String goodsID) throws Exception {
		return (List<PageData>)daoSupport.findForList("DataReadMapper.getAPriceData", goodsID);
	}
	
}
