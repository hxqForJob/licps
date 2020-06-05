package com.hxq.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 常点菜单，和自定义菜单
 * @author 强仔
 *
 */

@Entity
@Table(name="OFEN_MOUDLE_P")
public class OfenMoudle implements Serializable {

	@Id
	@Column(name="OFEN_MODULE_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id; //ID
	
	@Column(name="USER_ID")
	private String userId; //用户ID
	@Column(name="MODULE_ID")
	private String moduleId; //模块ID
	@Column(name="TIMES")
	private int times; //点击次数
	@Column(name="MODULE_NAME")
	private String moduleName; //模块名
	@Column(name="MODULE_URL")
	private String moduleUrl; //模块地址
	@Column(name="TYPE")
	private int type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleUrl() {
		return moduleUrl;
	}
	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
