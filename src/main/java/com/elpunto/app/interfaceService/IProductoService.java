package com.elpunto.app.interfaceService;

import java.util.List;

import com.elpunto.app.model.Producto;

public interface IProductoService {
	public List<Producto> listarProductos();
	public Producto guardarProducto(Producto p);
	public void eliminarProducto(int id);
	public Producto buscarProducto(int id);
}
