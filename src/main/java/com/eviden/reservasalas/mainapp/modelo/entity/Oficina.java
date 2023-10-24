package com.eviden.reservasalas.mainapp.modelo.entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/*
 * El índice de la base de datos es una estructura de datos que mejora la velocidad de las operaciones de recuperación de datos en una tabla 
 * a costa de escrituras y espacio de almacenamiento adicionales. Principalmente, es una copia de columnas de datos seleccionadas de una sola
 *  tabla. Deberíamos crear índices para aumentar el rendimiento en nuestra capa de persistencia.
 */
@Entity
@Table(name = "oficinas", indexes = { @Index(name = "officename_inx", columnList = "nombreOficina", unique = true) })
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
//@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Oficina implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long idOficina;

	@Column(unique = true)
	private String nombreOficina;
	
	//Direccion donde esta ubicada la oficina
	@Column(nullable = false)
	private String direccion;
	
	//Localidad ubicacion oficina
	@Column(nullable = false)
	private String localidad;
	
	//CP postal Oficina
	@Column(nullable = false)
	private String codPostal;
	
	//Provincia de la localidad de la oficcina
	@Column(nullable = false)
	private String provincia;
	
	//Coordenas de la ubicacion de la oficina
	// ---- Longitud
	@NonNull
	private String longitud;
	
	//---- Latitud
	@NonNull
	private String latitud;	

	@OneToMany(mappedBy = "salaOficina", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "oficina-salas")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<Sala> salas;

	@OneToMany(mappedBy = "userOficina",cascade = CascadeType.ALL)
	@JsonManagedReference(value = "oficina-usuario")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<Usuario> users;

	@ManyToOne
	@JoinColumn(name = "FK_pais")
	//@JsonBackReference(value = "pais-oficina")//==> Evitamos la recursividad infinita. Esta es la parte que NO se serializa
	@JsonProperty(access = Access.WRITE_ONLY)
	private Pais pais;

	@Version
	@Column(name = "regVersion", columnDefinition = "bigint DEFAULT 0", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private long version;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
