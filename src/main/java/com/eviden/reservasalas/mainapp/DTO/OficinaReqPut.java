package com.eviden.reservasalas.mainapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class OficinaReqPut {
	
	@JsonProperty(value = "nombre-oficina")
	private String nombreOficina;
	
	@JsonProperty
	private String direccion;
	
	@JsonProperty
	private String localidad;
	
	@JsonProperty(value = "cod-postal")
	private String codPostal;
	
	@JsonProperty
	private String provincia;
	
	@JsonProperty
	private String longitud;
	
	@JsonProperty
	private String latitud;	
}
