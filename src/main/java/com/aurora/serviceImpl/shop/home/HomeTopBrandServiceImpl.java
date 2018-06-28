package com.aurora.serviceImpl.shop.home;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.home.HomeTopBrand;
import com.aurora.service.shop.home.HomeTopBrandService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;

/**
 * @Title: HomeTopBrandServiceImpl 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  热门品牌维护页面接口实现类
 * @author BYG
 * @date 2018年5月4日 
 * @version V2.0
 */
@Service
public class HomeTopBrandServiceImpl implements HomeTopBrandService{
	
	@Autowired
	private DAO daoSupport;

	/**
	 * @Title: getBrandsByPageNumber 
	 * @Description:  热门品牌维护页面数据获取
	 * @param     Integer pageNumber
	 * @return    List<HomeTopBrand>
	 * @author BYG
	 * @date 2018年5月4日 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeTopBrand> getBrandsByPageNumber(Integer pageNumber) throws Exception {
		return (List<HomeTopBrand>) daoSupport.findForList("HomeTopBrandReadMapper.getBrandsByPageNumber", pageNumber);
	}

	/**
	 * @Title: updateBatchBrands 
	 * @Description:  批量更新品牌
	 * @param    List<HomeTopBrand> topBrandList
	 * @return   void
	 * @author BYG
	 * @date 2018年5月4日 
	 */
	@Override
	public void updateBatchBrands(List<HomeTopBrand> topBrandList) throws Exception {
		String updateTime = DateUtil.getTime();
		String operator = Jurisdiction.getUserEmail();
		//集合拆分为新增和修改
		List<HomeTopBrand> topBrandAddList = new ArrayList<>();
		List<HomeTopBrand> topBrandUpdateList = new ArrayList<>();
		for (HomeTopBrand b : topBrandList) {
			b.setUpdateTime(updateTime);
			b.setOperator(operator);
			if (b.getId() == null) {
				topBrandAddList.add(b);
			} else {
				topBrandUpdateList.add(b);
			}
		}
		
		if (topBrandAddList.size() > 0) {
			daoSupport.save("HomeTopBrandWriteMapper.addTopBrand", topBrandAddList);
		}
		if (topBrandUpdateList.size() > 0) {
			daoSupport.update("HomeTopBrandWriteMapper.updateTopBrand", topBrandUpdateList);
		}
	}

}
