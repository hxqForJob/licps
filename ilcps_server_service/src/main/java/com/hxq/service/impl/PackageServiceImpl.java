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
import com.hxq.domain.Contract;
import com.hxq.domain.Export;
import com.hxq.domain.Package;
import com.hxq.service.PackageService;
import com.hxq.utils.ContractState;
import com.hxq.utils.ExportState;

import freemarker.cache.StrongCacheStorage;

/**
 *装箱业务逻辑
 * @author 强仔
 *
 */
@Service("packageService")
public class PackageServiceImpl implements PackageService{

	/**
	 * 装箱数据访问
	 */
	@Autowired
	private PackageDao packageDao;
	
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
	 * 条件查询装箱单
	 */
	@Override
	public List<Package> find(Specification<Package> spec) {
		return packageDao.findAll(spec);
	}
	
	/**
	 * 根据id获取装箱单
	 */
	@Override
	public Package get(String id) {
		return packageDao.findOne(id);
	}

	/**
	 * 分页条件查询装箱单
	 */
	@Override
	public Page<Package> findPage(Specification<Package> spec, Pageable pageable) {
		return packageDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新装箱单
	 */
	@Override
	public void saveOrUpdate(Package entity) {
		//新增
		if(entity.getId()==null)
		{
			String contratcIds="";
			//获取当前装箱单所有的报运单Id
			String [] exportIds = entity.getExportIds().split(", ");
			for (String exportId : exportIds) {
				//修改报运单状态为装箱
				Export export = exportDao.findOne(exportId);
				export.setState(ExportState.PACKAGE);
				exportDao.save(export);
				
				//将报运单下的合同id拼接
				contratcIds+=export.getContractIds()+", ";
				
				//修改报运单下购销合同状态
				String [] exportContractIds=export.getContractIds().split(", ");
				for (String contractId : exportContractIds) {
					Contract contract = contractDao.findOne(contractId);
					contract.setState(ContractState.PACKAGE);
					contractDao.save(contract);
				}
			}
			//设置购销合同
			entity.setExportNos(contratcIds);
		}
		packageDao.save(entity);
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	public void saveOrUpdateAll(Collection<Package> entitys) {
		for (Package pac : entitys) {
			packageDao.save(pac);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		
		
		Package package1 = packageDao.findOne(id);
		//将对应的报运单状态改为草稿
		String[] exportIds = package1.getExportIds().split(", ");
		for (String exportId : exportIds) {
			Export export = exportDao.findOne(exportId);
			export.setState(ExportState.CANCEL);
			exportDao.save(export);
		}
		//将对应的购销合同状态改为已报运
		String[] contractIds=package1.getExportNos().split(", ");
		for (String contractId : contractIds) {
			Contract contract = contractDao.findOne(contractId);
			contract.setState(ContractState.EXPORT);
			contractDao.save(contract);
		}
		packageDao.delete(id);
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
