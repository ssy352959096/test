package com.aurora.serviceImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aurora.dao.DAO;
import com.aurora.entity.Page;
import com.aurora.service.OrderManageService;
import com.aurora.util.CustomException;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.MyDataException;
import com.aurora.util.PageData;
import com.aurora.util.Tools;
import com.aurora.util.KdniaoTrackQuery;

/**
 * 描述:客户登录注册ServiceImpl 创建:BYG 2017/5/25 修改:
 * 
 * @version 1.0
 */

@Service("orderManageServiceImpl")
public class OrderManageServiceImpl implements OrderManageService {

	@Resource(name = "daoSupport")
	private DAO daoSupport;

	/**
	 * 从数据库中条件查询订单列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	public Map<String, Object> queryOrder(Page page, String orderID, String goodsName, String orderType,
			String tradeType, String payType, String orderState, String beginTime, String endTime, String customerID,String customerEmail,String customerMobile,PageData pd)
			throws Exception {
		Map<String, Object> orderMap = new LinkedHashMap<String, Object>();
		orderID = Tools.notEmptys(orderID) ? orderID.trim() : null; // 订单ID
		goodsName = Tools.notEmptys(goodsName) ? goodsName.trim() : null; // 商品ID
		orderType = Tools.notEmptys(orderType) ? orderType.trim() : null; // 订单类型：1微仓；2非微仓。
		tradeType = Tools.notEmptys(tradeType) ? tradeType.trim() : null; // 贸易方式：1保税仓；2海外直邮；3国内现货。
		payType = Tools.notEmptys(payType) ? payType.trim() : null; // 付款方式：1保税仓；2海外直邮；3国内现货。
		orderState = Tools.notEmptys(orderState) ? orderState.trim() : null; // 订单状态：1待付款，2已付款，3客户取消订单，4客户取消订单，待退款，5北极光退款--已退款，6已接单--等待发货，7已发货--物流运输中，8买家收到货，交易完成，9北极光异常关闭订单，同时退款。
		customerID = Tools.notEmptys(customerID) ? customerID.trim() : null; //用户id
		customerEmail = Tools.notEmptys(customerEmail) ? customerEmail.trim() : null; //用户id
		customerMobile = Tools.notEmptys(customerMobile) ? customerMobile.trim() : null; //用户id
		String[] orderStateList = null;
		if (orderState != null) {
			orderStateList = orderState.split(",");
		}
		beginTime = Tools.notEmptys(beginTime) ? beginTime.trim() + " 00:00:00" : null; // 下单时间
		endTime = Tools.notEmptys(endTime) ? endTime.trim() + " 23:59:59" : null; // 下单时间,截止当天23点23分23秒
		pd.put("orderID", orderID);
		pd.put("goodsName", goodsName);
		pd.put("orderType", orderType);
		pd.put("tradeType", tradeType);
		pd.put("payType", payType);
		pd.put("orderStateList", orderStateList);
		pd.put("orderState", orderState);
		pd.put("beginTime", beginTime);
		pd.put("endTime", endTime);
		pd.put("customerID", customerID);
		pd.put("customerEmail", customerEmail);
		pd.put("customerMobile", customerMobile);
		if (page == null) {
			page = new Page();
			page.setPageSize(1000);//最大一次导出1000条;
			beginTime = Tools.notEmptys(beginTime) ? beginTime.trim() + " 00:00:00" : DateUtil.getDay() +" 00:00:00"; // 下单时间
			endTime = Tools.notEmptys(endTime) ? endTime.trim() + " 23:59:59" : DateUtil.getDay() + " 23:59:59"; // 下单时间,截止当天23点23分23秒
			pd.put("beginTime", beginTime);
			pd.put("endTime", endTime);
			pd.put("export", "true");
		} else {
			pd.put("export", "false");
		}
		page.setPd(pd);
		page.setPageSize(10);
		if (orderID != null) {
			List<PageData> orderGoods = getOrderGoodsByOID(orderID); // 根据定单ID获取该订单所有信息
			orderMap.put("orderID", orderGoods);
			page.setTotalRecord(1);
		} else {
			List<PageData> orderIDList = getOrderIDByS8(page); // 根据商品ID等条件获取订单ID
			int orderIDNum = getOrderIDNumByS8(page);
			page.setTotalRecord(orderIDNum);
			for (PageData o : orderIDList) {
				List<PageData> orderGoods = getOrderGoodsByOID(o.getString("order_id")); // 根据定单号获取定单包含的商品
				// 以下分割一下备注为数组;
				for (PageData pd2 : orderGoods) {
					pd2.put("customer_remark", Tools.notEmptys(pd2.getString("customer_remark"))
							? pd2.getString("customer_remark").split("&") : null);
					pd2.put("aurora_remark", Tools.notEmptys(pd2.getString("aurora_remark"))
							? pd2.getString("aurora_remark").split("&") : null);
				}
				orderMap.put(o.getString("order_id"), orderGoods);
			}
		}
		pd.remove("orderStateList");
		return orderMap;
	}

	/**
	 * 根据订单单号获取订单包含商品
	 * 
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getOrderGoodsByOID(String orderID) throws Exception {
		return (List<PageData>) daoSupport.findForList("OrderReadMapper.getOrderGoodsByOID", orderID);
	}

	/**
	 * 网站管理--订单处理(条件查询)
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 * @author SSY
	 */
	@Override
	public Map<String, Object> queryOrderByS5(Page page, String orderID, String goodsName, String orderAD,
			String orderState, String beginTime, String endTime, String customerID,PageData pd) throws Exception {

		orderID = Tools.notEmptys(orderID) ? orderID.trim() : null;
		goodsName = Tools.notEmptys(goodsName) ? goodsName.trim() : null;
		orderState = Tools.notEmptys(orderState) ? orderState.trim() : null; // 订单状态：9为关闭订单。
		beginTime = Tools.notEmptys(beginTime) ? beginTime.trim() + " 00:00:00" : null; // 下单时间
		endTime = Tools.notEmptys(endTime) ? endTime.trim() + " 23:59:59" : null; // 下单时间,截止当天23点23分23秒
		orderAD = Tools.notEmptys(orderAD) ? orderAD.trim() : "DESC";
		customerID = Tools.notEmptys(customerID) ? customerID.trim() : null; //用户邮箱或者姓名
		pd.put("orderID", orderID);
		pd.put("goodsName", goodsName);
		pd.put("orderState", orderState);
		pd.put("beginTime", beginTime);
		pd.put("endTime", endTime); 
		pd.put("orderAD", orderAD);
		pd.put("customerName", customerID);
		page.setPd(pd);
		page.setPageSize(10);
		Map<String, Object> orderMap = new LinkedHashMap<String, Object>();

		if (orderID != null) { // 返回类型一：根据订单ID精确查询。
			List<PageData> orderGoods = getOrderGoodsByOID(orderID); // 根据定单号获取定单包含的商品
			page.setTotalRecord(1);
			orderMap.put(orderID, orderGoods);
		} else { // 返回类型三：无订单编号  --商品goodsName条件查询。
			List<PageData> orderIDList = getOrderIDByS5(page);
			int orderIDNum = getOrderIDNumByS5(page);
			page.setTotalRecord(orderIDNum);
			for (PageData o : orderIDList) {
				List<PageData> orderGoods = getOrderGoodsByOID(o.getString("order_id")); // 根据定单号获取定单包含的商品

				// 以下分割一下备注为数组;
				for (PageData pd2 : orderGoods) {
					pd2.put("customer_remark", Tools.notEmptys(pd2.getString("customer_remark"))
							? pd2.getString("customer_remark").split("&") : null);
					pd2.put("aurora_remark", Tools.notEmptys(pd2.getString("aurora_remark"))
							? pd2.getString("aurora_remark").split("&") : null);
				}

				orderMap.put(o.getString("order_id"), orderGoods);
			}
		}
		return orderMap;
	}

