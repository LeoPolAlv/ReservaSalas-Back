package com.eviden.reservasalas.mainapp.modelo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.reservasalas.mainapp.DTO.TipoMenu;
import com.eviden.reservasalas.mainapp.modelo.entity.ItemsMenu;
import java.util.List;
import java.util.Optional;
//import com.eviden.reservasalas.mainapp.modelo.entity.Usuario;


public interface IItemsMenuDAO extends JpaRepository<ItemsMenu,Integer>{
	
	public Optional<List<ItemsMenu>> findByTipoMenu(TipoMenu tipoMenu);
	
	public Optional<List<ItemsMenu>> findByIdItemPadre(int id);

}
