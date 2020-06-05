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
import com.hxq.dao.DelegateDao;
import com.hxq.dao.ExportDao;
import com.hxq.dao.FinanceDao;
import com.hxq.dao.InvoiceDao;
import com.hxq.dao.PackageDao;
import com.hxq.domain.Contract;
import com.hxq.domain.Delegate;
import com.hxq.domain.Export;
import com.hxq.domain.Finance;
import com.hxq.domain.Invoice;
import com.hxq.domain.Package;
import com.hxq.service.FinanceService;
import com.hxq.utils.ContractState;
import com.hxq.utils.DelegateState;
import com.hxq.utils.ExportState;
import com.hxq.utils.FinanceState;
import com.hxq.utils.InvoiceState;
import com.hxq.utils.PackageState;

import freemarker.cache.StrongCacheStorage;

/**
 *财务业务逻辑
 * @author 强仔
 *
 */
@Service("financeService")
public class FinanceServiceImpl implements FinanceService{

	/**
	 * 财务数据访问
	 */
	@Autowired
	private FinanceDao financeDao;
	
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
	 * 装箱单数据访问
	 */
	@Autowired
	private PackageDao packageDao;
	
	/**
	 * 委托数据访问
	 */
	@Autowired
	private DelegateDao delegateDao;
	
	/**
	 * 发票数据访问
	 */
	@Autowired
	private InvoiceDao invoiceDao;
	/**
	 * 条件查询装箱单
	 */
	@Override
	public List<Finance> find(Specification<Finance> spec) {
		return financeDao.findAll(spec);
	}
	
	/**
	 * 根据id获取装箱单
	 */
	@Override
	public Finance get(String id) {
		return financeDao.findOne(id);
	}

	/**
	 * 分页条件查询装箱单
	 */
	@Override
	public Page<Finance> findPage(Specification<Finance> spec, Pageable pageable) {
		return financeDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新装箱单
	 */
	@Override
	@Deprecated
	public void saveOrUpdate(Finance entity) {
		
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	@Deprecated
	public void saveOrUpdateAll(Collection<Finance> entitys) {
		for (Finance pac : entitys) {
			financeDao.save(pac);
		}
	}

	/**
	 * 新增或修改
	 * @param entity 发票单实体
	 * @param id 委托单Id null 为更新 
	 */
	@Override
	public void saveOrUpdate(Finance entity,String id) {
		//新增
		if(id!=null)
		{
			entity.setId(id);
			//修改装箱单状态
			Package package1 = packageDao.findOne(id);
			package1.setState(PackageState.FINANCE);
			packageDao.save(package1);
			
			//修改委托单状态
			Delegate delegate = delegateDao.findOne(id);
			delegate.setState(DelegateState.FINANCE);
			delegateDao.save(delegate);
			
			//修改发票单状态
			Invoice invoice = invoiceDao.findOne(id);
			invoice.setState(InvoiceState.FINANCE);
			invoiceDao.save(invoice);
			
			//购销合同id集合
			String [] contractIds=package1.getExportNos().split(", ");
			//报运单id集合
			String [] exportIds=package1.getExportIds().split(", ");
			//修改购销合同状态
			for (String contractId : contractIds) {
				if(contractId!=null&&!"".equals(contractId))
				{
					Contract contract = contractDao.findOne(contractId);
					contract.setState(ContractState.FINANCE);
					contractDao.save(contract);
				}
			}
			//修改报运单状态
			for (String exportId : exportIds) {
				if(exportId!=null&&!"".equals(exportId))
				{
					Export export = exportDao.findOne(exportId);
					export.setState(ExportState.FINANCE);
					exportDao.save(export);
				}
			}
		}else {
			//更新财务单
			if(entity.getState()==FinanceState.CHECK)
			{
				//结算
				//修改装箱单状态
				Package package1 = packageDao.findOne(entity.getId());
				package1.setState(PackageState.CHECK);
				packageDao.save(package1);
				
				//修改委托单状态
				Delegate delegate = delegateDao.findOne(entity.getId());
				delegate.setState(DelegateState.CHECK);
				delegateDao.save(delegate);
				
				//修改发票单状态
				Invoice invoice = invoiceDao.findOne(entity.getId());
				invoice.setState(InvoiceState.CHECK);
				invoiceDao.save(invoice);
				
				//购销合同id集合
				String [] contractIds=package1.getExportNos().split(", ");
				//报运单id集合
				String [] exportIds=package1.getExportIds().split(", ");
				//修改购销合同状态
				for (String contractId : contractIds) {
					if(contractId!=null&&!"".equals(contractId))
					{
						Contract contract = contractDao.findOne(contractId);
						contract.setState(ContractState.CHECK);
						contractDao.save(contract);
					}
				}
				//修改报运单状态
				for (String exportId : exportIds) {
					if(exportId!=null&&!"".equals(exportId))
					{
						Export export = exportDao.findOne(exportId);
						export.setState(ExportState.CHECK);
						exportDao.save(export);
					}
				}
			}
		}
		financeDao.save(entity);
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		//修改发票单状态为草稿
		Invoice invoice = invoiceDao.findOne(id);
		invoice.setState(FinanceState.CANCEL);
		invoiceDao.save(invoice);
		
		//修改委托单状态为发票
		Delegate delegate = delegateDao.findOne(id);
		delegate.setState(DelegateState.INVOICE);
		delegateDao.save(delegate);
		
		//修改装箱单状态
		Package package1 = packageDao.findOne(id);
		//将对应的装箱单状态改为发票
		package1.setState(PackageState.INVOICE);
		packageDao.save(package1);
		
		//将对应的报运单状态改为发票
		String[] exportIds = package1.getExportIds().split(", ");
		for (String exportId : exportIds) {
			if(exportId!=null&&!"".equals(exportId)){
				Export export = exportDao.findOne(exportId);
				export.setState(ExportState.INVOICE);
				exportDao.save(export);
			}
			
		}
		//将对应的购销合同状态改为已发票
		String[] contractIds=package1.getExportNos().split(", ");
		for (String contractId : contractIds) {
			if(contractId!=null&&!"".equals(contractId)){
			Contract contract = contractDao.findOne(contractId);
			contract.setState(ContractState.INVOICE);
			contractDao.save(contract);
			}
		}
		financeDao.delete(id);
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
