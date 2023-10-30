package com.eviden.reservasalas.mainapp.servicios;

import java.util.List;
import java.util.Optional;

import com.eviden.reservasalas.mainapp.modelo.entity.Reserva;
import com.eviden.reservasalas.mainapp.modelo.entity.Usuario;

public interface IReservaService {
	
	public List<Reserva> allReservas();
	
	public Optional<Reserva> buscoReserva(long id);
	
	public Reserva generoReserva(Reserva newReserva);
	
	public void BorroReserva (Reserva reserva);
	
	public boolean existeReserva(Long id);
	
	public List<Reserva> ReservasPorUser(Usuario user);

}
