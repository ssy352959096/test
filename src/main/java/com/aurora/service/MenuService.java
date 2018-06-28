package com.aurora.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aurora.entity.Menu;
import com.aurora.entity.Page;
import com.aurora.util.PageData;

public interface MenuService {
	
	/**根据父级菜单ID=0查询出所有菜单
	 * @param menuID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> getAllMenuList(int menuID) throws Exception;
	
	/**根据父级菜单ID查询出相应子菜单
	 * @param menuParentID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> getSubMenuListByMenuParentID(int menuParentID)throws Exception;
	
	/**默认菜单列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuTotalRecord(Page page)throws Exception;
	
	/**默认查询出所有菜单
	 * @param page(keywords;menuLevel)
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getMenuList(Page page)throws Exception;
	
	/**条件查询菜单列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuTotalRecordByCondition(Page page)throws Exception;
	
	/**菜单管理页面模糊名称/级别查询出所有菜单
	 * @param page(keywords;menuLevel)
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getMenuListByCondition(Page page)throws Exception;
	
	/**保存新增菜单
	 * @param pd
	 * @throws Exception
	 */
	public void saveNewMenu(PageData pd)throws Exception;
	
	/**通过菜单ID获取菜单数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuByMenuID(PageData pd)throws Exception;
	
	/**查询出所有菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getAllMenu(PageData pd)throws Exception;
	
	/**保存修改菜单
	 * @param pd
	 * @throws Exception
	 */
	public void saveEditMenu(PageData pd)throws Exception;
	
	/**通过菜单ID删除菜单
	 * @param menuID
	 * @throws Exception
	 */
	public void deleteMenuByMenuID(int menuID) throws Exception;

	
	
//	
//	
//	
//	
//	public Map<String,List<Menu>> getAllMenuList(int menuID) throws Exception ;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
