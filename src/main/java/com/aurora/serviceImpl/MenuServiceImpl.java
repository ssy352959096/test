package com.aurora.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Menu;
import com.aurora.entity.Page;
import com.aurora.service.MenuService;
import com.aurora.util.PageData;

@Service("menuServiceImpl")
public class MenuServiceImpl implements MenuService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;

	
	/**
	 * 根据父级ID获取所有菜单并填充每个菜单的子菜单列表(系统菜单分级展示列表)(递归处理/多元数组)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Menu> getAllMenuList(int menuID) throws Exception {
		List<Menu> menuList = this.getSubMenuListByMenuParentID(menuID);
		for(Menu menu : menuList){
			menu.setSubMenu(this.getAllMenuList(menu.getMenuID()));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}
	
	
	/**
	 * 通过菜单父级ID获取其子一级菜单
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getSubMenuListByMenuParentID(int menuParentID) throws Exception {
		return (List<Menu>) daoSupport.findForList("MenuReadMapper.getSubMenuListByMenuParentID", menuParentID);
	}
	
	/**默认菜单列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getMenuTotalRecord(Page page) throws Exception {
		return (PageData) daoSupport.findForObject("MenuReadMapper.getMenuTotalRecord", page);
	}

	/**
	 * 默认查询出所有菜单
	 * @param page(keywords;menuLevel)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getMenuList(Page page) throws Exception {
		return (List<PageData>)daoSupport.findForList("MenuReadMapper.getMenuList", page);
	}
	
	/**条件查询菜单列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getMenuTotalRecordByCondition(Page page) throws Exception {
		return (PageData) daoSupport.findForObject("MenuReadMapper.getMenuTotalRecordByCondition", page);
	}

	/**
	 * 菜单管理页面模糊名称/级别查询出所有菜单
	 * @param page(keywords;menuLevel)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getMenuListByCondition(Page page) throws Exception {
		return (List<PageData>)daoSupport.findForList("MenuReadMapper.getMenuListByCondition", page);
	}
	
	/**
	 * 保存新增菜单
	 * @param pd(Menu)
	 * @return
	 * @throws Exception
	 */
	@Override
	public void saveNewMenu(PageData pd) throws Exception {
		daoSupport.save("MenuWriteMapper.saveNewMenu", pd);
	}

	/**通过菜单ID获取菜单数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getMenuByMenuID(PageData pd) throws Exception {
		return (PageData)daoSupport.findForObject("MenuReadMapper.getMenuByMenuID", pd);
	}
	
	/**
	 * 保存修改菜单
	 * @param pd(Menu)
	 * @return
	 * @throws Exception
	 */
	@Override
	public void saveEditMenu(PageData pd) throws Exception {
		daoSupport.save("MenuWriteMapper.saveEditMenu", pd);
	}

	/**通过菜单ID删除菜单
	 * @param menuID
	 * @throws Exception
	 */
	@Override
	public void deleteMenuByMenuID(int menuID) throws Exception {
		daoSupport.delete("MenuWriteMapper.deleteMenuByMenuID", menuID);
	}

	/**查询出所有菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getAllMenu(PageData pd) throws Exception {
		return (List<PageData>)daoSupport.findForList("MenuReadMapper.getAllMenu", pd);
	}

}
