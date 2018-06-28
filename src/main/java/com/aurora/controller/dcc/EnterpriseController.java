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
import com.aurora.entity.dcc.Contacts;
import com.aurora.entity.dcc.Enterprise;


/**
 * @Title: ContactsController.java 
 * @Package  com.aurora.controller.dcc.supplier
 * @Description:  企业信息(供应商)Controller层
 * @author KJH  
 * @date 2018年6月25日 下午3:27:20 
 * @version V1.0
 */
@Controller
@RequestMapping(value="/enterpriseInFo")
public class EnterpriseController extends BaseController{
	/**
	 * @Title: searAdvantageBrand
	 * @Description:查询品牌名称信息
	 * @param  
	 * @return Object
	 * @author KJH
	 * @date 2018年6月25日 下午5:20:42
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
	 * @param  
	 * @return Object
	 * @author KJH
	 * @date 2018年6月25日 下午5:20:46
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
	 * @Title: updateEnterpriseInFo
	 * @Description:增加/修改企业信息
	 * @param  Enterprise enterprise(企业信息对象),Contacts contacts(联系人信息对象)
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月25日 下午5:20:51
	 */
	@RequestMapping
	@ResponseBody
	public Result<Object> updateEnterpriseInFo(Enterprise enterprise,Contacts contacts) throws Exception{
		Result<Object> result=new Result<Object>();
		    try {
		    	 Enterprise enterprises=new Enterprise();
		    	 enterprises.setEnterpriseName(enterprise.getEnterpriseName());
		    	 result=enterpriseService.updateEnterpriseInFo(enterprise);//增加企业信息
		    	 List<Enterprise> enterpriseList=enterpriseService.searEnterpriseInFo(enterprises);//查询企业
		    	 if(enterpriseList.size()>0){
		    		 for (Enterprise e:enterpriseList) {
		    			 //企业id赋到联系人中
		    		 contacts.setEnterpriseId(e.getEnterpriseId());		    	
		    		 result=enterpriseService.updateContactsInFo(contacts);//增加联系人信息
		    		 }
		    	 }
			} catch (Exception e) {
				e.printStackTrace();
				result.setMsg("系统异常!");
				result.setState(Result.STATE_ERROR);
				logger.info("【ERROR】 添加/修改企业信息执行异常!");
			}
		return result;
		
	}
	
}
