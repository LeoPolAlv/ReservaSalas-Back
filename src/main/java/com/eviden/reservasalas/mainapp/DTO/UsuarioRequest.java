package com.eviden.reservasalas.mainapp.DTO;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {
	
	@NonNull
	private String dasUser;

	@NonNull
	private String email;

	@NonNull
	private String password;

	@NonNull
	private String rol;

	@NonNull
	private Long idOficina;

	/*public Long getIdOficina() {
		// TODO Auto-generated method stub
		return idOficina;
	}*/
}
