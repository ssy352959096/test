package com.aurora.serviceImpl.shop.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Result;
import com.aurora.service.shop.home.HomeKeywordService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
/**
 * @Title: HomeKeywordServiceImpl.java 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description:  首页关键词管理serviceImpl
 * @author SSY  
 * @date 2018年5月2日 下午2:55:09 
 * @version V1.0
 */
@Service
public class HomeKeywordServiceImpl implements HomeKeywordService {

	@Autowired
	private DAO daoSupport;
	/**
	 * @Title: getHomeKeyword 
	 * @Description: 查询首页关键词
	 * @param    int keywordType
	 * @return PageData  
	 * @author SSY
	 * @date 2018年5月2日 下午3:28:45
	 */
	public PageData getHomeKeyword(Integer keywordType) throws Exception{
		PageData pd = (PageData)daoSupport.findForObject("HomeKeywordReadMapper.getHomeKeyword",keywordType);
		return pd;
	}
	
	
	/**
	 * @Title: updateHomeKeyword 
	 * @Description:  更新首页关键词
	 * @param   Integer keywordType, String homeKeyword 
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月2日 下午3:35:14
	 */
	public Result<Object> updateHomeKeyword(Integer keywordType, String homeKeyword) throws Exception{
		Result<Object> result = new Result<Object>();
		if (null==keywordType) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_SUCCESS);
			return result;
		}
		PageData pd = new PageData();
		pd.put("keywordType", keywordType);
		pd.put("homeKeyword", homeKeyword);
//		pd.put("operator", Jurisdiction.getUserEmail());
		pd.put("operateTime", DateUtil.getTime());
		daoSupport.update("HomeKeywordWriteMapper.updateHomeKeyword",pd);
		result.setState(Result.STATE_SUCCESS);
		return result;
	}
	
	
	
	
	
	
	
}
