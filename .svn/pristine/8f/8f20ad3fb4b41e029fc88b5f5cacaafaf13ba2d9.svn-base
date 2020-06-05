package com.hxq.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 购销合同附件
 * @author 强仔
 *
 */
@Entity
@Table(name="EXT_CPRODUCT_C")
public class ExtCproduct  implements Serializable{
	@Id
	@Column(name="EXT_CPRODUCT_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="CONTRACT_PRODUCT_ID")
	private ContractProduct contractProduct = new ContractProduct();//附件与货物  多对一
	
	@ManyToOne
	@JoinColumn(name="FACTORY_ID")
	private Factory factory;//附件与工厂   多对一
	
	@Column(name="FACTORY_NAME")
	private String factoryName;//厂家
	@Column(name="PRODUCT_NO")
	private String productNo;//货号
	@Column(name="PRODUCT_IMAGE")
	private String productImage;//产品图片
	@Column(name="PRODUCT_DESC")
	private String productDesc;//货物描述
	@Column(name="PACKING_UNIT")
	private String packingUnit;//包装单位
	@Column(name="CNUMBER")
	private Integer cnumber;//数量
	@Column(name="PRICE")
	private Double price;//单价
	@Column(name="AMOUNT")
	private Double amount;//总金额
	@Column(name="PRODUCT_REQUEST")
	private String productRequest;//要求
	@Column(name="ORDER_NO")
	private Integer orderNo;//排序号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ContractProduct getContractProduct() {
		return contractProduct;
	}
	public void setContractProduct(ContractProduct contractProduct) {
		this.contractProduct = contractProduct;
	}
	public Factory getFactory() {
		return factory;
	}
	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getPackingUnit() {
		return packingUnit;
	}
	public void setPackingUnit(String packingUnit) {
		this.packingUnit = packingUnit;
	}
	public Integer getCnumber() {
		return cnumber;
	}
	public void setCnumber(Integer cnumber) {
		this.cnumber = cnumber;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getProductRequest() {
		return productRequest;
	}
	public void setProductRequest(String productRequest) {
		this.productRequest = productRequest;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
