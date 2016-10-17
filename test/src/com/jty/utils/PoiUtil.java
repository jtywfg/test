package com.jty.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class PoiUtil {

	public static void setBorder(){
		
	}
	
	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {

		if (cell == null)
			return "";

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

			return cell.getStringCellValue().trim();

		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

			return String.valueOf(cell.getBooleanCellValue());

		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

			return cell.getCellFormula();

		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue().trim();

		}

		return "";
	}
	
	/**
	  * @Description：校验行中的内容是否为空
	  * @author aijian
	  * @Version: V1.00 
	  * @Create Date: 2014-2-24
	  * @Parameters：colIndex 总共列数 从第0列开始算
	 */
	public static boolean checkNullRow(Row row,int colIndex){
		for(int index = 0 ; index <= colIndex ; index ++){
			if(row == null){
				return true;
			}
			if(row.getCell(index) != null && 
					!"".equals(PoiUtil.getCellValue(row.getCell(index)))){
				/**有一列的值不为空，则该行不为空*/
				return false;
			}
		}
		return true;
	}

	/**
	 * 使用java正则表达式去掉多余的.与0
	 * 
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}

}
