package com.yieldstreet.accreditation.service;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;


/**
 * Class with the Json structure to respond the accreditation call
 * <p>
 * It informs status of success, accreditation and also the problems that occured
 * @author Milani
 *
 */
public class Return {
	
	@Expose
	protected boolean success;
	@Expose
    protected boolean accredited;
	
	protected String error;
	
	public boolean isSuccess() {
		return success;
	}
	
	protected String accredited() {
		success= true;
		accredited = true;
		return new Gson().toJson(this);
	}
	
		
	protected String successful() {
		success= true;
		accredited = false;
		return new Gson().toJson(this);
	}
	
	/**
	 * This method is used to inform that the process failed
	 * 
	 * @return Json with the status of operation
	 */
	protected String invalidJson(String s) {
		success= false;
		accredited = false;
		error = s;
		return new Gson().toJson(this);
	}
	
	protected String error(String m) {
		success= false;
		accredited = false;
		error = m;
		return new Gson().toJson(this);
	}

}
