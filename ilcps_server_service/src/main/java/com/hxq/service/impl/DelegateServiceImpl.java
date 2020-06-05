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
import com.hxq.dao.ExportDao;
import com.hxq.dao.PackageDao;
import com.hxq.dao.DelegateDao;
import com.hxq.domain.Contract;
import com.hxq.domain.ContractProduct;
import com.hxq.domain.Export;
import com.hxq.domain.Package;
import com.hxq.domain.Delegate;
import com.hxq.service.DelegateService;
import com.hxq.utils.ContractState;
import com.hxq.utils.ExportState;
import com.hxq.utils.PackageState;
import com.hxq.utils.file.StringUtil;

import freemarker.cache.StrongCacheStorage;

/**
 *委托业务逻辑
 * @author 强仔
 *
 */
@Service("delegateService")
public class DelegateServiceImpl implements DelegateService{

	/**
	 * 委托数据访问
	 */
	@Autowired
	private DelegateDao delegateDao;
	
	/**
	 * 报运数据访问
	 */
	@Autowired
	private ExportDao exportDao;
	
	/**
	 * 购销合同数据访问
	 */
	@Autowired
	private ContractDao contractDao;
	
	/**
	 * 装箱数据访问
	 */
	@Autowired
	private PackageDao packageDao;
	/**
	 * 条件查询委托单
	 */
	@Override
	public List<Delegate> find(Specification<Delegate> spec) {
		return delegateDao.findAll(spec);
	}
	
	/**
	 * 根据id获取委托单
	 */
	@Override
	public Delegate get(String id) {
		return delegateDao.findOne(id);
	}

	/**
	 * 分页条件查询委托单
	 */
	@Override
	public Page<Delegate> findPage(Specification<Delegate> spec, Pageable pageable) {
		return delegateDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新委托单
	 */
	@Override
	@Deprecated
	public void saveOrUpdate(Delegate entity) {
		
	}
	
	/**
	 * 新增或修改
	 * @param entity 委托单实体
	 * @param id 装箱Id null 为更新 
	 */
	@Override
	public void saveOrUpdate(Delegate entity,String id) {
		//新增
		if(id!=null)
		{
			//货物总箱数
			int quantity=0;
			entity.setId(id);
			Package package1 = packageDao.findOne(id);
			//修改装箱单状态
			package1.setState(PackageState.DELEGATE);
			packageDao.save(package1);
			
			//购销合同id集合
			String [] contractIds=package1.getExportNos().split(", ");
			//报运单id集合
			String [] exportIds=package1.getExportIds().split(", ");
			//修改购销合同状态
			for (String contractId : contractIds) {
				if(contractId!=null&&!"".equals(contractId))
				{
					Contract contract = contractDao.findOne(contractId);
					contract.setState(ContractState.DELEGATE);
					contractDao.save(contract);
				}
			}
			//修改报运单状态
			for (String exportId : exportIds) {
				if(exportId!=null&&!"".equals(exportId))
				{
					Export export = exportDao.findOne(exportId);
					//计算总箱数
					quantity+=export.getBoxNums();
					export.setState(ExportState.DELEGATE);
					exportDao.save(export);
				}
			}
			//添加数量
			entity.setQuantity(quantity);
		}
		delegateDao.save(entity);
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	@Deprecated
	public void saveOrUpdateAll(Collection<Delegate> entitys) {
		for (Delegate pac : entitys) {
			delegateDao.save(pac);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		
		
		Package package1 = packageDao.findOne(id);
		
		//将对应的装箱单状态改为草稿
		package1.setState(PackageState.CANCEL);
		packageDao.save(package1);
		
		//将对应的报运单状态改为装箱
		String[] exportIds = package1.getExportIds().split(", ");
		for (String exportId : exportIds) {
			if(exportId!=null&&!"".equals(exportId)){
				Export export = exportDao.findOne(exportId);
				export.setState(ExportState.PACKAGE);
				exportDao.save(export);
			}
			
		}
		//将对应的购销合同状态改为已装箱
		String[] contractIds=package1.getExportNos().split(", ");
		for (String contractId : contractIds) {
			if(contractId!=null&&!"".equals(contractId)){
			Contract contract = contractDao.findOne(contractId);
			contract.setState(ContractState.PACKAGE);
			contractDao.save(contract);
			}
		}
		delegateDao.delete(id);
	}
	
	
	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for (String string : ids) {
			deleteById(string);
		}
	}

	
	

}
