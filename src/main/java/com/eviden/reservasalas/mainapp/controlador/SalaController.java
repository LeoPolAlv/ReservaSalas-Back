package com.eviden.reservasalas.mainapp.controlador;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
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
import com.eviden.reservasalas.mainapp.DTO.SalaRequest;
import com.eviden.reservasalas.mainapp.DTO.SalaRequestPut;
import com.eviden.reservasalas.mainapp.modelo.entity.Oficina;
import com.eviden.reservasalas.mainapp.modelo.entity.Sala;
import com.eviden.reservasalas.mainapp.servicios.IOficinaService;
import com.eviden.reservasalas.mainapp.servicios.ISalaService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(path = "/salas")
@Log4j2
public class SalaController {

	@Autowired
	ISalaService salaService;
	
	@Autowired
	IOficinaService oficinaService;
	
	@GetMapping(path = "/all")
	public ResponseEntity<?> buscarSalas(){
		log.info("**[RESERVAS]--- Estamos buscando el total de Salas en BBDD");
		
		List<Sala> allSalas = salaService.buscoSalas();
		log.info("**[RESERVAS]--- Tenemos " + allSalas.size() + " salas en BBDD");
		
		if (allSalas.isEmpty()) {
			throw new DataNotFoundException("S-001","No se ha encontrado ninguna sala en BBDD. Inserte nuevas salas en cada oficina");
		}
		return new ResponseEntity<List<Sala>>(allSalas, HttpStatus.OK);
		
	}
	/*
	 * Se busca una sala por su nombre que nos llega como parametro en la URL
	 */
	@GetMapping(path = "/name/{nombresala}")
	public ResponseEntity<?> buscarSala(@PathVariable String nombresala){
		log.info("**[RESERVAS]--- Estamos buscando una sala en BBDD");
		log.info("**[RESERVAS]--- La sala a busca es: " + nombresala);
		
		Sala sala = salaService.buscoSalaNombre(nombresala.toLowerCase())
				              .orElseThrow(() -> new DataNotFoundException("S-007","La sala '" + nombresala + "' no se encontra en la BBDD"));
		
		return new ResponseEntity<Sala>(sala, HttpStatus.OK);
		
	}
	
	/*
	 * Se busca una sala por su id que nos llega como parametro en la URL
	 */
	@GetMapping(path = "/id/{idsala}")
	public ResponseEntity<?> buscarSalaId(@PathVariable Long idsala){
		log.info("**[RESERVAS]--- Estamos buscando una sala en BBDD");
		
		Sala sala = salaService.buscoSaldaId(idsala)
				              .orElseThrow(() -> new DataNotFoundException("S-008","El ID sala " + idsala + " no se encontra en la BBDD"));
		
		return new ResponseEntity<Sala>(sala, HttpStatus.OK);
		
	}
	
	@PostMapping(path = "/new")
	public ResponseEntity<?> crearSala(@Valid @RequestBody SalaRequest nuevaSala) {
		log.info("**[RESERVAS]--- Estamos Insertando una nueva sala");
		
		//Validamos que la sala no exita en BBDD
		Optional<Sala> findSala = salaService.buscoSalaNombre(nuevaSala.getNombreSala());
		
		if(!findSala.isEmpty()) {
			 throw new BadRequestException("S-006","Ya existe una sala con ese nombre");
		}
		
		try {
			JSONObject returnObj = new JSONObject();
			//Buscamos el objeto oficina al que damos de alta la sala
			Oficina oficinaAux = oficinaService.buscoIdOficina(nuevaSala.getIdOficina())
					.orElseThrow(() -> new DataNotFoundException("S-002", "No se encuentra ninguna Oficina con este ID: " + nuevaSala.getIdOficina()));
			
			log.info("**[RESERVAS]--- La sala se la estamos asignando a la oficina " + oficinaAux.getNombreOficina());
			
			//Construimos el objeto Sala
			Sala salaAux = Sala.builder()
					.nombreSala(nuevaSala.getNombreSala().toLowerCase())
					.capacidad(nuevaSala.getCapacidad())
					.salaOficina(oficinaAux)
					.build();
			returnObj.put("NombreSala", salaAux.getNombreSala());
			returnObj.put("Capacidad", salaAux.getCapacidad());
			returnObj.put("Oficina", salaAux.getSalaOficina().getNombreOficina());
			log.info("**[RESERVAS]--- Vamos a insertar esta sala: " + returnObj.toString());
			
			//Persisitimos en BBDD 
			return new ResponseEntity<>(salaService.hagoSala(salaAux),HttpStatus.CREATED); 
			
		}catch (DataAccessException e) {
			throw  new BadRequestException("S-003",e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/delete/{idsala}")
	public ResponseEntity<?> borroSala(@PathVariable("idsala") Long id){
		log.info("**[RESERVAS]--- Vamos a borrar una sala ");
		
		try {
			Sala salaAux = salaService.buscoSaldaId(id)
					.orElseThrow(() -> new DataNotFoundException("S-004", "No se encuentra ninguna sala con este ID: " + id));
			
			log.info("**[RESERVAS]--- Sala a borrar: " + salaAux.getNombreSala() + " de la oficina: " + salaAux.getSalaOficina().getNombreOficina());
			
			//Borramos de BBDD la sala que se nos solicita
			salaService.borroSala(salaAux);
			//Devolvemos un OK si todo fue bien
			return new ResponseEntity<String>("Sala borrada correctamente", HttpStatus.CREATED);  
		}catch (DataAccessException e) {
			throw  new BadRequestException("S-005",e.getMessage());
		}
	}
	/*
	 * Actualizacion de los datos de una sala.
	 * Solo se pueden modificar los campos:
	 *  	- Nombre sala
	 *  	- Capacidad
	 */
	@PutMapping(path = "/update/{idsala}")
	public ResponseEntity<?> actualizoSala(@PathVariable("idsala") Long idSala, @RequestBody SalaRequestPut salaUpdate) {
		log.info("**[RESERVAS]--- Estamos actualizando la sala con ID: " + idSala);
		log.info("**[RESERVAS]--- Estamos actualizando la sala: " + salaUpdate.getNombreSala());
		log.info("**[RESERVAS]--- Estamos actualizando la sala con capacidad: " + salaUpdate.getCapacidad());
		
		try {
			
			//Validamos que la sala exista en BBDD
			Sala salaAux = salaService.buscoSaldaId(idSala)
						.orElseThrow(() -> new DataNotFoundException("S-011", "Esta sala " + idSala + "No esta registrada en el sistema"));
		
			if(!salaUpdate.getNombreSala().equals("") && !salaUpdate.getNombreSala().equals(null)) {
				salaAux.setNombreSala(salaUpdate.getNombreSala());
			}
			
			if(!salaUpdate.getCapacidad().equals(0) && !salaUpdate.getCapacidad().equals(null)) {
				salaAux.setCapacidad(salaUpdate.getCapacidad());
			}
			
			//Persisitimos en BBDD 
			return new ResponseEntity<>(salaService.hagoSala(salaAux),HttpStatus.CREATED); 
			
		}catch (DataAccessException e) {
			throw  new BadRequestException("S-010",e.getMessage());
		}
	}
}
