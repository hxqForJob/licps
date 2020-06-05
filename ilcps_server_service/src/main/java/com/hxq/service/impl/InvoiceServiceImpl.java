package com.hxq.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hxq.dao.ContractDao;
import com.hxq.dao.DelegateDao;
import com.hxq.dao.ExportDao;
import com.hxq.dao.InvoiceDao;
import com.hxq.dao.PackageDao;
import com.hxq.domain.Contract;
import com.hxq.domain.Delegate;
import com.hxq.domain.Export;
import com.hxq.domain.Invoice;
import com.hxq.domain.Package;
import com.hxq.service.InvoiceService;
import com.hxq.utils.ContractState;
import com.hxq.utils.DelegateState;
import com.hxq.utils.ExportState;
import com.hxq.utils.PackageState;

import freemarker.cache.StrongCacheStorage;

/**
 *发票业务逻辑
 * @author 强仔
 *
 */
@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService{

	/**
	 * 发票数据访问
	 */
	@Autowired
	private InvoiceDao invoiceDao;
	
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
	 * 装箱单数据访问层
	 */
	@Autowired
	private PackageDao packageDao; 
	
	/**
	 * 委托单数据访问层
	 */
	@Autowired
	private DelegateDao delegateDao;
	/**
	 * 条件查询发票单
	 */
	@Override
	public List<Invoice> find(Specification<Invoice> spec) {
		return invoiceDao.findAll(spec);
	}
	
	/**
	 * 根据id获取发票单
	 */
	@Override
	public Invoice get(String id) {
		return invoiceDao.findOne(id);
	}

	/**
	 * 分页条件查询发票单
	 */
	@Override
	public Page<Invoice> findPage(Specification<Invoice> spec, Pageable pageable) {
		return invoiceDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新发票单
	 */
	@Override
	@Deprecated
	public void saveOrUpdate(Invoice entity) {
		
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	@Deprecated
	public void saveOrUpdateAll(Collection<Invoice> entitys) {
		for (Invoice pac : entitys) {
			invoiceDao.save(pac);
		}
	}
	
	/**
	 * 新增或修改
	 * @param entity 发票单实体
	 * @param id 委托单Id null 为更新 
	 */
	@Override
	public void saveOrUpdate(Invoice entity,String id) {
		//新增
		if(id!=null)
		{
			entity.setId(id);
			//修改装箱单状态
			Package package1 = packageDao.findOne(id);
			//回传发票号给装箱单
			package1.setInvoiceNo(entity.getBlNo());
			package1.setInvoiceDate(entity.getCreateTime());
			package1.setState(PackageState.INVOICE);
			packageDao.save(package1);
			
			//修改委托单状态
			Delegate delegate = delegateDao.findOne(id);
			delegate.setState(DelegateState.INVOICE);
			delegateDao.save(delegate);
			
			//购销合同id集合
			String [] contractIds=package1.getExportNos().split(", ");
			//报运单id集合
			String [] exportIds=package1.getExportIds().split(", ");
			//修改购销合同状态
			for (String contractId : contractIds) {
				if(contractId!=null&&!"".equals(contractId))
				{
					Contract contract = contractDao.findOne(contractId);
					contract.setState(ContractState.INVOICE);
					contractDao.save(contract);
				}
			}
			//修改报运单状态
			for (String exportId : exportIds) {
				if(exportId!=null&&!"".equals(exportId))
				{
					Export export = exportDao.findOne(exportId);
					export.setState(ExportState.INVOICE);
					exportDao.save(export);
				}
			}
		}else {
			//修改发票号，发票时间
			Package package1 = packageDao.findOne(entity.getId());
			//回传发票号给装箱单
			package1.setInvoiceNo(entity.getBlNo());
			package1.setInvoiceDate(entity.getUpdateTime());
			packageDao.save(package1);
		}
		
		invoiceDao.save(entity);
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		//修改委托单状态为草稿
		Delegate delegate = delegateDao.findOne(id);
		delegate.setState(DelegateState.CANCEL);
		delegateDao.save(delegate);
		
		//修改装箱单状态
		Package package1 = packageDao.findOne(id);
		//将对应的装箱单状态改为委托
		package1.setState(PackageState.DELEGATE);
		packageDao.save(package1);
		
		//将对应的报运单状态改为委托
		String[] exportIds = package1.getExportIds().split(", ");
		for (String exportId : exportIds) {
			if(exportId!=null&&!"".equals(exportId)){
				Export export = exportDao.findOne(exportId);
				export.setState(ExportState.DELEGATE);
				exportDao.save(export);
			}
			
		}
		//将对应的购销合同状态改为已委托
		String[] contractIds=package1.getExportNos().split(", ");
		for (String contractId : contractIds) {
			if(contractId!=null&&!"".equals(contractId)){
			Contract contract = contractDao.findOne(contractId);
			contract.setState(ContractState.DELEGATE);
			contractDao.save(contract);
			}
		}
		invoiceDao.delete(id);
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
