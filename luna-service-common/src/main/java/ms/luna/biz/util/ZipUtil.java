package ms.luna.biz.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.UnmappableCharacterException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.alibaba.fastjson.JSONObject;

public class ZipUtil {

	private static int buffer = 1024;

	/**
	 * 递归取到当前目录所有文件
	 * @param dir
	 * @return
	 */
	public static List<String> getFiles(String dir){
		List<String> lstFiles = null;
		if (lstFiles == null) {
			lstFiles = new ArrayList<String>();
		}
		File file = new File(dir);
		File [] files = file.listFiles();
		if (files != null) {
			for(File f : files){
				if (f.isDirectory()) {
					lstFiles.add(f.getAbsolutePath());
					lstFiles.addAll(getFiles(f.getAbsolutePath()));
				} else { 
					String str =f.getAbsolutePath();
					lstFiles.add(str);
				}
			}
		}
		return lstFiles;
	}

	/**
	 * 解压zip文件支持（UTF-8格式以及GBK格式）
	 * @param zipFilePath
	 * @param saveFileDir
	 * @return
	 */
	public static JSONObject decompressZip(String zipFilePath, String saveFileDir) {

		// 创建文件夹
		if (!VbUtility.createLocalFolder(saveFileDir)) {
			RuntimeException re = new RuntimeException("创建文件夹失败["+saveFileDir+"]");
			MsLogger.error(re);
			throw re;
		}

		int count = -1;
		InputStream is = null;
		FileOutputStream fos = null;

		ZipFile zipFile = null;
		try {
			try {
				// 尝试以gbk字符集解压
				zipFile = new ZipFile(new File(zipFilePath), "gbk");
			} catch (UnmappableCharacterException uce) {

				// 尝试以uft-8字符集解压
				zipFile = new ZipFile(new File(zipFilePath), "utf-8");
			}

			Enumeration<?> entries = zipFile.getEntries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry)entries.nextElement();
				String filename = entry.getName();
				filename = saveFileDir + filename;

				// 如果是文件夹则跳过
				if (entry.isDirectory()){
					continue;
				}
				// 获取文件名
				String entryFileName = filename;
				if (entryFileName.toUpperCase().startsWith("__MACOSX")) {
					continue;
				}
				if (entryFileName.matches("(.*/|)\\..*")) {
					continue;
				}
				if (entry.isDirectory()) {
					continue;
				}
				entryFileName = ZipUtil.getFileNmInZip(entryFileName);
				// 构造解压出来的文件存放路径
				String entryFilePath = saveFileDir + entryFileName;
				byte[] content = new byte[buffer];

				try {
					is = zipFile.getInputStream(entry);
					File entryFile = new File(entryFilePath);
					fos = new FileOutputStream(entryFile);
					while ((count = is.read(content, 0 , buffer)) != -1) {
						fos.write(content, 0, count);
					}
				} catch(IOException e) {
					MsLogger.error(e);
					JSONObject result = new JSONObject();
					result.put("code", "-1");
					result.put("msg", e.getMessage());
					result.put("data", "{}");
					return result;
				} finally {
					if(fos != null) {
						fos.flush();
						fos.close();
					}
				}
			}
		} catch (Exception e) {
			MsLogger.error(e);
			throw new RuntimeException(e);
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					MsLogger.error(e);
				}
			}
		}
		return FastJsonUtil.sucess("OK");
	}

//	private static boolean skip(String filename) {
//		
//		filename.matches("/\\.");
//		
//		
//		if (filename.toUpperCase().startsWith("__MACOSX")) {
//			continue;
//		}
//		int index = filename.lastIndexOf("/");
//		String factName = filename;
//		if (index > -1 && (index+1) < filename.length()) {
//			factName = filename.substring(index+1);
//		}
//
//		if (entryFileName.indexOf(".") == 0) {
//			continue;
//		}
//	}
	
	private static String getFileNmInZip(String fileNm) {
		int index = fileNm.lastIndexOf("/");
		if (index >= 0) {
			System.out.println(fileNm);
			return fileNm.substring(index+1);
		}
		return fileNm;
	}
}
