package com.hxq.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hxq.dao.ContractDao;
import com.hxq.dao.ContractProductDao;
import com.hxq.domain.Contract;
import com.hxq.domain.ContractProduct;
import com.hxq.domain.ExtCproduct;
import com.hxq.service.ContractProductService;
import com.hxq.utils.UtilFuns;

/**
 *货物业务逻辑
 * @author 强仔
 *
 */
@Service("contractProductService")
public class ContractProductServiceImpl implements ContractProductService{

	@Autowired
	private ContractProductDao contractProductDao;
	
	@Autowired
	private ContractDao contractDao;
	
	/**
	 * 条件查询货物
	 */
	@Override
	public List<ContractProduct> find(Specification<ContractProduct> spec) {
		// TODO Auto-generated method stub
		return contractProductDao.findAll(spec);
	}
	
	/**
	 * 根据id获取货物
	 */
	@Override
	public ContractProduct get(String id) {
		// TODO Auto-generated method stub
		return contractProductDao.findOne(id);
	}

	/**
	 * 分页条件查询货物
	 */
	@Override
	public Page<ContractProduct> findPage(Specification<ContractProduct> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return contractProductDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新货物
	 */
	@Override
	public void saveOrUpdate(ContractProduct entity) {
		// TODO Auto-generated method stub
		Double totalMoney=0.0;
		if(UtilFuns.isNotEmpty(entity.getPrice())&&UtilFuns.isNotEmpty(entity.getCnumber()))
		{
			
			totalMoney=entity.getPrice()*entity.getCnumber();
		}
		if(UtilFuns.isEmpty(entity.getId()))
		{
			//新增
			
			
			//数量和单价不为空,分散计算
			entity.setAmount(totalMoney);
			Contract contract = contractDao.findOne(entity.getContract().getId());
			contract.setTotalAmount(contract.getTotalAmount()+totalMoney);
			contractDao.save(contract);
		}
		else {
			//修改
			Double oldTotalMoney=contractProductDao.findOne(entity.getId()).getAmount();
			if(oldTotalMoney!=totalMoney)
			{
				entity.setAmount(totalMoney);
				Contract contract = contractDao.findOne(entity.getContract().getId());
				contract.setTotalAmount(contract.getTotalAmount()-oldTotalMoney+totalMoney);
				contractDao.save(contract);
			}
		}
		contractProductDao.save(entity);
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	public void saveOrUpdateAll(Collection<ContractProduct> entitys) {
		for (ContractProduct contractProduct : entitys) {
			contractProductDao.save(contractProduct);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		Double money=0.0;
		ContractProduct findOne = contractProductDao.findOne(id);
		for(ExtCproduct  extCproduct : findOne.getExtCproducts())
		{
			if(UtilFuns.isNotEmpty(extCproduct.getAmount()))
			{
				money+=extCproduct.getAmount();
			}
			
		}
		//分散计算减去购销合同的总金额
		Contract contract = contractDao.findOne(findOne.getContract().getId());
		contract.setTotalAmount(contract.getTotalAmount()-findOne.getAmount()-money);
		contractDao.save(contract);
		contractProductDao.delete(id);
		
	}
	
	
	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		for (String string : ids) {
			deleteById(string);
		}
	}

	/**
	 * 根据船期获取出货信息
	 * @param shipTime
	 * @return
	 */
	@Override
	public List<ContractProduct> findByShipTime(String shipTime) {
		// TODO Auto-generated method stub
		return contractProductDao.findByShipTime(shipTime);
	}

	/**
	 * 查询生产厂家销售情况
	 */
	@Override
	public List<Object[]> findFactrySale() {
		// TODO Auto-generated method stub
		return contractProductDao.findFactrySale();
	}

	/**
	 * 查询产品销售情况
	 */
	@Override
	public List<Object[]> finProductSale() {
		// TODO Auto-generated method stub
		return contractProductDao.finProductSale();
	}

	/**
	 * 查询系统访问量
	 */
	@Override
	public List<Object[]> findOnlineInfo() {
		// TODO Auto-generated method stub
		return contractProductDao.findOnlineInfo();
	}

	
	
	

}
