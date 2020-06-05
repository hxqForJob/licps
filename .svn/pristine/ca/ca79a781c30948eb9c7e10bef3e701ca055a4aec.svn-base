package com.hxq.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.annotation.SynthesizedAnnotation;

public class ExportExcelTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//创建一个工作簿
		Workbook workbook=new  HSSFWorkbook();
		//创建字体
		Font createFont = workbook.createFont();
		createFont.setBold(true);
		createFont.setFontHeightInPoints((short) 16);
		createFont.setFontName("微软雅黑");
		//创建单元格的样式
		CellStyle createCellStyle = workbook.createCellStyle();
		//设置字体
		createCellStyle.setFont(createFont);
		createCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);			//横向具右对齐
		createCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		//创建一个sheet
		Sheet createSheet = workbook.createSheet();
		//设置列宽
		createSheet.setColumnWidth(0, 256*10);
		createSheet.setColumnWidth(1, 256*20);
		createSheet.setColumnWidth(2, 256*20);
		createSheet.setColumnWidth(3, 256*10);
		createSheet.setColumnWidth(4, 256*20);
		createSheet.setColumnWidth(5, 256*10);
		createSheet.setColumnWidth(6, 256*10);
		createSheet.setColumnWidth(7, 256*10);
		//合并单元格
		createSheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
		//创建行
		Row createRow = createSheet.createRow(0);
		//设置行高
		createRow.setHeightInPoints(26F);
		//创建单元格
		Cell createCell = createRow.createCell(1);
		//设置单元格内容
		createCell.setCellValue("2019-8-12出货报表");
		//设置单元格样式
		createCell.setCellStyle(createCellStyle);
		
		//输出excel
		FileOutputStream fileOutputStream = new FileOutputStream("e://excelDemo.xls");
		workbook.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
		System.out.println("打印完毕");
	}
}
