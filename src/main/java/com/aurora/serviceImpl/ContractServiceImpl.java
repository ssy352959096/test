package com.aurora.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.entity.ContractGoods;
import com.aurora.entity.ContractManage;
import com.aurora.entity.Page;
import com.aurora.service.ContractService;
import com.aurora.util.Const;
import com.aurora.util.CustomException;
import com.aurora.util.DateUtil;
import com.aurora.util.Jurisdiction;
import com.aurora.util.PageData;

/**
 * @Title: ContractServiceImpl.java 
 * @Package com.aurora.serviceImpl 
 * @Description:  合同serverImpl  
 * @author SSY  
 * @date 2018年1月9日 下午4:48:36 
 * @version V1.0
 */
@Service("contractServiceImpl")
public class ContractServiceImpl implements ContractService{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;
	
	/**
	 * @Title: getContractList 
	 * @Description: 查询符合条件的有效合同列表;
	 * @param    
	 * @return List<ContractManager>  
	 * @author SSY
	 * @date 2018年1月9日 下午4:45:39
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContractManage> getContractList(Page page) throws Exception{
		return (List<ContractManage>) daoSupport.findForList("ContractReadMapper.getContractList", page);
	}
	
	/**
	 * @Title: getContractNum 
	 * @Description: 查询符合条件有效合同数量
	 * @param page
	 * @return int  
	 * @author SSY
	 * @date 2018年1月9日 下午4:50:03
	 */
	@Override
	public int getContractNum(Page page) throws Exception{
		Object object = daoSupport.findForObject("ContractReadMapper.getContractNum", page);
		return object != null ? (int)object : 0;
	}
	
	
	/**
	 * @Title: getContractStateNum 
	 * @Description: 查询符合合同状态的合同数量;
	 * @param    int contractState,int userID
	 * @return int  
	 * @author SSY
	 * @date 2018年1月9日 下午4:52:05
	 */
	public int getContractStateNum(Integer contractState,Integer salesManager) throws Exception{
		PageData pd = new PageData();
		pd.put("contractState", contractState);
		pd.put("salesManager", salesManager);
		Object object = daoSupport.findForObject("ContractReadMapper.getContractStateNum", pd);
		return object != null ? (int)object : 0;
	}
	
	/**
	 * @Title: getContractDayNum 
	 * @Description: 查询当天有效合同总数;
	 * @param   String date, Integer userID 
	 * @return int  
	 * @author SSY
	 * @date 2018年1月9日 下午4:54:38
	 */
	public int getContractDayNum(String date, Integer salesManager) throws Exception{
		PageData pd = new PageData();
		pd.put("date", date);
		pd.put("salesManager", salesManager);
		Object object = daoSupport.findForObject("ContractReadMapper.getContractDayNum", pd);
		return object != null ? (int)object : 0;
	}
	
	/**
	 * @Title: getInquriySalesManage 
	 * @Description: 查询询价单专属销售id
	 * @param    String contractID 
	 * @return int  salesManager
	 * @author SSY 
	 * @date 2018年1月10日 下午8:07:31
	 */
	@Override
	public int getContractSalesManage(String contractID)throws Exception{
		return (int)daoSupport.findForObject("ContractReadMapper.getContractSalesManage", contractID);
	}
	
	/**
	 * @Title: updateContractFile 
	 * @Description: 保存合同文件url
	 * @param    String contractID, String contractFile, Integer userID
	 * @return int  
	 * @author SSY
	 * @date 2018年1月9日 下午4:58:04
	 */
	public int updateContractFile(String contractID, String contractFile, Integer salesManager) throws Exception{
		ContractManage contract = this.getContract(contractID, salesManager);
		if (salesManager!=null&&salesManager!=contract.getSalesManager()) {
			throw new CustomException("没有此合同!");
		}
		if (contract.getContractState()!=1) {
			throw new CustomException("合同文件已经上传,如需修改,请先创建新合同!");
		}
		PageData pd = new PageData();
		pd.put("contractID", contractID);
		pd.put("contractFile", contractFile);
		pd.put("uploadOperator", Jurisdiction.getUserEmail());
		pd.put("uploadTime", DateUtil.getTime());
		return (int) daoSupport.update("ContractWriteMapper.updateContractFile", pd);
	}
	
	/**
	 * @Title: getLastContractFileUrl 
	 * @Description: 查询上一份合同文件url,销售受限
	 * @param    String contractID, Integer userID
	 * @return String  
	 * @author SSY
	 * @date 2018年1月9日 下午5:01:47
	 */
	@Override
	public String getLastContractFileUrl(String contractID, Integer salesManager) throws Exception{
		PageData pd = new PageData();
		pd.put("contractID", contractID);
		pd.put("salesManager", salesManager);
		return (String) daoSupport.findForObject("ContractReadMapper.getLastContractFileUrl", pd);
	}
	
