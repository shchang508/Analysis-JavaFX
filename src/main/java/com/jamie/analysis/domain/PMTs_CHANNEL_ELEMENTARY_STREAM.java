package com.jamie.analysis.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ELEMENTARY-STREAM")
@XmlType(propOrder = { "streamType", "audioLanguage", "audioType", "horizontalResolution",
						"verticalResolution", "descriptorList" })
public class PMTs_CHANNEL_ELEMENTARY_STREAM {
	
	@XmlElement(name = "STREAM-TYPE")
	private String streamType;
	
	@XmlElement(name = "AUDIO-LANGUAGE")
	private String audioLanguage;
	
	@XmlElement(name = "AUDIO-TYPE")
	private String audioType;
	
	@XmlElement(name = "HORIZONTAL-RESOLUTION")
	private String horizontalResolution;
	
	@XmlElement(name = "VERTICAL-RESOLUTION")
	private String verticalResolution;
	
	@XmlElement(name = "DESCRIPTOR")
	private List<PMTs_CHANNEL_ELEMENTARY_STREAM_DESCRIPTOR> descriptorList;

	public String getStreamType() {
		return streamType;
	}

	public void setStreamType(String streamType) {
		this.streamType = streamType;
	}

	public String getAudioLanguage() {
		return audioLanguage;
	}

	public void setAudioLanguage(String audioLanguage) {
		this.audioLanguage = audioLanguage;
	}

	public String getAudioType() {
		return audioType;
	}

	public void setAudioType(String audioType) {
		this.audioType = audioType;
	}

	public String getHorizontalResolution() {
		return horizontalResolution;
	}

	public void setHorizontalResolution(String horizontalResolution) {
		this.horizontalResolution = horizontalResolution;
	}

	public String getVerticalResolution() {
		return verticalResolution;
	}

	public void setVerticalResolution(String verticalResolution) {
		this.verticalResolution = verticalResolution;
	}

	public List<PMTs_CHANNEL_ELEMENTARY_STREAM_DESCRIPTOR> getDescriptorList() {
		return descriptorList;
	}

	public void setDescriptorList(List<PMTs_CHANNEL_ELEMENTARY_STREAM_DESCRIPTOR> descriptorList) {
		this.descriptorList = descriptorList;
	}
	
	
}
