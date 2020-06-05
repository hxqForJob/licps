package com.hxq.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.hxq.domain.Invoice;

public interface InvoiceDao extends JpaRepository<Invoice,String>,JpaSpecificationExecutor<Invoice> {

}
