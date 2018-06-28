package com.aurora.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aurora.entity.InquiryGoods;
import com.aurora.entity.InquiryManage;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.service.InquiryService;
import com.aurora.serviceImpl.InquiryServiceImpl;
import com.aurora.util.AliyunSMS;
import com.aurora.util.Const;
import com.aurora.util.CustomException;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * @Title: InquiryController.java 
 * @Package com.aurora.controller.inquriy 
 * @Description: 询价控制器
 * @author SSY  
 * @date 2018年1月10日 上午10:15:57 
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/inquiryList")
public class InquiryController extends BaseController {
 
	@Resource(name="inquiryServiceImpl")
	private InquiryService inquiryServiceImpl;
	/**
	 * @Title: getInquiryList 
	 * @Description:  跳转询价处理页面
	 * @param  Page page,  String inquiryID, Integer inquiryState(1.报价未提交;2报价已提交;3.已失效), String beginTime, String endTime, 
	 * @return inquiryList,pendingNum,finishNum,todayNum,currency,page ,pd
	 * @author SSY
	 * @date 2018年1月10日 下午1:35:57
	 */
	@RequestMapping
	public String getInquiryList(ModelMap modelMap, Page page, String inquiryID,Integer inquiryState, String beginTime, String endTime) throws Exception {
		PageData pd = this.getPageData();
		Integer salesManager = null;
		if (SecurityUtils.getSubject().hasRole(Const.SALES_ROLE_ID)) {//销售只能看到自己客户的询价单;
			salesManager = Integer.valueOf(Jurisdiction.getUserID()) ;
		} 
		pd.put("salesManager", salesManager);
		pd.put("inquiryState", inquiryState);
		pd.put("inquiryID", Tools.notEmpty(inquiryID)?inquiryID.trim():null);
		pd.put("beginTime", Tools.notEmpty(beginTime)?beginTime.trim()+" 00:00:00":null);
		pd.put("endTime", Tools.notEmpty(endTime)?endTime.trim()+" 23:59:59":null);
		page.setPageSize(10);
		page.setPd(pd);
		try {
			List<InquiryManage> inquiryList = inquiryServiceImpl.getInquiryList(page);
			int totalRecord = inquiryServiceImpl.getInquiryNum(page);
			page.setTotalRecord(totalRecord);
			int	pendingNum = inquiryServiceImpl.getInquiryStateNum(1,salesManager); // 待报价询价单总数量
			int finishNum = inquiryServiceImpl.getInquiryStateNum(2,salesManager); // 已报价询价单总数量(状态：1未报；2已报。)
			int todayNum = inquiryServiceImpl.getInquiryDayNum(DateUtil.getDay(),salesManager); // 今天询价单总数量
			List<PageData> currency = inquiryServiceImpl.getCurrency();
			modelMap.put("inquiryList", JSON.toJSON(inquiryList));
			modelMap.put("pendingNum", JSON.toJSON(pendingNum));
			modelMap.put("finishNum", JSON.toJSON(finishNum));
			modelMap.put("todayNum", JSON.toJSON(todayNum));
			modelMap.put("currency", JSON.toJSON(currency));
			modelMap.put("pd", JSON.toJSON(pd));
			modelMap.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("ERROR:"+DateUtil.getTime()+"询价查询执行异常!");
			throw new Exception("查询失败!重试或联系系统管理员!");
		}
		return "system/websiteManager/inquirysList";
	}

