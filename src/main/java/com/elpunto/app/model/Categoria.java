package com.elpunto.app.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_cat;
	
	@Column(name = "nombre_cat", length = 70, nullable = false)
	private String nombre_cat;
	
	@Column(name = "foto_cat")
	private String foto_cat;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler","categoria"})
	private List<Producto> productos;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
