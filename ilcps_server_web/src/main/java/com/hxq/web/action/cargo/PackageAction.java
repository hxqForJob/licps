package com.hxq.web.action.cargo;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

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
import com.hxq.domain.Package;
import com.hxq.domain.Dept;
import com.hxq.domain.Export;
import com.hxq.domain.ExportProduct;
import com.hxq.domain.User;
import com.hxq.exception.NoLoginException;
import com.hxq.service.ExportProductService;
import com.hxq.service.ExportService;
import com.hxq.service.PackageService;
import com.hxq.utils.DownloadUtil;
import com.hxq.utils.ExportState;
import com.hxq.utils.FunUtils;
import com.hxq.utils.PackageState;
import com.hxq.utils.Page;
import com.hxq.utils.SysConstant;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

//装箱单控制器
@Namespace(value = "/cargo")
@Result(name = "toPackageList", type = "redirectAction", location = "packingListAction_list")
public class PackageAction extends BaseAction implements ModelDriven<Package> {

	private Package model = new Package();

	@Override
	public Package getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	@Autowired
	private PackageService packageService;

	@Autowired
	private ExportService exportService;
	
	/**
	 * 装箱分页参数
	 */
	private Page<Package> page = new Page<>();

	public Page<Package> getPage() {
		return page;
	}

	public void setPage(Page<Package> page) {
		this.page = page;
	}
	
	/**
	 * 装箱分页参数
	 */
	private Page<Export> exportPage = new Page<>();

	public Page<Export> getexportPage() {
		return exportPage;
	}

	public void setexportPage(Page<Export> page) {
		this.exportPage = page;
	}

