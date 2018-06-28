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
import com.aurora.entity.home.HomeTopBrand;
import com.aurora.entity.home.HotSell;
import com.aurora.redis.RedisUtil;
import com.aurora.service.BrandManageService;
import com.aurora.service.shop.home.HomeTopBrandService;
import com.aurora.serviceImpl.shop.home.HomeHotSellServiceImpl;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.RedisConst;
import com.aurora.util.Tools;

/**
 * @Title: HomeHotBrandController2.java 
 * @Package com.aurora.controller.shop.home 
 * @Description: 商城首页热门品牌维护
 * @author BYG 
 * @date 2018年5月4日 
 * @version 2.0
 */
@Controller
@RequestMapping(value="/topBrand")
public class HomeTopBrandController extends BaseController{

	@Autowired	private HomeTopBrandService HomeTopBrandServiceImpl;
	@Autowired	private BrandManageService BrandManageServiceImpl;
	@Autowired 	private RedisUtil RedisUtil;
	
	/**
	 * @Title: getTopBrand 
	 * @Description: 热门品牌维护页面数据获取
	 * @param    String pageNumber;
	 * @return  pageNumber;brandList;topBrandList
	 * @author BYG
	 * @date 2018年5月4日 
	 */
	@RequestMapping
	public String getTopBrand(ModelMap map) throws Exception {
		PageData pd = this.getPageData();
		String pageNumber = Tools.notEmpty(pd.getString("pageNumber")) ? pd.getString("pageNumber").trim() : "1";
		try {
			List<HomeTopBrand> topBrandList = HomeTopBrandServiceImpl.getBrandsByPageNumber(Integer.valueOf(pageNumber));
			List<PageData> brandList = BrandManageServiceImpl.getBrand();
			map.put("pageNumber", pageNumber);
			map.put("brandList", JSON.toJSON(brandList));
			map.put("topBrandList", JSON.toJSON(topBrandList));
		} catch (Exception e) {
			logger.info("【ERROR】"+DateUtil.getTime()+"热门品牌维护页面数据获取异常! ");
			e.printStackTrace();
		}
		return "/system/homeManager/homeTopBrand";
	}
	
	
	/**
	 * @Title: updateBatchBrands 
	 * @Description: 批量更新品牌
	 * @param    [{"pageNumber":"1","id":"1","seat":"11","brandID":"1","brandName":"1","brandShowMap":"1"}]
	 * 				新增时id为空。
	 * @return  result;
	 * @author BYG
	 * @date 2018年4月27日
	 */
	@RequestMapping(value = "/updateGoods")
	@ResponseBody
	public Result<Object> updateBatchBrands(String topBrands) throws Exception {
		Result<Object> result = new Result<Object>();
		List<HomeTopBrand> topBrandList = JSON.parseArray(topBrands, HomeTopBrand.class);
		try {
			HomeTopBrandServiceImpl.updateBatchBrands(topBrandList);
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
			RedisUtil.remove(RedisConst.HOME_TOP_BRAND);
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
