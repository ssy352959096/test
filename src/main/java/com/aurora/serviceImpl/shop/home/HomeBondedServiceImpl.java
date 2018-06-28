package com.aurora.serviceImpl.shop.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Result;
import com.aurora.entity.home.HomeBonded;
import com.aurora.service.shop.home.HomeBondedService;
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
public class HomeBondedServiceImpl implements HomeBondedService{
	
	@Autowired
	private DAO daoSupport;

 
	/**
	 * @Title: getHomeBondedList 
	 * @Description: 查询首页保税仓热卖产品列表
	 * @param    
	 * @return List<HomeBonded>  
	 * @author SSY
	 * @date 2018年4月28日 下午5:45:36
	 */
	@SuppressWarnings("unchecked")
	public List<HomeBonded> getHomeBondedList() throws Exception{
		List<HomeBonded> homeBondedList = (List<HomeBonded>)daoSupport.findForList("HomeBondedReadMapper.getHomeBondedList");
		return homeBondedList;
	}
	
	
	/**
	 * @Title: updateHomeBonded 
	 * @Description: 修改保税仓热卖
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月2日 上午9:32:56
	 */
	public Result<Object> updateHomeBonded(Integer id, String goodsID, String goodsNewName, Integer location, String homeMap) throws Exception{
		Result<Object> result = new Result<Object>();
		if (Tools.isEmpty(goodsID)||Tools.isEmpty(homeMap)||Tools.isEmpty(goodsNewName)||location==null) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		HomeBonded bonded = new HomeBonded();
		bonded.setGoodsID(goodsID.replace(" ", ""));
		bonded.setGoodsNewName(goodsNewName.replace(" ", ""));
		bonded.setLocation(location);
		bonded.setHomeMap(homeMap.replace(" ", ""));
		bonded.setId(id);
		bonded.setUpdateTime(DateUtil.getTime());
		bonded.setUpdator(Jurisdiction.getUserEmail());
		int num = 0;
		if (null==id) {//新增
			num = (int) daoSupport.save("HomeBondedWriteMapper.addHomeBonded",bonded);
		}else{//更新
			num = (int) daoSupport.update("HomeBondedWriteMapper.updateHomeBonded",bonded);
		}
		result.setMsg(num>=1?"操作成功! ":"操作失败! ");
		result.setState(num>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		return result;
	}

	/**
	 * @Title: deleteHomeBonded 
	 * @Description: 删除保税仓热卖
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月2日 上午9:32:22
	 */
	public Result<Object> deleteHomeBonded(Integer id) throws Exception{
		Result<Object> result = new Result<Object>();
		if (id==null) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		int num = (int) daoSupport.delete("HomeBondedWriteMapper.deleteHomeBonded",id);
		result.setMsg(num>=1?"操作成功! ":"操作失败! ");
		result.setState(num>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		return result;
	}
}
