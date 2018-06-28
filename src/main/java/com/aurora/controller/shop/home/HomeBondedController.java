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
import com.aurora.entity.home.HomeBonded;
import com.aurora.entity.home.HomeSpecial;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.RedisConst;

/**
 * @Title: HomeBondedController.java 
 * @Package com.aurora.controller.home 
 * @Description: 首页保税仓管理
 * @author SSY 
 * @date 2018年5月02日 下午2:07:20 
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/homeBonded")
public class HomeBondedController extends BaseController {

	
//	@Autowired
//	private RedisUtil redisUtil;

	/**
	 * @Title:   
	 * @Description: 查询Bonded列表以及保税仓专题列表
	 * @param    
	 * @return    homeBondedList,bondedSpecialList,homeKeyword.word
	 * @author SSY
	 * @date 2018年4月28日 下午6:58:38
	 */
	@RequestMapping
	public String getHomeBondedList(ModelMap map) throws Exception {
		try {
			List<HomeBonded> homeBondedList = homeBondedServiceImpl.getHomeBondedList();
			List<HomeSpecial> bondedSpecialList = homeSpecialServiceImpl.getHomeSpecialList(Const.SPECIAL_BONDED_MODULE);//查询保税仓专题列表;
			PageData homeKeyword = homeKeywordServiceImpl.getHomeKeyword(Const.KEYWORD_BONDED);//查询首页保税仓热卖关键词;
			map.put("homeBondedList", JSON.toJSON(homeBondedList));
			map.put("bondedSpecialList", JSON.toJSON(bondedSpecialList));
			map.put("homeKeyword", JSON.toJSON(homeKeyword));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【ERROR】"+DateUtil.getTime()+"首页保税仓热卖查询执行异常! ");
		}
		return "system/homeManager/bonded";
	}

	/**
	 * @Title: updateHomeKeyword 
	 * @Description:  修改保税仓热卖关键词
	 * @param    String homeKeyword
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月2日 上午10:31:41
	 */
	@RequestMapping(value = "/updateHomeKeyword")
	@ResponseBody
	public Result<Object> updateHomeKeyword(String homeKeyword) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			result = homeKeywordServiceImpl.updateHomeKeyword(Const.KEYWORD_BONDED, homeKeyword);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"更新保税仓热卖关键词执行异常! ");
		}
		return result;
	}
	
	/**
	 * @Title: updateBondedSpecial 
	 * @Description: 修改或新增bonded专题;3个
	 * @param    Integer id(如果是null则是新增,否则就是修改), String url,  String specialName, String specialMap, String specialBackColour, String specialBackground
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月2日 上午10:31:41
	 */
	@RequestMapping(value = "/updateBondedSpecial")
	@ResponseBody
	public Result<Object> updateBondedSpecial(Integer id, String url, String specialName,  String specialMap, String specialBackColour, String specialBackground) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			result = homeSpecialServiceImpl.updateHomeSpecial(Const.SPECIAL_BONDED_MODULE, id, url, specialName, specialMap, specialBackColour, specialBackground);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"更新/新增保税仓专题列表执行异常! ");
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
	 * @Title: updateHomeBonded 
	 * @Description: 修改或新增bonded首页保税仓热卖;
	 * @param     Integer id(null新增,不为null修改), String goodsID, String goodsNewName, Integer location, String homeMap
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月2日 上午10:31:41
	 */
	@RequestMapping(value = "/updateHomeBonded")
	@ResponseBody
	public Result<Object> updateHomeBonded(Integer id, String goodsID, String goodsNewName, Integer location, String homeMap) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			result = homeBondedServiceImpl.updateHomeBonded(id, goodsID, goodsNewName,  location, homeMap);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"更新/新增保税仓商品执行异常! ");
		}
		return result;
	}
	 
	
	/**
	 * @Title: deleteHomeBonded
	 * @Description: 删除首页保税仓商品设置
	 * @param    Integer id
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月28日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteHomeBonded")
	@ResponseBody
	public  Result<Object> deleteHomeBonded(Integer id){
		Result<Object> result = new Result<Object>();
		try {
			result = homeBondedServiceImpl.deleteHomeBonded(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除首页保税仓商品设置执行异常! ");
		}
		return result;
	}
	
	/**
	 * @Title: deleteHomeBondedSpecial
	 * @Description: 删除首页 HomeBondedSpecial
	 * @param    Integer id
	 * @return Result<TimedActivity>  
	 * @author SSY
	 * @date 2018年4月28日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteHomeBondedSpecial")
	@ResponseBody
	public Result<Object> deleteHomeBondedSpecial(Integer id){
		Result<Object> result = new Result<Object>();
		try {
			result = homeSpecialServiceImpl.deleteHomeSpecial(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除首页保税仓专题执行异常! ");
		}
		return result;
	}
	
	/**
	 * @Title: clearHomeBondedCache
	 * @Description: 清除首页Bonded缓存
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月28日 下午8:19:47
	 */
	@RequestMapping(value = "/deleteRedisKey")
	@ResponseBody
	public  Result<Object> clearHomeBondedCache(){
		Result<Object> result = new Result<Object>();
		try {
			if (redisUtil.hasKey(RedisConst.HOME_BONDED)) {
				redisUtil.remove(RedisConst.HOME_BONDED);
			}
			result.setState(Result.STATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"清除首页Bonded缓存执行异常! ");
		}
		return result;
	}
	
	
}
