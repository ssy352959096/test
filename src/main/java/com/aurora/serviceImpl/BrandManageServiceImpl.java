package com.aurora.serviceImpl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.service.BrandManageService;
import com.aurora.util.PageData;


/**
 * 描述:客户登录注册ServiceImpl
 * 创建:BYG 2017/5/25
 * 修改:
 * @version 1.0
 */

@Service("brandManageServiceImpl")
public class BrandManageServiceImpl implements BrandManageService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;
		
	/**获取品牌国信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getBrandCountry() throws Exception {
		return (List<PageData>)daoSupport.findForList("BrandReadMapper.getBrandCountry");
	}

	/**添加品牌
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void addBrand(PageData pd) throws Exception {
		daoSupport.save("BrandWriteMapper.addBrand", pd);
	}

	/**获取所有品牌ID和名称
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getBrand() throws Exception {
		return (List<PageData>)daoSupport.findForList("BrandReadMapper.getBrand");
	}
/**
 * 根据品牌id查询品牌id
 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getBrandByID(int brandID) throws Exception {
		return (List<PageData>)daoSupport.findForList("BrandReadMapper.getBrandByID" , brandID);
	}
	
	/**
	 * 根据品牌类目,当前页码
	 * 返回查询到的所有品牌信息
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getBrandAllInfo(Page page) throws Exception {
		List<PageData> brandList = (List<PageData>)daoSupport.findForList("BrandReadMapper.getBrandWithAll",page);
		for (Iterator iterator = brandList.iterator(); iterator.hasNext();) {
			PageData pageData = (PageData) iterator.next();
			pageData.put("goods_num", getGoodsNumByBID(pageData.getString("brand_id")));
		}
		return brandList;
	}
	
	/**
	 * 根据品牌id查询该品牌下商品的数量;
	 */
	public int getGoodsNumByBID(String brandID) throws Exception {
		return (int) daoSupport.findForObject("BrandReadMapper.getGoodsNumByBID",brandID);
	}
	
	/**修改品牌
	 * @param pd
	 * @throws Exception
	 * @author SSY
	 */
	@Override
	public void updateBrand(PageData pd) throws Exception {
		daoSupport.update ("BrandWriteMapper.updateBrand", pd);
	}
	
	/**
	 *  查询到的所有品牌类目
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getBrandCategory() throws Exception {
		return (List<PageData>)daoSupport.findForList("BrandReadMapper.getBrandCategory");
	}
	
	/**
	 *  查询到的所有品牌总数
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  PageData getBrandNum(Page page) throws Exception {
		return ( PageData )daoSupport.findForObject("BrandReadMapper.getBrandNum",page);
	}
	
	
	
	
	
	
	
	
	
	
	
}
