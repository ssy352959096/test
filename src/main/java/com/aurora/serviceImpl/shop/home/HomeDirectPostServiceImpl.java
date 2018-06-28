package com.aurora.serviceImpl.shop.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.home.HomeDirectPost;
import com.aurora.service.shop.home.HomeDirectPostService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;

/**
 * @Title: HomeDirectPostServiceImpl 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  商城首页海外直邮维护接口实现类
 * @author BYG
 * @date 2018年5月2日 
 * @version V2.0
 */
@Service
public class HomeDirectPostServiceImpl implements HomeDirectPostService{
	
	@Autowired
	private DAO daoSupport;

	/**
	 * @Title: getBannerAndKeywordsByTitleID 
	 * @Description:  根据标题ID获取banner图和关键词
	 * @param     String titleID
	 * @return    PageData 
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	@Override
	public HomeDirectPost getTitleBannerKeywordsByTitleID(String titleID) throws Exception {
		return (HomeDirectPost) daoSupport.findForObject("HomeDirectPostReadMapper.getTitleBannerKeywordsByTitleID", titleID);
	}

	/**
	 * @Title: getGoodsListByTitleID 
	 * @Description:  根据标题ID获取商品
	 * @param     String titleID
	 * @return    List<PageData> 
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeDirectPost> getGoodsListByTitleID(String titleID) throws Exception {
		return (List<HomeDirectPost>) daoSupport.findForList("HomeDirectPostReadMapper.getGoodsListByTitleID", titleID);
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
	public void updateTitle(HomeDirectPost homeDirectPost) throws Exception {
		int count = (int) daoSupport.findForObject("HomeDirectPostReadMapper.getCountIDByTitleID", homeDirectPost);
		homeDirectPost.setOperator(Jurisdiction.getUserEmail());
		homeDirectPost.setUpdateTime(DateUtil.getTime());
		if (count > 0) {
			daoSupport.update("HomeDirectPostWriteMapper.updateTitle", homeDirectPost);
		} else {
			homeDirectPost.setType(1);
			daoSupport.save("HomeDirectPostWriteMapper.addTitle", homeDirectPost);
		}
	}

	/**
	 * @Title: updateBannerOrKeywords 
	 * @Description: 更新banner图或者关键词(二选一)
	 * @param   HomeDirectPost homeDirectPost(String banner, String keywords)
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	@Override
	public void updateBannerOrKeywords(HomeDirectPost homeDirectPost) throws Exception {
		homeDirectPost.setType(1);
		homeDirectPost.setOperator(Jurisdiction.getUserEmail());
		homeDirectPost.setUpdateTime(DateUtil.getTime());
		daoSupport.update("HomeDirectPostWriteMapper.updateBannerOrKeywords", homeDirectPost);
	}

	/**
	 * @Title: updateGoods 
	 * @Description: 更新商品
	 * @param   HomeDirectPost homeDirectPost(Integer titleID,String titleName;Integer seat;String goodsID;
	 * 											String goodsShowName;String goodsShowMap;)
	 * @return    void
	 * @author BYG
	 * @date 2018年5月2日 
	 */
	@Override
	public void updateGoods(HomeDirectPost homeDirectPost) throws Exception {
		homeDirectPost.setType(2);
		int count = (int) daoSupport.findForObject("HomeDirectPostReadMapper.getCountIDByTitleIDAndSeat", homeDirectPost);
		homeDirectPost.setOperator(Jurisdiction.getUserEmail());
		homeDirectPost.setUpdateTime(DateUtil.getTime());
		if (count > 0) {
			daoSupport.update("HomeDirectPostWriteMapper.updateGoods", homeDirectPost);
		} else {
			daoSupport.save("HomeDirectPostWriteMapper.addGoods", homeDirectPost);
		}
	}

	
	
}
