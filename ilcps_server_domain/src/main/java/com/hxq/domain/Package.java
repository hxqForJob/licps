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
 * 装箱实体
 * @author 强仔
 *
 */
@Entity
@Table(name="PACKING_LIST_C")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Package extends BaseEntity {

	/**
	 * 装箱ID
	 */
	@Id
	@Column(name="PACKING_LIST_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id; //
	
	
	@Column(name="SELLER")
	private String seller; //卖方
	@Column(name="BUYER")
	private String buyer; //买方
	@Column(name="INVOICE_NO")
	private String invoiceNo;//发票号，回传
	@Column(name="INVOICE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date invoiceDate; //发票日期
	@Column(name="MARKS")
	private String marks;//唛头
	@Column(name="DESCRIPTIONS")
	private String description;//描述
	@Column(name="EXPORT_IDS")
	private String exportIds;//报运单合计 打断设计
	@Column(name="EXPORT_NOS")
	private String exportNos; //合同合计
	@Column(name="STATE")
	private int state; //状态
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExportIds() {
		return exportIds;
	}
	public void setExportIds(String exportIds) {
		this.exportIds = exportIds;
	}
	public String getExportNos() {
		return exportNos;
	}
	public void setExportNos(String exportNos) {
		this.exportNos = exportNos;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	

}
