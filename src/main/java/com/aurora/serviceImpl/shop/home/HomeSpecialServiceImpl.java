package com.aurora.serviceImpl.shop.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Result;
import com.aurora.entity.home.HomeSpecial;
import com.aurora.service.shop.home.HomeSpecialService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.Tools;

/**
 * @Title: HomeBannerServiceImpl.java 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author SSY  
 * @date 2018年4月28日 下午2:35:07 
 * @version V1.0
 */
@Service
public class HomeSpecialServiceImpl implements HomeSpecialService{
	
	@Autowired
	private DAO daoSupport;

 
	/**
	 * @Title: getHomeSpecialList 
	 * @Description: 查询首页专题列表
	 * @param    Integer module
	 * @return List<HomeBanner>  
	 * @author SSY
	 * @date 2018年4月28日 下午2:41:46
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeSpecial> getHomeSpecialList(Integer module) throws Exception{
		List<HomeSpecial> homeSpecialList = (List<HomeSpecial>)daoSupport.findForList("HomeSpecialReadMapper.getHomeSpecialList",module);
		return homeSpecialList;
	}
	
	/**
	 * @Title: updateHomeSpecial 
	 * @Description: 更新首页专题(保存或修改)
	 * @param    Integer module, Integer id(null新增), String url, String specialName, String bannerMap, String specialBackColour, String specialBackground
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月28日 下午3:07:00
	 */
	public Result<Object> updateHomeSpecial(Integer module, Integer id, String url,String specialName,  String specialMap, String specialBackColour, String specialBackground) throws Exception{
		Result<Object> result = new Result<Object>();
		if (Tools.isEmpty(specialMap)||Tools.isEmpty(url)||null==module) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_SUCCESS);
			return result;
		}
		HomeSpecial special = new HomeSpecial();
		special.setId(id);
		special.setModule(module);
		special.setSpecialMap(specialMap);
		special.setSpecialBackground(specialBackground);
		special.setSpecialBackColour(specialBackColour);
		special.setUrl(url.replace(" ", ""));
		special.setSpecialName(specialName);
		special.setUpdateTime(DateUtil.getTime());
		special.setUpdator(Jurisdiction.getUserEmail());
		if (id==null) {//新增
			daoSupport.save("HomeSpecialWriteMapper.addHomeSpecial",special);
		}else{//修改
			daoSupport.update("HomeSpecialWriteMapper.updateHomeSpecial",special);
		}
		result.setState(Result.STATE_SUCCESS);
		return result;
	}
	
	/**
	 * @Title: deleteHomeSpecial 
	 * @Description: 删除首页专题
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年4月28日 下午3:37:47
	 */
	public Result<Object> deleteHomeSpecial(Integer id) throws Exception{
		Result<Object> result = new Result<Object>();
		if (id ==null) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_SUCCESS);
			return result;
		}
		daoSupport.delete("HomeSpecialWriteMapper.deleteHomeSpecial",id);
		result.setState(Result.STATE_SUCCESS);
		return result;
	}
	

	
	
}
