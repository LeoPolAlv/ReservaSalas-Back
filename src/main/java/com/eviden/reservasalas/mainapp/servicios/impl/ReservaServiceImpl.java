package com.eviden.reservasalas.mainapp.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eviden.reservasalas.mainapp.modelo.DAO.IReservasDAO;
import com.eviden.reservasalas.mainapp.modelo.entity.Reserva;
import com.eviden.reservasalas.mainapp.servicios.IReservaService;

@Service
public class ReservaServiceImpl implements IReservaService {

	@Autowired
	IReservasDAO reservaDao;
	
	@Override
	@Transactional
	public List<Reserva> allReservas() {
		
		return reservaDao.findAll();
	}
	
	@Override
	@Transactional
	public Optional<Reserva> buscoReserva(long id) {
		
		return reservaDao.findByIdreserve(id);
	}

	@Override
	@Transactional
	public Reserva generoReserva(Reserva newReserva) {
		
		return reservaDao.save(newReserva);
	}

	@Override
	@Transactional
	public void BorroReserva(Long idreserva) {
		
		reservaDao.deleteById(idreserva);
	}

	

}