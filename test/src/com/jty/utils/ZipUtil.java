package com.jty.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtil {

	private static int BUFFER = 2048;
	private static String ENCODING = "UTF-8";

	/** */
	/**
	 * 压缩文件
	 * 
	 * @param src
	 *            源文件/目录
	 * @param dest
	 *            压缩后的文件/目录
	 */
	public static void zip(String src, String dest) {
		// System.out.println("生成压缩文件……");
		File srcFile = new File(src);
		File destFile = new File(dest);
		if (destFile.isDirectory()) {
			// 构造压缩文件名。取当前文件/目录名称为压缩文件名。
			String name = srcFile.getName();
			// System.out.println(name);
			name = name.indexOf(".") > 0 ? name.substring(0, name.indexOf("."))
					: name;
			name = name + ".zip";
			destFile = new File(destFile + "/" + name);
		}
		// System.out.println(destFile.getAbsolutePath());
		zip(srcFile, destFile);
	}

	/**
	 * 功能:将一个指定目录压缩
	 * 
	 * @param srcfile
	 *            ：要压缩文件目录
	 * @param zipfile
	 *            ：压缩之后的文件路径
	 */
	public static void zipFiles(String srcPath, File zipFile) {

		File srcdir = new File(srcPath);
		if (!srcdir.exists())
			throw new RuntimeException(srcPath + "不存在！");
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		zip.addFileset(fileSet);
		zip.execute();
	}

	/** */
	/**
	 * 压缩文件
	 * 
	 * @param src
	 * @param dest
	 */
	public static void zip(File src, File dest) {
		try {
			FileOutputStream fout = new FileOutputStream(dest);
			CheckedOutputStream chc = new CheckedOutputStream(fout, new CRC32());
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					chc));
			// System.out.println("开始递归压缩……");
			zip(out, src, src.getName());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** */
	/**
	 * 递归压缩文件夹
	 * 
	 * @param out
	 *            输出流
	 * @param srcFile
	 *            压缩文件名
	 * @param path
	 *            压缩文件路径
	 */
	public static void zip(ZipOutputStream out, File srcFile, String path) {
		try {
			if (srcFile.isDirectory()) {
				// System.out.println("压缩文件夹"+srcFile.getName());
				File[] f = srcFile.listFiles();
				out.putNextEntry(new org.apache.tools.zip.ZipEntry(path + "/"));
				path = path.equals("") ? "" : path + "/";

				for (int i = 0; i < f.length; i++) {
					zip(out, f[i], path + f[i].getName());
				}
			} else {
				// System.out.println("压缩文件"+path);
				out.putNextEntry(new org.apache.tools.zip.ZipEntry(path));
				BufferedReader in = new BufferedReader(new InputStreamReader(
						new FileInputStream(srcFile), "iso8859-1"));

				int c;
				while (-1 != (c = in.read())) {
					out.write(c);
				}
				in.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** */
	/**
	 * 定义解压缩zip文件的方法。 未解决中文文件名问题
	 * 
	 * @param zipFilePath
	 * @param outputDirectory
	 */
	public static List<File> unZip(String zipFilePath, String outputDirectory) {
		try {
			ZipInputStream in = new ZipInputStream(new BufferedInputStream(
					new FileInputStream(zipFilePath)));
			BufferedOutputStream bos = null;
			// 获取ZipInputStream中的ZipEntry条目，一个zip文件中可能包含多个ZipEntry，
			// 当getNextEntry方法的返回值为null，则代表ZipInputStream中没有下一个ZipEntry，
			// 输入流读取完成；
			ZipEntry entry;
			List<File> lists = new ArrayList<File>();
			while ((entry = in.getNextEntry()) != null) {
				// System.out.println("unziping " + entry.getName());
				entry.setSize(in.available());
				// 创建以zip包文件名为目录名的根目录
				File f = new File(outputDirectory);
				f.mkdir();
				if (entry.isDirectory()) {
					String name = entry.getName();
					name = name.substring(0, name.length() - 1);
					// System.out.println("name " + name);
					f = new File(outputDirectory + File.separator + name);
					f.mkdir();
//					System.out.println("mkdir " + outputDirectory
//							+ File.separator + name);
				} else {
					String path = outputDirectory + File.separator
							+ entry.getName();
					f = new File(path);
					f.createNewFile();
					FileOutputStream out = new FileOutputStream(f);
					bos = new BufferedOutputStream(out, BUFFER);
					int b;
					byte data[] = new byte[BUFFER];
					while ((b = in.read(data, 0, BUFFER)) != -1) {
						bos.write(data, 0, b);
					}
					lists.add(f);
					bos.close();
				}
			}
			in.close();
			return lists;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** */
	/**
	 * 设置缓冲区大小
	 * 
	 * @param size
	 */
	public static void setBuffer(int size) {
		BUFFER = size;
	}

	/** */
	/**
	 * 设置字符编码
	 * 
	 * @param size
	 */
	public static void setEncoding(String encoding) {
		ENCODING = encoding;
	}
	
	/**
	 * 解压文件
	 * @auther xf
	 * @date 2015-1-12 下午1:27:21
	 * @param srcFile
	 * @param destPath
	 * @return
	 */
	public static List<File> decompress(File srcFile, String destPath) {
		try {
			File outFile = new File(destPath);
			if (!outFile.exists()) {
				outFile.mkdirs();
			}
			ZipFile zipFile = new ZipFile(srcFile);
			Enumeration<?> en = zipFile.getEntries();
			ZipArchiveEntry zipEntry = null;
			List<File> files = new ArrayList<File>();
			while (en.hasMoreElements()) {
				zipEntry = (ZipArchiveEntry) en.nextElement();
				if (zipEntry.isDirectory()) {
					// mkdir directory
					String dirName = zipEntry.getName();
					dirName = dirName.substring(0, dirName.length() - 1);
					File f = new File(outFile.getPath() + "/" + dirName);
					f.mkdirs();
				} else {
					// unzip file
					String entryName = "";
					if(zipEntry.getName().endsWith("json")){
						entryName=  UUID.randomUUID().toString()+"_"+ zipEntry.getName();
					}else{
						entryName=zipEntry.getName();
					}
					File f = new File(outFile.getPath() + "/"
							+ entryName);
					if (!f.getParentFile().exists()) {
						f.getParentFile().mkdirs();
					}
					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					OutputStream out = new FileOutputStream(f);
					IOUtils.copy(in, out);
					out.close();
					in.close();
					files.add(f);
				}
			}
			zipFile.close();
			return files;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}