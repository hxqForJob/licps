package com.hxq.web.action.cargo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
import com.hxq.domain.Finance;
import com.hxq.domain.Invoice;
import com.hxq.domain.Delegate;
import com.hxq.domain.Dept;
import com.hxq.domain.Export;
import com.hxq.domain.ExportProduct;
import com.hxq.domain.ExtEproduct;
import com.hxq.domain.User;
import com.hxq.exception.NoLoginException;
import com.hxq.service.PackageService;
import com.hxq.service.InvoiceService;
import com.hxq.service.DelegateService;
import com.hxq.service.ExportService;
import com.hxq.service.FinanceService;
import com.hxq.service.InvoiceService;
import com.hxq.utils.PackageState;
import com.hxq.utils.InvoiceState;
import com.hxq.utils.DownloadUtil;
import com.hxq.utils.FinanceState;
import com.hxq.utils.FunUtils;
import com.hxq.utils.Page;
import com.hxq.utils.SysConstant;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;
import com.hxq.domain.Package;

//财务单单控制器
@Namespace(value = "/cargo")
@Result(name = "toFinanceList", type = "redirectAction", location = "financeListAction_list")
public class FinanceAction extends BaseAction implements ModelDriven<Finance> {

	//财务模型
	private Finance model = new Finance();

	@Override
	public Finance getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	//需要财务的发票ID
	private String invoiceId;
	
	

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * 财务业务逻辑
	 */
	@Autowired
	private FinanceService financeService;

	/**
	 * 发票业务逻辑
	 */
	@Autowired
	private InvoiceService invoiceService;
	
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
	 * 委托单业务逻辑
	 */
	@Autowired
	private DelegateService delegateService;
	
	/**
	 * 财务分页参数
	 */
	private Page<Finance> page = new Page<>();

	public Page<Finance> getPage() {
		return page;
	}

	public void setPage(Page<Finance> page) {
		this.page = page;
	}
	
	/**
	 * 委托分页参数
	 */
	private Page<Invoice> invoiceListPage = new Page<>();

	public Page<Invoice> getexportPage() {
		return invoiceListPage;
	}

	public void setexportPage(Page<Invoice> page) {
		this.invoiceListPage = page;
	}

