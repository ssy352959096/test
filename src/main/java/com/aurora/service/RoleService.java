package com.aurora.service;

import java.util.List;

import com.aurora.entity.Page;
import com.aurora.entity.Role;
import com.aurora.util.PageData;


public interface RoleService {
	
	/**通过id查找
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getRoleByRoleID(PageData pd) throws Exception;
	
	/**列出父级角色的下级角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Role> getRoleListByRoleParentID(PageData pd) throws Exception;
	
	/**取出全部角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Role> getAllRole() throws Exception;
	
	/**默认角色列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData getRoleTotalRecord(Page page)throws Exception;
	
	/**默认显示角色列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getRoleList(Page page)throws Exception;
	
	/**条件查询角色列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData getRoleTotalRecordByCondition(Page page)throws Exception;
	
	/**条件查询显示角色列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getRoleListByCondition(Page page)throws Exception;
	
	/**保存新增角色
	 * @param pd
	 * @throws Exception
	 */
	public void saveNewRole(PageData pd)throws Exception;
	
	/**保存修改角色
	 * @param pd
	 * @throws Exception
	 */
	public void saveEditRole(PageData pd)throws Exception;
	
	/**删除角色
	 * @param roleID
	 * @throws Exception
	 */
	public void deleteRoleByRoleID(int roleID) throws Exception;
	
	/**根据角色名称查询角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Role getRoleByRoleName(String roleName) throws Exception;
	
}
