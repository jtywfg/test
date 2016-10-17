package com.jty.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextCharacterProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextFont;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;

/**
 * @author sjl
 * @desc: ppt生成缩略图的类
 * @create date:2014-5-12下午08:08:45
 */
public class PPTImageUtil {
	private static Integer imgWidth=700;
	private static Integer imgHeight=500;
	/**
	 * 生成ppt2007缩略图
	 * @author sjl
	 * @Date:2014-5-12下午08:34:23
	 * @param inputStream
	 * @return
	 */
	private static InputStream create2007PPTImage(InputStream inputStream){
		InputStream in=null;
		try {
			
		    XMLSlideShow xmlSlideShow=new XMLSlideShow(inputStream);
		    XSLFSlide [] slides=xmlSlideShow.getSlides();
		    XSLFSlide slide=slides[0];
		    
		    //设置字体为宋体，解决中文乱码问题
		    CTSlide rawSlide=slide.getXmlObject();
		    CTGroupShape gs = rawSlide.getCSld().getSpTree();
            CTShape[] shapes = gs.getSpArray();
            for (CTShape shape : shapes) {
                CTTextBody tb = shape.getTxBody();
                if (null == tb)
                    continue;
                CTTextParagraph[] paras = tb.getPArray();
                CTTextFont font=CTTextFont.Factory.parse(
                		"<xml-fragment xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">"+
                		"<a:rPr lang=\"zh-CN\" altLang=\"en-US\" dirty=\"0\" smtClean=\"0\"> "+
						    "<a:latin typeface=\"+mj-ea\"/> "+
						  "</a:rPr>"+
						"</xml-fragment>");
                for (CTTextParagraph textParagraph : paras) {
                    CTRegularTextRun[] textRuns = textParagraph.getRArray();
                    for (CTRegularTextRun textRun : textRuns) {
                    	CTTextCharacterProperties properties=textRun.getRPr();
                    	properties.setLatin(font);
                    }
                }
            }
            
		    BufferedImage img=new BufferedImage(imgWidth,imgHeight, BufferedImage.TYPE_INT_RGB);
		    Graphics2D graphics=img.createGraphics();
//		    graphics.setPaint(Color.WHITE);
		    graphics.fill(new Rectangle2D.Float(0, 0, imgWidth, imgHeight));
		    slide.draw(graphics);
		    
		    ByteArrayOutputStream out=new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(img, "jpeg", out);
	        ByteArrayInputStream swapStream = new ByteArrayInputStream(out.toByteArray()); 
            
    		in=swapStream;
            
            out.close(); 
		} catch (Exception e) {
//			e.printStackTrace();
		} 
		return in;
	}
	
	/**
	 * 生成ppt2003缩略图
	 * @author sjl
	 * @Date:2014-5-12下午08:33:58
	 * @param inputStream
	 * @return
	 */
	private static InputStream create2003PPTImage(InputStream inputStream){
		InputStream in=null;
		try {
		    SlideShow slideShow=new SlideShow(inputStream);
		    
		    Slide[] slides=slideShow.getSlides();
		    Slide slide=slides[0];
		    
		    TextRun[] textRuns=slide.getTextRuns();
		    for(TextRun tr:textRuns){
		       RichTextRun[] rts=tr.getRichTextRuns();
		       for(RichTextRun rt:rts){
		    	   rt.setFontName("宋体");
		       }
		    }
		   
		    BufferedImage img=new BufferedImage(imgWidth,imgHeight, BufferedImage.TYPE_INT_RGB);
		    Graphics2D graphics=img.createGraphics();
		    graphics.setPaint(Color.WHITE);
		    graphics.fill(new Rectangle2D.Float(0, 0, imgWidth, imgHeight));
		    slide.draw(graphics);
		    
		    ByteArrayOutputStream out=new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(img, "jpg", out);
	        ByteArrayInputStream swapStream = new ByteArrayInputStream(out.toByteArray()); 
            
    		in=swapStream;
    		
            out.close();   
		    
            System.out.println("缩略图成功!");
		} catch (Exception e) {
//			e.printStackTrace();
		} 
		return in;
	}
	
	/**
	 * 生成ppt缩略图
	 * @author sjl
	 * @Date:2014-5-12下午08:39:51
	 * @param bytes
	 * @return
	 */
	public static InputStream createPPTImageByBytes(byte[] bytes){
		ByteArrayInputStream bis=new ByteArrayInputStream(bytes);
		return createPPTImage(bis);
	}
	
	/**
	 * 生成ppt的缩略图
	 * @author sjl
	 * @Date:2014-5-12下午08:33:43
	 * @param in
	 * @return
	 */
	public static InputStream createPPTImage(InputStream in){
		InputStream inputStream = null;
		try {
			if(!in.markSupported()){
				in=new BufferedInputStream(in);
			}
			if(in.markSupported()){
				in=new PushbackInputStream(in,8);
			}
			if(POIFSFileSystem.hasPOIFSHeader(in)){//2003
				inputStream=create2003PPTImage(in);
			}else if(POIXMLDocument.hasOOXMLHeader(in)){//2007
				inputStream=create2007PPTImage(in);
			}
		} catch (IOException e) {
//			e.printStackTrace();
		}
		return inputStream;
	}
	public static void main(String[] args) {
		try {
			InputStream inputStream=new FileInputStream(new File("C:\\Users\\xxz2\\Desktop\\《专制时代晚期的政治形态》导学案.ppt"));
			byte[] b = ByteUtil.getBytes(inputStream);
			InputStream in =createPPTImageByBytes(b);
			FileOutputStream out=new FileOutputStream("d:\\1.jpg");
			out.write(ByteUtil.getBytes(in));
			out.close();
			System.out.println("成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
