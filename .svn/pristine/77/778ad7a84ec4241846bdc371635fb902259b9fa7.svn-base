package com.hxq.client.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * BaseAction
 * @author Administrator
 */
@Controller
@ParentPackage("struts-default")
@Scope("prototype")
public class BaseAction extends ActionSupport implements RequestAware, SessionAware, ApplicationAware{
	
	private static Logger log = Logger.getLogger(BaseAction.class);
	
	private static final long serialVersionUID = 1L;

	protected Map<String, Object> request;
	protected Map<String, Object> session;
	protected Map<String, Object> application;

	public Map<String, Object> getRequest() {
		return request;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public Map<String, Object> getApplication() {
		return application;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	/**
	 * 
	 * 将一个对象放入栈顶
	 */
	public void push(Object obj){
		ActionContext.getContext().getValueStack().push(obj);
	}
	
	/**
	 * 放集合放入值栈中    context区域
	 */
	public void put(String key,Object value){
		ActionContext.getContext().put(key, value);
	}
	
}
