package com.eviden.reservasalas.mainapp.servicios;

import java.util.List;
import java.util.Optional;

import com.eviden.reservasalas.mainapp.modelo.entity.ItemsMenu;
import com.eviden.reservasalas.mainapp.modelo.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario hagoUsuario(Usuario usuario);
	
	public void borroUsuario(Usuario usuario);
	
	public Optional<Usuario> buscoDasUsuario(String dasUsuario);
	
	public Optional<Usuario> buscoEmailUsuario(String eMail);
	
	public List<Usuario> todosUsuarios();
	
	public Optional<Usuario> buscoUserById(Long id);
	
	public List<ItemsMenu> menuUsuario(String usuarioDas);

}
