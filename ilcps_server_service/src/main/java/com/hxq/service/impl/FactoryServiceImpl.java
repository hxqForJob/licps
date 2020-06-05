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

import com.hxq.dao.FactoryDao;
import com.hxq.domain.Factory;
import com.hxq.service.FactoryService;
import com.hxq.utils.UtilFuns;

/**
 *工厂业务逻辑
 * @author 强仔
 *
 */
@Service("factoryService")
public class FactoryServiceImpl implements FactoryService{

	@Autowired
	private FactoryDao factoryDao;
	
	/**
	 * 条件查询工厂
	 */
	@Override
	public List<Factory> find(Specification<Factory> spec) {
		// TODO Auto-generated method stub
		return factoryDao.findAll(spec);
	}
	
	/**
	 * 根据id获取工厂
	 */
	@Override
	public Factory get(String id) {
		// TODO Auto-generated method stub
		return factoryDao.findOne(id);
	}

	/**
	 * 分页条件查询工厂
	 */
	@Override
	public Page<Factory> findPage(Specification<Factory> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return factoryDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新工厂
	 */
	@Override
	public void saveOrUpdate(Factory entity) {
		// TODO Auto-generated method stub
		if(UtilFuns.isEmpty(entity.getId()))
		{
		
		}
		factoryDao.save(entity);
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	public void saveOrUpdateAll(Collection<Factory> entitys) {
		for (Factory factory : entitys) {
			factoryDao.save(factory);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		factoryDao.delete(id);
	}
	
	
	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		for (String string : ids) {
			factoryDao.delete(string);
		}
	}

	
	
	

}
