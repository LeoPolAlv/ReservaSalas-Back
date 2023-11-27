package com.eviden.reservasalas.mainapp.DTO;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemMenuResponce {

	private String label;
	private String icon;
	private String routerLink;
	private ArrayList<ItemMenuResponce> items;
}
