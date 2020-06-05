package com.hxq.web.action.cargo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.hxq.domain.Invoice;
import com.hxq.domain.Delegate;
import com.hxq.domain.Dept;
import com.hxq.domain.Export;
import com.hxq.domain.ExportProduct;
import com.hxq.domain.User;
import com.hxq.exception.NoLoginException;
import com.hxq.service.PackageService;
import com.hxq.service.DelegateService;
import com.hxq.service.ExportService;
import com.hxq.service.InvoiceService;
import com.hxq.utils.PackageState;
import com.hxq.utils.DelegateState;
import com.hxq.utils.DownloadUtil;
import com.hxq.utils.FunUtils;
import com.hxq.utils.InvoiceState;
import com.hxq.utils.Page;
import com.hxq.utils.SysConstant;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;
import com.hxq.domain.Package;

//发票单单控制器
@Namespace(value = "/cargo")
@Result(name = "toInvoiceList", type = "redirectAction", location = "invoiceListAction_list")
public class InvoiceAction extends BaseAction implements ModelDriven<Invoice> {

	//发票模型
	private Invoice model = new Invoice();

	@Override
	public Invoice getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	//需要开发票的委托单ID
	private String delegateId;
	
	

	public String getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(String delegateId) {
		this.delegateId = delegateId;
	}

	/**
	 * 发票业务逻辑
	 */
	@Autowired
	private InvoiceService invoiceService;

	/**
	 * 委托业务逻辑
	 */
	@Autowired
	private DelegateService delegateService;
	
	/**
	 * 装箱单业务逻辑
	 */
	@Autowired
	private PackageService packageService;
	
	/**
	 * 报运单业务逻辑
	 */
	@Autowired
	private ExportService exportService;
	
	/**
	 * 发票分页参数
	 */
	private Page<Invoice> page = new Page<>();

	public Page<Invoice> getPage() {
		return page;
	}

	public void setPage(Page<Invoice> page) {
		this.page = page;
	}
	
	/**
	 * 委托分页参数
	 */
	private Page<Delegate> delegateListPage = new Page<>();

	public Page<Delegate> getexportPage() {
		return delegateListPage;
	}

	public void setexportPage(Page<Delegate> page) {
		this.delegateListPage = page;
	}

