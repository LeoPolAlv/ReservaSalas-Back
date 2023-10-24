package com.eviden.reservasalas.mainapp.servicios;

import java.util.List;
import java.util.Optional;

import com.eviden.reservasalas.mainapp.modelo.entity.Sala;

public interface ISalaService {

	public Sala hagoSala(Sala sala);
	
	public Optional<Sala> actualizoSala(String name, Integer capacidad, Long id);
	
	public void borroSala( Sala sala);
	
	public List<Sala> buscoSalas();
	
	public Optional<Sala> buscoSaldaId( Long id);
	
	public Optional<Sala> buscoSalaNombre(String nombreSala);
}
