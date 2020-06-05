package com.hxq.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="FINANCE_C")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Finance extends BaseEntity {

	@Id
	@Column(name="FINANCE_ID")
	@GeneratedValue(generator="system-assigned")
	@GenericGenerator(name="system-assigned",strategy="assigned")
	private String id; //财务报运id
	
	@Column(name="MAKE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date makeDate; //制单日期
	@Column(name="MAKER")
	private String maker;//制单人
	@Column(name="STATE")
	private int state; //状态


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Date getMakeDate() {
		return makeDate;
	}


	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}


	public String getMaker() {
		return maker;
	}


	public void setMaker(String maker) {
		this.maker = maker;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}
}