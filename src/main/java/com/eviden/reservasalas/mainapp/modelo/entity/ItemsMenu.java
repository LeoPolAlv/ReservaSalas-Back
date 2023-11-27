package com.eviden.reservasalas.mainapp.modelo.entity;

import java.io.Serializable;
//import java.util.Set;

import com.eviden.reservasalas.mainapp.DTO.TipoMenu;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ItemsMenu implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdItem")
	private int idItem;
	
	@NonNull 
	private int idItemPadre;
	
	@NonNull
	private String label;
	
	@NonNull
	private String icono;
	
	@NonNull
	private String routerLink;
	
	//@Column(name = "tipoMenu", columnDefinition = "enum('USER','DEFAULT','ADMIN')")
	@Enumerated(EnumType.STRING)
	private TipoMenu tipoMenu;
	
	//@ManyToMany(mappedBy = "items")
	//@JsonProperty(access = Access.WRITE_ONLY)
	//private Set<Usuario> usuario;
	
	@Version
	@Column(name = "regVersion",columnDefinition = "bigint DEFAULT 0", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private long version;
		
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
