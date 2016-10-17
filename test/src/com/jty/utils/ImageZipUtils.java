package com.jty.utils;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
@SuppressWarnings("restriction")
public class ImageZipUtils {
	private static float quality=0.75f;
	
	private static int smallwidth=250;
	/** 
     * 等比例压缩图片文件<br> 先保存原文件，再压缩、上传 
     * @param InputStream  要进行压缩的文件 
     * @param width  宽度 //设置宽度时（高度传入0，等比例缩放） 
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放） 
     * @param quality 质量 
     * @return 返回压缩后的文件流
     */
	public static InputStream zipImageFromStream(InputStream in,Integer width,Integer height,float quality){
		InputStream inputStream = null;
		try {
//			Image srcImg = ImageIO.read(in);  
			Image src=Toolkit.getDefaultToolkit().createImage(ByteUtil.getBytes(in));//可讀取丟失ICC信息的圖片
            BufferedImage srcImg=toBufferedImage(src);//Image to BufferedImage
            
			int w = srcImg.getWidth(null);  
			int h = srcImg.getHeight(null);  
			double bili;  
			if(width==0 && height==0){
				width=w;
				height=h;
			}else if(width>0){  
			    bili=width/(double)w;  
			    height = (int) (h*bili);  
			}else{  
			    if(height>0){  
			        bili=height/(double)h;  
			        width = (int) (w*bili);  
			    }  
			}  
			/** 宽,高设定 */  
			BufferedImage tag = new BufferedImage(width, height,  
			        BufferedImage.TYPE_INT_RGB);  
			tag.getGraphics().drawImage(srcImg, 0, 0, width, height, null);  

			ByteArrayOutputStream bout=new ByteArrayOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bout);  
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);  
			/** 压缩质量 */  
			jep.setQuality(quality, true);  
			encoder.encode(tag, jep);  
			
			ByteArrayInputStream bis=new ByteArrayInputStream(bout.toByteArray());
			inputStream = bis;
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return inputStream;
    }
	
	/**
	 * 将image 转换为 bufferedimage
	 * @author sjl
	 * 创建时间:2014-12-15 下午4:36:12
	 * @param image
	 * @return
	 */
	public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        //boolean hasAlpha = hasAlpha(image);
        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
           /* if (hasAlpha) {

                transparency = Transparency.BITMASK;

            }*/
            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(

            image.getWidth(null), image.getHeight(null), transparency);

        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
            /*if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }*/
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;

    }
	
	/** 
     * 等比例压缩图片文件<br> 先保存原文件，再压缩、上传 
     * @param srcFile  要进行压缩的文件 
     * @param width  宽度 //设置宽度时（高度传入0，等比例缩放） 
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放） 
     * @param quality 质量 
     * @return 返回压缩后的文件流
     */
	public static InputStream zipImageFromFile(File srcFile,Integer width,Integer height,float quality){
		InputStream inputStream = null;
		try {
			InputStream in =new FileInputStream(srcFile);
			inputStream=zipImageFromStream(in, width, height, quality);
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return inputStream;
    }
	
	/***
	 * 压缩,不改变大小
	 * @author sjl
	 * @Date:2014-3-1下午03:06:27
	 * @param srcFile
	 * @param quality
	 * @return
	 */
	public static InputStream zipImageFromFile(File srcFile){
		InputStream inputStream = null;
		try {
			InputStream in =new FileInputStream(srcFile);
			inputStream=zipImageFromStream(in, 0, 0, quality);
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return inputStream;
	}
	
	/***
	 * 压缩,不改变大小
	 * @author sjl
	 * @Date:2014-3-1下午03:06:27
	 * @param in
	 * @param quality
	 * @return
	 */
	public static InputStream zipImageFromStream(InputStream in){
		InputStream inputStream = null;
		try {
			inputStream=zipImageFromStream(in, 0, 0, quality);
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} 
	    return inputStream;
	}
	
	/***
	 * 压缩,小图
	 * @author sjl
	 * @Date:2014-3-1下午03:06:27
	 * @param srcFile
	 * @param quality
	 * @return
	 */
	public static InputStream zipSmallImageFromFile(File srcFile){
		InputStream inputStream = null;
		try {
			InputStream in =new FileInputStream(srcFile);
			inputStream=zipImageFromStream(in, smallwidth, 0, quality);
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return inputStream;
	}
	
	/***
	 * 压缩,小图
	 * @author sjl
	 * @Date:2014-3-1下午03:06:27
	 * @param in
	 * @param quality
	 * @return
	 */
	public static InputStream zipSmallImageFromStream(InputStream in){
		InputStream inputStream = null;
		try {
			inputStream=zipImageFromStream(in, smallwidth, 0, quality);
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} 
	    return inputStream;
	}
	/**
	 * 判断是否是大图 超过50kb算大图,需要压缩
	 * @author sjl
	 * @Date:2014-3-1下午05:07:08
	 * @return
	 */
	public static boolean isMaxImage(long size){
		boolean result=false;
		if(size>51200){
			result=true;
		}
		return result;
	}
	
	/*****************************************测试方法************************************/
	public static void main(String[] args)
	{
			ImageZipUtils s = new ImageZipUtils();
			//s.zipImageFile(new File("E:\\太阳活动及其表现.png"),new File("e:\\250_1.png"), 250, 0,  0.75f);
//			InputStream inputStream=s.zipImageFromFile(new File("E:\\太阳活动及其表现.png"), 300, 0,  0.75f);
			
			try {
				FileInputStream fileInputStream=new FileInputStream(new File("D:\\资源网\\金太阳专区图片\\金太阳专区图片\\高三单元经销示范卷\\高三单元经销示范卷\\L版\\化学1.jpg"));
				
				InputStream inputStream=s.zipImageFromStream(fileInputStream, 0, 0,  0.75f);
				
				int size = 0;
				byte[] tmp = new byte[1024];
				int len = -1;// 处理流
				DataOutputStream dos = new DataOutputStream(new FileOutputStream("e:\\1.jpg"));
				while ((len = inputStream.read(tmp,0,1024)) != -1) {
				        dos.write(tmp, 0, len);
				        size += len;
				}
				dos.flush();
				dos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
}
