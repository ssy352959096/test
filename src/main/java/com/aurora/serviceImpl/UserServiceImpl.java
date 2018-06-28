package com.aurora.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.entity.User;
import com.aurora.service.UserService;
import com.aurora.util.PageData;


/**
 * 描述:客户登录注册ServiceImpl
 * 创建:BYG 2017/5/25
 * 修改:
 * @version 1.0
 */

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;
	
	/**登录判断
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public User getUserByEmailAndPwd(PageData pd) throws Exception {
		return (User)daoSupport.findForObject("UserReadMapper.getUserByEmailAndPwd", pd);
	}

	/**更新登录时间
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void updateUserLastLoginTime(PageData pd) throws Exception {
		daoSupport.update("UserWriteMapper.updateUserLastLoginTime", pd);
		
	}
	
	/**通过userName获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public User findByUserEmail(PageData pd) throws Exception {
		return (User)daoSupport.findForObject("UserReadMapper.findByUserEmail", pd);
	}

	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void saveNewUser(PageData pd) throws Exception {
		daoSupport.save("UserWriteMapper.saveNewUser", pd);
		
	}
	
	/**通过用户ID获取用户信息和角色信息
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	@Override
	public User getUserAndRoleById(int userID) throws Exception {
		return (User)daoSupport.findForObject("UserReadMapper.getUserAndRoleById", userID);
	}
	
	/**获取用户总数
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData getUserCount(String value) throws Exception {
		return (PageData)daoSupport.findForObject("UserReadMapper.getUserCount", value);
	}
	
	/**保存用户IP以及登陆时间
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void saveIP(PageData pd) throws Exception {
		daoSupport.update("UserWriteMapper.saveIP", pd);
		
	}
	
	/**更新用户状态
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void updateUserStatus(PageData pd) throws Exception {
		daoSupport.update("UserWriteMapper.updateUserStatus", pd);
	}
	
	/**默认用户列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getUserTotalRecord(Page page) throws Exception {
		return (PageData) daoSupport.findForObject("UserReadMapper.getUserTotalRecord", page);
	}

	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getUserList(Page page) throws Exception {
		return (List<PageData>)daoSupport.findForList("UserReadMapper.getUserList", page);
	}
	
	/**条件查询用户列表总记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getUserTotalRecordByCondition(Page page) throws Exception {
		return (PageData) daoSupport.findForObject("UserReadMapper.getUserTotalRecordByCondition", page);
	}
	
	/**条件查询用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getUserListByCondition(Page page) throws Exception {
		return (List<PageData>)daoSupport.findForList("UserReadMapper.getUserListByCondition", page);
	}
	
	/**删除单个用户
	 * @param pd
	 * @throws Exception deleteSomeUser
	 */
	@Override
	public void deleteOneUser(PageData pd) throws Exception {
		daoSupport.delete("UserWriteMapper.deleteOneUser", pd);
	}

	/**批量删除用户
	 * @param userID
	 * @throws Exception
	 */
	@Override
	public void deleteSomeUser(String[] userID) throws Exception {
		daoSupport.delete("UserWriteMapper.deleteSomeUser", userID);
		
	}

	/**通过用户ID获取用户数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getUserByUserID(PageData pd) throws Exception {
		return (PageData)daoSupport.findForObject("UserReadMapper.getUserByUserID", pd);
	}

	/**管理员修改系统用户资料保存
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void saveEditSystemUser(PageData pd) throws Exception {
		daoSupport.update("UserWriteMapper.saveEditSystemUser", pd);		
	}

	/**修改个人用户资料
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void updateMyUser(PageData pd) throws Exception {
		daoSupport.update("UserWriteMapper.updateMyUser", pd);		
	}
	
	/**根据角色ID获取用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getUserListByRoldID(PageData pd) throws Exception {
		return (List<PageData>)daoSupport.findForList("UserReadMapper.getUserListByRoldID", pd);
	}
	
	/**
	 * 验证账户邮箱是否重复;
	 * @param userEmail
	 */
	@Override
	public PageData getUserCountByEmail(String userEmail) throws Exception {
	return (PageData)daoSupport.findForObject("UserReadMapper.getUserCountByEmail", userEmail);
	}

	/**根据销售角色ID获取所属用户id
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getUserIDBySalesRoleID(String salesRoleID) throws Exception {
		return (List<PageData>)daoSupport.findForList("UserReadMapper.getUserIDBySalesRoleID", salesRoleID);
	}
		
	/**根据值班角色ID获取所属用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public User getWatchUser(String watchRoleID) throws Exception {
		return (User)daoSupport.findForObject("UserReadMapper.getWatchUser", watchRoleID);
	}
	
	
	
	
}
