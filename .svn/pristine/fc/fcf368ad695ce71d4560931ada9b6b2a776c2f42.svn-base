package com.hxq.web.action.sysadmin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.hxq.domain.Dept;
import com.hxq.domain.Module;
import com.hxq.service.DeptService;
import com.hxq.service.ModuleService;
import com.hxq.utils.Page;
import com.hxq.utils.UtilFuns;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 模块管理
 * @author 强仔
 *
 */
@Namespace(value= "/sysadmin")
public class ModuleAction extends BaseAction implements ModelDriven<Module> {

	/**
	 * 模型参数
	 */
	private Module module=new Module();
	

	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private DeptService deptService;

	@Override
	public Module getModel() {
		// TODO Auto-generated method stub
		return module;
	}
	
	private Page<Module> page=new Page<>();

	public Page<Module> getPage() {
		return page;
	}

	public void setPage(Page<Module> page) {
		this.page = page;
	}
	
	/**
	 * 分页查询模块
	 * @return
	 */
	@Action(value="moduleAction_list",results={
			@Result(location="/WEB-INF/pages/sysadmin/module/jModuleList.jsp")})
	public String searchModule()
	{
		//获取分页参数
		PageRequest pageRequest = new PageRequest(page.getPageNo()-1, page.getPageSize());
		//调用service 查询分页数据
		org.springframework.data.domain.Page<Module> findPage = moduleService.findPage(null, pageRequest);
		//将查询的数据放入值栈中
		page.setTotalPage(findPage.getTotalPages());
		page.setTotalRecord(findPage.getTotalElements());
		List<Module> content = findPage.getContent();
		page.setResults(content);
		page.setUrl("moduleAction_list");
		push(page);
		return SUCCESS;
	}
	
	/**
	 * 新增模块视图
	 * @return
	 */
	@Action(value="moduleAction_tocreate",results={@Result(name="createUI",location="/WEB-INF/pages/sysadmin/module/jModuleCreate.jsp")})
	public String createModuleUI()
	{
		List<Module> modules=moduleService.find(new Specification<Module>() {
			
			@Override
			public Predicate toPredicate(Root<Module> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return cb.equal(root.get("state").as(Integer.class), 1);
			}
		});
		super.put("moduleList", modules);
		return "createUI";
	}
	
	/**
	 * 查看模块
	 * @return
	 */
	@Action(value="moduleAction_toview",results={@Result(name="watchUI",location="/WEB-INF/pages/sysadmin/module/jModuleView.jsp")})
	public String watchModuleUI() {
		module=moduleService.get(module.getId());
		push(module);
		return "watchUI";
	}
	
	/**
	 * 新增模块
	 * @return
	 */
	@Action(value="moduleAction_insert",results={@Result(type="redirectAction",location="moduleAction_list")})
	public String addModule()
	{	
		if(UtilFuns.isNotEmpty(module.getParentId()))
		{
			Module pModule=moduleService.get(module.getParentId());
			module.setParentName(pModule.getName());
		}
		
		moduleService.saveOrUpdate(module);
		return SUCCESS;
	}
	
	
	/**
	 * 更新模块视图
	 * @return
	 */
	@Action(value="moduleAction_toupdate",results={@Result(name="updateUI",location="/WEB-INF/pages/sysadmin/module/jModuleUpdate.jsp")})
	public String updateModuleUI()
	{
		module=moduleService.get(module.getId());
		push(module);//修改前的数据
		return "updateUI";
	}
	
	/**
	 * 更新模块
	 * @return
	 */
	@Action(value="moduleAction_update",results={@Result(type="redirectAction",location="moduleAction_list")})
	public String updateModule()
	{
		Module updateModule = moduleService.get(module.getId());
		updateModule.setName(module.getName());
		updateModule.setLayerNum(module.getLayerNum());
		updateModule.setRemark(module.getRemark());
		updateModule.setCpermission(module.getCpermission());
		updateModule.setCurl(module.getCurl());
		updateModule.setCtype(module.getCtype());
		updateModule.setState(module.getState());
		updateModule.setBelong(module.getBelong());
		updateModule.setCwhich(module.getCwhich());
		updateModule.setRemark(module.getRemark());
		updateModule.setOrderNo(module.getOrderNo());

		moduleService.saveOrUpdate(updateModule);
		return SUCCESS;
	}
	
	/**
	 * 删除模块
	 * @return
	 */
	@Action(value="moduleAction_delete",results={@Result(type="redirectAction",location="moduleAction_list")})
	public String  delModule()
	{
		String[] ids = module.getId().split(", ");  //通过逗号空格切割成string数组
		for (String id : ids) {
			deleteModule(id);
			Module delModule=moduleService.get(id);
			if(UtilFuns.isNotEmpty(delModule))
			{
				moduleService.deleteById(id);
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 递归删除子模块
	 * @param id
	 */
	private void deleteModule(final String id) {
		// TODO Auto-generated method stub
		List<Module> find = moduleService.find(new Specification<Module>() {
			
			@Override
			public Predicate toPredicate(Root<Module> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return cb.equal(root.get("parentId").as(String.class),id);
			}
		});
		if(find!=null&&find.size()>0)
		{
			for (Module delModule : find) {
				deleteModule(delModule.getId());
				moduleService.deleteById(delModule.getId());
			}
		}
		
	}

	
	
}
