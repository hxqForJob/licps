package com.hxq.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.action.spi.Executable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hxq.dao.ContractDao;
import com.hxq.dao.ContractProductDao;
import com.hxq.dao.ExportDao;
import com.hxq.dao.ExportProductDao;
import com.hxq.domain.Contract;
import com.hxq.domain.ContractProduct;
import com.hxq.domain.Export;
import com.hxq.domain.ExportProduct;
import com.hxq.domain.ExtCproduct;
import com.hxq.domain.ExtEproduct;
import com.hxq.service.ExportService;
import com.hxq.utils.ContractState;
import com.hxq.utils.FunUtils;
import com.hxq.utils.UtilFuns;

/**
 *报运单业务逻辑
 * @author 强仔
 *
 */
@Service("exportService")
public class ExportServiceImpl implements ExportService{

	//报运Dao
	@Autowired
	private ExportDao exportDao;
	
	//购销合同Dao
	@Autowired
	private ContractDao contractDao;
	
	//购销合同货物Dao
	@Autowired
	private ContractProductDao contractProductDao;
	
	//报运货物Dao
	@Autowired
	private ExportProductDao exportProductDao;
	
	//报运附件Dao
	//@Autowired
	//private Ext
	
	/**
	 * 条件查询报运单
	 */
	@Override
	public List<Export> find(Specification<Export> spec) {
		// TODO Auto-generated method stub
		return exportDao.findAll(spec);
	}
	
	/**
	 * 根据id获取报运单
	 */
	@Override
	public Export get(String id) {
		// TODO Auto-generated method stub
		return exportDao.findOne(id);
	}

	/**
	 * 分页条件查询报运单
	 */
	@Override
	public Page<Export> findPage(Specification<Export> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return exportDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新报运单
	 */
	@Override
	public void saveOrUpdate(Export entity) {
		// TODO Auto-generated method stub
		if(UtilFuns.isEmpty(entity.getId()))
		{
			String contractNos="";
			//总箱数量;
			int totalBoxNum=0;
			//新增报运单
			entity.setState(0);
			entity.setInputDate(new Date());
			String [] contractIds = entity.getContractIds().split(", ");
			for (String contratcId : contractIds) {
				//获取报运的购销合同
				Contract findOne = contractDao.findOne(contratcId);
				//修改合同状态为报运状态,
				findOne.setState(ContractState.EXPORT);
				//拼接合同号
				contractNos+=findOne.getContractNo()+",";
				contractDao.save(findOne);
			}
			//设置合同号
			entity.setCustomerContract(contractNos);
			//获取购销合同下的货物
			List<ContractProduct> contractProducts = contractProductDao.findCpByContractIds(contractIds);
			
			HashSet<ExportProduct> exportProducts=new HashSet<>();
			//数据搬家，获取合同下的货物添加到报运明细中
			for (ContractProduct contractProduct : contractProducts) {
				//计算总箱数量
				totalBoxNum+=FunUtils.checkIsNull(contractProduct.getBoxNum());
				ExportProduct exportProduct=new ExportProduct();
				BeanUtils.copyProperties(contractProduct, exportProduct);
				exportProduct.setId(null);
				exportProduct.setExport(entity);
				exportProducts.add(exportProduct);
				//数据搬家，获取合同下货物的附件添加到报运附件中
				HashSet<ExtEproduct> extEproducts=new HashSet<>();
				for (ExtCproduct extCproduct : contractProduct.getExtCproducts()) {
					ExtEproduct extEproduct=new ExtEproduct();
					BeanUtils.copyProperties(extCproduct, extEproduct);
					extEproduct.setId(null);
					extEproduct.setExportProduct(exportProduct);
					extEproducts.add(extEproduct);
				}
				exportProduct.setExtEproducts(extEproducts);
			}
			entity.setExportProducts(exportProducts);
			entity.setBoxNums(totalBoxNum);
			entity.setGrossWeights(0.0);
			entity.setMeasurements(0.0);
		}else {
			//修改
			double sumGrossWeight=0.0;
			double sumV=0.0;
			//重新计算总毛重，总体积
			Export export = exportDao.findOne(entity.getId());
			Set<ExportProduct> exportProducts = export.getExportProducts();
			for (ExportProduct exportProduct : exportProducts) {
				sumGrossWeight+=FunUtils.checkIsNull(exportProduct.getGrossWeight())*FunUtils.checkIsNull(exportProduct.getCnumber());
				sumV+=FunUtils.checkIsNull(exportProduct.getSizeLength())*
						FunUtils.checkIsNull(exportProduct.getSizeWidth())*
						FunUtils.checkIsNull(exportProduct.getSizeHeight())*
						FunUtils.checkIsNull(exportProduct.getCnumber());
			}
			entity.setGrossWeights(sumGrossWeight);
			sumV= new BigDecimal(sumV/1000000).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			entity.setMeasurements(sumV);
		}
		exportDao.save(entity);
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	public void saveOrUpdateAll(Collection<Export> entitys) {
		for (Export export : entitys) {
			exportDao.save(export);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		//将报运单下的合同状态改为草稿状态
		Export export = get(id);
		String[] contractIds = export.getContractIds().split(", ");
		for (String contratctId : contractIds) {
			Contract contract = contractDao.findOne(contratctId);
			contract.setState(ContractState.CANCEL);
			contractDao.save(contract);
		}
		exportDao.delete(id);
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

	
	
	

}
