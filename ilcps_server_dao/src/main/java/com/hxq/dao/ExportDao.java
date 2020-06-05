package com.hxq.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hxq.domain.Contract;
import com.hxq.domain.ContractProduct;
import com.hxq.domain.Export;

public interface ExportDao extends JpaRepository<Export,String>,JpaSpecificationExecutor<Export> {


	
}
