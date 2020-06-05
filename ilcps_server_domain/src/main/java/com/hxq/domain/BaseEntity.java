package com.hxq.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 基础类
 * @author 强仔
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
	@Column(name="CREATE_BY")
	protected String createBy;//创建者的id
	
	@Column(name="CREATE_DEPT")
	protected String createDept;//创建者所在部门的id
	
	@Column(name="CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createTime;//创建时间
	
	@Column(name="UPDATE_BY")
	protected String updateBy;//修改者的用户id
	
	@Column(name="UPDATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updateTime;//更新时间

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
