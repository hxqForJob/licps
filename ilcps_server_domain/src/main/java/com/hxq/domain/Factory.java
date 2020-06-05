package com.hxq.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;


/**
 * 工厂
 * @author 强仔
 *
 */
@Entity
@Table(name="FACTORY_C")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Factory extends BaseEntity {
	@Id
	@Column(name="FACTORY_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="CTYPE")
	private String ctype;//类型
	@Column(name="FULL_NAME")
	private String fullName;//厂家全称
	@Column(name="FACTORY_NAME")
	private String factoryName;//工厂简称
	@Column(name="CONTACTS")
	private String contacts;//联系人
	@Column(name="PHONE")
	private String phone;//电话
	@Column(name="MOBILE")
	private String mobile;//手机
	@Column(name="FAX")
	private String fax;//传真
	@Column(name="ADDRESS")
	private String address;//地址
	@Column(name="INSPECTOR")
	private String inspector;//验货员
	@Column(name="REMARK")
	private String remark;//说明
	@Column(name="ORDER_NO")
	private String orderNo;//排序号
	@Column(name="STATE")
	private String state;//状态
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
