package com.aurora.service;

import java.util.List;

import com.aurora.entity.InquiryGoods;
import com.aurora.entity.InquiryManage;
import com.aurora.entity.Page;
import com.aurora.util.PageData;
/**
 * @Title: InquiryService.java 
 * @Package com.aurora.service 
 * @Description:询价service
 * @author SSY  
 * @date 2018年1月10日 上午9:41:57 
 * @version V1.0
 */
public interface InquiryService {
	
	/**
	 * 询价列表分页查询;
	 * @author SSY 2018-1-8
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<InquiryManage> getInquiryList(Page page) throws Exception;
	
	/**
	 * 查询符合条件的询价商品数量;
	 * @author SSY 2018-1-8
	 * @param  Page page
	 * @return int inquiryNum
	 * @throws Exception
	 */
	public int getInquiryNum(Page page) throws Exception;
	
	/**
	 * 查询符合询价状态的询价单数量
	 * @param Integer inquiryState,Integer salesManager
	 * @return int inquiryStateNum
	 * @throws Exception
	 * @author SSY 2018-1-8
	 */
	public int getInquiryStateNum(Integer inquiryState,Integer salesManager) throws Exception ;
	

	/**
	 * @param String today,Integer salesManager
	 * @return int inquiryDayNum
	 * @throws Exception
	 * @author SSY 2018-1-8
	 */
	public int getInquiryDayNum(String today,Integer salesManager) throws Exception;
	
	

	/**
	 * 查询询价商品
	 * @param inquiryGoodsID,String inquiryID,Integer userID 
	 * @return InquiryGoods inquiryGoods
	 * @throws Exception
	 * @author SSY 2018-1-8
	 */
	public InquiryGoods getInquiryGoods(String inquiryGoodsID,String inquiryID,Integer salesManager) throws Exception;
	
	
	/**
	 * 更新商品报价
	 * @param  InquiryGoods inquiryGoods,Integer salesManager
	 * @return  
	 * @throws Exception
	 * @author SSY 2018-1-8
	 */
	public void updateInquiryGoods(InquiryGoods inquiryGoods,Integer salesManager)throws Exception;
	
	/**
	 * @Title: getInquriySalesManage 
	 * @Description: 查询询价单专属销售id
	 * @param    String inquiryID,Integer salesManager
	 * @return int  salesManager
	 * @author SSY 
	 * @date 2018年1月9日 下午8:07:31
	 */
	public int getInquriySalesManage(String inquiryID)throws Exception;
	
	/**
	 * 更新询价单状态,销售权限受限
	 * @param  InquiryManage inquiryManage
	 * @return  int updateNum
	 * @throws Exception
	 * @author SSY 2018-1-8
	 */
	public int updateInquriyState(InquiryManage inquiryManage)throws Exception;
	
	/**
	 * 获取所有货币符号;
	 * @param  
	 * @return List<PageData> currency
	 * @throws Exception
	 * @author SSY 2018-1-8
	 */
	public List<PageData> getCurrency() throws Exception;

	/**
	 * @Title: updateInquriyGoodsValidity 
	 * @Description: 定时检查询价商品有效期;
	 * @param    
	 * @return void  
	 * @author SSY
	 * @date 2018年1月23日 下午2:29:12
	 */
	public void updateInquriyGoodsValidity() throws Exception;

	/**
	 * @Title: getNoQuotationRemain 
	 * @Description: 未报价询价单短信提醒;包含询价单的专属销售id和数量;
	 * @param    
	 * @return List<PageData>  
	 * @author SSY
	 * @date 2018年2月22日 下午5:25:24
	 */
	public List<PageData> getNoQuotationRemain() throws Exception;

	/**
	 * @Title: getInquiryBrandAndMobile 
	 * @Description: 获取询价商品品牌、及客户手机号
	 * @param    
	 * @return List<PageData>  
	 * @author BYG
	 * @date 2018年4月16日
	 */
	public List<PageData> getInquiryBrandAndMobile(String inquiryID) throws Exception;
	
	
	

	/**
	 * ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ 
	 * **************************************我是分割线******************************************* 
	 * ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
	 */
	
	
	 
	
}
