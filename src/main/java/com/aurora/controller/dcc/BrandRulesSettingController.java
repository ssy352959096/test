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

/**
 * @Title: BrandRulesSettingController.java
 * @Package com.aurora.controller.dcc.brand
 * @Description: 品名规则设置中商品设定
 * @author KJH
 * @date 2018年6月21日 下午2:02:45
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/brandRules")
public class BrandRulesSettingController extends BaseController {
	// 品名规则设置状态 1：可用 2：不可用
	/**
	 * @Title: getBrandList
	 * @Description:查询商品设定信息
	 * @param Page<BrandRulesSetting> page
	 * @return "/system/dcc/brandRulesSetting"
	 * @author KJH
	 * @date 2018年6月21日 下午2:32:00
	 */
	@RequestMapping
	public String getBrandRulesSettingList(ModelMap modelMap,Page<BrandRulesSetting> page,BrandRulesSetting brandRulesSetting) throws Exception {
		try {
			page.setT(brandRulesSetting);
			List<BrandRulesSetting> brandRulesSettings = brandRulesSettingService.getBrandRulesSettingList(page);	
			modelMap.put("brandRulesSettings", JSON.toJSON(brandRulesSettings));
		} catch (Exception e) {
			logger.info("【ERROR】 查询商品设定异常");
		}
		return "/system/dcc/brandRulesSetting";
	
	}
	/**
	 * @Title: deleteBrandRulesSetting
	 * @Description:根据ID删除商品设定信息
	 * @param Integer  id
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月21日 下午3:52:14
	 */
	@RequestMapping(value = "/deleteBrandRules")
	@ResponseBody
	public Result<Object> deleteBrandRulesSetting(Integer id) {
		Result<Object> result = new Result<Object>();
		try {
			result = brandRulesSettingService.deleteBrandRulesSetting(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】商品设定删除执行异常! ");
		}
		return result;
	}

	/**
	 * @Title: updateBrandRulesSetting
	 * @Description:新增或者修改商品设定信息
	 * @param BrandRulesSetting brandRulesSetting
	 * @return Result<Object> result
	 * @author KJH
	 * @date 2018年6月21日 下午3:52:20
	 */
	@RequestMapping(value = "/updateBrandRules")
	@ResponseBody
	public Result<Object> updateBrandRulesSetting(BrandRulesSetting brandRulesSetting) {
		Result<Object> result = new Result<Object>();
		try{
			result = brandRulesSettingService.updateBrandRulesSetting(brandRulesSetting);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】商品设定添加/修改执行异常! ");
		}
		return result;
	}
}
