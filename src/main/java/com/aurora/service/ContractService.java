package com.aurora.service;

import java.util.List;

import com.aurora.entity.ContractManage;
import com.aurora.entity.Page;
/**
 * @Title: ContractService.java 
 * @Package com.aurora.service 
 * @Description: 合同service
 * @author SSY  
 * @date 2018年1月9日 下午5:10:57 
 * @version V1.0
 */
public interface ContractService {
	
	/**
	 * @Title: getContractList 
	 * @Description: 查询符合条件的有效合同列表;
	 * @param    
	 * @return List<ContractManager>  
	 * @author SSY
	 * @date 2018年1月9日 下午4:45:39
	 */
	public List<ContractManage> getContractList(Page page) throws Exception;
	
	/**
	 * @Title: getContractNum 
	 * @Description: 查询符合条件有效合同数量
	 * @param page
	 * @return int  
	 * @author SSY
	 * @date 2018年1月9日 下午4:50:03
	 */
	public int getContractNum(Page page) throws Exception;

	/**
	 * @Title: getContractStateNum 
	 * @Description: 查询符合合同状态的合同数量;
	 * @param    int contractState,int userID
	 * @return int  
	 * @author SSY
	 * @date 2018年1月9日 下午4:52:05
	 */
	public int getContractStateNum(Integer contractState,Integer salesManager) throws Exception;
	
	/**
	 * @Title: getContractDayNum 
	 * @Description: 查询当天有效合同总数;
	 * @param   String date, Integer userID 
	 * @return int  
	 * @author SSY
	 * @date 2018年1月9日 下午4:54:38
	 */
	public int getContractDayNum(String date, Integer salesManager) throws Exception;
	
	/**
	 * @Title: getInquriySalesManage 
	 * @Description: 查询询价单专属销售id
	 * @param    String contractID 
	 * @return int  salesManager
	 * @author SSY 
	 * @date 2018年1月10日 下午8:07:31
	 */
	public int getContractSalesManage(String contractID)throws Exception;
	
	/**
	 * @Title: updateContractFile 
	 * @Description: 保存合同文件url
	 * @param    String contractID, String contractFile, Integer userID
	 * @return int  
	 * @author SSY
	 * @date 2018年1月9日 下午4:58:04
	 */
	public int updateContractFile(String contractID, String contractFile, Integer salesManager) throws Exception;
	
	
	/**
	 * @Title: getLastContractFileUrl 
	 * @Description: 查询上一份合同文件url,销售受限
	 * @param    String contractID, Integer userID
	 * @return String  
	 * @author SSY
	 * @date 2018年1月9日 下午5:01:47
	 */
	public String getLastContractFileUrl(String contractID, Integer salesManager) throws Exception;
	
	/**
	 * @Title: addContract 
	 * @Description: 更新合同
	 * @param  ContractManage contractManager, Integer userID
	 * @return String newContractID
	 * @author SSY
	 * @date 2018年1月9日 下午5:06:40
	 */
	public String addContract(ContractManage contractManager, Integer salesManager) throws Exception;
	
	/**
	 * @Title: updatePayStateContract 
	 * @Description: 合同线下已经付款;
	 * @param    String contractID, Integer userID
	 * @return int  
	 * @author SSY
	 * @date 2018年1月9日 下午5:08:10
	 */
	public int updatePayStateContract(String contractID, Integer salesManager)throws Exception;
	
	
	
	/**
	 * ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ 
	 * **************************************我是分割线******************************************* 
	 * ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
	 */
 
	
}
