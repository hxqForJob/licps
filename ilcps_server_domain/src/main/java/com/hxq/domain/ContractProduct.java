package com.hxq.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 购销合同货物
 */
@Entity
@Table(name="CONTRACT_PRODUCT_C")
public class ContractProduct implements Serializable{
	@Id
	@Column(name="CONTRACT_PRODUCT_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="CONTRACT_ID")
	private Contract contract = new Contract();//购销合同对象   货物与合同   多对一 
	
	@ManyToOne
	@JoinColumn(name="FACTORY_ID")
	private Factory factory = new Factory();//工厂对象   多对一
	
	@OneToMany(mappedBy="contractProduct",cascade=CascadeType.ALL)
	private Set<ExtCproduct> extCproducts = new HashSet<ExtCproduct>(0);//货物与附件   一对多
	
	@Column(name="FACTORY_NAME")
	private String factoryName;//工厂名字
	@Column(name="PRODUCT_NO")
	private String productNo;//产品编号
	@Column(name="PRODUCT_IMAGE")
	private String productImage;//产品图片
	@Column(name="PRODUCT_DESC")
	private String productDesc;//产品描述
	@Column(name="LOADING_RATE")
	private String loadingRate;//装率
	@Column(name="BOX_NUM")
	private Integer boxNum;//箱数
	@Column(name="PACKING_UNIT")
	private String packingUnit;//包装单位
	@Column(name="CNUMBER")
	private Integer cnumber;//数量
	@Column(name="OUT_NUMBER")
	private Integer outNumber;//实际出货数量
	@Column(name="FINISHED")
	private Integer finished;//是否出货完毕
	@Column(name="PRODUCT_REQUEST")
	private String productRequest;//产品要求
	@Column(name="PRICE")
	private Double price;//单价
	@Column(name="AMOUNT")
	private Double amount;//总金额 
	@Column(name="ORDER_NO")
	private Integer orderNo;//排序
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
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
	public String getLoadingRate() {
		return loadingRate;
	}
	public void setLoadingRate(String loadingRate) {
		this.loadingRate = loadingRate;
	}
	public Integer getBoxNum() {
		return boxNum;
	}
	public void setBoxNum(Integer boxNum) {
		this.boxNum = boxNum;
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
	public Integer getOutNumber() {
		return outNumber;
	}
	public void setOutNumber(Integer outNumber) {
		this.outNumber = outNumber;
	}
	public Integer getFinished() {
		return finished;
	}
	public void setFinished(Integer finished) {
		this.finished = finished;
	}
	public String getProductRequest() {
		return productRequest;
	}
	public void setProductRequest(String productRequest) {
		this.productRequest = productRequest;
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
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Set<ExtCproduct> getExtCproducts() {
		return extCproducts;
	}
	public void setExtCproducts(Set<ExtCproduct> extCproducts) {
		this.extCproducts = extCproducts;
	}
}
