package com.hxq.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hxq.domain.Dept;
import com.hxq.domain.OfenMoudle;
import com.hxq.domain.User;

public interface OfenMoudleDao extends JpaRepository<OfenMoudle,String>,JpaSpecificationExecutor<OfenMoudle> {

	
	
	
}
