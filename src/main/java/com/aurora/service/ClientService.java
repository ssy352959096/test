package com.aurora.service;

import java.util.List;

import com.aurora.entity.Page;
import com.aurora.util.PageData;

public interface ClientService {
	/**
	 * 条件查询商城客户列表;
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getClientListByCondition(Page page) throws Exception ;
	
	/**
	 * 条件查询商城客户总数;
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public int getClientNumByCondition(Page page) throws Exception;
	
	/**
	 * 查询用户状态;
	 * @param customerID
	 * @return
	 * @throws Exception
	 */
	public String getClientState(String customerID) throws Exception;
	
	
	/**
	 * 禁用/启用该用户;
	 * @param customerID
	 * @return
	 * @throws Exception
	 */
	public void updateClientState(String customerID) throws Exception;
	
	/**
	 * 备注该用户;
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void updateClientRemark(PageData pd) throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
}
