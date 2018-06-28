package com.aurora.controller.shop.home;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aurora.controller.BaseController;
import com.aurora.entity.GoodsManage;
import com.aurora.entity.Result;
import com.aurora.entity.home.Category;
import com.aurora.entity.home.HomeFloorBrand;
import com.aurora.entity.home.HomeFloorGoods;
import com.aurora.entity.home.HomeSpecial;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.RedisConst;
import com.aurora.util.Tools;

/**
 * @Title: HomeFloorController.java 
 * @Package com.aurora.controller.shop.home 
 * @Description: 首页各类目Floor商品管理
 * @author SSY  
 * @date 2018年5月2日 下午4:32:06 
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/homeFloor")
public class HomeFloorController extends BaseController {
	
	/**
	 * @Title: getHomeSearchKeyword 
	 * @Description: 查询类目楼层商品
	 * @param   Integer category1ID
	 * @return homeFloor , homeSpecialList ,homeFloorBrand, homeKeyword.word, category1List,category1ID(回显)
	 * @author SSY
	 * @date 2018年5月2日 下午2:50:55
	 */
	@RequestMapping
	public String getHomeFloor(ModelMap map,Integer category1ID) throws Exception {
		try {
			category1ID = null==category1ID?Const.SPECIAL_CATEGORY1_MODULE:category1ID;
			List<HomeFloorGoods> homeFloor = homeFloorServiceImpl.getHomeFloor(category1ID);//查询该类目下的首页楼层商品;
			List<HomeFloorBrand> homeFloorBrand = homeFloorServiceImpl.getHomeFloorBrand(category1ID);//查询该类目商品Floor中热门品牌
			List<PageData> allBrandList = brandManageServiceImpl.getBrand();//查询所有品牌列表;
			List<HomeSpecial> homeSpecialList = homeSpecialServiceImpl.getHomeSpecialList(category1ID);//查询该楼层下的专题;
			PageData homeKeyword = homeKeywordServiceImpl.getHomeKeyword(category1ID);//查询该楼层下的搜索关键字
			List<Category> category1List = goodsCategoryServiceImpl.getHomeCategory(1);//查询首页展示所有一级类目前5个;
			map.put("homeFloor", JSON.toJSON(homeFloor));
			map.put("homeSpecialList", JSON.toJSON(homeSpecialList));
			map.put("homeKeyword", JSON.toJSON(homeKeyword));
			map.put("category1List", JSON.toJSON(category1List));
			map.put("homeFloorBrand", JSON.toJSON(homeFloorBrand));
			map.put("category1ID", category1ID);
			map.put("allBrandList", JSON.toJSON(allBrandList));
		} catch (Exception e) {
			e.printStackTrace(); 
			logger.info("【ERROR】"+DateUtil.getTime()+"首页类目商品查询执行异常! ");
		}
		return "system/homeManager/categoryGoods";
	}
	
	/**
	 * @Title: updateBondedSpecial 
	 * @Description: 修改或新增首页 类目商品专题;3个
	 * @param    Integer id(如果是null则是新增,否则就是修改),Integer category1ID, String url, String specialName,  String specialMap, String specialBackColour, String specialBackground
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月2日 上午10:31:41
	 */
	@RequestMapping(value = "/updateHomeFloorSpecial")
	@ResponseBody
	public Result<Object> updateHomeFloorSpecial(Integer id, Integer category1ID, String url,  String specialName, 
			String specialMap, String specialBackColour, String specialBackground) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			result = homeSpecialServiceImpl.updateHomeSpecial(category1ID, id, url, specialName, specialMap, specialBackColour, specialBackground);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"更新/新增首页楼层商品专题执行异常! ");
		}
		return result;
	}
	
	
	/**
	 * @Title: searchGoods 
	 * @Description: 搜索商品
	 * @param    
	 * @return Result<GoodsManage>  
	 * @author SSY
	 * @date 2018年4月26日 下午4:04:06
	 */
	@RequestMapping(value = "/searchGoods")
	@ResponseBody
	public  Result<GoodsManage> searchGoods(String goodsID){
		Result<GoodsManage> result = new Result<GoodsManage>();
		try {
			result = homeTimedActivityServiceImpl.searchGoods(goodsID);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"商品查询执行异常! ");
		}
		return result;
	}
	
	
	
	/**
	 * @Title: updateHomeFloor
	 * @Description: 新增/修改,  更新首页类目商品
	 * @param    Integer id(null新增), String goodsID, String goodsNewName, Integer category1ID,String homeMap
	 * @return Object  
	 * @author SSY
	 * @date 2018年5月2日 下午3:33:49
	 */
	@RequestMapping(value = "/updateHomeFloorGoods", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object updateHomeFloorGoods(Integer id, String goodsID, String goodsNewName, Integer category1ID,String homeMap) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			result = homeFloorServiceImpl.updateHomeFloorGoods(id, goodsID, goodsNewName, category1ID, homeMap);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"更新/新增首页各楼层商品执行异常! ");
		}
		return result;
	}

	/**
	 * @Title: updateHomeKeyword 
	 * @Description:  修改首页各类目商品关键词
	 * @param    String homeKeyword
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月2日 上午10:31:41
	 */
	@RequestMapping(value = "/updateHomeKeyword")
	@ResponseBody
	public Result<Object> updateHomeKeyword( Integer category1ID, String homeKeyword) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			result = homeKeywordServiceImpl.updateHomeKeyword(category1ID, homeKeyword);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"更新保税仓热卖关键词执行异常! ");
		}
		return result;
	}
	
	
	/**
	 * @Title: deleteHomeFloorGoods
	 * @Description: 删除首页 deleteHomeFloorGoods
	 * @param    Integer id
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月28日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteHomeFloorGoods")
	@ResponseBody
	public  Result<Object> deleteHomeFloorGoods(Integer id){
		Result<Object> result = new Result<Object>();
		try {
			result = homeFloorServiceImpl.deleteHomeFloorGoods(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除首页各类目商品执行异常! ");
		}
		return result;
	}
	 
	
	/**
	 * @Title: deleteHomeFloorSpecial
	 * @Description: 删除首页 HomeFloorSpecial
	 * @param    Integer id
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月28日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteHomeFloorSpecial")
	@ResponseBody
	public  Result<Object> deleteHomeFloorSpecial(Integer id){
		Result<Object> result = new Result<Object>();
		try {
			result = homeSpecialServiceImpl.deleteHomeSpecial(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除首页各类目专题执行异常! ");
		}
		return result;
	}
	
	
	/**
	 * @Title: updateHomeFloorBrand 
	 * @Description: 新增或者修改首页各类目楼层热门品牌设置,不支持批量,哼!
	 * @param   Integer floor,Integer location, Integer brandID, String brandName
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月4日 下午19:16:09
	 */
	@RequestMapping(value = "/updateHomeFloorBrand")
	@ResponseBody
	public Result<Object> updateHomeFloorBrand(Integer floor,Integer location, Integer brandID, String brandName) throws Exception{
		Result<Object> result = new Result<Object>();
		try {
			result = homeFloorServiceImpl.updateHomeFloorBrand(floor, location, brandID, brandName);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除首页各类目专题执行异常! ");
		}
		return result;
	}
	
	/**
	 * @Title: clearHomeFloorCache
	 * @Description: 清除首页banner缓存
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月8日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteRedisKey")
	@ResponseBody
	public  Result<Object> clearHomeFloorCache(){
		Result<Object> result = new Result<Object>();
		try {
			redisUtil.remove(RedisConst.HOME_FLOOR);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"清除首页品类商品缓存执行异常! ");
		}
		return result;
	}
	
	
	
	
	
	
}
