package com.hxq.web.action.baseinfo;

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
import com.hxq.domain.Factory;
import com.hxq.domain.User;
import com.hxq.service.FactoryService;
import com.hxq.utils.Page;
import com.hxq.utils.SysConstant;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

@Namespace(value= "/baseinfo")
public class FactoryAction extends BaseAction implements ModelDriven<Factory> {

	/**
	 * 模型参数
	 */
	private Factory factory=new Factory();
	

	@Autowired
	private FactoryService factoryService;

	@Override
	public Factory getModel() {
		// TODO Auto-generated method stub
		return factory;
	}
	
	private Page<Factory> page=new Page<>();

	public Page<Factory> getPage() {
		return page;
	}

	public void setPage(Page<Factory> page) {
		this.page = page;
	}
	
	/**
	 * 分页查询工厂
	 * @return
	 */
	@Action(value="factoryAction_tolist",results={
			@Result(location="/WEB-INF/pages/baseinfo/factory/factorylist.jsp")})
	public String searchFactory()
	{
		//获取分页参数
		PageRequest pageRequest = new PageRequest(page.getPageNo()-1, page.getPageSize());
		//调用service 查询分页数据
		org.springframework.data.domain.Page<Factory> findPage = factoryService.findPage(new Specification<Factory>() {
			
			@Override
			public Predicate toPredicate(Root<Factory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return cb.equal(root.get("state").as(Integer.class), 1);
			}
		}, pageRequest);
		//将查询的数据放入值栈中
		page.setTotalPage(findPage.getTotalPages());
		page.setTotalRecord(findPage.getTotalElements());
		List<Factory> content = findPage.getContent();
		page.setResults(content);
		page.setUrl("factoryAction_tolist");
		push(page);
		return SUCCESS;
	}
	
	/**
	 * 新增工厂视图
	 * @return
	 */
	@Action(value="factoryAction_tocreate",results={@Result(name="createUI",location="/WEB-INF/pages/baseinfo/factory/jFactoryCreate.jsp")})
	public String createFactoryUI()
	{
		return "createUI";
	}
	
	/**
	 * 查看工厂
	 * @return
	 */
	@Action(value="factoryAction_toview",results={@Result(name="watchUI",location="/WEB-INF/pages/baseinfo/factory/jFactoryView.jsp")})
	public String watchFactoryUI() {
		factory=factoryService.get(factory.getId());
		push(factory);
		return "watchUI";
	}
	
	/**
	 * 新增工厂
	 * @return
	 */
	@Action(value="factoryAction_insert",results={@Result(type="redirectAction",location="factoryAction_tolist")})
	public String addFactory()
	{	 User user=(User)session.get(SysConstant.CURRENT_USER_INFO);
		factory.setCreateTime(new Date());
		factory.setCreateBy(user.getId());
		factory.setCreateDept(user.getDept().getId());
		factory.setState("1");
		factory.setUpdateBy(user.getId());
		factory.setUpdateTime(new Date());
		factoryService.saveOrUpdate(factory);
		return SUCCESS;
	}
	
	
	/**
	 * 更新工厂视图
	 * @return
	 */
	@Action(value="factoryAction_toupdate",results={@Result(name="updateUI",location="/WEB-INF/pages/baseinfo/factory/jFactoryUpdate.jsp")})
	public String updateFactoryUI()
	{
		factory=factoryService.get(factory.getId());
		push(factory);//修改前的数据
		return "updateUI";
	}
	
	/**
	 * 更新工厂
	 * @return
	 */
	@Action(value="factoryAction_update",results={@Result(type="redirectAction",location="factoryAction_tolist")})
	public String updateFactory()
	{
		User user=(User)session.get(SysConstant.CURRENT_USER_INFO);
		factory.setUpdateBy(user.getId());
		factory.setUpdateTime(new Date());
		Factory oldFactory=factoryService.find(new Specification<Factory>() {

			@Override
			public Predicate toPredicate(Root<Factory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return null;
			}
		}).get(0);
		factory.setCreateBy(oldFactory.getCreateBy());
		factory.setCreateDept(oldFactory.getCreateDept());
		factory.setCreateTime(oldFactory.getCreateTime());
		factoryService.saveOrUpdate(factory);
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@Action(value="factoryAction_delete",results={@Result(type="redirectAction",location="factoryAction_tolist")})
	public String  delFactory()
	{
		String[] factoryId=factory.getId().split(", ");
		factoryService.delete(factoryId);
		return SUCCESS;
	}

	
	
}