	/**
	 * 发票单视图
	 * 查询发票单
	 * @return
	 */
	@Action(value = "invoiceListAction_list", results = {
			@Result(name = "toList", location = "/WEB-INF/pages/cargo/invoice/jInvoiceListPage.jsp") })
	public String toInvoiceList() {
		// 获取当前用户,用来细粒度控制
		final User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		// 获取当前用户的级别
		final Integer degree = currentUser.getUserinfo().getDegree();

		Specification<Invoice> conSpe = new Specification<Invoice>() {

			@Override
			public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
				return predicate;
			}
		};
		org.springframework.data.domain.Page<Invoice> page2 = invoiceService.findPage(conSpe,
				new PageRequest(page.getPageNo() - 1, page.getPageSize()));
		page.setResults(page2.getContent());
		page.setTotalPage(page2.getTotalPages());
		page.setTotalRecord(page2.getTotalElements());
		page.setUrl("invoiceListAction_list");
		push(page);
		return "toList";
	}

	/**
	 * 查看发票单
	 * 
	 * @return
	 */
	@Action(value = "invoiceAction_toview", results = {
			@Result(name = "toInvoiceView", location = "/WEB-INF/pages/cargo/invoice/jInvoiceView.jsp") })
	public String invoiceView() {

		Invoice invoice = invoiceService.get(model.getId());
		push(invoice);
		return "toInvoiceView";
	}

	/**
	 * 新增发票单视图
	 * 
	 * @return
	 */
	@Action(value = "invoiceListAction_tocreate", results = {
			@Result(name = "toCreate", location = "/WEB-INF/pages/cargo/invoice/jInvoiceCreate.jsp") })
	public String createInvoice() {
		// 获取当前用户,用来细粒度控制
				final User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
				// 获取当前用户的级别
				final Integer degree = currentUser.getUserinfo().getDegree();

				Specification<Delegate> spec = new Specification<Delegate>() {

					@Override
					public Predicate toPredicate(Root<Delegate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						// TODO Auto-generated method stub
						Predicate predicate;
						switch (degree) {
						case 4: //普通員工
							predicate=cb.and(cb.equal(root.get("createBy").as(String.class),currentUser.getId()),cb.equal(root.get("state").as(int.class), DelegateState.SUBMMIT));
							break;
						case 3://本部门经理
							predicate=cb.and(cb.equal(root.get("createDept").as(String.class),currentUser.getDept().getId()),cb.equal(root.get("state").as(int.class), DelegateState.SUBMMIT));
							break;
						case 2://部门总经理
							//当前用户的所在部门和子部门
							Set<String> depts=new HashSet<>();
							depts.add(currentUser.getDept().getId());
							//递归获取depts
							getAllDepts(depts, currentUser.getDept());
							//添加in条件
							 In<String> in = cb.in(root.get("createDept").as(String.class));
							 for (String string : depts) {
								in.value(string);
							}
							predicate=cb.and(cb.equal(root.get("state").as(int.class), DelegateState.SUBMMIT),in);
							break;
						case 1://副总裁
							predicate=cb.equal(root.get("state").as(int.class), DelegateState.SUBMMIT);
							break;

						default://总裁
							predicate=cb.equal(root.get("state").as(int.class), DelegateState.SUBMMIT) ;
							break;
						}
						return predicate;
					}
				};
				
				
				org.springframework.data.domain.Page<Delegate> findPage = delegateService.
						findPage(spec, 
								new PageRequest(page.getPageNo()-1, 
										page.getPageSize()));
				delegateListPage.setResults(findPage.getContent());
				delegateListPage.setTotalRecord(findPage.getTotalElements());
				delegateListPage.setUrl("invoiceListAction_tocreate");
				push(delegateListPage);
		return "toCreate";
	}

	/**
	 * 新增发票单单
	 * 
	 * @return
	 * @throws NoLoginException
	 */
	@Action("invoiceListAction_insert")
	public String insertCotract() {
		User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		if (currentUser != null) {
			model.setCreateTime(new Date());
			model.setCreateBy(currentUser.getId());
			model.setCreateDept(currentUser.getDept().getId());
			model.setUpdateBy(currentUser.getId());
			model.setUpdateTime(new Date());
			model.setReallName(currentUser.getUserinfo().getName());
		} else {
			throw new NoLoginException("当前系统未登录,请登录!");
		}
		invoiceService.saveOrUpdate(model,delegateId);
		return "toInvoiceList";
	}
	
	/**
	 * 打印发票单单
	 * @return
	 * @throws Exception 
	 */
	@Action("invoiceAction_print")
	public String printInvoice() throws Exception
	{
				//总金额
				double amount=0.0;
				//获取发票单
				Invoice invoice = invoiceService.get(model.getId());
				//获取委托单
				Delegate delegate = delegateService.get(model.getId());
				//获取装箱单
				Package package1=packageService.get(model.getId());
				String[] exportIds = package1.getExportIds().split(", ");
				//计算装箱单下的货物附件总金额
				for (String exportId : exportIds) {
					Export export = exportService.get(exportId);
					Set<ExportProduct> exportProducts = export.getExportProducts();
					for (ExportProduct eP : exportProducts) {
						amount+= FunUtils.checkIsNull(eP.getCnumber())*FunUtils.checkIsNull(eP.getExPrice());
					}
				}	
				//获取文件路径
				String filePath=ServletActionContext.getServletContext().getRealPath("/make/xlsprint/tINVOICE.xls");
				filePath=filePath.replace("/", File.separator);
				// 创建工作簿
				Workbook wb = new HSSFWorkbook(new FileInputStream(filePath));
				// 获取sheet
				Sheet delegateSheet = wb.getSheetAt(0);
				//设置Seller
				Row row4 = delegateSheet.getRow(3);
				row4.getCell(0).setCellValue(FunUtils.checkIsNull(package1.getSeller()));
				//设置 Buyer
				Row row9 = delegateSheet.getRow(8);
				row9.getCell(0).setCellValue(FunUtils.checkIsNull(package1.getBuyer()));
				//设置Invoice No.
				Row row16 = delegateSheet.getRow(15);
				row16.createCell(0).setCellValue(FunUtils.checkIsNull(package1.getInvoiceNo()));
				//设置Date
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				if(package1.getInvoiceDate()!=null){
					String date=dateFormat.format(package1.getInvoiceDate());
					row16.createCell(2).setCellValue(date);
				}
				//设置s/l no
				row16.createCell(5).setCellValue(invoice.getScNo());
				//设置b/l NO
				row16.createCell(9).setCellValue(invoice.getBlNo());
				Row row20 = delegateSheet.getRow(19);
				//设置lcno
				row20.createCell(0).setCellValue(FunUtils.checkIsNull(delegate.getLcNo()));
				//设置装船港 
				row20.createCell(5).setCellValue(FunUtils.checkIsNull(delegate.getPortLoading()));
				//设置转船港
				row20.createCell(7).setCellValue(FunUtils.checkIsNull(delegate.getPortTrans()));
				//设置marks，description，quantity，unit，measurement
				Row row24 = delegateSheet.getRow(23);
				row24.getCell(0).setCellValue(FunUtils.checkIsNull(delegate.getMarksAndNos()));
				row24.getCell(3).setCellValue(FunUtils.checkIsNull(delegate.getDescription()));
				row24.getCell(5).setCellValue(FunUtils.checkIsNull(delegate.getQuantity()));
				row24.getCell(9).setCellValue(FunUtils.checkIsNull(amount));
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				HttpServletResponse response = ServletActionContext.getResponse();
				// 将工作簿写入字节流
				wb.write(outputStream);
				// 下载
				new DownloadUtil().download(outputStream, response,
						package1.getId() + "委托单.xls");
				outputStream.close();
				return NONE;
	}

	/**
	 * 修改发票单视图
	 * 
	 * @return
	 */
	@Action(value = "invoiceListAction_toupdate", results = {
			@Result(name = "toInvoiceUpdate", location = "/WEB-INF/pages/cargo/invoice/jInvoiceUpdate.jsp") })
	public String updateInvoiceView() {
		Invoice invoice = invoiceService.get(model.getId());
		push(invoice);
		return "toInvoiceUpdate";
	}

	/**
	 * 更新发票单
	 * 
	 * @return
	 */
	@Action("invoiceListAction_update")
	public String updateInvoice() { // 1.先查询原有的对象
		Invoice obj = invoiceService.get(model.getId());
		//获取当前登录用户,设置更新人，时间
		User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		model.setCreateBy(obj.getCreateBy());
		model.setCreateDept(obj.getCreateDept());
		model.setCreateTime(obj.getCreateTime());
		model.setUpdateBy(currentUser.getId());
		model.setUpdateTime(new Date());
		model.setReallName(currentUser.getUserinfo().getName());
		invoiceService.saveOrUpdate(model,null);
		return "toInvoiceList";
	}

	/**
	 * 删除发票单
	 * 
	 * @return
	 */
	@Action(value = "invoiceListAction_delete")
	public String delInvoice() {
		String[] ids = model.getId().split(", ");
		invoiceService.delete(ids);
		return "toInvoiceList";
	}

	/**
	 * 提交发票单
	 */
	@Action("invoiceListAction_tosubmit")
	public String submitInvoice() {
		String[] ids = model.getId().split(", ");
		for (String id : ids) {
			Invoice invoices = invoiceService.get(id);
			invoices.setState(InvoiceState.SUBMMIT);
			invoiceService.saveOrUpdate(invoices,null);
		}
		
		return "toInvoiceList";
	}

	/**
	 * 取消发票单
	 * 
	 * @return
	 */
	@Action("invoiceAction_cancel")
	public String cancelInvoice() {
		String[] ids = model.getId().split(", ");
		for (String id : ids) {
			Invoice invoice = invoiceService.get(id);
			invoice.setState(InvoiceState.CANCEL);
			invoiceService.saveOrUpdate(invoice,null);
		}
		return "toInvoiceList";
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
