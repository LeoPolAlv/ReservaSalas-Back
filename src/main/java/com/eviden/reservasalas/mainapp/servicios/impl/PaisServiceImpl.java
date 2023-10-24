package com.eviden.reservasalas.mainapp.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eviden.reservasalas.mainapp.modelo.DAO.IPaisDAO;
import com.eviden.reservasalas.mainapp.modelo.entity.Oficina;
import com.eviden.reservasalas.mainapp.modelo.entity.Pais;
import com.eviden.reservasalas.mainapp.servicios.IPaisService;

@Service
public class PaisServiceImpl implements IPaisService {

	@Autowired
	private IPaisDAO paisDAO;
	
	@Override
	@Transactional()
	public List<Pais> buscoAllPaises() {
		
		return paisDAO.findAll();
	}

	@Override
	@Transactional()
	public Optional<Pais> buscoNombrePais(String nombre) {
		
		return paisDAO.findByNombre(nombre);
	}
	
	@Override
	@Transactional()
	public Optional<Pais> buscoIdPais(Long idPais) {
		
		return paisDAO.findById(idPais);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public List<Oficina> buscoOficinasPorPais(Long idPais) {
		
		return paisDAO.findOficinasByIdPais(idPais);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Pais nuevoPais(Pais datosPais) {
		
		return paisDAO.save(datosPais) ;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void borroPais(Pais datosPais) {
		
		paisDAO.delete(datosPais);
	}
}
