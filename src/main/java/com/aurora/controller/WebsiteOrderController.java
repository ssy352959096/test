package com.aurora.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.aurora.entity.Page;
import com.aurora.service.GoodsManageService;
import com.aurora.service.InquiryService;
import com.aurora.service.OrderManageService;
import com.aurora.util.AppUtil;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.RightsHelper;
import com.aurora.util.Tools;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 网站管理/订单管理
 * 
 * @author BYG 2017/5/25
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/websiteOrder")
public class WebsiteOrderController extends BaseController {

	String menuUrl = "websiteOrder.do"; // 菜单地址(权限用)

	@Resource(name = "orderManageServiceImpl")
	private OrderManageService orderManageServiceImpl;

	/**
	 * 跳转网站管理/订单管理
	 * 
	 * @param Page,orderID ,goodsName,排序orderAD(升序ASC/降序DESC),orderState,beginTime,endTime,customerID
	 * @return
	 */
	@RequestMapping
	public ModelAndView goOrderHandle(Page page, String orderID, String goodsName, String orderAD, String orderState,
			String beginTime, String endTime,String customerID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		Map<String, Object> orderMap = null;
		try {
			
			orderMap = orderManageServiceImpl.queryOrderByS5(page, orderID, goodsName, orderAD, orderState, beginTime, endTime,customerID, pd);
		
		} catch (Exception e) {
			e.printStackTrace();
			msg = "S40089";
			logger.info("【error:网站管理--订单处理条件查询订单执行异常!】");
			throw new Exception(msg);
		}
		mv.addObject("orderMap", orderMap);
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.setViewName("system/websiteManager/orderHandleList");
		return mv;
	}
	
 
	/**
	 * 网站管理--订单处理批量条件导出订单;
	 * @param Page,orderID ,goodsName,排序orderAD(升序ASC/降序DESC),orderState,beginTime,endTime, customerID
	 * @return
	 */
	@RequestMapping(value = "/exportOrder")
	public void exportOrder(HttpServletResponse response,Page page, String orderID, String goodsName, String orderAD, String orderState,
			String beginTime, String endTime,String customerID) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> orderMap = orderManageServiceImpl.queryOrderByS5(page, orderID, goodsName, orderAD, orderState, beginTime, endTime, customerID, pd);
		
		String fileName = DateUtil.getSdfTimes() + ".xls";// 生成文件名;
		response.setContentType("application/x-excel");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

