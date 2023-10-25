package com.eviden.reservasalas.mainapp.DTO;

import java.util.Date;

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
public class ReservaRequest {

	//Fecha de la reserva
	@JsonProperty(value = "fecha_reserva")
	@NotEmpty(message = "No debe traer valor nulo")
	private Date fechaReserva;
	
	//Fecha cuando finaliza la reserva.
	@NotEmpty(message = "No debe traer valor nulo")
	@JsonProperty(value = "fecha_hasta")
	private Date fechaHasta;
	
	@NotEmpty(message = "No debe traer valos nulo")
	@JsonProperty
	private String titulo;
	
	@NotEmpty(message = "No debe traer valos nulo")
	@JsonProperty
	private String descripcion;
	
	@NotEmpty(message = "No debe traer valos nulo")
	@JsonProperty(value = "das_user")
	private String dasUser;
	
	@NotEmpty(message = "No debe traer valos nulo")
	@JsonProperty(value = "id_sala")
	private Long idSala;
}
