package com.jamie.analysis.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CHANNEL")
@XmlType(propOrder = { "eitChannelEventList" })
public class EIT_CHANNEL {
	
	@XmlElement(name = "EVENT")
	private List<EIT_CHANNEL_EVENT> eitChannelEventList;

	public List<EIT_CHANNEL_EVENT> getEitChannelEventList() {
		return eitChannelEventList;
	}

	public void setEitChannelEventList(List<EIT_CHANNEL_EVENT> eitChannelEventList) {
		this.eitChannelEventList = eitChannelEventList;
	}
	
	

}
