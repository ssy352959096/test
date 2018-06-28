package com.aurora.service.statistics;

import java.util.List;
import java.util.Map;

import com.aurora.entity.Page;
import com.aurora.util.PageData;

public interface StatisticsGoodsService {

	/**
	 * 数据统计---条件获取商品统计列表;
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getGoodsList(Page page) throws Exception;

	
	/**
	 * 数据统计---条件获取商品数量;
	 * @param page
	 * @return 
	 * @throws Exception
	 */
	public int getGoodsNum(Page page) throws Exception;

	
	/**
	 * 查询商品统计详细数据-----
	 * ------该商品今日,本月,近7日,近30天点击总量以及时间分布;
	 * @param goodsID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getGoodsStatisticsData(String goodsID) throws Exception;
	
 
}
