package com.eviden.reservasalas.mainapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UsuarioReqPut {
	
	@JsonProperty
	@Email
	private String email;

	@JsonProperty
	private String password;

	@JsonProperty
	private String rol;
	
	@JsonProperty(value = "cambio-estado")
	private boolean cambioEstado;
	
	@JsonProperty(value = "estado-user")
	private boolean estadoUser;
}
