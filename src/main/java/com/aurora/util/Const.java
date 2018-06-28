package com.aurora.util;

import org.springframework.context.ApplicationContext;
/**
 * 项目名称：
 * 创建:BYG 2017/5/25
 * 修改：
 * @version
*/
public class Const {
	public static final String SESSION_VERIFICATION_CODE = "sessionVerificationCode";	//验证码
	public static final String SESSION_USER = "sessionUser";							//session用的用户
	public static final String SESSION_USER_NAME = "userName";					//用户名
	public static final String SESSION_USER_ID = "sessionUserID";							//session用的用户ID
	public static final String SESSION_USER_EMAIL = "sessionUserEmail";							//session用的用户邮箱
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";				//用户权限
	public static final String sSESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_MENU_LIST = "sessionMenuList";					//当前菜单
	public static final String SESSION_ALL_MENU_LIST = "sessionAllMenuList";			//角色所属全部菜单
	public static final String SESSION_RIGHTS = "sessionRights";						//权限
	public static final String SESSION_USER_PASSWORD = "userPassword";			
	public static final String SESSION_USER_ROLE = "userRole";					//用户角色对象
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String LOGIN = "/login/toLogin";				//登录地址
	public static final String SYSNAME = "admin/config/SYSNAME.txt";	//系统名称路径
	//public static final String PAGE	= "admin/config/PAGE.txt";			//分页条数配置路径
	public static final String EMAIL = "admin/config/EMAIL.txt";		//邮箱服务器配置路径
	public static final String SMS1 = "admin/config/SMS1.txt";			//短信账户配置路径1
	public static final String SMS2 = "admin/config/SMS2.txt";			//短信账户配置路径2
	public static final String FWATERM = "admin/config/FWATERM.txt";	//文字水印配置路径
	public static final String IWATERM = "admin/config/IWATERM.txt";	//图片水印配置路径
	public static final String WEIXIN	= "admin/config/WEIXIN.txt";	//微信配置路径
	public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";//WEBSOCKET配置路径
	public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";	//图片上传路径
	public static final String FILEPATHFILE = "uploadFiles/file/";		//文件上传路径
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径
	public static final String NO_INTERCEPTOR_PATH1 = ".*/((login)|(logout)|(code)|(static)|(websocket)).*";	//不对匹配该值的访问路径拦截（正则）
	public static final String NO_INTERCEPTOR_PATH2 = ".*/((code)|(upload)).*";											//用户处于登陆状态不对匹配该值的访问路径拦截（正则）
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	
//	public static final String ERROR_MSG_BACK_PAGE = "系统可能走神了,刷新重试或联系后端技术人员!";
//	public static final String ERROR_MSG_BACK_AJAX = "操作失败!重试或联系后端技术人员!";
//	public static final String ERROR_MSG_FRONT_PAGE = "系统可能走神了,刷新重试或联系络驿吴彦祖!";
//	public static final String ERROR_MSG_FRONT_AJAX = "操作失败!重试或联系络驿吴彦祖!";
	
	/**
	 * APP Constants
	 */
	//app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries","uname","passwd","title","full_name","company_name","countries_code","area_code","telephone","mobile"};
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍","邮箱帐号","密码","称谓","名称","公司名称","国家编号","区号","电话","手机号"};
	
	//app根据用户名获取会员信息接口_请求协议中的参数
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};
	
	/**------------------------------------------------------------------------------------------------
	 * -----------------------------------------------分割线--------------------------------------------
	 * ------------------------------------------------------------------------------------------------
	 */
	
	/**
	 * 销售角色id		
	 */
	public static final String SALES_ROLE_ID = "6"; 
	/**
	 * 客服角色id
	 */
	public static final String SERVICE_ROLE_ID = "4"; 
	/**
	 * 运营角色id	
	 */
	public static final String OPERATION_ROLE_ID = "5"; 
	/**
	 * 公池客户编码;	
	 */
	public static final int  PUBLIC_POOL_CODE = 888; 
	
	/**
	 * 商品是否是折扣价
	 */
	public static final int GOODS_IS_ACTIVITY = 1; //1是折扣价
	/**
	 * 限时活动3已过期 
	 */
	public static final int ACTIVITY_DATED_STATE = 3; //
	/**
	 * 限时活动2生效中 
	 */
	public static final int ACTIVITY_VALID_STATE = 2; //
	/**
	 * 限时活动1未生效 
	 */
	public static final int ACTIVITY_INVALID_STATE = 1;  
	/**
	 * 1.banner专题;2.保税仓专题;10000.类目1---大额采购母婴儿童专题;20000.类目2---美妆护类专题;30000.类目3---厨卫家居类专题;40000.类目4---营养保健;50000. 类目5--数码家电
	 */
	/**
	 * 专题归属模块 1.banner专题;
	 */
	public static final int SPECIAL_BANNER_MODULE = 1;  
	/**
	 * 专题归属模块 2.保税仓专题;
	 */
	public static final int SPECIAL_BONDED_MODULE = 2;  
	/**
	 * 专题归属模块 10000.类目1---大额采购母婴儿童专题
	 */
	public static final int SPECIAL_CATEGORY1_MODULE = 10000;  
	/**
	 * 专题归属模块 20000.类目2---美妆护类专题;
	 */
	public static final int SPECIAL_CATEGORY2_MODULE = 20000;  
	/**
	 * 专题归属模块 30000.类目3---厨卫家居类专题
	 */
	public static final int SPECIAL_CATEGORY3_MODULE = 30000;  
	/**
	 * 专题归属模块 40000.类目4---营养保健类专题
	 */
	public static final int SPECIAL_CATEGORY4_MODULE = 40000;  
	/**
	 * 专题归属模块 50000.类目5--数码家电类专题
	 */
	public static final int SPECIAL_CATEGORY5_MODULE = 50000;  
	/**
	 * 首页关键词类型1.搜索框搜索关键词; 2.其他关键词; 3.保税仓关键词; 10000.类目1--母婴儿童类目商品关键词;20000.类目2---美妆护肤 30000.类目3---厨卫家居类专题;40000.类目4---营养保健;50000. 类目5--数码家电
	 */
	/**
	 *  首页关键词类型1.搜索框搜索关键词;
	 */
	public static final int KEYWORD_SEARCH = 1;
	/**
	 *  首页关键词类型2.其他关键词
	 */
	public static final int KEYWORD_OVERSEA = 2;
	/**
	 *  首页关键词类型3.保税仓关键词;
	 */
	public static final int KEYWORD_BONDED = 3;
	/**
	 *  首页关键词类型4. 类目1--母婴儿童类目商品关键词
	 */
	public static final int KEYWORD_CATEGORY1 = 10000;
	/**
	 *  首页关键词类型5.类目2---美妆护肤关键词
	 */
	public static final int KEYWORD_CATEGORY2 = 20000;
	/**
	 *  首页关键词类型 6.类目3---厨卫家居类关键词;
	 */
	public static final int KEYWORD_CATEGORY3 = 30000;
	/**
	 *  首页关键词类型7.类目4---营养保健关键词;
	 */
	public static final int KEYWORD_CATEGORY4 = 40000;
	/**
	 *  首页关键词类型8. 类目5--数码家电关键词;
	 */
	public static final int KEYWORD_CATEGORY5 = 50000;
	
	/**
	 *  首页品类展示一级类目常量字符串;
	 */
	public static final String CATEGORYID_FLOORS = "10000,20000,30000,40000,50000";
	
}
