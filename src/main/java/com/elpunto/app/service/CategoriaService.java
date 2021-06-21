package com.elpunto.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.elpunto.app.dao.ICategoriaDao;
import com.elpunto.app.interfaceService.ICategoriaService;
import com.elpunto.app.model.Categoria;

public class CategoriaService implements ICategoriaService{

	@Autowired
	ICategoriaDao categoriaDao;

	@Override
	public List<Categoria> listarCategorias() {
		return (List<Categoria>)categoriaDao.findAll();
	}

	@Override
	public Categoria guardarCategoria(Categoria c) {
		return categoriaDao.save(c);
	}

	@Override
	public void eliminarCategoria(int id) {
		categoriaDao.deleteById(id);
	}

	@Override
	public Categoria buscarCategoria(int id) {
		return categoriaDao.findById(id).orElse(null);
	}
	
}
