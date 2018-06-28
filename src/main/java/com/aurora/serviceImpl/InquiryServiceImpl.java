package com.aurora.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.InquiryGoods;
import com.aurora.entity.InquiryManage;
import com.aurora.entity.Page;
import com.aurora.service.InquiryService;
import com.aurora.util.CustomException;
import com.aurora.util.PageData;

/**
 * @Title: InquiryServiceImpl.java 
 * @Package com.aurora.serviceImpl 
 * @Description: 询价service
 * @author SSY  
 * @date 2018年1月10日 上午9:41:41 
 * @version V1.0
 */

@Service("inquiryServiceImpl")
public class InquiryServiceImpl implements InquiryService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;
	
	/**
	 * 查询符合条件的询价列表;
	 * @author SSY 2018-1-8
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InquiryManage> getInquiryList(Page page) throws Exception{
		return (List<InquiryManage>)daoSupport.findForList("InquiryReadMapper.getInquiryList", page);
	}
	
	/**
	 * 查询符合条件的询价商品数量;
	 * @author SSY 2018-1-8
	 * @param  Page page
	 * @return int inquiryNum
	 * @throws Exception
	 */
	@Override
	public int getInquiryNum(Page page) throws Exception{
		Object object = daoSupport.findForObject("InquiryReadMapper.getInquiryNum", page);
		return object != null ? (int)object : 0;
	}
	
	/**
	 * 查询符合询价状态的询价单数量 1未报；2已报
	 * @param Integer inquiryState,Integer salesManager
	 * @return int inquiryStateNum
	 * @throws Exception
	 * @author SSY 2018-1-8
	 */
	@Override
	public int getInquiryStateNum(Integer inquiryState,Integer salesManager) throws Exception {
		PageData pd = new PageData();
		pd.put("inquiryState",inquiryState);
		pd.put("salesManager",salesManager);
		Object object = daoSupport.findForObject("InquiryReadMapper.getInquiryStateNum", pd);
		return object != null ? (int)object : 0;
	}

	
	/**
	 * 查询当天询价单数量;
	 * @param String date
	 * @return int inquiryDayNum
	 * @throws Exception
	 */
	@Override
	public int getInquiryDayNum(String date,Integer salesManager) throws Exception {
		PageData pd = new PageData();
		pd.put("date",date);
		pd.put("salesManager",salesManager);
		Object object = daoSupport.findForObject("InquiryReadMapper.getInquiryDayNum", pd);
		return object != null ? (int)object : 0;
	}
	
	/**
	 * 查询询价商品
	 * @param String inquiryGoodsID,String inquiryID,Integer userID
	 * @return InquiryGoods inquiryGoods
	 * @throws Exception
	 * @author SSY 2018-1-8
	 */
	@Override
	public InquiryGoods getInquiryGoods(String inquiryGoodsID,String inquiryID,Integer salesManager) throws Exception{
		PageData pd = new PageData();
		pd.put("inquiryGoodsID", inquiryGoodsID);
		pd.put("inquiryID", inquiryID);
		return (InquiryGoods) daoSupport.findForObject("InquiryReadMapper.getInquiryGoods", pd);
	}
	
	/**
	 * 更新商品报价
	 * @param  InquiryGoods inquiryGoods, Integer salesManager
	 * @return 
	 * @throws Exception
	 * @author SSY 2018-1-8
	 */
	@Override
	public void updateInquiryGoods(InquiryGoods inquiryGoods,Integer salesManager)throws Exception{
	 
		daoSupport.update("InquiryWriteMapper.updateInquiryGoods", inquiryGoods);
	}
	
	/**
	 * @Title: getInquriySalesManage 
	 * @Description: 查询询价单专属销售id
	 * @param    String inquiryID,Integer salesManager
	 * @return int  salesManager
	 * @author SSY 
	 * @date 2018年1月9日 下午8:07:31
	 */
	@Override
	public int getInquriySalesManage(String inquiryID)throws Exception{
		return (int)daoSupport.findForObject("InquiryReadMapper.getInquriySalesManage", inquiryID);
	}
	
	/**
	 * 更新询价单状态,销售权限受限
	 * @param String inquiryID,Integer inquiryState
	 * @return int updateNum
	 * @throws Exception
	 * @author SSY 2018-1-8
	 */
	@Override
	public int updateInquriyState(InquiryManage inquiryManage)throws Exception{
		int updateNum = (int)daoSupport.update("InquiryWriteMapper.updateInquriyState", inquiryManage);
		return updateNum;
	}
	

	/**
	 * 获取所有货币符号;
	 * @param  
	 * @return List<PageData> currency
	 * @throws Exception
	 * @author SSY 2018-1-8
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getCurrency() throws Exception{
		return (List<PageData>)daoSupport.findForList("DictionaryReadMapper.getCurrencySign");
	}
	
	/**
	 * @Title: updateInquriyGoodsValidity 
	 * @Description: 定时检查询价商品有效期;
	 * @param    
	 * @return void  
	 * @author SSY
	 * @date 2018年1月23日 下午2:29:12
	 */
	@Override
	public void updateInquriyGoodsValidity() throws Exception{
		daoSupport.update("InquiryWriteMapper.updateInquriyGoodsValidity");
	}
	
	
	/**
	 * @Title: getNoQuotationRemain 
	 * @Description: 未报价询价单短信提醒;包含询价单的现任专属销售id和数量;
	 * @param    
	 * @return List<PageData>  
	 * @author SSY
	 * @date 2018年2月22日 下午5:24:02
	 */
	public List<PageData> getNoQuotationRemain() throws Exception{
		//查询未报价的询价单,包含询价单的专属销售id和数量;
		@SuppressWarnings("unchecked")
		List<PageData> noQuotationRemainInquiry = (List<PageData>)daoSupport.findForList("InquiryReadMapper.getNoQuotationRemain");
		return noQuotationRemainInquiry;
	}
	
	/**
	 * @Title: getInquiryBrandAndMobile 
	 * @Description: 获取询价商品品牌、及客户手机号
	 * @param    
	 * @return List<PageData>  
	 * @author BYG
	 * @date 2018年4月16日
	 */
	public List<PageData> getInquiryBrandAndMobile(String inquiryID) throws Exception{
		//查询未报价的询价单,包含询价单的专属销售id和数量;
		@SuppressWarnings("unchecked")
		List<PageData> noQuotationRemainInquiry = (List<PageData>)daoSupport.findForList("InquiryReadMapper.getInquiryBrandAndMobile", inquiryID);
		return noQuotationRemainInquiry;
	}

	 
}
