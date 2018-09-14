package com.jamie.analysis.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CHANNEL")
@XmlType(propOrder = { "serviceNumber", "pmtsChannelEementaryStreamList" })
public class PMTs_CHANNEL {
	
	@XmlElement(name = "SERVICE-NUMBER")
	private String serviceNumber;

	@XmlElement(name = "ELEMENTARY-STREAM")
	private List<PMTs_CHANNEL_ELEMENTARY_STREAM> pmtsChannelEementaryStreamList;

	public String getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public List<PMTs_CHANNEL_ELEMENTARY_STREAM> getPmtsChannelEementaryStreamList() {
		return pmtsChannelEementaryStreamList;
	}

	public void setPmtsChannelEementaryStreamList(List<PMTs_CHANNEL_ELEMENTARY_STREAM> pmtsChannelEementaryStreamList) {
		this.pmtsChannelEementaryStreamList = pmtsChannelEementaryStreamList;
	}
	
	
}
