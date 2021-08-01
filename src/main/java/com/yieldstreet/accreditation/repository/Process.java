package com.yieldstreet.accreditation.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.yieldstreet.accreditation.domain.Accreditation;
import com.yieldstreet.accreditation.domain.Document;
import com.yieldstreet.accreditation.model.json.EInvalidVerb;
import com.yieldstreet.accreditation.model.json.JsonAccreditation;
import com.yieldstreet.accreditation.model.json.JsonDocument;

/**
 * Class resposible for persistence and validations
 * @author Milani
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Process {
	

	@Autowired
	private RepoDocument repositoryDocument;

	@Autowired
	private RepoAccreditation repoAccreditation;

	
	/**
	 * persist the info of Accreditation
	 * @param acred
	 * @return true if it was successful
	 * @throws EInvalidVerb 
	 */
	public boolean save(JsonAccreditation acred) throws EInvalidVerb {
		
		// random number to give a pseudo accreditation
		int rand = (int)(100*Math.random());
		
        if (rand % 2== 0) {  
			Accreditation a = new Accreditation();
			a.setUserId(acred.getUserId());
	
			//check if alredy exists the Accreditation
			if (repoAccreditation.findByUserId(acred.getUserId())!=null) {
				throw new EInvalidVerb("It must be called with PUT");
			}
			
			repoAccreditation.save(a);
			
			//save the documents
			for (JsonDocument d0 : acred.getPay().getDocs()) {
				Document d = new Document();
				d.setMimeType(d0.getMimeType());
				d.setContent(d0.getContent());
				d.setName(d0.getName());
				d.setAccreditation(a);
				repositoryDocument.save(d);
			}
			
			return true; 
        } else {
        	return false;
        }
	}
	
	public List<Accreditation> findAll() {
		return repoAccreditation.findAll();
	}
}
