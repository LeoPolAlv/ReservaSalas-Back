package com.eviden.reservasalas.mainapp.modelo.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.reservasalas.mainapp.modelo.entity.Usuario;

public interface IUsuarioDAO extends JpaRepository<Usuario, Long> {
	
	public Optional<Usuario> findByDasUser(String dasUsuario);
	
	public Optional<Usuario> findByEmail(String email);

}
