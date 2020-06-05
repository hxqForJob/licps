package com.hxq.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.jfree.util.Log.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.hxq.dao.UserDao;
import com.hxq.domain.User;
import com.hxq.service.UserService;
import com.hxq.utils.SysConstant;
import com.hxq.utils.UtilFuns;
import com.hxq.utils.file.StringUtil;

import net.sf.ehcache.hibernate.ccs.EhcacheNonstrictReadWriteCache;

/**
 *用户管理业务逻辑
 * @author 强仔
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	/**
	 * 条件查询用户
	 */
	@Override
	public List<User> find(Specification<User> spec) {
		// TODO Auto-generated method stub
		return userDao.findAll(spec);
	}
	
	/**
	 * 根据id获取用户
	 */
	@Override
	public User get(String id) {
		// TODO Auto-generated method stub
		return userDao.findOne(id);
	}

	/**
	 * 分页条件查询用户
	 */
	@Override
	public Page<User> findPage(Specification<User> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return userDao.findAll(spec, pageable);
	}

	/**
	 * 保存或者更新用户
	 */
	@Override
	public void saveOrUpdate(User entity) {
		// TODO Auto-generated method stub
		if(UtilFuns.isEmpty(entity.getId()))
		{
			String uuid=UUID.randomUUID().toString();
			entity.setId(uuid);
			entity.getUserinfo().setId(uuid);
			Md5Hash md5Pwd=new Md5Hash(SysConstant.DEFAULT_PASS,entity.getUserName(),2);
			entity.setPassword(md5Pwd.toString());
		}
		userDao.save(entity);
	}

	/**
	 * 批量保存或者更新
	 */
	@Override
	public void saveOrUpdateAll(Collection<User> entitys) {
		for (User User : entitys) {
			userDao.save(User);
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		userDao.delete(id);
	}
	
	
	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		for (String string : ids) {
			userDao.delete(string);
		}
	}

	
	
	

}
