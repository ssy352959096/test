package com.aurora.serviceImpl.shop.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.home.HotSell;
import com.aurora.service.shop.home.HomeHotSellService;
import com.aurora.util.DateUtil;

/**
 * @Title: HomeHotSellServiceImpl 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  商城首页本站热卖维护接口实现类
 * @author BYG
 * @date 2018年4月27日 
 * @version V2.0
 */
@Service
public class HomeHotSellServiceImpl implements HomeHotSellService{
	
	@Autowired
	private DAO daoSupport;

	
	/**
	 * @Title: getHotSellListByTitleID 
	 * @Description:  获取商城首页本站热卖商品列表
	 * @param     int titleID
	 * @return    List<HotSell>
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HotSell> getHotSellListByTitleID(int titleID) throws Exception {
		return (List<HotSell>) daoSupport.findForList("HomeHotSellReadMapper.getHotSellListByTitleID", titleID);
	}


	/**
	 * @Title: updateTitleName 
	 * @Description:  修改标题接口
	 * @param    int titleID,String titleName
	 * @return   Result<Object>
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	@Override
	public void updateTitleName(int titleID, String titleName) throws Exception {
		int count = (int) daoSupport.findForObject("HomeHotSellReadMapper.getIDByTitleID", titleID);
		HotSell hotSell = new HotSell();
		hotSell.setTitleID(titleID);
		hotSell.setTitleName(titleName);
		hotSell.setUpdateTime(DateUtil.getTime());
		if (count > 0) {
			daoSupport.update("HomeHotSellWriteMapper.updateTitle", hotSell);
		} else {
			hotSell.setSeat(1);
			daoSupport.save("HomeHotSellWriteMapper.addTitle", hotSell);
		}
	}


	/**
	 * @Title: updateGoods 
	 * @Description:  修改商品
	 * @param    HotSell hotsell
	 * @return   
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	@Override
	public void updateGoods(HotSell hotSell) throws Exception {
		int count = (int) daoSupport.findForObject("HomeHotSellReadMapper.getCountIDByTitleIDAndSeat", hotSell);
		hotSell.setUpdateTime(DateUtil.getTime());
		if (count > 0) {
			daoSupport.update("HomeHotSellWriteMapper.updateGoods", hotSell);
		} else {
			daoSupport.save("HomeHotSellWriteMapper.addGoods", hotSell);
		}
	}


	
}
