package com.elpunto.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elpunto.app.dao.IProductoDao;
import com.elpunto.app.interfaceService.IProductoService;
import com.elpunto.app.model.Producto;

@Service
public class ProductoService implements IProductoService{
	@Autowired
	IProductoDao productoDao;
	
	@Override
	public List<Producto> listarProductos() {
		return (List<Producto>)productoDao.findAll();
	}

	@Override
	public Producto guardarProducto(Producto p) {
		return productoDao.save(p);
	}

	@Override
	public void eliminarProducto(int id) {
		productoDao.deleteById(id);
	}

	@Override
	public Producto buscarProducto(int id) {
		return productoDao.findById(id).orElse(null);
	}
}
