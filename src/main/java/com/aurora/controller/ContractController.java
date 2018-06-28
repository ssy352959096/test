package com.aurora.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aurora.entity.ContractManage;
import com.aurora.entity.Page;
import com.aurora.entity.Result;
import com.aurora.serviceImpl.ContractServiceImpl;
import com.aurora.serviceImpl.InquiryServiceImpl;
import com.aurora.util.Const;
import com.aurora.util.CustomException;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;
/**
 * @Title: ContractController.java 
 * @Package com.aurora.controller.inquriy 
 * @Description:  合同管理
 * @author SSY  
 * @date 2018年1月10日 上午10:16:19 
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/contractList")
public class ContractController extends BaseController {
//  合同状态:1.合同待上传;2待付款；3已线上付款；4已经线下付款; 5作废；11实付金额与应付金额不等值
 
	@Resource(name="contractServiceImpl")
	private ContractServiceImpl contractServiceImpl;
 
	@Resource(name="inquiryServiceImpl")
	private InquiryServiceImpl inquiryServiceImpl;
 
	/**
	 * 跳转合同列表处理页面
	 * @param  Page page, Integer contractState,String contractID, String beginTime, String endTime
	 * @return contractList,penddingUploadNum,penddingPayNum,todayNum,currency,page ,pd
	 * @author SSY 2018-01-08
	 * @exception Exception
	 */
	@RequestMapping
	public String getContractList(ModelMap modelMap, Page page, Integer contractState, String contractID,String beginTime, String endTime) throws Exception {
		PageData pd = new PageData();	
		pd.put("contractState", contractState);
		pd.put("contractID", Tools.notEmptys(contractID) ? contractID.trim() : null);
		pd.put("beginTime", Tools.notEmpty(beginTime)?beginTime.trim()+" 00:00:00":null);
		pd.put("endTime", Tools.notEmpty(endTime)?endTime.trim()+" 23:59:59":null);
		Integer salesManager = null;
		if (SecurityUtils.getSubject().hasRole(Const.SALES_ROLE_ID)) {//销售 只能操作自己的客户合同;
			salesManager = Integer.valueOf(Jurisdiction.getUserID()) ;
		} 
		pd.put("salesManager", salesManager);
		page.setPd(pd);
		page.setPageSize(10);
		try{
			List<ContractManage> contractList = contractServiceImpl.getContractList(page);
			int totalRecord = contractServiceImpl.getContractNum(page);
			page.setTotalRecord(totalRecord);
			int penddingUploadNum = contractServiceImpl.getContractStateNum(1,salesManager);
			int penddingPayNum = contractServiceImpl.getContractStateNum(2,salesManager);
			int todayNum = contractServiceImpl.getContractDayNum(DateUtil.getDay(),salesManager); // 今天合同单总数量
			List<PageData> currency = inquiryServiceImpl.getCurrency();
			modelMap.put("contractList", JSON.toJSON(contractList));
			modelMap.put("penddingUploadNum", JSON.toJSON(penddingUploadNum));
			modelMap.put("penddingPayNum", JSON.toJSON(penddingPayNum));
			modelMap.put("todayNum", JSON.toJSON(todayNum));
			modelMap.put("currency", JSON.toJSON(currency));
			modelMap.put("pd", JSON.toJSON(pd));
			modelMap.put("page", page);
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("ERROR:"+DateUtil.getTime()+"合同查询执行异常!");
			throw new Exception("查询失败!重试或联系系统管理员!");
		}
		return "system/websiteManager/ContractList";
	}

	
	/**
	 * @Title: getLastContractFile 
	 * @Description:  sourceID查询之前失效合同单; ***注释原因:不需要SSY 2018-01-15
	 * @param String sourceID (也就是sourceID)
	 * @return contractManager
	 * @author SSY
	 * @date 2018年1月10日 下午2:41:33
	 */
	@RequestMapping(value = "/getLastContractFile", produces = "application/json;charset=UTF-8")
	public String getLastContractFileUrl(String sourceID) throws Exception {
		if (!Tools.notEmptys(sourceID)) {
			 return "redirect:/contractList.do";
		}
		Integer salesManager = null;
		if (SecurityUtils.getSubject().hasRole(Const.SALES_ROLE_ID)) {//销售 只能操作自己的客户合同;
			salesManager = Integer.valueOf(Jurisdiction.getUserID()) ;
		} 
		try {
			String lastContractFileUrl = contractServiceImpl.getLastContractFileUrl(sourceID,salesManager);
			if (Tools.notEmptys(lastContractFileUrl)) {
				return "redirect:"+lastContractFileUrl;
			}else{
				return "redirect:/contractList.do";
			}
			
		} catch (Exception e) {
			logger.info("ERROR:"+DateUtil.getTime()+"查询上一份合同文件url系统执行异常!");
			return "redirect:/contractList.do";
		}
		
	}


	/**
	 * 合同文件上传;
	 * @param String contractID,String contractFile
	 * @return result
	 * @author SSY 2018-01-08
	 * @exception Exception
	 */
	@RequestMapping(value = "/uploadContract", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Result<Object> uploadContract(String contractID,String contractFile) throws Exception {
		Result<Object> result = new Result<Object>();
		if (!Tools.notEmptys(contractID)||!Tools.notEmptys(contractFile)) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		Integer salesManager = null;
		if (SecurityUtils.getSubject().hasRole(Const.SALES_ROLE_ID)) {//编码方式,销售角色是18,只能看到自己客户的询价单;
			salesManager = Integer.valueOf(Jurisdiction.getUserID()) ;
		} 
		try {
			int updateNum = contractServiceImpl.updateContractFile(contractID,contractFile,salesManager);
			result.setMsg(updateNum>=1?"操作成功!":"操作失败!没有此合同单!");
			result.setState(updateNum>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		} catch (CustomException ce) {
			result.setMsg(ce.getMessage());
			result.setState(Result.STATE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("操作失败!");
			result.setState(Result.STATE_ERROR);
			logger.info("ERROR:"+DateUtil.getTime()+"合同管理上传合同执行异常!】");
		}
		return result;
	}
	
	
	
	/**
	 * @Title: createContract 
	 * @Description: 更新合同
	 * @param contractManagerJson:{"sourceID":"45645","contractFile":"",
	 * 					"contractGoodsList":[{'goodsName':'测试合同商品更新1','goodsCode':'88888888999','goodsBrand':'测试合同商品品牌',
	 * 				'goodsNorm':'3*62','goodsNum':'1000','goodsMap':'safsaf','deliverCountry':'中国','deliverCity':'杭州','logisticsType':'1',
	 * 				'tradeType':'3','readyTime':'10','goodsPrice':'800','volume':'500','palletVolume':'100','palletPrice':'200','currencySign':'¥',
	 * 				'currencyName':'人民币'}]}
	 * @return Result<Object>  result
	 * @author SSY
	 * @date 2018年1月10日 下午2:48:08
	 */
	@RequestMapping(value = "/createContract", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Result<String> createContract(String contractManagerJson) throws Exception {
		Result<String> result = new Result<String>();
		if (!Tools.notEmptys(contractManagerJson)) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		Integer salesManager = null;
		if (SecurityUtils.getSubject().hasRole(Const.SALES_ROLE_ID)) {//编码方式,销售角色是18, 只能操作自己的客户合同;
			salesManager = Integer.valueOf(Jurisdiction.getUserID()) ;
		} 
		try{
			ContractManage contractManager = JSON.parseObject(contractManagerJson, ContractManage.class);
			String newContractID = contractServiceImpl.addContract(contractManager,salesManager);//验证,原合同失效;新增合同,
			result.setMsg("操作成功!");
			result.setResult(newContractID);
			result.setState(Result.STATE_SUCCESS);
		} catch (CustomException ce) {
			result.setMsg(ce.getMessage());
			result.setState(Result.STATE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("操作失败!");
			result.setState(Result.STATE_ERROR);
			logger.info("ERROR:"+DateUtil.getTime()+"更新合同系统执行异常!");
		}
		return  result;
	}  
	 
	/**
	 * 合同列表管理--已线下付款操作,
	 * param String contractID 
	 * @return result
	 * @author SSY 2018-01-09
	 * @exception Exception
	 */
	@RequestMapping(value = "/updatePayStateContract", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Result<Object> updatePayStateContract(String contractID) throws Exception {
		Result<Object> result = new Result<Object>();
		if (!Tools.notEmptys(contractID)) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		try {
			Integer salesManager = null;
			if (SecurityUtils.getSubject().hasRole(Const.SALES_ROLE_ID)) {//编码方式,销售角色是18,  只能操作自己的客户合同;
				salesManager = Integer.valueOf(Jurisdiction.getUserID()) ;
			} 
			int updateNum = contractServiceImpl.updatePayStateContract(contractID,salesManager);//销售确认已经线下付款 状态4
			result.setMsg(updateNum>=1?"操作成功!":"操作失败!没有此合同单!");
			result.setState(updateNum>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		} catch (CustomException ce) {
			result.setMsg(ce.getMessage());
			result.setState(Result.STATE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("操作失败!");
			result.setState(Result.STATE_ERROR);
			logger.info("ERROR:"+DateUtil.getTime()+"确认合同已经线下付款系统执行异常！】");
		}
		return result;
	}
	
}



