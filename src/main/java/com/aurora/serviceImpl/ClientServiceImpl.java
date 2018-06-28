package com.aurora.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.service.ClientService;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 描述:客户登录注册ServiceImpl 
 * @author SSY 2017/8/23
 * @version 1.0
 */

@Service("clientUserServiceImpl")
public class ClientServiceImpl implements ClientService {

	@Resource(name = "daoSupport")
	private DAO daoSupport;

	/**
	 * 条件查询商城客户列表;
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getClientListByCondition(Page page) throws Exception {
		return (List<PageData>) daoSupport.findForList("ClientReadMapper.getClientListByCondition", page);
	}
	
	
	/**
	 * 条件查询商城客户总数;
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getClientNumByCondition(Page page) throws Exception {
		return (int) daoSupport.findForObject("ClientReadMapper.getClientNumByCondition", page);
	}

	/**
	 * 查询用户状态;
	 * @param customerID
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getClientState(String customerID) throws Exception{

		return (String)daoSupport.findForObject("ClientReadMapper.getClientState", customerID);
	}
	

	/**
	 * 禁用/启用该用户;
	 * @param customerID
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateClientState(String customerID) throws Exception{
		String clientState = getClientState(customerID);
		if (Tools.notEmptys(clientState)&&clientState.equals("4")) {//禁用状态,进行启用操作;(用户状态1.新注册未登陆；2在线；3离线；4禁用)
			daoSupport.update("ClientWriteMapper.enabledClient", customerID);
		}else{//禁用操作;
			daoSupport.update("ClientWriteMapper.disableClient", customerID);
		}
	}
	
	
	/**
	 * 备注该用户;
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateClientRemark(PageData pd) throws Exception{
			daoSupport.update("ClientWriteMapper.updateClientRemark", pd);
		 
	}




































}
