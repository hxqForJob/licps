package com.hxq.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 报运单附件
 * @author 强仔
 *
 */
@Entity
@Table(name="EXT_EPRODUCT_C")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ExtEproduct  implements Serializable{
	@Id
	@Column(name="EXT_EPRODUCT_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;	  	

	@ManyToOne
	@JoinColumn(name="EXPORT_PRODUCT_ID")
	private ExportProduct exportProduct;		//附件和货物，多对一
	
	@ManyToOne
	@JoinColumn(name="FACTORY_ID")
	private Factory factory;					//附件和厂家，多对一

	@Column(name="PRODUCT_NO")
	private String productNo;			
	@Column(name="PRODUCT_IMAGE")
	private String productImage;
	@Column(name="PRODUCT_DESC")
	private String productDesc;
	@Column(name="CNUMBER")
	private Integer cnumber;
	@Column(name="PACKING_UNIT")
	private String packingUnit;			//PCS/SETS
	@Column(name="PRICE")
	private Double price;
	@Column(name="AMOUNT")
	private Double amount;			//自动计算: 数量x单价
	@Column(name="PRODUCT_REQUEST")
	private String productRequest;
	@Column(name="ORDER_NO")
	private Integer orderNo;			

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getProductNo() {
		return this.productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}	
	
	public String getProductImage() {
		return this.productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}	
	
	public String getProductDesc() {
		return this.productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}	
	
	public Integer getCnumber() {
		return this.cnumber;
	}
	public void setCnumber(Integer cnumber) {
		this.cnumber = cnumber;
	}	
	
	public String getPackingUnit() {
		return this.packingUnit;
	}
	public void setPackingUnit(String packingUnit) {
		this.packingUnit = packingUnit;
	}	
	
	public Double getPrice() {
		return this.price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}	
	
	public Double getAmount() {
		return this.amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}	
	
	public String getProductRequest() {
		return this.productRequest;
	}
	public void setProductRequest(String productRequest) {
		this.productRequest = productRequest;
	}	
	
	public Integer getOrderNo() {
		return this.orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public ExportProduct getExportProduct() {
		return exportProduct;
	}
	public void setExportProduct(ExportProduct exportProduct) {
		this.exportProduct = exportProduct;
	}
	public Factory getFactory() {
		return factory;
	}
	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	
}
