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
import com.aurora.entity.supply.intention.SupplyIntention;
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
@RequestMapping(value = "/supplyIntention")
public class SupplyIntentionController extends BaseController {

	/**
	 * @Title: getSupplyIntentionList 
	 * @Description: 查询全站供货意向信息
	 * @param    supplyIntentionList,page,intention.beginInputTime, intention.endInputTime,
	 * @return String  
	 * @author SSY
	 * @date 2018年5月4日 上午10:43:36
	 */
	@RequestMapping
	public String getSupplyIntentionList(ModelMap map,Page<SupplyIntention> page, String beginInputTime, String endInputTime ) throws Exception {
		try {
			page.setPageSize(20);
			SupplyIntention intention = new SupplyIntention();
			intention.setBeginInputTime(beginInputTime);
			intention.setEndInputTime(endInputTime);
			page.setT(intention);
			List<SupplyIntention> supplyIntentionList = supplyIntentionServiceImpl.getSupplyIntentionList(page);
			int num = supplyIntentionServiceImpl.getSupplyIntentionNum(page);
			page.setTotalRecord(num);
			map.put("supplyIntentionList", JSON.toJSON(supplyIntentionList));
			map.put("intention", JSON.toJSONString(intention, SerializerFeature.WriteMapNullValue));
			map.put("page", JSON.toJSON(page));
		} catch (Exception e) {
			e.printStackTrace(); 
			logger.info("【ERROR】"+DateUtil.getTime()+"全站供货意向查询执行异常! ");
		}
		return "system/homeManager/supplyIntention";
	}

	/**
	 * @Title: deleteSupplyIntention 
	 * @Description: 删除全站供货意向
	 * @param    
	 * @return Object  
	 * @author SSY
	 * @date 2018年5月4日 下午3:33:49
	 */
	@RequestMapping(value = "/deleteSupplyIntention", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteSupplyIntention(Integer id) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			result = supplyIntentionServiceImpl.deleteSupplyIntention(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】"+DateUtil.getTime()+"删除全站供货意向执行异常! ");
		}
		return result;
	}

}
