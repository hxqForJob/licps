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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.hxq.dao.RoleDao;
import com.hxq.domain.Role;
import com.hxq.service.RoleService;
import com.hxq.utils.UtilFuns;
import com.hxq.utils.file.StringUtil;

/**
 *用户管理业务逻辑
 * @author 强仔
 *
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 条件查询用户
	 */
	@Override
	public List<Role> find(Specification<Role> spec) {
		// TODO Auto-generated method stub
		return roleDao.findAll(spec);
	}
	
	/**
	 * 根据id获取用户
	 */
	@Override
	public Role get(String id) {
		// TODO Auto-generated method stub
		return roleDao.findOne(id);
	}

	/**
	 * 分页条件查询用户
	 */
	@Override
	public Page<Role> findPage(Specification<Role> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return roleDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新用户
	 */
	@Override
	public void saveOrUpdate(Role entity) {
		// TODO Auto-generated method stub
		roleDao.save(entity);
		//redis数据同步
		redisTemplate.delete("getTreeNodes"+entity.getId());
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	public void saveOrUpdateAll(Collection<Role> entitys) {
		for (Role Role : entitys) {
			roleDao.save(Role);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		roleDao.delete(id);
	}
	
	
	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		for (String string : ids) {
			roleDao.delete(string);
		}
	}

	
	
	

}
