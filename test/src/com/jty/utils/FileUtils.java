package com.jty.utils;

import java.io.File;

public class FileUtils {
	/**
	 * 删除文件夹下的所有的文件和文件夹
	 * @auther xf
	 * @date 2015-1-12 下午4:10:19
	 * @param dir
	 */
	public static void deleteDir(File dir){
		if(!dir.exists()){
			return ;
		}
		File subFiles [] = dir.listFiles();
		if(subFiles.length>0){
			for (File file : subFiles) {
				if(file.isDirectory()){
					deleteDir(file);
				}else if(file.isFile()){
					file.delete();
				}
			}
		}
		dir.delete();
	}
}
