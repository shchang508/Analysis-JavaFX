package com.jamie.analysis.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PMTs")
@XmlType(propOrder = { "pmtsChannelList" })
public class PMTs {
	
	@XmlElement(name = "CHANNEL")
	private List<PMTs_CHANNEL> pmtsChannelList;

	public List<PMTs_CHANNEL> getPmtsChannelList() {
		return pmtsChannelList;
	}

	public void setPmtsChannelList(List<PMTs_CHANNEL> pmtsChannelList) {
		this.pmtsChannelList = pmtsChannelList;
	}
}
