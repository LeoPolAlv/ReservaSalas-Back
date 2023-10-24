package com.eviden.reservasalas.mainapp.modelo.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.reservasalas.mainapp.modelo.entity.Reserva;

public interface IReservasDAO extends JpaRepository<Reserva, Long> {
	
	public Optional<Reserva> findByIdreserve(Long id);

}
