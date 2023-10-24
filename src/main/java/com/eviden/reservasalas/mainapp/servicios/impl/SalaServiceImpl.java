package com.eviden.reservasalas.mainapp.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eviden.reservasalas.mainapp.modelo.DAO.ISalasDAO;
import com.eviden.reservasalas.mainapp.modelo.entity.Sala;
import com.eviden.reservasalas.mainapp.servicios.ISalaService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SalaServiceImpl implements ISalaService {

	@Autowired
	ISalasDAO salaDao;
	
	@Override
	@Transactional
	public Sala hagoSala(Sala sala) {
		
		return salaDao.save(sala);
	}

	@Override
	@Transactional
	public void borroSala(Sala sala) {
		
		salaDao.delete(sala);
	}

	@Override
	@Transactional
	public List<Sala> buscoSalas() {
		
		return salaDao.findAll();
	}

	@Override
	@Transactional
	public Optional<Sala> buscoSalaNombre(String nombreSala) {
		
		return salaDao.findByNombreSala(nombreSala);
	}

	@Override
	@Transactional
	public Optional<Sala> buscoSaldaId(Long id) {
		
		return salaDao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Optional<Sala> actualizoSala(String name, Integer capacidad, Long id) {
		log.info("PARAMETROS ENTRADA *************");
		System.out.println("ID: " + id);
		System.out.println("Nombre: " + name);
		System.out.println("Capacidad: " + capacidad);
		
		if(name != null && name != "") {
		   salaDao.actualizoNameSala(name, id);
		
		}
		if(capacidad != 0 && capacidad != null) {
			salaDao.actualizoCapSala(capacidad, id);
		
		}
		return salaDao.findById(id);
	}

}
