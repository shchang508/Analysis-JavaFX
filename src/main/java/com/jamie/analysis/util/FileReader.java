package com.jamie.analysis.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;


public class FileReader {
	
//	read files in the project
	 public static InputStream getFileWithUtil(Class clazz, String fileName) {
		ClassLoader classLoader = clazz.getClassLoader();
		return classLoader.getResourceAsStream(fileName);
	 }

	 
	 public static ArrayList readFile(String filePath) {
			File folder = new File(filePath);
//			File[] listOfFiles = folder.listFiles();
			
			ArrayList<String> fileList = new ArrayList<String>(); 
			
			for(String name: folder.list()) {
				fileList.add(name);
			}
			
//			for(int i = 0; i < listOfFiles.length; i++) {
//				fileName = listOfFiles[i].getName();
//				if (fileName.endsWith(".xml") || fileName.endsWith(".XML")) {
//					//System.out.println(filename);
//					
//				}
//			
//			}
			return fileList;	
	
	 }


}
