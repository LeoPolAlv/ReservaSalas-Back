package com.eviden.reservasalas.mainapp.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.reservasalas.excepciones.exceptions.DataNotFoundException;
import com.eviden.reservasalas.mainapp.DTO.UsuarioRequest;
import com.eviden.reservasalas.mainapp.modelo.entity.Oficina;
import com.eviden.reservasalas.mainapp.modelo.entity.Usuario;
import com.eviden.reservasalas.mainapp.servicios.IOficinaService;
import com.eviden.reservasalas.mainapp.servicios.IUsuarioService;

import lombok.extern.log4j.*;



@RestController
@RequestMapping(path = "/usuario")
@Log4j2
public class UsuarioController {

	//private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(UsuarioController.class);
	
	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	IOficinaService oficinaService;
	
	@GetMapping(path = "/all")
	public ResponseEntity<?> buscoPaises() {
		log.info("**[RESERVAS]--- Estamos buscando el total de Usuarios en BBDD");
		
		List<Usuario> listaUsuarios = usuarioService.todosUsuarios();
		
		log.info("**[RESERVAS]--- Lista de Usuarios: " + listaUsuarios.size());
		if(listaUsuarios.isEmpty()) {
			throw new DataNotFoundException("U-001", "No tenemos usuarios inscritos en Base de Datos. Recomendamos dar de alta nuevos usuarios en BBDD.");
		}
		
		return new ResponseEntity<List<Usuario>>(listaUsuarios, HttpStatus.OK);
	}
	
	@PostMapping(path = "/new")
	public ResponseEntity<?> nuevoUsuario(@RequestBody UsuarioRequest usuario) {
		Map<String, Object> mensajeEnv = new HashMap<>();
		
		log.info("**[RESERVAS]--- Estamos insertando un nuevo usuario");
		log.info("**[RESERVAS]--- Usuario: " + usuario.toString());
		
		try {
			
			//Buscamos el objeto oficina donde esta asignado el usuario
			Oficina oficinaAux = oficinaService.buscoIdOficina(usuario.getIdOficina())
					   .orElseThrow(() -> new DataNotFoundException("U-002", "No se encuentra ninguna oficina con este ID: " + usuario.getIdOficina()));
			
			Usuario usuarioAux = Usuario.builder()
					.dasUser(usuario.getDasUser())
					.email(usuario.getEmail())
					.password(usuario.getPassword())
					.rol(usuario.getRol())
					.estadoUser(true)
					.userOficina(oficinaAux)
					.reserves(null)
					.build();
			
			return new ResponseEntity<>(usuarioService.hagoUsuario(usuarioAux), HttpStatus.OK);
			
		} catch (DataAccessException e) {
			String[] mensaje = e.getMessage().split("ERROR: ");
			log.info("**[RESERVAS]--- Error crear al insertar el usuario en BBDD: " + e );
			mensajeEnv.put("code","U-003");
			mensajeEnv.put("mensaje", mensaje[1]);
			return new ResponseEntity <Map<String,Object>>(mensajeEnv,HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/delete/{dasUser}")
	public void borroUsuario(@PathVariable("dasUser") String dasUser ) {
		log.info("**[RESERVAS]--- Estamos dando de baja un usuario");
		log.info("**[RESERVAS]--- Usuario: " + dasUser);
		
		Usuario usuarioAux = usuarioService.buscoDasUsuario(dasUser)
				.orElseThrow(() -> new DataNotFoundException("U-004", "El usuario: " + dasUser + " no se encuentra en la BBDD" ));
		
		usuarioService.borroUsuario(usuarioAux);
	}
	
	@GetMapping(path="/findU/{dasUser}")
	public ResponseEntity<?> buscoUsuarioDas(@PathVariable("dasUser") String dasUser ) {
		log.info("**[RESERVAS]--- Estamos buscando un usuario por su Das");
		log.info("**[RESERVAS]--- Usuario: " + dasUser);
		
		Usuario usuarioAux = usuarioService.buscoDasUsuario(dasUser)
				.orElseThrow(() -> new DataNotFoundException("U-005", "El usuario: " + dasUser + " no se encuentra en la BBDD" ));
		
		return new ResponseEntity<>(usuarioAux, HttpStatus.OK);
		
	}
	
	@GetMapping(path="/findE/{email}")
	public ResponseEntity<?> buscoUsuarioEmail(@PathVariable("email") String email ) {
		log.info("**[RESERVAS]--- Estamos buscando un usuario por su email");
		log.info("**[RESERVAS]--- Email usuario: " + email);
		
		Usuario usuarioAux = usuarioService.buscoEmailUsuario(email)
				.orElseThrow(() -> new DataNotFoundException("U-006", "El Email: " + email + " no se encuentra en la BBDD" ));
		
		return new ResponseEntity<>(usuarioAux, HttpStatus.OK);
		
	}
	
}
