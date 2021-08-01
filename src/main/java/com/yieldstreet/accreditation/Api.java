package com.yieldstreet.accreditation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yieldstreet.accreditation.model.Accreditation;
import com.yieldstreet.accreditation.model.json.JsonAccreditation;
import com.yieldstreet.accreditation.repo.Process;
import com.yieldstreet.accreditation.validate.EInvalidVerb;  

/**
 * Class that will process the requisitions made to the service
 * @author Milani
 *
 */

@RestController  //it will control all the request
@RequestMapping("user/accreditation")   //
@Validated

public class Api {
	
	
    /**
     * method that responds to a post call 
     * @param acred
     * @return
     */
	@PostMapping
	public ResponseEntity<Object> addAccreditation(@Valid @RequestBody JsonAccreditation acred) {
		Return ro = new Return();
	
		try {
		
			//class that persist the info and validate it
			Process repo = new Process();

			//check the json format
			String valid = repo.isAValidJson(acred);
					
					
			if (!valid.equals("")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ro.invalidJson(valid));
			}
			//persist the info 
			else if (!repo.persist(acred)) {
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
		Process repo = new Process();
		return repo.findAll();
		
	}

}
