package com.jamie.analysis.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "EIT")
@XmlType(propOrder = { "eitChannelList" })
public class EIT {

	@XmlElement(name = "CHANNEL")
	private List<EIT_CHANNEL> eitChannelList;

	public List<EIT_CHANNEL> getEitChannelList() {
		return eitChannelList;
	}

	public void setEitChannelList(List<EIT_CHANNEL> eitChannelList) {
		this.eitChannelList = eitChannelList;
	}
	
	
}