	/**
	 * @Title: getInquiryGoods 
	 * @Description: 更新报价单 ,回显询价商品信息;
	 * @param String inquiryID,String inquiryGoodsID
	 * @return result.inquiryGoods 
	 * @author SSY
	 * @date 2018年1月10日 下午1:35:37
	 */
	@RequestMapping(value = "/getInquiryGoods", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Result<InquiryGoods> getInquiryGoods(String inquiryGoodsID,String inquiryID) throws Exception {
		Result<InquiryGoods> result = new Result<InquiryGoods>();
		if (!Tools.notEmptys(inquiryGoodsID)||!Tools.notEmptys(inquiryID)) {
			result.setMsg("你长点心吧!");
			result.setState(Result.STATE_FAILED);
			return result;
		} 
		try {
			Integer salesManager = null;
			if (SecurityUtils.getSubject().hasRole(Const.SALES_ROLE_ID)) {//编码方式,销售角色是18,只能看到自己客户的询价单;
				salesManager = Integer.valueOf(Jurisdiction.getUserID()) ;
			} 
			InquiryGoods inquiryGoods = inquiryServiceImpl.getInquiryGoods(inquiryGoodsID,inquiryID,salesManager); 
			result.setResult(inquiryGoods);
			result.setMsg("操作成功!");
			result.setState(Result.STATE_SUCCESS);
		} catch (CustomException ce) {
			result.setMsg(ce.getMessage());
			result.setState(Result.STATE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("操作失败!重试或联系系统管理员");
			result.setState(Result.STATE_ERROR);
			logger.info("ERROR:"+DateUtil.getTime()+"查询询价商品执行异常!");
		}
		return result;
	}

	/**
	 * @Title: updateInquiryGoods 
	 * @Description: 单个商品报价信息保存/更新
	 *  @param    inquiryGoodsJson:{'inquiryID':'','inquiryGoodsID':'', 'goodsCode':'241551','updateNum':'52','deliverCountry':'中国','deliverCity':'杭州',
	 * 				'logisticsType':'1'(1海2陆3空),'tradeType':'3'(贸易方式：1CIF;2FCA;3EXW;4.FOB;),'currencySign':'¥','currencyName':'人民币', volume(商品体积),palletVolume(托盘体积),
	 * 				palletPrice(一坨运费),readyTime(备货天数),goodsPrice(商品单价) ,inquiryGoodsState(2.已经报价;3表示无法报价),validTime(有效期)
	 * 			}
	 * @return Result<Object>  result
	 * @author SSY
	 * @date 2018年1月10日 下午1:35:17
	 */
	@RequestMapping(value = "/updateInquiryGoods", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Result<Object> updateInquiryGoods(String inquiryGoodsJson) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			InquiryGoods inquiryGoods = JSON.parseObject(inquiryGoodsJson, InquiryGoods.class);
			Integer salesManager = null;
			if (SecurityUtils.getSubject().hasRole(Const.SALES_ROLE_ID)) {//编码方式,销售角色是18,只能看到自己客户的询价单;
				salesManager = Integer.valueOf(Jurisdiction.getUserID()) ;
			}
			inquiryGoods.setInputor(Jurisdiction.getUserEmail());
			inquiryGoods.setInputTime(DateUtil.getTime());
			inquiryServiceImpl.updateInquiryGoods(inquiryGoods,salesManager); // 根据询价ID获取询价商品信息
			result.setMsg("操作成功!");
			result.setState(Result.STATE_SUCCESS);
		} catch (CustomException ce) {
			result.setMsg(ce.getMessage());
			result.setState(Result.STATE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("操作失败!");
			result.setState(Result.STATE_ERROR);
		}
		return result;
	}

	/**
	 * @Title: commitInquriy 
	 * @Description: 提交报价单给客户
	 * @param    String inquiryID
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年1月10日 下午1:34:45
	 */
	@RequestMapping(value = "/commitInquriy", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Result<Object> commitInquriy(String inquiryID) throws Exception {
		Result<Object> result = new Result<Object>();
		try {
			Integer salesManager = null;
			InquiryManage inquiryManage = new InquiryManage();
			inquiryManage.setInquiryID(inquiryID);
			inquiryManage.setInquiryState(2);
			inquiryManage.setOfferer(Jurisdiction.getUserEmail());
			inquiryManage.setOfferTime(DateUtil.getTime());
			inquiryManage.setSalesManager(salesManager);
			int updateNum = inquiryServiceImpl.updateInquriyState(inquiryManage);
			result.setMsg(updateNum>=1?"操作成功!":"操作失败!没有此询价单!");
			result.setState(updateNum>=1?Result.STATE_SUCCESS:Result.STATE_FAILED);
			//询价报价后给客户发送短信通知
			List<PageData> brandAndMobile= inquiryServiceImpl.getInquiryBrandAndMobile(inquiryID);
			String mobile = brandAndMobile.get(0).getString("customer_mobile");
			StringBuilder brand = new StringBuilder();
			for (PageData b : brandAndMobile) {
				brand.append(b.get("goods_brand"));
			}
			String templateParam = brand.toString();
			AliyunSMS.sendSMS(AliyunSMS.getTemplatecode1(), mobile, templateParam);
			
		} catch (CustomException ce) {
			result.setMsg(ce.getMessage());
			result.setState(Result.STATE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("操作失败!");
			result.setState(Result.STATE_ERROR);
		}
		return result;
	}
	
	
}
