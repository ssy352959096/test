package com.aurora.serviceImpl.shop.home;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.home.Category;
import com.aurora.service.shop.home.GoodsCategoryService;
import com.aurora.util.DateUtil;

/**
 * @Title: GoodsCategoryServiceImpl 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  商城首页本站热卖维护接口实现类
 * @author BYG
 * @date 2018年4月27日 
 * @version V2.0
 */
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService{
	
	@Autowired
	private DAO daoSupport;

	/**
	 * @Title: getCategoryListByLevelAndPID 
	 * @Description:  根据级别和父级类目ID获取相应类目列表接口实现类
	 * @param     Category category
	 * @return    List<Category> 
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryListByLevelAndPID(Category category) throws Exception {
		return (List<Category>) daoSupport.findForList("GoodsCategoryReadMapper.getCategoryListByLevelAndPID", category);
	}

	/**
	 * @Title: getCategoryListByLevel 
	 * @Description:  根据级别获取相应类目列表接口实现类
	 * @param     int categoryLevel
	 * @return    List<Category> 
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryListByLevel(int categoryLevel) throws Exception {
		return (List<Category>) daoSupport.findForList("GoodsCategoryReadMapper.getCategoryListByLevel", categoryLevel);
	}

	/**
	 * @Title: updateCategory
	 * @Description: 修改或新增类目
	 * @param    Category category(int categoryID;String categoryName;int categoryLevel;int categoryParentID;int seat;);
	 * @return  
	 * @author BYG
	 * @date 2018年4月27日
	 */
	@Override
	public void updateCategory(Category category) throws Exception {
		category.setUpdateTime(DateUtil.getTime());
		if (category.getCategoryID() == null) {
			daoSupport.save("GoodsCategoryWriteMapper.addCategory", category);
		} else {
			daoSupport.update("GoodsCategoryWriteMapper.updateCategory", category);
		}
	}

	/**
	 * @Title: updateBatchCategorySeat
	 * @Description: 批量更改类目顺序
	 * @param    List<Category> categorySeat
	 * @return  
	 * @author BYG
	 * @date 2018年4月27日
	 */
	@Override
	public void updateBatchCategorySeat(List<Category> categorySeat) throws Exception {
		daoSupport.update("GoodsCategoryWriteMapper.updateBatchCategorySeat", categorySeat);
	}

	/**
	 * @Title: getAllCategory
	 * @Description: 获取所有目录（一二三级递归嵌套）
	 * @param    
	 * @return  List<Category>
	 * @author BYG
	 * @date 2018年5月7日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAllCategory() throws Exception {
		List<Category> category = new ArrayList<Category>();
		int categoryLevel = 1;
		int categoryParentID = 0;
		List<Category> category1 = (List<Category>) daoSupport.findForList("GoodsCategoryReadMapper.getCategoryListByLevel", categoryLevel);
		if (category1.size() > 0) {
			for (Category c1 : category1) {
				categoryParentID = c1.getCategoryID();
				List<Category> category2 = (List<Category>) daoSupport.findForList("GoodsCategoryReadMapper.getCategoryListByPID", categoryParentID);
				if (category2.size() > 0) {
					for (Category c2 : category2) {
						categoryParentID = c2.getCategoryID();
						List<Category> category3 = (List<Category>) daoSupport.findForList("GoodsCategoryReadMapper.getCategoryListByPID", categoryParentID);
						c2.setSubcategory(category3);
					}
				}
				c1.setSubcategory(category2);
			}
		}
		category = category1;
		return category;
	}

	/**
	 * @Title: getHomeCategory 
	 * @Description:  查询首页展示一级类目
	 * @param     int categoryLevel
	 * @return    List<Category> 
	 * @author SSY
	 * @date 2018年5月17日 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getHomeCategory(int categoryLevel) throws Exception {
		return (List<Category>) daoSupport.findForList("GoodsCategoryReadMapper.getHomeCategory", categoryLevel);
	}
	
}
