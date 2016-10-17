package com.jty.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.redisson.Redisson;
import org.redisson.core.RLock;

import com.jty.commons.redis.redisson.DistributedQueue;

import com.jty.config.RedisConfig;

public class StringUtil {
//	public static Random rand = null;
//	public static final String fitst_str = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
//	public static final String str = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
//	public static String firstArr [] = null;
//	public static String strArr [] = null;
	/**
	 * @Description: string 
	 * @Author: libiq
	 * @Version: V1.00 
	 * @Create Date: 2013-9-24
	 * @Parameters: 
	 * 			option: 切割条件 
	 * 			data: 被切割数据
	 * @Return: String[] 
	 */
	public static String [] String2Array (String data,String option){
		String[] split = data.split(option);
		return split;
	}
	/**
	  * @Description:对密码进行MD5加密
	  * @Author: libiq
	  * @Version: V1.00 
	  * @Create Date: 2013-11-18上午9:21:04
	  * @Parameters:@param logonPwd
	  * @Parameters:@return
	 */
	public static String getMD5Psw(String logonPwd) {
		MD5keyBean md5 = new MD5keyBean();
		String md5Psw = md5.getkeyBeanofStr(logonPwd);
		return md5Psw;
	}
	/**
	  * @Description:汉字转成拼音首字母大写
	  * @Author: libiq
	  * @Version: V1.00 
	  * @Create Date: 2013-11-18上午9:21:12
	  * @Parameters:@param SourceStr
	  * @Parameters:@return
	 */
	public static String getPY(String SourceStr){
	    	getPY obj1 = new getPY();  
	    	String string2Alpha = obj1.String2Alpha(SourceStr);
			return string2Alpha;
	 }
	
	/**
	 * 得到文件的后缀   如 ppt ,不包含.
	 * @author sjl
	 * @Date:2013-12-30下午05:14:00
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName){
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	
	/**
	 * 得到文件的名称
	 * @author sjl
	 * @Date:2014-6-4下午03:44:17
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName){
		return fileName.substring(0,fileName.lastIndexOf("."));
	}
	
	
	/**
	  * @Description：随机生成4位验证码
	  * @author aijian
	  * @Version: V1.00 
	  * @Create Date: 2014-5-10
	  * @Parameters：
	 */
	/*
	 *0 1 2 3 4 5 6 7 8
	 *9 a b c d e f g h i
	 *j k l m n o p q r s
	 *t u v w x y z A B C
	 *D E F G H I J K L M
	 *N O P Q R S T U V W
	 *X Y Z
	 *a
	 *验证码：u8rg
	 *#
	 *
	 * */
	public static String generateCaptcha(){

		String str = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
		String str2[] = str.split(",");//将字符串以,分割
		Random rand = new Random();//创建Random类的对象rand
		int index = 0;
		String randStr = "";//创建内容为空字符串对象randStr
		randStr = "";//清空字符串对象randStr中的值
		for (int i=0; i<4; ++i)
		{
			index = rand.nextInt(str2.length-1);//在0到str2.length-1生成一个伪随机数赋值给index
			randStr += str2[index];//将对应索引的数组与randStr的变量值相连接
		}
		return randStr;
	
	}
	
	/**
	 * 根据姓名生成随机用户名
	 * @auther xf
	 * @date 2015-8-4 上午11:20:18
	 * @param maxLength 最大长度 
	 * @param minLength 最短长度 如果最短长度为零或者不比最大长度小，则使用最大长度为固定长度
	 * @return
	 */
	public static String generateRandowUserName(String name){
		String head = Pinyin.getPinYinHeadChar(name);
		
		if(head.length()>8){
			head = head.substring(0,8);
		}
		//确定用户名长度
		int length = 8-head.length();
		StringBuffer sb = new StringBuffer(head);
		Random rand  = new Random();
		//生成用户名首字母
		for (int i=0; i<length; ++i)
		{
			int index = rand.nextInt(9);//在0到9生成一个伪随机数赋值给index
			sb.append(index);//将对应索引的数组与randStr的变量值相连接
		}
		return sb.toString();
	}
	
	/**
	 * 将栏目bitCode拆分的所有上级栏目
	 * @author sjl
	 * 创建时间:2014-9-26 上午11:46:45
	 * @param bitCode
	 * @return
	 */
	private static int BITCODE_DIGIT = 3;
	public static String[] split3Char(String bitCode){
		String[] arr = new String[bitCode.length()/BITCODE_DIGIT];
		for(int i = 0;i < arr.length; i++){
			arr[i]=bitCode.substring(0,i*BITCODE_DIGIT+BITCODE_DIGIT);
		}
		return arr;
	}
	
	/**
	 * 将bitCode后三位转换为新的下一个三位字符串
	 * @author sjl
	 * 创建时间:2014-9-26 下午07:46:12
	 * @param bitCode
	 * @return
	 */
	public static String parseBitCode2NextThreeStr(String bitCode){
		String code=bitCode.substring(bitCode.length()-3, bitCode.length());
		char[] c=code.toCharArray();
		String numstr="";
		Integer num=0;
		for(int i=0;i<c.length;i++){
			if(c[i]=='0' && numstr.equals("")){
				
			}else{
				numstr+=c[i];
			}
		}
		num=Integer.parseInt(numstr)+1;
		String result=num.toString();
		result=result.length()==3?result:(result.length()==2?"0"+result:"00"+result);
		return result;
	}
	
	/**
	 * 过滤掉特殊字符
	 * @author sjl
	 * 创建时间:2015-4-21 下午4:28:12
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringFilter(String str) throws PatternSyntaxException{      
		  // 只允许字母和数字        
		  // String   regEx  =  "[^a-zA-Z0-9]";                      
		  // 清除掉所有特殊字符   
		  String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";   
		  Pattern p = Pattern.compile(regEx);      
		  Matcher m = p.matcher(str);      
		  return m.replaceAll("").trim();      
	}
    /**
      * 获取当前学年
      *
      * @author xiongx
      * @date 2015-6-3 下午1:34:43
      * @version V1.0
      *
      * @Title: getCurrentAcademicYear
      * @Description: 获取当前学年
      * @param @return    设定文件
      * @return String    返回类型
      * @throws
      */
    public static String getCurrentAcademicYear(){
    	Calendar c = Calendar.getInstance();
    	int year = c.get(Calendar.YEAR);
    	int month = c.get(Calendar.MONTH)+1;
    	if(month >= 1 && month < 9){
    		return (year-1)+"";
    	}else{
    		return year+"";
    	}
    }
    /**
     * 生成随机数
     * @param n
     * @return
     */
    public static String createRandomNum(int n){
    	String randomNums = "";
    	for(int i=0;i<n;i++){
    		Random random = new Random();
    		randomNums += random.nextInt(10);
    	}
    	return randomNums;
    }
    /**
     * 是否全部为中文
     * @param s
     * @return
     */
    public static Boolean isAllChinese(String s){
    	s = new String(s.getBytes());//用GBK编码
        String pattern="[\u4e00-\u9fa5]+";  
        Pattern p=Pattern.compile(pattern);  
        Matcher result=p.matcher(s);   
        return result.matches();
    }
    /**
     * 是否含有非法字符
     * @param s
     * @return
     */
    public static Boolean isConstainIillegalStr(String s){
    	String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    	Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(s);
        return m.find();
    }
	public static void main(String[] args) {
		System.out.println(generateRandowUserName("张三"));
		//System.out.println(createRandomNum(4));
		//System.out.println(getCurrentAcademicYear());
		//System.out.println(StringUtil.getMD5Psw("jtyhjy123!"));
	}
}
