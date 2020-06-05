package com.hxq.client.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="USERINFO_C")
public class UserinfoClient implements Serializable {
	
	@Id
	@Column(name="USER_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="NAME")
	private String name;//真实姓名
	
	@Column(name="ENAME")
	private String ename;//英文名
	
	
	@Column(name="CODE_TYPE")
	private Integer codeType;//证件类型
	
	@Column(name="CODE_NO")
	private String codeNo;//证件号
	

	
	@Column(name="QQ")
	private String qq;//QQ
	
	@Column(name="PIC")
	private String pic;//证件图片

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

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	

	public Integer getCodeType() {
		return codeType;
	}

	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	
	
}
