package com.eviden.reservasalas.mainapp.modelo.entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pais", indexes = { @Index(name = "nombrepais_inx", columnList = "nombre")})
@Getter @Setter
//@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Pais implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long idPais;

	@Getter @Setter
	@Column(unique = true)
	private String nombre;

	@OneToMany(mappedBy = "pais",cascade = CascadeType.ALL)
	//@JsonManagedReference(value = "pais-oficina") //==> Evitamos la recursividad infinita. Esta es la parte que se serializa
	@JsonProperty(access = Access.WRITE_ONLY)// Con esta anotacion decimos a spring que no envie los datos en las respuestas
	private Set<Oficina> oficinas;

	/*
	 * JPA usa un campo de versión en sus entidades para detectar modificaciones simultáneas en el mismo registro del almacén de datos. 
	 * Cuando el tiempo de ejecución de JPA detecta un intento de modificar simultáneamente el mismo registro, 
	 * lanza una excepción a la transacción que intenta confirmar en último lugar.
	 */
	@Version
	@Column(name = "regVersion",columnDefinition = "bigint DEFAULT 0", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private long version = 0L;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
