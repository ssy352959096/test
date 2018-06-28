package com.aurora.service.dcc;

import java.util.List;

import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.dcc.BrandSmallClassSetting;

public interface BrandSmallClassSettingService {
	
   /**
    * @Title: getBrandSmallClassSettingList
    * @Description:查询商品小类信息
    * @param  List<BrandSmallClassSetting>
    * @return List<BrandSmallClassSetting>
    * @author KJH
    * @date 2018年6月21日 下午7:36:04
    */
	List<BrandSmallClassSetting> getBrandSmallClassSettingList(Page<BrandSmallClassSetting> page ) throws Exception;
	/**
	 * @Title: deleteBrandSmallClassSetting
	 * @Description:根据商品小类id删除信息
	 * @param  Integer smallClassId
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月21日 下午7:41:17
	 */
	Result<Object> deleteBrandSmallClassSetting(Integer smallClassId) throws Exception;
	/**
	 * @Title: updateBrandSmallClassSetting
	 * @Description: 增加或者修改商品小类信息
	 * @param  
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月21日 下午7:43:35
	 */
	Result<Object> updateBrandSmallClassSetting(BrandSmallClassSetting brandSmallClassSetting) throws Exception;
}
