package com.eviden.reservasalas.mainapp.modelo.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eviden.reservasalas.mainapp.modelo.entity.Reserva;
import com.eviden.reservasalas.mainapp.modelo.entity.Usuario;

public interface IReservasDAO extends JpaRepository<Reserva, Long> {
	
	public Optional<Reserva> findByIdreserve(Long id);
	
	@Query("SELECT r FROM Reserva r WHERE usuario = ?1")
	public List<Reserva>findReservasByDasUser(Usuario usuario);

}
