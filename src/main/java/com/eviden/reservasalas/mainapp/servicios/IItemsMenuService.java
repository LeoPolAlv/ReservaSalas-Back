package com.eviden.reservasalas.mainapp.servicios;

import java.util.List;
import java.util.Optional;

import com.eviden.reservasalas.mainapp.DTO.TipoMenu;
import com.eviden.reservasalas.mainapp.modelo.entity.ItemsMenu;
//import com.eviden.reservasalas.mainapp.modelo.entity.Usuario;

public interface IItemsMenuService {
	
	public Optional<List<ItemsMenu>> itemsPorTipo(TipoMenu tipo);
	
	public Optional<List<ItemsMenu>> itemsPorIdPadre(int idItem);

}
