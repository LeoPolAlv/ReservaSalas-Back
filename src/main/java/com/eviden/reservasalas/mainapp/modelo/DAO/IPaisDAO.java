package com.eviden.reservasalas.mainapp.modelo.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.reservasalas.mainapp.modelo.entity.Oficina;
import com.eviden.reservasalas.mainapp.modelo.entity.Pais;

public interface IPaisDAO extends JpaRepository<Pais, Long> {

	public Optional <Pais> findByNombre(String nombre);
	
	public List<Oficina> findOficinasByIdPais(Long id);
}
