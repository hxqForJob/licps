package com.hxq.client.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="USER_C")
public class UserClient implements Serializable {

	@Id
	@Column(name="USER_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	//用户与用户详情   一对一
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="USER_ID")
	private UserinfoClient userinfo;
	
	//用户与公司   一对多
	@OneToMany(mappedBy="user")
	private Set<CompanyClient> companys = new HashSet<CompanyClient>(0);
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="EMAIL")
	private String email;//邮箱


	@Column(name="TELEPHONE")
	private String telephone;//电话号码
	
	@Column(name="DEGREE")
	private Integer degree;//等级
	
	@Column(name="STATE")
	private Integer state;//状态
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public Integer getDegree() {
		return degree;
	}
	public void setDegree(Integer degree) {
		this.degree = degree;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public UserinfoClient getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserinfoClient userinfo) {
		this.userinfo = userinfo;
	}
	public Set<CompanyClient> getCompanys() {
		return companys;
	}
	public void setCompanys(Set<CompanyClient> companys) {
		this.companys = companys;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
