package com.hxq.web.action.sysadmin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.struts2.components.Push;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.hxq.domain.Dept;
import com.hxq.domain.Role;
import com.hxq.domain.User;
import com.hxq.domain.Userinfo;
import com.hxq.service.DeptService;
import com.hxq.service.RoleService;
import com.hxq.service.UserService;
import com.hxq.utils.Page;
import com.hxq.utils.SysConstant;
import com.hxq.utils.UtilFuns;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;


/**
 * 用户管理
 * @author 强仔
 *
 */
@Namespace(value = "/sysadmin")
public class UserAction extends BaseAction implements ModelDriven<User> {

	/**
	 * 模型参数
	 */
	private User user = new User();

	@Autowired
	private UserService userService;

	@Autowired
	private DeptService deptService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SimpleMailMessage mailMessage;
	
	@Autowired
	private MailSender mailSender;
	

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}

	// 分页参数
	private Page<User> page = new Page<>();

	//角色参数
	private String [] roleIds;
	
	
	
	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public Page<User> getPage() {
		return page;
	}

	public void setPage(Page<User> page) {
		this.page = page;
	}

	/**
	 * 分页查询用户
	 * 
	 * @return
	 */
	@Action(value = "userAction_list", results = { @Result(location = "/WEB-INF/pages/sysadmin/user/jUserList.jsp") })
	public String searchUser() {
		// 获取分页参数
		PageRequest pageRequest = new PageRequest(page.getPageNo() - 1, page.getPageSize());
		// 调用service 查询分页数据
		org.springframework.data.domain.Page<User> findPage = userService.findPage(null, pageRequest);
		// 将查询的数据放入值栈中
		page.setTotalPage(findPage.getTotalPages());
		page.setTotalRecord(findPage.getTotalElements());
		List<User> content = findPage.getContent();
		page.setResults(content);
		page.setUrl("userAction_list");
		push(page);
		return SUCCESS;
	}

	/**
	 * 新增用户视图
	 * 
	 * @return
	 */
	@Action(value = "userAction_tocreate", results = {
			@Result(name = "createUI", location = "/WEB-INF/pages/sysadmin/user/jUserCreate.jsp") })
	public String createUserUI() {
		List<User> users = userService.find(null);
		List<Dept> depts = deptService.find(new Specification<Dept>() {

			@Override
			public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return query.where(cb.equal(root.get("state").as(Integer.class), 1)).getRestriction();
			}
		});
		put("userList", users);// 存放要选择的直属领导
		put("deptList", depts);// 存放要选择的部门
		return "createUI";
	}

	/**
	 * 查看用户
	 * 
	 * @return
	 */
	@Action(value = "userAction_toview", results = {
			@Result(name = "watchUI", location = "/WEB-INF/pages/sysadmin/user/jUserView.jsp") })
	public String watchUserUI() {
		user = userService.get(user.getId());
		push(user);
		return "watchUI";
	}

	/**
	 * 新增用户
	 * 
	 * @return
	 */
	@Action(value = "userAction_insert", results = { @Result(type = "redirectAction", location = "userAction_list") })
	public String addUser() {
		
		userService.saveOrUpdate(user);
		Thread mailThread=new Thread(new Runnable() {
			public void run() {
				mailMessage.setTo(user.getUserinfo().getEmail());
				mailMessage.setSubject("您好，欢迎加入本集团");
				// 设置正文
				mailMessage.setText("您的登录名是："+user.getUserName()+"，初始密码是："+SysConstant.DEFAULT_PASS);
				mailSender.send(mailMessage);
			}
		});
		if(UtilFuns.isNotEmpty(user.getUserinfo().getEmail()))
		{
			mailThread.start();
		}
		return SUCCESS;
	}

	/**
	 * 更新用户视图
	 * 
	 * @return
	 */
	@Action(value = "userAction_toupdate", results = {
			@Result(name = "updateUI", location = "/WEB-INF/pages/sysadmin/user/jUserUpdate.jsp") })
	public String updateUserUI() {
		user = userService.get(user.getId());
		push(user);// 修改前的数据
		List<Dept> depts = deptService.find(new Specification<Dept>() {

			@Override
			public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return query.where(cb.equal(root.get("state").as(Integer.class), 1)).getRestriction();
			}
		});
		put("deptList", depts);// 存放要选择的部门
		return "updateUI";
	}

	/**
	 * 更新用户
	 * 
	 * @return
	 */
	@Action(value = "userAction_update", results = { @Result(type = "redirectAction", location = "userAction_list") })
	public String updateUser() {
		User updateUser = userService.get(user.getId());
		updateUser.setUpdateTime(new Date());
		updateUser.getUserinfo().setName(user.getUserinfo().getName());
		updateUser.setDept(user.getDept());
		updateUser.setState(user.getState());
		updateUser.getUserinfo().setDegree(user.getUserinfo().getDegree());
		userService.saveOrUpdate(updateUser);
		return SUCCESS;
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	@Action(value = "userAction_delete", results = { @Result(type = "redirectAction", location = "userAction_list") })
	public String delUser() {
		String[] userId = user.getId().split(", ");
		for (String string : userId) {

			// 删除部门状态为1
			User sureDelUser = userService.get(string.trim());
			// User sureDelUser=userService.get(string.trim());
			if (sureDelUser != null) {
				// 将子级的领导设置为空
				deleteChildrenParent(sureDelUser);
				// 父
				userService.deleteById(sureDelUser.getId());
			}

		}
		return SUCCESS;
	}

	// 只删除子级父领导
	private void deleteChildrenParent(final User delUser) {
		List<User> employees = userService.find(new Specification<User>() {
			
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return cb.equal(root.get("userinfo").get("managerId").as(String.class), delUser.getId());
			}
		});
		if (employees!=null&&employees.size() > 0) {
			for (User usr : employees) {

				usr.getUserinfo().setManagerId(null);
				userService.saveOrUpdate(usr);
			}
		}

	}

	/**
	 * 角色分配视图
	 */
	@Action(value = "userAction_torole", results = {
			@Result(name = "torole", location = "/WEB-INF/pages/sysadmin/user/jUserRole.jsp") })
	public String torole() {
		// 获取选中用户信息
		User rUser = userService.get(user.getId());
		push(rUser);
		// 获取当前用户的角色
		StringBuilder roleStr = new StringBuilder();

		for (Iterator<Role> iterator = rUser.getRoles().iterator(); iterator.hasNext();) {
			if (!"".equals(roleStr.toString())) {
				roleStr.append(",");
			}
			roleStr.append(iterator.next().getName());
		}
		System.out.println(roleStr);
		put("roleStr", roleStr.toString());
		// 获取所有角色
		List<Role> roles = roleService.find(null);
		put("roleList", roles);
		return "torole";
	}

	/**
	 * 分配权限
	 * 
	 * @return
	 */
	@Action(value = "userAction_role", results = {
			@Result(name = "role", type = "redirectAction", location = "userAction_list") })
	public String role() {
		
		//获取要修改角色的用户
		User rUser = userService.get(user.getId());
		//获取要修改后的角色集合
		HashSet<Role> roles = new HashSet<>();
		for (String roleStr : roleIds) {
			Role role = roleService.get(roleStr);
			roles.add(role);
		}
		rUser.setRoles(roles);
		userService.saveOrUpdate(rUser);
		return "role";
	}
}
