package com.hxq.web.action;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.hxq.domain.OfenMoudle;
import com.hxq.domain.User;
import com.hxq.service.OfenMoudleService;
import com.hxq.service.UserService;
import com.hxq.utils.SysConstant;
import com.opensymphony.xwork2.ModelDriven;


/**
 * @Description: 登录,页面跳转
 * @Author:		
 * @Company:	
 * @CreateDate:	
 */
@Namespace("/")
@Results({
	     @Result(name="title",location="/WEB-INF/pages/home/title.jsp"),
	     @Result(name="fmain",location="/WEB-INF/pages/home/fmain.jsp"),
	     @Result(name="toleft",location="/WEB-INF/pages/${moduleName}/left.jsp"),
	     @Result(name="tomain",location="/WEB-INF/pages/${moduleName}/main.jsp"),
	     @Result(name="tonoAu",location="/WEB-INF/pages/${moduleName}/noauthor.jsp"),
	     @Result(name="toUpdateUser",location="/WEB-INF/pages/${moduleName}/userinfo.jsp"),
	     @Result(name="logout",type="redirect",location="/index.jsp")
	     })
public class HomeAction extends BaseAction implements ModelDriven<User>{
	
	//user模型驱动
	private User user=new User();
	
	//新密码
	private String newPwd;
	
	//老密码
	private String oldPwd;
	
	
	
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	
	
	//常用功能业务
	@Autowired
	private OfenMoudleService ofenMoudleService;
	
	//用户业务
	@Autowired
	private UserService userService;
	
	private String moduleName;		//动态指定跳转的模块，在struts.xml中配置动态的result
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Action("homeAction_fmain")
	public String fmain(){
		return "fmain";
	}
	@Action("homeAction_title")
	public String title(){
		return "title";
	}

	//转向moduleName指向的模块
	@Action("homeAction_tomain")
	public String tomain(){
		return "tomain";
	}
	
	@Action("homeAction_toleft")
	public String toleft(){
		
		final User user = (User)session.get(SysConstant.CURRENT_USER_INFO);
		//取前五
		PageRequest pageRequest=new PageRequest(0, 5);
		//查询当前用户的常用功能
		Page<OfenMoudle> pages = ofenMoudleService.findPage(new Specification<OfenMoudle>() {
			
			@Override
			public Predicate toPredicate(Root<OfenMoudle> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate = cb.equal(root.get("userId").as(String.class), user.getId());
				return query.where(predicate).orderBy(new OrderImpl(root.get("times").as(int.class)).reverse()).getRestriction();
			}
		},pageRequest);
		List<OfenMoudle> contetnts = pages.getContent();
		put("menus", contetnts);
		return "toleft";
	}
	
	@Action("homeAction_noauthor")
	public String tonoauthor(){
		return "tonoAu";
	}
	
	/**
	 * 清楚常用功能
	 * @return
	 */
	@Action("homeAction_deleteRecordMenu")
	public String deleteRecordMenu()
	{
		//获取当前用户
		final User user = (User)session.get(SysConstant.CURRENT_USER_INFO);
		List<OfenMoudle> find = ofenMoudleService.find(new Specification<OfenMoudle>() {

			@Override
			public Predicate toPredicate(Root<OfenMoudle> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return cb.equal(root.get("userId").as(String.class), user.getId());
			}
		});
		if(find!=null&&find.size()>0)
		{
			for (OfenMoudle ofenMoudle : find) {
				ofenMoudleService.deleteById(ofenMoudle.getId());
			}
		}
		
		return "toleft";
	}
	
	
	/**
	 * 去更新用户视图
	 * @return
	 */
	@Action("homeAction_toUpdateUserInfo")
	public String toUpdateUserView()
	{
		user=(User)session.get(SysConstant.CURRENT_USER_INFO);
		push(user);
		return "toUpdateUser";
	}
	
	/**
	 * 修改密码视图
	 * @return
	 */
	@Action(value="homeAction_toUpdateUserPwd",results={
			@Result(name="toUpdatePwd",location="/WEB-INF/pages/home/updatePwd.jsp")
	})
	public String toUpdateUserPwd()
	{
		return "toUpdatePwd";
	}
	
	
	/**
	 * 修改密码
	 * @return
	 */
	@Action(value="homeAction_updateUserPwd",results={
			@Result(name="pwdError",location="/WEB-INF/pages/home/updatePwd.jsp"),
			@Result(name="logout",type="redirectAction",location="/loginAction_logout")})
	public String updateUserPwd()
	{
		user=(User)session.get(SysConstant.CURRENT_USER_INFO);
		oldPwd=new Md5Hash(new String(oldPwd),user.getUserName(),2).toString();
		
		if(user.getPassword().equals(oldPwd))
		{
			//旧密码匹配
			newPwd=new Md5Hash(new String(newPwd),user.getUserName(),2).toString();
			user.setPassword(newPwd);
			userService.saveOrUpdate(user);
			return "logout";
		}else {
			//旧密码不匹配
			put("errorMsg", "当前密码不匹配,请重新输入！");
			return "pwdError";
		}
		
	}
	
	
	
	/**	
	 * 修改用户信息
	 */
	@Action(value="homeAction_UpdateUserInfo")
	public String updateUser()
	{
		moduleName="home";
		List<com.hxq.domain.User> users = userService.find(new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("id").as(String.class), user.getId());
			}
		});
		if(user!=null&&users.size()>0)
		{
			User selectUser=users.get(0);
			selectUser.getUserinfo().setBirthday(user.getUserinfo().getBirthday());
			selectUser.getUserinfo().setEmail(user.getUserinfo().getEmail());
			selectUser.getUserinfo().setGender(user.getUserinfo().getGender());
			selectUser.getUserinfo().setName(user.getUserinfo().getName());
			selectUser.getUserinfo().setTelephone(user.getUserinfo().getTelephone());
			selectUser.getUserinfo().setUpdateBy(user.getId());
			selectUser.getUserinfo().setUpdateTime(new Date());
			userService.saveOrUpdate(selectUser);
			session.put(SysConstant.CURRENT_USER_INFO, selectUser);
		}
		return "tomain";
	}
	
	

}
