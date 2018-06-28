package com.aurora.service.shop.home;

import com.aurora.entity.Result;
import com.aurora.util.PageData;

/**
 * @Title: HomeKeywordService.java 
 * @Package com.aurora.service.shop.home 
 * @Description: 首页关键词
 * @author SSY  
 * @date 2018年5月2日 下午2:54:05 
 * @version V1.0
 */
public interface HomeKeywordService {

	/**
	 * @Title: getHomeKeyword 
	 * @Description: 查询首页关键词
	 * @param    Integer keywordType
	 * @return PageData  
	 * @author SSY
	 * @date 2018年5月2日 下午3:28:45
	 */
	PageData getHomeKeyword(Integer keywordType) throws Exception;
	
	/**
	 * @Title: updateHomeKeyword 
	 * @Description:  更新首页关键词
	 * @param    Integer keywordType, String homeKeyword
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月2日 下午3:35:14
	 */
	public Result<Object> updateHomeKeyword(Integer keywordType, String homeKeyword) throws Exception;

	

	
}
