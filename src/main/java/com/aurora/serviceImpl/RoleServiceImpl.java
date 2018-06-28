package com.aurora.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.entity.Role;
import com.aurora.service.RoleService;
import com.aurora.util.PageData;

@Service("roleServiceImpl")
public class RoleServiceImpl implements RoleService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;
	
	/**根据角色ID查询角色
	 * @param pd(roleID)
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getRoleByRoleID(PageData pd) throws Exception {
		return (PageData)daoSupport.findForObject("RoleReadMapper.getRoleByRoleID", pd);
	}
	
	/**根据角色父级ID查询所属下级角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoleListByRoleParentID(PageData pd) throws Exception {
		return (List<Role>)daoSupport.findForList("RoleReadMapper.getRoleListByRoleParentID", pd);
	}

	/**默认用户列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData getRoleTotalRecord(Page page) throws Exception {
		return (PageData) daoSupport.findForObject("RoleReadMapper.getRoleTotalRecord", page);
	}

	/**默认查询角色信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getRoleList(Page page) throws Exception {
		return (List<PageData>)daoSupport.findForList("RoleReadMapper.getRoleList", page);
	}
	
	/**条件查询用户列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getRoleTotalRecordByCondition(Page page) throws Exception {
		return (PageData) daoSupport.findForObject("RoleReadMapper.getRoleTotalRecordByCondition", page);
	}
	
	/**条件查询显示角色列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getRoleListByCondition(Page page) throws Exception {
		return (List<PageData>)daoSupport.findForList("RoleReadMapper.getRoleListByCondition", page);
	}
	
	/**无参取出全部角色信息
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRole() throws Exception {
		return (List<Role>)daoSupport.findForList("RoleReadMapper.getAllRole");
	}
	
	/**保存新增角色
	 * @param pd(Role)
	 * @return
	 * @throws Exception
	 */
	@Override
	public void saveNewRole(PageData pd) throws Exception {
		daoSupport.save("RoleWriteMapper.saveNewRole", pd);
	}

	/**根据角色ID保存角色修改信息
	 * @param pd(roleID)
	 * @return
	 * @throws Exception
	 */
	@Override
	public void saveEditRole(PageData pd) throws Exception {
		daoSupport.save("RoleWriteMapper.saveEditRole", pd);
		
	}
	
	/**根据角色ID删除角色
	 * @param roleID
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deleteRoleByRoleID(int roleID) throws Exception {
		daoSupport.delete("RoleWriteMapper.deleteRoleByRoleID", roleID);		
	}
	
	/**根据角色名称查询角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public Role getRoleByRoleName(String roleName) throws Exception {
		return (Role)daoSupport.findForObject("RoleReadMapper.getRoleByRoleName", roleName);
	}



}
