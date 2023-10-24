package com.eviden.reservasalas.mainapp.modelo.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "salas", indexes = {@Index(name = "nombreSala_inx", columnList = "nombre_sala", unique = true)})
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@OptimisticLocking(type = OptimisticLockType.VERSION) Solo se usa si no hay definido campo @Version

public class Sala implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@Getter @Setter
	private Long idSala;
	
	@Column(name = "nombre_sala", nullable = false, unique = true)
	@Getter @Setter
	private String nombreSala;

	@Getter @Setter
	private Integer capacidad;

	// ********************************
	// Join otras tablas
	// ********************************

	@ManyToOne(optional = false)
	@Getter @Setter
	@JoinColumn(name = "FK_oficina")
	@JsonBackReference(value = "oficina-salas")
	private Oficina salaOficina;

	//@OneToMany(mappedBy = "room")
	//@Getter @Setter
	//@JsonManagedReference(value = "room-roomEquipment")
	//@JsonProperty(access = Access.WRITE_ONLY)
	//private List<RoomEquipment> equipment;

	@OneToMany(mappedBy = "sala")
	@Getter @Setter
	@JsonManagedReference(value = "sala-reserva")
	//@JsonProperty(access = Access.WRITE_ONLY)
	private List<Reserva> reserva;

	@Version
	@Column(name = "regVersion", columnDefinition = "bigint DEFAULT 0", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private long version;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
