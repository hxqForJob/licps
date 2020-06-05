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

/**
 * 委托单实体
 * @author 强仔
 *
 */
@Entity
@Table(name="DELEGATE_C")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Delegate extends BaseEntity {
	@Id
	@Column(name="DELEGATE_ID")
	@GeneratedValue(generator="system-assigned")
	@GenericGenerator(name="system-assigned",strategy="assigned")
	private String id; //委托ID 与装箱单一对一
	
	@Column(name="TRANSPORT_MODE")
	private String transportMode; //运输方式
	
	@Column(name="SHIPPER")
	private String shipper; //货主
	
	@Column(name="CONSIGNEE")
	private String consihnee; //抬头人
	
	@Column(name="ORIGNAL_NOTIFY_PARTY")
	private String originalNotifyParty;//正本通知人
	@Column(name="LC_NO")
	private String lcNo;//信用证号
	
	@Column(name="PORT_OF_LOADING")
	private String portLoading;//装运港
	
	@Column(name="PORT_OF_TRANS")
	private String portTrans;//转船港
	
	@Column(name="PORT_OF_DISCHARGE")
	private String portDischange;//卸货港
	
	@Column(name="INSTALLATION_PERIOD")
	@Temporal(TemporalType.TIMESTAMP)
	private Date installPeriod;//装期
	
	@Column(name="EFFECTIVE_PERIOD")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectPeriod;//效期
	
	@Column(name="IS_BATCHES")
	private String isBatches;//是否分批
	
	@Column(name="IS_TRANSSHIPMENT")
	private String isTransshipment;//是否转船
	
	@Column(name="NUMBER_OF_COPIES")
	private String copies;//份数
	
	@Column(name="MARKS_AND_NOS")
	private String marksAndNos;//扼要说明
	
	@Column(name="DESCRIPTIONS")
	private String description;//运费说明
	
	@Column(name="QUANTITY")
	private int quantity;//总数量
	
	@Column(name="SPECIAL_CONDITION")
	private String specialCondition;//运输要求
	
	@Column(name="REVIEWER")
	private String reviewer;//复核人
	
	@Column(name="STATE")
	private int state;//状态
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTransportMode() {
		return transportMode;
	}
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
	public String getShipper() {
		return shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}
	public String getConsihnee() {
		return consihnee;
	}
	public void setConsihnee(String consihnee) {
		this.consihnee = consihnee;
	}
	public String getOriginalNotifyParty() {
		return originalNotifyParty;
	}
	public void setOriginalNotifyParty(String originalNotifyParty) {
		this.originalNotifyParty = originalNotifyParty;
	}
	public String getLcNo() {
		return lcNo;
	}
	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}
	public String getPortLoading() {
		return portLoading;
	}
	public void setPortLoading(String portLoading) {
		this.portLoading = portLoading;
	}
	public String getPortTrans() {
		return portTrans;
	}
	public void setPortTrans(String portTrans) {
		this.portTrans = portTrans;
	}
	public String getPortDischange() {
		return portDischange;
	}
	public void setPortDischange(String portDischange) {
		this.portDischange = portDischange;
	}
	public Date getInstallPeriod() {
		return installPeriod;
	}
	public void setInstallPeriod(Date installPeriod) {
		this.installPeriod = installPeriod;
	}
	public Date getEffectPeriod() {
		return effectPeriod;
	}
	public void setEffectPeriod(Date effectPeriod) {
		this.effectPeriod = effectPeriod;
	}
	public String getIsBatches() {
		return isBatches;
	}
	public void setIsBatches(String isBatches) {
		this.isBatches = isBatches;
	}
	public String getIsTransshipment() {
		return isTransshipment;
	}
	public void setIsTransshipment(String isTransshipment) {
		this.isTransshipment = isTransshipment;
	}
	public String getCopies() {
		return copies;
	}
	public void setCopies(String copies) {
		this.copies = copies;
	}
	public String getMarksAndNos() {
		return marksAndNos;
	}
	public void setMarksAndNos(String marksAndNos) {
		this.marksAndNos = marksAndNos;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSpecialCondition() {
		return specialCondition;
	}
	public void setSpecialCondition(String specialCondition) {
		this.specialCondition = specialCondition;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
	

	
}
