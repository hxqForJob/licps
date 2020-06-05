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
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
import com.hxq.domain.Delegate;
import com.hxq.domain.Dept;
import com.hxq.domain.Export;
import com.hxq.domain.ExportProduct;
import com.hxq.domain.User;
import com.hxq.exception.NoLoginException;
import com.hxq.service.PackageService;
import com.hxq.service.DelegateService;
import com.hxq.service.ExportService;
import com.hxq.utils.PackageState;
import com.hxq.utils.DelegateState;
import com.hxq.utils.DownloadUtil;
import com.hxq.utils.FunUtils;
import com.hxq.utils.Page;
import com.hxq.utils.SysConstant;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;
import com.hxq.domain.Package;

//委托单单控制器
@Namespace(value = "/cargo")
@Result(name = "toDelegateList", type = "redirectAction", location = "delegateListAction_list")
public class DelegateAction extends BaseAction implements ModelDriven<Delegate> {

	//委托模型
	private Delegate model = new Delegate();

	@Override
	public Delegate getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	//需要委托的装箱ID
	private String packageId;
	
	

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	/**
	 * 委托业务逻辑
	 */
	@Autowired
	private DelegateService delegateService;

	/**
	 * 装箱业务逻辑
	 */
	@Autowired
	private PackageService packageService;
	
	/**
	 * 报运单业务逻辑
	 */
	@Autowired
	private ExportService exportService;
	
	/**
	 * 委托分页参数
	 */
	private Page<Delegate> page = new Page<>();

	public Page<Delegate> getPage() {
		return page;
	}

	public void setPage(Page<Delegate> page) {
		this.page = page;
	}
	
	/**
	 * 装箱分页参数
	 */
	private Page<Package> packingListPage = new Page<>();

	public Page<Package> getexportPage() {
		return packingListPage;
	}

	public void setexportPage(Page<Package> page) {
		this.packingListPage = page;
	}

