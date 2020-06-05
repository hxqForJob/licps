package com.hxq.client.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="COMPANY_C")
public class CompanyClient implements Serializable {
	
	@Id
	@Column(name="COMPANY_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	//公司与用户  多对一
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private UserClient user;
	
	@Column(name="CNAME")
	private String cname;//公司名称 
	
	@Column(name="LINKMAN")
	private String linkman;
	
	@Column(name="TELEPHONE")
	private String telephone;

	
	@Column(name="FAX")
	private String fax;
	
	@Column(name="ADDRESS")
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserClient getUser() {
		return user;
	}

	public void setUser(UserClient user) {
		this.user = user;
	}
	
	
}
