package com.eviden.reservasalas.mainapp.modelo.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.reservasalas.mainapp.modelo.entity.Oficina;

public interface IOficinasDAO extends JpaRepository<Oficina, Long> {
	
	public Optional<Oficina> findByNombreOficina(String nombreOficina);

}
