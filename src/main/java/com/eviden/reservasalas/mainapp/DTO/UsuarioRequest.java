package com.eviden.reservasalas.mainapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UsuarioRequest {
	
	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty(value = "das-user")
	private String dasUser;

	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty
	@Email
	private String email;

	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty
	private String password;

	@NotEmpty(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty
	private String rol;

	@NotNull(message = "Debe ir informado, no puede ser NULL")
	@JsonProperty(value = "id-oficina")
	private Long idOficina;
}
