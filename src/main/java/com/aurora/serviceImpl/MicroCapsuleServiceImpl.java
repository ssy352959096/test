package com.aurora.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.dao.DAO;
import com.aurora.service.MicroCapsuleService;
/**
 * @Title: MicroCapsuleServiceImpl.java 
 * @Package com.aurora.serviceImpl 
 * @Description: 微仓
 * @author SSY  
 * @date 2018年6月27日 下午2:34:31 
 * @version V1.0
 */
@Service
public class MicroCapsuleServiceImpl implements MicroCapsuleService {

	@Autowired DAO daoSupport;
	/**
	 * @Title: updateFreeDemurrage 
	 * @Description: 定时更新微仓商品免仓期
	 * @param    
	 * @return int  
	 * @author SSY
	 * @date 2018年6月27日 下午2:36:17
	 */
	@Override
	public int updateFreeDemurrage() throws Exception{
		int num = (int)daoSupport.update("MicroCapsuleWriteMapper.updateFreeDemurrage");
		return num;
	}
	
	
}
