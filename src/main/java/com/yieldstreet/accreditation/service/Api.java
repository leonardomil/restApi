package com.yieldstreet.accreditation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yieldstreet.accreditation.domain.Accreditation;
import com.yieldstreet.accreditation.model.json.EInvalidVerb;
import com.yieldstreet.accreditation.model.json.JsonAccreditation;
import com.yieldstreet.accreditation.model.json.Validate;
import com.yieldstreet.accreditation.repository.Process;
import com.yieldstreet.accreditation.repository.RepoAccreditation;
import com.yieldstreet.accreditation.repository.RepoDocument;  

/**
 * Class that will process the requisitions made to the service
 * @author Milani
 *
 */

@RestController  //it will control all the request
@RequestMapping("user/accreditation")   
public class Api {
	
	
	//class that persist the info and validate it
    @Autowired
	private Process repo ;
	
    /**
     * method that responds to a post call 
     * @param acred
     * @return
     */
	@PostMapping
	public ResponseEntity<Object> addAccreditation(@RequestBody JsonAccreditation acred) {
		Return ro = new Return();
	
		try {
		

			//check the json format
			String valid = Validate.isAValidJson(acred);
					
					
			if (!valid.equals("")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ro.invalidJson(valid));
			}
			//persist the information 
			else if (!repo.save(acred)) {
				//the client is not accredited
				return ResponseEntity.status(HttpStatus.OK).body(ro.successful());
			} 
			//
			//the client is accredited
			else {
			   return ResponseEntity.status(HttpStatus.CREATED).body(ro.accredited());
			}
			
			
		} catch (EInvalidVerb e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ro.error(e.getMessage()));
	    }catch (Exception e) {
			//return http status in case of a non specified error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ro.error(e.getMessage()));
		} 
		
	}

	@GetMapping
	public List<Accreditation> getAccreditation() {
		return repo.findAll();
		
	}

}
