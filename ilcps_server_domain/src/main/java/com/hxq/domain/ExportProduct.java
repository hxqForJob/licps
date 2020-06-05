package com.hxq.domain;

import java.io.Serializable;
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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Lazy;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 报运单明细（报运单货物）
 * @author 强仔
 *
 */
@Entity
@Table(name="EXPORT_PRODUCT_C")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ExportProduct implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EXPORT_PRODUCT_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;	  	
	
	@ManyToOne
	@JoinColumn(name="EXPORT_ID")
	private Export export;			//报运货物和报运的关系，多对一
	
	@ManyToOne
	@JoinColumn(name="FACTORY_ID")
	private Factory factory;		//报运货物和厂家的关系，多对一
	
	@OneToMany(mappedBy="exportProduct",cascade=CascadeType.ALL)
	private Set<ExtEproduct> extEproducts;		//报运货物和报运附件的关系，一对多

	@Column(name="PRODUCT_NO")
	private String productNo;		
	@Column(name="PACKING_UNIT")
	private String packingUnit;			//PCS/SETS
	@Column(name="CNUMBER")
	private Integer cnumber;
	@Column(name="BOX_NUM")
	private Integer boxNum;
	@Column(name="GROSS_WEIGHT")
	private Double grossWeight;
	@Column(name="NET_WEIGHT")
	private Double netWeight;
	@Column(name="SIZE_LENGTH")
	private Double sizeLength;
	@Column(name="SIZE_WIDTH")
	private Double sizeWidth;	
	@Column(name="SIZE_HEIGHT")
	private Double sizeHeight;
	@Column(name="EX_PRICE")
	private Double exPrice;			//sales confirmation 中的价格（手填）
	@Column(name="PRICE")
	private Double price;
	@Column(name="TAX")
	private Double tax;			//收购单价=合同单价
	
	@Column(name="ORDER_NO")
	private Integer orderNo;	
	
	/*private String factoryId;
	public String getFactoryId() {
		factoryId = factory.getId();
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}*/
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
	
	public String getPackingUnit() {
		return this.packingUnit;
	}
	public void setPackingUnit(String packingUnit) {
		this.packingUnit = packingUnit;
	}	
	
	public Integer getCnumber() {
		return this.cnumber;
	}
	public void setCnumber(Integer cnumber) {
		this.cnumber = cnumber;
	}	
	
	public Integer getBoxNum() {
		return this.boxNum;
	}
	public void setBoxNum(Integer boxNum) {
		this.boxNum = boxNum;
	}	
	
	public Double getGrossWeight() {
		return this.grossWeight;
	}
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}	
	
	public Double getNetWeight() {
		return this.netWeight;
	}
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}	
	
	public Double getSizeLength() {
		return this.sizeLength;
	}
	public void setSizeLength(Double sizeLength) {
		this.sizeLength = sizeLength;
	}	
	
	public Double getSizeWidth() {
		return this.sizeWidth;
	}
	public void setSizeWidth(Double sizeWidth) {
		this.sizeWidth = sizeWidth;
	}	
	
	public Double getSizeHeight() {
		return this.sizeHeight;
	}
	public void setSizeHeight(Double sizeHeight) {
		this.sizeHeight = sizeHeight;
	}	
	
	public Double getExPrice() {
		return this.exPrice;
	}
	public void setExPrice(Double exPrice) {
		this.exPrice = exPrice;
	}	
	
	public Double getPrice() {
		return this.price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}	
	
	public Double getTax() {
		return this.tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}	
	
	public Integer getOrderNo() {
		return this.orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Export getExport() {
		return export;
	}
	public void setExport(Export export) {
		this.export = export;
	}
	public Factory getFactory() {
		return factory;
	}
	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	public Set<ExtEproduct> getExtEproducts() {
		return extEproducts;
	}
	public void setExtEproducts(Set<ExtEproduct> extEproducts) {
		this.extEproducts = extEproducts;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boxNum == null) ? 0 : boxNum.hashCode());
		result = prime * result + ((cnumber == null) ? 0 : cnumber.hashCode());
		result = prime * result + ((exPrice == null) ? 0 : exPrice.hashCode());
		result = prime * result + ((export == null) ? 0 : export.hashCode());
		result = prime * result + ((extEproducts == null) ? 0 : extEproducts.hashCode());
		result = prime * result + ((factory == null) ? 0 : factory.hashCode());
		result = prime * result + ((grossWeight == null) ? 0 : grossWeight.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((netWeight == null) ? 0 : netWeight.hashCode());
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result + ((packingUnit == null) ? 0 : packingUnit.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((productNo == null) ? 0 : productNo.hashCode());
		result = prime * result + ((sizeHeight == null) ? 0 : sizeHeight.hashCode());
		result = prime * result + ((sizeLength == null) ? 0 : sizeLength.hashCode());
		result = prime * result + ((sizeWidth == null) ? 0 : sizeWidth.hashCode());
		result = prime * result + ((tax == null) ? 0 : tax.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExportProduct other = (ExportProduct) obj;
		if (boxNum == null) {
			if (other.boxNum != null)
				return false;
		} else if (!boxNum.equals(other.boxNum))
			return false;
		if (cnumber == null) {
			if (other.cnumber != null)
				return false;
		} else if (!cnumber.equals(other.cnumber))
			return false;
		if (exPrice == null) {
			if (other.exPrice != null)
				return false;
		} else if (!exPrice.equals(other.exPrice))
			return false;
		if (export == null) {
			if (other.export != null)
				return false;
		} else if (!export.equals(other.export))
			return false;
		if (extEproducts == null) {
			if (other.extEproducts != null)
				return false;
		} else if (!extEproducts.equals(other.extEproducts))
			return false;
		if (factory == null) {
			if (other.factory != null)
				return false;
		} else if (!factory.equals(other.factory))
			return false;
		if (grossWeight == null) {
			if (other.grossWeight != null)
				return false;
		} else if (!grossWeight.equals(other.grossWeight))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (netWeight == null) {
			if (other.netWeight != null)
				return false;
		} else if (!netWeight.equals(other.netWeight))
			return false;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		if (packingUnit == null) {
			if (other.packingUnit != null)
				return false;
		} else if (!packingUnit.equals(other.packingUnit))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (productNo == null) {
			if (other.productNo != null)
				return false;
		} else if (!productNo.equals(other.productNo))
			return false;
		if (sizeHeight == null) {
			if (other.sizeHeight != null)
				return false;
		} else if (!sizeHeight.equals(other.sizeHeight))
			return false;
		if (sizeLength == null) {
			if (other.sizeLength != null)
				return false;
		} else if (!sizeLength.equals(other.sizeLength))
			return false;
		if (sizeWidth == null) {
			if (other.sizeWidth != null)
				return false;
		} else if (!sizeWidth.equals(other.sizeWidth))
			return false;
		if (tax == null) {
			if (other.tax != null)
				return false;
		} else if (!tax.equals(other.tax))
			return false;
		return true;
	}	
	
	
}
