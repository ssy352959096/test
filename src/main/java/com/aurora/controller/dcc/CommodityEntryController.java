package com.aurora.controller.dcc;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.dcc.BrandRulesSetting;
import com.aurora.entity.dcc.BrandSmallClassSetting;
import com.aurora.entity.dcc.CommodityEntry;

/**
 * @Title: CommodityEntry.java 
 * @Package  com.aurora.controller.dcc.commodity
 * @Description:  商品信息
 * @author KJH  
 * @date 2018年6月26日 下午2:32:51 
 * @version V1.0
 */
@Controller
@RequestMapping(value="/commodityEntry")
public class CommodityEntryController extends BaseController{

	/**
	 * @Title: searAdvantageBrand
	 * @Description:查询品牌名称信息
	 * @param  Page<BrandRulesSetting> page,BrandRulesSetting brandRulesSetting
	 * @return Object
	 * @author KJH
	 * @date 2018年6月26日 下午3:30:50
	 */
	@RequestMapping(value="/searAdvantageBrand")
	@ResponseBody
	public Object searAdvantageBrand(ModelMap map,Page<BrandRulesSetting> page,BrandRulesSetting brandRulesSetting) throws Exception{
		Result<BrandRulesSetting> result=new Result<BrandRulesSetting>();
		try {
			page.setT(brandRulesSetting);
			List<BrandRulesSetting> brandRulesSettings =brandRulesSettingService.getBrandRulesSettingList(page);
			map.put("brandRulesSettings", JSON.toJSON(brandRulesSettings));
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】根据条件查询商品名称执行异常! ");
			map.put("result", JSON.toJSON(result));
		}
		
		return map;
	}
	/**
	 * @Title: searproductSubclass
	 * @Description:查询小类信息
	 * @param  Page<BrandSmallClassSetting> page,BrandSmallClassSetting brandSmallClassSetting
	 * @return Object
	 * @author KJH
	 * @date 2018年6月26日 下午3:30:50
	 */
	@RequestMapping(value="/searproductSubclass")
	@ResponseBody
	public Object searproductSubclass(ModelMap map,Page<BrandSmallClassSetting> page,BrandSmallClassSetting brandSmallClassSetting) throws Exception{
		Result<BrandRulesSetting> result=new Result<BrandRulesSetting>();
		try {
			page.setT(brandSmallClassSetting);
			List<BrandSmallClassSetting> brandSmallClassSettings =brandSmallClassSettingService.getBrandSmallClassSettingList(page);
			map.put("brandRulesSettings", JSON.toJSON(brandSmallClassSettings));
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】根据条件查询商品名称执行异常! ");
			map.put("result", JSON.toJSON(result));
		}
		return map;
	}
	
	/**
	 * @Title: updateCommodityEntry
	 * @Description:新增/修改商品信息
	 * @param  
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月26日 下午3:13:37
	 */
	@RequestMapping(value="/updateCommodityEntry")
	public Result<Object> updateCommodityEntry(CommodityEntry commodityEntry) throws Exception{
		Result<Object> result=new Result<Object>();
		try {
			result = commodityEntryService.updateCommodityEntryInFo(commodityEntry);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】商品设定添加/修改执行异常! ");
		}
		return result ;
	}
}
