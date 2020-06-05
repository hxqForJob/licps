package com.hxq.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.hxq.domain.Contract;
import com.hxq.domain.ContractProduct;
import com.hxq.domain.Dept;
import com.hxq.domain.User;

public interface ContractProductService extends BaseService<ContractProduct> {

	
	/**
	 * 根据船期获取出货信息
	 * @param shipTime
	 * @return
	 */
	public List<ContractProduct> findByShipTime(String shipTime);
	
	
	/**
	 * 查询生产厂家销售情况
	 */
	public List<Object[]> findFactrySale();
	
	
	/**
	 * 查询产品销售情况
	 * @return
	 */
	public List<Object[]> finProductSale();
	
	
	/**
	 * 查询系统访问量
	 * @return
	 */
	public List<Object[]> findOnlineInfo();
}
