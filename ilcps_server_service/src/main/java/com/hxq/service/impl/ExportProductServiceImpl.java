package com.hxq.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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
import com.hxq.dao.ExportProductDao;
import com.hxq.domain.Contract;
import com.hxq.domain.ContractProduct;
import com.hxq.domain.ExportProduct;
import com.hxq.domain.ExtCproduct;
import com.hxq.domain.ExtEproduct;
import com.hxq.service.ExportProductService;
import com.hxq.utils.UtilFuns;

/**
 *报运单货物业务逻辑
 * @author 强仔
 *
 */
@Service("exportProductService")
public class ExportProductServiceImpl implements ExportProductService{

	@Autowired
	private ExportProductDao exportProductDao;
	
	@Autowired
	private ContractDao contractDao;
	
	@Autowired
	private ContractProductDao contractProductDao;
	
	/**
	 * 条件查询报运单货物
	 */
	@Override
	public List<ExportProduct> find(Specification<ExportProduct> spec) {
		// TODO Auto-generated method stub
		return exportProductDao.findAll(spec);
	}
	
	/**
	 * 根据id获取报运单货物
	 */
	@Override
	public ExportProduct get(String id) {
		// TODO Auto-generated method stub
		return exportProductDao.findOne(id);
	}

	/**
	 * 分页条件查询报运单货物
	 */
	@Override
	public Page<ExportProduct> findPage(Specification<ExportProduct> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return exportProductDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新报运单货物
	 */
	@Override
	public void saveOrUpdate(ExportProduct entity) {
		// TODO Auto-generated method stub
		exportProductDao.save(entity);
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	public void saveOrUpdateAll(Collection<ExportProduct> entitys) {
		for (ExportProduct exportProduct : entitys) {
			exportProductDao.save(exportProduct);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		exportProductDao.delete(id);
	}
	
	
	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		for (String string : ids) {
			exportProductDao.delete(string);
		}
	}

	
	
	

}
