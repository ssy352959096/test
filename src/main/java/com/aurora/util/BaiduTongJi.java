package com.aurora.util;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * @Description:百度统计api接口,用于接收百度统计数据;  
 * @author: SSY   
 * @date:   2017-11-18
 * @version V1.0 
 */
public class BaiduTongJi {
	
	
	/**
	 * 查询趋势数据
	 * getData  overview/getTimeTrendRpt方法
	 */
	public static final String METHOD_DistrictRpt = "overview/getDistrictRpt";	
	
	/**
	 * 查询趋势数据
	 * getData  overview/getTimeTrendRpt方法
	 */
	public static final String METHOD_TIME_TREND = "overview/getTimeTrendRpt";	 
	
	/**
	 * 查询定义的事件数据
	 * getData  custom/event_track/a方法
	 */
	public static final String METHOD_EVENT_TRACK = "custom/event_track/a";	
	
	/**
	 * 查询全部流量来源,"source/all/a";	
	 */
	public static final String METHOD_SOURCE_ALL = "source/all/a";	
	 
	/**
	 * 查询地域分布--按省
	 * getData 	visit/district/a 
	 */
	public static final String METHOD_VISIT_DISTRICT = "visit/district/a";	
	
	/**
	 * 查询地域分布--按省
	 * getData 	visit/world/a
	 */
	public static final String METHOD_VISIT_WORLD = "visit/world/a";	
	
	/**
	 * 参数--要查询的数据:PV, 
	 */
	public static final String METRICS_PV = "pv_count";
	/**
	 * 参数--要查询的数据:UV,  
	 */
	public static final String METRICS_UV = "visitor_count";
	
	/**
	 * 参数--要查询的数据:ip数 和地域uv分布
	 */
	public static final String METRICS_IPUV = "visitor_count,ip_count";
	
	/**
	 * 参数--要查询的数据:事件数量 event_count
	 */
	public static final String METRICS_EVENT_COUNT = "event_count";
	
	/**
	 * 参数--要查询的数据:PV,平均访问时长,跳出率;
	 * "pv_count,avg_visit_time,bounce_ratio";
	 * 使用时请注意要调用的方法是否有这些属性
	 */
	public static final String METRICS = "pv_count,avg_visit_time,bounce_ratio";	
	
	/**
	 * 测试
	 * @param  
	 */
//	@Test
//	public void getBaiduTongjiDate() {
//		getSiteList();//测试连接站点
//		String metrics = BaiduTongJi.METRICS;
//		String method = BaiduTongJi.METHOD_TIME_TREND;
//		String startDate = DateUtil.getDays();
//		String endDate = DateUtil.getDays();
	/**
	 * @Title: getBaiduTongjiDate 
	 * @Description: 百度统计取数据;
	 * @param    
	 * @return List<Object>  
	 * @author SSY
	 * @date 2018年3月27日 下午4:22:58
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getBaiduTongjiDate(String startDate,String endDate,String method,String metrics) {
		JSONObject data = getData(startDate,endDate,method,metrics);
		Map<String, Object> object2 = (Map<String, Object>)data.get("body");
		Map<String, Object> object3 = ((List<Map<String, Object>>)object2.get("data")).get(0);
		Map<String, Object> object4 = (Map<String, Object>)object3.get("result");
		List<Object> object5 = (List<Object>)object4.get("items");
		return object5;
	}
	 
	
	/**
	 * 获取用户的站点 列表
	 */
	public static JSONObject getSiteList() {
		System.out.println("------百度统计API 获取站点信息开始--------");
		JSONObject content = new JSONObject();
		content.put("header", init());
		JSONObject body = new JSONObject();
		content.put("body", body);

		//baidu统计站点登陆接口
		String url = "https://api.baidu.com/json/tongji/v1/ReportService/getSiteList";
		String result = connet(url, content.toJSONString());
		
		System.out.println("--返回数据--"+JSON.parseObject(result));
		System.out.println("------百度统计API 获取站点信息结束--------");
		return JSON.parseObject(result);
	}
	
	/**
	 * 根据站点 ID 获取站点报告数据
	 */
	private static JSONObject getData(String startDate,String endDate,String method,String metrics) {
		System.out.println("------百度统计API 获取Data信息开始---------");
		JSONObject content = new JSONObject();
		content.put("header", init());
		JSONObject body = new JSONObject();
		body.put("site_id", 11409947);//中国版站点;
		body.put("method", method);
		body.put("start_date", startDate);
		body.put("end_date", endDate);
		body.put("metrics", metrics);
		body.put("gran", "day");
		body.put("max_results", "0");
		
		content.put("body", body);

		//baidu统计接口
		String url = "https://api.baidu.com/json/tongji/v1/ReportService/getData";
		String result = connet(url, content.toJSONString());
		
//		System.out.println("--返回数据--"+result);
		System.out.println("------百度统计API 获取Data信息结束--------");
		
		
		return JSON.parseObject(result);
	}
	
	/**
	 * 初始化百度统计登录信息
	 */
	private static JSONObject init(){
		JSONObject header = new JSONObject();
		header.put("username", "ssy352959096");//百度统计账号;
		header.put("password", "LOii20160825");//密码
		header.put("token", "d7a6c1cec53d597ee37e1e55cdfb7225");//token值
		header.put("account_type", "1");
		return header;
	}
	
	/**
	 * 链接百度统计平台获取数据
	 */
	private static String connet(String url, String content) {
		URLConnection connection;
		try {
			System.out.println("------百度统计API 链接百度接口获取数据开始----");
			connection = new java.net.URL(url).openConnection();
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Length", "" + content.length());
			connection.setRequestProperty("Cache-Control", "no-cache");
			connection.setRequestProperty("Content-Type", "application/json");
			DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
			stream.write(content.getBytes("UTF-8"));
			stream.close();
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			StringBuffer sb = new StringBuffer();
			String str = br.readLine();
			while (str != null) {
				sb.append(str);
				str = br.readLine();
			}
			br.close();
			System.out.println("------百度统计API 链接百度接口获取数据成功---------");
			return sb.toString();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("------百度统计API 链接百度接口获取数据失败---------");
			System.out.println("--失败信息--"+e1.getMessage());
		}
		return "";
	}
	
	

}