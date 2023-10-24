package com.eviden.reservasalas.excepciones.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataNotFoundException extends RuntimeException{

	private String code;
	
	public DataNotFoundException(String code, String message) {
		super(message);
		this.code = code;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
