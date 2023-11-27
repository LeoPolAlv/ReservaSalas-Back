package com.eviden.reservasalas.mainapp.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.reservasalas.excepciones.exceptions.DataNotFoundException;
import com.eviden.reservasalas.mainapp.DTO.ItemMenuResponce;
import com.eviden.reservasalas.mainapp.DTO.TipoMenu;
import com.eviden.reservasalas.mainapp.helper.ItemsMenuHelper;
import com.eviden.reservasalas.mainapp.modelo.entity.ItemsMenu;
import com.eviden.reservasalas.mainapp.servicios.IItemsMenuService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(path = "/items")
@Log4j2
public class ItemsmenuController {
	
	@Autowired
	IItemsMenuService itemsMenuService;
	
	@Autowired
	ItemsMenuHelper menuHelper;
	
	@GetMapping(path="/tipo")
	public ResponseEntity<?> cargoItemsUsuario(@RequestParam("tipo") TipoMenu tipo ) {
		log.info("**[RESERVAS]--- Estamos cargando el menu por tipo.");
		log.info("**[RESERVAS]--- Tipo Menu: " + tipo);
		List<ItemMenuResponce> itemsM = new ArrayList<ItemMenuResponce>();
		//ArrayList<ItemMenuResponce> itemX = new ArrayList<ItemMenuResponce>();
		
		List<ItemsMenu> itemsAux = itemsMenuService.itemsPorTipo(tipo)
				.orElseThrow(() -> new DataNotFoundException("IT-001", "El tipo: " + tipo + " no se encuentra en la BBDD" ));
		
		itemsAux.stream()
			.filter(item->item.getIdItemPadre()==0)
			//.map(item->item.getIdItem())
			.forEach(item ->{ 
				itemsM.addAll(menuHelper.crearItem(item));
			});
		return new ResponseEntity<List<ItemMenuResponce>>(itemsM, HttpStatus.OK);
	}

}
