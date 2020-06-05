package com.hxq.web.action;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hxq.domain.LoginLog;
import com.hxq.domain.User;
import com.hxq.service.LoginLogService;
import   com.hxq.utils.SysConstant;
import   com.hxq.utils.UtilFuns;

/**
 * @Description: 登录和退出类
 * @Author:		强仔
 * @Company:	
 * @CreateDate:	2019年7月31日
 * 
 * 继承BaseAction的作用
 * 1.可以与struts2的API解藕合
 * 2.还可以在BaseAction中提供公有的通用方法
 */
@Namespace("/")
@Results({
	@Result(name="login",location="/WEB-INF/pages/sysadmin/login/login.jsp"),
	@Result(name="success",location="/WEB-INF/pages/home/fmain.jsp"),
	@Result(name="logout",location="/index.jsp")})
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String username;//用户名	
	private String password;//密码

	@Autowired
	private LoginLogService loginLogService;


	//SSH传统登录方式
	@Action("loginAction_login")
	public String login() throws Exception {
		if(UtilFuns.isEmpty(username)||UtilFuns.isEmpty(password))
		{
			return "login";
		}
		//获取shiro的subject
		Subject subject = SecurityUtils.getSubject();
		//自定义token
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		//登录,自动会执行AuthRealm的认证方法。异常说明用户名或密码错误。
		try {
			subject.login(token);
		} catch (Exception e) {
			// TODO: handle exception
			super.put("errorInfo", "用户名或者密码错误！");
			return "login";
		}
		//从shiro中获取用户
		User user=(User)subject.getPrincipal();
		//存放到session 中
		session.put(SysConstant.CURRENT_USER_INFO, user);
		//记录到LoginLog中
		LoginLog loginLog=new LoginLog();
		loginLog.setIpAddress(ServletActionContext.getRequest().getLocalAddr());
		loginLog.setLoginName(user.getUserName());
		loginLog.setLoginTime(new Date());
		loginLogService.saveOrUpdate(loginLog);
		return SUCCESS;
	}
	
	
	//退出
	@Action("loginAction_logout")
	public String logout(){
		session.remove(SysConstant.CURRENT_USER_INFO);		//删除session
		SecurityUtils.getSubject().logout();   //登出
		return "logout";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

