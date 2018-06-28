package com.aurora.serviceImpl.shop.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.home.HomeLargeCargo;
import com.aurora.service.shop.home.HomeLargeCargoService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * @Title: HomeHotSellServiceImpl 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  商城首页本站热卖维护接口实现类
 * @author BYG
 * @date 2018年4月27日 
 * @version V2.0
 */
@Service
public class HomeLargeCargoServiceImpl implements HomeLargeCargoService{
	
	@Autowired
	private DAO daoSupport;
	
	
	/**
	 * @Title: getLargeCargoByCategory 
	 * @Description: 分页查询大货集散
	 * @param    
	 * @return List<HomeLargeCargo>  
	 * @author SSY
	 * @date 2018年6月15日 上午10:54:50
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeLargeCargo> getLargeCargoByCategory(Integer categoryID, Page<HomeLargeCargo> page) throws Exception{
		page.setPageSize(20);
		HomeLargeCargo homeLargeCargoQuery = new HomeLargeCargo();
		homeLargeCargoQuery.setCategoryID(categoryID);
		page.setT(homeLargeCargoQuery);
		List<HomeLargeCargo> largeCargoList = (List<HomeLargeCargo>)daoSupport.findForList("HomeLargeCargoReadMapper.getLargeCargoByCategory", page);
		int totalRecord = (int)daoSupport.findForObject("HomeLargeCargoReadMapper.getLargeCargoNumByCategory", page);
		page.setTotalRecord(totalRecord);
		return largeCargoList;
	}
	
	/**
	 * @Title: getHomeLCOrderByCategory 
	 * @Description: 查询该类目首页大货商品排序
	 * @param    
	 * @return   
	 * @author SSY
	 * @date 2018年6月15日 上午10:54:50
	 */
	@Override
	public String getHomeLCOrderByCategory(Integer categoryID) throws Exception{
		String homeLCOrder = (String)daoSupport.findForObject("HomeLargeCargoReadMapper.getHomeLCOrderByCategory", categoryID);
		return homeLCOrder;
	}
	
	 
	/**
	 * @Title: getLargeCargoListByCategoryID 
	 * @Description:  添加或更新大货商品
	 * @param     HomeLargeCargo largeCargo(Integer id 如果是null或者找不到就是新增,否则修改)
	 * @return    void 
	 * @author SSY
	 * @date 2018年6月15日 
	 */
	@Override
	public void updateGoods(HomeLargeCargo largeCargo) throws Exception {
		Integer id = largeCargo.getId();
		largeCargo.setUpdateTime(DateUtil.getTime());
		largeCargo.setOperator(Jurisdiction.getUserEmail());
		if (id == null) {
			daoSupport.save("HomeLargeCargoWriteMapper.addGoods", largeCargo);
		} else {
			daoSupport.update("HomeLargeCargoWriteMapper.updateGoods", largeCargo);
		}		
	}

	/**
	 * @Title: deleteLargeCargo 
	 * @Description: 删除大货
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年6月25日 上午11:24:23
	 */
	public Result<Object> deleteLargeCargo(Integer id) throws Exception{
		Result<Object> result = new Result<Object>();
		if (id==null) {
			result.setMsg("参数错误! ");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		daoSupport.delete("HomeLargeCargoWriteMapper.deleteLargeCargo", id);
		result.setState(Result.STATE_SUCCESS);
		return result;
	}
	
	
	/**
	 * @Title: updateHomeLCOrder 
	 * @Description: 更新大货集散首页展示排序
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年6月15日 下午7:26:21
	 */
	public Result<Object> updateHomeLCOrder(Integer categoryID, String homeLCOrder) throws Exception{
		Result<Object> result = new Result<Object>();
		if (categoryID==null||Tools.isEmpty(homeLCOrder)) {
			result.setMsg("参数错误! ");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		PageData pd = new PageData();
		pd.put("categoryID", categoryID);
		pd.put("homeLCOrder", homeLCOrder);
		pd.put("updator", Jurisdiction.getUserEmail());
		pd.put("updateTime", DateUtil.getTime());
		daoSupport.update("HomeLargeCargoWriteMapper.updateHomeLCOrder", pd);
		result.setState(Result.STATE_SUCCESS);
		return result;
	}
	
}
