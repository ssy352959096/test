package com.aurora.service.dcc;

import com.aurora.entity.Result;
import com.aurora.entity.dcc.CommodityEntry;

/**
 * @Title: CommodityEntryService.java
 * @Package com.aurora.service.dcc.commodity
 * @Description:商品信息service
 * @author KJH
 * @date 2018年6月26日 下午2:38:03
 * @version V1.0
 */
public interface CommodityEntryService {
	/**
	 * @Title: CommodityEntryInFo
	 * @Description:新增/修改商品信息
	 * @param
	 * @return Result<Object>
	 * @author KJH
	 * @date 2018年6月26日 下午2:42:35
	 */
	public Result<Object> updateCommodityEntryInFo(CommodityEntry commodityEntry) throws Exception;
}
