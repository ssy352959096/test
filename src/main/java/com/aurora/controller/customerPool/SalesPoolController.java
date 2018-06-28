package com.aurora.controller.customerPool;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.controller.BaseController;
import com.aurora.entity.Page;
import com.aurora.entity.User;
import com.aurora.service.CustomerPoolService;
import com.aurora.service.statistics.StatisticsSalesRepService;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 客户池---销售池
 * @author SSY 2017/11/28
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/salesPool")
public class SalesPoolController extends BaseController {

//	String menuUrl = "salesPool.do"; // 菜单地址(权限用)

	@Resource(name = "customerPoolServiceImpl")
	private CustomerPoolService customerPoolServiceImpl;
	@Resource(name = "statisticsSalesRepServiceImpl")
	private StatisticsSalesRepService statisticsSalesRepServiceImpl;

	/**
	 * 销售池页面;
	 * @param  page,name,phone,salesmanID(销售人员id),orderAD(降序"DESC",升序"ASC"),orderBY(购买金额1,分配时间2)
	 * @return
	 */
	@RequestMapping
	public ModelAndView goSalesPoolPage(Page page,String name, String phone, String salesmanID,String orderAD,String orderBY) throws Exception {
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
		User salesman = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		pd.put("roleID", salesman.getUserRoleID());
		pd.put("userID", salesman.getUserID());
		pd.put("orderAD", orderAD);
		pd.put("orderBY", orderBY);
		mv.addObject("pd", pd);
		page.setPd(pd);
		page.setPageSize(20);
		try {
			List<PageData> customerPool = customerPoolServiceImpl.getCustomerPool(page);	//查询所有商城客户池列表；
			int totalRecord =  customerPoolServiceImpl.getCustomerPoolNum(pd);//查询商城客户池用户数;
			List<PageData> salesmanList = customerPoolServiceImpl.getSalesman(pd);//根据角色查询销售代表,包含公池虚拟用户;如果是销售的代表就只能获取自己和公池虚拟销售代表
			page.setTotalRecord(totalRecord);
			Map<String, String> totalSales = statisticsSalesRepServiceImpl.getTotalSales(pd);//统计该销售人员业绩,时间,销售员id;
			 
			mv.addObject("customerPool", customerPool);
			mv.addObject("salesmanList", salesmanList);
			mv.addObject("totalSales", totalSales);//该销售代表各阶段的业绩;
			mv.addObject("registerHttps", "https://www.aurorascm.com/registerLogin/goRegister?salasManager="+salesman.getUserID());//该销售代表各阶段的业绩;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CSPC:    系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CSPC: 系统异常!客户池--运营池系统执行异常！】");
			throw new Exception(msg);
		}
		mv.setViewName("system/customerPool/salesPool");
		return mv;
	}

	
	
}
