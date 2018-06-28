package com.aurora.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.service.CustomerPoolService;
import com.aurora.util.PageData;

@Service("customerPoolServiceImpl")
public class CustomerPoolServiceImpl implements CustomerPoolService {
	@Resource(name = "daoSupport")
	private DAO daoSupport;
	
	
	/**
	 * 获取客户池列表;
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getCustomerPool(Page page) throws Exception{
		List<PageData> customerPool = (List<PageData>)daoSupport.findForList("CustomerPoolReadMapper.getCustomerPool", page);
		return customerPool;
	}

	/**
	 * 查询商城客户池用户数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getCustomerPoolNum(PageData pd) throws Exception{
		int customerPool = (int)daoSupport.findForObject("CustomerPoolReadMapper.getCustomerPoolNum", pd);
		return customerPool;
	}
	
	/**
	 * 查询销售代表 包含虚拟公池;
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getSalesman(PageData pd) throws Exception{
		List<PageData> salesman = (List<PageData>)daoSupport.findForList("CustomerPoolReadMapper.getSalesman",pd);
		return salesman;
	}
	
	/**
	 * 更新客户销售代表
	 * @param pd
	 * @throws Exception
	 */
	public void updateCustomerSalesman(PageData pd) throws Exception{
		daoSupport.update("CustomerPoolWriteMapper.updateCustomerSalesman",pd);
	}
	
	
}
