package com.hxq.service;

import com.hxq.domain.Dept;

public interface DeptService extends BaseService<Dept> {

	Dept findDeptByIdAndState(String string, int i);
	
	
}
