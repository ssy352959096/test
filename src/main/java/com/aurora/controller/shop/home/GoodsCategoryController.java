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
import com.aurora.entity.home.Category;
import com.aurora.redis.RedisUtil;
import com.aurora.service.shop.home.GoodsCategoryService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.RedisConst;
import com.aurora.util.Tools;

/**
 * @Title: GoodsCategoryController.java 
 * @Package com.aurora.controller.shop.home 
 * @Description: 商品类目维护
 * @author BYG 
 * @date 2018年4月27日 
 * @version 2.0
 */
@Controller
@RequestMapping(value="/goodsCategory")
public class GoodsCategoryController extends BaseController{
	
	@Autowired GoodsCategoryService GoodsCategoryServiceImpl;
	@Autowired 	private RedisUtil RedisUtil;
	/**
	 * @Title: getCategoryList 
	 * @Description: 获取类目信息
	 * @param    String categoryLevel;String categoryParentID1(1级ID);String categoryParentID2(2级ID);
	 * @return  list hotSellList;
	 * @author BYG
	 * @date 2018年4月27日
	 */
	@RequestMapping
	public String getCategoryList(ModelMap map) throws Exception {
		PageData pd = this.getPageData();
		String categoryLevel = Tools.notEmpty(pd.getString("categoryLevel")) ? pd.getString("categoryLevel").trim() : "1";
		String categoryParentID1 = Tools.notEmpty(pd.getString("categoryParentID1")) ? pd.getString("categoryParentID1").trim() : null;
		String categoryParentID2 = Tools.notEmpty(pd.getString("categoryParentID2")) ? pd.getString("categoryParentID2").trim() : null;
		Category category = new Category();
		category.setCategoryLevel(Integer.valueOf(categoryLevel));
		try {
			if ("2".equals(categoryLevel)) {
				List<Category> categoryList1 = GoodsCategoryServiceImpl.getCategoryListByLevel(1);
				if (categoryParentID1 == null) {
					categoryParentID1 = categoryList1.get(0).getCategoryID().toString();
				}
				map.put("categoryList1", JSON.toJSON(categoryList1));
			} else if("3".equals(categoryLevel)){
				List<Category> categoryList1 = GoodsCategoryServiceImpl.getCategoryListByLevel(1);
				List<Category> categoryList2 = GoodsCategoryServiceImpl.getCategoryListByLevel(2);
				if (categoryParentID1 == null) {
					categoryParentID1 = categoryList1.get(0).getCategoryID().toString();
				}
				if (categoryParentID2 == null) {
					categoryParentID2 = categoryList2.get(0).getCategoryID().toString();
				}
				map.put("categoryList1", JSON.toJSON(categoryList1));
				map.put("categoryList2", JSON.toJSON(categoryList2));
			}
			if (categoryParentID1 != null) {
				if (categoryParentID2 != null) {
					category.setCategoryParentID(Integer.valueOf(categoryParentID2));
				} else {
					category.setCategoryParentID(Integer.valueOf(categoryParentID1));
				}
			}
			List<Category> categoryList = GoodsCategoryServiceImpl.getCategoryListByLevelAndPID(category);
			map.put("categoryList", JSON.toJSON(categoryList));
			map.put("categoryLevel", categoryLevel);
			map.put("categoryParentID1", categoryParentID1);
			map.put("categoryParentID2", categoryParentID2);
		} catch (Exception e) {
			logger.info("【ERROR】"+DateUtil.getTime()+"首页导航栏类目维护页面数据获取异常! ");
			e.printStackTrace();
		}
		return "/system/homeManager/category";
	}
	
	/**
	 * @Title: updateCategory
	 * @Description: 修改或新增类目
	 * @param    Category category(int categoryID;String categoryName;int categoryLevel;int categoryParentID;int locationSort;Integer red;);
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年4月27日
	 */
	@RequestMapping(value = "/updateCategory")
	@ResponseBody
	public Result<Object> updateCategory(Category category) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			GoodsCategoryServiceImpl.updateCategory(category);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("修改或新增类目执行异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"修改或新增类目执行异常!");
		}
		return result;
	}
	
	/**
	 * @Title: updateCategorylocationSort
	 * @Description: 修改类目顺序
	 * @param    [{"categoryID":"1","locationSort":"11"},{"categoryID":"2","locationSort":"22"}]
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年4月27日
	 */
	@RequestMapping(value = "/updateCategorylocationSort")
	@ResponseBody
	public Result<Object> updateCategorylocationSort(String categoryAndlocationSort) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			List<Category> categorylocationSort = JSON.parseArray(categoryAndlocationSort, Category.class);
			GoodsCategoryServiceImpl.updateBatchCategorySeat(categorylocationSort);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("修改类目顺序执行异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"修改类目顺序执行异常!");
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
			RedisUtil.remove(RedisConst.HOME_CATEGORY);
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
