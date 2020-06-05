package com.hxq.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hxq.domain.Export;
import com.hxq.domain.ExportProduct;

public interface ExportProductDao  extends JpaRepository<ExportProduct,String>,JpaSpecificationExecutor<ExportProduct> {

}
