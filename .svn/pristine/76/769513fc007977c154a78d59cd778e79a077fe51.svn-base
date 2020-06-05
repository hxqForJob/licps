package com.hxq.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hxq.domain.Dept;
import com.hxq.service.DeptService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DeptTest {

	@Autowired
	private DeptService ds;
	
	@Test
	public void Test()
	{
		
		Dept find = ds.get("100");
		for (Dept dept : find.getChildDepts()) {
			System.out.println(dept.getDeptName());
		}
	}
}
