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
import com.aurora.entity.home.HomeDirectPost;
import com.aurora.redis.RedisUtil;
import com.aurora.service.shop.home.HomeDirectPostService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.RedisConst;
import com.aurora.util.Tools;

/**
 * @Title: HomeDirectPostController.java 
 * @Package com.aurora.controller.shop.home 
 * @Description: 商城首页海外直邮维护
 * @author BYG 
 * @date 2018年5月2日 
 * @version 2.0
 */
@Controller
@RequestMapping(value="/directPost")
public class HomeDirectPostController extends BaseController{
	
	@Autowired HomeDirectPostService HomeDirectPostServiceImpl;
	@Autowired 	private RedisUtil RedisUtil;
	/**
	 * @Title: getDirectPost 
	 * @Description: 获取海外直邮信息
	 * @param    String titleID
	 * @return  List<HomeDirectPost> goodsList ，
	 * @author BYG
	 * @date 2018年5月2  日
	 */
	@RequestMapping
	public String getDirectPost(ModelMap map) throws Exception {
		PageData pd = this.getPageData();
		String titleID = Tools.notEmpty(pd.getString("titleID")) ? pd.getString("titleID").trim() : "1";
		try {
			HomeDirectPost titleBannerKeywords = HomeDirectPostServiceImpl.getTitleBannerKeywordsByTitleID(titleID);
			if (titleBannerKeywords ==null) {
				titleBannerKeywords = new HomeDirectPost();
				titleBannerKeywords.setTitleID(Integer.valueOf(titleID));
			}
			List<HomeDirectPost> goodsList = HomeDirectPostServiceImpl.getGoodsListByTitleID(titleID);
			map.put("titleBannerKeywords", JSON.toJSON(titleBannerKeywords));
			map.put("goodsList", JSON.toJSON(goodsList));
			map.put("titleID", titleID);
		} catch (Exception e) {
			logger.info("【ERROR】"+DateUtil.getTime()+"海外直邮信息数据获取异常! ");
			e.printStackTrace();
		}
		return "/system/homeManager/homeDirectPost";
	}
	
	/**
	 * @Title: updateTitle
	 * @Description: 更新标题
	 * @param   HomeDirectPost homeDirectPost(String titleID, String titleName)
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年5月2日
	 */
	@RequestMapping(value = "/updateTitle")
	@ResponseBody
	public Result<Object> updateTitle(HomeDirectPost homeDirectPost) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			HomeDirectPostServiceImpl.updateTitle(homeDirectPost);
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("修改或新增商品执行异常!");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"海外直邮修改或新增商品执行异常!");
		}
		return result;
	}
	
	/**
	 * @Title: updateBannerOrKeywords
	 * @Description: 更新banner图或者关键词(二选一)
	 * @param   HomeDirectPost homeDirectPost(Integer titleID,String banner, String keywords)
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年5月2日
	 */
	@RequestMapping(value = "/updateBannerOrKeywords")
	@ResponseBody
	public Result<Object> updateBannerOrKeywords(HomeDirectPost homeDirectPost) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			HomeDirectPostServiceImpl.updateBannerOrKeywords(homeDirectPost);
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
	 * @param   HomeDirectPost homeDirectPost(Integer titleID,String titleName;Integer seat;String goodsID;
	 * 											String goodsShowName;String goodsShowMap;)
	 * @return  Result<Object>
	 * @author BYG
	 * @date 2018年5月2日
	 */
	@RequestMapping(value = "/updateGoods")
	@ResponseBody
	public Result<Object> updateGoods(HomeDirectPost homeDirectPost) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			HomeDirectPostServiceImpl.updateGoods(homeDirectPost);
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
			RedisUtil.remove(RedisConst.HOME_DIRECT_POST);
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
