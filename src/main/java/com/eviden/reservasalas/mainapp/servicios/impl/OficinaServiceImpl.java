package com.eviden.reservasalas.mainapp.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eviden.reservasalas.mainapp.modelo.DAO.IOficinasDAO;
import com.eviden.reservasalas.mainapp.modelo.entity.Oficina;
import com.eviden.reservasalas.mainapp.servicios.IOficinaService;

@Service
public class OficinaServiceImpl implements IOficinaService {

	@Autowired
	private IOficinasDAO oficinaDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Oficina> buscoOficinas() {
		
		return oficinaDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Oficina> buscoIdOficina(Long idOficina) {

		//return Optional.empty();
		return oficinaDAO.findById(idOficina);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Oficina> buscoIdNombre(String nombreOficina) {

		//return Optional.empty();
		return oficinaDAO.findByNombreOficina(nombreOficina);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Oficina nuevaOficina(Oficina oficinaNew) {
		
		return oficinaDAO.save(oficinaNew);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Oficina actualizoOficina(Oficina oficinaUpdate) {
		
		return oficinaDAO.saveAndFlush(oficinaUpdate);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void borroOficina(Oficina oficina) {
		
		oficinaDAO.delete(oficina);

	}

}
