package com.aurora.controller.statistics;

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
import com.aurora.service.statistics.StatisticsCustomerService;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 数据统计---顾客信息
 * @author SSY 2017/11/16
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statisticsCustomer")
public class StatisticsCustomerController extends BaseController {

	String menuUrl = "statisticsCustomer.do"; // 菜单地址(权限用)

	@Resource(name = "statisticsCustomerServiceImpl")
	private StatisticsCustomerService statisticsCustomerServiceImpl;

	/**
	 * 跳转到数据统计 -- 客户数据分析页面---客户列表;
	 * @param name(姓名)  phone(手机) orderAD(排序规则), orderBY(排序字段), page(分页)
	 * @return
	 */
	@RequestMapping
	public ModelAndView goStatisticsCustomerPage(Page page, String phone,String name, String orderAD,String orderBY) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		pd.put("phone", Tools.notEmptys(phone)?phone.trim():null);
		pd.put("name", Tools.notEmptys(name)?name.trim():null);
		pd.put("orderAD", Tools.notEmptys(orderAD)?orderAD.trim():"DESC");
		pd.put("orderBY", Tools.notEmptys(orderBY)?orderBY.trim():null);
		
		page.setPd(pd);
		page.setPageSize(20);
		List<PageData>	customerList = null;
		try {
			customerList = statisticsCustomerServiceImpl.getCustomerList(page);	//条件列出客户列表
			int totalRecord =statisticsCustomerServiceImpl.getCustomerNum(page); //条件查询客户总数;
			page.setTotalRecord(totalRecord);
			int newMember = statisticsCustomerServiceImpl.getRegisterCustomerNum(DateUtil.getAfterDay(-1));
			int newMemberLastMonth = statisticsCustomerServiceImpl.getRegisterCustomerNum(DateUtil.getAfterMonth(-1));
			int totalMember = statisticsCustomerServiceImpl.getRegisterCustomerNum("20");//总注册会员.至少能用到本世纪末;
			mv.addObject("newMember", newMember);//昨日新增会员数
			mv.addObject("newMemberLastMonth", newMemberLastMonth);//上月新增会员数
			mv.addObject("totalMember", totalMember);//总注册会员
			//总注册会员数
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CSCC:    系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CSGC: 系统异常!数据统计--用户统计系统执行异常！】");
			throw new Exception(msg);
		}
		mv.addObject("customerList", customerList);//客户列表
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.setViewName("system/statistics/customerList");
		return mv;
	}

	/**
	 * 统计数据--用户列表---查看明细;
	 * @param customerID(用户id)  
	 * @return
	 */
		@RequestMapping(value="/getCustomerBehaviorData")
		@ResponseBody
		public Object getCustomerBehaviorData(String customerID) throws Exception {
			PageData pd = new PageData();
			Map<String,Object> map = new HashMap<String,Object>();
			String result = "";
			String msg = "";
			pd = this.getPageData();
			if (Tools.notEmptys(customerID)) {
				try {
					//查询该用户的微仓周转时间,点击类目比例, 购买类目比例,登录时间分布;
					Map<String,Object> customerBehaviorData = statisticsCustomerServiceImpl.getCustomerBehaviorData(customerID); 
					map.put("customerBehaviorData", customerBehaviorData);
					result = "success";
				} catch (Exception e) {
					e.printStackTrace();
					result = "error";
					msg = "CSCC: 操作失败!重试或联系后端管理员!";
					logger.info("【CSCC: 系统异常!数据统计--客户分析明细查看--系统执行异常!】");
				}
			}else{
				result = "failed";
				msg = "CSCC: 操作失败!请稍后重试或联系络驿吴彦祖!";
			}
			map.put("result", result);
			map.put("msg", msg);
			map.put("pd", pd);
			return map;
		}
	
	
}
