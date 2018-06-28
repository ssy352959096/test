package com.aurora.service;

import java.util.List;

import com.aurora.entity.Page;
import com.aurora.util.PageData;

public interface CustomerPoolService {

	/**
	 * 获取客户池列表;
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getCustomerPool(Page page) throws Exception;

	/**
	 * 查询商城客户池用户数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public int getCustomerPoolNum(PageData pd) throws Exception;

	/**
	 * 查询销售代表 包含虚拟公池;
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getSalesman(PageData pd) throws Exception;

	/**
	 * 更新客户销售代表
	 * @param pd
	 * @throws Exception
	 */
	public void updateCustomerSalesman(PageData pd) throws Exception;

}
