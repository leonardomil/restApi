package com.yieldstreet.accreditation.repo;

import com.yieldstreet.accreditation.model.Accreditation;
import com.yieldstreet.accreditation.model.Document;
import com.yieldstreet.accreditation.model.json.JsonAccreditation;
import com.yieldstreet.accreditation.model.json.JsonDocument;
import com.yieldstreet.accreditation.validate.EInvalidVerb;

/**
 * Class resposible for persistence and validations
 * @author Milani
 *
 */
public class Process {
	
	
	/**
	 * Check if all the required values were informed
	 * @param acred
	 * @return true if every parameter is informed
	 */
	public String isAValidJson(JsonAccreditation acred) {
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
	public boolean isFieldOk(StringBuilder missing, Object x,String fieldName) {
		
		if (x == null || (x instanceof String && ((String) x).isEmpty()) || x.toString().equals("[]") ) {
			// avoid insert more than one time the name of field to the missing values
			if (missing.indexOf(fieldName)==-1) {
				missing.append(" "+ fieldName + ",");
			}	
			return false;
		} else 
			return true;
	}
	
	/**
	 * persist the info of Accreditation
	 * @param acred
	 * @return true if it was successful
	 * @throws EInvalidVerb 
	 */
	public boolean persist(JsonAccreditation acred, RepoAccreditation repoA, RepoDocument repoD) throws EInvalidVerb {
		 
		 // random number to give a pseudo accreditation
		 int rand = (int)(100*Math.random());

		 if (rand % 2== 0)  
		     return save(acred, repoA, repoD);
		 else  
			 return false; 
	
	}
	
	
	private boolean save(JsonAccreditation acred, RepoAccreditation repoA, RepoDocument repoD	) throws EInvalidVerb {
		
		Accreditation a = new Accreditation();
		a.setUserId(acred.getUserId());
		
		//check if alredy exists the Accreditation
		if (repoA.findByUserId(acred.getUserId())!=null) {
			throw new EInvalidVerb("It must be called with PUT");
		}
		
		repoA.save(a);
		
		//save the documents
		for (JsonDocument d0 : acred.getPay().getDocs()) {
			Document d = new Document();
			d.setMimeType(d0.getMimeType());
			d.setContent(d0.getContent());
			d.setName(d0.getName());
			d.setAccreditation(a);
			repoD.save(d);
		}
		
		return true;

	}
	
}
