package com.eviden.reservasalas.mainapp.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eviden.reservasalas.mainapp.DTO.ItemMenuResponce;
import com.eviden.reservasalas.mainapp.modelo.entity.ItemsMenu;
import com.eviden.reservasalas.mainapp.servicios.IItemsMenuService;

@Component
public class ItemsMenuHelper {
	
	@Autowired
	IItemsMenuService itemsMenuService;
	
	/*public void inciarArrayItems() {
		this.items = new ArrayList<ItemMenuResponce>();
	}*/
	
	public List<ItemMenuResponce> crearItem(ItemsMenu item) {
		List<ItemMenuResponce> itemsM = new ArrayList<ItemMenuResponce>();
		ArrayList<ItemMenuResponce> itemX = new ArrayList<ItemMenuResponce>();
		ItemMenuResponce itemComodin = new ItemMenuResponce();
		itemComodin.setIcon(item.getIcono());
		itemComodin.setLabel(item.getLabel());
		itemComodin.setRouterLink(item.getRouterLink());
		//itemX.clear();
		//System.out.println("Item: " + itemComodin.getLabel());
		List<ItemsMenu> itemsAuxB = itemsMenuService.itemsPorIdPadre(item.getIdItem()).get();
		//System.out.println("ItemB: " + itemsAuxB.size());
		//ArrayList<ItemMenuResponce> itemY = new ArrayList<ItemMenuResponce>();
		
		//itemX.clear();
		//System.out.println("itemX antes del bucle: " +itemX.size() );
		itemsAuxB.forEach(itemB -> {
			ArrayList<ItemMenuResponce> itemY = new ArrayList<ItemMenuResponce>();
			ItemMenuResponce itemComodin2 = new ItemMenuResponce();
			
			//System.out.println("ItemB: " +itemB.getIdItem());
			
			itemComodin2.setIcon(itemB.getIcono());
			itemComodin2.setLabel(itemB.getLabel());
			itemComodin2.setRouterLink(itemB.getRouterLink());
			//itemX.add(itemB);
			List<ItemsMenu> itemsAuxC = itemsMenuService.itemsPorIdPadre(itemB.getIdItem()).get();
			itemsAuxC.forEach(itemC -> {
				ArrayList<ItemMenuResponce> itemZ = new ArrayList<ItemMenuResponce>();
				ItemMenuResponce itemComodin3 = new ItemMenuResponce();
				itemComodin3.setIcon(itemC.getIcono());
				itemComodin3.setLabel(itemC.getLabel());
				itemComodin3.setRouterLink(itemC.getRouterLink());
				
				List<ItemsMenu> itemsAuxD = itemsMenuService.itemsPorIdPadre(itemC.getIdItem()).get();
				itemsAuxD.forEach(itemD -> {
					//ArrayList<ItemMenuResponce> itemT = new ArrayList<ItemMenuResponce>();
					ItemMenuResponce itemComodin4 = new ItemMenuResponce();
					itemComodin4.setIcon(itemD.getIcono());
					itemComodin4.setLabel(itemD.getLabel());
					itemComodin4.setRouterLink(itemD.getRouterLink());
					itemComodin4.setItems(new ArrayList<ItemMenuResponce>());
					itemZ.add(itemComodin4);
				});
				//System.out.println("ItemC: " +itemC.getIdItem());
				if (itemZ != null) {
					itemComodin3.setItems(itemZ);
				}
				itemY.add(itemComodin3);
			});
			if(itemY != null) {
				itemComodin2.setItems(itemY);
			}
			itemX.add(itemComodin2);
			//itemX.get(JPAIndexHolder)
			//System.out.println("itemX: " +itemX.size() );
		});
		//itemX.add(itemComodin2);
		if(itemX != null) {
			itemComodin.setItems(itemX);
		}
		itemsM.add(itemComodin);
		//itemsM.forEach(dato -> System.out.println("Dato de ItemsM: " + dato.getSubItems()));
		return itemsM;
	}
}
