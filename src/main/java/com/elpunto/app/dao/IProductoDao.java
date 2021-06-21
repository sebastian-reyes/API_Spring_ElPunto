package com.elpunto.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elpunto.app.model.Producto;

public interface IProductoDao extends JpaRepository<Producto, Integer>{
	
}
