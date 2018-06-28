package com.aurora.serviceImpl.dcc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.dcc.BrandRulesSetting;
import com.aurora.service.dcc.BrandRulesSettingService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.Tools;

@Service
public class BrandRulesSettingServiceImpl implements BrandRulesSettingService{

	@Autowired
	private DAO dao;
	
	/**
	 * @Title: getBrandRulesSettingList
	 * @Description:查询品牌信息
	 * @param  
	 * @return brandRulesSettings
	 * @author KJH
	 * @date 2018年6月21日 下午2:35:08
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BrandRulesSetting> getBrandRulesSettingList(Page<BrandRulesSetting> page) throws Exception {
		BrandRulesSetting brandRulesSetting=page.getT();
		if (Tools.isEmpty(brandRulesSetting.getBrandCname())) {
			page.setPageSize(12);
			int num=(int) dao.findForObject("BrandRulesSettingReadMapper,getBrandRulesSettingNum");
			page.setTotalPage(num);
		List<BrandRulesSetting> brandRulesSettings=(List<BrandRulesSetting>) dao.findForList("BrandRulesSettingReadMapper.getBrandRulesSettingList",page);
		return brandRulesSettings;
		}else{
			List<BrandRulesSetting> brandRulesSettings=(List<BrandRulesSetting>) dao.findForList("BrandRulesSettingReadMapper.searBrandRulesSettingList",brandRulesSetting);
			return brandRulesSettings;
		}
	}

	/**
	 * @Title: deleteBrandRulesSetting
	 * @Description:根据ID删除商品设定信息
	 * @param  
	 * @return 
	 * @author KJH
	 * @date 2018年6月21日 下午4:01:00
	 */
	@Override
	public Result<Object> deleteBrandRulesSetting(Integer brandId) throws Exception {
		Result<Object> result = new Result<Object>();
		if(brandId==null){
			result.setMsg("参数错误！");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		int num=(int) dao.delete("BrandRulesSettingWriterMapper.deleteBrandRulesSetting", brandId);
		result.setMsg(num>=1?"操作成功":"操作失败");
		result.setState(num>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		return result;
	}

	/**
	 * @Title: updateBrandRulesSetting
	 * @Description:新增或者修改商品设定信息
	 * @param  
	 * @return 
	 * @author KJH
	 * @date 2018年6月21日 下午4:01:00
	 */
	@Override
	public Result<Object> updateBrandRulesSetting(BrandRulesSetting brandRulesSetting) throws Exception {
		Result<Object> result = new Result<Object>();
		if(Tools.isEmpty(brandRulesSetting.getBrandNo()) || Tools.isEmpty(brandRulesSetting.getBrandCname()) || Tools.isEmpty(brandRulesSetting.getBrandEname())){
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		brandRulesSetting.setUpdateTime(DateUtil.getTime());
		brandRulesSetting.setUpdateName(Jurisdiction.getUserEmail());
		int num=0;
		if(null==brandRulesSetting.getBrandId()){
			num=(int) dao.save("BrandRulesSettingWriterMapper.addBrandRulesSetting", brandRulesSetting);
		}else{
			num=(int) dao.update("BrandRulesSettingWriterMapper.updateBrandRulesSetting", brandRulesSetting);
		}
		result.setMsg(num>=1?"操作成功! ":"操作失败! ");
		result.setState(num>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		return result;
	}



}
