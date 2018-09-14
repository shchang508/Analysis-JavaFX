package com.jamie.analysis.util;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CwFileUtils {
	
	// 在指定的位置建立excel
	public static void createExcelFile(XSSFWorkbook workbook, String fileName, String dirLocation) throws Exception {
		System.out.println("dirLocation : " + dirLocation);
		
		FileOutputStream fos = null;
		try {
			// 資料夾位置
			File acctDirPath = new File(dirLocation);
			Path path = Paths.get(acctDirPath.toString());
			
			// 不存在的話,直接建立資料夾
			if (!Files.exists(path)) {
			    Files.createDirectory(path);
			    System.out.println("create folder : " + dirLocation);
			} else {
				System.out.println("folder exists");
			}
			
			// 檔案位置
			String fileLocation = dirLocation + "/" + fileName + ".xlsx";
			
			File xlsFile = new File(fileLocation);
			
			if (xlsFile.exists()){
				System.out.println(fileLocation + " file exists");
				xlsFile.delete();
			}
			
			fos = new FileOutputStream(xlsFile);
			workbook.write(fos);
			
			System.out.println("create file : " + fileName + ".xlsx");
			
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		} finally {
			if(fos != null) {
				fos.flush();
				fos.close();
			}
		}
	}
}
