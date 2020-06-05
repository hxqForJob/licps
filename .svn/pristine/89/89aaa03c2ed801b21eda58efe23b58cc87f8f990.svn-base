package com.hxq.client.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hxq.client.domain.UserClient;


public interface UserClientDao extends JpaRepository<UserClient, String> {
	
	UserClient findByUserNameAndPassword(String userName,String password);

	UserClient findByEmail(String email);
}
