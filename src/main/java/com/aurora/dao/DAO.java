package com.aurora.dao;
/**
 * @author FH Q313596790
 * 修改时间：2015、12、11
 */
public interface DAO {
	
	/**
	 * 保存对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object save(String str, Object obj) throws Exception;
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object update(String str, Object obj) throws Exception;
	
	/**
	 * @Title: update 
	 * @Description: 修改对象
	 * @param    
	 * @return Object  
	 * @author SSY
	 * @date 2018年1月23日 下午2:33:01
	 */
	public Object update(String str) throws Exception;
	
	
	/**
	 * 删除对象 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object delete(String str, Object obj) throws Exception;
	
	/**
	 * 删除对象 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public Object delete(String str) throws Exception;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String str, Object obj) throws Exception;

	/**
	 * 查找对象
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String str) throws Exception;
	
	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForList(String str, Object obj) throws Exception;
	
	/**
	 * 查找对象
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public Object findForList(String str) throws Exception;
	
	/**
	 * 查找对象封装成Map
	 * @param s
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForMap(String sql, Object obj, String key , String value) throws Exception;
	
}
