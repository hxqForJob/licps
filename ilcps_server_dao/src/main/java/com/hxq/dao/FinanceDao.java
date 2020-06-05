package com.hxq.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.hxq.domain.Finance;

public interface FinanceDao extends JpaRepository<Finance,String>,JpaSpecificationExecutor<Finance> {

}
