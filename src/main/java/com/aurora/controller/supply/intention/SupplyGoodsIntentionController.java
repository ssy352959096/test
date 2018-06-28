package com.aurora.controller.supply.intention;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aurora.controller.BaseController;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.supply.intention.SupplyGoodsIntention;
import com.aurora.util.DateUtil;

/**
 * @Title: SupplyIntentionController.java 
 * @Package com.aurora.controller.shop.home 
 * @Description: 供货商信息登记管理
 * @author SSY  
 * @date 2018年5月3日 下午7:56:58 
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/supplyGoodsIntention")
public class SupplyGoodsIntentionController extends BaseController {

	/**
	 * @Title: getSupplyGoodsIntentionList
	 * @Description: 查询单个商品供货意向信息
	 * @param    supplyGoodsIntentionList,page, intention.beginInputTime, intention.endInputTime,
	 * @return String  
	 * @author SSY
	 * @date 2018年5月4日 上午10:43:36
	 */
	@RequestMapping
	public String getSupplyGoodsIntentionList(ModelMap map,Page<SupplyGoodsIntention> page, String beginInputTime, String endInputTime ) throws Exception {
		try {
			page.setPageSize(20);
			SupplyGoodsIntention intention = new SupplyGoodsIntention();
			intention.setBeginInputTime(beginInputTime);
			intention.setEndInputTime(endInputTime);
			page.setT(intention);
			List<SupplyGoodsIntention> supplyGoodsIntentionList = supplyIntentionServiceImpl.getSupplyGoodsIntentionList(page);
			int num = supplyIntentionServiceImpl.getSupplyGoodsIntentionNum(page);
			page.setTotalRecord(num);
			map.put("supplyGoodsIntentionList", JSON.toJSON(supplyGoodsIntentionList));
			map.put("intention", JSON.toJSONString(intention, SerializerFeature.WriteMapNullValue));//转json时当实体类为null值的时候为""
			map.put("page", JSON.toJSON(page));
		} catch (Exception e) {
			e.printStackTrace(); 
			logger.info("【ERROR】"+DateUtil.getTime()+"单个商品供货意向查询执行异常! ");
		}
		return "system/homeManager/supplyGoodsIntention";
	}

	/**
	 * @Title: deleteSupplyGoodsIntention 
	 * @Description: 删除单个商品供货意向
	 * @param    
	 * @return Object  
	 * @author SSY
	 * @date 2018年5月4日 下午3:33:49
	 */
	@RequestMapping(value = "/deleteSupplyGoodsIntention", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteSupplyGoodsIntention(Integer id) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			result = supplyIntentionServiceImpl.deleteSupplyGoodsIntention(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除单个商品供货意向执行异常! ");
		}
		return result;
	}

}
