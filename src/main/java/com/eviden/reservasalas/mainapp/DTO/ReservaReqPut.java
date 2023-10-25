package com.eviden.reservasalas.mainapp.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReservaReqPut {
	
	@JsonProperty(value = "fecha-reserva")
	private Date fechaReserva;
	
	//Fecha cuando finaliza la reserva.
	@JsonProperty(value = "fecha-hasta")
	private Date fechaHasta;
	
	@JsonProperty
	private String titulo;
	
	@JsonProperty
	private String descripcion;
}
