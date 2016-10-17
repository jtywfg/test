package com.jty.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CharUtil {
	// 汉子 字母 和数字 并且数字不能再第一个
	public static boolean isnNameByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[a-zA-Z\u4E00-\u9FA5]{1}[a-zA-Z0-9\u4E00-\u9FA5]*$");
		return pattern.matcher(str.trim()).find();

	}
	
	// 汉字 字母 
	public static boolean isTeacherNameByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[a-zA-Z\u4E00-\u9FA5]{1}[a-zA-Z\u4E00-\u9FA5]*$");
		return pattern.matcher(str.trim()).find();

	}
	
	// 根据Unicode编码完美的判断中文汉字和符号
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	/**
	 * 完整的判断中文汉字和符号
	 * 
	 * @author xf
	 * @Version: V1.00
	 * @Create 2014-12-9 上午10:02:34
	 * @param strName
	 * @return
	 */
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 只能判断部分CJK字符（CJK统一汉字）
	 * @author xf
	 * @Version: V1.00
	 * @Create 2014-12-9 上午10:03:44
	 * @param str
	 * @return
	 */
	public static boolean isChineseByName(String str) {
		if (str == null) {
			return false;
		}
		// 大小写不同：\\p 表示包含，\\P 表示不包含
		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
		String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(str.trim()).find();
	}
	
	
	/**
	 * 判断特殊字符
	 * @auther xf
	 * @date 2015-3-25 上午11:11:02
	 * @param str
	 * @return
	 */
	public   static   boolean checkUserName(String   str)  {   
		  String regEx="^[a-zA-Z]{1}[a-zA-Z0-9_]{5,19}$";
		  Pattern   p   =   Pattern.compile(regEx);   
		  Matcher   m   =   p.matcher(str);   
		  return   m.find();   
	 }
}
