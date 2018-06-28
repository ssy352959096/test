package com.aurora.service.dcc;

import java.util.List;

import com.aurora.entity.Result;
import com.aurora.entity.dcc.Contacts;
import com.aurora.entity.dcc.Enterprise;

/**
 * @Title: EnterpriseService.java
 * @Package com.aurora.service.dcc.supplier
 * @Description:企业信息service
 * @author KJH
 * @date 2018年6月25日 下午3:29:00
 * @version V1.0
 */

public interface EnterpriseService {
	/**
	 * @Title: updateEnterpriseInFo
	 * @Description:修改/增加企业信息
	 * @param Enterprise enterprise
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月25日 下午4:06:37
	 */
	public Result<Object> updateEnterpriseInFo(Enterprise enterprise)throws Exception;
	/**
	 * @Title: updateContactsInFo
	 * @Description:企业信息联系人增加/修改
	 * @param  Contacts contacts
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月25日 下午7:21:26
	 */
	public Result<Object> updateContactsInFo (Contacts contacts) throws Exception;
	/**
	 * @Title: searEnterpriseInFo
	 * @Description:查询企业信息
	 * @param  Enterprise
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月26日 下午12:47:29
	 */
	public List<Enterprise> searEnterpriseInFo (Enterprise enterprise) throws Exception;
}
