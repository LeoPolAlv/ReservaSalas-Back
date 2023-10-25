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
import com.eviden.reservasalas.mainapp.DTO.ReservaReqPut;
import com.eviden.reservasalas.mainapp.DTO.ReservaRequest;
import com.eviden.reservasalas.mainapp.modelo.entity.Reserva;
import com.eviden.reservasalas.mainapp.modelo.entity.Sala;
import com.eviden.reservasalas.mainapp.modelo.entity.Usuario;
import com.eviden.reservasalas.mainapp.servicios.IReservaService;
import com.eviden.reservasalas.mainapp.servicios.ISalaService;
import com.eviden.reservasalas.mainapp.servicios.IUsuarioService;

import lombok.extern.log4j.Log4j2;
import jakarta.validation.*;


@RestController()
@RequestMapping(path = "/reserva")
@Log4j2
public class ReservaController {

	@Autowired
	IReservaService reservaService;
	
	@Autowired
	ISalaService salaService;
	
	@Autowired
	IUsuarioService usuarioService;
	
	
	
	@GetMapping(path = "/find/all")
	public ResponseEntity<?> buscoAllReservas(){
		log.info("**[RESERVAS]--- Estamos buscando una reserva");
		
		List<Reserva> reservas_aux = reservaService.allReservas();
		if(reservas_aux.isEmpty()) {
			throw new DataNotFoundException("R-002","No hay reservas en la BBDD");
		}
                 
		return new ResponseEntity<>(reservas_aux, HttpStatus.OK);
		
	}
	
	@GetMapping(path = "/find/{id}")
	public ResponseEntity<?> buscoReserva(@PathVariable Long id){
		log.info("**[RESERVAS]--- Estamos buscando una reserva");
		
		Reserva reserva_aux = reservaService.buscoReserva(id)
                .orElseThrow(() -> new DataNotFoundException("R-001","Reserva no encontrada en BBDD"));
		
		log.info("**[RESERVAS]--- Hemos encontrado esta reserva " + reserva_aux.getTitulo());
		log.info("**[RESERVAS]--- Hemos encontrado esta reserva " + reserva_aux.getDescripcion());
		
		return new ResponseEntity<>(reserva_aux, HttpStatus.OK);
		
	}
	
	@PostMapping(path = "/new")
	public ResponseEntity<?> nuevaReserva(@Valid @RequestBody ReservaRequest reservaRequest){
		log.info("**[RESERVAS]--- Estamos Creando una nueva reserva");
		try {
			//Buscamos datos de la sala que queremos reservar
			Sala salaReserva = salaService.buscoSaldaId(reservaRequest.getIdSala())
					.orElseThrow(() -> new DataNotFoundException("R-003","Esta sala " + reservaRequest.getIdSala() + " no esta dado de alta en el sistema"));
			
			//Buscamos los datos del usuario que reserva la sala.
			Usuario userReserva = usuarioService.buscoDasUsuario(reservaRequest.getDasUser())
					.orElseThrow( () -> new DataNotFoundException("R-004","Este usuario " + reservaRequest.getDasUser() + " no esta dado de alta en el sistema"));
			
			//Si todo va bien creamos la reserva.
			Reserva newReserva = Reserva.builder()
					.fechaReserva(reservaRequest.getFechaReserva())
					.fechaHasta(reservaRequest.getFechaHasta())
					.titulo(reservaRequest.getTitulo())
					.descripcion(reservaRequest.getDescripcion())
					.activa(true)
					.sala(salaReserva)
					.usuario(userReserva)
					.build();
			
			return new ResponseEntity<Reserva>(reservaService.generoReserva(newReserva), HttpStatus.CREATED);
		
		}catch (DataAccessException e) {
			throw  new BadRequestException("R-005",e.getMessage());
		}
	}
	
	@PutMapping(path = "/update/{idreserva}")
	public ResponseEntity<?> Actualizoreserva(@PathVariable("idreserva") Long idReserva, @RequestBody ReservaReqPut reservaReqPut){
		log.info("**[RESERVAS]--- Estamos actualizando una reserva. Datos que llegan: ");
		log.info("**[RESERVAS]--- Fecha Reserva: " + reservaReqPut.getFechaReserva());
		log.info("**[RESERVAS]--- Fecha Hasta: " + reservaReqPut.getFechaHasta());
		log.info("**[RESERVAS]--- Titulo: " + reservaReqPut.getTitulo());
		log.info("**[RESERVAS]--- Descripcion: " + reservaReqPut.getDescripcion());
		
		try {
			Reserva reservaAux = reservaService.buscoReserva(idReserva)
					             .orElseThrow(() -> new DataNotFoundException("R-006","Reserva " + idReserva + " no encontrada en BBDD"));
			
			if(reservaReqPut.getFechaReserva() != null) {
				reservaAux.setFechaReserva(reservaReqPut.getFechaReserva());
			}
			
			if(reservaReqPut.getFechaHasta() != null) {
				reservaAux.setFechaHasta(reservaReqPut.getFechaHasta());
			}
			
			if(reservaReqPut.getFechaReserva() != null && reservaReqPut.getFechaHasta() == null) {
				throw  new BadRequestException("R-006","Se debe indicar inicio y fin de la reserva");
			}
			
			if(!reservaReqPut.getTitulo().equals("")) {
				reservaAux.setTitulo(reservaReqPut.getTitulo());
			}
			
			if(!reservaReqPut.getDescripcion().equals("")) {
				reservaAux.setDescripcion(reservaReqPut.getDescripcion());
			}
					
			return new ResponseEntity<Reserva>(reservaService.generoReserva(reservaAux),HttpStatus.CREATED) ;
		
		} catch(DataAccessException e) {
			throw  new BadRequestException("R-007",e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/delete/{idreserva}")
	public ResponseEntity<?> borroReserva(@PathVariable("idreserva") Long idReserva){
		log.info("**[RESERVAS]--- Vamos a borrar la reserva Nº " + idReserva);
		
		try {
			Reserva reservaAux = reservaService.buscoReserva(idReserva)
		             .orElseThrow(() -> new DataNotFoundException("R-008","Reserva " + idReserva + " no encontrada en BBDD"));
			
			reservaService.BorroReserva(reservaAux);
			
			return new ResponseEntity<>(reservaAux, HttpStatus.NO_CONTENT);
			
		} catch(DataAccessException e) {
			throw  new BadRequestException("R-009",e.getMessage());
		}
	}
}
