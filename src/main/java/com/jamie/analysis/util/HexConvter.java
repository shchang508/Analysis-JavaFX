package com.jamie.analysis.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class HexConvter extends XmlAdapter<String, String> {

	@Override
	public String unmarshal(String dateStr) throws Exception {
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

	@Override
	public String marshal(String date) throws Exception {
		return null;
	}

}
