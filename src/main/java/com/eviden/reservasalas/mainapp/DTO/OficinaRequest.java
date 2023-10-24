package com.eviden.reservasalas.mainapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OficinaRequest {
	
	@NonNull
	private String nombreOficina;
	
	@NonNull
	private String direccion;
	
	@NonNull
	private String localidad;
	
	@NonNull
	private String codPostal;
	
	@NonNull
	private String provincia;
	
	//Coordenas de la ubicacion de la oficina
	// ---- Longitud
	@NonNull
	private String longitud;
	
	//---- Latitud
	@NonNull
	private String latitud;	
	
	@NonNull
	private String nombrePais;
}
