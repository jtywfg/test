package com.jty.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.ant.taskdefs.Sync.MyCopy;


/**
 * @Desc：生成excel（03、07版）工具类</br>
 * @Filename:CreatExcelUtil.java</br>
 * @Author:zsb</br>
 * @Date:2012下午11:16:22</br>
 */
@SuppressWarnings("unchecked")
public class CreatExcelUtil {

    public static final int CELL_TYPE_STRING = 0; // 字符串类型
    public static final int CELL_TYPE_DATE = 1; // 日期类型
    public static final int CELL_TYPE_FORMULA = 2; // 自定义格式
    public static final int CELL_TYPE_BOOLEAN = 3; // 布尔类型
    public static final int CELL_TYPE_NUMERIC = 4; // 数值类型
    public static final int CELL_TYPE_BANKCODE = 5; // 银行卡号类型
    public static int model_begin_row_valid; // 模版有效数据开始行
    private static final String NUMBER_FORMAT = " #,##0.00 ";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static int[] headsType; // 表头每个单元格属性

    /**
     * 生成excel03/07版文件主方法
     * 
     * @param (List) list --生成文件信息
     * @param (String) fileUrl --模版路径
     * @param (String) modelFilePath --生成文件路径
     */
    public static void createExcel(List list, String fileUrl,
        String modelFileUrl) {

        File file = new File(modelFileUrl);
        if (file.exists()) {
            String fname = file.getName();
            if (null != fname) {
                ReadExcelUtil.readModel(modelFileUrl);
                model_begin_row_valid = ReadExcelUtil.model_begin_row_valid;
                headsType = ReadExcelUtil.headsType;
                String hzname = fname.substring(fname.lastIndexOf(".") + 1,
                    fname.length());
                // 生成2003文件
                if ("xls".equalsIgnoreCase(hzname)) {
                    createExcel03(list, fileUrl, modelFileUrl, headsType,
                        model_begin_row_valid);
                }
                // 生成2007文件
                if ("xlsx".equalsIgnoreCase(hzname)) {
                    createExcel07(list, fileUrl, modelFileUrl, headsType,
                        model_begin_row_valid);
                }
            }
        }
    }

    /**
     * 生成excel03/07版文件主方法
     * 
     * @param (List) list --生成文件信息
     * @param (String) fileName --模版名称 ,如xx.xlsx
     * @param (String) modelFilePath --生成文件路径
     * @param (HttpServletResponse) response
     */
    public static void createExcelQgh(List list, String fileName,
        String modelFileUrl, HttpServletResponse response) {

        File file = new File(modelFileUrl);
        if (file.exists()) {
            String fname = file.getName();
            if (null != fname) {
                ReadExcelUtil.readModel(modelFileUrl);
                model_begin_row_valid = ReadExcelUtil.model_begin_row_valid;
                headsType = ReadExcelUtil.headsType;
                String hzname = fname.substring(fname.lastIndexOf(".") + 1,
                    fname.length());
                // 生成2003文件
                if ("xls".equalsIgnoreCase(hzname)) {
                    createExcel03Qgh(list, fileName, modelFileUrl, headsType,
                        model_begin_row_valid, response);
                }
                // 生成2007文件
                if ("xlsx".equalsIgnoreCase(hzname)) {
                    createExcel07Qgh(list, fileName, modelFileUrl, headsType,
                        model_begin_row_valid, response);
                }
            }
        }
    }

