package com.aurora.serviceImpl.dcc;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Result;
import com.aurora.entity.dcc.Contacts;
import com.aurora.entity.dcc.Enterprise;
import com.aurora.service.dcc.EnterpriseService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.Tools;

/**
 * @Title: EnterpriseServiceImpl.java
 * @Package com.aurora.serviceImpl.dcc.supplier
 * @Description:
 * @author KJH
 * @date 2018年6月25日 下午3:29:16
 * @version V1.0
 */
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

	@Autowired
	private DAO dao;

	/**
	 * @Title: updateEnterpriseInFo
	 * @Description:修改/增加企业信息
	 * @param Enterprise enterprise
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月25日 下午4:09:17
	 */
	@Override
	public Result<Object> updateEnterpriseInFo(Enterprise enterprise) throws Exception {

		Result<Object> result = new Result<Object>();
		if (Tools.isEmpty(enterprise.getAccountPeriodStar())||Tools.isEmpty(enterprise.getAccountPeriodEnd())|| null == enterprise.getCurrency()
				|| null == enterprise.getTrandMode() || null == enterprise.getCooperationDegree()
				|| Tools.isEmpty(enterprise.getAdvantageBrand()) || Tools.isEmpty(enterprise.getEnterpriseName())
				|| Tools.isEmpty(enterprise.getProductSubclass()) || Tools.isEmpty(enterprise.getCountry())
				|| Tools.isEmpty(enterprise.getRegion()) || Tools.isEmpty(enterprise.getStreet())
				|| Tools.isEmpty(enterprise.getPurchaseUser())) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		enterprise.setUpdateName(Jurisdiction.getUserEmail());
		enterprise.setUpdateTime(DateUtil.getTime());
		int num = 0;
		if (null == enterprise.getEnterpriseId()) {
			num = (int) dao.save("EnterpriseWriteMapper.addEnterPriseInFo", enterprise);
		} else {
			num = (int) dao.update("EnterpriseWriteMapper.updateEnterPriseInFo", enterprise);
		}
		result.setMsg(num >= 1 ? "操作成功！" : "操作失败！");
		result.setState(num >= 1 ? Result.STATE_SUCCESS : Result.STATE_ERROR);
		return result;
	}
     /**
      * @Title: updateContactsInFo
      * @Description:新增/修改联系人信息
      * @param  Contacts contacts
      * @return Result<Object>
      * @author KJH
      * @date 2018年6月26日 下午12:44:12
      */
	@Override
	public Result<Object> updateContactsInFo(Contacts contacts) throws Exception {
		Result<Object> result = new Result<Object>();
		if (null == contacts.getEnterpriseId() || Tools.isEmpty(contacts.getContacts())
				|| Tools.isEmpty(contacts.getPost()) || Tools.isEmpty(contacts.getTelephone())
				|| Tools.isEmpty(contacts.getWeixin())) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		contacts.setUpdateName(Jurisdiction.getUserEmail());
		contacts.setUpdateTime(DateUtil.getTime());
		int num = 0;
		if (null == contacts.getContactsId()) {
			num = (int) dao.save("ContactsWriteMapper.addContactsInFo", contacts);
		} else {
			num = (int) dao.update("ContactsWriteMapper.updateContactsInFo", contacts);
		}
		result.setMsg(num >= 1 ? "操作成功! " : "操作失败! ");
		result.setState(num >= 1 ? Result.STATE_SUCCESS : Result.STATE_ERROR);
		return result;
	}
	/**
	 * @Title: searEnterpriseInFo
	 * @Description:
	 * @param  
	 * @return 
	 * @author KJH
	 * @date 2018年6月26日 下午12:48:25
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Enterprise> searEnterpriseInFo(Enterprise enterprise) throws Exception {
		System.out.println("serviceImpl:"+enterprise.getEnterpriseName());
		List<Enterprise> enterprises=(List<Enterprise>) dao.findForList("EnterpriseReadMapper.searEnterPriseInFo", enterprise);
		return enterprises;
	}
}