	/**
	 * 委托单视图
	 * 查询委托单
	 * @return
	 */
	@Action(value = "delegateListAction_list", results = {
			@Result(name = "toList", location = "/WEB-INF/pages/cargo/delegate/jDelegateListPage.jsp") })
	public String toDelegateList() {
		// 获取当前用户,用来细粒度控制
		final User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		// 获取当前用户的级别
		final Integer degree = currentUser.getUserinfo().getDegree();

		Specification<Delegate> conSpe = new Specification<Delegate>() {

			@Override
			public Predicate toPredicate(Root<Delegate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
		org.springframework.data.domain.Page<Delegate> page2 = delegateService.findPage(conSpe,
				new PageRequest(page.getPageNo() - 1, page.getPageSize()));
		page.setResults(page2.getContent());
		page.setTotalPage(page2.getTotalPages());
		page.setTotalRecord(page2.getTotalElements());
		page.setUrl("delegateListAction_list");
		push(page);
		return "toList";
	}

	/**
	 * 查看委托单
	 * 
	 * @return
	 */
	@Action(value = "delegateAction_toview", results = {
			@Result(name = "toDelegateView", location = "/WEB-INF/pages/cargo/delegate/jDelegateView.jsp") })
	public String delegateView() {

		Delegate delegate = delegateService.get(model.getId());
		push(delegate);
		return "toDelegateView";
	}

	/**
	 * 新增委托单视图
	 * 
	 * @return
	 */
	@Action(value = "delegateListAction_tocreate", results = {
			@Result(name = "toCreate", location = "/WEB-INF/pages/cargo/delegate/jDelegateCreate.jsp") })
	public String createDelegate() {
		// 获取当前用户,用来细粒度控制
				final User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
				// 获取当前用户的级别
				final Integer degree = currentUser.getUserinfo().getDegree();

				Specification<Package> spec = new Specification<Package>() {

					@Override
					public Predicate toPredicate(Root<Package> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						// TODO Auto-generated method stub
						Predicate predicate;
						switch (degree) {
						case 4: //普通員工
							predicate=cb.and(cb.equal(root.get("createBy").as(String.class),currentUser.getId()),cb.equal(root.get("state").as(int.class), PackageState.SUBMMIT));
							break;
						case 3://本部门经理
							predicate=cb.and(cb.equal(root.get("createDept").as(String.class),currentUser.getDept().getId()),cb.equal(root.get("state").as(int.class), PackageState.SUBMMIT));
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
							predicate=cb.and(cb.equal(root.get("state").as(int.class), PackageState.SUBMMIT),in);
							break;
						case 1://副总裁
							predicate=cb.equal(root.get("state").as(int.class), PackageState.SUBMMIT);
							break;

						default://总裁
							predicate=cb.equal(root.get("state").as(int.class), PackageState.SUBMMIT) ;
							break;
						}
						return predicate;
					}
				};
				
				
				org.springframework.data.domain.Page<Package> findPage = packageService.
						findPage(spec, 
								new PageRequest(page.getPageNo()-1, 
										page.getPageSize()));
				packingListPage.setResults(findPage.getContent());
				packingListPage.setTotalRecord(findPage.getTotalElements());
				packingListPage.setUrl("delegateListAction_tocreate");
				push(packingListPage);
		return "toCreate";
	}

	/**
	 * 新增委托单
	 * 
	 * @return
	 * @throws NoLoginException
	 */
	@Action("delegateListAction_insert")
	public String insertCotract() {
		User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		if (currentUser != null) {
			model.setCreateTime(new Date());
			model.setCreateBy(currentUser.getId());
			model.setCreateDept(currentUser.getDept().getId());
		} else {
			throw new NoLoginException("当前系统未登录,请登录!");
		}
		delegateService.saveOrUpdate(model,packageId.trim());
		return "toDelegateList";
	}
	
	/**
	 * 打印委托单单
	 * @return
	 * @throws Exception 
	 */
	@Action("delegateAction_print")
	public String printDelegate() throws Exception
	{
		//总毛重
		Double sumWg=0.0;
		//总体积
		Double sumV=0.0;
		//获取委托单
		Delegate delegate = delegateService.get(model.getId());
		//获取装箱单
		Package package1=packageService.get(model.getId());
		String[] exportIds = package1.getExportIds().split(", ");
		//获取所有装箱单下的货物
		for (String exportId : exportIds) {
			Export export = exportService.get(exportId);
			sumWg+=export.getGrossWeights();
			sumV+=export.getMeasurements();
		}	
		//获取文件路径
		String filePath=ServletActionContext.getServletContext().getRealPath("/make/xlsprint/tSHIPPINGORDER.xls");
		filePath=filePath.replace("/", File.separator);
		// 创建工作簿
		Workbook wb = new HSSFWorkbook(new FileInputStream(filePath));
		// 获取sheet
		Sheet delegateSheet = wb.getSheetAt(0);
		//设置Shipper	
		Row row4 = delegateSheet.getRow(3);
		row4.getCell(0).setCellValue(FunUtils.checkIsNull(delegate.getShipper()));
		//设置Consignee
		Row row9 = delegateSheet.getRow(8);
		row9.getCell(0).setCellValue(FunUtils.checkIsNull(delegate.getConsihnee()));
		//设置Orignal Notify Party
		Row row16 = delegateSheet.getRow(15);
		row16.getCell(0).setCellValue(FunUtils.checkIsNull(delegate.getOriginalNotifyParty()));
		//设置发票
		Row row20 = delegateSheet.getRow(19);
		row20.createCell(0).setCellValue(FunUtils.checkIsNull(package1.getInvoiceNo()));
		//设置InvoiceDate
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		if(package1.getInvoiceDate()!=null){
			String date=dateFormat.format(package1.getInvoiceDate());
			row20.createCell(3).setCellValue(date);
		}
		//设置lcno
		row20.createCell(6).setCellValue(FunUtils.checkIsNull(delegate.getLcNo()));
		//设置装船港 
		Row row24 = delegateSheet.getRow(23);
		row24.createCell(0).setCellValue(FunUtils.checkIsNull(delegate.getPortLoading()));
		//设置转船港
		row24.createCell(3).setCellValue(FunUtils.checkIsNull(delegate.getPortTrans()));
		//设置卸货港 
		row24.createCell(6).setCellValue(FunUtils.checkIsNull(delegate.getPortDischange()));
		//设置装期,效期,是否分批,是否转船,份数
		Row row28 = delegateSheet.getRow(27);
		String installDate=dateFormat.format(delegate.getInstallPeriod());
		String effectDate=dateFormat.format(delegate.getEffectPeriod());
		row28.createCell(0).setCellValue(FunUtils.checkIsNull(installDate));
		row28.createCell(2).setCellValue(FunUtils.checkIsNull(effectDate));
		if("0".equals(FunUtils.checkIsNull(delegate.getIsBatches())))
		{
			row28.createCell(3).setCellValue("否");
		}else {
			row28.createCell(3).setCellValue("是");
		}
		if("0".equals(FunUtils.checkIsNull(delegate.getIsTransshipment())))
		{
			row28.createCell(5).setCellValue("否");
		}else {
			row28.createCell(5).setCellValue("是");
		}
		row28.createCell(7).setCellValue(FunUtils.checkIsNull(delegate.getCopies()));
		//设置marks，description，quantity，grossweight，measurement
		Row row32 = delegateSheet.getRow(31);
		row32.getCell(0).setCellValue(FunUtils.checkIsNull(delegate.getMarksAndNos()));
		row32.getCell(3).setCellValue(FunUtils.checkIsNull(delegate.getDescription()));
		row32.getCell(5).setCellValue(FunUtils.checkIsNull(delegate.getQuantity()));
		row32.getCell(6).setCellValue(FunUtils.checkIsNull(sumWg));
		row32.getCell(8).setCellValue(FunUtils.checkIsNull(sumV));
		//设置运输要求
		Row row38 = delegateSheet.getRow(37);
		row38.getCell(1).setCellValue(delegate.getSpecialCondition());
		//设置复核人
		Row row44 = delegateSheet.getRow(43);
		row44.createCell(7).setCellValue(delegate.getReviewer());
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
	 * 修改委托单视图
	 * 
	 * @return
	 */
	@Action(value = "delegateListAction_toupdate", results = {
			@Result(name = "toDelegateUpdate", location = "/WEB-INF/pages/cargo/delegate/jDelegateUpdate.jsp") })
	public String updateDelegateView() {
		Delegate delegate = delegateService.get(model.getId());
		push(delegate);
		return "toDelegateUpdate";
	}

	/**
	 * 更新委托单
	 * 
	 * @return
	 */
	@Action("delegateListAction_update")
	public String updateDelegate() { // 1.先查询原有的对象
		Delegate obj = delegateService.get(model.getId());
		//获取当前登录用户,设置更新人，时间
		User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		model.setQuantity(obj.getQuantity());
		model.setCreateBy(obj.getCreateBy());
		model.setCreateDept(obj.getCreateDept());
		model.setCreateTime(obj.getCreateTime());
		model.setUpdateBy(currentUser.getId());
		model.setUpdateTime(new Date());
		delegateService.saveOrUpdate(model,null);

		return "toDelegateList";
	}

	/**
	 * 删除委托单
	 * 
	 * @return
	 */
	@Action(value = "delegateListAction_delete")
	public String delDelegate() {
		String[] ids = model.getId().split(", ");
		delegateService.delete(ids);
		return "toDelegateList";
	}

	/**
	 * 提交委托单
	 */
	@Action("delegateListAction_tosubmit")
	public String submitDelegate() {
		String[] ids = model.getId().split(", ");
		for (String id : ids) {
			Delegate delegates = delegateService.get(id);
			delegates.setState(DelegateState.SUBMMIT);
			delegateService.saveOrUpdate(delegates,null);
		}
		
		return "toDelegateList";
	}

	/**
	 * 取消委托单
	 * 
	 * @return
	 */
	@Action("delegateAction_cancel")
	public String cancelDelegate() {
		String[] ids = model.getId().split(", ");
		for (String id : ids) {
			Delegate delegate = delegateService.get(id);
			delegate.setState(DelegateState.CANCEL);
			delegateService.saveOrUpdate(delegate,null);
		}
		return "toDelegateList";
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
