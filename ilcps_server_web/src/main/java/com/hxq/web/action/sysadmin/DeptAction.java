package com.hxq.web.action.sysadmin;

import java.util.ArrayList;
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
import com.hxq.service.DeptService;
import com.hxq.utils.Page;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

@Namespace(value= "/sysadmin")
public class DeptAction extends BaseAction implements ModelDriven<Dept> {

	/**
	 * 模型参数
	 */
	private Dept dept=new Dept();
	

	@Autowired
	private DeptService deptService;

	@Override
	public Dept getModel() {
		// TODO Auto-generated method stub
		return dept;
	}
	
	private Page<Dept> page=new Page<>();

	public Page<Dept> getPage() {
		return page;
	}

	public void setPage(Page<Dept> page) {
		this.page = page;
	}
	
	/**
	 * 分页查询部门
	 * @return
	 */
	@Action(value="deptAction_list",results={
			@Result(location="/WEB-INF/pages/sysadmin/dept/jDeptList.jsp")})
	public String searchDept()
	{
		//获取分页参数
		PageRequest pageRequest = new PageRequest(page.getPageNo()-1, page.getPageSize());
		//调用service 查询分页数据
		org.springframework.data.domain.Page<Dept> findPage = deptService.findPage(new Specification<Dept>() {
			
			@Override
			public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return cb.equal(root.get("state").as(Integer.class), 1);
			}
		}, pageRequest);
		//将查询的数据放入值栈中
		page.setTotalPage(findPage.getTotalPages());
		page.setTotalRecord(findPage.getTotalElements());
		List<Dept> content = findPage.getContent();
		page.setResults(content);
		page.setUrl("deptAction_list");
		push(page);
		return SUCCESS;
	}
	
	/**
	 * 新增部门视图
	 * @return
	 */
	@Action(value="deptAction_tocreate",results={@Result(name="createUI",location="/WEB-INF/pages/sysadmin/dept/jDeptCreate.jsp")})
	public String createDeptUI()
	{
		Specification<Dept> specification=new Specification<Dept>() {
			
			@Override
			public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
					return query.where(cb.equal(root.get("state").as(Integer.class), 1)).getRestriction() ;
			}
		};
		List<Dept> depts = deptService.find(specification);
		put("deptList", depts);//存放要选择的部门
		return "createUI";
	}
	
	/**
	 * 查看部门
	 * @return
	 */
	@Action(value="deptAction_toview",results={@Result(name="watchUI",location="/WEB-INF/pages/sysadmin/dept/jDeptView.jsp")})
	public String watchDeptUI() {
		dept=deptService.get(dept.getId());
		push(dept);
		return "watchUI";
	}
	
	/**
	 * 新增部门
	 * @return
	 */
	@Action(value="deptAction_insert",results={@Result(type="redirectAction",location="deptAction_list")})
	public String addDept()
	{	
		
		dept.setState(1);
		deptService.saveOrUpdate(dept);
		return SUCCESS;
	}
	
	
	/**
	 * 更新部门视图
	 * @return
	 */
	@Action(value="deptAction_toupdate",results={@Result(name="updateUI",location="/WEB-INF/pages/sysadmin/dept/jDeptUpdate.jsp")})
	public String updateDeptUI()
	{
		dept=deptService.get(dept.getId());
		push(dept);//修改前的数据
		Specification<Dept> specification=new Specification<Dept>() {
			
			@Override
			public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> list=new ArrayList<>();
				Predicate p1=cb.equal(root.get("state").as(Integer.class), 1);
				Predicate p2=cb.notEqual(root.get("id").as(String.class), dept.getId());
				list.add(p1);
				list.add(p2);
				Predicate[] predicates=new Predicate[list.size()];
				predicates=list.toArray(predicates);
					return query.where(predicates).getRestriction() ;
			}
		};
		
		List<Dept> depts = deptService.find(specification);//下拉框
		put("deptList", depts);//存放要选择的部门
		return "updateUI";
	}
	
	/**
	 * 更新部门
	 * @return
	 */
	@Action(value="deptAction_update",results={@Result(type="redirectAction",location="deptAction_list")})
	public String updateDept()
	{
		deptService.saveOrUpdate(dept);
		return SUCCESS;
	}
	
	@Action(value="deptAction_delete",results={@Result(type="redirectAction",location="deptAction_list")})
	public String  delDept()
	{
		String[] deptId=dept.getId().split(", ");
		for (String string : deptId) {
			
		//删除部门状态为1
		Dept sureDelDept=deptService.findDeptByIdAndState(string.trim(),1);
			//Dept sureDelDept=deptService.get(string.trim());
				if(sureDelDept!=null)
				{
					//调用递归删除子级
					//sureDelete(sureDelDept);
					//只删除子级父部门
					deleteChildrenParent(sureDelDept);
					//父
					sureDelDept.setState(0);
					deptService.saveOrUpdate(sureDelDept);
				}
				
	
			
		}
		return SUCCESS;
	}

	//根据父部门递归删除子级(修改状态)
	private void sureDelete(Dept delDept) {
		if(delDept.getChildDepts().size()>0)
		{
			for (Dept dep : delDept.getChildDepts()) {
				//递归修改状态
				sureDelete(dep);
				dep.setState(0);
				deptService.saveOrUpdate(dep);
			}
		}
		
	}
	
	//只删除子级父部门
		private void deleteChildrenParent(Dept delDept) {
			if(delDept.getChildDepts().size()>0)
			{
				for (Dept dep : delDept.getChildDepts()) {
					dep.setParent(null);;
					deptService.saveOrUpdate(dep);
				}
			}
			
		}
}
