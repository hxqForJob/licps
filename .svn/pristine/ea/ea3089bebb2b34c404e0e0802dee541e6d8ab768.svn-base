package com.hxq.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.core.annotation.Order;

/**
 * 模块
 * @author 强仔
 *
 */
@Entity
@Table(name="MODULE_P")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Module extends BaseEntity {
	@Id
	@Column(name="MODULE_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@ManyToMany(mappedBy="modules")
	private Set<Role> roles = new HashSet<Role>(0);//模块与角色 多对多
	
	@Column(name="PARENT_ID")
	private String parentId;//父结点编号
	@Column(name="PARENT_NAME")
	private String parentName;//父结点名称
	@Column(name="NAME")
	private String name;//模块名
	@Column(name="LAYER_NUM")
	private String layerNum;//层数
	@Column(name="IS_LEAF")
	private String isLeaf;//是否为叶子
	@Column(name="ICO")
	private String ico;//展示图标
	@Column(name="CPERMISSION")
	private String cpermission;//权限
	@Column(name="CURL")
	private String curl;//链接
	@Column(name="CTYPE")
	private String ctype;//类型
	@Column(name="STATE")
	private Integer state;//状态
	@Column(name="BELONG")
	private String belong;//从属于
	@Column(name="CWHICH")
	private String cwhich;//复用标识
	@Column(name="QUOTE_NUM")
	private String quoteNum;//引用次数
	@Column(name="REMARK")
	private String remark;//备注
	@Column(name="ORDER_NO")
	private String orderNo;//排序号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLayerNum() {
		return layerNum;
	}
	public void setLayerNum(String layerNum) {
		this.layerNum = layerNum;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public String getCpermission() {
		return cpermission;
	}
	public void setCpermission(String cpermission) {
		this.cpermission = cpermission;
	}
	public String getCurl() {
		return curl;
	}
	public void setCurl(String curl) {
		this.curl = curl;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getCwhich() {
		return cwhich;
	}
	public void setCwhich(String cwhich) {
		this.cwhich = cwhich;
	}
	public String getQuoteNum() {
		return quoteNum;
	}
	public void setQuoteNum(String quoteNum) {
		this.quoteNum = quoteNum;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((belong == null) ? 0 : belong.hashCode());
		result = prime * result + ((cpermission == null) ? 0 : cpermission.hashCode());
		result = prime * result + ((ctype == null) ? 0 : ctype.hashCode());
		result = prime * result + ((curl == null) ? 0 : curl.hashCode());
		result = prime * result + ((cwhich == null) ? 0 : cwhich.hashCode());
		result = prime * result + ((ico == null) ? 0 : ico.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isLeaf == null) ? 0 : isLeaf.hashCode());
		result = prime * result + ((layerNum == null) ? 0 : layerNum.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((parentName == null) ? 0 : parentName.hashCode());
		result = prime * result + ((quoteNum == null) ? 0 : quoteNum.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		Module other = (Module) obj;
		if (belong == null) {
			if (other.belong != null)
				return false;
		} else if (!belong.equals(other.belong))
			return false;
		if (cpermission == null) {
			if (other.cpermission != null)
				return false;
		} else if (!cpermission.equals(other.cpermission))
			return false;
		if (ctype == null) {
			if (other.ctype != null)
				return false;
		} else if (!ctype.equals(other.ctype))
			return false;
		if (curl == null) {
			if (other.curl != null)
				return false;
		} else if (!curl.equals(other.curl))
			return false;
		if (cwhich == null) {
			if (other.cwhich != null)
				return false;
		} else if (!cwhich.equals(other.cwhich))
			return false;
		if (ico == null) {
			if (other.ico != null)
				return false;
		} else if (!ico.equals(other.ico))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isLeaf == null) {
			if (other.isLeaf != null)
				return false;
		} else if (!isLeaf.equals(other.isLeaf))
			return false;
		if (layerNum == null) {
			if (other.layerNum != null)
				return false;
		} else if (!layerNum.equals(other.layerNum))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (parentName == null) {
			if (other.parentName != null)
				return false;
		} else if (!parentName.equals(other.parentName))
			return false;
		if (quoteNum == null) {
			if (other.quoteNum != null)
				return false;
		} else if (!quoteNum.equals(other.quoteNum))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
	
	
	
	
	
	

	
}
