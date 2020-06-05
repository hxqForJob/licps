package com.hxq.web.action.cargo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.jpa.criteria.expression.function.AggregationFunction.COUNT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.alibaba.fastjson.JSON;
import com.hxq.domain.Contract;
import com.hxq.domain.Dept;
import com.hxq.domain.Export;
import com.hxq.domain.ExportProduct;
import com.hxq.domain.ExtEproduct;
import com.hxq.domain.User;
import com.hxq.server.export.domain.ExportProductResult;
import com.hxq.server.export.domain.ExportProductVo;
import com.hxq.server.export.domain.ExportResult;
import com.hxq.server.export.domain.ExportVo;
import com.hxq.service.ContractService;
import com.hxq.service.ExportProductService;
import com.hxq.service.ExportService;
import com.hxq.service.UserService;
import com.hxq.utils.ContractState;
import com.hxq.utils.DownloadUtil;
import com.hxq.utils.FunUtils;
import com.hxq.utils.Page;
import com.hxq.utils.SysConstant;
import com.hxq.utils.UtilFuns;
import com.hxq.utils.file.StringUtil;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.export.webservice.IEpService;

/**
 * 报运控制器
 * @author 强仔
 *
 */
@Namespace("/cargo")
@Result(name="toExportListView",type="redirectAction",location="exportAction_list")
public class ExportAction extends BaseAction implements ModelDriven<Export> {

	private Export model=new Export();
	
	@Autowired
	private ExportService exportService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private ExportProductService exportProductService;
	
	@Autowired
	private IEpService epService;
	
	@Autowired
	private UserService userService;
	
	private  Page page=new Page();
	
	
	
	public Page getPage() {
		return page;
	}



	public void setPage(Page page) {
		this.page = page;
	}



	@Override
	public Export getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	/**
	 * 查询合同管理视图,细粒度控制
	 * @return
	 * @throws Exception
	 */
	@Action(value="exportAction_contractList",results={@Result(name="toConlist",location="/WEB-INF/pages/cargo/export/jContractList.jsp")})
	public String toContarctList() throws Exception {
		
				// 获取当前用户,用来细粒度控制
				final User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
				// 获取当前用户的级别
				final Integer degree = currentUser.getUserinfo().getDegree();

				Specification<Contract> spec = new Specification<Contract>() {

					@Override
					public Predicate toPredicate(Root<Contract> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						// TODO Auto-generated method stub
						Predicate predicate;
						switch (degree) {
						case 4: //普通員工
							predicate=cb.and(cb.equal(root.get("createBy").as(String.class),currentUser.getId()),cb.equal(root.get("state").as(Integer.class), ContractState.SUBMMIT));
							break;
						case 3://本部门经理
							predicate=cb.and(cb.equal(root.get("createDept").as(String.class),currentUser.getDept().getId()),cb.equal(root.get("state").as(Integer.class), ContractState.SUBMMIT));
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
							predicate=cb.and(cb.equal(root.get("state").as(Integer.class), ContractState.SUBMMIT),in);
							break;
						case 1://副总裁
							predicate=cb.equal(root.get("state").as(Integer.class), ContractState.SUBMMIT);
							break;

						default://总裁
							predicate=cb.equal(root.get("state").as(Integer.class), ContractState.SUBMMIT);
							break;
						}
						return predicate;
					}
				};
		
//		Specification<Contract> spec = new Specification<Contract>() {
//
//			@Override
//			public Predicate toPredicate(Root<Contract> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				// TODO Auto-generated method stub
//				return cb.equal(root.get("state").as(Integer.class), 1);
//			}
//		};
		org.springframework.data.domain.Page<Contract> findPage = contractService.
				findPage(spec, 
						new PageRequest(page.getPageNo()-1, page.getPageSize()));
		page.setResults(findPage.getContent());
		page.setTotalRecord(findPage.getTotalElements());
		page.setUrl("exportAction_contractList");
		push(page);
		return "toConlist";
	}
	/**
	 * 报运视图
	 * @return
	 */
	@Action(value="exportAction_tocreate",results={@Result(name="toCreate",location="/WEB-INF/pages/cargo/export/jExportCreate.jsp")})
	public String toCreate()
	{
		return "toCreate";
	}
	
