package com.aurora.serviceImpl.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.aurora.dao.DAO;
import com.aurora.service.statistics.StatisticsCategoryService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;

/**
 * 描述:数据统计--网站数据分析统计ServiceImpl
 * 创建:SSY 2017/11-18
 * 修改:
 * @version 1.0
 */
 
@Service("statisticsCategoryServiceImpl")
public class StatisticsCategoryServiceImpl implements StatisticsCategoryService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;

	/**
	 * 查询数据统计 -- 类目分析数据
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getCategoryStatisticsData() throws Exception{
		Map<String, Object> categoryStatisticsData = new HashMap<String, Object>();
		PageData pd = new PageData();
		 
		String payTime = DateUtil.getAfterDay(-6);//
		pd.put("payTime", payTime);
		
		//1.查询订单中各一级类目订单量比例,按照时间筛选;
		List<PageData> categoryOrderChart = (List<PageData>)daoSupport.findForList("StatisticsCategoryReadMapper.getCategory1OrderChart",pd);
		categoryStatisticsData.put("categoryOrderChart", categoryOrderChart);
		//2.查询订单中各一级类目累计销售件数比例 ;按照时间筛选
		List<PageData> categorySaleChart = (List<PageData>)daoSupport.findForList("StatisticsCategoryReadMapper.getCategory1SaleChart",pd);
		categoryStatisticsData.put("categorySaleChart", categorySaleChart);
		//3.查询价格段销量比,按照时间,类目筛选
		List<PageData> priceSaleChart = (List<PageData>)daoSupport.findForList("StatisticsCategoryReadMapper.getPriceSaleChart",pd);
		categoryStatisticsData.put("priceSaleChart", priceSaleChart);
		//4.关键字统计
		String monthNum = "month"+DateUtil.getMonth()+"_num";
		pd.put("monthNum", monthNum);
		List<PageData> keyWordStatisticsData = (List<PageData>)daoSupport.findForList("StatisticsCategoryReadMapper.getKeyWordStatisticsData",pd);
		categoryStatisticsData.put("keyWordStatisticsData", keyWordStatisticsData);
		//5.类目基本统计--平均客单价,新增商品数,在线商品数 ---按照一级类目展示;
		List<PageData> category1ShelfGoodsNum = (List<PageData>)daoSupport.findForList("StatisticsGoodsReadMapper.getCategory1ShelfGoodsNum");//在线商品数
		String monthMM = DateUtil.getMonthMM();
		List<PageData> category1NewGoods = (List<PageData>)daoSupport.findForList("StatisticsGoodsReadMapper.getCategory1NewGoods",monthMM);//本月新增商品数
		List<PageData> category1Saleroom = (List<PageData>)daoSupport.findForList("StatisticsCategoryReadMapper.getCategory1Saleroom");//各类目累计销售额
		pd.put("payTime", "2017-08-01 12:12:12");
		List<PageData> categoryTotalOrderChart = (List<PageData>)daoSupport.findForList("StatisticsCategoryReadMapper.getCategory1OrderChart",pd);//各类目累计订单量;
		for (int i = 0; i < category1ShelfGoodsNum.size(); i++) {
			PageData shelfGoodsNum = category1ShelfGoodsNum.get(i);
			PageData newGoods = category1NewGoods.get(i);
			PageData saleroom = category1Saleroom.get(i);
			PageData orderNum = categoryTotalOrderChart.get(i);
			shelfGoodsNum.putAll(newGoods);
			shelfGoodsNum.putAll(saleroom);
			shelfGoodsNum.putAll(orderNum);
		}
		categoryStatisticsData.put("category1ShelfGoodsNum", category1ShelfGoodsNum);
		return categoryStatisticsData;
	}

	
	/**
	 * 查询数据统计 --  查询订单中各类目订单量比例;按照时间,二级类目筛选
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getCategoryOrderChart(PageData pd) throws Exception{
		if (pd.get("category1ID")!=null) {
			return (List<PageData>) daoSupport.findForList("StatisticsCategoryReadMapper.getCategory2OrderChart",pd);
		}else{
			return (List<PageData>) daoSupport.findForList("StatisticsCategoryReadMapper.getCategory1OrderChart",pd);
	 	}
	}
	
	 
	/**
	 * 查询数据统计 --  查询订单中各类目累计销售件数比例 ;按照时间,二级类目筛选
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getCategorySaleChart(PageData pd) throws Exception{
		if (pd.get("category1ID")!=null) {
			return (List<PageData>) daoSupport.findForList("StatisticsCategoryReadMapper.getCategory2SaleChart",pd);
		}else{
			return (List<PageData>) daoSupport.findForList("StatisticsCategoryReadMapper.getCategory1SaleChart",pd);
	 	}
	}
	 
	/**
	 * 查询数据统计 --  查询订单中各类目价格段销量比例 ;按照时间,二级类目筛选
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getPriceSaleChart(PageData pd) throws Exception{
		return (List<PageData>) daoSupport.findForList("StatisticsCategoryReadMapper.getPriceSaleChart",pd);
	}
	
	
	/**
	 * 时间月份查询关键字统计月份累计数据
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getKeyWordStatisticsData(Integer month) throws Exception{
		String monthNum = "month"+month+"_num";
		PageData pd = new PageData();
		pd.put("monthNum", monthNum);
		return (List<PageData>) daoSupport.findForList("StatisticsCategoryReadMapper.getKeyWordStatisticsData",pd);
	}
	
	
	
	
	
	
}
