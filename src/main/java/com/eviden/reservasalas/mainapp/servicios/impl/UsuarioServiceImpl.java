package com.eviden.reservasalas.mainapp.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eviden.reservasalas.mainapp.modelo.DAO.IUsuarioDAO;
import com.eviden.reservasalas.mainapp.modelo.entity.Reserva;
import com.eviden.reservasalas.mainapp.modelo.entity.Usuario;
import com.eviden.reservasalas.mainapp.servicios.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	IUsuarioDAO usuarioDAO;
	
	
	@Override
	@Transactional
	public Usuario hagoUsuario(Usuario usuario) {
		
		return usuarioDAO.save(usuario);
	}

	@Override
	@Transactional
	public void borroUsuario(Usuario usuario) {
		
		usuarioDAO.delete(usuario);
	}

	@Override
	@Transactional
	public Optional<Usuario> buscoDasUsuario(String dasUsuario) {
		
		return usuarioDAO.findByDasUser(dasUsuario);
	}

	@Override
	@Transactional
	public Optional<Usuario> buscoEmailUsuario(String eMail) {
		
		return usuarioDAO.findByEmail(eMail);
	}

	@Override
	@Transactional
	public List<Usuario> todosUsuarios() {
		
		return usuarioDAO.findAll();
	}

	@Override
	public Optional<Usuario> buscoUserById(Long id) {
		
		return usuarioDAO.findById(id);
	}
}
