package com.hxq.web.action.cargo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STRef;
import org.springframework.beans.factory.annotation.Autowired;

import com.hxq.domain.ContractProduct;
import com.hxq.service.ContractProductService;
import com.hxq.utils.DownloadUtil;
import com.hxq.utils.ExcelStyleUtils;
import com.hxq.utils.UtilFuns;
import com.hxq.web.action.BaseAction;

//出货表控制器
@Namespace("/cargo")
public class OutProductAction extends BaseAction {

	// 日期
	private String inputDate;

	public String getInputDate() {
		return inputDate;
	}

	@Autowired
	private ContractProductService contractProductService;

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 出货表视图
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "outProductAction_toedit", results = {
			@Result(name = "toeidt", location = "/WEB-INF/pages/cargo/outproduct/jOutProduct.jsp") })
	public String toEdit() {
		// TODO Auto-generated method stub
		return "toeidt";
	}
	
	/**
	 * 打印出货表(导入模板方式)
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Action("outProductAction_print")
	public String print() throws IOException, ParseException {
		// 行号
		int rowsNum = 0;
		//获取文件路径
		String filePath=ServletActionContext.getServletContext().getRealPath("/make/xlsprint/tOUTPRODUCT.xls");
		filePath=filePath.replace("/", File.separator);
		// 创建工作簿
		Workbook wb = new HSSFWorkbook(new FileInputStream(filePath));
		// 获取sheet
		Sheet outProSheet = wb.getSheetAt(0);
		
		/**
		 * 获取大标题行
		 */
		Row bigTitleRow = outProSheet.getRow(rowsNum++);
		// 获取大标题单元格
		Cell bigTitleCell = bigTitleRow.getCell(1);
		//设置大标题单元格内容
		bigTitleCell.setCellValue(inputDate.replace("-0", "-").replace("-", "年") + "月出货报表");
		
		
		//获取模板内容行
		Row contentRow=outProSheet.getRow(++rowsNum);
		
		// 获取内容单元格样式
		CellStyle cellStyle1 = contentRow.getCell(1).getCellStyle();
		CellStyle cellStyle2 = contentRow.getCell(2).getCellStyle();
		CellStyle cellStyle3 = contentRow.getCell(3).getCellStyle();
		CellStyle cellStyle4 = contentRow.getCell(4).getCellStyle();
		CellStyle cellStyle5 = contentRow.getCell(5).getCellStyle();
		CellStyle cellStyle6 = contentRow.getCell(6).getCellStyle();
		CellStyle cellStyle7 = contentRow.getCell(7).getCellStyle();
		CellStyle cellStyle8 = contentRow.getCell(8).getCellStyle();
		outProSheet.removeRow(contentRow);
		/**
		 * 获取内容
		 * 
		 */
		List<ContractProduct> contractProducts = contractProductService.findByShipTime(inputDate);
		Cell cell=null;
		for (int i = 0; i < contractProducts.size(); i++) {
			int cellNum = 1;
			ContractProduct cProduct = contractProducts.get(i);
			Row row = outProSheet.createRow(rowsNum++);
			row.setHeightInPoints(24f);

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(cellStyle1);
			// 设置内容 客户名称
			cell.setCellValue(cProduct.getContract().getCustomName());

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(cellStyle2);
			// 设置内容 订单号
			cell.setCellValue(cProduct.getContract().getContractNo());

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(cellStyle3);
			// 设置内容 货号
			cell.setCellValue(cProduct.getProductNo());

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(cellStyle4);
			// 设置内容 数量
			cell.setCellValue(cProduct.getCnumber());

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(cellStyle5);
			// 设置内容 工厂
			cell.setCellValue(cProduct.getFactoryName());

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(cellStyle6);
			// 设置内容 工厂交期
			cell.setCellValue(UtilFuns.dateTimeFormat(cProduct.getContract().getDeliveryPeriod()));

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(cellStyle7);
			// 设置内容 船期
			cell.setCellValue(UtilFuns.dateTimeFormat(cProduct.getContract().getShipTime()));

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(cellStyle8);
			// 设置内容 ","贸易条款"};
			cell.setCellValue(cProduct.getContract().getTradeTerms());
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpServletResponse response = ServletActionContext.getResponse();
		// 将工作簿写入字节流
		wb.write(outputStream);
		// 下载
		new DownloadUtil().download(outputStream, response,
				inputDate.replace("-0", "-").replace("-", "年") + "月出货报表.xls");
		return NONE;
	}


	/**
	 * 打印出货表(手写样式)
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Action("outProductAction_oldprint")
	public String oldprint() throws IOException, ParseException {
		// 行号
		int rowsNum = 0;
		// 创建工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建sheet
		Sheet outProSheet = wb.createSheet();
		// 设置列宽
		outProSheet.setColumnWidth(1, 256 * 10);
		outProSheet.setColumnWidth(2, 256 * 30);
		outProSheet.setColumnWidth(3, 256 * 30);
		outProSheet.setColumnWidth(4, 256 * 10);
		outProSheet.setColumnWidth(5, 256 * 10);
		outProSheet.setColumnWidth(6, 256 * 30);
		outProSheet.setColumnWidth(7, 256 * 30);
		outProSheet.setColumnWidth(8, 256 * 30);
		// 合并单元格
		outProSheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 8));
		/**
		 * 创建大标题行
		 */
		Row bigTitleRow = outProSheet.createRow(rowsNum++);
		// 设置行高
		bigTitleRow.setHeightInPoints(26);
		// 创建大标题单元格
		Cell bigTitleCell = bigTitleRow.createCell(1);
		// 获取大标题单元格样式
		CellStyle bigTitleStyle = ExcelStyleUtils.bigTitle(wb);
		// 设置大标题单元格样式
		bigTitleCell.setCellStyle(bigTitleStyle);
		bigTitleCell.setCellValue(inputDate.replace("-0", "-").replace("-", "年") + "月出货报表");

		/**
		 * 创建小标题行
		 */
		Row littleTitleRow = outProSheet.createRow(rowsNum++);
		// 设置行高
		littleTitleRow.setHeightInPoints(15);
		// 定义小标题数组
		String[] titles = { "客户", "订单号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款" };
		// 获取小单元格样式
		CellStyle lilttleTitleStyle = ExcelStyleUtils.title(wb);
		for (int i = 0; i < titles.length; i++) {
			// 创建小标题单元格
			Cell title = littleTitleRow.createCell(i + 1);
			title.setCellStyle(lilttleTitleStyle);
			title.setCellValue(titles[i]);

		}
		// 获取内容单元格样式
		CellStyle titleStyle = ExcelStyleUtils.title(wb);
		/**
		 * 获取内容
		 * 
		 */
		List<ContractProduct> contractProducts = contractProductService.findByShipTime(inputDate);
		Cell cell;
		for (int i = 0; i < contractProducts.size(); i++) {
			int cellNum = 1;
			ContractProduct cProduct = contractProducts.get(i);
			Row row = outProSheet.createRow(rowsNum++);
			row.setHeightInPoints(24f);

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(titleStyle);
			// 设置内容 客户名称
			cell.setCellValue(cProduct.getContract().getCustomName());

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(titleStyle);
			// 设置内容 订单号
			cell.setCellValue(cProduct.getContract().getContractNo());

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(titleStyle);
			// 设置内容 货号
			cell.setCellValue(cProduct.getProductNo());

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(titleStyle);
			// 设置内容 数量
			cell.setCellValue(cProduct.getCnumber());

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(titleStyle);
			// 设置内容 工厂
			cell.setCellValue(cProduct.getFactoryName());

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(titleStyle);
			// 设置内容 工厂交期
			cell.setCellValue(UtilFuns.dateTimeFormat(cProduct.getContract().getDeliveryPeriod()));

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(titleStyle);
			// 设置内容 船期
			cell.setCellValue(UtilFuns.dateTimeFormat(cProduct.getContract().getShipTime()));

			// 创建单元格
			cell = row.createCell(cellNum++);
			// 设置样式
			cell.setCellStyle(titleStyle);
			// 设置内容 ","贸易条款"};
			cell.setCellValue(cProduct.getContract().getTradeTerms());
		}
		System.out.println(contractProducts.size());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpServletResponse response = ServletActionContext.getResponse();
		// 将工作簿写入字节流
		wb.write(outputStream);
		// 下载
		new DownloadUtil().download(outputStream, response,
				inputDate.replace("-0", "-").replace("-", "年") + "月出货报表.xls");
		return NONE;
	}

}
