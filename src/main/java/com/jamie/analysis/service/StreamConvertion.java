package com.jamie.analysis.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class StreamConvertion {
	private static final Logger logger = Logger.getLogger(StreamConvertion.class);

	public String path = "C:\\";

	public ArrayList<String> xfilenames = new ArrayList<String>();

	public ArrayList<String> getXfilenames() {
		return xfilenames;
	}

	public StreamConvertion(String streampath) throws Exception {
		createfolder();
		
		File folder = new File(streampath);
		File[] listofFiles = folder.listFiles();

		String xfilepath = path + "xml\\";
		File xmlFolder = new File(xfilepath);
		File[] listofXml = xmlFolder.listFiles();
		
//		if (listofFiles.length == 0) {
//			throw new Exception();
//		}
		

		Set<String> checkFileList = checkFile(listofFiles, listofXml);
		logger.info("Stream List Size: " + checkFileList.size());
		TSReader tsReader = new TSReader();
		logger.info("--------------------FILE LIST--------------------"); 
		/* Load stream and export as XML */
			int count = 1;
			for (String tsFile : checkFileList) {
				logger.info("TS File : " + tsFile);
				if (tsFile.toLowerCase().endsWith(".ts") || tsFile.toLowerCase().endsWith(".trp") || tsFile.toLowerCase().endsWith(".tp") || tsFile.toLowerCase().endsWith(".mpg")) {
					tsReader.send("setting PAST_EIT true");
					tsReader.send("setting KEEP_SPECIAL_XML true");
					tsReader.send("source TSReader_FileLoop.dll\n");
					tsReader.send("tune " + streampath + tsFile + "\n");
					tsReader.send("stall 300");
					// tsReader.waitfor("311 Table decoding complete"); //TSReader2.8.47c not support

					String xfilename = nameconvert(tsFile, "xml");
					tsReader.send("export xml " + xfilepath + xfilename);
					String responseCode = tsReader.waitfor("Data exported");
					logger.info(">>>>TSReader Response Code : " + responseCode);

					String result = "Stream [" + (count++) + "] is exported as XML: " + tsFile;
					logger.info(result);
					xfilenames.add(result);

				}
			}
			tsReader.disconnect();
	}

	
	public Set<String> checkFile(File[] listofFiles, File[] listofXml) {

		Set<String> list = new HashSet<String>();
//		if (listofFiles.length != 0 && listofXml.length != 0) {
		for (File f : listofFiles) {
			Boolean isNew = true;
			for (File x : listofXml) {
				String tsName = f.getName().replace(".ts", "").replace(".trp", "");
				logger.info("Stream: " + tsName);

				String xmlName = x.getName().replace(".xml", "");
				logger.info("XML: " + xmlName);
				
				//有一樣的代表有重複在加到list
				if (tsName.equals(xmlName)) {
					isNew = false;
				}

			}
//		}
			if (isNew) {
				list.add(f.getName());
			}

		}
		return list;
	}

	private void createfolder() {
		String[] folder = { "xml\\", "excel\\" };
		for (String i : folder) {
			File directory = new File(path, i);
			if (directory.exists() && i.equals("xml\\")) {
				File f = new File(path + i);
				File[] f1 = f.listFiles();
				for (File j : f1) //Delete last xml file
					j.delete();
			}
			directory.mkdir();
		}
	}

	private void createfolder(String path) {
		String[] folder = { "xml\\", "excel\\" };
		for (String i : folder) {
			File directory = new File(path, i);
			if (directory.exists()) {
				File f = new File(path + i);
				File[] f1 = f.listFiles();
				for (File j : f1) //Delete xml files from last time
					j.delete();
				directory.delete();
			}
			directory.mkdir();
		}
	}

	public String nameconvert(String name, String ftype) {

		String finalname = null;
		if (ftype == "xml") {
			int index = name.lastIndexOf(".");
			finalname = name.substring(0, index) + ".xml";
		}

		return finalname;
	}
}
