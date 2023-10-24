package com.eviden.reservasalas.mainapp.DTO;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class SalaRequestPut {

	@NonNull
	private Long idSala;
	
	private String nombreSala;

	private Integer capacidad;
}
