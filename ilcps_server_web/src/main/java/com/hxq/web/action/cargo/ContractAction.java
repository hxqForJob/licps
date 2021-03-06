package com.hxq.web.action.cargo;


import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.hxq.domain.Contract;
import com.hxq.domain.ContractProduct;
import com.hxq.domain.Dept;
import com.hxq.domain.User;
import com.hxq.exception.NoLoginException;
import com.hxq.service.ContractProductService;
import com.hxq.service.ContractService;
import com.hxq.utils.ContractState;
import com.hxq.utils.Page;
import com.hxq.utils.SysConstant;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

//购销合同控制器
@Namespace(value = "/cargo")
@Result(name = "toContractList", type = "redirectAction", location = "contractAction_list")
public class ContractAction extends BaseAction implements ModelDriven<Contract> {

	private Contract model = new Contract();

	@Override
	public Contract getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	@Autowired
	private ContractService contractService;

	@Autowired
	private ContractProductService contractProductService;
	/**
	 * 分页参数
	 */
	private Page<Contract> page = new Page<>();

	public Page<Contract> getPage() {
		return page;
	}

	public void setPage(Page<Contract> page) {
		this.page = page;
	}

	/**
	 * 购销合同
	 * 
	 * @return
	 */
	@Action(value = "contractAction_list", results = {
			@Result(name = "toList", location = "/WEB-INF/pages/cargo/contract/jContractList.jsp") })
	public String toContractList() {
		// 获取当前用户,用来细粒度控制
		final User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		// 获取当前用户的级别
		final Integer degree = currentUser.getUserinfo().getDegree();

		Specification<Contract> conSpe = new Specification<Contract>() {

			@Override
			public Predicate toPredicate(Root<Contract> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate;
				switch (degree) {
				case 4: //普通員工
					predicate=	cb.equal(root.get("createBy").as(String.class),currentUser.getId());
					break;
				case 3://本部门经理
					predicate=cb.equal(root.get("createDept").as(String.class),currentUser.getDept().getId());
					break;
				case 2://部门总经理
					//当前用户的所在部门和子部门
					Set<String> depts=new HashSet<>();
					depts.add(currentUser.getDept().getId());
					getAllDepts(depts, currentUser.getDept());
					//添加in条件
					 In<String> in = cb.in(root.get("createDept").as(String.class));
					 for (String string : depts) {
						in.value(string);
					}
					predicate=in;
					break;
				case 1://副总裁
					predicate=null;
					break;

				default://总裁
					predicate=null;
					break;
				}
				if(predicate!=null){
					return query.where(cb.and(predicate)).orderBy(cb.desc(root.get("createTime"))).getRestriction();
				}
				return query.orderBy(cb.desc(root.get("createTime"))).getRestriction();
				
			}
		};
		org.springframework.data.domain.Page<Contract> page2 = contractService.findPage(conSpe,
				new PageRequest(page.getPageNo() - 1, page.getPageSize()));
		page.setResults(page2.getContent());
		page.setTotalPage(page2.getTotalPages());
		page.setTotalRecord(page2.getTotalElements());
		page.setUrl("contractAction_list");
		push(page);
		return "toList";
	}

	/**
	 * 查看购销合同视图
	 * 
	 * @return
	 */
	@Action(value = "contractAction_toview", results = {
			@Result(name = "toContractView", location = "/WEB-INF/pages/cargo/contract/jContractView.jsp") })
	public String contractView() {

		Contract contract = contractService.get(model.getId());
		push(contract);
		return "toContractView";
	}

	/**
	 * 新增购销合同视图
	 * 
	 * @return
	 */
	@Action(value = "contractAction_tocreate", results = {
			@Result(name = "toCreate", location = "/WEB-INF/pages/cargo/contract/jContractCreate.jsp") })
	public String createContract() {
		return "toCreate";
	}

	/**
	 * 新增购销合同
	 * 
	 * @return
	 * @throws NoLoginException
	 */
	@Action("contractAction_insert")
	public String insertCotract() {
		User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		if (currentUser != null) {
			model.setCreateTime(new Date());
			model.setCreateBy(currentUser.getId());
			model.setCreateDept(currentUser.getDept().getId());
		} else {
			throw new NoLoginException("当前系统未登录,请登录!");
		}
		contractService.saveOrUpdate(model);
		return "toContractList";
	}
	
