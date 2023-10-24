package com.eviden.reservasalas.excepciones.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosEntradaException extends RuntimeException {

	private String code;
	
	public DatosEntradaException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
