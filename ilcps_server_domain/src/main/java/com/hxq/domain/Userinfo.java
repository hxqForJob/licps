package com.hxq.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 用户信息
 * @author 强仔
 *
 */
@Entity
@Table(name="USER_INFO_P")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Userinfo extends BaseEntity {
	@Id
	@Column(name="USER_INFO_ID")
	@GeneratedValue(generator="system-assigned")
	@GenericGenerator(name="system-assigned",strategy="assigned")
	private String id;
	
	@Column(name="NAME")
	private String name;//真实姓名
	
	
	@Column(name="MANAGER_ID")
	private String managerId;//直属领导 ID
	
	
	@Column(name="JOIN_DATE")
	private Date joinDate;//入职时间
	
	@Column(name="SALARY")
	private Double salary;//薪水
	
	@Column(name="BIRTHDAY")
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthday;//生日
	
	@Column(name="GENDER")
	private String gender;//性别
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="STATION")
	private String station;//岗位
	
	@Column(name="TELEPHONE")
	private String telephone;//电话
	
	@Column(name="DEGREE")
	private Integer degree;//等级
	
	@Column(name="REMARK")
	private String remark;//说明
	
	@Column(name="ORDER_NO")
	private Integer orderNo;//排序号
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getDegree() {
		return degree;
	}
	public void setDegree(Integer degree) {
		this.degree = degree;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
	
	
}
