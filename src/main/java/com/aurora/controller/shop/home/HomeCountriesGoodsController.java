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
import com.aurora.entity.home.HomeCountriesGoods;
import com.aurora.redis.RedisUtil;
import com.aurora.service.shop.home.HomeCountriesGoodsService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.RedisConst;
import com.aurora.util.Tools;

/**
 * @Title: HomeCountriesGoodsController.java 
 * @Package com.aurora.controller.shop.home 
 * @Description: 商城首页各国好货
 * @author BYG 
 * @date 2018年5月2日 
 * @version 2.0
 */
@Controller
@RequestMapping(value="/countriesGoods")
public class HomeCountriesGoodsController extends BaseController{
	
	@Autowired HomeCountriesGoodsService HomeCountriesGoodsServiceImpl;
	@Autowired 	private RedisUtil RedisUtil;
	/**
	 * @Title: getCountriesGoods 
	 * @Description: 获取各国好货
	 * @param    String titleID
	 * @return  list goodsList;HomeCountriesGoods bannerAndTitle
	 * @author BYG
	 * @date 2018年5月2  日
	 */
	@RequestMapping
	public String getCountriesGoods(ModelMap map) throws Exception {
		PageData pd = this.getPageData();
		String titleID = Tools.notEmpty(pd.getString("titleID")) ? pd.getString("titleID").trim() : "1";
		try {
			HomeCountriesGoods bannerAndTitle = HomeCountriesGoodsServiceImpl.getBannerAndTitleByTitleID(titleID);
			if (bannerAndTitle == null) {
				bannerAndTitle = new HomeCountriesGoods();
				bannerAndTitle.setTitleID(Integer.valueOf(titleID));
			}
			List<HomeCountriesGoods> goodsList = HomeCountriesGoodsServiceImpl.getGoodsListByTitleID(titleID);
			map.put("bannerAndTitle", JSON.toJSON(bannerAndTitle));
			map.put("goodsList", JSON.toJSON(goodsList));
		} catch (Exception e) {
			logger.info("【ERROR】"+DateUtil.getTime()+"各国好货数据获取异常! ");
			e.printStackTrace();
		}
		return "/system/homeManager/homeCountriesGoods";
	}
	
	/**
	 * @Title: updateGoods
	 * @Description: 更新标题
	 * @param   HomeCountriesGoods HomeCountriesGoods(String titleID, String titleName)
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年5月2日
	 */
	@RequestMapping(value = "/updateTitle")
	@ResponseBody
	public Result<Object> updateTitle(HomeCountriesGoods homeCountriesGoods) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			HomeCountriesGoodsServiceImpl.updateTitle(homeCountriesGoods);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("修改或新增标题执行异常!");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"修改或新增标题执行异常!");
		}
		return result;
	}
	
	/**
	 * @Title: updateBanner
	 * @Description: 更新banner图
	 * @param   HomeCountriesGoods HomeCountriesGoods(Integer titleID,String banner)
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年5月2日
	 */
	@RequestMapping(value = "/updateBanner")
	@ResponseBody
	public Result<Object> updateBanner(HomeCountriesGoods homeCountriesGoods) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			HomeCountriesGoodsServiceImpl.updateBanner(homeCountriesGoods);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("修改或新增商品执行异常!");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"修改或新增商品执行异常!");
		}
		return result;
	}
	
	/**
	 * @Title: updateGoods
	 * @Description: 更新商品
	 * @param   HomeCountriesGoods HomeCountriesGoods(Integer titleID,String titleName;Integer seat;String goodsID;
	 * 											String goodsShowName;String goodsShowMap;)
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年5月2日
	 */
	@RequestMapping(value = "/updateGoods")
	@ResponseBody
	public Result<Object> updateGoods(HomeCountriesGoods homeCountriesGoods) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			HomeCountriesGoodsServiceImpl.updateGoods(homeCountriesGoods);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("修改或新增商品执行异常!");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"修改或新增商品执行异常!");
		}
		return result;
	}
	
	/**
	 * @Title: deleteGoods
	 * @Description: 删除商品
	 * @param   int id
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年5月2日
	 */
	@RequestMapping(value = "/deleteGoods")
	@ResponseBody
	public Result<Object> deleteGoods(int id) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			HomeCountriesGoodsServiceImpl.deleteGoods(id);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("删除商品执行异常!");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除商品执行异常!");
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
			RedisUtil.remove(RedisConst.HOME_COUNTRIES_GOODS);
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
