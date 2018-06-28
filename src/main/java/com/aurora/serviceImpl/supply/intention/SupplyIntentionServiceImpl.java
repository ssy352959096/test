package com.aurora.serviceImpl.supply.intention;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.entity.supply.intention.SupplyGoodsIntention;
import com.aurora.entity.supply.intention.SupplyIntention;
import com.aurora.service.supply.intention.SupplyIntentionService;

/**
 * @Title: SupplyIntentionServiceImpl.java 
 * @Package com.aurora.serviceImpl.dcc 
 * @Description: 全站供货意向serviceImpl
 * @author SSY  
 * @date 2018年5月4日 上午10:57:13 
 * @version V1.0
 */
@Service
public class SupplyIntentionServiceImpl implements SupplyIntentionService {
	
	@Autowired
	private DAO daoSupport;	
	
	/**
	 * @Title: getSupplyIntentionList 
	 * @Description: 查询全站供货意向信息
	 * @param    
	 * @return List<SupplyIntention>  
	 * @author SSY
	 * @date 2018年5月4日 上午10:57:37
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SupplyIntention> getSupplyIntentionList(Page<SupplyIntention> page) throws Exception{
		List<SupplyIntention> supplyIntentionList = (List<SupplyIntention>) daoSupport.findForList("SupplyIntentionReadMapper.getSupplyIntentionList", page);
		return supplyIntentionList;
	}
	
	/**
	 * @Title: getSupplyIntentionNum 
	 * @Description: 查询全站供货意向信息
	 * @param    
	 * @return List<SupplyIntention>  
	 * @author SSY
	 * @date 2018年5月4日 上午10:57:37
	 */
	@Override
	public int getSupplyIntentionNum(Page<SupplyIntention> page) throws Exception{
		int supplyIntentionNum = (int) daoSupport.findForObject("SupplyIntentionReadMapper.getSupplyIntentionNum", page);
		return supplyIntentionNum;
	}
	
	
	/**
	 * @Title: deleteSupplyIntention 
	 * @Description: 删除全站供货意向
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月4日 下午4:04:54
	 */
	@Override
	public Result<Object> deleteSupplyIntention(Integer id) throws Exception{
		Result<Object> result = new Result<Object>();
		if (id==null) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		int num = (int) daoSupport.delete("SupplyIntentionWriteMapper.deleteSupplyIntention",id);
		result.setMsg(num>=1?"操作成功! ":"操作失败! ");
		result.setState(num>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		return result;
	}
	
	/**
	 * @Title: getSupplyGoodsIntentionList 
	 * @Description: 查询供货商品意向信息
	 * @param    
	 * @return List<SupplyIntention>  
	 * @author SSY
	 * @date 2018年5月4日 上午10:57:37
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SupplyGoodsIntention> getSupplyGoodsIntentionList(Page<SupplyGoodsIntention> page) throws Exception{
		List<SupplyGoodsIntention> supplyGoodsIntentionList = (List<SupplyGoodsIntention>) daoSupport.findForList("SupplyIntentionReadMapper.getSupplyGoodsIntentionList", page);
		return supplyGoodsIntentionList;
	}
	
	/**
	 * @Title: getSupplyGoodsIntentionNum 
	 * @Description: 查询供货商品意向信息数量
	 * @param    
	 * @return  int
	 * @author SSY
	 * @date 2018年5月4日 上午10:57:37
	 */
	@Override
	public int getSupplyGoodsIntentionNum(Page<SupplyGoodsIntention> page) throws Exception{
		int supplyGoodsIntentionNum = (int) daoSupport.findForObject("SupplyIntentionReadMapper.getSupplyGoodsIntentionNum", page);
		return supplyGoodsIntentionNum;
	}
	
	/**
	 * @Title: deleteSupplyGoodsIntention 
	 * @Description: 删除供货商品意向货意向
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月4日 下午4:04:54
	 */
	@Override
	public Result<Object> deleteSupplyGoodsIntention(Integer id) throws Exception{
		Result<Object> result = new Result<Object>();
		if (id==null) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		int num = (int) daoSupport.delete("SupplyIntentionWriteMapper.deleteSupplyGoodsIntention",id);
		result.setMsg(num>=1?"操作成功! ":"操作失败! ");
		result.setState(num>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		return result;
		
	}
	
	
}
