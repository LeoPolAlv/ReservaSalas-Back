package com.eviden.reservasalas.mainapp.servicios;

import java.util.List;
import java.util.Optional;

import com.eviden.reservasalas.mainapp.modelo.entity.Oficina;
import com.eviden.reservasalas.mainapp.modelo.entity.Pais;

public interface IPaisService {

	public List<Pais> buscoAllPaises();
	
	public Optional<Pais> buscoNombrePais(String nombre);
	
	public Optional<Pais> buscoIdPais(Long idPais);
	
	public List<Oficina> buscoOficinasPorPais(Long idPais);
	
	public Pais nuevoPais(Pais datosPais);
	
	public void borroPais(Pais datosPais);
}
