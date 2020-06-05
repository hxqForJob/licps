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

import com.hxq.dao.LoginLogDao;
import com.hxq.domain.LoginLog;
import com.hxq.service.LoginLogService;
import com.hxq.service.LoginLogService;
import com.hxq.utils.UtilFuns;
import com.hxq.utils.file.StringUtil;

/**
 *用户管理业务逻辑
 * @author 强仔
 *
 */
@Service("LoginLogService")
public class LoginLogServiceImpl implements LoginLogService{

	@Autowired
	private LoginLogDao moduleDao;
	
	/**
	 * 条件查询用户
	 */
	@Override
	public List<LoginLog> find(Specification<LoginLog> spec) {
		// TODO Auto-generated method stub
		return moduleDao.findAll(spec);
	}
	
	/**
	 * 根据id获取用户
	 */
	@Override
	public LoginLog get(String id) {
		// TODO Auto-generated method stub
		return moduleDao.findOne(id);
	}

	/**
	 * 分页条件查询用户
	 */
	@Override
	public Page<LoginLog> findPage(Specification<LoginLog> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return moduleDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新用户
	 */
	@Override
	public void saveOrUpdate(LoginLog entity) {
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
	public void saveOrUpdateAll(Collection<LoginLog> entitys) {
		for (LoginLog LoginLog : entitys) {
			moduleDao.save(LoginLog);
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
