package com.aurora.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.entity.GoodsManage;
import com.aurora.entity.Page;
import com.aurora.service.BrandManageService;
import com.aurora.service.GoodsManageService;
import com.aurora.util.AppUtil;
import com.aurora.util.Const;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/** 未上架商品
 * @author BYG 2017/5/25
 * @version 1.0
 */
@Controller
@RequestMapping(value="/noShelvesGoods")
public class GoodsNoShelvesController extends BaseController{

	String menuUrl = "noShelvesGoods.do"; //菜单地址(权限用)
	
	@Resource(name="goodsManageServiceImpl")
	private GoodsManageService goodsManageServiceImpl;
	@Resource(name="brandManageServiceImpl")
	private BrandManageService brandManageServiceImpl;
		
	/**
	 * 跳转到未上架商品查询、管理页面(默认待上架)
	 * @param Page  keyWord(搜索名称)
	 * @return
	 */
	@RequestMapping
	public ModelAndView goNoShelvesGoods(Page page, String state, String keyWord, 
										String category1ID, String orderColumn, String orderAD)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		state = Tools.notEmptys(state)?state.trim():"1,6";//默认待上架;
		String[] states = state.split(",");
		pd.put("goodsState", state);
		 
		keyWord = Tools.notEmptys(keyWord)? keyWord : null;
		category1ID = category1ID != null && !"".equals(category1ID)? category1ID : null;
		if (states[0]=="2") {//审核中
			orderColumn = orderColumn != null && !"".equals(orderColumn.trim())? orderColumn.trim() : "submit_time";
		}else if (states[0] == "5") {//审核未通过
			orderColumn = orderColumn != null && !"".equals(orderColumn.trim())? orderColumn.trim() : "review_time";
		}else {//待上架
			orderColumn = orderColumn != null && !"".equals(orderColumn.trim())? orderColumn.trim() : "lnput_time";
		}
		orderAD = orderAD != null && !"".equals(orderAD)? orderAD : "DESC";
		pd.put("goodsStates", states);
		pd.put("keyWord", keyWord);
		pd.put("category1ID", category1ID);
		pd.put("orderColumn", orderColumn);
		pd.put("orderAD", orderAD);
		page.setPd(pd);
		page.setPageSize(20);
		List<PageData>	goodsCategory1 = null;	//商品一级类目
		List<PageData> goodsList = null;
		try {
			goodsCategory1 = goodsManageServiceImpl.getGoodsCategory("0");	//商品一级类目
			goodsList = goodsManageServiceImpl.getGoodsByS5(page);
			int totalRecord =Integer.parseInt(goodsManageServiceImpl.getGoodsNum(page).get("num").toString());
			page.setTotalRecord(totalRecord);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CGNSC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CGNSC:系统异常!未上架商品列表---查询执行异常！】");
			throw new Exception(msg);
		}
		mv.addObject("goodsCategory1", goodsCategory1);
		mv.addObject("goodsList", goodsList);
		mv.addObject("page", page);
		mv.addObject("msg", msg);
		mv.addObject("pd", pd);
		mv.setViewName("system/goodsManager/noShelves");
		return mv;	
	}
	
	/**
	 * 跳转到单个商品查看页面
	 * @param Page
	 * @return
	 */
	@RequestMapping(value="/goUpdateGoods",produces="application/json;charset=UTF-8")
	public ModelAndView goUpdateGoods(String goodsID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		String msg = "";
		if(goodsID != null){
			try{
				GoodsManage goodsDM = goodsManageServiceImpl.getGoodsDM(goodsID);
				String property1 = goodsDM.getGoodsDetails().getProperty();
				String property2[] = property1.split(",");
				List<String> property = new ArrayList<String>(Arrays.asList(property2));						//商品属性
				String map61 = goodsDM.getGoodsDetails().getMap6();
				String map62[] = map61.split(",");
				List<String> map6 = new ArrayList<String>(Arrays.asList(map62));             					//商品六面图
				String advertiseMap1 = goodsDM.getGoodsDetails().getAdvertiseMap();
				String advertiseMap2[] = advertiseMap1.split(",");
				List<String> advertiseMap = new ArrayList<String>(Arrays.asList(advertiseMap2));             	//商品广告图
				mv.addObject("property",property);											//商品私有属性
				mv.addObject("map6",map6);													//商品六面图
				mv.addObject("advertiseMap",advertiseMap);									//商品广告图
				mv.addObject("goods", goodsDM);
				mv.setViewName("system/goodsManager/reviewGoods");
				List<PageData>	goodsCategory1 = goodsManageServiceImpl.getGoodsCategory("0");	//商品一级类目
				List<PageData>	brand = brandManageServiceImpl.getBrand();	//商品品牌信息
				mv.addObject("brand", brand);
				mv.addObject("goodsCategory1", goodsCategory1);
				mv.setViewName("system/goodsManager/goodsUpdate");
			}catch(Exception e){
				e.printStackTrace();
				msg = "CGNSC: 系统可能走神了,刷新重试或联系后端管理员!";
				logger.info("【CGNSC:系统异常!商品审核页---商品详情回显系统异常！】");
				throw new Exception(msg);
			}
		}else{
			msg = "CGNSC: 系统可能走神了,刷新重试或联系络驿吴彦祖!";
			logger.info("【CGNSC:参数异常!商品审核页---商品详情回显参数错误！】");
			throw new Exception(msg);
		}
		mv.addObject("msg", msg);
		return mv;
	}
		
	/**
	 * 单个商品修改保存
	 * @param Page
	 * @return
	 */
	@RequestMapping(value="/updateGoods",produces="application/json;charset=UTF-8")
	@ResponseBody
	public  Object updateGoods(String goodsID)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
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
			String[] keywordsArry =  Tools.notEmptys(pd.getString("keywords"))? pd.getString("keywords").trim().split(",") : null;
			if (keywordsArry==null || keywordsArry.length == 0){
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
				goodsManageServiceImpl.updateGoodsByID(pd);	//修改商品
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CGNSC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGNSC:系统异常！商品修改--保存系统异常！】");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "failed";
			msg = "CGNSC:商品修改参数错误!重试或联系管理员!";
			logger.info("CGNSC:商品修改参数错误!");
		}
		map.put("result", result);
		map.put("pd", pd);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}

}