	/**
	 * 网站管理/订单管理 --根据商品goodsName等条件获取订单ID
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getOrderIDByS5(Page page) throws Exception {
		return (List<PageData>) daoSupport.findForList("OrderReadMapper.getOrderIDByS5", page);
	}

	/**
	 * 网站管理/订单管理 --根据商品ID等条件获取订单数量
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOrderIDNumByS5(Page page) throws Exception {
		return (int) daoSupport.findForObject("OrderReadMapper.getOrderIDNumByS5", page);
	}

	/**
	 * 订单管理/所有订单 --根据商品ID等条件获取订单ID
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getOrderIDByS8(Page page) throws Exception {
		return (List<PageData>) daoSupport.findForList("OrderReadMapper.getOrderIDByS8", page);
	}

	/**
	 * 订单管理/所有订单 --根据商品ID等条件获取订单数量
	 * 
	 * @param page
	 * @throws Exception
	 */
	@Override
	public int getOrderIDNumByS8(Page page) throws Exception {
		return (int) daoSupport.findForObject("OrderReadMapper.getOrderIDNumByS8", page);
	}

	/**
	 * 根据订单ID获取订单老状态
	 * 
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getOrderOldState(String orderID) throws Exception {
		return (String) daoSupport.findForObject("OrderReadMapper.getOrderOldState", orderID);
	}

	/**
	 * 关闭或恢复订单(网站管理--订单管理) ------(关闭异常订单修改库存未完成!)
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateOrderShut(PageData pd) throws Exception {
		int orderStateNew = Integer.valueOf(pd.getString("orderStateNew"));
		int state = Integer.valueOf(this.getOrderState(pd.getString("orderID")));
		int level = getOrderLevel(pd.getString("orderID"));
		// 如果微仓订单,
		if (level != 1) {
			return "微仓发出订单不能关闭!";
		}
		if (9 != orderStateNew) {
			return "异常关闭订单不能再恢复了!";
		}
		pd.put("orderStateNow", state);// 记录订单当前状态;
		daoSupport.update("OrderWriteMapper.updateOrderShut", pd);// 更改订单状态

		// 注释的原因:已经异常关闭的订单不可恢复;@ssy2017-11-27
		// if (9 != orderStateNew) {//恢复订单;恢复之后订单商品成本是当前阶段成本,订单成本重新计算;价格依然是原先价格;
		// int olderState =
		// Integer.valueOf(Tools.notEmptys(getOrderOldState(pd.getString("orderID")))?getOrderOldState(pd.getString("orderID")):"0");
		// //如果订单老状态是已接单后面的,6,7,8
		// if (olderState >= 6 ) {//已经接单---再次恢复需要减掉库存;
		// this.updateBackStock(pd.getString("orderID"),2);//批量减掉订单里的每个商品库存goods_manage;
		// this.saveOrderCost(pd.getString("orderID"));//重新计算订单和订单里商品成本,商品销量以及利润
		// }
		// }else{//关闭订单;商品利润,
		// //状态不能是10,9;
		if (state >= 9) {
			return "已关闭订单不能异常关闭!";
		} else {//
			List<PageData> orderGoods = this.getOrderGoodsByOID(pd.getString("orderID"));// 根据订单id
																							// 查询订单商品信息;
			if (state >= 6) {// 已经接单的话,要恢复商品阶段成本库存;
				for (Iterator iterator = orderGoods.iterator(); iterator.hasNext();) {
					PageData pageData = (PageData) iterator.next();
					daoSupport.save("GoodsWriteMapper.saveGoodsCostBatch", pageData);// 批量新增订单商品阶段成本库存;
					// updateGPSWithShut(pd.getString("orderID"));//减去商品销量和利润;注释的原因:废弃原先的商品销量以及利润统计;@ssy2017-11-27
				}
			}
			if (state != 1 && state != 3) {// 已经付款的订单关闭需要恢复商品库存
				for (Iterator iterator = orderGoods.iterator(); iterator.hasNext();) {
					PageData pageData = (PageData) iterator.next();
					daoSupport.update("GoodsWriteMapper.addMGStockBatch", pageData);// 批量新增订单商品库存;同步添加库存至goods_manage
				}
			}
		}
		return "success";
	}
	
	/**
	 * 异常订单关闭,减掉商品销量(一段,二段),减掉对应时间的商品利润和销售额;
	 * @throws Exception 
	 *//*注释的原因:废弃原先的商品销量以及利润统计;@ssy2017-11-27
	public void updateGPSWithShut(String orderID) throws Exception{
		@SuppressWarnings("unchecked")
		List<PageData> orderGoodsList = (List<PageData>)daoSupport.findForList("OrderReadMapper.getOrderGoodsByOID", orderID);
		for (Iterator iterator = orderGoodsList.iterator(); iterator.hasNext();) {
			PageData pageData = (PageData) iterator.next();
			daoSupport.update("GoodsWriteMapper.updateGS", pageData);//批量减去商品总销售量;
			daoSupport.update("GoodsWriteMapper.updateGPT", pageData);//批量减去商品总营业额和总利润;
		}
	}*/
	
