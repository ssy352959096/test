package com.aurora.service;

import java.util.List;

import com.aurora.entity.Page;
import com.aurora.util.PageData;

public interface PushManageService {

  
	/**
	 * 条件查询到货提醒 -- 商品推送列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getPushArrival(Page page) throws Exception;
	
	/**
	 * 条件查询条件查询到货提醒 -- 商品总数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public int getNumPushArrival(Page page) throws Exception;


	/**
	 * 批量更新到货提醒 状态为已推送-
	 * @param id
	 * @throws Exception
	 */
	public void updatePushArrival(String[] idArray) throws Exception;

	
	
}
