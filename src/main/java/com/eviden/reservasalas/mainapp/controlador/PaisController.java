package com.eviden.reservasalas.mainapp.controlador;

import java.util.*;
//import java.util.List;

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
import com.eviden.reservasalas.mainapp.DTO.PaisRequest;
import com.eviden.reservasalas.mainapp.modelo.entity.Oficina;
import com.eviden.reservasalas.mainapp.modelo.entity.Pais;
import com.eviden.reservasalas.mainapp.servicios.IPaisService;

import lombok.extern.log4j.Log4j2;


@RestController
@Log4j2
@RequestMapping(path = "/pais")
public class PaisController {
	
	//private final static Logger logger = LoggerFactory.getLogger(PaisController.class);

	@Autowired
	IPaisService paisService;
	
	@GetMapping(path = "/all")
	public ResponseEntity<?> buscoPaises() {
		
		log.info("**[RESERVAS]--- Estamos buscando el total de paises en BBDD");
		
		List<Pais> listaPaises = paisService.buscoAllPaises();
		
		log.info("**[RESERVAS]--- Lista de paises: " + listaPaises.size());
		if(listaPaises.isEmpty()) {
			throw new DataNotFoundException("P-003", "No tenemos paises inscritos en Base de Datos. Recomendamos dar de alta nuevos paises.");
		}
		
		return new ResponseEntity<List<Pais>>(listaPaises, HttpStatus.OK);
	}
	
	@PostMapping(path="/new")
	public ResponseEntity<?> nuevoPais(@RequestBody PaisRequest nombrePais){
		//Map<String, Object> mensajeEnv = new HashMap<>();
		
		log.info("**[RESERVAS]--- Estamos creando un nuevo Pais");
		log.info("**[RESERVAS]--- Nombre Pais: " + nombrePais.getNombrePais());
		//try {
			Pais nuevoPais = new Pais();
			
			nuevoPais.setNombre(nombrePais.getNombrePais());
			nuevoPais.setOficinas(new HashSet<Oficina>());
			
			return new ResponseEntity<>(paisService.nuevoPais(nuevoPais),HttpStatus.CREATED);
			
		/*} catch (DataAccessException  e) {
			String[] mensaje = e.getMessage().split("ERROR: ");
			log.info("**[RESERVAS]--- Error crear nuevo Pais: " + e );
			mensajeEnv.put("code","P-002");
			mensajeEnv.put("mensaje", mensaje[1]);
			return new ResponseEntity <Map<String,Object>>(mensajeEnv,HttpStatus.BAD_REQUEST);
		}*/
	}
	
	@GetMapping("/find/{nombrePais}")
	public ResponseEntity<?> buscoPais(@PathVariable String nombrePais){
		
		log.info("**[RESERVAS]--- Estamos buscando un Pais");
		log.info("**[RESERVAS]--- Nombre Pais: " + nombrePais);
		
		Pais paisAux = paisService.buscoNombrePais(nombrePais)
				                .orElseThrow(() -> new DataNotFoundException("P-001", nombrePais + " no se encuentra dado de alta en el sistema"));
		
		return new ResponseEntity<>(paisAux, HttpStatus.OK);
	}
	
	@GetMapping("/findId/{idPais}")
	public ResponseEntity<?> buscoPais(@PathVariable Long idPais){
		
		log.info("**[RESERVAS]--- Estamos buscando un Pais");
		log.info("**[RESERVAS]--- ID del Pais: " + idPais);
		
		Pais paisAux = paisService.buscoIdPais(idPais)
				                .orElseThrow(() -> new DataNotFoundException("P-004", "El ID " + idPais + " no se encuentra dado de alta en el sistema"));
		
		return new ResponseEntity<>(paisAux, HttpStatus.OK);
	}
	
	@GetMapping("/oficinas/{nombrePais}")
	public ResponseEntity<?> buscoOficinas(@PathVariable("nombrePais") String nombre){
		
		log.info("**[RESERVAS]--- Estamos buscando oficinas dentro de un pais");
		log.info("**[RESERVAS]--- Nombre del Pais: " + nombre);
		
		Pais paisAux = paisService.buscoNombrePais(nombre)
                .orElseThrow(() -> new DataNotFoundException("P-006", nombre + " no se encuentra dado de alta en el sistema"));
		
		log.info("**[RESERVAS]--- Pais encontrado: " + paisAux.getIdPais());
		log.info("**[RESERVAS]--- Pais encontrado: " + paisAux.getNombre());
		
		Set<Oficina> oficinas = paisAux.getOficinas();
		//List<Oficina> oficinas = paisService.buscoOficinasPorPais(paisAux.getIdPais());
		
		log.info("**[RESERVAS]--- Numero de oficinas encontradas: " + oficinas.size());
		
		if(oficinas.isEmpty()) {
			log.info("**[RESERVAS]--- No existen oficinas para este pais: " + nombre );
			return new ResponseEntity <>("No existen oficinas para este pais: " + nombre, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(oficinas, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{nombrePais}")
	public ResponseEntity<?> borroPais(@PathVariable String nombrePais) {
		Map<String, Object> mensajeEnv = new HashMap<>();
		
		log.info("**[RESERVAS]--- Estamos borrando un Pais");
		log.info("**[RESERVAS]--- Nombre Pais: " + nombrePais);
		try {
			Pais paisAux = paisService.buscoNombrePais(nombrePais).get();
			paisService.borroPais(paisAux);
			return new ResponseEntity <String>("Pais borrado correctamente",HttpStatus.OK);
		} catch (DataAccessException e) {
			log.info("**[RESERVAS]--- Error en la operacion de borrado de: " + nombrePais + "//" + e );
			mensajeEnv.put("code","P-005");
			mensajeEnv.put("mensaje", e.getMessage());
			return new ResponseEntity <Map<String,Object>>(mensajeEnv,HttpStatus.BAD_REQUEST);
		}
		
	}
}
