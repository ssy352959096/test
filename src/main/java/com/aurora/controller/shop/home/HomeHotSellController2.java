package com.aurora.controller.shop.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aurora.controller.BaseController;
import com.aurora.entity.Result;
import com.aurora.entity.home.HotSell;
import com.aurora.redis.RedisUtil;
import com.aurora.service.GoodsManageService;
import com.aurora.service.shop.home.HomeHotSellService;
import com.aurora.solr.SolrUtil;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.RedisConst;
import com.aurora.util.Tools;

/**
 * @Title: HomeHotSellController.java 
 * @Package com.aurora.controller.shop.home 
 * @Description: 商城首页本站热卖维护
 * @author BYG 
 * @date 2018年4月27日 
 * @version 2.0
 */
@Controller
@RequestMapping(value="/homeHotSell2")
public class HomeHotSellController2 extends BaseController{

	@Autowired	private HomeHotSellService HomeHotSellServiceImpl;
	@Autowired	private GoodsManageService goodsManageServiceImpl;
	@Autowired 	private RedisUtil RedisUtil;
	
	@Autowired 	private SolrUtil solrUtil;
	/**
	 * @Title: getHotSellList 
	 * @Description: 跳转到本站热卖维护页面，或者根据标题刷新本站热卖维护页面
	 * @param    int titleID;
	 * @return  list hotSellList;
	 * @author BYG
	 * @date 2018年4月27日
	 */
	@RequestMapping
	public String getHotSellList(ModelMap map) throws Exception {
		PageData pd = this.getPageData();
		String titleID = Tools.notEmpty(pd.getString("titleID")) ? pd.getString("titleID").trim() : "1";
		try {
			List<HotSell> hotSellList = HomeHotSellServiceImpl.getHotSellListByTitleID(Integer.valueOf(titleID));
//			solrUtil.updateGlobalSolr();
			map.put("titleID", titleID);
			map.put("hotSellList", JSON.toJSON(hotSellList));
		} catch (Exception e) {
			logger.info("【ERROR】"+DateUtil.getTime()+"本站热卖维护页面数据获取异常! ");
			e.printStackTrace();
		}
		return "/system/homeManager/hotSell";
	}
	
	/**
	 * @Title: updateTitleName 
	 * @Description: 修改标题名称
	 * @param    int titleID；String titleName；
	 * @return  result;
	 * @author BYG
	 * @date 2018年4月27日
	 */
	@RequestMapping(value = "/updateTitleName")
	@ResponseBody
	public Result<Object> updateTitleName(int titleID,String titleName) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			HomeHotSellServiceImpl.updateTitleName(titleID,titleName);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("修改标题名称执行异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"修改标题名称执行异常! ");
		}
		return result;
	}
	
	/**
	 * @Title: getGoods 
	 * @Description: 获取商品名称
	 * @param    string goodsID;
	 * @return  result;
	 * @author BYG
	 * @date 2018年4月27日
	 */
	@RequestMapping(value = "/getGoods")
	@ResponseBody
	public Result<Object> getGoods(String goodsID) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			PageData goods = goodsManageServiceImpl.getGoodsInfoByID(goodsID);
			result.setResult(goods);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("修改商品执行异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"修改商品执行异常! ");
		}
		return result;
	}
	
	/**
	 * @Title: updateGoods 
	 * @Description: 修改商品
	 * @param    HotSell(int titleID；String titleName;int seat;string goodsID;String goodsShowName;String goodsShowMap;)；
	 * @return  result;
	 * @author BYG
	 * @date 2018年4月27日
	 */
	@RequestMapping(value = "/updateGoods")
	@ResponseBody
	public Result<Object> updateGoods(HotSell hotSell) throws Exception {
		Result<Object> result = new Result<Object>();
		hotSell.setOperator(Jurisdiction.getUserEmail());
		try {
			HomeHotSellServiceImpl.updateGoods(hotSell);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("修改商品执行异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"修改商品执行异常! ");
		}
		return result;
	}
	
	/**
	 * @Title: deleteRedisKey
	 * @Description: 类目修改后删除redis中相应缓存
	 * @param   
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年5月7日
	 */
	@RequestMapping(value = "/deleteRedisKey")
	@ResponseBody
	public Result<Object> deleteRedisKey() throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			RedisUtil.remove(RedisConst.HOME_HOT_SELL);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("删除redis_key执行异常!");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除redis_key执行异常!");
		}
		return result;
	}
	
}
