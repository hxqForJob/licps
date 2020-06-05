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
import com.hxq.domain.Contract;
import com.hxq.service.ContractService;
import com.hxq.utils.UtilFuns;

/**
 *购销合同业务逻辑
 * @author 强仔
 *
 */
@Service("contractService")
public class ContractServiceImpl implements ContractService{

	@Autowired
	private ContractDao contractDao;
	
	/**
	 * 条件查询购销合同
	 */
	@Override
	public List<Contract> find(Specification<Contract> spec) {
		// TODO Auto-generated method stub
		return contractDao.findAll(spec);
	}
	
	/**
	 * 根据id获取购销合同
	 */
	@Override
	public Contract get(String id) {
		// TODO Auto-generated method stub
		return contractDao.findOne(id);
	}

	/**
	 * 分页条件查询购销合同
	 */
	@Override
	public Page<Contract> findPage(Specification<Contract> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return contractDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新购销合同
	 */
	@Override
	public void saveOrUpdate(Contract entity) {
		// TODO Auto-generated method stub
		if(UtilFuns.isEmpty(entity.getId()))
		{
			entity.setState(0);
			entity.setTotalAmount(0.0);
		}
		contractDao.save(entity);
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	public void saveOrUpdateAll(Collection<Contract> entitys) {
		for (Contract contract : entitys) {
			contractDao.save(contract);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		contractDao.delete(id);
	}
	
	
	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		for (String string : ids) {
			contractDao.delete(string);
		}
	}

	
	
	

}
