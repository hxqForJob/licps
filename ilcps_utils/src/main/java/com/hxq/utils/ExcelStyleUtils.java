package com.hxq.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelStyleUtils {
	//大标题的样式
		public static CellStyle bigTitle(Workbook wb){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short)16);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);					//字体加粗
			
			style.setFont(font);
			
			style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
			
			return style;
		}
		//小标题的样式
		public static CellStyle title(Workbook wb){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontName("黑体");
			font.setFontHeightInPoints((short)12);
			
			style.setFont(font);
			
			style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
			
			style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
			style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
			style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
			style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
			
			return style;
		}
		
		//文字样式
		public static CellStyle text(Workbook wb){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short)10);
			
			style.setFont(font);
			
			style.setAlignment(CellStyle.ALIGN_LEFT);					//横向居左
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
			
			style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
			style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
			style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
			style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
			
			return style;
		}
}
