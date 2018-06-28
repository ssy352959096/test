package com.aurora.service;

import java.util.ArrayList;
import java.util.List;

import com.aurora.entity.Page;
import com.aurora.entity.User;
import com.aurora.util.PageData;

public interface UserService {

	/**登录判断
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public User getUserByEmailAndPwd(PageData pd)throws Exception;
	
	/**更新登录时间
	 * @param pd
	 * @throws Exception
	 */
	public void updateUserLastLoginTime(PageData pd)throws Exception;
	
	/**通过Username获取用户数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public User findByUserEmail(PageData pd)throws Exception;
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveNewUser(PageData pd)throws Exception;
	
	/**通过用户ID获取用户信息和角色信息
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public User getUserAndRoleById(int userID) throws Exception;
	
	/**获取用户总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData getUserCount(String value)throws Exception;
	
	/**保存用户IP
	 * @param pd
	 * @throws Exception
	 */
	public void saveIP(PageData pd)throws Exception;
	
	/**更新用户状态
	 * @param pd
	 * @throws Exception
	 */
	public void updateUserStatus(PageData pd)throws Exception;
	
	/**默认用户列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData getUserTotalRecord(Page page)throws Exception;
	
	/**默认用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getUserList(Page page)throws Exception;
	
	/**条件查询用户列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData getUserTotalRecordByCondition(Page page)throws Exception;
	
	/**条件查询用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getUserListByCondition(Page page)throws Exception;
	
	/**删除单个用户
	 * @param pd
	 * @throws Exception deleteSomeUser
	 */
	public void deleteOneUser(PageData pd)throws Exception;
	
	/**批量删除用户
	 * @param userID
	 * @throws Exception
	 */
	public void deleteSomeUser(String[] userID)throws Exception;
	
	/**管理员修改系统用户资料保存
	 * @param pd
	 * @throws Exception
	 */
	public void saveEditSystemUser(PageData pd)throws Exception;
	
	/**修改个人用户资料
	 * @param pd
	 * @throws Exception
	 */
	public void updateMyUser(PageData pd)throws Exception;
	
	/**通过用户ID获取用户数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByUserID(PageData pd)throws Exception;
	
	/**根据角色ID获取用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getUserListByRoldID(PageData pd) throws Exception;

	/**
	 * 验证账户邮箱是否重复;
	 * @param userEmail
	 */
	public PageData getUserCountByEmail(String userEmail) throws Exception;
	
	/**根据销售角色ID获取所属用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getUserIDBySalesRoleID(String salesRoleID) throws Exception;
	
	/**根据值班角色ID获取所属用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public User getWatchUser(String watchRoleID) throws Exception;
}
