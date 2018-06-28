package com.aurora.controller.customerPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.controller.BaseController;
import com.aurora.entity.Page;
import com.aurora.service.CustomerPoolService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 客户池---运营池
 * @author SSY 2017/11/28
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/operatorPool")
public class OperatorPoolController extends BaseController {

	String menuUrl = "operatorPool.do"; // 菜单地址(权限用)

	@Resource(name = "customerPoolServiceImpl")
	private CustomerPoolService customerPoolServiceImpl;

	/**
	 * 运营池页面;
	 * @param  page,name,phone,salesmanID(销售人员id),orderAD(降序"DESC",升序"ASC"),orderBY(购买金额1,分配时间2)
	 * @return
	 */
	@RequestMapping
	public ModelAndView goOperatorPoolPage(Page page,String name, String phone, String salesmanID,String orderAD,String orderBY) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String msg = "";
		name = Tools.notEmptys(name)?name.trim():null;
		phone = Tools.notEmptys(phone)?phone.trim():null;
		salesmanID = Tools.notEmptys(salesmanID)?salesmanID.trim():null;
		orderAD = Tools.notEmptys(orderAD)?orderAD.trim():null;
		orderBY = Tools.notEmptys(orderBY)?orderBY.trim():"DESC";
		pd.put("name", name);
		pd.put("phone", phone);
		pd.put("salesmanID", salesmanID);
		pd.put("orderAD", orderAD);
		pd.put("orderBY", orderBY);
		mv.addObject("pd", pd);
		page.setPd(pd);
		page.setPageSize(20);
		try {
			List<PageData> customerPool = customerPoolServiceImpl.getCustomerPool(page);	//查询所有商城客户池列表；
			int totalRecord =  customerPoolServiceImpl.getCustomerPoolNum(pd);//查询商城客户池用户数;
			List<PageData> salesmanList = customerPoolServiceImpl.getSalesman(pd);//查询销售代表,包含公池虚拟用户
			page.setTotalRecord(totalRecord);
			PageData publicPd = new PageData();
			publicPd.put("salesmanID", 888);
			int publicCustomerNum =  customerPoolServiceImpl.getCustomerPoolNum(publicPd);//查询客户池中公池客户数量
			publicPd.put("salesmanID", null);
			int totalCustomerNum =  customerPoolServiceImpl.getCustomerPoolNum(publicPd);//查询客户池所有客户数量
			mv.addObject("customerPool", customerPool);
			mv.addObject("salesmanList", salesmanList);
			mv.addObject("publicCustomerNum", publicCustomerNum);
			mv.addObject("totalCustomerNum", totalCustomerNum);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "COPC:    系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【COPC: 系统异常!客户池--运营池系统执行异常！】");
			throw new Exception(msg);
		}
		mv.setViewName("system/customerPool/operatorPool");
		return mv;
	}

	/**
	 *  运营池页面--- 分配销售,批量操作;
	 * @param customerID(用户id)
	 * @return
	 */
	@RequestMapping(value = "/updateCustomerSalesman")
	@ResponseBody
	public Object updateCustomerSalesman(String customerIDs,String salesmanID) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		String result = "";
		String msg = "";
		if (Tools.notEmptys(customerIDs)&&Tools.notEmptys(salesmanID)) {
			try {
				pd.put("arrayCustomerID",customerIDs.trim().split(","));
				pd.put("salesmanID",Tools.notEmptys(salesmanID)?salesmanID.trim():null);
				pd.put("time", DateUtil.getTime());
				customerPoolServiceImpl.updateCustomerSalesman(pd);//更新客户的销售代表
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "COPC: 操作失败!重试或联系后端管理员!";
				logger.info("【COPC: 系统异常!客户池--运营池分配销售系统执行异常!】");
			}
		}else{
			result = "failed";
			msg = "COPC: 操作失败!重试或联系前端!";
			logger.info("【COPC: 参数错误!客户池--运营池分配销售参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
	
	
}
