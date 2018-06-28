package com.aurora.controller;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.aurora.service.DataService;
import com.aurora.service.GoodsManageService;
import com.aurora.util.AppUtil;
import com.aurora.util.Const;
import com.aurora.util.CustomException;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/** 单个商品审核
 * @author BYG 2017/5/25
 * @version 1.0
 */
@Controller
@RequestMapping(value="/goodsReview")
public class GoodsReviewController extends BaseController{

	String menuUrl = "goodsReview.do"; //菜单地址(权限用)
	
	@Resource(name="goodsManageServiceImpl")
	private GoodsManageService goodsManageServiceImpl;
	@Resource(name="brandManageServiceImpl")
	private BrandManageService brandManageServiceImpl;
	@Resource(name="dataServiceImpl")
	private DataService dataServiceImpl;
	
	
	/**
	 * 跳转到商品审核查看页面(默认待审核)
	 * @param Page keyWord
	 * @return
	 */
	@RequestMapping
	public ModelAndView goGoodsReview(Page page, String state, String keyWord, 
										String category1ID, String orderColumn, String orderAD)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		state = Tools.notEmptys(state)? state.trim() : "2";
		String[] states = state.split(",");
		keyWord = Tools.notEmptys(keyWord)? keyWord : null;
		category1ID = category1ID != null && !"".equals(category1ID)? category1ID : null;
		if (states[0].equals("2")) {//如果待审核就按提交时间排序
			orderColumn = Tools.notEmptys(orderColumn)? orderColumn.trim() : "submit_time";
		}else {
			orderColumn = Tools.notEmptys(orderColumn)? orderColumn.trim() : "review_time";
		}
		orderAD = orderAD != null && !"".equals(orderAD)? orderAD : "DESC";
		pd.put("goodsState", state);
		pd.put("goodsStates", states);
		pd.put("keyWord", keyWord);
		pd.put("category1ID", category1ID);
		pd.put("orderColumn", orderColumn);
		pd.put("orderAD", orderAD);
		page.setPd(pd);
		page.setPageSize(20);
		List<PageData>	goodsCategory1 = null;	//商品一级类目
		List<PageData> goodsList = null;
		int pendingReviewNum = 0;			
		
		try {
			goodsCategory1 = goodsManageServiceImpl.getGoodsCategory("0");	//商品一级类目
			goodsList = goodsManageServiceImpl.getGoodsByS5(page);
			int totalRecord =Integer.parseInt(goodsManageServiceImpl.getGoodsNum(page).get("num").toString());
			pendingReviewNum = goodsManageServiceImpl.getGoodsNumByState("2");							//待审核商品数量
			page.setTotalRecord(totalRecord);
		} catch (Exception e) {
			msg = "CGRC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CGRC: 系统异常!商品审核--列表查询执行异常！】");
			throw new Exception(msg);
		}	
		mv.addObject("pendingReviewNum", pendingReviewNum);
		mv.addObject("goodsCategory1", goodsCategory1);
		mv.addObject("goodsList", goodsList);
		mv.addObject("page", page);
		mv.addObject("msg", msg);
		mv.addObject("pd", pd);
		mv.setViewName("system/websiteManager/goodsReview");
		return mv;
		
	}

	/**
	 * 跳转到单个商品审核查看页面
	 * @param Page
	 * @return
	 */
	@RequestMapping(value="/goReviewGoods", produces="application/json;charset=UTF-8")
	public ModelAndView goReviewGoods(String goodsID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		if(goodsID != null){
			pd.put("goodsID", goodsID);
			try{
				GoodsManage goodsDM = goodsManageServiceImpl.getGoodsDM(goodsID);
				int shipType0 = goodsDM.getShipType();
				Map<Integer, String> sgIDMap = new HashMap<>();
				sgIDMap.put(shipType0, goodsID);
				String property1 = goodsDM.getGoodsDetails().getProperty();
				String property2[] = property1.split(",");
				List<String> property = new ArrayList<String>(Arrays.asList(property2));						//商品属性
				String map61 = goodsDM.getGoodsDetails().getMap6();
				String map62[] = map61.split(",");
				List<String> map6 = new ArrayList<String>(Arrays.asList(map62));             					//商品六面图
				String advertiseMap1 = goodsDM.getGoodsDetails().getAdvertiseMap();
				String advertiseMap2[] = advertiseMap1.split(",");
				List<String> advertiseMap = new ArrayList<String>(Arrays.asList(advertiseMap2));             	//商品广告图
				int brandID = goodsDM.getGoodsCommon().getBrandID();
				List<PageData> brand = brandManageServiceImpl.getBrandByID(brandID);							//获取当前商品品牌信息			
				List<PageData> goodsCost = goodsManageServiceImpl.getGCById(goodsID);
				mv.addObject("goodsCost",goodsCost);			//该商品阶段库存;
				mv.addObject("sgIDMap",sgIDMap);											//邮寄方式对应的商品id
				mv.addObject("property",property);											//商品私有属性
				mv.addObject("map6",map6);													//商品六面图
				mv.addObject("advertiseMap",advertiseMap);									//商品广告图
				mv.addObject("goodsDM",goodsDM);
				mv.addObject("brand",brand);
				mv.addObject("pd",pd);
				mv.setViewName("system/websiteManager/reviewGoods");
			}catch(Exception e){
				e.printStackTrace();
				msg = "CGRC: 系统可能走神了,刷新重试或联系后端管理员!";
				logger.info("【CGRC:系统异常!商品审核--商品详情回显以及商品成本获取执行异常!】");
				throw new Exception(msg);
			}
		}else{
			msg = "CGRC: 系统可能走神了,刷新重试或联系络驿吴彦祖!";
			logger.info("【CGRC: 参数错误!商品审核--商品goodsID参数错误！】");
			throw new Exception(msg);
		}
		mv.addObject("msg",msg);
		return mv;
	}
	
	
	/**
	 * 单个商品【审核】保存
	 * @param Page
	 * @return
	 */
	@RequestMapping(value="/reviewGoods",produces="application/json;charset=UTF-8")
	@ResponseBody
	public  Object reviewGoods(String goodsID,String rnum1,String remark,String goodsState)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		if (Tools.notEmptys(goodsID)&&Tools.notEmptys(goodsState)&&Tools.notEmptys(rnum1)) {
			pd.put("remark", remark);
			pd.put("goodsState", goodsState.trim());
			pd.put("goodsID", goodsID.trim());
			pd.put("rnum1", rnum1.trim());
			Session session = Jurisdiction.getSession();
			String reviewer = (String) session.getAttribute(Const.SESSION_USER_EMAIL);
			pd.put("reviewer", reviewer);
			pd.put("reviewTime", DateUtil.getTime());
			try {
				goodsManageServiceImpl.updateReviewGoodsByID(pd);	//审核商品
				result = "success";
			} catch (CustomException ce) {
				result = "failed";
				msg = "CGRC: 上架失败!商品成本库存0或小于最小起订量";
			} catch (Exception e) {
				result = "error";
				msg = "CGRC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGRC:系统异常!商品审核--商品审核保存执行异常！】");
			}
		}else{
			result = "failed";
			msg = "CGRC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGRC: 参数错误!商品审核---商品审核保存参数错误！】");
		}
		map.put("result", result);
		map.put("pd", pd);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
}
