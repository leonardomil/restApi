package com.yieldstreet.accreditation.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.yieldstreet.accreditation.model.Document;
import com.yieldstreet.accreditation.model.json.JsonAccreditation;
import com.yieldstreet.accreditation.repo.Process;
import com.yieldstreet.accreditation.repo.RepoAccreditation;
import com.yieldstreet.accreditation.repo.RepoDocument;
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
	 * Class with the Json structure to response the accreditation call
	 * <p>
	 * Possibility of improvement  e.g. in a case of invalid json 
	 * format inform what field is missing
	 * @author Milani
	 *
	 */
	protected class Returned {
		
		@Expose
		boolean success;
		@Expose
	    boolean accredited;
		
		String error;
		
		public boolean isSuccess() {
			return success;
		}
		
		public String accredited() {
			success= true;
			accredited = true;
			return new Gson().toJson(this);
		}
		
			
		public String successful() {
			success= true;
			accredited = false;
			return new Gson().toJson(this);
		}
		
		/**
		 * This method is used to inform that the process failed
		 * 
		 * @return Json with the status of operation
		 */
		public String invalidJson(String s) {
			success= false;
			accredited = false;
			error = s;
			return new Gson().toJson(this);
		}
		
		public String error(String m) {
			success= false;
			accredited = false;
			error = m;
			return new Gson().toJson(this);
		}

	}
	
	@Autowired
	private RepoAccreditation repoA;
	
	@Autowired
	private RepoDocument repoD;
	
    /**
     * method that attend to a post call 
     * @param acred
     * @return
     */
	@PostMapping
	public ResponseEntity<Object> addAccreditation(@Valid @RequestBody JsonAccreditation acred) {
		Returned ro = new Returned();
	
		try {
		
			//class that persist the info and validate it
			Process repo = new Process();

			//check the json format
			String valid = repo.isAValidJson(acred);
					
					
			if (!valid.equals("")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ro.invalidJson(valid));
			}
			//persist the info 
			else if (!repo.persist(acred, repoA, repoD)) {
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
	public List<Document> getAccreditation() {
		return  repoD.findAll();
	}

}
