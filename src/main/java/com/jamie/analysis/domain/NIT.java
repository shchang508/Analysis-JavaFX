package com.jamie.analysis.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "NIT")
@XmlType(propOrder = { "nitEntryList" })
public class NIT {
	
	@XmlElement(name = "NIT-ENTRY")
	private List<NIT_ENTRY> nitEntryList;

	public List<NIT_ENTRY> getNitEntryList() {
		return nitEntryList;
	}

	public void setNitEntryList(List<NIT_ENTRY> nitEntryList) {
		this.nitEntryList = nitEntryList;
	}
	
	
}
