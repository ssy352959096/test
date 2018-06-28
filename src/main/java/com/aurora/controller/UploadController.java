package com.aurora.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aurora.util.AliyunOSSClientUtil;
import com.aurora.util.AppUtil;
import com.aurora.util.DateUtil;
import com.aurora.util.PageData;
import com.aurora.util.Tools;


@Controller
@RequestMapping(value="/upload")
public class UploadController extends BaseController{
	
	String menuUrl = "upload.do"; //菜单地址(权限用)
	
	/**单图片上传
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadImage")
	@ResponseBody
	public Object uploadImage(HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		if(file.isEmpty()){ 
			logger.info("【failed:未选择文件!】");
			result = "failed";
			msg = "未选择文件!异常码：U20067";
	    }else{
	    	try{
	    	String fileName =  DateUtil.getSdfTimes() + file.getOriginalFilename();
    		result = AliyunOSSClientUtil.uploadPicture2OSS(file , fileName); 					//result = "success" 或 "error"
			String url = "https://aurora-picture.oss-cn-hangzhou.aliyuncs.com/" + fileName; //文件存储地址
			map.put("url", url);
	    	}catch (Exception e) {
	    		e.printStackTrace();
				result = "error";
				msg = "图片上传失败!异常码：S40099";
				logger.info("【error:文件上传失败!】");
			}
	    }
		map.put("result", result);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
	/**单图片删除（图片路径未保存数据库）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteImage")
	@ResponseBody
	public Object deleteImage(String url) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		if(url.isEmpty()){ 
			logger.info("【failed:未选择删除图片!】");
			result = "failed";
			msg = "未选择删除图片!异常码：U20068";
	    }else{
	        try {
	        	String fileName = url.split(".com/")[1];
	    		AliyunOSSClientUtil.deletePicture(fileName);
	    		result = "success";
			} catch (Exception e) {
				result = "error";
				msg = "图片删除失败!异常码：S40100";
				logger.info("【error:图片删除失败!】");
			}
	    }
		map.put("msg", msg);
		map.put("result", result);
		return AppUtil.returnObject(pd, map);
	}
	
	/**单文件上传
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadfile")
	@ResponseBody
	public Object uploadFile(HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		if(file.isEmpty()){ 
			logger.info("【failed:未选择文件!】");
			result = "failed";
			msg = "未选择文件!异常码：U20069";
	    }else{
	    	try{
		    	String fileName = DateUtil.getSdfTimes()+file.getOriginalFilename();
	    		result = AliyunOSSClientUtil.uploadFile2OSS(file , fileName); 					//result = "success" 或 "error"
				String url = "https://aurora-file.oss-cn-hangzhou.aliyuncs.com/" + fileName; 		//文件存储地址
				map.put("url", url);
				result = "success";
	    	} catch (Exception e) {
	    		result = "error";
				msg = "文件上传失败!异常码：S40101";
				logger.info("【error:文件上传失败!】");
	    	}
	    }
		map.put("result", result);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
	/**单文件下载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/downloadfile")
	@ResponseBody
	public Object uploadFile(String url, File file) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String msg = "";
		if(Tools.notEmptys(url)){ 
			logger.info("【failed:无效的url!】");
			result = "failed";
			msg = "无效的url!异常码：U20070";
	    }else{ 
	    	try {
	    		String fileName = url.substring(url.lastIndexOf("/") + 1); 
	    		result = AliyunOSSClientUtil.getObjectFile(fileName , file); 			//result = "success" 或 "error"		
	    	} catch (Exception e) {
				result = "error";
				msg = "文件下载失败!异常码：S40102";
				logger.info("【error:文件下载失败!】");
	    	}
	    }
		map.put("url", url);
		map.put("result", result);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
}
