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

import com.hxq.dao.DeptDao;
import com.hxq.domain.Dept;
import com.hxq.service.DeptService;

/**
 *部门管理业务逻辑
 * @author 强仔
 *
 */
@Service("deptService")
public class DeptServiceImpl implements DeptService{

	@Autowired
	private DeptDao deptDao;
	
	/**
	 * 条件查询部门
	 */
	@Override
	public List<Dept> find(Specification<Dept> spec) {
		// TODO Auto-generated method stub
		return deptDao.findAll(spec);
	}
	
	/**
	 * 根据id获取部门
	 */
	@Override
	public Dept get(String id) {
		// TODO Auto-generated method stub
		return deptDao.findOne(id);
	}

	/**
	 * 分页条件查询部门
	 */
	@Override
	public Page<Dept> findPage(Specification<Dept> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return deptDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新部门
	 */
	@Override
	public void saveOrUpdate(Dept entity) {
		// TODO Auto-generated method stub
		deptDao.save(entity);
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	public void saveOrUpdateAll(Collection<Dept> entitys) {
		for (Dept dept : entitys) {
			deptDao.save(dept);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		deptDao.delete(id);
	}
	
	
	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		for (String string : ids) {
			deptDao.delete(string);
		}
	}

	/**
	 * 根据部门id和状态查询部门
	 */
	@Override
	public Dept findDeptByIdAndState(String string, int i) {
		// TODO Auto-generated method stub
		return deptDao.findDeptByIdAndState(string,i);
	}
	
	

}