	/**
	 * 获取今天下单数量
	 * @param time
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getTodayONum(String time) throws Exception {
		return (int) daoSupport.findForObject("OrderReadMapper.getTodayONum", time);
	}

	/**
	 * 根据订单状态获取订单数量
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getONumByState(String state) throws Exception {
		return (int) daoSupport.findForObject("OrderReadMapper.getONumByState", state);
	}

	/**
	 * 获取今天营业额(只计算正常已付款订单)
	 * @param time
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getTodayTurnover(String time) throws Exception {
		return (String) daoSupport.findForObject("OrderReadMapper.getTodayTurnover", time);
	}

	/**
	 * 根据订单ID更新单个订单备注
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateRemark(PageData pd) throws Exception {
		daoSupport.update("OrderWriteMapper.updateRemark", pd);
	}

	/**
	 * 北极光退款操作; 修改该订单的退款时间;操作者; 修改该订单的状态order_state
	 * 
	 * @author SSY
	 */
	@Override
	public String updateAuroraRefund(PageData pd) throws Exception {
		String orderID = pd.getString("orderID");
		// 当前订单状态是不是4;
		String orderState = getOrderState(orderID);
		if (orderState == null && Integer.valueOf(orderState) != 4) {
			return "退款失败!订单状态不为4";
		}
		if (getOrderLevel(orderID) != 1) {
			return "微仓发出订单不能退款!";
		}
		pd.put("orderState", 5);
		pd.put("refundTime", DateUtil.getSdfMinutes());
		pd.put("refundOperator", Jurisdiction.getUserEmail());
		daoSupport.update("OrderWriteMapper.updateRefundOperate", pd);// 据订单ID更新订单北极光退款操作时间以及订单状态,orderState=5
		// 订单商品库存恢复--增加,商品管理表goods_manage;
		updateBackStock(orderID);
		return "success";
	}

