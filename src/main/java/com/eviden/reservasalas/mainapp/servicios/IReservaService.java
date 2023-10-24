package com.eviden.reservasalas.mainapp.servicios;

import java.util.List;
import java.util.Optional;

import com.eviden.reservasalas.mainapp.modelo.entity.Reserva;

public interface IReservaService {
	
	public List<Reserva> allReservas();
	
	public Optional<Reserva> buscoReserva(long id);
	
	public Reserva generoReserva(Reserva newReserva);
	
	public void BorroReserva (Long idreserva);
	
	

}