	/**
	 * @Title: getContract
	 * @Description: 查询合同,销售受限
	 * @param    String contractID, Integer userID
	 * @return String  
	 * @author SSY
	 * @date 2018年1月9日 下午5:01:47
	 */
	private ContractManage getContract(String contractID, Integer salesManager) throws Exception{
		PageData pd = new PageData();
		pd.put("contractID", contractID);
		pd.put("salesManager", salesManager);
		return (ContractManage) daoSupport.findForObject("ContractReadMapper.getContract", pd);
	}
	
	/**
	 * @Title: addContract 
	 * @Description: 更新合同
	 * @param  ContractManage contractManager, Integer userID
	 * @return void  newContractID
	 * @author SSY
	 * @date 2018年1月9日 下午5:06:40
	 */
	@Override
	public String addContract(ContractManage contractManager, Integer salesManager) throws Exception{
		String contractID = contractManager.getSourceID();
		ContractManage contractSource = this.getContract(contractID, salesManager);
		if (contractSource==null) {
			throw new CustomException("操作失败!没有此合同!");
		}
		if (contractSource.getContractState()!=1 && contractSource.getContractState()!=2) {
			throw new CustomException("操作失败!合同状态错误!");
		}
//		if (contractSource.getContractState()==1 ) {//合同待上传,只需修改合同文件,并保存合同地址;
//			this.updateContractFile(contractSource.getContractID(), contractManager.getContractFile(), salesManager);
//			return contractSource.getContractID();
//		}else{//已上传合同文件,创建一份新合同
//			this.addContract(contractManager, salesManager);
//		}
		String newContractID = DateUtil.getSdfTimes()+contractSource.getCustomerID();
		contractManager.setContractID(newContractID);
		contractManager.setInquiryID(contractSource.getInquiryID());
		contractManager.setSalesManager(contractSource.getSalesManager());
		contractManager.setCreateTime(DateUtil.getTime());
		contractManager.setSourceID(contractSource.getContractID());
		contractManager.setCustomerID(contractSource.getCustomerID());
		contractManager.setCustomerName(contractSource.getCustomerName());
		contractManager.setCustomerEmail(contractSource.getCustomerEmail());
		contractManager.setCustomerMobile(contractSource.getCustomerMobile());
		contractManager.setCompany(contractSource.getCompany());
		contractManager.setAddress(contractSource.getAddress());
		//计算合同总邮费及金额;
		BigDecimal contractMoney = new BigDecimal("0.00");
		BigDecimal totalPostage = new BigDecimal("0.00");// 
		List<ContractGoods> contractGoodsList = contractManager.getContractGoodsList();
		for (ContractGoods contractGoods : contractGoodsList) {
			BigDecimal goodsPrice = contractGoods.getGoodsPrice();
			BigDecimal goodsNum = contractGoods.getGoodsNum();
			BigDecimal volume = contractGoods.getVolume();
			BigDecimal palletVolume = contractGoods.getPalletVolume();
			BigDecimal palletPrice = contractGoods.getPalletPrice();
			if (goodsPrice==null||volume==null||palletVolume==null||palletPrice==null||goodsNum==null) {
				throw new CustomException("操作失败!合同报价参数错误!");
			}
			contractGoods.setContractID(newContractID);
			contractGoods.setPostage(palletPrice.multiply(volume.divide(palletVolume,2, RoundingMode.HALF_UP)));
			contractGoods.setSupplyPrice(goodsNum.multiply(goodsPrice));
			totalPostage = totalPostage.add(contractGoods.getPostage());
			contractMoney = contractMoney.add(contractGoods.getSupplyPrice());
		}
		contractMoney.add(totalPostage);
		contractManager.setTotalPostage(totalPostage);
		contractManager.setContractMoney(contractMoney);
		daoSupport.save("ContractWriteMapper.addContract", contractManager);//新增合同单
		daoSupport.save("ContractWriteMapper.addContractGoods", contractGoodsList);//批量新增合同商品
		//原合同失效;
		daoSupport.update("ContractWriteMapper.updateInvalidContract", contractID);
		return newContractID;
	}
	
	
	
	
	/**
	 * @Title: updatePayStateContract 
	 * @Description: 合同线下已经付款;
	 * @param    String contractID, Integer userID
	 * @return int  
	 * @author SSY
	 * @date 2018年1月9日 下午5:08:10
	 */
	public int updatePayStateContract(String contractID, Integer salesManager)throws Exception{
		int contractSalesManage = getContractSalesManage(contractID);
		if (salesManager!=null&&salesManager!=contractSalesManage) {
			throw new CustomException("没有此合同!");
		}
		return (int) daoSupport.update("ContractWriteMapper.updatePayStateContract", contractID);
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ 
	 * **************************************我是分割线******************************************* 
	 * ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
	 */
}