	/**
	 * 保存报运单
	 * @return
	 */
	@Action(value="exportAction_insert")
	public String toInsert()
	{
		//获取当前登录用户
		User currentUser = (User) session.get(SysConstant.CURRENT_USER_INFO);
		model.setCreateTime(new Date());
		model.setCreateBy(currentUser.getId());
		model.setCreateDept(currentUser.getDept().getId());
		exportService.saveOrUpdate(model);
		return "toExportListView";
	}
	
	/**
	 * 查询出口报运视图
	 * @return
	 */
	@Action(value="exportAction_list",results={@Result(name="toElist",location="/WEB-INF/pages/cargo/export/jExportList.jsp")})
	public String toList()
	{
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
					predicate=cb.and(cb.equal(root.get("createBy").as(String.class),currentUser.getId()));
					break;
				case 3://本部门经理
					predicate=cb.and(cb.equal(root.get("createDept").as(String.class),currentUser.getDept().getId()));
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
					predicate=in;
					break;
				case 1://副总裁
					predicate=null;
					break;

				default://总裁
					predicate=null ;
					break;
				}
				return predicate;
			}
		};
		
		
		org.springframework.data.domain.Page<Export> findPage = exportService.
				findPage(spec, 
						new PageRequest(page.getPageNo()-1, 
								page.getPageSize()));
		page.setResults(findPage.getContent());
		page.setTotalRecord(findPage.getTotalElements());
		page.setUrl("exportAction_list");
		push(page);
		return "toElist";
	}
	
	
	/**
	 * 查看报运单
	 * @return
	 */
	@Action(value="exportAction_toview",results={@Result(name="toWatchExport",location="/WEB-INF/pages/cargo/export/jExportView.jsp")})
	public String toWathcView()
	{
		Export export = exportService.get(model.getId());
		push(export);
		return "toWatchExport";
		
	}
	
	/**
	 * 修改视图
	 * @return
	 */
	@Action(value="exportAction_toupdate",results={@Result(name="toUpdateView",location="/WEB-INF/pages/cargo/export/jExportUpdate.jsp")})
	public String toUpdateView()
	{
		Export export = exportService.get(model.getId());
		push(export);
		return "toUpdateView";
	}
	
	/**
	 * 删除报运单
	 * @return
	 */
	@Action(value="exportAction_delete")
	public String delete()
	{
		String [] ids=model.getId().split(", ");
		exportService.delete(ids);
		return "toExportListView";
	}
	
	/**
	 * 提交
	 * @return
	 */
	@Action(value="exportAction_submit")
	public String submit()
	{
		String [] ids=model.getId().split(", ");
		for (String id : ids) {
			Export export = exportService.get(id);
			export.setState(1);
			exportService.saveOrUpdate(export);
		}
		return "toExportListView";
	}
	
	/**
	 * 取消
	 * @return
	 */
	@Action(value="exportAction_cancel")
	public String cancel()
	{
		String [] ids=model.getId().split(", ");
		for (String id : ids) {
			Export export = exportService.get(id);
			export.setState(0);
			exportService.saveOrUpdate(export);
		}
		return "toExportListView";
	}
	
	/**
	 * 获取报运单下的货物
	 * @return
	 * @throws IOException 
	 */
	@Action("exportAction_getExProduct")
	public String getExProduct() throws IOException
	{
		List<ExportProduct> exProducts = exportProductService.find(new Specification<ExportProduct>() {

			@Override
			public Predicate toPredicate(Root<ExportProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return cb.equal(root.get("export").get("id"), model.getId());
			}
		});
		//addTRRecord(objId, id, productNo, cnumber, grossWeight, netWeight, sizeLength, sizeWidth, sizeHeight, exPrice, tax)
		List<Map> maps= new ArrayList<>();
		for (ExportProduct ep : exProducts) {
			HashMap<String, Object> hashMap=new HashMap<>();
			hashMap.put("id",UtilFuns.convertNull(ep.getId()));
			hashMap.put("productNo", UtilFuns.convertNull(ep.getProductNo()));
			hashMap.put("cnumber", UtilFuns.convertNull(ep.getCnumber()));
			hashMap.put("grossWeight", UtilFuns.convertNull(ep.getGrossWeight()));
			hashMap.put("netWeight", UtilFuns.convertNull(ep.getNetWeight()));
			hashMap.put("sizeLength", UtilFuns.convertNull(ep.getSizeLength()));
			hashMap.put("sizeWidth", UtilFuns.convertNull(ep.getSizeWidth()));
			hashMap.put("sizeHeight", UtilFuns.convertNull(ep.getSizeHeight()));
			hashMap.put("exPrice", UtilFuns.convertNull(ep.getExPrice()));
			hashMap.put("tax", UtilFuns.convertNull(ep.getTax()));
			maps.add(hashMap);
		}
		String jsonString = JSON.toJSONString(maps);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().write(jsonString);
		return NONE;
	}
	
	/**
	 * 打印报运单
	 * @return
	 * @throws IOException 
	 */
	@Action("exportAction_printExport")
	public String printExProduct() throws IOException
	{
		List<ExportProduct> exProducts = exportProductService.find(new Specification<ExportProduct>() {

			@Override
			public Predicate toPredicate(Root<ExportProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return cb.equal(root.get("export").get("id"), model.getId());
			}
		});
		
		Export export = exportService.get(model.getId());
		printExProduct(exProducts,export);
		
		return NONE;
	}
	
	/**
	 * 打印报运单逻辑
	 * @param exportPros  报运单下的货物
	 * @param export 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void printExProduct(List<ExportProduct> exportPros, Export export) throws FileNotFoundException, IOException
	{
				// 行号
				int rowsNum = 1;
				//获取文件路径
				String filePath=ServletActionContext.getServletContext().getRealPath("/make/xlsprint/tEXPORT.xls");
				filePath=filePath.replace("/", File.separator);
				// 创建工作簿
				Workbook wb = new HSSFWorkbook(new FileInputStream(filePath));
				// 获取sheet
				Sheet outProSheet = wb.getSheetAt(0);
				
				//获取第二行
				Row secondRow=outProSheet.getRow(rowsNum++);
				//制单日期
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy 年 MM 月 dd 日");
				//System.out.println(export.getInputDate());
				//secondRow.getCell(8).setCellValue();
				secondRow.getCell(9).setCellValue(dateFormat.format(export.getInputDate()));
				
				//获取第三行
				Row thirdRow=outProSheet.getRow(rowsNum++);
				//设置合同确认书号
				thirdRow.getCell(3).setCellValue(export.getCustomerContract());
				//设置信用证号
				Cell cell = thirdRow.getCell(11);
				String lcno = export.getLcno();
				cell.setCellValue(lcno);
				
				//获取第四行
				Row fourthRow=outProSheet.getRow(rowsNum++);
				//设置收货人和收货地址
				fourthRow.getCell(3).setCellValue(export.getConsignee());
				//设置唛头
				fourthRow.getCell(16).setCellValue(export.getMarks());
				//设置备注
				fourthRow.getCell(18).setCellValue(export.getRemark());
				
				
				//获取第五行
				Row fifthRow=outProSheet.getRow(rowsNum++);
				//设置装运港
				fifthRow.createCell(2).setCellValue(export.getShipmentPort());
				//设置目的港
				fifthRow.createCell(4).setCellValue(export.getDestinationPort());
				//设置运输方式
				fifthRow.createCell(7).setCellValue(export.getTransportMode());
				//设置条件
				fifthRow.createCell(10).setCellValue(export.getPriceCondition());
				
				//获取第六行
				Row sixthRow=outProSheet.getRow(rowsNum++);
				String value = sixthRow.getCell(1).getStringCellValue();
				//System.out.println(value);
				rowsNum++;//自增到第七行
				//获取第20个单元格的样式
				CellStyle cellStyle20 = sixthRow.getCell(20).getCellStyle();
				
				//获取第4个单元格的样式
				CellStyle cellStyle4 = sixthRow.getCell(4).getCellStyle();
				//获取第19行单元格的样式
				Row row19 = outProSheet.getRow(18);
				CellStyle cellStyle19 = row19.getCell(1).getCellStyle();
				//获取第19行第20单元格的样式
				CellStyle cell20Style = row19.getCell(20).getCellStyle();
				
				
				
				double sumCount=0;//合计总数量
				double sumNetWeight=0;//总净重
//				double sumExPrice=0;//总出口单价
//				double sumPrice=0;//总不含税收购单价
//				double sumTaxPrice=0;//总含税收购单价
				double sumCost=0;//总成本
				double sumTax=0;//总税金
				
				for (ExportProduct ep : exportPros) {
					
					//删除原来第19行的合并单元格
					if (rowsNum==19) {
						CellReference refK19 = new CellReference("K19");
						CellReference refM19 =new CellReference("M19");
						//System.out.println(refK19.getRow());
						//System.out.println(refM19.getCol());
						for (int i = outProSheet.getNumMergedRegions()-1; i >= 0; i--) {
							 org.apache.poi.ss.util.CellRangeAddress region = outProSheet.getMergedRegion(i);
							 //System.out.println(region+"-row:"+region.getFirstRow()+",col:"+region.getLastColumn());
							//判断到K19才进行拆分单元格
						   	if(region.getFirstRow()==refK19.getRow()&&region.getLastColumn()==refM19.getCol()){
						   		outProSheet.removeMergedRegion(i);
						    }
						}
					}
					Row row = outProSheet.createRow(rowsNum++);
					
					//设置单元格1样式
					Cell cell1 = row.createCell(1);
					cell1.setCellStyle(cellStyle4);
					cell1.setCellValue(ep.getProductNo()==null?"":ep.getProductNo());//货号
					
					double count=ep.getCnumber()==null?0:ep.getCnumber();
					sumCount+=count;
					double num=ep.getBoxNum()==null?0:ep.getBoxNum();
					
					double grossWeight=ep.getGrossWeight()==null?0:ep.getGrossWeight();
					
					double netWeight=ep.getNetWeight()==null?0:ep.getNetWeight();
					
					sumNetWeight+=netWeight*count;
					double length=ep.getSizeLength()==null?0:ep.getSizeLength();
					
					double width=ep.getSizeWidth()==null?0:ep.getSizeWidth();
					
					double height=ep.getSizeHeight()==null?0:ep.getSizeHeight();
					row.createCell(4).setCellValue(ep.getFactory().getFactoryName()==null?"":ep.getFactory().getFactoryName());//厂家
					row.createCell(5).setCellValue(ep.getPackingUnit()==null?"":ep.getPackingUnit());//单位
					row.createCell(6).setCellValue(count);//数量
					row.createCell(7).setCellValue(num);//件数
					row.createCell(8).setCellValue(grossWeight);//毛重
					row.createCell(9).setCellValue(netWeight);//净重
					row.createCell(10).setCellValue(length);//长
					row.createCell(11).setCellValue(width);//宽
					row.createCell(12).setCellValue(height);//高
					//货物单价
					double epPrice=FunUtils.checkIsNull(ep.getPrice());
					//计算一个货物的成本包含附件
					Double cost=epPrice;
					for (ExtEproduct extEP : ep.getExtEproducts()) {
							//附件数量
							int extCount=FunUtils.checkIsNull(extEP.getCnumber());
							//附件单价
							double extEPrice=FunUtils.checkIsNull(extEP.getPrice());
							//一个货物的成本=货物单价+（附件*附件数量/货物数量）+税金
							cost+=(extEPrice*extCount/count);
					}
					//获取税金
					Double tax=FunUtils.checkIsNull(ep.getTax());
					cost+=tax;//加上税金
					//计算总成本
					sumCost+=cost*count;
					//计算总税金
					sumTax+=tax*count;
					if(ep.getExPrice()==null)
					{
						row.createCell(16).setCellValue("");//出口单价
					}else {
						row.createCell(16).setCellValue(ep.getExPrice());//出口单价
					}
					row.createCell(17).setCellValue(epPrice);//不含税
					row.createCell(18).setCellValue(epPrice+tax);//含税
					row.createCell(19).setCellValue(cost);//金额
					Cell cell20 = row.createCell(20);//税金
					
					cell20.setCellStyle(cellStyle20);
					if(tax==0)
					{
						row.getCell(18).setCellValue("");
						cell20.setCellValue("");
					}else {
						cell20.setCellValue(tax);
					}
					
					
					//设置单元格1-19样式
					for (int i = 1; i <=19; i++) {
						Cell getCell = row.getCell(i);
						if(getCell==null)
						{
							row.createCell(i).setCellStyle(cellStyle4);
						}else {
							getCell.setCellStyle(cellStyle4);
						}
						
					}
					//合并单元格
					CellRangeAddress region = new CellRangeAddress(rowsNum-1,rowsNum-1,1,3);
					outProSheet.addMergedRegion(region);
				}
				//合计行
				Row countRow = outProSheet.createRow(rowsNum++);
				countRow.createCell(1).setCellValue("合计");
				countRow.createCell(6).setCellValue(sumCount);//总数量
				countRow.createCell(7).setCellValue(export.getBoxNums());//总件数
				countRow.createCell(8).setCellValue(export.getGrossWeights());//总毛重
				countRow.createCell(9).setCellValue(sumNetWeight);//总净重
				countRow.createCell(10).setCellValue(export.getMeasurements()+"m3");//总体积
				countRow.createCell(16).setCellValue("---");//总出口单价
				countRow.createCell(17).setCellValue("---");//总不含税
				countRow.createCell(18).setCellValue("---");//总含税
				countRow.createCell(19).setCellValue(sumCost);//总收购成本
				countRow.createCell(20).setCellValue(sumTax);//总税金
				//设置最后一个单元格样式
				countRow.getCell(20).setCellStyle(cell20Style);
				
				//设置单元格1-19样式
				for (int i = 1; i <=19; i++) {
					Cell getCell = countRow.getCell(i);
					if(getCell==null)
					{
						countRow.createCell(i).setCellStyle(cellStyle19);
					}else {
						getCell.setCellStyle(cellStyle19);
					}
					
				}
				//合并单元格
				CellRangeAddress region = new CellRangeAddress(rowsNum-1,rowsNum-1,1,3);
				outProSheet.addMergedRegion(region);
				
				CellRangeAddress region2 = new CellRangeAddress(rowsNum-1,rowsNum-1,10,12);
				outProSheet.addMergedRegion(region2);
				
				//创建最后一行
				Row lastRow = outProSheet.createRow(rowsNum++);
				lastRow.createCell(17).setCellValue("制单人:");
				lastRow.createCell(18).setCellValue(userService.get(export.getCreateBy()).getUserinfo().getName());
				
				
				//删除模板多余行
				for (int i = rowsNum; i <=outProSheet.getLastRowNum(); i++) {
					Row row = outProSheet.getRow(i);
					outProSheet.removeRow(row);
				}
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				HttpServletResponse response = ServletActionContext.getResponse();
				
					
//				System.out.println(rowsNum);
//				System.out.println(outProSheet.getFirstRowNum());
//				System.out.println(outProSheet.getLastRowNum());
				//if(rowsNum<)
				// 将工作簿写入字节流
				wb.write(outputStream);
				// 下载
				new DownloadUtil().download(outputStream, response,
						export.getId() + "报运单.xls");
				outputStream.close();
	}
	
	/**
	 * 修改逻辑
	 * @return
	 * @throws Exception
	 */
	@Action(value="exportAction_update")
	public String update() throws Exception {
		// TODO Auto-generated method stub
		
		// 出口报运单的修改
		Export export = exportService.get(model.getId());
		export.setInputDate(model.getInputDate());
		export.setLcno(model.getLcno());
		export.setConsignee(model.getConsignee());
		export.setShipmentPort(model.getShipmentPort());
		export.setDestinationPort(model.getDestinationPort());
		export.setTransportMode(model.getTransportMode());
		export.setPriceCondition(model.getPriceCondition());
		export.setMarks(model.getMarks());
		export.setRemark(model.getRemark());
		
		// 出口报运修改的商品
		for (int i = 0; i < mr_id.length ; i++) {
			if(mr_changed[i].equals("1")){  // 修改过的进行操作 1代表修改
				ExportProduct exportProduct = exportProductService.get(mr_id[i]);
				exportProduct.setCnumber(FunUtils.checkIsNull(mr_cnumber[i]));
				exportProduct.setGrossWeight(FunUtils.checkIsNull(mr_grossWeight[i]));
				exportProduct.setNetWeight(FunUtils.checkIsNull(mr_netWeight[i]));
				exportProduct.setSizeLength(FunUtils.checkIsNull(mr_sizeLength[i]));
				exportProduct.setSizeWidth(FunUtils.checkIsNull(mr_sizeWidth[i]));
				exportProduct.setSizeHeight(FunUtils.checkIsNull(mr_sizeHeight[i]));
				exportProduct.setExPrice(FunUtils.checkIsNull(mr_exPrice[i]));
				exportProduct.setTax(FunUtils.checkIsNull(mr_tax[i]));
				exportProductService.saveOrUpdate(exportProduct);
			}
		}
		exportService.saveOrUpdate(export);
		return "toExportListView";
	}
	/**
	 * 电子报运  Restful风格方法
	 * @return
	 * @throws Exception
	 */
	@Action(value="exportAction_exportE")	
	public String exportE_Rs() throws Exception {
		// TODO Auto-generated method stub
		String[] ids=model.getId().split(", ");
		for (String id : ids) {
				// 出口报运单对象
				Export export = exportService.get(id);
				WebClient client = WebClient.create("http://localhost:8080/jk_export/ws/export/user");
				
				ExportVo exportVo = new ExportVo();
				// export拷贝到exportVo
				BeanUtils.copyProperties(export, exportVo);
				// 拷贝后不相同的属性需要手动赋值
				exportVo.setExportId(export.getId());
				
				// 封装报运单下的货物对象
				Set<ExportProduct> exportProducts = export.getExportProducts();
				HashSet<ExportProductVo> products = new HashSet<ExportProductVo>();
				for (ExportProduct ep : exportProducts) {
					ExportProductVo epVo = new ExportProductVo();
					BeanUtils.copyProperties(ep, epVo);
					epVo.setExportProductId(ep.getId());
					epVo.setExportId(export.getId());  //设置报运单货物对应的报运单号
					
					products.add(epVo);
				}
				
				exportVo.setProducts(products);
				client.post(exportVo);  
				
				WebClient returnClient = WebClient.create("http://localhost:8080/jk_export/ws/export/user/" + exportVo.getId());
				ExportResult exportResult = returnClient.get(ExportResult.class);
				
				//根据返回的id查询数据库对象
				Export exportDB = exportService.get(exportResult.getExportId());
				exportDB.setState(exportResult.getState());
				exportDB.setRemark(exportResult.getRemark());
				exportService.saveOrUpdate(exportDB);
				
				Set<ExportProductResult> products2 = exportResult.getProducts();
				for (ExportProductResult epResult : products2) {
					ExportProduct ep = exportProductService.get(epResult.getExportProductId());
					ep.setTax(epResult.getTax());
					exportProductService.saveOrUpdate(ep);
				}
		}
			
		
		return "toExportListView";
	}
	
	
	/**
	 * 电子报运  ws 方法
	 * @return
	 * @throws Exception
	 */
	/**
	   * {
			 exportId:"",
			 state:"",
			 remark:"",
			 products:[
			            {
			               exportProductId:"",
			               tax:""
			            },
				   {
			               exportProductId:"",
			               tax:""
			            }
			          ]
			}
	   * 
	   */
	@Action(value="exportAction_exportEWS")	
	public String exportE_WS() throws Exception {
		String[] ids=model.getId().split(", ");
		for (String id : ids) {
			Export export = exportService.get(id);
			HashMap exportMap=new HashMap<>();
			exportMap.put("exportId", export.getId());
			exportMap.put("state", export.getState());
			exportMap.put("remark",export.getRemark());
			exportMap.put("boxNums",export.getBoxNums());
			exportMap.put("grossWeights", export.getGrossWeights());
			List<HashMap> products=new ArrayList<>();
			for (ExportProduct product : export.getExportProducts()) {
				HashMap productMap=new HashMap<>();
				productMap.put("exportProductId", product.getId());
				productMap.put("cnumber", product.getCnumber());
				productMap.put("factoryId", product.getFactory().getId());
				products.add(productMap);
			}
			exportMap.put("products", products);
			String jsonString = JSON.toJSONString(exportMap);
			//System.out.println("//////////"+jsonString);
			String exportE = epService.exportE(jsonString);
			//System.out.println("////"+exportE);
			HashMap parseObject = JSON.parseObject(exportE, HashMap.class);
			export.setState(Integer.parseInt(parseObject.get("state").toString()));
			export.setRemark(parseObject.get("remark").toString());
			exportService.saveOrUpdate(export);
			//货物
			 List<HashMap> parseArray = JSON.parseArray((parseObject.get("products").toString()),HashMap.class);
			 for (HashMap proMap : parseArray) {
				ExportProduct exportProduct = exportProductService.get(proMap.get("exportProductId").toString());
				exportProduct.setTax(Double.valueOf(proMap.get("tax").toString()));
				exportProductService.saveOrUpdate(exportProduct);
			}
		}
		
		return "toExportListView";
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
	
	private String[] mr_id;//货物id   
	private String[] mr_changed;//是否修改
	private Integer[] mr_cnumber;//数量
	private Double[] mr_grossWeight;//毛重
	private Double[] mr_netWeight;//净重
	private Double[] mr_sizeLength;//长度
	private Double[] mr_sizeWidth;//宽度
	private Double[] mr_sizeHeight;//高度
	private Double[] mr_exPrice;//单价
	private Double[] mr_tax;//税金

	public String[] getMr_id() {
		return mr_id;
	}

	public void setMr_id(String[] mr_id) {
		this.mr_id = mr_id;
	}

	public String[] getMr_changed() {
		return mr_changed;
	}

	public void setMr_changed(String[] mr_changed) {
		this.mr_changed = mr_changed;
	}

	public Integer[] getMr_cnumber() {
		return mr_cnumber;
	}

	public void setMr_cnumber(Integer[] mr_cnumber) {
		this.mr_cnumber = mr_cnumber;
	}

	public Double[] getMr_grossWeight() {
		return mr_grossWeight;
	}

	public void setMr_grossWeight(Double[] mr_grossWeight) {
		this.mr_grossWeight = mr_grossWeight;
	}

	public Double[] getMr_netWeight() {
		return mr_netWeight;
	}

	public void setMr_netWeight(Double[] mr_netWeight) {
		this.mr_netWeight = mr_netWeight;
	}

	public Double[] getMr_sizeLength() {
		return mr_sizeLength;
	}

	public void setMr_sizeLength(Double[] mr_sizeLength) {
		this.mr_sizeLength = mr_sizeLength;
	}

	public Double[] getMr_sizeWidth() {
		return mr_sizeWidth;
	}

	public void setMr_sizeWidth(Double[] mr_sizeWidth) {
		this.mr_sizeWidth = mr_sizeWidth;
	}

	public Double[] getMr_sizeHeight() {
		return mr_sizeHeight;
	}

	public void setMr_sizeHeight(Double[] mr_sizeHeight) {
		this.mr_sizeHeight = mr_sizeHeight;
	}

	public Double[] getMr_exPrice() {
		return mr_exPrice;
	}

	public void setMr_exPrice(Double[] mr_exPrice) {
		this.mr_exPrice = mr_exPrice;
	}

	public Double[] getMr_tax() {
		return mr_tax;
	}

	public void setMr_tax(Double[] mr_tax) {
		this.mr_tax = mr_tax;
	}
}