	/**
	 * 装箱单
	 * 
	 * @return
	 */
	@Action(value = "packingListAction_list", results = {
			@Result(name = "toList", location = "/WEB-INF/pages/cargo/packinglist/jPackingListListPage.jsp") })
	public String toPackageList() {
		// 获取当前用户,用来细粒度控制
		final User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		// 获取当前用户的级别
		final Integer degree = currentUser.getUserinfo().getDegree();

		Specification<Package> conSpe = new Specification<Package>() {

			@Override
			public Predicate toPredicate(Root<Package> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
		org.springframework.data.domain.Page<Package> page2 = packageService.findPage(conSpe,
				new PageRequest(page.getPageNo() - 1, page.getPageSize()));
		page.setResults(page2.getContent());
		page.setTotalPage(page2.getTotalPages());
		page.setTotalRecord(page2.getTotalElements());
		page.setUrl("packingListAction_list");
		push(page);
		return "toList";
	}

	/**
	 * 查看装箱单
	 * 
	 * @return
	 */
	@Action(value = "packageAction_toview", results = {
			@Result(name = "toPackageView", location = "/WEB-INF/pages/cargo/packinglist/jPackingListView.jsp") })
	public String packageView() {

		Package package2 = packageService.get(model.getId());
		push(package2);
		return "toPackageView";
	}

	/**
	 * 新增装箱单视图
	 * 
	 * @return
	 */
	@Action(value = "packingListAction_tocreate", results = {
			@Result(name = "toCreate", location = "/WEB-INF/pages/cargo/packinglist/jPackingListCreate.jsp") })
	public String createPackage() {
		// 获取当前用户,用来细粒度控制
				final User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
				// 获取当前用户的级别
				final Integer degree = currentUser.getUserinfo().getDegree();

				Specification<Export> spec = new Specification<Export>() {

					@Override
					public Predicate toPredicate(Root<Export> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						// TODO Auto-generated method stub
						Predicate predicate;
						switch (degree) {
						case 4: //普通員工
							predicate=cb.and(cb.equal(root.get("createBy").as(String.class),currentUser.getId()),cb.equal(root.get("state").as(int.class), ExportState.SUBMMIT));
							break;
						case 3://本部门经理
							predicate=cb.and(cb.equal(root.get("createDept").as(String.class),currentUser.getDept().getId()),cb.equal(root.get("state").as(int.class), ExportState.SUBMMIT));
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
							predicate=cb.and(cb.equal(root.get("state").as(int.class), ExportState.SUBMMIT),in);
							break;
						case 1://副总裁
							predicate=cb.equal(root.get("state").as(int.class), ExportState.SUBMMIT);
							break;

						default://总裁
							predicate=cb.equal(root.get("state").as(int.class), ExportState.SUBMMIT) ;
							break;
						}
						return predicate;
					}
				};
				
				
				org.springframework.data.domain.Page<Export> findPage = exportService.
						findPage(spec, 
								new PageRequest(page.getPageNo()-1, 
										page.getPageSize()));
				exportPage.setResults(findPage.getContent());
				exportPage.setTotalRecord(findPage.getTotalElements());
				exportPage.setUrl("packingListAction_tocreate");
				push(exportPage);
		return "toCreate";
	}

	/**
	 * 新增装箱单
	 * 
	 * @return
	 * @throws NoLoginException
	 */
	@Action("packingListAction_insert")
	public String insertCotract() {
		User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		if (currentUser != null) {
			model.setCreateTime(new Date());
			model.setCreateBy(currentUser.getId());
			model.setCreateDept(currentUser.getDept().getId());
		} else {
			throw new NoLoginException("当前系统未登录,请登录!");
		}
		packageService.saveOrUpdate(model);
		return "toPackageList";
	}
	
	/**
	 * 打印装箱单
	 * @return
	 * @throws Exception 
	 */
	@Action("packageAction_print")
	public String printPackage() throws Exception
	{
		//获取装箱单
		Package package1 = packageService.get(model.getId());
		String[] exportIds = package1.getExportIds().split(", ");
		//获取所有装箱单下的货物
		List<ExportProduct> exports=new ArrayList<>();
		//总毛重
		Double sumWg=0.0;
		//总体积
		Double sumV=0.0;
		for (String exportId : exportIds) {
			Export export = exportService.get(exportId);
			sumWg+=FunUtils.checkIsNull(export.getGrossWeights());
			sumV+=FunUtils.checkIsNull(export.getMeasurements());
			exports.addAll(export.getExportProducts());
		}	
		//获取文件路径
		String filePath=ServletActionContext.getServletContext().getRealPath("/make/xlsprint/tPARKINGLIST.xls");
		filePath=filePath.replace("/", File.separator);
		// 创建工作簿
		Workbook wb = new HSSFWorkbook(new FileInputStream(filePath));
		// 获取sheet
		Sheet packingSheet = wb.getSheetAt(0);
		//设置seller
		Row row4 = packingSheet.getRow(3);
		row4.getCell(0).setCellValue(FunUtils.checkIsNull(package1.getSeller()));
		//设置Buyer
		Row row9 = packingSheet.getRow(8);
		row9.getCell(0).setCellValue(FunUtils.checkIsNull(package1.getBuyer()));
		
		Row row16 = packingSheet.getRow(15);
		//设置InvoiceNo
		row16.getCell(0).setCellValue(FunUtils.checkIsNull(package1.getInvoiceNo()));
		//设置InvoiceDate
		if(package1.getInvoiceDate()!=null)
		{
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String date=dateFormat.format(package1.getInvoiceDate());
			row16.getCell(3).setCellValue(date);
		}
		//设置marks
		Row row19 = packingSheet.getRow(19);
		row19.getCell(0).setCellValue(FunUtils.checkIsNull(package1.getMarks()));
		row19.getCell(2).setCellValue(FunUtils.checkIsNull(package1.getDescription()));
		// 行号
		int rowsNum = 20;
		//总数
		Long sums=0l;
		//总净重
		Double sumNg=0.0;
		
		//打印报运单下的货物
		for (ExportProduct eProduct : exports) {
			Row row = packingSheet.getRow(rowsNum);
			if(row==null)
			{
				row=packingSheet.createRow(rowsNum);
			}
			//货物编号
			Cell cell1 = row.createCell(2);
			cell1.setCellValue(FunUtils.checkIsNull(eProduct.getProductNo()));
			//货物数量
			Cell cell6 = row.createCell(6);
			cell6.setCellValue(FunUtils.checkIsNull(eProduct.getCnumber()));
			sums+=FunUtils.checkIsNull(eProduct.getCnumber());
			//数量单位
			Cell cell7 = row.createCell(7);
			cell7.setCellValue(FunUtils.checkIsNull(eProduct.getPackingUnit()));
			//货物毛重
			Cell cell8 = row.createCell(8);
			cell8.setCellValue(FunUtils.checkIsNull(eProduct.getGrossWeight())+"kg");
			//货物净重
			Cell cell9 = row.createCell(9);
			cell9.setCellValue(FunUtils.checkIsNull(eProduct.getNetWeight())+"kg");
			sumNg+=FunUtils.checkIsNull(eProduct.getNetWeight())*FunUtils.checkIsNull(eProduct.getCnumber());
			//长
			Cell cell10 = row.createCell(10);
			cell10.setCellValue(FunUtils.checkIsNull(eProduct.getSizeLength()));
			//单位
			Cell cell11 = row.createCell(11);
			cell11.setCellValue("cm");
			//宽
			Cell cell12 = row.createCell(12);
			cell12.setCellValue(FunUtils.checkIsNull(eProduct.getSizeWidth()));
			//单位
			Cell cell13 = row.createCell(13);
			cell13.setCellValue("cm");
			//高
			Cell cell14 = row.createCell(14);
			cell14.setCellValue(FunUtils.checkIsNull(eProduct.getSizeHeight()));
			//单位
			Cell cell15 = row.createCell(15);
			cell15.setCellValue("cm");
			rowsNum++;
		}
		//合计行
		Row row = packingSheet.getRow(rowsNum);
		if(row==null)
		{
			row=packingSheet.createRow(rowsNum);
		}
		//创建虚线单元格样式
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM_DASH_DOT);
		row.createCell(2).setCellValue("合计：");
		row.createCell(6).setCellValue(sums);
		row.createCell(7).setCellValue("PCS/CTNS");
		row.createCell(8).setCellValue(sumWg+"kg");
		row.createCell(9).setCellValue(sumNg+"kg");
		row.createCell(10).setCellValue(sumV+"cm3");
		row.createCell(11);
		row.createCell(12);
		row.createCell(13);
		row.createCell(14);
		//设置样式
		for(int i=2;i<15;i++)
		{	Cell cell = row.getCell(i);
			if(cell==null)
			{
				row.createCell(i).setCellStyle(cellStyle);;
				
			}else {
				row.getCell(i).setCellStyle(cellStyle);
			}
			
		}
		//合并单元格
		CellRangeAddress region = new CellRangeAddress(rowsNum,rowsNum,10,14);
		packingSheet.addMergedRegion(region);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpServletResponse response = ServletActionContext.getResponse();
		// 将工作簿写入字节流
		wb.write(outputStream);
		// 下载
		new DownloadUtil().download(outputStream, response,
				package1.getId() + "装箱单.xls");
		outputStream.close();
		return NONE;
	}

	/**
	 * 修改装箱单视图
	 * 
	 * @return
	 */
	@Action(value = "packingListAction_toupdate", results = {
			@Result(name = "toPackageUpdate", location = "/WEB-INF/pages/cargo/packinglist/jPackingListUpdate.jsp") })
	public String updatePackageView() {
		Package package2 = packageService.get(model.getId());
		push(package2);
		return "toPackageUpdate";
	}

	/**
	 * 更新装箱单
	 * 
	 * @return
	 */
	@Action("packingListAction_update")
	public String updatePackage() { // 1.先查询原有的对象
		Package obj = packageService.get(model.getId());
		//获取当前登录用户,设置更新人，时间
		User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		obj.setBuyer(model.getBuyer());
		obj.setSeller(model.getSeller());
		obj.setDescription(model.getDescription());
		obj.setUpdateBy(currentUser.getId());
		obj.setUpdateTime(new Date());
		packageService.saveOrUpdate(obj);

		return "toPackageList";
	}

	/**
	 * 删除装箱单
	 * 
	 * @return
	 */
	@Action(value = "packingListAction_delete")
	public String delPackage() {
		String[] ids = model.getId().split(", ");
		packageService.delete(ids);
		return "toPackageList";
	}

	/**
	 * 提交装箱单
	 */
	@Action("packingListAction_tosubmit")
	public String submitPackage() {
		String[] ids = model.getId().split(", ");
		for (String id : ids) {
			Package packages = packageService.get(id);
			packages.setState(PackageState.SUBMMIT);
			packageService.saveOrUpdate(packages);
		}
		
		return "toPackageList";
	}

	/**
	 * 取消装箱单
	 * 
	 * @return
	 */
	@Action("packageAction_cancel")
	public String cancelPackage() {
		String[] ids = model.getId().split(", ");
		for (String id : ids) {
			Package package2 = packageService.get(id);
			package2.setState(PackageState.CANCEL);
			packageService.saveOrUpdate(package2);
		}
		return "toPackageList";
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
