package com.aurora.service.dcc;

import java.util.List;

import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.dcc.BrandRulesSetting;

public interface BrandRulesSettingService {

	
	/**
	 * @Title: getBrandRulesSettingList
	 * @Description:查询品牌设定列表
	 * @param  
	 * @return List<BrandRulesSetting>
	 * @author KJH
	 * @date 2018年6月21日 下午3:36:22
	 */
	 List<BrandRulesSetting> getBrandRulesSettingList(Page<BrandRulesSetting> page)throws Exception;	
	/**
	 * @Title: deleteBrandRulesSetting
	 * @Description:根据id删除品牌设定信息
	 * @param  
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月21日 下午3:39:01
	 */
	Result<Object> deleteBrandRulesSetting(Integer id) throws Exception;
	/**
	 * @Title: updateBrandRulesSetting
	 * @Description:新增或修改品牌设定信息
	 * @param  Integer id(null新增,不为null修改)
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月21日 下午3:40:35
	 */
	Result<Object> updateBrandRulesSetting(BrandRulesSetting brandRulesSetting)throws Exception;
	
}
