package com.hxq.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hxq.domain.Dept;

public interface DeptDao extends JpaRepository<Dept,String>,JpaSpecificationExecutor<Dept> {

	Dept findDeptByIdAndState(String string, Integer i);
	
	
}
