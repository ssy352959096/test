package com.aurora.serviceImpl.shop.home;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.Result;
import com.aurora.entity.home.HomeFloorBrand;
import com.aurora.entity.home.HomeFloorGoods;
import com.aurora.service.shop.home.HomeFloorService;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.Tools;

/**
 * @Title: HomeFloorServiceImpl.java 
 * @Package com.aurora.serviceImpl.shop.home 
 * @Description: 首页楼层类目商品管理
 * @author SSY  
 * @date 2018年5月2日 下午4:55:47 
 * @version V1.0
 */
@Service
public class HomeFloorServiceImpl implements HomeFloorService {

	@Autowired
	private DAO daoSupport;

	/**
	 * @Title: getHomeFloor 
	 * @Description:  按楼层类目id查询首页各类目楼层展示商品
	 * @param    Integer category1ID
	 * @return     List<HomeFloorGoods>  
	 * @author SSY
	 * @date 2018年5月2日 下午4:53:16
	 */
	@SuppressWarnings("unchecked")
	public List<HomeFloorGoods> getHomeFloor(Integer category1ID) throws Exception{
		List<HomeFloorGoods> homeFloor = null;
		homeFloor = (List<HomeFloorGoods>)daoSupport.findForList("HomeFloorReadMapper.getHomeFloor", category1ID);
		return homeFloor;
	}
	
	/**
	 * @Title: updateHomeFloorGoods 
	 * @Description: 修改首页各类目商品
	 * @param    Integer id(null新增), String goodsID, String goodsNewName, Integer category1ID,String homeMap
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月3日 上午9:55:22
	 */
	public Result<Object> updateHomeFloorGoods(Integer id, String goodsID, String goodsNewName, Integer category1ID,String homeMap) throws Exception{
		Result<Object> result = new Result<Object>();
		if (Tools.isEmpty(goodsID)||Tools.isEmpty(goodsNewName)||Tools.isEmpty(homeMap)||category1ID==null) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		HomeFloorGoods floorGoods = new HomeFloorGoods();
		floorGoods.setCategory1ID(category1ID);
		floorGoods.setGoodsID(goodsID);
		floorGoods.setGoodsNewName(goodsNewName);
		floorGoods.setHomeMap(homeMap);
		floorGoods.setId(id);
		floorGoods.setOperateTime(DateUtil.getTime());
		floorGoods.setOperator(Jurisdiction.getUserEmail());
		int num = 0;
		if (null==id) {//新增
			num = (int) daoSupport.save("HomeFloorWriteMapper.addHomeFloorGoods", floorGoods);
		}else{//修改
			num = (int) daoSupport.update("HomeFloorWriteMapper.updateHomeFloorGoods", floorGoods);
		}
		result.setMsg(num>=1?"操作成功! ":"操作失败! ");
		result.setState(num>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		return result;
	}
	
	
	
	/**
	 * @Title: deleteHomeFloorGoods 
	 * @Description: 删除首页各类目楼层商品设置
	 * @param    
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月3日 上午9:16:09
	 */
	public Result<Object> deleteHomeFloorGoods(Integer id) throws Exception{
		Result<Object> result = new Result<Object>();
		if (id==null) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		int num = (int) daoSupport.delete("HomeFloorWriteMapper.deleteHomeFloorGoods", id);
		result.setMsg(num>=1?"操作成功! ":"操作失败! ");
		result.setState(num>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		return result;
	}
	
	/**
	 * @Title: getHomeFloorBrand 
	 * @Description: 查询首页各类目楼层热门品牌设置
	 * @param    
	 * @return  List<HomeFloorBrand>
	 * @author SSY
	 * @date 2018年5月4日 下午19:16:09
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeFloorBrand> getHomeFloorBrand(Integer floor) throws Exception{
		floor = null==floor ? 10000 : floor;
		List<HomeFloorBrand> homeFloorBrands = (List<HomeFloorBrand>) daoSupport.findForList("HomeFloorReadMapper.getHomeFloorBrand", floor);
		List<HomeFloorBrand> homeFloorBrandList = new ArrayList<HomeFloorBrand>(10);
		for (int i = 1; i <= 8; i++) {
			for (Iterator<HomeFloorBrand> iterator = homeFloorBrands.iterator(); iterator.hasNext();) {
				HomeFloorBrand homeFloorBrand = (HomeFloorBrand) iterator.next();
				if (homeFloorBrand.getLocation()==i){
					homeFloorBrandList.add(homeFloorBrand);
					break;
				}
			}
			if (homeFloorBrandList.size()<i) {
				homeFloorBrandList.add(new HomeFloorBrand());
			}
		}
		return homeFloorBrandList;
	}
	
	/**
	 * @Title: updateHomeFloorBrand 
	 * @Description: 新增或者修改首页各类目楼层热门品牌设置
	 * @param    Integer id
	 * @return Result<Object>  
	 * @author SSY
	 * @date 2018年5月4日 下午19:16:09
	 */
	@Override
	public Result<Object> updateHomeFloorBrand(Integer floor,Integer location, Integer brandID, String brandName) throws Exception{
		Result<Object> result = new Result<Object>();
		if (floor==null||null==location||brandID==null||Tools.isEmpty(brandName)) {
			result.setMsg("参数错误!");
			result.setState(Result.STATE_ERROR);
			return result;
		}
		HomeFloorBrand homeFloorBrand = new HomeFloorBrand();
		homeFloorBrand.setBrandID(brandID);
		homeFloorBrand.setBrandName(brandName);
		homeFloorBrand.setFloor(floor);
		homeFloorBrand.setLocation(location);
		homeFloorBrand.setOperateTime(DateUtil.getTime());
		homeFloorBrand.setOperator(Jurisdiction.getUserEmail());
		int num = (int) daoSupport.save("HomeFloorWriteMapper.updateHomeFloorBrand", homeFloorBrand);
		result.setMsg(num>=1?"操作成功! ":"操作失败! ");
		result.setState(num>=1?Result.STATE_SUCCESS:Result.STATE_ERROR);
		return result;
	}
	
	
	
	
}