		try {

			WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());
			WritableCellFormat wf = new WritableCellFormat();
			wf.setAlignment(Alignment.CENTRE);
			WritableSheet sheet = null;
			SheetSettings settings = null;
			//1. 只创建一张sheet
			for (int i = 0; i < 1; i++) {
				// 2.创建sheet并设置冻结前两行
				sheet = book.createSheet("订单列表", i);
				settings = sheet.getSettings();
				settings.setVerticalFreeze(1);
				// 3.添加第一行标题数据
				sheet.addCell(new Label(0, 0, "订单号", wf));
				sheet.addCell(new Label(1, 0, "身份证姓名", wf));
				sheet.addCell(new Label(2, 0, "身份证号码", wf));
				sheet.addCell(new Label(3, 0, "收货人姓名", wf));
				sheet.addCell(new Label(4, 0, "联系方式", wf));
				sheet.addCell(new Label(5, 0, "收货人省份", wf));
				sheet.addCell(new Label(6, 0, "收货人城市", wf));
				sheet.addCell(new Label(7, 0, "收货人地区", wf));
				sheet.addCell(new Label(8, 0, "详细地址", wf));
				sheet.addCell(new Label(9, 0, "产品名称", wf));
				sheet.addCell(new Label(10, 0, "商品编码", wf));
				sheet.addCell(new Label(11, 0, "数量", wf));
				if (orderMap != null && orderMap.size() > 0) {

					// 5.将数据添加到单元格中

					int m = 0;
					int n = 0;
					Iterator<Entry<String, Object>> iterator = orderMap.entrySet().iterator();
					while (iterator.hasNext()) {// 对订单遍历
						Entry<String, Object> entry = iterator.next();
						List goodsList = (List) entry.getValue();// 商品列表 List集合;
						Iterator iterator2 = goodsList.iterator();
						while (iterator2.hasNext()) {// 对订单中的商品进行遍历;
							Map<String, Object> goods = (Map<String, Object>) iterator2.next();// 订单中商品的Map集合;
							n = 0;
							m++;
							sheet.addCell(new Label(n++, m, goods.get("order_id") + "", wf));
							sheet.addCell(new Label(n++, m, goods.get("consignee") + "", wf));// 用户身份证真实姓名
							sheet.addCell(new Label(n++, m, goods.get("consignee_id_card") + "", wf));
							sheet.addCell(new Label(n++, m, goods.get("consignee") + "", wf));// 订单收货人
							sheet.addCell(new Label(n++, m, goods.get("consignee_mobile") + "", wf));
							sheet.addCell(new Label(n++, m, goods.get("ship_address") + "", wf));
							sheet.addCell(new Label(n++, m, goods.get("ship_address") + "", wf));
							sheet.addCell(new Label(n++, m, goods.get("ship_address") + "", wf));
							sheet.addCell(new Label(n++, m, goods.get("ship_address") + "", wf));
							sheet.addCell(new Label(n++, m, goods.get("goods_name") + "", wf));
							sheet.addCell(new Label(n++, m, goods.get("goods_code") + "", wf));// 商品条码
							sheet.addCell(new Label(n, m, goods.get("goods_num") + "", wf));
						}
					}  
				}

			} // sheet循环到此结束;

			// 6.写入excel并关闭
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("表格写出失败!执行异常!");

		}

	
	}
//	/**
//	 * 跳转到订单查看详情页面
//	 * 
//	 * @param orderID
//	 * @return
//	 */
//	@RequestMapping(value = "/goOrderSee", produces = "application/json;charset=UTF-8")
//	public ModelAndView goOrderSee(String orderID) throws Exception {
//		ModelAndView mv = this.getModelAndView();
//		String msg = null;
//		List<PageData> orderGoods = new ArrayList<PageData>();
//		if (Tools.notEmptys(orderID)) {
//			try {
//				orderGoods = orderManageServiceImpl.getOrderGoodsByOID(orderID); // 根据订单ID获取订单信息
//			} catch (Exception e) {
//				msg = "";
//				throw new Exception();
//			}
//		} else {
//			msg = "";
//			throw new Exception();
//		}
//		mv.addObject("orderGoods", orderGoods);
//		mv.setViewName("");
//		return mv;
//	}

	/**
	 * 关闭异常订单,恢复已关闭订单
	 * 更新订单状态, /关闭订单orderStateNew==9:老订单状态更新为当前订单状态,订单状态改为9;/恢复正常订单orderStateNew!=9:当前订单状态改为老订单状态;
	 * 
	 * @param orderID,orderStateNew,remark
	 * @return
	 */
	@RequestMapping(value = "/updateOrderState", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object updateOrderState(String orderID, String orderStateNew,String remark) throws Exception {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		if (Tools.notEmptys(orderID)) {
				try {
					pd.put("orderStateNew", orderStateNew);
					pd.put("orderID", orderID);
					pd.put("remark", remark);
					pd.put("time", DateUtil.getSdfMinutes());
					pd.put("operator", Jurisdiction.getUserEmail());
					msg = orderManageServiceImpl.updateOrderShut(pd);
					result = "success".equals(msg) ? "success":"error";
				} catch (Exception e) {
					e.printStackTrace();
					result = "error";
					msg = "S40089";
					logger.info("【error:订单关闭状态更新执行异常!】");
				}
		} else {
			result = "failed";
			msg = "U20054";
			logger.info("【failed:订单orderID参数不正确!】");
		}
		map.put("msg", msg);
		map.put("result", result);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}

	 
}