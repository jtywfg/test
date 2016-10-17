package com.jty.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 字节转换的工具
 * @author sjl
 * @desc:
 * @create date:2014-1-8下午02:37:29
 */
public class ByteUtil {
	/**
	 * 字节转换为数字
	 * @author sjl
	 * @Date:2014-1-8下午02:37:58
	 * @param b
	 * @return
	 */
	 public static int byte2int(byte[] b) {
	        int value = 0;
	        for (int i = 0; i < 4; i++) {
	            int shift = (4 - 1 - i) * 8;
	            value += (b[i] & 0x000000FF) << shift;
	        }
	        return value;
	}
	
	/**
	 * 数字转换为字节
	 * @author sjl
	 * @Date:2014-1-8下午02:40:01
	 * @param i
	 * @return
	 */
	public static byte[] int2byte(int i) {
	        return new byte[]{
	                (byte) ((i >> 24) & 0xFF),
	                (byte) ((i >> 16) & 0xFF),
	                (byte) ((i >> 8) & 0xFF),
	                (byte) (i & 0xFF)
	        };
	}
	
	/***
	 * 从流中读取一个数字
	 * @author sjl
	 * @Date:2014-1-8下午02:41:32
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static int readInteger(InputStream is) throws IOException {
        byte[] bytes = new byte[4];
        is.read(bytes);
        return byte2int(bytes);
    }
	
	public static final int BUFFER_SIZE = 1024;
    /**
     * 将流转成字节
     * @author sjl
     * @Date:2013-12-16下午05:10:33
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] getBytes(InputStream is) throws IOException {

       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       byte[] b = new byte[BUFFER_SIZE];
       int len = 0;

       while ((len = is.read(b, 0, BUFFER_SIZE)) != -1) {
        baos.write(b, 0, len);
       }

       baos.flush();
       byte[] bytes = baos.toByteArray();
       return bytes;
    }
    
    /**
     * 将byte[]转为inputStream
     * @param b
     * @return
     */
    public static InputStream getIsFromBytes(byte [] b){
    	ByteArrayInputStream is=new ByteArrayInputStream(b);
    	return is;
    }
}
