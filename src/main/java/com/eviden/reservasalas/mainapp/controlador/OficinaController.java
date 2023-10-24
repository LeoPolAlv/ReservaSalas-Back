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
import com.eviden.reservasalas.mainapp.DTO.OficinaRequest;
import com.eviden.reservasalas.mainapp.modelo.entity.Oficina;
import com.eviden.reservasalas.mainapp.modelo.entity.Pais;
import com.eviden.reservasalas.mainapp.servicios.IOficinaService;
import com.eviden.reservasalas.mainapp.servicios.IPaisService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(path = "/oficina")
@Log4j2
public class OficinaController {

	@Autowired
	private IOficinaService oficinaService;
	
	@Autowired
	private IPaisService paisService;
	
	@GetMapping(path = "/all")
	public ResponseEntity<?> buscoPaises() {
		log.info("**[RESERVAS]--- Estamos buscando el total de oficinas en BBDD");
		
		List<Oficina> listaOficinas = oficinaService.buscoOficinas();
		log.info("**[RESERVAS]--- Oficinas " + listaOficinas.size() + " encontradas");
		if(listaOficinas.isEmpty()) {
			throw new DataNotFoundException("O-001", "No tenemos oficinas inscritas en Base de Datos. Recomendamos dar de alta nuevas oficinas.");
		}
		
		return new ResponseEntity<List<Oficina>>(listaOficinas, HttpStatus.OK);
	}
	
	@GetMapping(path = "/findn/{nOficina}")
	public ResponseEntity<?> buscoNombreOficina(@PathVariable("nOficina") String nombre){
		log.info("**[RESERVAS]--- Estamos buscando Oficina por nombre: " + nombre);
		
		Oficina oficinaAux = oficinaService.buscoIdNombre(nombre)
				.orElseThrow(() -> new DataNotFoundException("O-005", nombre + " no se encuentra dada de alta en el sistema como oficina"));
		
		log.info("**[RESERVAS]--- Oficina encontrada: " + oficinaAux);
		
		return new ResponseEntity<>(oficinaAux,HttpStatus.OK);
	}
	
	@GetMapping(path = "/findid/{idOficina}")
	public ResponseEntity<?> buscoIdOficina(@PathVariable("idOficina") Long id){
		
		log.info("**[RESERVAS]--- Estamos buscando Oficina por ID: " + id);
		
		Oficina oficinaAux = oficinaService.buscoIdOficina(id)
				.orElseThrow(() -> new DataNotFoundException("O-006", "No se encuentra ninguna oficina con este ID: " + id));
		
		log.info("**[RESERVAS]--- Oficina encontrada: " + oficinaAux);
		
		return new ResponseEntity<>(oficinaAux,HttpStatus.OK);
	}
	
	@PostMapping(path = "/new")
	public ResponseEntity<?> nuevaOficina(@RequestBody OficinaRequest oficinaNueva){
		Map<String, Object> mensajeEnv = new HashMap<>();
		
		log.info("**[RESERVAS]--- Estamos creando una oficina nueva");
		log.info("**[RESERVAS]--- Nombre Oficina: " + oficinaNueva.getNombreOficina());
		
		try {
			Oficina oficinaAux = new Oficina();
			
			//Buscamos los datos del pais de la oficina. Si no existe en BBDD se envia excepcion
			Pais PaisAux = paisService.buscoNombrePais(oficinaNueva.getNombrePais())
					.orElseThrow(() -> new DataNotFoundException("O-002", oficinaNueva.getNombrePais() + " no se encuentra dada de alta en el sistema como pais"));
			
			// Seteamos la clase Oficina con los valores que nos vienen en el body de la request.
			oficinaAux.setNombreOficina(oficinaNueva.getNombreOficina());
			oficinaAux.setDireccion(oficinaNueva.getDireccion());
			oficinaAux.setCodPostal(oficinaNueva.getCodPostal());
			oficinaAux.setLocalidad(oficinaNueva.getLocalidad());
			oficinaAux.setProvincia(oficinaNueva.getProvincia());
			oficinaAux.setLatitud(oficinaNueva.getLatitud());
			oficinaAux.setLongitud(oficinaNueva.getLongitud());
			oficinaAux.setPais(PaisAux);
			oficinaAux.setSalas(null);
			oficinaAux.setUsers(null);
			
			//Persistimos en BBDD el valor seteado y enviamos el nuevo registro a la response.
			return new ResponseEntity<>(oficinaService.nuevaOficina(oficinaAux), HttpStatus.OK);
			
		} catch (DataAccessException e) {
			String[] mensaje = e.getMessage().split("ERROR: ");
			log.info("**[RESERVAS]--- Error crear nueva Oficina: " + e );
			mensajeEnv.put("code","O-003");
			mensajeEnv.put("mensaje", mensaje[1]);
			return new ResponseEntity <Map<String,Object>>(mensajeEnv,HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/delete/{oficina}")
	public ResponseEntity<?> borroOficina(@PathVariable("oficina") String nombreOficina) {
		Map<String, Object> mensajeEnv = new HashMap<>();
		
		log.info("**[RESERVAS]--- Nombre de la oficina a borrar: " + nombreOficina );
		
		try {
			Oficina oficinaAux = oficinaService.buscoIdNombre(nombreOficina)
					      .orElseThrow(() -> new DataNotFoundException("O-004", nombreOficina + " no se encuentra dado de alta en el sistema como oficina"));
			
			oficinaService.borroOficina(oficinaAux);
			return new ResponseEntity <String>("Oficina borrada correctamente",HttpStatus.OK);
		} catch (DataAccessException e) {
			String[] mensaje = e.getMessage().split("ERROR: ");
			log.info("**[RESERVAS]--- Error al borrar una Oficina: " + e );
			mensajeEnv.put("code","O-007");
			mensajeEnv.put("mensaje", mensaje[1]);
			return new ResponseEntity <Map<String,Object>>(mensajeEnv,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/usuarios/{oficina}")
	public ResponseEntity<?> usuariosPorOficina(@PathVariable("oficina") String nombre){
		log.info("**[RESERVAS]--- Estamos buscando los usuarios de una Oficina: " + nombre);
		Oficina oficinaAux = oficinaService.buscoIdNombre(nombre)
				.orElseThrow(() -> new DataNotFoundException("O-008", nombre + " no se encuentra dada de alta en el sistema como oficina"));
		return new ResponseEntity<>(oficinaAux.getUsers(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/salas/{oficina}")
	public ResponseEntity<?> salasPorOficina(@PathVariable("oficina") String nombre){
		log.info("**[RESERVAS]--- Estamos buscando las salas de una Oficina: " + nombre);
		Oficina oficinaAux = oficinaService.buscoIdNombre(nombre)
				.orElseThrow(() -> new DataNotFoundException("O-009", nombre + " no se encuentra dada de alta en el sistema como oficina"));
		return new ResponseEntity<>(oficinaAux.getSalas(), HttpStatus.OK);
	}
}
