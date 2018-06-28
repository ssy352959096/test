package com.aurora.serviceImpl.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.service.statistics.StatisticsGoodsService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;

/**
 * 描述:数据统计ServiceImpl
 * 创建:SSY 2017/9/25
 * 修改:
 * @version 1.0
 */
 
@Service("statisticsGoodsServiceImpl")
public class StatisticsGoodsServiceImpl implements StatisticsGoodsService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;

	/**
	 * 数据统计---条件获取商品统计列表;
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getGoodsList(Page page) throws Exception{
		return (List<PageData>)daoSupport.findForList("StatisticsGoodsReadMapper.getGoodsList",page);
	}
	
	/**
	 * 数据统计---条件获取商品数量;
	 * @param page
	 * @return 
	 * @throws Exception
	 */
	@Override
	public int getGoodsNum(Page page) throws Exception{
		return (int)daoSupport.findForObject("StatisticsGoodsReadMapper.getGoodsNum",page);
	}
	
	/**
	 * 查询商品统计详细数据-----
	 * ------该商品今日,本月,近7日,近30天点击总量以及时间分布;
	 * @param goodsID
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGoodsStatisticsData(String goodsID) throws Exception{
		Map<String,Object> goodsStatisticsData = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd.put("goodsID", goodsID);
		
		//查询今日商品累计点击数以及点击分布图;
		pd.put("time", DateUtil.getDay());
		PageData todayClickDistribution = (PageData)daoSupport.findForObject("StatisticsGoodsReadMapper.getDayClickDistribution",pd);
		
		//查询最近七天商品累计点击数,每天累计点击变化趋势以及累计点击时间分布图;
		pd.put("time", DateUtil.getAfterDay(-6));
		List<PageData> sevenClickDistribution = (List<PageData>)daoSupport.findForList("StatisticsGoodsReadMapper.getXDayClickDistribution",pd);//查询该商品 近 X 日以来累计点击总量,以及时间分布
		List<String> sevenDayList = DateUtil.iteratorAfterDay(-6);
		List<PageData> sevenClickTrend = new ArrayList<PageData>();
		for (Iterator iterator = sevenDayList.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			pd.put("time", string);
			PageData dayClickNum = (PageData)daoSupport.findForObject("StatisticsGoodsReadMapper.getDayTotalClickNum",pd);//查询该商品 某日累计点击量
			sevenClickTrend.add(dayClickNum);
		}
		
		//查询最近30天商品累计点击数,每天累计点击变化趋势以及累计点击时间分布图;
		pd.put("time", DateUtil.getAfterDay(-29));
		List<PageData> thirtyClickDistribution = (List<PageData>)daoSupport.findForList("StatisticsGoodsReadMapper.getXDayClickDistribution",pd);//查询该商品 近 X 日累计点击总量,变化趋势以及时间分布
		List<String> thirtyDayList = DateUtil.iteratorAfterDay(-29);
		List<PageData> thirtyClickTrend = new ArrayList<PageData>();
		for (Iterator iterator = thirtyDayList.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			pd.put("time", string);
			PageData dayClickNum = (PageData)daoSupport.findForObject("StatisticsGoodsReadMapper.getDayTotalClickNum",pd);//查询该商品 某日累计点击量
			thirtyClickTrend.add(dayClickNum);
		}
		
		goodsStatisticsData.put("todayClickDistribution", todayClickDistribution);
		goodsStatisticsData.put("sevenClickDistribution", sevenClickDistribution);
		goodsStatisticsData.put("sevenClickTrend", sevenClickTrend);
		goodsStatisticsData.put("thirtyClickDistribution", thirtyClickDistribution);
		goodsStatisticsData.put("thirtyClickTrend", thirtyClickTrend);
		return goodsStatisticsData;
	}
	
	
}
