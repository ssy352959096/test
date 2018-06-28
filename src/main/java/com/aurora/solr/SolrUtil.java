package com.aurora.solr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrException.ErrorCode;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import com.aurora.entity.home.GoodsSolr;
import com.aurora.service.GoodsManageService;
import com.aurora.util.DateUtil;

/** solr 搜索引擎
 * @author BYG 2017-12-12
 * @version 1.0
 */
@Repository("solrUtil")
public class SolrUtil {

	@Resource(name="goodsManageServiceImpl")
	protected GoodsManageService goodsManageServiceImpl;
	
	private final String solrUrl = "http://120.27.217.255:8983/solr/goods-core";
	//创建solrClient同时指定超时时间，不指定走默认配置
	private HttpSolrClient getsolrClient(){
		HttpSolrClient solrClient = null;
		try {
			solrClient = new HttpSolrClient.Builder(solrUrl)
	                .withConnectionTimeout(10000)
	                .withSocketTimeout(60000)
	                .build();
		} catch (Exception e) {
			throw new SolrException(ErrorCode.SERVER_ERROR,e);
		}
		return solrClient;
	}
	
	/**商品上架后，商品信息同步到solr
	 * @author BYG 2017-12-13
	 * @param  goodsIDs
	 * @return 
	 */
	public void addGoodsToSolr(String goodsIDs) throws Exception{
		HttpSolrClient solrClient = this.getsolrClient();
		String[] arrayGoodsID = goodsIDs.split(",");
		List<GoodsSolr> goodsSolr = goodsManageServiceImpl.getGoodsForSolr(arrayGoodsID);
		for (GoodsSolr gs : goodsSolr) {
			gs.setId(gs.getGoods_id());
			gs.setUpdate_time(DateUtil.getTime());
			if (gs.getDeposit().equals("100")) {
				gs.setDeposit_name("全款");
			} else {
				gs.setDeposit_name("定金");
			}
			if (gs.getPostage_style().equals("1")) {
				gs.setPostage_style_name("包邮");
			} else {
				gs.setPostage_style_name("不包邮");
			}
		}
		
		goodsManageServiceImpl.addGoodsToGoodsSolr(goodsSolr);//商品上架后，同步到 goods_solr表中
		try {
			solrClient.addBeans(goodsSolr);//商品上架后，同步到 solr中
		} catch (SolrException e) {
			throw new SolrException(ErrorCode.SERVER_ERROR,e);
		}
//		不需要solrClient.commit();   solrconfig.xml中配置<autoCommit>和<autoSoftCommit>软提交
	}
	
	/**
	 * 商品下架后，从solr中删除相应商品
	 * @author BYG 2017-12-13
	 * @param  goodsIDs
	 * @return 
	 */
	public void deleteSolrGoods(String goodsIDs) throws Exception{
		HttpSolrClient solrClient = this.getsolrClient();
		String[] arrayGoodsID = goodsIDs.split(",");
		goodsManageServiceImpl.deleteSolrGoods(arrayGoodsID);//商品下架，同步删除 goods_solr表中商品
		if (arrayGoodsID.length<=1) {
			solrClient.deleteById(goodsIDs);//id和goods_id相同,商品下架，同步删除 solr中商品
		}else{
			List<String> goodsID = Arrays.asList(arrayGoodsID);
			try {
				solrClient.deleteById(goodsID);//商品下架，同步删除 solr中商品
			} catch (SolrException e) {
				throw new SolrException(ErrorCode.SERVER_ERROR,e);
			}
		}
	}
	
	/**
	 * 全局更新solr
	 * @author BYG 2017-12-13
	 * @param  goodsIDs
	 * @return 
	 */
	public void updateGlobalSolr() throws Exception{
		HttpSolrClient solrClient = this.getsolrClient();
		//步骤一、查询当前solr服务器上全部商品ID，用于稍后删除。
		SolrQuery query = new SolrQuery();	//封装查询参数
		query.setQuery("*:*");
		query.addField("id");
		query.setRows(Integer.MAX_VALUE);	//solr默认查询10条记录。
		QueryResponse response = solrClient.query(query);
		List<GoodsSolr> goodsSolr = new ArrayList<GoodsSolr>();
        if (!response.getBeans(GoodsSolr.class).isEmpty()) {
        	goodsSolr = (List<GoodsSolr>) response.getBeans(GoodsSolr.class);
        }
        List<String> goodsIDs = new ArrayList<>();
        for(GoodsSolr g : goodsSolr){
        	goodsIDs.add(g.getId());
        }
		//步骤二、查询当前已上架全部商品，用于solr、及goods_solr表更新。
		List<GoodsSolr> goods = goodsManageServiceImpl.getAllShelvesGoodsForSolr();
		for (GoodsSolr g : goods) {
			g.setId(g.getGoods_id());
			g.setUpdate_time(DateUtil.getTime());
			if (g.getDeposit().equals("100")) {
				g.setDeposit_name("全款");
			} else {
				g.setDeposit_name("定金");
			}
			if (g.getPostage_style().equals("1")) {
				g.setPostage_style_name("包邮");
			} else {
				g.setPostage_style_name("不包邮");
			}
		}
		//步骤三、goods_solr全表删除、及跟根据第二步查询到的数据更新。
		goodsManageServiceImpl.deleteGoodsSolrAll();
		goodsManageServiceImpl.addGoodsToGoodsSolr(goods);
		//步骤四、根据第一步查询到的商品ID删除solr上商品
		solrClient.deleteById(goodsIDs);
		//步骤五、根据第二步查询到的商品，更新到solr
		solrClient.addBeans(goods);
	}
	
	      
}
