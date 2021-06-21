package com.elpunto.app.interfaceService;

import java.util.List;

import com.elpunto.app.model.Categoria;

public interface ICategoriaService {
	public List<Categoria> listarCategorias();
	public Categoria guardarCategoria(Categoria c);
	public void eliminarCategoria(int id);
	public Categoria buscarCategoria(int id);
}
