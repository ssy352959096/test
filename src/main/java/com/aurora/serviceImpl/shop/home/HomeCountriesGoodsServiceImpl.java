package com.aurora.serviceImpl.shop.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.home.HomeCountriesGoods;
import com.aurora.service.shop.home.HomeCountriesGoodsService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;

/**
 * @Title: HomeCountriesGoodsServiceImpl 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  商城首页海外直邮维护接口实现类
 * @author BYG
 * @date 2018年5月2日 
 * @version V2.0
 */
@Service
public class HomeCountriesGoodsServiceImpl implements HomeCountriesGoodsService{
	
	@Autowired
	private DAO daoSupport;

	/**
	 * @Title: getBannerByTitleID 
	 * @Description:  根据标题ID获取banner图
	 * @param     String titleID
	 * @return    String banner
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	@Override
	public HomeCountriesGoods getBannerAndTitleByTitleID(String titleID) throws Exception {
		return (HomeCountriesGoods) daoSupport.findForObject("HomeCountriesGoodsReadMapper.getBannerAndTitleByTitleID", titleID);
	}
	
	/**
	 * @Title: getGoodsListByTitleID 
	 * @Description:  根据标题ID获取商品
	 * @param     String titleID
	 * @return   List<HomeCountriesGoods> 
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeCountriesGoods> getGoodsListByTitleID(String titleID) throws Exception {
		return (List<HomeCountriesGoods>) daoSupport.findForList("HomeCountriesGoodsReadMapper.getGoodsListByTitleID", titleID);
	}

	/**
	 * @Title: updateTitle 
	 * @Description:  更新标题
	 * @param     PageData pd(String titleID, String titleName)
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	@Override
	public void updateTitle(HomeCountriesGoods HomeCountriesGoods) throws Exception {
		int count = (int) daoSupport.findForObject("HomeCountriesGoodsReadMapper.getCountIDByTitleID", HomeCountriesGoods);
		HomeCountriesGoods.setOperator(Jurisdiction.getUserEmail());
		HomeCountriesGoods.setUpdateTime(DateUtil.getTime());
		if (count > 0) {
			daoSupport.update("HomeCountriesGoodsWriteMapper.updateTitle", HomeCountriesGoods);
		} else {
			HomeCountriesGoods.setType(1);
			daoSupport.save("HomeCountriesGoodsWriteMapper.addTitle", HomeCountriesGoods);
		}
	}

	/**
	 * @Title: updateBanner 
	 * @Description: 更新banner图
	 * @param   HomeCountriesGoods HomeCountriesGoods(String banner)
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	@Override
	public void updateBanner(HomeCountriesGoods homeCountriesGoods) throws Exception {
		homeCountriesGoods.setType(1);
		homeCountriesGoods.setOperator(Jurisdiction.getUserEmail());
		homeCountriesGoods.setUpdateTime(DateUtil.getTime());
		daoSupport.update("HomeCountriesGoodsWriteMapper.updateBanner", homeCountriesGoods);
	}

	/**
	 * @Title: updateGoods 
	 * @Description: 更新商品
	 * @param   HomeCountriesGoods HomeCountriesGoods(Integer titleID,String titleName;Integer seat;String goodsID;
	 * 											String goodsShowName;String goodsShowMap;)
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	@Override
	public void updateGoods(HomeCountriesGoods homeCountriesGoods) throws Exception {
		homeCountriesGoods.setType(2);
		int count = (int) daoSupport.findForObject("HomeCountriesGoodsReadMapper.getCountIDByTitleIDAndSeat", homeCountriesGoods);
		homeCountriesGoods.setOperator(Jurisdiction.getUserEmail());
		homeCountriesGoods.setUpdateTime(DateUtil.getTime());
		if (count > 0) {
			daoSupport.update("HomeCountriesGoodsWriteMapper.updateGoods", homeCountriesGoods);
		} else {
			daoSupport.save("HomeCountriesGoodsWriteMapper.addGoods", homeCountriesGoods);
		}
	}

	/**
	 * @Title: deleteGoods
	 * @Description: 删除商品
	 * @param   int id
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	@Override
	public void deleteGoods(int id) throws Exception {
		daoSupport.delete("HomeCountriesGoodsWriteMapper.deleteGoods", id);
	}

	
	
}
