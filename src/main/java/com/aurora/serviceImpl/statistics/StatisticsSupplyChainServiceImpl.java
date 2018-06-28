package com.aurora.serviceImpl.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.service.statistics.StatisticsSupplyChainService;
import com.aurora.util.PageData;

/**
 * 描述:数据统计--网站数据分析统计ServiceImpl
 * 创建:SSY 2017/11-18
 * 修改:
 * @version 1.0
 */
 
@Service("statisticsSupplyChainServiceImpl")
public class StatisticsSupplyChainServiceImpl implements StatisticsSupplyChainService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;

	/**
	 * 获取供应链数据;
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getSupplyChainData(Page page) throws Exception{
		 Map<String, Object> supplyChainData = new HashMap<String, Object>();
		
//		1.查询商品库存列表(真假库存)
		List<PageData> goodsStockList = (List<PageData>)daoSupport.findForList("StatisticsSupplyChainReadMapper.getGoodsStockList",page);
		supplyChainData.put("goodsStockList", goodsStockList);
		
//		2.平均交货天数;( )
		Double deliveryAverageDays = (Double)daoSupport.findForObject("StatisticsOrderReadMapper.getDeliveryAverageDays");
		supplyChainData.put("deliveryAverageDays", deliveryAverageDays);		
		
//		3.平均接单时间; 
		Double receiveOrderAverageTime = (Double)daoSupport.findForObject("StatisticsOrderReadMapper.getReceiveOrderAverageTime");
		supplyChainData.put("receiveOrderAverageTime", receiveOrderAverageTime);		
		
//		4.周退单率; 
//		Double weekChargeBackRate = (Double)daoSupport.findForList("StatisticsOrderReadMapper.getWeekChargeBackRate");
//		supplyChainData.put("weekChargeBackRate", weekChargeBackRate);
		
		return supplyChainData;
	}
	
	/**
	 * 条件查询商品总数
	 * @param page
	 * @return
	 */
	public int getGoodsNum(Page page) throws Exception{
		return (int)daoSupport.findForObject("StatisticsSupplyChainReadMapper.getGoodsNum", page);
	}
	
	
	
	
	
	
	
	
	
}
