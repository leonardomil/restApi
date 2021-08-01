package com.yieldstreet.accreditation.model.json;

public class Validate {

	/**
	 * Check if all the required values were informed
	 * @param acred
	 * @return true if every parameter is informed
	 */
	public static String isAValidJson(JsonAccreditation acred) {
		StringBuilder issues = new StringBuilder("");
		String ret="";
		
		try {
				isFieldOk(issues, acred.getUserId(),"user_id");
				
				if (isFieldOk(issues, acred.getPay(),"payload")) {
					isFieldOk(issues, acred.getPay().getAccreditationType(),"accreditation_type");
					
					
			        if (isFieldOk(issues, acred.getPay().getDocs(),"documents")) {
						for (JsonDocument doc : acred.getPay().getDocs()) {
				        	isFieldOk(issues, doc.getName() ,"name");
				        	isFieldOk(issues, doc.getMimeType(),"mime_type");
				        	isFieldOk(issues, doc.getContent(),"content");
				        }
			        }
				}    
	        } catch (Exception e) {
	        	ret = "Invalid format";
	        } finally {
		        if (issues.length()>0) {
		        	ret = "Missing fields:" + issues.substring(0,issues.length()-1);
		        }
	        	
	        }
        
		return ret;
	}
	
	/**
	 * Check if an object is empty, null or ""
	 * @param missing String that will receive the name of empty values
	 * @param x Object to evaluate
	 * @param fieldName Name of the object 
	 * @return false if the value is not fullfiled
	 */
	public static boolean isFieldOk(StringBuilder missing, Object x,String fieldName) {
		
		if (x == null || (x instanceof String && ((String) x).isEmpty()) || x.toString().equals("[]") ) {
			// avoid insert more than one time the name of field to the missing values
			if (missing.indexOf(fieldName)==-1) {
				missing.append(" "+ fieldName + ",");
			}	
			return false;
		} else 
			return true;
	}

}
