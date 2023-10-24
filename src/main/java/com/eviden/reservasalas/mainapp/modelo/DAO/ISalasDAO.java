package com.eviden.reservasalas.mainapp.modelo.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eviden.reservasalas.mainapp.modelo.entity.Sala;

public interface ISalasDAO extends JpaRepository<Sala, Long> {

	public Optional<Sala> findByNombreSala(String nombreSala);
	
	public Optional<Sala> findById(Long id);
	
	@Modifying
	@Query("UPDATE VERSIONED Sala s SET s.nombreSala = ?1 WHERE s.idSala = ?2")
	public void actualizoNameSala(String newName, Long id);
	
	@Modifying
	@Query("UPDATE VERSIONED Sala s SET s.capacidad = ?1 WHERE s.idSala = ?2")
	public void actualizoCapSala(Integer capacidad, Long id);
}
