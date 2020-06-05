package com.hxq.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hxq.domain.Contract;
import com.hxq.domain.Factory;

public interface FactoryDao extends JpaRepository<Factory,String>,JpaSpecificationExecutor<Factory> {

	
	
}
