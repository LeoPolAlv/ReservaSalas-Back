package com.eviden.reservasalas.mainapp.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eviden.reservasalas.mainapp.DTO.TipoMenu;
import com.eviden.reservasalas.mainapp.modelo.DAO.IItemsMenuDAO;
import com.eviden.reservasalas.mainapp.modelo.entity.ItemsMenu;
//import com.eviden.reservasalas.mainapp.modelo.entity.Usuario;
import com.eviden.reservasalas.mainapp.servicios.IItemsMenuService;

@Service
public class ItemsMenuServiceImpl implements IItemsMenuService {

	@Autowired
	IItemsMenuDAO itemsMenuDao;

	@Override
	public Optional<List<ItemsMenu>> itemsPorTipo(TipoMenu tipo) {
		
		return itemsMenuDao.findByTipoMenu(tipo);
	}

	@Override
	public Optional<List<ItemsMenu>> itemsPorIdPadre(int idItem) {
		
		return itemsMenuDao.findByIdItemPadre(idItem);
	}
	
	

}
