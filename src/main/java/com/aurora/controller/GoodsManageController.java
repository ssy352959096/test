package com.aurora.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.service.BrandManageService;
import com.aurora.service.GoodsManageService;
import com.aurora.util.AppUtil;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/** 商品录入
 * @author BYG 2017/5/25
 * @version 1.0
 */
@Controller
@RequestMapping(value="/goodsManage")
public class GoodsManageController extends BaseController{

	String menuUrl = "goodsManage.do"; //菜单地址(权限用)
	
	@Resource(name="goodsManageServiceImpl")
	private GoodsManageService goodsManageServiceImpl;
	@Resource(name="brandManageServiceImpl")
	private BrandManageService brandManageServiceImpl;
	
	/**
	 * 跳转到商品录入页面
	 * @param Page
	 * @return
	 */
	@RequestMapping
	public ModelAndView goGoodsEntry()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		List<PageData>	goodsCategory1 = null;	//商品一级类目
		List<PageData>	brand = null;	//商品品牌信息
		try {
			goodsCategory1 = goodsManageServiceImpl.getGoodsCategory("0");	//商品一级类目
			brand = brandManageServiceImpl.getBrand();	//商品品牌信息
		} catch (Exception e) {
			 msg = "CGCC: 系统可能走神了,刷新重试或联系后端管理员!";
			 logger.info("【CGMC:系统异常！商品录入---回显商品类目或品牌类目失败！】");
			 throw new Exception(msg);
		}
		mv.addObject("brand", brand);
		mv.addObject("goodsCategory1", goodsCategory1);
		mv.addObject("msg", msg);
		mv.addObject("pd", pd);
		mv.setViewName("system/goodsManager/goodsEntry");
		return mv;
	}
		
	/**根据父级类目ID获取类目信息
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/getGoodsCategory",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object getGoodsCategory() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String categoryID = pd.getString("categoryID") != null && !"".equals(pd.getString("categoryID").trim()) ? pd.getString("categoryID").trim() : null;
		if (categoryID != null) {
			try {
				List<PageData>	goodsCategory = goodsManageServiceImpl.getGoodsCategory(categoryID);	 
				map.put("goodsCategory", goodsCategory);
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CGMC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGMC:系统异常！商品录入---根据父级类目ID获取下级类目信息获取异常！】");
			}
		}else {
			result = "failed";
			msg = "CGMC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGMC:参数错误！商品录入---根据父级类目ID获取下级类目信息参数错误！】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 根据商品二级类目ID获取商品对应属性
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/getGoodsColumns",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object getGoodsColumns() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String category2ID = pd.getString("category2ID") != null && !"".equals(pd.getString("category2ID").trim()) ? pd.getString("category2ID").trim() : null;
		if (category2ID != null) {
			try {
				pd.put("category2ID", category2ID);
				List<PageData>	goodsCategory1 = goodsManageServiceImpl.getGoodsColumns(pd);	//根据商品二级类目ID获取对应属性
				map.put("goodsCategory1", goodsCategory1);
				result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "CGMC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGMC:系统异常！商品录入--查询商品属性--根据商品二级类目ID获取商品属性执行异常！】");
			}
		}else {
			result = "failed";
			msg = "CGMC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGMC:参数错误！商品录入--查询商品属性--根据商品二级类目ID获取商品属性参数错误！】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**添加商品
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/addGoods",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object addGoods() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String category1ID = pd.getString("category1ID");
			String category1 = pd.getString("category1");
			String category2ID = pd.getString("category2ID");
			String category2 = pd.getString("category2");
			String category3ID = pd.getString("category3ID");
			String category3 = pd.getString("category3");
			String shipType = pd.getString("shipType");
			String shipTypeN = pd.getString("shipTypeN");
			String goodsName = pd.getString("goodsName");
			String gDescribe1 = pd.getString("gDescribe1");
			String brandID = pd.getString("brandID");
			String brandName = pd.getString("brandName");
			String goodsCode = pd.getString("goodsCode");
			String productArea = pd.getString("productArea");
			String weight = pd.getString("weight");
			String volume = pd.getString("volume");
			String goodsPrice1 = pd.getString("goodsPrice1");
			String goodsPrice2 = pd.getString("goodsPrice2");
			String rnum1 = pd.getString("rnum1");
			String rnum2 = pd.getString("rnum2");
			String rnum3 = pd.getString("rnum3");
			int discount = Tools.notEmptys(pd.getString("discount"))?Integer.valueOf(pd.getString("discount").trim()):100;
			String deposit = pd.getString("deposit");
//			if (!Tools.isNum(deposit)&&Double.valueOf(deposit)<=0&&Double.valueOf(deposit)>100) {
//				msg = "全款or定金?定金比例1~100之间";
//			}
			String goodsStock = pd.getString("goodsStock");
			String stockEmergency = pd.getString("stockEmergency");
			String fakeSales = pd.getString("fakeSales");
			String marketPrice = pd.getString("marketPrice");
			String jdPrice = pd.getString("jdPrice");
			String tbPrice = pd.getString("tbPrice");
			String tSellStoreNnum = pd.getString("tSellStoreNnum");
			String largeBuy = pd.getString("largeBuy");
			String largeNorm = pd.getString("largeNorm");
			String largeMinNum = pd.getString("largeMinNum");
			String goodsState = pd.getString("goodsState");
			String postageStyle = pd.getString("postageStyle");
			String exw = pd.getString("exw");
			String norm = pd.getString("norm");
			String property = pd.getString("property");
			String mainMap = pd.getString("mainMap");
			String map6 = pd.getString("map6");
			String advertiseMap = pd.getString("advertiseMap");
			String keywords = pd.getString("keywords");
			String[] keywordsArry = keywords.split(",");
			if (keywordsArry.length == 0){
				pd.put("keyword1", null);
				pd.put("keyword2", null);
				pd.put("keyword3", null);
				pd.put("keyword4", null);
				pd.put("keyword5", null);
			}else if (keywordsArry.length == 1){
				pd.put("keyword1", keywordsArry[0]);
				pd.put("keyword2", null);
				pd.put("keyword3", null);
				pd.put("keyword4", null);
				pd.put("keyword5", null);
			}else if (keywordsArry.length == 2){
				pd.put("keyword1", keywordsArry[0]);
				pd.put("keyword2", keywordsArry[1]);
				pd.put("keyword3", null);
				pd.put("keyword4", null);
				pd.put("keyword5", null);
			}else if (keywordsArry.length == 3){
				pd.put("keyword1", keywordsArry[0]);
				pd.put("keyword2", keywordsArry[1]);
				pd.put("keyword3", keywordsArry[2]);
				pd.put("keyword4", null);
				pd.put("keyword5", null);
			}else if (keywordsArry.length == 4){
				pd.put("keyword1", keywordsArry[0]);
				pd.put("keyword2", keywordsArry[1]);
				pd.put("keyword3", keywordsArry[2]);
				pd.put("keyword4", keywordsArry[3]);
				pd.put("keyword5", null);
			}else{
				pd.put("keyword1", keywordsArry[0]);
				pd.put("keyword2", keywordsArry[1]);
				pd.put("keyword3", keywordsArry[2]);
				pd.put("keyword4", keywordsArry[3]);
				pd.put("keyword5", keywordsArry[4]);
			}
			pd.put("property", property);
			pd.put("mainMap", mainMap);
			pd.put("map6", map6);
			pd.put("advertiseMap", advertiseMap);
			pd.put("norm", norm);
			pd.put("exw", exw);
			pd.put("category1ID", category1ID);
			pd.put("category1", category1);
			pd.put("category2ID", category2ID);
			pd.put("category2", category2);
			pd.put("category3ID", category3ID);
			pd.put("category3", category3);
			pd.put("shipType", shipType);
			pd.put("shipTypeN", shipTypeN);
			pd.put("goodsName", goodsName);
			pd.put("goodsDescribe", gDescribe1);
			pd.put("brandID", brandID);
			pd.put("brandName", brandName);
			pd.put("goodsCode", goodsCode);
			pd.put("productArea", productArea);
			pd.put("weight", weight);
			pd.put("volume", volume);
			pd.put("goodsPrice1", goodsPrice1);
			pd.put("goodsPrice2", goodsPrice2);
			pd.put("rnum1", rnum1);
			pd.put("rnum2", rnum2);
			pd.put("rnum3", rnum3);
			pd.put("discount", discount);
			pd.put("deposit", deposit);
			if (!deposit.equals("100")) {
				pd.put("depositen", "定金");
			}else{
				pd.put("depositen", "全款");
			}
			pd.put("goodsStock", goodsStock);
			pd.put("stockEmergency", stockEmergency);
			pd.put("fakeSales", fakeSales);
			pd.put("marketPrice", marketPrice);
			pd.put("jdPrice", jdPrice);
			pd.put("tbPrice", tbPrice);
			pd.put("tSellStoreNnum", tSellStoreNnum);
			pd.put("largeBuy", largeBuy);
			pd.put("largeNorm", largeNorm);
			pd.put("largeMinNum", largeMinNum);
			pd.put("goodsState", goodsState);
			pd.put("postageStyle", postageStyle);
			if (postageStyle.equals("1")) {
				pd.put("postageStylen", "包邮");
			}else{
				pd.put("postageStylen", "不包邮");
			}
			String goodsID = category2ID + shipType + DateUtil.getSdfTimes();
			pd.put("goodsID", goodsID);
			Session session = Jurisdiction.getSession();
			String inputer = (String) session.getAttribute(Const.SESSION_USER_EMAIL);
			pd.put("inputer", inputer);
			pd.put("inputTime", DateUtil.getTime());
			if (goodsState.equals("2") ) {
				pd.put("submitTime", DateUtil.getTime());
			}else {
				pd.put("submitTime", null);
			}
			try {
				goodsManageServiceImpl.addGoods(pd);	//添加商品
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CGMC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGMC:商品录入--新增商品系统执行异常！】");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "failed";
			msg = "CGMC:商品录入参数错误!重试或联系管理员!";
			logger.info("CGMC:商品录入参数错误!");
		}
		map.put("result", result);
		map.put("pd", pd);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
	/**修改单个商品状态(上架 下架)
	 * 下架之前判断热搜;
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/uptadeOGState",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object uptadeOGState(String goodsID,String goodsState) throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		 goodsID = Tools.notEmptys(goodsID) ? goodsID.trim() : null;
		 goodsState = Tools.notEmptys(goodsState) ? goodsState.trim() : null;
		if (goodsID != null && goodsState != null) {
			pd.put("goodsID", goodsID);
			pd.put("goodsState", goodsState);
			try {
				msg = goodsManageServiceImpl.uptadeOGState(pd);
				if ("success".equals(msg)) {
					result = "success";	
				}else{
					result = "failed";	
				}
			} catch (Exception e) {
				result = "error";
				msg = "CGMC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGMC:系统异常!商品状态修改--系统异常!】");
			}
		}else{
			result = "failed";
			msg = "CGMC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGMC:参数错误!商品状态修改参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**批量更新商品状态
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/batchUptadeGState",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object batchUptadeGState() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String goodsIDs = Tools.notEmptys(pd.getString("goodsIDs"))? pd.getString("goodsIDs").trim() : null;
		String goodsState = Tools.notEmptys(pd.getString("goodsState").trim()) ? pd.getString("goodsState").trim() : null;
		if (goodsIDs != null && goodsState != null) {
			String[] ArrayGoodsID = goodsIDs.split(",");
			pd.put("ArrayGoodsID", ArrayGoodsID);
			pd.put("goodsState", goodsState);
			try {
				msg = goodsManageServiceImpl.uptadeBatchGState(pd);
				if ("success".equals(msg)) {
					result = "success";	
				}else{
					result = "failed";	
				}
			} catch (Exception e) {
				result = "error";
				msg = "CGMC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGMC:系统异常!商品状态批量修改--系统执行异常！】");
			}
		}else{
			result = "failed";
			msg = "CGMC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGMC:参数错误!商品状态批量修改--参数错误!！】");
		}	
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 删除单个商品(先排除表home_hb_sell、home_hot_sell、home_large_buy、home_less_buy、home_new_goods、 home_tj_sell)
	 * 如果购物车shop_cart中有此商品删除购物车中商品
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteOGoods",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteOGoods() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String goodsID = pd.getString("goodsID") != null && !"".equals(pd.getString("goodsID").trim()) ? pd.getString("goodsID").trim() : null;
		if(null != goodsID ){
			pd.put("goodsID", goodsID);
			List<PageData> existGoods = goodsManageServiceImpl.checkExistGoods(pd);
			if (existGoods.isEmpty()) {
				try {
				goodsManageServiceImpl.deleteOGoods(pd);   //同时删除goods_common/goods_details/goods_manag三个表中相应的数据
				result = "success";
				} catch (Exception e) {
					e.printStackTrace();
					result = "error";
					msg = "CGMC: 操作失败!重试或联系后端管理员!";
					logger.info("【CGMC:系统异常!商品删除--执行异常！】");
				}
			}else {
				result = "failed";
				msg = "操作失败!该商品存在热搜管理中!";
			}
		}else{
			result = "failed";
			msg = "CGMC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGMC:参数错误!商品删除--参数错误！】");
		}	
		map.put("result", result);
		map.put("msg", msg);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**批量删除商品(先排除表home_hb_sell、home_hot_sell、home_large_buy、home_less_buy、home_new_goods 、home_tj_sell)
	 * 如果购物车shop_cart中有此商品删除购物车中商品
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/batchDeleteGoods",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object batchDeleteGoods() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String goodsIDs = pd.getString("goodsIDs") != null && !"".equals(pd.getString("goodsIDs").trim()) ? pd.getString("goodsIDs").trim() : null;
		if(null != goodsIDs ){
			String ArrayGoodsID[] = goodsIDs.split(",");
			pd.put("ArrayGoodsID", ArrayGoodsID);
			List<PageData> existGoods = goodsManageServiceImpl.batchCheckExistGoods(pd);
			if (existGoods.isEmpty()) {
				try {
					goodsManageServiceImpl.deleteBatchGoods(pd);
					result = "success";
				} catch (Exception e) {
					e.printStackTrace();
					result = "error";
					msg = "CGMC: 操作失败!重试或联系后端管理员!";
					logger.info("【CGMC:系统异常!商品批量删除执行异常！】");
				}
			}else {
				result = "failed";
				msg = "操作失败！批量删除的商品有商品存在热搜管理中!";
			}
		}else{
			result = "failed";
			msg = "CGMC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGMC:参数错误!商品批量删除--参数错误！】");
		}	
		map.put("result", result);
		map.put("pd", pd);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
	/**修改单个商品库存
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/uptadeOGStock",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object uptadeOGStock() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		String msg = "";
		pd = this.getPageData();
		String goodsID = pd.getString("goodsID") != null && !"".equals(pd.getString("goodsID").trim()) ? pd.getString("goodsID").trim() : null;
		String goodsStock = pd.getString("goodsStock") != null && !"".equals(pd.getString("goodsStock").trim()) ? pd.getString("goodsStock").trim() : null;
		if (goodsID != null && goodsStock != null) {
			pd.put("goodsID", goodsID);
			pd.put("goodsStock", goodsStock);
			try {
				goodsManageServiceImpl.uptadeOGStock(pd);
				result = "success";	
			} catch (Exception e) {
				result = "error";
				msg = "CGMC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGMC:商品库存修改--系统异常！】");
			}
		}else{
			result = "failed";
			msg = "CGMC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGMC:商品库存修改--参数错误！】");
		}	
		map.put("result", result);
		map.put("pd", pd);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
}
