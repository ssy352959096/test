package com.aurora.controller.shop.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aurora.controller.BaseController;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.home.Category;
import com.aurora.entity.home.HomeLargeCargo;
import com.aurora.redis.RedisUtil;
import com.aurora.service.GoodsManageService;
import com.aurora.service.shop.home.GoodsCategoryService;
import com.aurora.service.shop.home.HomeLargeCargoService;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.RedisConst;
import com.aurora.util.Tools;

/**
 * @Title: HomeLargeCargoController.java 
 * @Package com.aurora.controller.shop.home 
 * @Description: 商城首页大货集散维护
 * @author BYG 
 * @date 2018年5月2日 
 * @version 2.0
 */
@Controller
@RequestMapping(value="/largeCargo")
public class HomeLargeCargoController extends BaseController{
	
	/**
	 * @Title: getLargeCargoList 
	 * @Description: 根据类目分页查询大货集散商品列表
	 * @param    
	 * @return page  , largeCargoList, categoryList1,homeLCOrder(首页展示排序)
	 * @author SSY
	 * @date 2018年6月15日 上午11:37:52
	 */
	@RequestMapping
	public String getLargeCargoList(ModelMap map,Integer categoryID, Page<HomeLargeCargo> page) throws Exception {
		if (null==categoryID) {
			categoryID=Const.SPECIAL_CATEGORY1_MODULE;
		}
//		page.setCurrentPage(1);
		try {
			List<Category> categoryList1 = GoodsCategoryServiceImpl.getCategoryListByLevel(1);
			map.put("categoryList1", JSON.toJSON(categoryList1));
			List<HomeLargeCargo> largeCargoList = homeLargeCargoServiceImpl.getLargeCargoByCategory(categoryID,page);
			map.put("largeCargoList", JSON.toJSON(largeCargoList));
			String homeLCOrder = homeLargeCargoServiceImpl.getHomeLCOrderByCategory(categoryID);
			map.put("homeLCOrder", homeLCOrder);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【ERROR】"+DateUtil.getTime()+"大货集散信息数据获取异常! ");
			throw new Exception();
		}
		map.put("page", JSON.toJSON(page));
		return "/system/homeManager/homeLargeCargo";
	}
 
	/**
	 * @Title: getGoodsByID
	 * @Description: 查询商品
	 * @param    String goodsID
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年5月2日
	 */
	@RequestMapping(value = "/getGoodsByID")
	@ResponseBody
	public Result<Object> getGoodsByID(String goodsID) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			PageData goods = GoodsManageServiceImpl.getGoodsInfoByID(goodsID);
			if (goods==null) {
				result.setMsg("没有此商品! ");
				result.setState(Result.STATE_ERROR);
			} else {
				result.setResult(goods);
				result.setState(Result.STATE_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常!");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"获取商品信息异常!");
		}
		return result;
	}
	
	/**
	 * @Title: updateGoods
	 * @Description: 更新商品
	 * @param    HomeLargeCargo largeCargo(Integer id(如果是null或者找不到就是新增,否则修改), Integer categoryID; String categoryName String goodsID;String goodsShowName;String goodsCode;
	 * 				String currency , String price;String norm;Integer minNum;String period;String deliveryAddress;Integer hot;);
	 * @return  Result<Object>
	 * @author SSY
	 * @date 2018年6月15日
	 */
	@RequestMapping(value = "/updateGoods")
	@ResponseBody
	public Result<Object> updateGoods(HomeLargeCargo largeCargo) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			homeLargeCargoServiceImpl.updateGoods(largeCargo);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常!");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"修改或新增商品执行异常!");
		}
		return result;
	}
	
	/**
	 * @Title: deleteLargeCargo
	 * @Description: 删除大货
	 * @param    Integer categoryID
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年4月27日
	 */
	@RequestMapping(value = "/deleteLargeCargo")
	@ResponseBody
	public Result<Object> deleteLargeCargo(Integer id) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			result = homeLargeCargoServiceImpl.deleteLargeCargo(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除大货执行异常!");
		}
		return result;
	}
	
	
	
	
	/**
	 * @Title: updateHomeLCOrder
	 * @Description: 更新大货集散首页展示排序
	 * @param    Integer categoryID, String homeLCOrder 
	 * @return  Result<Object>
	 * @author SSY
	 * @date 2018年6月15日
	 */
	@RequestMapping(value = "/updateHomeLCOrder")
	@ResponseBody
	public Result<Object> updateHomeLCOrder(Integer categoryID, String homeLCOrder) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			result = homeLargeCargoServiceImpl.updateHomeLCOrder(categoryID, homeLCOrder);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常!");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"更新大货集散首页展示排序!");
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
			RedisUtil.remove(RedisConst.HOME_LARGE_CARGO);
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
