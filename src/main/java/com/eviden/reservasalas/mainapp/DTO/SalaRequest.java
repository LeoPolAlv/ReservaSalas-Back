package com.eviden.reservasalas.mainapp.DTO;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class SalaRequest {
	
	@NonNull
	private String nombreSala;

	@NonNull
	private Integer capacidad;
	
	@NonNull
	private Long idOficina;
}
