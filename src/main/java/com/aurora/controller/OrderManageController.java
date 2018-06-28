package com.aurora.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.aurora.entity.Page;
import com.aurora.service.OrderManageService;
import com.aurora.util.AppUtil;
import com.aurora.util.CustomException;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.MyDataException;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 订单管理/所有订单
 * 
 * @author SSY 2017/8/11
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/orderList")
public class OrderManageController extends BaseController {

	String menuUrl = "orderList.do"; // 菜单地址(权限用)

	@Resource(name = "orderManageServiceImpl")
	private OrderManageService orderManageServiceImpl;

	/**
	 * 跳转订单管理/所有订单
	 * @param Page
	 *            orderID/goodsName,orderType(订单类型：1微仓；2非微仓),
	 *            tradeType(贸易方式：1保税仓；2海外直邮；3国内现货),
	 *            orderState, beginTime, endTime
	 *            customerID(用户名id 账号),String customerEmail,String customerMobile
	 * @return 
	 */
	@RequestMapping
	public ModelAndView goOrderList(Page page, String orderID, String goodsName, String orderType, String tradeType, String payType,
			  String orderState, String beginTime, String endTime,String customerID,String customerEmail,String customerMobile) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		try {
			Map<String, Object> orderMap = orderManageServiceImpl.queryOrder(page, orderID, goodsName, orderType, tradeType, payType,
					orderState, beginTime, endTime, customerID,customerEmail, customerMobile,pd);
			int todayONum = orderManageServiceImpl.getTodayONum(DateUtil.getDay()); // 获取今天下单数量
			int pendingPayONum = orderManageServiceImpl.getONumByState("1"); // 获取待付款订单数量
			int pendingSendONum = orderManageServiceImpl.getONumByState("6"); // 获取待发货订单数量
			int pendingRefundONum = orderManageServiceImpl.getONumByState("4"); // 获取待退款订单数量
			String todayTurnover = orderManageServiceImpl.getTodayTurnover(DateUtil.getDay()); // 获取今天营业额(只计算正常已付款订单)
			//回显物流公司信息;
			List<PageData> logisticsCompany  = orderManageServiceImpl.getLogisticsCompany();
			mv.addObject("todayONum", todayONum);
			mv.addObject("pendingPayONum", pendingPayONum);
			mv.addObject("pendingSendONum", pendingSendONum);
			mv.addObject("pendingRefundONum", pendingRefundONum);
			mv.addObject("todayTurnover", todayTurnover);
			mv.addObject("orderMap", orderMap);
			mv.addObject("logisticsCompany", logisticsCompany);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "COMC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【COMC:订单管理--订单列表查询执行异常！】");
			throw new Exception(msg);
		}
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.addObject("msg", msg);
		mv.setViewName("system/orderManager/orderList");
		return mv;
	}

	/**
	 * 根据条件导出订单数据;
	 * 
	 * @param Page
	 *            orderID/goodsName,orderType(订单类型：1微仓；2非微仓),
	 *            tradeType(贸易方式：1保税仓；2海外直邮；3国内现货),payType(付款方式:1全款支付；其他值定金付款，值为定金比例),
	 *            orderState, beginTime, endTime
	 *           	customerName(用户名或者用户邮箱)String customerEmail,String customerMobile
	 * @return
	 */
	@RequestMapping(value = "/exportOrder")
	public void exportOrder(HttpServletResponse response, Page page, String orderID, String goodsName, String orderType,
			String tradeType, String payType, String orderState, String beginTime, String endTime,String customerID,String customerEmail,String customerMobile) throws Exception {
		page = null;
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> orderMap = orderManageServiceImpl.queryOrder(page, orderID, goodsName, orderType, tradeType,payType,
				 orderState, beginTime, endTime, customerID,customerEmail, customerMobile, pd);

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
				sheet.addCell(new Label(3, 0, "联系电话", wf));
				sheet.addCell(new Label(4, 0, "收货地址", wf));
				sheet.addCell(new Label(5, 0, "产品名称", wf));
				sheet.addCell(new Label(6, 0, "商品条码", wf));
				sheet.addCell(new Label(7, 0, "本站商品id", wf));
				sheet.addCell(new Label(8, 0, "数量", wf));
				sheet.addCell(new Label(9, 0, "商品单价", wf));
				sheet.addCell(new Label(10, 0, "运单号", wf));
				sheet.addCell(new Label(11, 0, "快递公司", wf));
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
							@SuppressWarnings("unchecked")
							Map<String, Object> goods = (Map<String, Object>) iterator2.next();// 订单中商品的Map集合;
							n = 0;
							m++;
							sheet.addCell(new Label(n++, m, goods.get("order_id") + "", wf));//1.订单号
							sheet.addCell(new Label(n++, m, goods.get("consignee") + "", wf));//2.身份证姓名
							sheet.addCell(new Label(n++, m, goods.get("consignee_id_card") + "", wf));//3.身份证号码
							sheet.addCell(new Label(n++, m, goods.get("consignee_mobile") + "", wf));//4.联系电话
							sheet.addCell(new Label(n++, m, goods.get("ship_address") + "", wf));//5.收货地址
							sheet.addCell(new Label(n++, m, goods.get("goods_name") + "", wf));//6.产品名称
							sheet.addCell(new Label(n++, m, goods.get("goods_code") + "", wf));//7.商品条码
							sheet.addCell(new Label(n++, m, goods.get("goods_id") + "", wf));//8.本站商品id
							sheet.addCell(new Label(n++, m, goods.get("goods_num") + "", wf));//9.数量
							sheet.addCell(new Label(n++, m, goods.get("goods_price") + "", wf));//10.商品单价
							sheet.addCell(new Label(n++, m, goods.get("logistics_number") + "", wf));//11.运单号
							sheet.addCell(new Label(n, m, goods.get("logistics_company") + "", wf));//12.快递公司
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

	/**
	 * 跳转到订单查看详情页面
	 * 
	 * @param orderID
	 * @return
	 */
	@RequestMapping(value = "/goOrderDetail", produces = "application/json;charset=UTF-8")
	public ModelAndView goOrderDetail(String orderID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		String msg = "";
		List<PageData> orderGoods = new ArrayList<PageData>();
		if (orderID != null && !"".equals(orderID)) {
			try {
				orderGoods = orderManageServiceImpl.getOrderGoodsByOID(orderID); // 根据订单ID获取订单信息
				//物流公司信息;
				List<PageData> logisticsCompany  = orderManageServiceImpl.getLogisticsCompany();
				mv.addObject("logisticsCompany", logisticsCompany);
			} catch (Exception e) {
				msg = "COMC: 系统可能走神了,刷新重试或联系后端管理员!";
				logger.info("【COMC: 系统异常!订单管理列表--查看订单详情--订单回显系统执行异常!】");
				throw new Exception(msg);
			}
		} else {
			msg = "COMC: 系统可能走神了,刷新重试或联系络驿吴彦祖!";
			logger.info("【COMC: 参数错误!订单管理列表--查看订单详情--订单参数错误!】");
			throw new Exception(msg);
		}
		mv.addObject("orderGoods", orderGoods);
		mv.addObject("msg", msg);
		mv.setViewName("system/orderManager/orderDetail");
		return mv;
	}

	/**
	 * 更新订单备注
	 * @return
	 */
	@RequestMapping(value = "/updateRemark", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object updateRemark() throws Exception {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String orderID = pd.getString("orderID") != null && !"".equals(pd.getString("orderID").trim())
				? pd.getString("orderID").trim() : null; // 订单ID
		String remark = pd.getString("remark") != null && !"".equals(pd.getString("remark").trim())
				? pd.getString("remark").trim() : null;
		pd.put("orderID", orderID);
		pd.put("remark", remark);
		if (orderID != null && remark != null) {
			try {
				orderManageServiceImpl.updateRemark(pd); // 根据订单ID更新订单备注
				result = "success";
			} catch (Exception e) {
				msg = "COMC: 操作失败!重试或联系后端管理员!";
				result = "error";
				logger.info("【COMC: 系统异常!更新订单备注系统执行异常!】");
			}
		} else {
			msg = "COMC: 操作失败!重试或联系络驿吴彦祖!";
			result = "failed";
			logger.info("【COMC: 参数错误,更新订单备注--参数错误!】");
		}
		map.put("msg", msg);
		map.put("result", result);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 海外直邮订单商品,接单之前,先录入商品总成本
	 */
	@RequestMapping(value = "/orderHGCost", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object orderHGCost(String goodsCost) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		if (Tools.notEmptys(goodsCost)) {
			try {
				orderManageServiceImpl.updateOHGCost(goodsCost); // 根据订单ID和商品id修改海外直邮的商品总成本;
				result = "success";
			} catch (Exception e) {
				msg = "COMC: 操作失败!重试或联系后端管理员!";
				result = "error";
				logger.info("COMC: 系统异常!接单--成本录入执行异常!");
			}	
		}else{
			msg = "COMC: 操作失败!重试或联系络驿吴彦祖!";
			result = "failed";
			logger.info("COMC: 参数错误!接单--成本录入--参数错误!");
		}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
	
	
	/**
	 * 接单orderState=2--->6；若是微仓订单同步到微仓;
	 * 若是微仓订单,直接确认收货走完流程;
	 * @param orderID
	 * @return
	 */
	@RequestMapping(value = "/orderReceiving", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object orderReceiving(String orderID) throws Exception {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		orderID = Tools.notEmptys(orderID) ? orderID.trim() : null; // 订单ID
		if (orderID != null) {
			try {
				orderManageServiceImpl.updateOrderReceiving(orderID); // 根据订单ID修改订单状态为接单orderState=6；若是微仓订单同步到微仓
				result = "success";
			} catch (CustomException ce) {
				msg = "商品库存不足,接单失败!";
				result = "failed";
			} catch (MyDataException me) {
				msg = "接单失败!订单状态错误!";
				result = "failed";
			} catch (Exception e) {
				e.printStackTrace();
				msg = "COMC: 操作失败!重试或联系后端管理员!";
				result = "error";
				logger.info("【COMC: 参数错误!接单--成本录入--参数错误!】");
			}
		} else {
			msg = "COMC: 操作失败!重试或联系络驿吴彦祖!";
			result = "failed";
			logger.info("【COMC:订单管理--订单编号参数不正确!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 北极光退款(客户取消付款订单状态为4)orderState=4---->5
	 * (微仓发出订单不退)
	 * @param orderID
	 * @return
	 */
	@RequestMapping(value = "/refund", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object refund(String orderID) throws Exception {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		orderID = Tools.notEmptys(orderID) ? orderID.trim() : null; // 订单ID
		if (orderID != null) {
			try {
				pd.put("orderID", orderID.trim());
				msg = orderManageServiceImpl.updateAuroraRefund(pd); // 
				result = "success".equals(msg)?"success":"failed";
			} catch (Exception e) {
				e.printStackTrace();
				msg = "COMC: 操作失败!重试或联系后端管理员!";
				result = "error";
				logger.info("【COMC: 订单管理--订单状态修改系统异常!】");
			}
		} else {
			msg = "COMC: 操作失败!重试或联系络驿吴彦祖!";
			result = "failed";
			logger.info("【COMC:订单管理--订单编号参数不正确!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 发货/修改物流信息--订单信息回显;
	 * 回显物流公司
	 * 订单状态orderState = 6已接单等待发货,7修改物流信息;
	 * 订单orderId,物流信息(地址ship_address+姓名consignee+手机号码consignee_mobile)商品种数goods_id_num;
	 * 订单下的所有商品goods_id,商品名goods_name,商品数量goods_num,发货方式logistics_type(1物流,2无需物流),物流公司logistics_company,
	 * 物流单号logistics_number(逗号拼接);
	 * @param orderID
	 * @return
	 */
	@RequestMapping(value = "/sendOutGoods", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object sendOutGoods(String orderID) throws Exception {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		List<PageData> orderGoods = null;
		String orderState = "";
		orderID = Tools.notEmptys(orderID) ? orderID.trim() : null; // 订单ID
		if (orderID != null) {
			try {
				// 当前订单状态;
				orderState = orderManageServiceImpl.getOrderState(orderID);
				if (orderState != null && (Integer.valueOf(orderState) == 6 || Integer.valueOf(orderState) == 7)) {
					pd.put("orderID", orderID);
					pd.put("orderState", orderState);// 6代发货,7修改物流信息
					orderGoods = orderManageServiceImpl.getOrderGoodsByOID(orderID); // 根据订单id查询订单里的所有的商品信息;
					pd.put("orderGoods", orderGoods);
					result = "success";
				} else {
					msg = "COMC: 操作失败!重试或联系后端管理员!";
					result = "failed";
					logger.info("【COMC: 订单管理--订单状态异常!发货回显操作失败!】");
				}
			} catch (Exception e) {
				msg = "COMC: 操作失败!重试或联系后端管理员!";
				result = "error";
				logger.info("【COMC: 订单管理--发货回显操作订单查询系统执行异常!】");
			}
		} else {
			msg = "COMC: 操作失败!重试或联系络驿吴彦祖!";
			result = "failed";
			logger.info("【COMC: 订单管理--订单编号参数不正确!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
 
	/**
	 * 保存物流信息orderOldState=6,orderState=7 发货方式logistics_type(1物流,2无需物流),
	 * @param orderID goodsList(logisticsCode,物流公司编号) 
	 * @return
	 */
	@RequestMapping(value = "/saveLogistics", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object saveLogistics() throws Exception {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String orderID = Tools.notEmptys(pd.getString("orderID")) ? pd.getString("orderID").trim() : null; // 订单ID
		String orderState = "";
		if (orderID != null) {
			try {
				 @SuppressWarnings("unchecked")
				List<Map<String, String>> goodsList = JSONArray.parseObject(pd.getString("goodsList"), List.class);
				// 当前订单状态;
				orderState = orderManageServiceImpl.getOrderState(orderID);
				if (orderState != null && (Integer.valueOf(orderState) == 6 || Integer.valueOf(orderState) == 7)) {
					pd.put("orderID", orderID);
					orderManageServiceImpl.updateLogistics(pd, goodsList, orderState); // 保存该订单发货信息;
					result = "success";
				} else {
					msg = "COMC: 操作失败!请检查订单状态!";
					result = "failed";
					logger.info("【COMC: 订单状态异常!发货信息保存失败!】");
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg = "COMC: 操作失败!重试或联系后端管理员!";
				result = "error";
				logger.info("【COMC:订单管理--订单发货信息保存异常!】");
			}
		} else {
			msg = "COMC: 操作失败!重试或联系络驿吴彦祖!";
			result = "failed";
			logger.info("【COMC:订单管理--保存发货信息参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 后台人工强制--确认收货orderState=8 
	 */
	@RequestMapping(value = "/confirmReceipt", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object confirmReceipt(String orderID) throws Exception {
//	orderID  = "2017090916070010019";
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		if (Tools.notEmptys(orderID)) {
			try {
				msg = orderManageServiceImpl.updateConfirmReceipt(orderID); // 
				result = "success".equals(msg)?"success":"failed";
			} catch (Exception e) {
				msg = "COMC: 操作失败!重试或联系后端管理员!";
				result = "error";
			}
		}else{
			msg = "COMC: 操作失败!重试或联系络驿吴彦祖!";
			result = "failed";
		}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
}
