package com.eviden.reservasalas.mainapp.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequest {

	//Fecha de la reserva
	@JsonProperty
	@NonNull
	private Date fechaReserva;
	
	//Fecha cuando finaliza la reserva.
	@NonNull
	@JsonProperty
	private Date fechaHasta;
	
	@NonNull
	@JsonProperty
	private String titulo;
	
	@NonNull
	@JsonProperty
	private String descripcion;
	
	@NonNull
	@JsonProperty
	private String dasUser;
	
	@NonNull
	@JsonProperty
	private Long idSala;
}
