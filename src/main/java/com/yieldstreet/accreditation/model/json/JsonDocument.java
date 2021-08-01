package com.yieldstreet.accreditation.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object used to map json object received
 * @author Milani
 *
 */
public class JsonDocument {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("mime_type")
	private String mimeType;
	
	@JsonProperty("content")
	private String content;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