	/**   
	 *  (北极光退款,异常订单关闭;)增加订单商品里的库存;
	 */
	public void updateBackStock(String orderID) throws Exception {
		@SuppressWarnings("unchecked")
		List<PageData> goodsStock = (List<PageData>) daoSupport.findForList("OrderReadMapper.getOGStock", orderID);// 根据订单号,查询订单中商品购买数量;
			for (Iterator iterator = goodsStock.iterator(); iterator.hasNext();) {
				PageData pageData = (PageData) iterator.next();
				daoSupport.update("GoodsWriteMapper.addMGStockBatch", pageData);//批量增加库存,goods_manage
			}
	}

	/**
	 * 根据订单ID获取订单状态
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getOrderState(String orderID) throws Exception {
		return (String) daoSupport.findForObject("OrderReadMapper.getOrderState", orderID);
	}

	/**
	 * 根据订单编号查询订单类型;*(微仓Or非微仓)
	 */
	@Override
	public int getOrderLevel(String orderID) throws Exception {
		return (int) daoSupport.findForObject("OrderReadMapper.getOrderLevel", orderID);
	}

	/**
	 * 接单之前修改海外直邮的总成本
	 * @param goodsCost
	 * @throws Exception
	 */
	@Override
	public void updateOHGCost(String goodsCost) throws Exception{
		@SuppressWarnings("unchecked")
		List<Map<String, String>> goodsCostList = JSONArray.parseObject(goodsCost, List.class);
		for (Iterator iterator = goodsCostList.iterator(); iterator.hasNext();) {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) iterator.next();
			daoSupport.update("OrderWriteMapper.updateOHGCost", map);
		}
	}
	
	/**
	 * 接单操作; 修改该订单的接单时间;操作者; 修改该订单的状态order_state
	 * @author SSY
	 */
	@Override
	public void updateOrderReceiving(String orderID) throws Exception {
		// 当前订单状态是不是2;
		String orderState = getOrderState(orderID);
		if (orderState == null || Integer.valueOf(orderState) != 2) {
			throw new MyDataException();
		}
		int orderType = (int) daoSupport.findForObject("OrderReadMapper.getOrderType", orderID);// 获取订单类型(微仓/非微仓)
		if (orderType == 1) {//如果是微仓订单,就新增用户微仓商品,更改此订单为已完成!
			PageData pdmw = new PageData();
			@SuppressWarnings("unchecked")
			List<PageData> orderGoods = (List<PageData>) daoSupport.findForList("OrderReadMapper.getOrderGMByOID",orderID);
			pdmw.put("orderGoods", orderGoods);
			pdmw.put("orderID", orderID);
			pdmw.put("time", DateUtil.getTime());
			daoSupport.save("OrderWriteMapper.addMWGoods", pdmw);//保存到用户微仓货物中
			pdmw.put("orderState", "8");
			pdmw.put("takeOrderTime", DateUtil.getTime());
			pdmw.put("takeOrderOperator", Jurisdiction.getUserEmail());
			daoSupport.update("OrderWriteMapper.updateReceivingOperate", pdmw);// 据订单ID更新订单北极光接单操作时间以及订单状态,
		} else{//北极光订单个人收货地址;
			PageData pdos = new PageData();
			pdos.put("orderState", 6);
			pdos.put("orderID", orderID);
			pdos.put("takeOrderTime", DateUtil.getSdfMinutes());
			pdos.put("takeOrderOperator", Jurisdiction.getUserEmail());
			daoSupport.update("OrderWriteMapper.updateReceivingOperate", pdos);// 据订单ID更新订单北极光接单操作时间以及订单状态,
		}
		
		//计算成本利润,减库存; 
		int orderLevel = this.getOrderLevel(orderID);//1北极光订单,2微仓发出订单;
		if (orderLevel==1) {//北极光订单;
			saveOrderCost(orderID);//计算商品成本,商品销量(一段,二段)利润,订单成本;
		}  
	}

	
	
	/**
	 * 订单分阶段订单和商品成本,商品销量(一段,二段),商品利润,销售额计算--北极光订单;
	 */
	@SuppressWarnings("unchecked")
	public void saveOrderCost(String orderID) throws Exception {
		PageData pd = new PageData();
		BigDecimal orderCost = new BigDecimal("0.0000");//订单商品成本
		List<PageData> orderGoodsList = (List<PageData>) daoSupport.findForList("OrderReadMapper.getOrderGMByOID",
				orderID);// 根据订单id 查询不为所有商品id和数量num;
		for (Iterator iterator = orderGoodsList.iterator(); iterator.hasNext();) {
			PageData orderGoods = (PageData) iterator.next();
			int buyNum = Integer.valueOf(orderGoods.getString("goods_num"));// 订单该商品购买数量;
			// 1.统计商品销售数量;
			PageData goodsSale = (PageData) daoSupport.findForObject("GoodsReadMapper.getGoodsByID",orderGoods.getString("goods_id"));//
			goodsSale.put("saleNum", buyNum);
			daoSupport.save("GoodsWriteMapper.updateGoodsSale", goodsSale);// 保存商品销量--只用来排序和展示,不做统计使用,goods_manage
			// 注释原因:原商品统计废弃,@ssy2017-11-27
			// int rnum1 = Integer.valueOf(goodsSale.getString("rnum1"));
			// int rnum2 = Integer.valueOf(goodsSale.getString("rnum2"));
			// if (rnum1<=buyNum&&buyNum<=rnum2) {//一段销量
			// goodsSale.put("month",
			// "month_a"+DateUtil.getMonth());//例如:month_a9
			// }else{//二段销量
			// goodsSale.put("month",
			// "month_b"+DateUtil.getMonth());//例如:month_b9
			// }
			// daoSupport.save("StatisticsGoodsWriteMapper.saveOrUpdateGSale",goodsSale);//统计表专用--保存商品销量
			// 2.商品总售价,税费计算
//			BigDecimal goodsTPrice = new BigDecimal("0.0000");// 商品总价;营业额
//			BigDecimal taxes = new BigDecimal("0.0000");// 税费
//			BigDecimal goodsNum = new BigDecimal(buyNum);
//			BigDecimal goodsPrice = new BigDecimal(orderGoods.getString("goods_price"));
//			goodsTPrice = goodsNum.multiply(goodsPrice);
//			taxes = goodsTPrice.multiply(new BigDecimal("0.119"));
			
			// 3.计算商品成本;
			BigDecimal goodsCost = new BigDecimal("0.0000");// 该商品总成本;
			if (!"1".equals(orderGoods.getString("trade_type"))) {// 如果该订单商品是海外直邮商品和国内现货;
				if (!"0.00".equals(orderGoods.getString("cost_price"))) {// 并已经录入商品总成本;
					goodsCost = goodsCost.add(new BigDecimal(orderGoods.getString("cost_price")));
				} else {
					throw new CustomException();// 海外直邮手动录入商品总成本;
				}
			} else {// 保税仓商品;
				String goodsID = orderGoods.getString("goods_id");// 订单该商品id;
				List<PageData> goodsCSList = (List<PageData>) daoSupport.findForList("GoodsReadMapper.getCSByGoodsID",
						goodsID);// 根据商品id集合 查询不为0的库存成本;
				for (Iterator iterator2 = goodsCSList.iterator(); iterator2.hasNext();) {
					PageData goodsCS = (PageData) iterator2.next();
					int stock = Integer.valueOf(goodsCS.getString("stock"));// 该时间段成本商品库存;
					BigDecimal price2 = new BigDecimal(goodsCS.getString("cost_price"));// 该时间段商品成本价;
					BigDecimal stock2 = new BigDecimal(stock);// 该段成本商品库存数量;
					if (buyNum > stock) {
						BigDecimal multiply = stock2.multiply(price2);
						goodsCost = goodsCost.add(multiply);// 累加计算商品成本;
						buyNum -= stock;
						stock = 0;
						// 修改该商品库存该段时间库存;goods_cost_stock;
						goodsCS.put("stock", stock);
						daoSupport.update("GoodsWriteMapper.updateStockByID", goodsCS);
					} else {
						stock -= buyNum;// 减掉库存;
						BigDecimal buyNum2 = new BigDecimal(buyNum);// 购买该段成本商品数量;
						BigDecimal multiply = buyNum2.multiply(price2);
						goodsCost = goodsCost.add(multiply);// 累加计算商品成本;
						buyNum = 0;
						// 修改该商品库存该段时间库存;goods_cost_stock;
						goodsCS.put("stock", stock);
						daoSupport.update("GoodsWriteMapper.updateStockByID", goodsCS);
						break;
					}
				} // 该商品减库存,计算该商品成本结束;
				if (buyNum > 0) {
					throw new CustomException("保税仓库存不足;");
				}
				// 写进订单商品成本中;
				orderGoods.put("costPrice", goodsCost);
				daoSupport.update("OrderWriteMapper.updateOGoodsCostByID", orderGoods);// 根据订单id,商品id修改订单商品的总成本;
			}
			// 4.统计商品利润;商品总售价-税费-成本即可
			// 注释原因:原商品统计废弃,@ssy2017-11-27
			// BigDecimal goodsProfit = new BigDecimal("0.0000");//商品利润
			// goodsProfit = goodsTPrice.subtract(taxes).subtract(goodsCost);
			// orderGoods.put("goodsProfit", goodsProfit);
			// orderGoods.put("goodsTPrice", goodsTPrice);
			// orderGoods.put("monthp",
			// "month_p"+DateUtil.getMonth());//例如:month_p9
			// orderGoods.put("montht",
			// "month_t"+DateUtil.getMonth());//例如:month_t9
			// daoSupport.save("GoodsWriteMapper.saveOrUpdateGPT",orderGoods);//保存商品利润,营业额
			orderCost = orderCost.add(goodsCost);
		}
		pd.put("orderID", orderID);
		pd.put("orderCost", orderCost);
		daoSupport.update("OrderWriteMapper.updateOrderCostByID", pd);// 根据订单id,修改订单总成本;
	}
	
	/**
	 * 发货/修改物流信息|no 修改订单状态/修改物流信息;
	 * @param pd
	 * @param goodsMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateLogistics(PageData pd, List<Map<String, String>> goodsList, String orderState) throws Exception {
		pd.put("sendOperator", Jurisdiction.getUserEmail());
		pd.put("sendTime", DateUtil.getSdfMinutes());
		String goodsID = "";
		String logisticsType = "";
		String logisticsNum = null;
		String logisticsName = null;
		String logisticsCode = null;
		// 遍历操作;

		for (Object object : goodsList) {
			@SuppressWarnings("unchecked")
			Map<String, String> goods = (Map<String, String>) object;

			goodsID = goods.get("goodsID").toString().trim();
			logisticsType = goods.get("logisticsType").toString().trim();
			if (logisticsType.equals("1")) {
				logisticsNum = goods.get("logisticsNum").toString().trim();
				logisticsName = goods.get("logisticsName").toString().trim();
				logisticsCode = goods.get("logisticsCode").toString().trim();
			} else {
				logisticsCode = null;
				logisticsNum = null;
				logisticsName = null;
			}
			pd.put("goodsID", goodsID);
			pd.put("logisticsNum", logisticsNum);
			pd.put("logisticsName", logisticsName);
			pd.put("logisticsCode", logisticsCode);
			if (orderState.equals("6")) {// 该商品是第一次发货操作;
				pd.put("orderState", 7);
				daoSupport.update("OrderWriteMapper.updateSendGoodsOperate", pd);// 订单发货状态,操作者/操作时间;order_manage
				daoSupport.update("OrderWriteMapper.updateLogistics", pd);// 商品发货信息 // 发货时间,发货者,物流信息,物流信息填写者;order_goods
			} else {// 该商品已经发货,再次修改物流信息
				daoSupport.update("OrderWriteMapper.updateLogistics", pd);// 更新发货信息;order_goods
			}
		}
	}

	/**
	 * 批量退款处理
	 */
	@Override
	public void batchRefund(PageData pd) throws Exception {
		daoSupport.update("OrderWriteMapper.batchRefund", pd);// 据订单ID更新订单北极光接单操作时间以及订单状态
	}

	/**
	 * 查询回显所有物流公司
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getLogisticsCompany() throws Exception {
		return (List<PageData>) daoSupport.findForList("OrderReadMapper.getLogisticsCompany");
	}
	
	/**
	 * 后台手动确定收货,如果该订单中有商品物流信息还未签收状态,就不能确认收货;//累计用户购买次数,购买商品总数
	 * @param orderID
	 */
	public String updateConfirmReceipt(String orderID) throws Exception{
		String orderState = this.getOrderState(orderID);
		if (!"7".equals(orderState)) {
			return "没发货你就想确认?";
		}
		@SuppressWarnings("unchecked")
		List<PageData> goodsLogisticInfo = (List<PageData>) daoSupport.findForList("OrderReadMapper.getLogisticInfo",orderID);
		if (goodsLogisticInfo!=null&&goodsLogisticInfo.size()>0) {
			String orderTracesByJson = "";
			for (Iterator iterator = goodsLogisticInfo.iterator(); iterator.hasNext();) {
				PageData goodsLInfo = (PageData) iterator.next();
				String expCode = goodsLInfo.getString("logistics_company_code");
				String expNo = goodsLInfo.getString("logistics_number").split(",")[0];
				orderTracesByJson = KdniaoTrackQuery.getOrderTracesByJson(expCode, expNo);
				Map orderTraces = JSONObject.parseObject(orderTracesByJson, Map.class);
				String state = (String)orderTraces.get("State");
				if (!"3".equals(state)) {
					return "表急,有商品还没签收呢!";
				}
			}
		}
		PageData pd = new PageData();
		pd.put("orderID", orderID);
		pd.put("time", DateUtil.getTime());
		pd.put("operator", Jurisdiction.getUserEmail());
		daoSupport.update("OrderWriteMapper.updateOrderReceipt",pd);
		
		return "success";
	}

	/**
	 * 接单提醒用--30分钟未接单的订单数量
	 */
	@Override
	public int get30MMissedOrderNum(String userID) throws Exception {
		return (int) daoSupport.findForObject("OrderReadMapper.get30MMissedOrderNum", userID);
	}
	
	
	/**
	 * @Title: updateOrderAutoShut 
	 * @Description: 自动关闭两小时未付款订单
	 * @param    
	 * @return int  
	 * @author SSY
	 * @date 2018年6月21日 下午6:38:10
	 */
	@Override
	public int updateOrderAutoShut() throws Exception{
		PageData pd = new PageData();
		pd.put("time", DateUtil.getTime());
		pd.put("operator", "2小时未付款订单自动关闭! ");
		int num = (int)daoSupport.update("OrderWriteMapper.updateOrderAutoShut",pd);
		return num;
	}
	
}
