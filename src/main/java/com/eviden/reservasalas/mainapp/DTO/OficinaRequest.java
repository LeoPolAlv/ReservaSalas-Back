package com.eviden.reservasalas.mainapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OficinaRequest {
	
	//@NotNull(message = "Falta este campo")
	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty(value = "nombre-oficina")
	private String nombreOficina;
	
	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty
	private String direccion;
	
	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty
	private String localidad;
	
	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty(value = "cod-postal")
	private String codPostal;
	
	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty
	private String provincia;
	
	//Coordenas de la ubicacion de la oficina
	// ---- Longitud
	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty
	private String longitud;
	
	//---- Latitud
	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty
	private String latitud;	
	
	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty(value = "nombre-pais")
	private String nombrePais;
}
