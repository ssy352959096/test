package com.aurora.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.aurora.entity.Page;
import com.aurora.service.GoodsManageService;
import com.aurora.util.CustomException;
import com.aurora.util.PageData;
import com.aurora.util.Tools;

/**
 * 商品审核/上架
 * 
 * @author BYG 2017/5/25
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/shelvesGoods")
public class GoodsShelvesController extends BaseController {

	String menuUrl = "shelvesGoods.do"; // 菜单地址(权限用)

	@Resource(name = "goodsManageServiceImpl")
	private GoodsManageService goodsManageServiceImpl;

	/**
	 * 跳转到上架商品查询管理页面
	 * 
	 * @param Page   keyWord
	 * @return
	 */
	@RequestMapping
	public ModelAndView goShelvesGoods(Page page, String shipType, String keyWord, String minPrice, String maxPrice,
			String category1ID, String orderColumn, String orderAD) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";

		shipType = shipType != null && !"".equals(shipType.trim()) ? shipType.trim() : null;
		keyWord = Tools.notEmptys(keyWord)? keyWord : null;
		minPrice = minPrice != null && !"".equals(minPrice.trim()) ? minPrice.trim() : null;
		maxPrice = maxPrice != null && !"".equals(maxPrice.trim()) ? maxPrice.trim() : null;
		category1ID = category1ID != null && !"".equals(category1ID.trim()) ? category1ID.trim() : null;
		orderColumn = orderColumn != null && !"".equals(orderColumn.trim()) ? orderColumn.trim() : "review_time";
		orderAD = orderAD != null && !"".equals(orderAD.trim()) ? orderAD.trim() : "DESC";
		pd.put("shipType", shipType);
		pd.put("keyWord", keyWord);
		pd.put("category1ID", category1ID);
		pd.put("minPrice", minPrice);
		pd.put("maxPrice", maxPrice);
		pd.put("orderColumn", orderColumn);
		pd.put("orderAD", orderAD);
		page.setPd(pd);
		page.setPageSize(20);
		List<PageData> goodsCategory1 = null; // 商品一级类目
		List<PageData> goodsList = null; // 条件查询出上架中商品
		try {
			goodsCategory1 = goodsManageServiceImpl.getGoodsCategory("0");
			goodsList = goodsManageServiceImpl.getShelvesGByS5(page);// 商品状态是4,审核通过3,4,这里只筛选4
			int totalRecord = Integer.parseInt(goodsManageServiceImpl.getShelvesGNum(page).get("num").toString());// 条件查询出上架商品总数量
			page.setTotalRecord(totalRecord);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "CGSC: 系统可能走神了,刷新重试或联系后端管理员!";
			logger.info("【CGSC:系统异常!已上架商品---列表查询系统异常！】");
			throw new Exception(msg);
		}
		mv.addObject("goodsCategory1", goodsCategory1);
		mv.addObject("goodsList", goodsList);
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.addObject("msg", msg);
		mv.setViewName("system/goodsManager/shelves");
		return mv;
	}

	/**
	 * 关联商品id
	 */
	@RequestMapping(value = "/goodsRelate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object goodsRelate(String goodsID, String relateID1, String relateID2) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		String msg = "";
		// 查询这两个商品id是否存在并已经上架;如果上架就保存,否则不保存.
		if (Tools.notEmptys(goodsID)) {
			try {
				msg = goodsManageServiceImpl.updateRelateID(goodsID.trim(), relateID1.trim(), relateID2.trim());
				result = ("success").equals(msg) ? "success":"failed";
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
				msg = "CGSC: 操作失败!重试或联系后端管理员!";
				logger.info("【CGSC:系统异常!已上架商品--关联商品--系统执行异常!】");
			}
		} else {
			result = "failed";
			msg = "CGSC: 操作失败!重试或联系络驿吴彦祖!";
			logger.info("【CGSC:参数错误!已上架商品--关联商品--参数错误!】");
		}
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}

}
