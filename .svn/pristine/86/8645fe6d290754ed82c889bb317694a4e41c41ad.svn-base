package com.hxq.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hxq.domain.Dept;
/**
 * @description:部门持久化类
 * @author 
 * @date 
 * @version 
 * 
 * PO类规范：
 * 1.是一个公有类
 * 2.属性私有
 * 3.提供属性的公有的getter与setter
 * 4.存在一个公有的无参构造
 * 5.不能使用final修饰
 * 6.一般都实现java.io.Serializable接口
 * 7.如果是基本类型，请使用它们的包装类
 */
@Entity
@Table(name="DEPT_P")

public class Dept implements Serializable {
	@Id
	@Column(name="DEPT_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;//编号
	
	@Column(name="DEPT_NAME")
	private String deptName;//部门名称
	
	@ManyToOne
	@JoinColumn(name="PARENT_ID")
	private Dept parent;//父部门   子部门与父部门   多对一
	
	@OneToMany(mappedBy="dept")
	private Set<User> users = new HashSet<>(0);//部门与用户  一对多
	
	@OneToMany(mappedBy="parent")
	private Set<Dept> childDepts=new HashSet<Dept>();//子级部门
	
	@Column(name="STATE")
	private Integer state;//状态
	
	
	
	
	public Set<Dept> getChildDepts() {
		return childDepts;
	}
	public void setChildDepts(Set<Dept> childDepts) {
		this.childDepts = childDepts;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Dept getParent() {
		return parent;
	}
	public void setParent(Dept parent) {
		this.parent = parent;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
	
}
