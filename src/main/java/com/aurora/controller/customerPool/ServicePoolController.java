package com.aurora.controller.customerPool;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.controller.BaseController;
import com.aurora.entity.Page;
import com.aurora.service.CustomerPoolService;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 客户池---客服池
 * @author SSY 2017/11/28
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/servicePool")
public class ServicePoolController extends BaseController {

	String menuUrl = "servicePool.do"; // 菜单地址(权限用)

	@Resource(name = "customerPoolServiceImpl")
	private CustomerPoolService customerPoolServiceImpl;

	/**
	 * 客服池页面;
	 * @param  page,name,phone,salesmanID(销售人员id),orderAD(降序"DESC",升序"ASC"),orderBY(购买金额1,分配时间2)
	 * @return
	 */
	@RequestMapping
	public ModelAndView goServicePoolPoolPage(Page page,String name, String phone, String salesmanID,String orderAD,String orderBY) throws Exception {
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
			List<PageData> salesmanList = customerPoolServiceImpl.getSalesman(pd);//获取所有销售代表,包含公池虚拟用户
			page.setTotalRecord(totalRecord);
			mv.addObject("customerPool", customerPool);
			mv.addObject("salesmanList", salesmanList);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CSPC:    系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CSPC: 系统异常!客户池--运营池系统执行异常！】");
			throw new Exception(msg);
		}
		mv.setViewName("system/customerPool/servicePool");
		return mv;
	}

	
	
}
