package com.hxq.service;

import com.hxq.domain.Delegate;
import com.hxq.domain.Finance;

public interface FinanceService extends BaseService<Finance> {
	public void saveOrUpdate(Finance entity,String id);
}
