package com.yieldstreet.accreditation.model.json;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

/**
 * Object used to map json object received
 * @author Milani
 *
 */
public class JsonPayload {

	@JsonProperty("accreditation_type")
	@NotBlank	
	private String accreditationType;
	
	@JsonProperty("documents")
	@NotNull	
	private Set<JsonDocument> docs;

	
	public String getAccreditationType() {
		return accreditationType;
	}

	public void setAccreditationType(String accreditationType) {
		this.accreditationType = accreditationType;
	}

	public Set<JsonDocument> getDocs() {
		return docs;
	}

	public void setDocs(Set<JsonDocument> docs) {
		this.docs = docs;
	}

}
