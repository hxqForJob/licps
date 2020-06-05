package com.hxq.web.action.sysadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.FastArrayList;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.hxq.domain.Dept;
import com.hxq.domain.Module;
import com.hxq.domain.Role;
import com.hxq.service.DeptService;
import com.hxq.service.ModuleService;
import com.hxq.service.RoleService;
import com.hxq.utils.Page;
import com.hxq.utils.UtilFuns;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 角色管理
 * @author 强仔
 *
 */
@Namespace(value= "/sysadmin")
public class RoleAction extends BaseAction implements ModelDriven<Role> {

	/**
	 * 模型参数
	 */
	private Role role=new Role();
	
	/**
	 *获取重选后的模块
	 */
	private String moduleIds;
	
	

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ModuleService moduleService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public Role getModel() {
		// TODO Auto-generated method stub
		return role;
	}
	
	private Page<Role> page=new Page<>();

	public Page<Role> getPage() {
		return page;
	}

	public void setPage(Page<Role> page) {
		this.page = page;
	}
	
	
	
	public String getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}

	/**
	 * 分页查询角色
	 * @return
	 */
	@Action(value="roleAction_list",results={
			@Result(location="/WEB-INF/pages/sysadmin/role/jRoleList.jsp")})
	public String searchRole()
	{
		//获取分页参数
		PageRequest pageRequest = new PageRequest(page.getPageNo()-1, page.getPageSize());
		//调用service 查询分页数据
		org.springframework.data.domain.Page<Role> findPage = roleService.findPage(null, pageRequest);
		//将查询的数据放入值栈中
		page.setTotalPage(findPage.getTotalPages());
		page.setTotalRecord(findPage.getTotalElements());
		List<Role> content = findPage.getContent();
		page.setResults(content);
		page.setUrl("roleAction_list");
		push(page);
		return SUCCESS;
	}
	
	/**
	 * 新增角色视图
	 * @return
	 */
	@Action(value="roleAction_tocreate",results={@Result(name="createUI",location="/WEB-INF/pages/sysadmin/role/jRoleCreate.jsp")})
	public String createRoleUI()
	{
		return "createUI";
	}
	
	/**
	 * 查看角色
	 * @return
	 */
	@Action(value="roleAction_toview",results={@Result(name="watchUI",location="/WEB-INF/pages/sysadmin/role/jRoleView.jsp")})
	public String watchRoleUI() {
		role=roleService.get(role.getId());
		push(role);
		return "watchUI";
	}
	
	/**
	 * 新增角色
	 * @return
	 */
	@Action(value="roleAction_insert",results={@Result(type="redirectAction",location="roleAction_list")})
	public String addRole()
	{	
		
		roleService.saveOrUpdate(role);
		return SUCCESS;
	}
	
	
	/**
	 * 更新角色视图
	 * @return
	 */
	@Action(value="roleAction_toupdate",results={@Result(name="updateUI",location="/WEB-INF/pages/sysadmin/role/jRoleUpdate.jsp")})
	public String updateRoleUI()
	{
		role=roleService.get(role.getId());
		push(role);
		return "updateUI";
	}
	
	/**
	 * 更新角色
	 * @return
	 */
	@Action(value="roleAction_update",results={@Result(type="redirectAction",location="roleAction_list")})
	public String updateRole()
	{
		Role updateRole=roleService.get(role.getId());
		updateRole.setUpdateTime(new Date());
		updateRole.setName(role.getName());
		updateRole.setRemark(role.getRemark());
		roleService.saveOrUpdate(updateRole);
		return SUCCESS;
	}
	
	/**
	 * 删除角色
	 * @return
	 */
	@Action(value="roleAction_delete",results={@Result(type="redirectAction",location="roleAction_list")})
	public String  delRole()
	{
		String[] roleIds=role.getId().split(", ");
		roleService.delete(roleIds);
		return SUCCESS;
	}

	/**
	 *返回权限分配视图
	 * @return
	 */
	@Action(value="roleAction_tomodule",results={@Result(name="toModule",location="/WEB-INF/pages/sysadmin/role/jRoleModule.jsp")})
	public String toModule()
	{	
		//获取当前角色
			role=roleService.get(role.getId());
			push(role);
		
		return "toModule";
	}
	
	/**
	 * 选中当前角色拥有的模块
	 * @return
	 */
	@Action(value="roleAction_genzTreeNodes")
	public String roleAction_genzTreeNodes()
	{
		String redisJson = (String) redisTemplate.opsForValue().get("getTreeNodes"+role.getId());
		
		if(UtilFuns.isEmpty(redisJson)){
			System.out.println("数据库读");
		//获取当前角色拥有的模块
				Set<Module> hasModules=roleService.get(role.getId()).getModules();
				//获取所有的模块
				List<Module> modules = moduleService.find(new Specification<Module>() {
					
					@Override
					public Predicate toPredicate(Root<Module> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						// TODO Auto-generated method stub
						
						return cb.equal(root.get("state").as(Integer.class),1);
					}
				});
				//返回指定json
				List<HashMap> maps=new ArrayList<>();
				
				for (Module object : modules) {
					HashMap jsonHash=new HashMap<>();
					jsonHash.put("id", object.getId());
					jsonHash.put("pId", object.getParentId());
					jsonHash.put("name", object.getName());
					if(hasModules.contains(object))
					{
						jsonHash.put("checked",true);
					}
					maps.add(jsonHash);
				}
				redisJson=JSON.toJSONString(maps);
				redisTemplate.opsForValue().set(("getTreeNodes"+role.getId()),redisJson);
		}
		else
		{
			System.out.println("redis读");
		}
				//System.out.println(faString);
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("utf-8");
				try {
					response.getWriter().write(redisJson);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				
		return NONE;
	
	}
	
	
	@Action(value="roleAction_module",results={@Result(type="redirectAction",location="/roleAction_list")})
	public String updateModule()
	{
		//获取选中的模块
		String [] idStrings=moduleIds.split(",");
		Set<Module> selectModules=new HashSet<>();
		for (String idString : idStrings) {
			selectModules.add(moduleService.get(idString));
		}
		//获取当前要保存的角色
		Role currentRole=roleService.get(role.getId());
		currentRole.setModules(selectModules);
		//更新
		roleService.saveOrUpdate(currentRole);
		return SUCCESS;
	}
}
