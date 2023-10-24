package com.eviden.reservasalas.mainapp.servicios;

import java.util.List;
import java.util.Optional;

import com.eviden.reservasalas.mainapp.modelo.entity.Oficina;

public interface IOficinaService {
	
	public List<Oficina> buscoOficinas();
	
	public Optional<Oficina> buscoIdOficina(Long idOficina);
	
	public Optional<Oficina> buscoIdNombre(String nombreOficina);
	
	public Oficina nuevaOficina(Oficina oficinaNew);
	
	public Oficina actualizoOficina(Oficina oficinaUpdate);
	
	public void borroOficina(Oficina oficina);
	
	
}
