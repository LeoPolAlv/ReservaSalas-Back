package com.eviden.reservasalas.mainapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaRequest {
	
	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty(value = "nombre-sala")
	private String nombreSala;

	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty
	private Integer capacidad;
	
	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty
	private Long idOficina;
}
