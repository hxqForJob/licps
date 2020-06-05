package com.hxq.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hxq.dao.OfenMoudleDao;
import com.hxq.domain.OfenMoudle;
import com.hxq.service.OfenMoudleService;
import com.hxq.utils.UtilFuns;
import com.hxq.utils.file.StringUtil;

/**
 *用户管理业务逻辑
 * @author 强仔
 *
 */
@Service("ofenOfenMoudleService")
public class OfenMoudleServiceImpl implements OfenMoudleService{

	@Autowired
	private OfenMoudleDao moduleDao;
	
	/**
	 * 条件查询用户
	 */
	@Override
	public List<OfenMoudle> find(Specification<OfenMoudle> spec) {
		// TODO Auto-generated method stub
		return moduleDao.findAll(spec);
	}
	
	/**
	 * 根据id获取用户
	 */
	@Override
	public OfenMoudle get(String id) {
		// TODO Auto-generated method stub
		return moduleDao.findOne(id);
	}

	/**
	 * 分页条件查询用户
	 */
	@Override
	public Page<OfenMoudle> findPage(Specification<OfenMoudle> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return moduleDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新用户
	 */
	@Override
	public void saveOrUpdate(OfenMoudle entity) {
		// TODO Auto-generated method stub
		if(UtilFuns.isEmpty(entity.getId()))
		{
			
		}
		moduleDao.save(entity);
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	public void saveOrUpdateAll(Collection<OfenMoudle> entitys) {
		for (OfenMoudle OfenMoudle : entitys) {
			moduleDao.save(OfenMoudle);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		moduleDao.delete(id);
	}
	
	
	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		for (String string : ids) {
			moduleDao.delete(string.trim());
		}
	}

	
	
	

}
