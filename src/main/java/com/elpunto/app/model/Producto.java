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
@Table(name = "productos")
public class Producto implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_producto;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "fecha_venc")
	@Temporal(TemporalType.DATE)
	private Date fecha_venc;

	@Column(name = "desc_prod", nullable = false)
	private String desc_prod;

	@Column(name = "stock_min")
	private Integer stock_min;

	@Column(name = "stock_act")
	private Integer stock_act;

	@Column(name = "precio", nullable = false)
	private Double precio;

	@Column(name = "foto")
	private String foto;

	// Relación con tabla categorías
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler","productos"})
	private Categoria categoria;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
