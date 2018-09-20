package com.jamie.analysis.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jamie.analysis.domain.EIT_CHANNEL;
import com.jamie.analysis.domain.EIT_CHANNEL_EVENT;
import com.jamie.analysis.domain.MPEG_TABLES;
import com.jamie.analysis.domain.NIT_ENTRY;
import com.jamie.analysis.domain.PMTs_CHANNEL;
import com.jamie.analysis.domain.PMTs_CHANNEL_ELEMENTARY_STREAM;
import com.jamie.analysis.domain.PMTs_CHANNEL_ELEMENTARY_STREAM_DESCRIPTOR;
import com.jamie.analysis.util.XMLUtil;

public class TestService extends BaseAbstractService {

	private static final Logger logger = Logger.getLogger(TestService.class);

	public Object genTable(String path, Class clazz) {

		return XMLUtil.convertXmlFileToObject(clazz, path);

	}

	public void genMpegExcel(XSSFWorkbook workbook, ArrayList<MPEG_TABLES> mpegList, ArrayList<String> fileList) {
		System.out.println("generate excel...");

		this.setStyle(workbook);

		XSSFSheet sheet = workbook.createSheet("sheet01");

		sheet.createFreezePane(1, 1);

		// Set width of the column
		sheet.setColumnWidth(0, 500 * 20);
		sheet.setColumnWidth(1, 256 * 20);
		sheet.setColumnWidth(2, 256 * 20);
		sheet.setColumnWidth(3, 256 * 20);
		sheet.setColumnWidth(4, 256 * 20);
		sheet.setColumnWidth(5, 256 * 20);
		sheet.setColumnWidth(6, 256 * 20);
		sheet.setColumnWidth(7, 256 * 20);
		sheet.setColumnWidth(8, 256 * 20);
		sheet.setColumnWidth(9, 256 * 20);
		sheet.setColumnWidth(10, 800 * 20);

		XSSFRow title_header = sheet.createRow(0);

		XSSFCell title00 = title_header.createCell(0);
		title00.setCellValue("Name of the Stream");
		title00.setCellStyle(cellStyleMap.get("style_01"));

		XSSFCell title01 = title_header.createCell(1);
		title01.setCellValue("Number of Channels");
		title01.setCellStyle(cellStyleMap.get("style_01"));

		XSSFCell title02 = title_header.createCell(2);
		title02.setCellValue("Subtitle Language");
		title02.setCellStyle(cellStyleMap.get("style_01"));

		XSSFCell title03 = title_header.createCell(3);
		title03.setCellValue("Audio Language");
		title03.setCellStyle(cellStyleMap.get("style_01"));

		XSSFCell title04 = title_header.createCell(4);
		title04.setCellValue("TELETEXT");
		title04.setCellStyle(cellStyleMap.get("style_01"));

		XSSFCell title05 = title_header.createCell(5);
		title05.setCellValue("Dolby Digital");
		title05.setCellStyle(cellStyleMap.get("style_01"));

		XSSFCell title06 = title_header.createCell(6);
		title06.setCellValue("Resolution");
		title06.setCellStyle(cellStyleMap.get("style_01"));

		XSSFCell title07 = title_header.createCell(7);
		title07.setCellValue("HbbTV");
		title07.setCellStyle(cellStyleMap.get("style_01"));

		XSSFCell title08 = title_header.createCell(8);
		title08.setCellValue("Freeview");
		title08.setCellStyle(cellStyleMap.get("style_01"));

		XSSFCell title09 = title_header.createCell(9);
		title09.setCellValue("Radio channel");
		title09.setCellStyle(cellStyleMap.get("style_01"));

		XSSFCell title10 = title_header.createCell(10);
		title10.setCellValue("Rating");
		title10.setCellStyle(cellStyleMap.get("style_01"));

		for (int i = 0; i < mpegList.size(); i++) {

			MPEG_TABLES table = mpegList.get(i);
			String fileName = fileList.get(i);
			int initRow = i + 1;

			XSSFCell cell = null;

			XSSFRow detailRow = sheet.createRow(initRow);

			logger.info("---------------Convert XML to Object START---------------");

			/************************************ Name of the Stream ************************************/
			cell = detailRow.createCell(0);
			// TUNED_MULTIPLEX tunerString = table.getTunedMultiplex();
			logger.info("Name of the Stream : " + fileName);
			cell.setCellValue(fileName.replaceAll(".xml", ""));
			cell.setCellStyle(cellStyleMap.get("style_02"));

			/************************************ Number of channels ************************************/
			cell = detailRow.createCell(1);
			List<PMTs_CHANNEL> pmtsChannelList = table.getPmts().getPmtsChannelList();
			logger.info("Number of Channels : " + pmtsChannelList.size());
			cell.setCellValue(pmtsChannelList.size());
			cell.setCellStyle(cellStyleMap.get("style_02"));

			boolean dolbyFlag = true;
			boolean resolutionFlag = true;
			boolean hbbFlag = true;
			boolean teletextFlag = true;
			boolean radioFlag = true;

			Set<String> setsData = new HashSet<String>();
			Set<String> setsData1 = new HashSet<String>();

			for (PMTs_CHANNEL c : pmtsChannelList) {
				logger.info("Service Number : " + c.getServiceNumber());
				List<PMTs_CHANNEL_ELEMENTARY_STREAM> elementaryStreamList = c.getPmtsChannelEementaryStreamList();

				boolean hasVideo = false;
				boolean hasAudio = false;

				if (elementaryStreamList != null) {
					for (PMTs_CHANNEL_ELEMENTARY_STREAM e : elementaryStreamList) {
						List<PMTs_CHANNEL_ELEMENTARY_STREAM_DESCRIPTOR> descriptorList = e.getDescriptorList();
						logger.info("descriptorList : " + descriptorList);

						if (descriptorList != null) {
							for (PMTs_CHANNEL_ELEMENTARY_STREAM_DESCRIPTOR d : descriptorList) {
								logger.info("TAG : " + d.getTag());
								logger.info("LENGTH : " + d.getLength());
								logger.info("DATA : " + d.getData());

								/************************************ Subtitle Language ************************************/
								if ("0x59".equals(d.getTag())) {
									String data = d.getData().substring(0, 3);
									setsData.add(data);
								}

								/************************************ HbbTV ************************************/
								if (hbbFlag) {
									logger.info(">>>>>>TAG : " + d.getTag()); // TAG-->0x6f & DATA -->0x10
									cell = detailRow.createCell(7);
									if (d.getData() != null) {
										logger.info("Comparison : " + d.getData().contains(unmarshal("0x10"))); 

										if ("0x6f".equals(d.getTag()) && d.getData().contains(unmarshal("0x10"))) {
											cell.setCellValue("Y");
											hbbFlag = false;
											cell.setCellStyle(cellStyleMap.get("style_02"));
										} else {
											cell.setCellValue("N");
											cell.setCellStyle(cellStyleMap.get("style_02"));
										}
									}
								}

								/************************************ Visually Impaired ************************************/
//							if (visFlag) {
//								cell = detailRow.createCell(7);
//								if ("0x0a".equals(d.getTag())) {
//									cell.setCellValue("Y");
//									cell.setCellStyle(cellStyleMap.get("style_02"));
//									visFlag = false;
//								} else {
//									cell.setCellValue("N");
//									cell.setCellStyle(cellStyleMap.get("style_02"));
//								}
//							}

								/************************************ TELETEXT ************************************/

								if (teletextFlag) {
									if ("0x56".equals(d.getTag())) {
										logger.info(">>>>>>TELETEXT : " + d.getTag());
										cell = detailRow.createCell(4);
										cell.setCellValue("Y");
										teletextFlag = false;
										cell.setCellStyle(cellStyleMap.get("style_02"));
									} else {
										cell = detailRow.createCell(4);
										cell.setCellValue("N");
										cell.setCellStyle(cellStyleMap.get("style_02"));
									}

								}

							}
						} else {
							logger.info("descriptorList is an empty list!");
						}

						/************************************ Audio Language ************************************/
						logger.info("AUDIO-LANGUAGE : " + e.getAudioLanguage());
						if (e.getAudioLanguage() != null) {
							setsData1.add(e.getAudioLanguage());
						}

						/************************************ Audio Type ************************************/
						logger.info("AUDIO-TYPE : " + e.getAudioType());
						if (dolbyFlag) {
							if (e.getAudioType() != null && e.getAudioType().contains("AC3")) {
								cell = detailRow.createCell(5);
								logger.info(">>>>>>AUDIO-TYPE : " + e.getAudioType());
								cell.setCellValue("Y");
								dolbyFlag = false;
								cell.setCellStyle(cellStyleMap.get("style_02"));
							} else {
								cell = detailRow.createCell(5); // add
								cell.setCellValue("N");
								cell.setCellStyle(cellStyleMap.get("style_02"));
							}
						}

						/************************************ Resolution ************************************/
						logger.info("HORIZONTAL-RESOLUTION : " + e.getHorizontalResolution());
						logger.info("VERTICAL-RESOLUTION : " + e.getVerticalResolution());

						if (StringUtils.isNotBlank(e.getHorizontalResolution()) && StringUtils.isNotBlank(e.getVerticalResolution())) {
							if (resolutionFlag) {
								cell = detailRow.createCell(6);
								cell.setCellValue(e.getHorizontalResolution() + "*" + e.getVerticalResolution());
								resolutionFlag = false;
								cell.setCellStyle(cellStyleMap.get("style_02"));
							}
						}
//						if (e.getHorizontalResolution() != null && e.getVerticalResolution() != null) {
//							if (resolutionFlag) {
//								cell = detailRow.createCell(6);
//								cell.setCellValue(e.getHorizontalResolution() + "*" + e.getVerticalResolution());
//								resolutionFlag = false;
//								cell.setCellStyle(cellStyleMap.get("style_02"));
//							}
//						}

						/************************************ Radio Channel ************************************/
						logger.info("STREAM-TYPE : " + e.getStreamType());
						if (radioFlag) {
							if ("VIDEO".equals(e.getStreamType())) {
								logger.info(">>>>>>STREAM-TYPE: " + e.getStreamType());
								hasVideo = true;
							} else if ("AUDIO".equals(e.getStreamType())) {
								hasAudio = true;
							}

						}

					}
				} else {
					logger.info("elementaryStreamList is an empty list!");
				}

				if (radioFlag) {
					if (hasVideo == false && hasAudio == true) {
						cell = detailRow.createCell(9);
						cell.setCellValue("Y");
						radioFlag = false;
						cell.setCellStyle(cellStyleMap.get("style_02"));
					} else {
						cell = detailRow.createCell(9);
						cell.setCellValue("N");
						cell.setCellStyle(cellStyleMap.get("style_02"));
					}
				}

			}

			/************************************ Audio Language ************************************/
			String su2 = StringUtils.join(setsData1, ", ");
			cell = detailRow.createCell(3);
			cell.setCellStyle(cellStyleMap.get("style_02"));
			if ("".equals(su2)) {
				cell.setCellValue("N/A");
			} else {
				cell.setCellValue(su2);
			}

			/************************************ Subtitle Language ************************************/
//			StringBuilder sbData = new StringBuilder();
//			int countData = 0;
//			for (String sData : setsData) {
//				countData++;
//				sbData.append(sData);
//				if (countData != setsData.size()) {
//					sbData.append(", ");
//				}
//			}
			
			String su1 = StringUtils.join(setsData, ", ");
			cell = detailRow.createCell(2);
			cell.setCellStyle(cellStyleMap.get("style_02"));
			if ("".equals(su1)) {
				cell.setCellValue("N/A");
			} else {
				cell.setCellValue(su1);
			}

			/************************************ Freeview ************************************/
			cell = detailRow.createCell(8);
			List<NIT_ENTRY> entryList = table.getNit().getNitEntryList();
			
			for (NIT_ENTRY nc : entryList) {
				if ("Freeview".equals(nc.getNetworkName().trim())) {
					logger.info("NETWORK-NAME : " + nc.getNetworkName());
					cell.setCellValue("Y");
					cell.setCellStyle(cellStyleMap.get("style_02"));
				} else {
					cell.setCellValue("N");
					cell.setCellStyle(cellStyleMap.get("style_02"));
				}
			}

			/************************************ Rating ************************************/
			Set<String> sets = new HashSet<String>();
			Set<Integer> setInt = new HashSet<Integer>();
			cell = detailRow.createCell(10);
			List<EIT_CHANNEL> eitChannelList = table.getEit().getEitChannelList();
			
			if (eitChannelList != null && !eitChannelList.isEmpty()) {
				for (EIT_CHANNEL ec : eitChannelList) {
					List<EIT_CHANNEL_EVENT> eventList = ec.getEitChannelEventList();
					if (eventList != null && !eventList.isEmpty()) {
						for (EIT_CHANNEL_EVENT evnt : eventList) {
							logger.info("EVENT : " + evnt.getRating());

							// Check if string is a number
							String numberRegex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";

							String[] arrRating = evnt.getRating().split(": ");
							if ((evnt.getRating() != null && !"".equals(evnt.getRating())) && !"undefined".equals(arrRating[1]) && !"Not classified".equals(arrRating[1])) {
								String[] numbers = arrRating[1].split(" ");
								if (numbers[0].matches(numberRegex)) {
									setInt.add(Integer.parseInt(numbers[0].trim()));

								} else {
									sets.add(arrRating[1]);
								}
							}
						}
					} else {
						logger.info("eventList is an empty list!");
					}
				}
			} else {
				logger.info("eitChannelList is an empty list!");
			}

			String years = this.addYears(setInt, sets);
			cell.setCellValue(years);
			cell.setCellStyle(cellStyleMap.get("style_02"));

			logger.info("---------------Convert XML to Object END---------------");
		}

	}

	//Add "years old" for rating
	private String addYears(Set<Integer> s1, Set<String> s2) {
		List<Integer> list = new ArrayList<Integer>();

		for (int x : s1) {
			list.add(x);
		}

		List<String> sList = new ArrayList<String>();
		for (int ss : list) {
			sList.add(ss + " years old");
		}

		sList.addAll(s2);

		Collections.sort(list);

		String su = StringUtils.join(sList, ", ");

		return su;
	}

	
	private String unmarshal(String dateStr) {
		StringBuilder text = new StringBuilder();

		String[] arrData = dateStr.split("0x");
		for (int i = 1; i < arrData.length; i++) {
			String[] arr1 = arrData[i].split(" ");

			int hexVal = Integer.parseInt(arr1[0], 16);
			char[] character = Character.toChars(hexVal);

			String text1 = new String(character);

			text.append(text1);
		}

		return text.toString();
	}
}
