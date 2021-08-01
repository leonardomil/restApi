package com.yieldstreet.accreditation.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object used to map json object received 
 * @author Milani
 *
 */
public class JsonAccreditation {
	
	@JsonProperty("user_id")
	private String userId;

	@JsonProperty("payload")
	private JsonPayload pay;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public JsonPayload getPay() {
		return pay;
	}
	public void setPay(JsonPayload pay) {
		this.pay = pay;
	}
	
	

}