	/**
	 * 打印购销合同
	 * @return
	 * @throws Exception 
	 */
	@Action("contractAction_print")
	public String printContract() throws Exception
	{
		Contract contract=contractService.get(model.getId());
		List<ContractProduct> contractProducts = contractProductService.find(new Specification<ContractProduct>() {
			
			@Override
			public Predicate toPredicate(Root<ContractProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				query.where(cb.equal(root.get("contract").get("id").as(String.class), model.getId()));
				query.orderBy(cb.asc(root.get("factory").get("factoryName").as(String.class)));
				return query.getRestriction();
			}
		});
		HttpServletResponse response = ServletActionContext.getResponse();
		String path=ServletActionContext.getServletContext().getRealPath("/");
		
		//打印
		new ContractPrint().print(contract, contractProducts, path, response);
		return NONE;
	}

	/**
	 * 修改购销合同视图
	 * 
	 * @return
	 */
	@Action(value = "contractAction_toupdate", results = {
			@Result(name = "toContractUpdate", location = "/WEB-INF/pages/cargo/contract/jContractUpdate.jsp") })
	public String updateContractView() {
		Contract contract = contractService.get(model.getId());
		push(contract);
		return "toContractUpdate";
	}

	/**
	 * 更新购销合同
	 * 
	 * @return
	 */
	@Action("contractAction_update")
	public String updateContract() { // 1.先查询原有的对象
		Contract obj = contractService.get(model.getId());
		// 2.针对页面上要修改的属性进行修改
		obj.setCustomName(model.getCustomName());
		obj.setPrintStyle(model.getPrintStyle());
		obj.setContractNo(model.getContractNo());
		obj.setOfferor(model.getOfferor());
		obj.setInputBy(model.getInputBy());
		obj.setCheckBy(model.getCheckBy());
		obj.setInspector(model.getInspector());
		obj.setSigningDate(model.getSigningDate());
		obj.setImportNum(model.getImportNum());
		obj.setShipTime(model.getShipTime());
		obj.setTradeTerms(model.getTradeTerms());
		obj.setDeliveryPeriod(model.getDeliveryPeriod());
		obj.setCrequest(model.getCrequest());
		obj.setRemark(model.getRemark());
		//获取当前登录用户,设置更新人，时间
		User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		obj.setUpdateBy(currentUser.getId());
		obj.setUpdateTime(new Date());
		contractService.saveOrUpdate(obj);

		return "toContractList";
	}

	/**
	 * 删除购销合同
	 * 
	 * @return
	 */
	@Action(value = "contractAction_delete")
	public String delContract() {
		String[] ids = model.getId().split(", ");
		contractService.delete(ids);
		return "toContractList";
	}

	/**
	 * 提交购销合同
	 */
	@Action("contractAction_submit")
	public String submitContract() {
		String[] ids = model.getId().split(", ");
		for (String id : ids) {
			Contract contract = contractService.get(id);
			contract.setState(ContractState.SUBMMIT);
			contractService.saveOrUpdate(contract);
		}
		
		return "toContractList";
	}

	/**
	 * 取消购销合同
	 * 
	 * @return
	 */
	@Action("contractAction_cancel")
	public String cancelContract() {
		String[] ids = model.getId().split(", ");
		for (String id : ids) {
			Contract contract = contractService.get(id);
			contract.setState(ContractState.CANCEL);
			contractService.saveOrUpdate(contract);
		}
		return "toContractList";
	}
	
	/**
	 * 递归查询所有部门Id
	 */
	private void  getAllDepts(Set<String> set,Dept dept)
	{
		if(dept.getChildDepts()!=null&&dept.getChildDepts().size()>0)
		{
			for (Dept children : dept.getChildDepts()) {
				//没有被删除
				if(children.getState()==1)
				{
					set.add(children.getId());
					getAllDepts(set, children);
				}
				
			}
		}
	}
}
