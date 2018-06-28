package com.aurora.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.service.PushManageService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;

/**
 * 描述:客户登录注册ServiceImpl
 * 创建:SSY 2017/10/23
 * @version 1.0
 */

@Service("pushManageServiceImpl")
public class PushManageServiceImpl implements PushManageService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;
 
	/**
	 * 条件查询到货提醒 -- 商品推送列表
	 * @param page
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List<PageData> getPushArrival(Page page) throws Exception{
		return (List<PageData>) daoSupport.findForList("PushManageReadMapper.getPushArrival",page);
	}
	
	 /**
	 * 条件查询条件查询到货提醒 -- 商品总数
	 * @param page
	 * @return
	 * @throws Exception
	 */ 
	public int getNumPushArrival(Page page) throws Exception{
		return (int) daoSupport.findForObject("PushManageReadMapper.getNumPushArrival",page);
	}
	 
	 /**
	 * 批量更新到货提醒 状态为已推送-
	 * @param id
	 * @throws Exception
	 */ 
	public void updatePushArrival(String[] idArray) throws Exception{
		PageData pd = new PageData();
		pd.put("idArray", idArray);
		pd.put("time",DateUtil.getTime());
		pd.put("operator", Jurisdiction.getUserEmail());
		daoSupport.update("PushManageWriteMapper.updatePushArrival",pd);
	} 
	
}
