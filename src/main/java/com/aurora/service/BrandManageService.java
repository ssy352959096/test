package com.aurora.service;

import java.util.List;

import com.aurora.entity.Page;
import com.aurora.util.PageData;

public interface BrandManageService {

	/**获取品牌国信息
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getBrandCountry()throws Exception;
	
	/**获取所有品牌ID和名称
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getBrand()throws Exception;
	
	/**添加品牌
	 * @param pd
	 * @throws Exception
	 */
	public void addBrand(PageData pd)throws Exception;
	
	/**根据品牌ID获取品牌信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getBrandByID(int brandID)throws Exception;
	
	/**
	 * 返回查询到的所有品牌信息
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	public List<PageData> getBrandAllInfo(Page page) throws Exception;
	
	/**修改品牌
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	public void updateBrand(PageData pd) throws Exception;

	/**
	 *  查询到的所有品牌类目
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	public List<PageData> getBrandCategory() throws Exception;
	
	/**
	 *  查询到的所有品牌总数
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	public PageData  getBrandNum(Page page) throws Exception;
	
	
	
	
	
	
	
	
	
	
	
}
