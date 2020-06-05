package com.hxq.service;

import com.hxq.domain.Delegate;

public interface DelegateService extends BaseService<Delegate> {
	public void saveOrUpdate(Delegate entity,String id);
}
