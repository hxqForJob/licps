package com.hxq.service;

import com.hxq.domain.Delegate;
import com.hxq.domain.Invoice;

public interface InvoiceService extends BaseService<Invoice> {
	public void saveOrUpdate(Invoice entity,String id);
}
