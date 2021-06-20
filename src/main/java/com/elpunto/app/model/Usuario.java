package com.elpunto.app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_usuario;
	
	@Column(name="email", unique = true, nullable = false)
	private String email;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="dni", unique = true, length = 8, nullable = false)
	private String dni;
	
	@Column(name="nombres", nullable = false)
	private String nombres;
	
	@Column(name="apellidos", nullable = false)
	private String apellidos;
	
	@Column(name="telefono", length = 50)
	private String telefono;
	
	//Guardar fecha actual al registrarse
	@PrePersist
	public void prePersist() {
		fecha_creacion = new Date();
	}
	
	@Column(name="fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fecha_creacion;
	
	@Column(name="foto")
	private String foto;
	
	//Relación entre tablas ROL - USUARIO
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rol")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Rol rol;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
