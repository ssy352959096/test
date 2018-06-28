package com.aurora.service.shop.home;

import java.util.List;

import com.aurora.entity.Result;
import com.aurora.entity.home.Category;

/**
 * @Title: HomeCategoryService 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  商城首页导航栏类目维护接口
 * @author BYG
 * @date 2018年4月27日 
 * @version V2.0
 */
public interface HomeCategoryService {

	
	
	/**
	 * @Title: getCategoryListByLevelAndPID 
	 * @Description:  根据级别和父级类目ID获取相应类目列表接口
	 * @param     Category category
	 * @return    List<Category> 
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	List<Category> getCategoryListByLevelAndPID(Category category) throws Exception;
	
	/**
	 * @Title: getCategoryListByLevel 
	 * @Description:  根据级别获取相应类目列表接口
	 * @param     int categoryLevel
	 * @return    List<Category> 
	 * @author BYG
	 * @date 2018年4月27日 
	 */
	List<Category> getCategoryListByLevel(int categoryLevel) throws Exception;
	
	/**
	 * @Title: updateCategory
	 * @Description: 修改或新增类目
	 * @param    Category category(int categoryID;String categoryName;int categoryLevel;int categoryParentID;int seat;);
	 * @return  
	 * @author BYG
	 * @date 2018年4月27日
	 */
	void updateCategory(Category category) throws Exception;
	
	/**
	 * @Title: updateBatchCategorySeat
	 * @Description: 批量更改类目顺序
	 * @param    List<Category> categorySeat
	 * @return  
	 * @author BYG
	 * @date 2018年4月27日
	 */
	void updateBatchCategorySeat(List<Category> categorySeat) throws Exception;
	
	/**
	 * @Title: getAllCategory
	 * @Description: 获取所有目录（一二三级递归嵌套）
	 * @param    
	 * @return  List<Category>
	 * @author BYG
	 * @date 2018年5月7日
	 */
	List<Category> getAllCategory() throws Exception;

	/**
	 * @Title: deleteCategory 
	 * @Description: 删除首页导航,如果有子类目,提示不能删除
	 * @param    Integer categoryID
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年6月21日 下午1:54:34
	 */
	Result<Object> deleteCategory(Integer categoryID) throws Exception;
}
