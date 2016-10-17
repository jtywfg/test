package com.jty.utils;


import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWorkSheet{
	
	/**
	 * 判断文件类型，返回可读excel的对象
	 * @param is
	 * @param fileName
	 * @return
	 * @throws IOException
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2015-7-2 上午10:03:56
	 */
	public Workbook createWorkBook(InputStream is,String fileName) throws IOException{
		if(fileName.toLowerCase().endsWith("xls")){
			return new HSSFWorkbook(is);
		}
		if(fileName.toLowerCase().endsWith("xlsx")){
			return new XSSFWorkbook(is);
		}
		return null;
	}
	
}