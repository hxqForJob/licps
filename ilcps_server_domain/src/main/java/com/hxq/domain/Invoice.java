package com.hxq.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="INVOICE_C")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Invoice extends BaseEntity {

	@Id
	@Column(name="INVOICE_ID")
	@GeneratedValue(generator="system-assigned")
	@GenericGenerator(name="system-assigned",strategy="assigned")
	private String id; //发票id
	
	@Column(name="SC_NO")
	private String scNo; //提货单编号
	@Column(name="BL_NO")
	private String blNo;//发票号
	@Column(name="PRICE_CONDITION")
	private String priceCondition; //贸易条款 FAS/FOB
	@Column(name="STATE")
	private int state	; //状态
	@Column(name="REALL_NAME")
	private String reallName;//开票人
	
	
	
	public String getReallName() {
		return reallName;
	}
	public void setReallName(String reallName) {
		this.reallName = reallName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScNo() {
		return scNo;
	}
	public void setScNo(String scNo) {
		this.scNo = scNo;
	}
	public String getBlNo() {
		return blNo;
	}
	public void setBlNo(String blNo) {
		this.blNo = blNo;
	}
	public String getPriceCondition() {
		return priceCondition;
	}
	public void setPriceCondition(String priceCondition) {
		this.priceCondition = priceCondition;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
}
