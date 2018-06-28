package com.aurora.serviceImpl.dcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Result;
import com.aurora.entity.dcc.CommodityEntry;
import com.aurora.service.dcc.CommodityEntryService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.Tools;

/**
 * @Title: CommodityEntryServiceImpl.java
 * @Package com.aurora.serviceImpl.dcc.commodity
 * @Description:
 * @author KJH
 * @date 2018年6月26日 下午2:40:54
 * @version V1.0
 */
@Service
public class CommodityEntryServiceImpl implements CommodityEntryService {

	@Autowired
	private  DAO dao;
	/**
	 * @Title: CommodityEntryInFo
	 * @Description:新增/修改商品信息
	 * @param
	 * @return
	 * @author KJH
	 * @date 2018年6月26日 下午2:43:26
	 */
	@Override
	public Result<Object> updateCommodityEntryInFo(CommodityEntry commodityEntry) throws Exception {
		Result<Object> result = new Result<Object>();
		if (Tools.isEmpty(commodityEntry.getBrandName()) || Tools.isEmpty(commodityEntry.getSmallClassName())
				|| Tools.isEmpty(commodityEntry.getCommodityName()) || Tools.isEmpty(commodityEntry.getCompany())
				|| Tools.isEmpty(commodityEntry.getEAN())|| Tools.isEmpty(commodityEntry.getSPEC())
				|| Tools.isEmpty(commodityEntry.getMajorSupplier())|| Tools.isEmpty(commodityEntry.getCategoryAttribution())) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		commodityEntry.setUpdateTime(DateUtil.getTime());
		commodityEntry.setUpdateName(Jurisdiction.getUserEmail());
		int num=0;
		if (null == commodityEntry.getCommodityId()) {
			num=(int) dao.save("CommodityEntryWriteMapper.addCommodityEntryInFo", commodityEntry);
		} else {
			num=(int) dao.update("CommodityEntryWriteMapper.updateCommodityEntryInFo", commodityEntry);
		}
		result.setMsg(num >= 1 ? "操作成功! " : "操作失败! ");
		result.setState(num >= 1 ? Result.STATE_SUCCESS : Result.STATE_ERROR);
		return result;
	}
	

}