    /**
     * 导出03版本excel
     * 
     * @param (List) list --生成文件信息
     * @param (String) fileUrl --模版路径
     * @param (String) modelFilePath --生成文件路径
     * @param (int[]) headsType --每列属性
     * @param (int) model_begin_row_valid --有效数据开始行
     */
    public static void createExcel03(List list, String fileUrl,
        String modelFilePath, int[] headsType, int model_begin_row_valid) {

        FileOutputStream fos = null;
        HSSFRow hrow = null;
        HSSFCell hcell = null;
        try {
            // 套用模版
            HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(
                modelFilePath));
            // 创建数据格式对象
            HSSFDataFormat format = hwb.createDataFormat();
            // 复制获取的模版第一个sheet
            HSSFSheet hsheet = hwb.getSheetAt(0);
            // 加载单元格属性
            CellStyle cellStyle = CreatExcelUtil.createStyleCell(hwb);
            // 设置样式
            cellStyle = CreatExcelUtil.setCellStyleAlignment(cellStyle,
                CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
            // 先创建字体样式,并把这个样式加到单元格的字体里面
            cellStyle.setFont(createFonts(hwb));
            // 存在有效数据
            if (list.size() > 0) {
                // 循环有效数据
                for (Object objs : list.toArray()) {
                    hrow = hsheet.createRow(model_begin_row_valid);
                    hrow.setHeightInPoints(15);
                    Object[] objArry = (Object[]) objs;
                    // 展开每一行的每一列
                    for (int j = 0; j < objArry.length; j++) {
                        // 设置每行宽度
                        hsheet.setColumnWidth(j, (short) 5000);
                        // 生成每列数据
                        hcell = hrow.createCell(j);
                        switch (headsType[j]) {
                            // 在查询有效数据时所有对象如果空值全部都赋值："null" 避免空指针
                            // 根据模版中的属性 生成样式
                            case CELL_TYPE_STRING:
                                hcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                hcell
                                    .setCellValue(objArry[j]!=null && !"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                hcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_DATE:
                                hcell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                hcell
                                    .setCellValue(objArry[j]!=null && !"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                cellStyle.setDataFormat(HSSFDataFormat
                                    .getBuiltinFormat(DATE_FORMAT));// 设置cell样式为定制的"yyyy-MM-dd"格式
                                hcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_FORMULA:
                                hcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                hcell
                                    .setCellValue(objArry[j]!=null && !"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                hcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_BANKCODE:
                                hcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                hcell
                                    .setCellValue(objArry[j]!=null && !"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                hcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_NUMERIC:
                                if(objArry[j]!=null && !"".equals(objArry[j].toString()) &&!"null".equals(objArry[j].toString()) ){
                                    hcell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                    hcell.setCellValue(!"null".equals(objArry[j].toString()) ? new BigDecimal(objArry[j].toString()).doubleValue() : null);
                                    format = hwb.createDataFormat();
                                    if(objArry[j].toString().indexOf(".")!=-1){
	                                    cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
	                                    hcell.setCellStyle(cellStyle);
                                    }else{
                                    	cellStyle.setDataFormat(format.getBuiltinFormat("0")); // 设置cell样式为定制的整型格式
	                                    hcell.setCellStyle(cellStyle);
                                    }
                                    }else{
                                        hcell.setCellType(XSSFCell.CELL_TYPE_BLANK);
                                        hcell.setCellValue("");
                                        hcell.setCellStyle(cellStyle);
                                    }
                                break;
                            case CELL_TYPE_BOOLEAN:
                                hcell.setCellType(XSSFCell.CELL_TYPE_BOOLEAN);
                                hcell
                                    .setCellValue(objArry[j]!=null && !"null".equals(objArry[j]
                                        .toString()) ? Boolean
                                        .parseBoolean(objArry[j].toString())
                                        : null);
                                hcell.setCellStyle(cellStyle);
                                break;
                        }
                    }
                    // 冲掉模版最后一行类型数据
                    ++model_begin_row_valid;
                }
                list.clear();
            }else{
            	hrow = hsheet.getRow(model_begin_row_valid);
            	hsheet.removeRow(hrow);
            }
            fos = new FileOutputStream(new File(fileUrl));
            hwb.write(fos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出07版本excel
     * 
     * @param (List) list --生成文件信息
     * @param (String) fileUrl --模版路径
     * @param (String) modelFilePath --生成文件路径
     * @param (int[]) headsType --每列属性
     * @param (int) model_begin_row_valid --有效数据开始行
     */
    public static void createExcel07(List list, String fileUrl,
        String modelFilePath, int[] headsType, int model_begin_row_valid) {

        FileOutputStream fos = null;
        XSSFRow xrow = null;
        XSSFCell xcell = null;

        try {

            // 套用模版
            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(
                modelFilePath));
            // 复制获取的模版第一个sheet
            XSSFSheet xsheet = xwb.getSheetAt(0);
            // 创建数据格式对象
            CellStyle cellStyle = CreatExcelUtil.createStyleCell(xwb);
            // 加载单元格属性
            XSSFDataFormat format = xwb.createDataFormat();
            // 设置样式
            cellStyle = CreatExcelUtil.setCellStyleAlignment(cellStyle,
                CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
            // 先创建字体样式,并把这个样式加到单元格的字体里面
            cellStyle.setFont(createFonts(xwb));
            // 存在有效数据
            if (list.size() > 0) {
                // 循环有效数据
                for (Object objs : list.toArray()) {
                    xrow = xsheet.createRow(model_begin_row_valid);
                    xrow.setHeightInPoints(15);
                    Object[] objArry = (Object[]) objs;
                    // 展开每一行的每一列
                    for (int j = 0; j < objArry.length; j++) {
                        // 生成每列数据
                        xcell = xrow.createCell(j);
                        // 设置宽度
                        xsheet.setColumnWidth(j, (short) 5000);
                        switch (headsType[j]) {
                            // 所有对象都赋值："null" 避免空指针
                            case CELL_TYPE_STRING:
                                // 把这个样式加到单元格里面
                                xcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                if(null!=objArry[j]){
                                	 xcell.setCellValue(objArry[j]!=null && !"null".equals(objArry[j].toString()) ? objArry[j].toString(): null);
                                }else{
                                	 xcell.setCellValue("null");
                                }
                                xcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_DATE:
                                xcell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                xcell
                                    .setCellValue(objArry[j]!=null && !"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                cellStyle.setDataFormat(HSSFDataFormat
                                    .getBuiltinFormat(DATE_FORMAT));// 设置cell样式为定制的"yyyy-MM-dd"格式
                                xcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_FORMULA:
                                xcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                xcell
                                    .setCellValue(objArry[j]!=null && !"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                xcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_BANKCODE:
                                xcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                xcell
                                    .setCellValue(objArry[j]!=null && !"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                xcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_NUMERIC:
                                if(objArry[j]!=null &&!"".equals(objArry[j].toString())&&!"null".equals(objArry[j].toString())){
                                    xcell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                    xcell.setCellValue(objArry[j]!=null && !"null".equals(objArry[j].toString()) ? new BigDecimal(objArry[j].toString()).doubleValue() : null);
                                    format = xwb.createDataFormat();
                                    if(objArry[j].toString().indexOf(".")!=-1){
	                                    cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
	                                    xcell.setCellStyle(cellStyle);
                                    }else{
                                    	cellStyle.setDataFormat(format.getFormat("0")); // 设置cell样式为定制的整型格式
                                    	xcell.setCellStyle(cellStyle);
                                    }
                                  }else{
                                      xcell.setCellType(XSSFCell.CELL_TYPE_BLANK);
                                      xcell.setCellValue("");
                                      xcell.setCellStyle(cellStyle);
                                  }
                                break;
                            case CELL_TYPE_BOOLEAN:
                                xcell.setCellType(XSSFCell.CELL_TYPE_BOOLEAN);
                                xcell
                                    .setCellValue(objArry[j]!=null && !"null".equals(objArry[j]
                                        .toString()) ? Boolean
                                        .parseBoolean(objArry[j].toString())
                                        : null);
                                xcell.setCellStyle(cellStyle);
                                break;
                        }
                    }
                    // 冲掉模版最后一行类型数据
                    ++model_begin_row_valid;
                }
            }else{
            	 xrow = xsheet.getRow(model_begin_row_valid);
            	 xsheet.removeRow(xrow);
            }
            fos = new FileOutputStream(new File(fileUrl));
            xwb.write(fos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置边框
     * 
     * @param (XSSFWorkbook) xwb
     * @return (CellStyle) cellStyle
     */
    public static CellStyle createStyleCell(XSSFWorkbook xwb) {
        CellStyle cellStyle = xwb.createCellStyle();
        // 设置一个单元格边框颜色
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        // 设置一个单元格边框颜色
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return cellStyle;
    }

    /**
     * 设置边框
     * 
     * @param (HSSFWorkbook) hwb
     * @return (CellStyle) cellStyle
     */
    public static CellStyle createStyleCell(HSSFWorkbook hwb) {
        CellStyle cellStyle = hwb.createCellStyle();
        // 设置一个单元格边框颜色
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        // 设置一个单元格边框颜色
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return cellStyle;
    }

    /**
     * 设置文字在单元格里面的位置 CellStyle.ALIGN_CENTER CellStyle.VERTICAL_CENTER
     * 
     * @param (CellStyle) cellStyle
     * @param (short) halign
     * @param (short) valign
     * @return (CellStyle) cellStyle
     */
    public static CellStyle setCellStyleAlignment(CellStyle cellStyle,
        short halign, short valign) {
        // 设置上下
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 设置左右
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    /**
     * 设置字体
     * 
     * @param (HSSFWorkbookcellStyle) hwb
     * @return (Font) font
     */
    public static Font createFonts(HSSFWorkbook hwb) {
        // 创建Font对象
        Font font = hwb.createFont();
        // 设置字体
        ((org.apache.poi.ss.usermodel.Font) font).setFontName("宋体");
        // 字体大小
        font.setFontHeight((short) 200);
        return font;
    }

    /**
     * 设置字体
     * 
     * @param (XSSFWorkbook) xwb
     * @return(Font) font
     */
    public static Font createFonts(XSSFWorkbook xwb) {
        // 创建Font对象
        Font font = xwb.createFont();
        // 设置字体
        ((org.apache.poi.ss.usermodel.Font) font).setFontName("宋体");
        // 字体大小
        font.setFontHeight((short) 200);
        return font;
    }
    
    /**
     * 导出03版本excel
     * 
     * @param (List) list --生成文件信息
     * @param (String) fileName --文件下载名称
     * @param (String) modelFilePath --模板的文件路径
     * @param (int[]) headsType --每列属性
     * @param (int) model_begin_row_valid --有效数据开始行
     * @param (HttpServletResponse) response --
     */
    public static void createExcel03Qgh(List list, String fileName,
        String modelFilePath, int[] headsType, int model_begin_row_valid,
        HttpServletResponse response) {
    	createExcel03Qgh(list, fileName, modelFilePath, headsType, model_begin_row_valid, null, response);
    }

    /**
     * 导出03版本excel
     * 
     * @param (List) list --生成文件信息
     * @param (String) fileName --文件下载名称
     * @param (String) modelFilePath --模板的文件路径
     * @param (int[]) headsType --每列属性
     * @param (int) model_begin_row_valid --有效数据开始行
     * @param (List<MyHcell>) myHcellList 表格中格外添加的数据
     * @param (HttpServletResponse) response --
     */
    public static void createExcel03Qgh(List list, String fileName,
        String modelFilePath, int[] headsType, int model_begin_row_valid,
        List<MyHcell> myHcellList,
        HttpServletResponse response) {

        response.setContentType("application/vnd.ms-excel");
        @SuppressWarnings("unused")
        FileOutputStream fos = null;
        HSSFRow hrow = null;
        HSSFCell hcell = null;
        ServletOutputStream out = null;
        try {
            response.setHeader("Content-disposition", "attachment; filename="
                + new String((fileName).getBytes("GBK"), "ISO8859_1"));
            out = response.getOutputStream();
            
            // 套用模版
            HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(
                modelFilePath));
            // 创建数据格式对象
            HSSFDataFormat format = hwb.createDataFormat();
            // 复制获取的模版第一个sheet
            HSSFSheet hsheet = hwb.getSheetAt(0);
            // 加载单元格属性
            CellStyle cellStyle = CreatExcelUtil.createStyleCell(hwb);
            // 设置样式
            cellStyle = CreatExcelUtil.setCellStyleAlignment(cellStyle,
                CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
            // 先创建字体样式,并把这个样式加到单元格的字体里面
            cellStyle.setFont(createFonts(hwb));
            
            
            //是否有单独的单元格创建
            if(myHcellList != null && myHcellList.size() > 0){
            	for(MyHcell myHcell : myHcellList){
            		hrow = hsheet.getRow(myHcell.getRowIndex());
            		if(hrow == null){
            			hrow = hsheet.createRow(myHcell.getRowIndex());
            		}
            		hcell = hrow.createCell(myHcell.getCollIndex());
        			hcell.setCellType(myHcell.getCellType());
                    hcell.setCellValue(myHcell.getCellValue());
                    hcell.setCellStyle(cellStyle);
            	}
            }
            
            // 存在有效数据
            if (list.size() > 0) {
                // 循环有效数据
                for (Object objs : list.toArray()) {
                    hrow = hsheet.createRow(model_begin_row_valid);
                    hrow.setHeightInPoints(15);
                    Object[] objArry = (Object[]) objs;
                    // 展开每一行的每一列
                    for (int j = 0; j < objArry.length; j++) {
                        // 设置每行宽度
                        hsheet.setColumnWidth(j, (short) 5000);
                        // 生成每列数据
                        hcell = hrow.createCell(j);
                        switch (headsType[j]) {
                            // 在查询有效数据时所有对象如果空值全部都赋值："null" 避免空指针
                            // 根据模版中的属性 生成样式
                            case CELL_TYPE_STRING:
                                hcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                hcell
                                    .setCellValue(objArry[j] != null && !"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                hcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_DATE:
                                hcell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                hcell
                                    .setCellValue(objArry[j] != null && !"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                cellStyle.setDataFormat(HSSFDataFormat
                                    .getBuiltinFormat(DATE_FORMAT));// 设置cell样式为定制的"yyyy-MM-dd"格式
                                hcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_FORMULA:
                                hcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                hcell
                                    .setCellValue(objArry[j] != null && !"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                hcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_BANKCODE:
                                hcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                hcell
                                    .setCellValue(objArry[j] != null && !"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                hcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_NUMERIC:
                                if( objArry[j]!=null && !"".equals(objArry[j].toString().trim()) &&!"null".equals(objArry[j].toString()) ){
                                    hcell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                    hcell.setCellValue(objArry[j]!=null && !"null".equals(objArry[j].toString()) ? new BigDecimal(objArry[j].toString()).doubleValue() : null);
                                    format = hwb.createDataFormat();
                                    if(objArry[j].toString().indexOf(".")!=-1){
	                                    cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
	                                    hcell.setCellStyle(cellStyle);
                                    }else{
                                    	cellStyle.setDataFormat(format.getBuiltinFormat("0")); // 设置cell样式为定制的整型格式
	                                    hcell.setCellStyle(cellStyle);
                                    }
                                    }else{
                                        hcell.setCellType(XSSFCell.CELL_TYPE_BLANK);
                                        hcell.setCellValue("");
                                        hcell.setCellStyle(cellStyle);
                                    }
                                break;
                            case CELL_TYPE_BOOLEAN:
                                hcell.setCellType(XSSFCell.CELL_TYPE_BOOLEAN);
                                hcell
                                    .setCellValue(objArry[j] != null && !"null".equals(objArry[j]
                                        .toString()) ? Boolean
                                        .parseBoolean(objArry[j].toString())
                                        : null);
                                hcell.setCellStyle(cellStyle);
                                break;
                        }
                    }
                    // 冲掉模版最后一行类型数据
                    ++model_begin_row_valid;
                }
                list.clear();
            }
            hwb.write(out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
            	if(out != null){
            		out.flush();
            		out.close();
            	}
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 导出07版本excel
     * 
     * @param (List) list --生成文件信息
     * @param (String) fileName --模版名称
     * @param (String) modelFilePath --生成文件路径
     * @param (int[]) headsType --每列属性
     * @param (int) model_begin_row_valid --有效数据开始行
     * @param (HttpServletResponse) response --
     */
    public static void createExcel07Qgh(List list, String fileName,
        String modelFilePath, int[] headsType, int model_begin_row_valid,
        HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        @SuppressWarnings("unused")
        FileOutputStream fos = null;
        XSSFRow xrow = null;
        XSSFCell xcell = null;

        try {
            response.setHeader("Content-disposition", "attachment; filename="
                + new String((fileName).getBytes("GBK"), "ISO8859_1"));

            // 套用模版
            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(
                modelFilePath));
            // 复制获取的模版第一个sheet
            XSSFSheet xsheet = xwb.getSheetAt(0);
            // 创建数据格式对象
            CellStyle cellStyle = CreatExcelUtil.createStyleCell(xwb);
            // 加载单元格属性
            XSSFDataFormat format = xwb.createDataFormat();
            // 设置样式
            cellStyle = CreatExcelUtil.setCellStyleAlignment(cellStyle,
                CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
            // 先创建字体样式,并把这个样式加到单元格的字体里面
            cellStyle.setFont(createFonts(xwb));
            // 存在有效数据
            if (list.size() > 0) {
                // 循环有效数据
                for (Object objs : list.toArray()) {
                    xrow = xsheet.createRow(model_begin_row_valid);
                    xrow.setHeightInPoints(15);
                    Object[] objArry = (Object[]) objs;
                    // 展开每一行的每一列
                    for (int j = 0; j < objArry.length; j++) {
                        // 生成每列数据
                        xcell = xrow.createCell(j);
                        // 设置宽度
                        xsheet.setColumnWidth(j, (short) 5000);
                        switch (headsType[j]) {
                            // 所有对象都赋值："null" 避免空指针
                            case CELL_TYPE_STRING:
                                // 把这个样式加到单元格里面
                                xcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                if(null!=objArry[j]){
                                	xcell.setCellValue(!"null".equals(objArry[j].toString()) ? objArry[j].toString(): null);
                                }else{
                                	xcell.setCellValue("null");
                                }
                                xcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_DATE:
                                xcell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                xcell
                                    .setCellValue(!"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                cellStyle.setDataFormat(HSSFDataFormat
                                    .getBuiltinFormat(DATE_FORMAT));// 设置cell样式为定制的"yyyy-MM-dd"格式
                                xcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_FORMULA:
                                xcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                xcell
                                    .setCellValue(!"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                xcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_BANKCODE:
                                xcell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                xcell
                                    .setCellValue(!"null".equals(objArry[j]
                                        .toString()) ? objArry[j].toString()
                                        : null);
                                xcell.setCellStyle(cellStyle);
                                break;
                            case CELL_TYPE_NUMERIC:
                                if(objArry[j]!=null &&!"".equals(objArry[j].toString().trim()) &&!"null".equals(objArry[j].toString())){
                                    xcell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                    xcell.setCellValue(!"null".equals(objArry[j].toString()) ? new BigDecimal(objArry[j].toString()).doubleValue() : null);
                                    format = xwb.createDataFormat();
//                                    cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
//                                    xcell.setCellStyle(cellStyle);
                                    if(objArry[j].toString().indexOf(".")!=-1){
	                                    cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
	                                    xcell.setCellStyle(cellStyle);
                                    }else{
                                    	cellStyle.setDataFormat(format.getFormat("0")); // 设置cell样式为定制的整型格式
                                    	xcell.setCellStyle(cellStyle);
                                    }
                                  }else{
                                      xcell.setCellType(XSSFCell.CELL_TYPE_BLANK);
                                      xcell.setCellValue("");
                                      xcell.setCellStyle(cellStyle);
                                  }
                                break;
                            case CELL_TYPE_BOOLEAN:
                                xcell.setCellType(XSSFCell.CELL_TYPE_BOOLEAN);
                                xcell
                                    .setCellValue(!"null".equals(objArry[j]
                                        .toString()) ? Boolean
                                        .parseBoolean(objArry[j].toString())
                                        : null);
                                xcell.setCellStyle(cellStyle);
                                break;
                        }
                    }
                    // 冲掉模版最后一行类型数据
                    ++model_begin_row_valid;
                }
            }
            xwb.write(response.getOutputStream());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().flush();
                response.getOutputStream().close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    public class MyHcell{
    	int cellType;
    	String cellValue;
    	int rowIndex;
    	int collIndex;
    	public MyHcell(){}
    			
    	
    	public MyHcell(int rowIndex,int collIndex,int cellType,String cellValue){
    		this.collIndex = collIndex;
    		this.rowIndex = rowIndex;
    		this.cellType = cellType;
    		this.cellValue = cellValue;
    	}


		public int getRowIndex() {
			return rowIndex;
		}

		public void setRowIndex(int rowIndex) {
			this.rowIndex = rowIndex;
		}

		public int getCollIndex() {
			return collIndex;
		}

		public void setCollIndex(int collIndex) {
			this.collIndex = collIndex;
		}


		public int getCellType() {
			return cellType;
		}


		public void setCellType(int cellType) {
			this.cellType = cellType;
		}


		public String getCellValue() {
			return cellValue;
		}


		public void setCellValue(String cellValue) {
			this.cellValue = cellValue;
		}
    }
}
