package com.aurora.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.entity.Page;
import com.aurora.service.ClientService;
import com.aurora.util.AppUtil;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 
 * @author SSY 2017-08-23
 * @version 1.0
 */
@Controller
@RequestMapping(value="/clientList")
public class ClientController extends BaseController{
	
	String menuUrl = "clientList.do"; //菜单地址(权限用)
	@Resource(name="clientUserServiceImpl")
	private ClientService clientUserServiceImpl;
 
		
	/**条件查询显示客户列表
	 * @param page,keywords(电话号码/用户邮箱),customerStatus用户状态
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView clientList(Page page, String keywords, String customerStatus)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		keywords = Tools.notEmptys(keywords)?keywords.trim():null;
		customerStatus = Tools.notEmptys(customerStatus)?customerStatus.trim():null; //0全部；1在线；2离线；3禁用
		pd.put("customerStatus", customerStatus);
		pd.put("keywords", keywords);
		page.setPd(pd);
		page.setPageSize(10);
		List<PageData>	clientList = null;
		try {
			clientList = clientUserServiceImpl.getClientListByCondition(page);	//列出客户列表
			int totalRecord =clientUserServiceImpl.getClientNumByCondition(page); //客户总数;
			page.setTotalRecord(totalRecord);
			for (Iterator iterator = clientList.iterator(); iterator.hasNext();) {
				PageData pageData = (PageData) iterator.next();
				pageData.put("aurora_remark", pageData.getString("aurora_remark")!=null?pageData.getString("aurora_remark").split("&"):null);
			}
		} catch (Exception e) {
			msg = "CCC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CCC:系统管理--客户列表--查询系统执行异常】");
			throw new Exception(msg);
		}
		mv.addObject("page", page);
		mv.addObject("msg", msg);
		mv.addObject("clientList", clientList);
		mv.addObject("pd", pd);
		mv.setViewName("system/manager/clientList");
		return mv;
	}
	
	/**
	 * 启用/禁用该用户;
	 * @param 用户customerID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateClientState",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object updateClientState(String customerID) throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		if (customerID!=null) {
			try {
				clientUserServiceImpl.updateClientState(customerID);
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CCC: 操作失败!重试或联系后端管理员!";
				logger.info("【CCC:系统管理--客户状态修改系统执行异常】");
			}
		}else{
			result = "failed";
			msg = "CCC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CCC:系统管理--客户状态修改参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
		
	
	
	/**
	 * 备注该用户;
	 *  @param 用户customerID,备注信息remark;
	 */
	@RequestMapping(value="/updateClientRemark",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object updateClientRemark(String customerID,String remark) throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		if (customerID!=null) {
			pd.put("customerID", customerID);
			pd.put("remark", remark);
			try {
				clientUserServiceImpl.updateClientRemark(pd);
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CCC: 操作失败!重试或联系后端管理员!";
				logger.info("【CCC:系统管理--客户备注修改系统执行异常】");
			}
		}else{
			result = "failed";
			msg = "CCC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CCC:系统管理--客户备注修改参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
}
