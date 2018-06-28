package com.aurora.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 * 创建: BYG 2017/5/25
 * 修改:
 * @version 1.0
 */

public class Menu implements Serializable{
	
	private int menuID;			//菜单ID
	private String menuName;	//菜单名称
	private String menuURL;		//链接
	private int menuParentID;	//上级菜单ID
	private String menuOrder;	//排序
	private int menuLevel;		//菜单级别
	private String menuState;	//菜单状态״̬
	private String target;
	private Menu parentMenu;	//父菜单
	private List<Menu> subMenu; //子菜单
	private boolean hasMenu = false;
	
	public int getMenuID() {
		return menuID;
	}
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuURL() {
		return menuURL;
	}
	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}
	public int getMenuParentID() {
		return menuParentID;
	}
	public void setMenuParentID(int menuParentID) {
		this.menuParentID = menuParentID;
	}
	public String getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(String menuOrder) {
		this.menuOrder = menuOrder;
	}
	public int getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}
	public String getMenuState() {
		return menuState;
	}
	public void setMenuState(String menuState) {
		this.menuState = menuState;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Menu getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	public List<Menu> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	public boolean isHasMenu() {
		return hasMenu;
	}
	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}
	@Override
	public String toString() {
		return "Menu [menuID=" + menuID + ", menuName=" + menuName + ", menuURL=" + menuURL + ", menuParentID="
				+ menuParentID + ", menuOrder=" + menuOrder + ", menuLevel=" + menuLevel + ", menuState=" + menuState
				+ ", target=" + target + ", parentMenu=" + parentMenu + ", subMenu=" + subMenu + ", hasMenu=" + hasMenu
				+ ", getMenuID()=" + getMenuID() + ", getMenuName()=" + getMenuName() + ", getMenuURL()=" + getMenuURL()
				+ ", getMenuParentID()=" + getMenuParentID() + ", getMenuOrder()=" + getMenuOrder()
				+ ", getMenuLevel()=" + getMenuLevel() + ", getMenuState()=" + getMenuState() + ", getTarget()="
				+ getTarget() + ", getParentMenu()=" + getParentMenu() + ", getSubMenu()=" + getSubMenu()
				+ ", isHasMenu()=" + isHasMenu() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
	
}
