package com.eviden.reservasalas.mainapp.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.reservasalas.excepciones.exceptions.BadRequestException;
import com.eviden.reservasalas.excepciones.exceptions.DataNotFoundException;
import com.eviden.reservasalas.mainapp.DTO.UsuarioReqPut;
import com.eviden.reservasalas.mainapp.DTO.UsuarioRequest;
import com.eviden.reservasalas.mainapp.modelo.entity.Oficina;
import com.eviden.reservasalas.mainapp.modelo.entity.Usuario;
import com.eviden.reservasalas.mainapp.servicios.IOficinaService;
import com.eviden.reservasalas.mainapp.servicios.IUsuarioService;

import jakarta.validation.Valid;
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
	public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody UsuarioRequest usuario) {
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
			
			return new ResponseEntity<>(usuarioService.hagoUsuario(usuarioAux), HttpStatus.CREATED);
			
		} catch(DataAccessException e) {
			throw  new BadRequestException("U-003",e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/delete/{dasuser}")
	public ResponseEntity<?> borroUsuario(@PathVariable("dasuser") String dasUser ) {
		log.info("**[RESERVAS]--- Estamos dando de baja un usuario");
		log.info("**[RESERVAS]--- Usuario: " + dasUser);
	
		try {
			Usuario usuarioAux = usuarioService.buscoDasUsuario(dasUser)
					.orElseThrow(() -> new DataNotFoundException("U-004", "El usuario: " + dasUser + " no se encuentra en la BBDD" ));
			 
			usuarioService.borroUsuario(usuarioAux);
			
			return new ResponseEntity<String>("OK",HttpStatus.OK);
		
		}catch(DataAccessException e) {
			throw  new BadRequestException("U-008",e.getMessage());
		}
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
	
	@PutMapping(path = "/update/{idusuario}")
	public ResponseEntity<?> Actualizoreserva(@PathVariable("idusuario") Long idUsuario, @RequestBody UsuarioReqPut usuarioReqPut){
		log.info("**[RESERVAS]--- Estamos actualizando los datos de una oficina. Datos que llegan: ");
		log.info("**[RESERVAS]--- Email: " + usuarioReqPut.getEmail());
		log.info("**[RESERVAS]--- Password: " + usuarioReqPut.getPassword());
		log.info("**[RESERVAS]--- Rol: " + usuarioReqPut.getRol());
		log.info("**[RESERVAS]--- Activar Usuario? : " + usuarioReqPut.isCambioEstado());
		log.info("**[RESERVAS]--- Estado: " + usuarioReqPut.isEstadoUser());
		
		try {
			Usuario usuarioAux = usuarioService.buscoUserById(idUsuario)
					             .orElseThrow(() -> new DataNotFoundException("U-007","El usuario " + idUsuario + " no se encuentra regsitrado en el sistema"));
			
			if(!usuarioReqPut.getEmail().equals("")) {
				usuarioAux.setEmail(usuarioReqPut.getEmail());
			}
			
			if(!usuarioReqPut.getPassword().equals("")) {
				usuarioAux.setPassword(usuarioReqPut.getPassword());
			}
			
			if(!usuarioReqPut.getRol().equals("")) {
				usuarioAux.setRol(usuarioReqPut.getRol());
			}
			//Si el campo activarUser es true significa que vamos a cambiar el estado del usuario 
			if(usuarioReqPut.isCambioEstado()) {
				usuarioAux.setEstadoUser(usuarioReqPut.isEstadoUser());
			}
			return new ResponseEntity<Usuario>(usuarioService.hagoUsuario(usuarioAux),HttpStatus.CREATED) ;
		
		} catch(DataAccessException e) {
			throw  new BadRequestException("U-007",e.getMessage());
		}
	}
	
}
