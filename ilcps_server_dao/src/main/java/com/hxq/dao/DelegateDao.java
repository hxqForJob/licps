package com.hxq.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.hxq.domain.Delegate;

public interface DelegateDao extends JpaRepository<Delegate,String>,JpaSpecificationExecutor<Delegate> {

}