	/**
	 * 财务单视图
	 * 查询财务单
	 * @return
	 */
	@Action(value = "financeListAction_list", results = {
			@Result(name = "toList", location = "/WEB-INF/pages/cargo/finance/jFinanceListPage.jsp") })
	public String toFinanceList() {
		// 获取当前用户,用来细粒度控制
		final User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		// 获取当前用户的级别
		final Integer degree = currentUser.getUserinfo().getDegree();

		Specification<Finance> conSpe = new Specification<Finance>() {

			@Override
			public Predicate toPredicate(Root<Finance> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
		org.springframework.data.domain.Page<Finance> page2 = financeService.findPage(conSpe,
				new PageRequest(page.getPageNo() - 1, page.getPageSize()));
		page.setResults(page2.getContent());
		page.setTotalPage(page2.getTotalPages());
		page.setTotalRecord(page2.getTotalElements());
		page.setUrl("financeListAction_list");
		push(page);
		return "toList";
	}

	/**
	 * 查看财务单
	 * 
	 * @return
	 */
	@Action(value = "financeAction_toview", results = {
			@Result(name = "toFinanceView", location = "/WEB-INF/pages/cargo/finance/jFinanceView.jsp") })
	public String financeView() {

		Finance finance = financeService.get(model.getId());
		push(finance);
		return "toFinanceView";
	}

	/**
	 * 新增财务单视图
	 * 
	 * @return
	 */
	@Action(value = "financeListAction_tocreate", results = {
			@Result(name = "toCreate", location = "/WEB-INF/pages/cargo/finance/jFinanceCreate.jsp") })
	public String createFinance() {
		// 获取当前用户,用来细粒度控制
				final User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
				// 获取当前用户的级别
				final Integer degree = currentUser.getUserinfo().getDegree();

				Specification<Invoice> spec = new Specification<Invoice>() {

					@Override
					public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						// TODO Auto-generated method stub
						Predicate predicate;
						switch (degree) {
						case 4: //普通員工
							predicate=cb.and(cb.equal(root.get("createBy").as(String.class),currentUser.getId()),cb.equal(root.get("state").as(int.class), InvoiceState.SUBMMIT));
							break;
						case 3://本部门经理
							predicate=cb.and(cb.equal(root.get("createDept").as(String.class),currentUser.getDept().getId()),cb.equal(root.get("state").as(int.class), InvoiceState.SUBMMIT));
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
							predicate=cb.and(cb.equal(root.get("state").as(int.class), InvoiceState.SUBMMIT),in);
							break;
						case 1://副总裁
							predicate=cb.equal(root.get("state").as(int.class), InvoiceState.SUBMMIT);
							break;

						default://总裁
							predicate=cb.equal(root.get("state").as(int.class), InvoiceState.SUBMMIT) ;
							break;
						}
						return predicate;
					}
				};
				
				
				org.springframework.data.domain.Page<Invoice> findPage = invoiceService.
						findPage(spec, 
								new PageRequest(page.getPageNo()-1, 
										page.getPageSize()));
				invoiceListPage.setResults(findPage.getContent());
				invoiceListPage.setTotalRecord(findPage.getTotalElements());
				invoiceListPage.setUrl("financeListAction_tocreate");
				push(invoiceListPage);
		return "toCreate";
	}

	/**
	 * 新增财务单单
	 * 
	 * @return
	 * @throws NoLoginException
	 */
	@Action("financeListAction_insert")
	public String insertCotract() {
		User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		if (currentUser != null) {
			model.setMakeDate(new Date());
			model.setMaker(currentUser.getUserinfo().getName());
			model.setCreateTime(new Date());
			model.setCreateBy(currentUser.getId());
			model.setCreateDept(currentUser.getDept().getId());
		} else {
			throw new NoLoginException("当前系统未登录,请登录!");
		}
		financeService.saveOrUpdate(model,invoiceId);
		return "toFinanceList";
	}
	
	/**
	 * 打印财务单单
	 * @return
	 * @throws Exception 
	 */
	@Action("financeAction_print")
	public String printFinance() throws Exception
	{
		//获取财务单
		Finance finance=financeService.get(model.getId());
		//获取装箱单
		Package package1 = packageService.get(model.getId());
		//获取委托单
		Delegate delegate = delegateService.get(model.getId());
		//获取发票单
		Invoice invoice = invoiceService.get(model.getId());
		//装箱单下的报运单Id
		String[] exportIds = package1.getExportIds().split(", ");
		//获取装箱单下的报运单
		List<Export> exports=new ArrayList<>();
		for (String exportId : exportIds) {
			Export export = exportService.get(exportId);
			exports.add(export);
		}
		//获取文件路径
		String filePath=ServletActionContext.getServletContext().getRealPath("/make/xlsprint/tFINANCE.xls");
		filePath=filePath.replace("/", File.separator);
		// 创建工作簿
		Workbook wb = new HSSFWorkbook(new FileInputStream(filePath));
		Sheet sheet=null;
		//单元格样式
		CellStyle cellStyle1 = wb.createCellStyle();
		cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		
		CellStyle cellStyle2 = wb.createCellStyle();
		cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//复制sheet
		for (int i = 0; i < exportIds.length; i++) {
			Export export=exportService.get(exportIds[i]);
			if(i==0)
			{
				sheet=wb.getSheetAt(0);
			}
			else {
				 sheet = wb.cloneSheet(0);
			}
			wb.setSheetName(i, exportIds[i]+"报运单");
			Row row2 = sheet.getRow(1);
			Date makeDate=finance.getMakeDate();
			makeDate=FunUtils.checkIsNull(makeDate);
			DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			//制单日期
			row2.getCell(3).setCellValue(dateFormat.format(makeDate));
			//发票号
			row2.getCell(15).setCellValue(FunUtils.checkIsNull(invoice.getBlNo()));
			//合同或确认书号
			Row row3 = sheet.getRow(2);
			row3.getCell(3).setCellValue(FunUtils.checkIsNull(export.getCustomerContract()));
			//L/C NO
			row3.getCell(11).setCellValue(FunUtils.checkIsNull(export.getLcno()));
			Row row4 = sheet.getRow(3);
			//收货人地址
			row4.getCell(3).setCellValue(FunUtils.checkIsNull(export.getConsignee()));
			//备注
			row4.getCell(13).setCellValue(FunUtils.checkIsNull(export.getRemark()));
			Row row5 = sheet.getRow(4);
			//装船
			row5.getCell(2).setCellValue(FunUtils.checkIsNull(export.getShipmentPort()));
			//目的
			row5.getCell(5).setCellValue(FunUtils.checkIsNull(export.getDestinationPort()));
			//价格条件
			row5.getCell(10).setCellValue(FunUtils.checkIsNull(export.getPriceCondition()));
			// 行号
			int rowsNum = 7;
			//合计总数量
			double sumCount=0;
			//合计净重
			double sumNetWeight=0;
			//合计成本
			double sumCost=0;
			//合计税金
			double sumTax=0;
			//打印货物
			for (ExportProduct exportProduct : export.getExportProducts()) {
				Row row = sheet.createRow(rowsNum++);
				//创建单元格
				for (int j = 1; j < 18; j++) {
					Cell createCell = row.createCell(j);
					createCell.setCellStyle(cellStyle1);
					if(j==17)
					{
						createCell.setCellStyle(cellStyle2);
					}
					
				}
				//货号
				 row.getCell(1).setCellValue(FunUtils.checkIsNull(exportProduct.getProductNo()));
				//厂家
				 row.getCell(4).setCellValue(FunUtils.checkIsNull(exportProduct.getFactory().getFactoryName()));
				//单位
				 row.getCell(5).setCellValue(FunUtils.checkIsNull(exportProduct.getPackingUnit()));
				//数量
				 double count=FunUtils.checkIsNull(exportProduct.getCnumber());
				 sumCount+=count;
				 row.getCell(6).setCellValue(count);
				//件数
				 row.getCell(7).setCellValue(FunUtils.checkIsNull(exportProduct.getBoxNum()));
				//毛重
				 row.getCell(8).setCellValue(FunUtils.checkIsNull(exportProduct.getGrossWeight()));
				//净重
				 Double netWeight = FunUtils.checkIsNull(exportProduct.getNetWeight());
				 row.getCell(9).setCellValue(netWeight);
				 sumNetWeight+=netWeight;
				//长
				 row.getCell(10).setCellValue(FunUtils.checkIsNull(exportProduct.getSizeLength()));
				//宽
				 row.getCell(11).setCellValue(FunUtils.checkIsNull(exportProduct.getSizeWidth()));
				//高
				 row.getCell(12).setCellValue(FunUtils.checkIsNull(exportProduct.getSizeHeight()));
				//出口单价
				 row.getCell(13).setCellValue(FunUtils.checkIsNull(exportProduct.getExPrice()));
				//单价
				 double price=FunUtils.checkIsNull(exportProduct.getPrice());
				 //税金
				 double tax=FunUtils.checkIsNull(exportProduct.getTax());
				//收购单价（不含附件）
				 row.getCell(14).setCellValue(price);
				//收购单价含税（不含附件）
				 row.getCell(15).setCellValue(price+tax);
				 //成本
				 double cost=0;
				 //计算附件平均价格
				 for (ExtEproduct extEP : exportProduct.getExtEproducts()) {
						//附件数量
						int extCount=FunUtils.checkIsNull(extEP.getCnumber());
						//附件单价
						double extEPrice=FunUtils.checkIsNull(extEP.getPrice());
						//一个货物的成本=货物单价+（附件*附件数量/货物数量）+税金
						cost+=(extEPrice*extCount/count);
				}
				 cost+=tax+price;
				 sumCost+=cost*count;
				 sumTax+=tax*count;
				//收购单价成本（含附件）
				 row.getCell(16).setCellValue(cost);
				//收购成本税金
				 row.getCell(17).setCellValue(tax);
				 //合并单元格
				 CellRangeAddress rangeAddress=new CellRangeAddress(rowsNum-1,rowsNum-1,1,3);
				 sheet.addMergedRegion(rangeAddress);
				 
			}
			//合计行
			Row countRow = sheet.createRow(rowsNum);
			//创建单元格
			for (int j = 1; j < 18; j++) {
				Cell createCell = countRow.createCell(j);
				createCell.setCellStyle(cellStyle1);
				if(j==17)
				{
					createCell.setCellStyle(cellStyle2);
				}
				
			}
			//合并单元格
			 CellRangeAddress rangeAddress=new CellRangeAddress(rowsNum,rowsNum,1,3);
			 sheet.addMergedRegion(rangeAddress);
			//合并单元格
			 CellRangeAddress rangeAddress2=new CellRangeAddress(rowsNum,rowsNum,10,12);
			 sheet.addMergedRegion(rangeAddress2);
			 	countRow.getCell(1).setCellValue("合计");
				countRow.getCell(6).setCellValue(sumCount);//总数量
				countRow.getCell(7).setCellValue(export.getBoxNums());//总件数
				countRow.getCell(8).setCellValue(export.getGrossWeights());//总毛重
				countRow.getCell(9).setCellValue(sumNetWeight);//总净重
				countRow.getCell(10).setCellValue(export.getMeasurements()+"m3");//总体积
				countRow.getCell(13).setCellValue("---");//总出口单价
				countRow.getCell(14).setCellValue("---");//总不含税
				countRow.getCell(15).setCellValue("---");//总含税
				countRow.getCell(16).setCellValue(sumCost);//总收购成本
				countRow.getCell(17).setCellValue(sumTax);//总税金
		}
	
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpServletResponse response = ServletActionContext.getResponse();
		
			
		// 将工作簿写入字节流
		wb.write(outputStream);
		// 下载
		new DownloadUtil().download(outputStream, response,
				 "报运单.xls");
		outputStream.close();
		return NONE;
	}

	/**
	 * 修改财务单视图
	 * 
	 * @return
	 */
	@Action(value = "financeListAction_toupdate", results = {
			@Result(name = "toFinanceUpdate", location = "/WEB-INF/pages/cargo/finance/jFinanceUpdate.jsp") })
	public String updateFinanceView() {
		Finance finance = financeService.get(model.getId());
		push(finance);
		return "toFinanceUpdate";
	}

	/**
	 * 更新财务单
	 * 
	 * @return
	 */
//	@Action("financeListAction_update")
//	public String updateFinance() { // 1.先查询原有的对象
//		Finance obj = financeService.get(model.getId());
//		//获取当前登录用户,设置更新人，时间
//		User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
//		model.setCreateBy(obj.getCreateBy());
//		model.setCreateDept(obj.getCreateDept());
//		model.setCreateTime(obj.getCreateTime());
//		model.setUpdateBy(currentUser.getId());
//		model.setUpdateTime(new Date());
//		financeService.saveOrUpdate(model);
//
//		return "toFinanceList";
//	}

	/**
	 * 删除财务单
	 * 
	 * @return
	 */
	@Action(value = "financeListAction_delete")
	public String delFinance() {
		String[] ids = model.getId().split(", ");
		financeService.delete(ids);
		return "toFinanceList";
	}

	/**
	 * 提交财务单
	 */
	@Action("financeListAction_tosubmit")
	public String submitFinance() {
		String[] ids = model.getId().split(", ");
		for (String id : ids) {
			Finance finances = financeService.get(id);
			finances.setState(FinanceState.CHECK);
			financeService.saveOrUpdate(finances,null);
		}
		
		return "toFinanceList";
	}

//	/**
//	 * 取消财务单
//	 * 
//	 * @return
//	 */
//	@Action("financeAction_cancel")
//	public String cancelFinance() {
//		String[] ids = model.getId().split(", ");
//		for (String id : ids) {
//			Finance finance = financeService.get(id);
//			finance.setState(FinanceState.CANCEL);
//			financeService.saveOrUpdate(finance,null);
//		}
//		return "toFinanceList";
//	}
	
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
