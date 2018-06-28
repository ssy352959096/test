package com.aurora.serviceImpl.dcc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.dcc.BrandSmallClassSetting;
import com.aurora.service.dcc.BrandSmallClassSettingService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.Tools;

/**
 * @Title: BrandSmallClassSettingServiceImpl.java
 * @Package com.aurora.serviceImpl.dcc.brand
 * @Description:
 * @author KJH
 * @date 2018年6月21日 下午7:49:04
 * @version V1.0
 */
@Service
public class BrandSmallClassSettingServiceImpl implements BrandSmallClassSettingService {

	@Autowired
	private DAO dao;

	/**
	 * @Title: BrandSmallClassSettingList
	 * @Description:查询商品小类信息
	 * @param
	 * @return List<BrandSmallClassSetting>
	 * @author KJH
	 * @date 2018年6月21日 下午7:49:48
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BrandSmallClassSetting> getBrandSmallClassSettingList(Page<BrandSmallClassSetting> page ) throws Exception {
		BrandSmallClassSetting brandSmallClassSetting=page.getT();
		if (Tools.isEmpty(brandSmallClassSetting.getSmallClassName())) {
			page.setPageSize(12);
			int num=(int) dao.findForObject("BrandSmallClassSettingReadMapper.getBrandSmallClassSettingNum");
			page.setTotalPage(num);
		List<BrandSmallClassSetting> brandSmallClassSettings = (List<BrandSmallClassSetting>) dao
				.findForList("BrandSmallClassSettingReadMapper.getBrandSmallClassSettingList",page);
		return brandSmallClassSettings;
		}else{
			List<BrandSmallClassSetting> brandSmallClassSettings = (List<BrandSmallClassSetting>) dao
					.findForList("BrandSmallClassSettingReadMapper.searBrandSmallClassSettingList",brandSmallClassSetting);
			return brandSmallClassSettings;
		}
	}
	/**
	 * @Title: deleteBrandSmallClassSetting
	 * @Description:根据id删除商品小类信息
	 * @paramInteger smallClassId
	 * @return result
	 * @author KJH
	 * @date 2018年6月21日 下午7:49:48
	 */
	@Override
	public Result<Object> deleteBrandSmallClassSetting(Integer smallClassId) throws Exception {
		Result<Object> result = new Result<Object>();
		if (smallClassId == null) {
			result.setMsg("参数错误！");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		int num = (int) dao.delete("BrandSmallClassSettingReadMapper.deleteBrandSmallClassSetting", smallClassId);
		result.setMsg(num >= 1 ? "操作成功" : "操作失败");
		result.setState(num >= 1 ? Result.STATE_SUCCESS : Result.STATE_ERROR);
		return result;
	}

	/**
	 * @Title: updateBrandSmallClassSetting
	 * @Description:增加或者修改商品小类信息
	 * @paramInteger smallClassId, Integer brandId, Integer smallClassState,
			String commodityName, String smallClassNo
	 * @return result
	 * @author KJH
	 * @date 2018年6月21日 下午7:49:48
	 */
	@Override
	public Result<Object> updateBrandSmallClassSetting(BrandSmallClassSetting brandSmallClassSetting) throws Exception {
		Result<Object> result = new Result<Object>();
		if (Tools.isEmpty(brandSmallClassSetting.getSmallClassName())||Tools.isEmpty(brandSmallClassSetting.getCommodityName()) || Tools.isEmpty(brandSmallClassSetting.getSmallClassNo()) || null == brandSmallClassSetting.getBrandId()) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		brandSmallClassSetting.setUpdateTime(DateUtil.getTime());
		brandSmallClassSetting.setUpdateName(Jurisdiction.getUserEmail());
		int num;
		if (null == brandSmallClassSetting.getSmallClassId()) {                                
			num = (int) dao.save("BrandSmallClassSettingReadMapper.addBrandSmallClassSetting", brandSmallClassSetting);
		} else {
			num = (int) dao.update("BrandSmallClassSettingReadMapper.updateBrandSmallClassSetting",
					brandSmallClassSetting);
		}
		result.setMsg(num >= 1 ? "操作成功! " : "操作失败! ");
		result.setState(num >= 1 ? Result.STATE_SUCCESS : Result.STATE_ERROR);
		return result;
	}


}
