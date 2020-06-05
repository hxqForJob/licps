package com.hxq.client.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxq.client.dao.UserClientDao;
import com.hxq.client.domain.UserClient;



@Service
public class UserClientServiceImpl implements UserClientService {
	
	@Autowired
	private UserClientDao userClientDao;
	
	public UserClient get(Serializable id) {
		return userClientDao.getOne((String)id);
	}
	
	public void saveOrUpdate(UserClient entity) {
		userClientDao.save(entity);
	}
	
	public void deleteById(Serializable id) {
		userClientDao.delete((String)id);
	}
	
	public UserClient findByUserNameAndPassword(String userName, String password) {
		return userClientDao.findByUserNameAndPassword(userName, password);
	}
	
	public UserClient findByEmail(String email) {
		return userClientDao.findByEmail(email);
	}
	
}
