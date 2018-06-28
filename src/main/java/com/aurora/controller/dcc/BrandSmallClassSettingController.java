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

/**
 * @Title: BrandSmallClassSettingController.java
 * @Package com.aurora.controller.dcc.brand
 * @Description: 商品小类设定
 * @author KJH
 * @date 2018年6月21日 下午6:29:50
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/brandSmallClass")
public class BrandSmallClassSettingController extends BaseController{
	/**
	 * @Title: getBrandSmallClassSettingList
	 * @Description:查询商品小类信息
	 * @param  
	 * @return String
	 * @author KJH
	 * @date 2018年6月21日 下午8:01:14
	 */
	@RequestMapping
	public String getBrandSmallClassSettingList(ModelMap map,Page<BrandSmallClassSetting> page,BrandSmallClassSetting brandSmallClassSetting) throws Exception {
		try {
			page.setT(brandSmallClassSetting);
			List<BrandSmallClassSetting> brandSmallClassSettings = brandSmallClassSettingService
					.getBrandSmallClassSettingList(page);
			map.put("brandSmallClassSettings", JSON.toJSON(brandSmallClassSettings));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【ERROR】 查询商品小类异常");
		}
		return "system/dcc/brandSmallClassSetting";
	}   
	/**
	 * @Title: searBrandName
	 * @Description:查询商品名称
	 * @param  
	 * @return Result<BrandSmallClassSetting>
	 * @author KJH
	 * @date 2018年6月22日 上午10:21:57
	 */
	@RequestMapping(value="/searBrandName")
	@ResponseBody
	public Object searBrandName(ModelMap map,Page<BrandRulesSetting> page,BrandRulesSetting brandRulesSetting)throws Exception{
		Result<BrandRulesSetting> result=new Result<BrandRulesSetting>();
		try {
			page.setT(brandRulesSetting);
			List<BrandRulesSetting> brandRulesSettings=  brandRulesSettingService.getBrandRulesSettingList(page);
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
	 * 
	 * @Title: deleteBrandSmallClassSetting
	 * @Description:根据id删除商品小类信息
	 * @param  Integer smallClassId
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月22日 上午9:15:00
	 */
	@RequestMapping(value="/deleteBrandSmallClass")
	@ResponseBody
	public Result<Object> deleteBrandSmallClassSetting(Integer smallClassId)throws Exception{
		Result<Object> result=new Result<Object>();
		try {
			result=brandSmallClassSettingService.deleteBrandSmallClassSetting(smallClassId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】商品小类设定删除执行异常! ");
		}
		return result;
	}
	/**
	 * @Title: updateBrandSmallClassSetting
	 * @Description:增加或者修改商品小类信息
	 * @param  Integer smallClassId(smallClassId为空则为增加，不为空则修改),Integer brandId,Integer smallClassState,String commodityName,String smallClassNo
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月22日 上午10:22:03
	 */
	@RequestMapping(value="/updateBrandSmallClass")
	@ResponseBody
	public Result<Object> updateBrandSmallClassSetting(BrandSmallClassSetting brandSmallClassSetting) throws Exception{
		Result<Object> result=new Result<Object>();
		try {
			result=brandSmallClassSettingService.updateBrandSmallClassSetting(brandSmallClassSetting);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统异常! ");
			result.setState(Result.STATE_ERROR);
			logger.info("【ERROR】商品小类设定增加/修改执行异常! ");
		}
		return result;
	}
}
