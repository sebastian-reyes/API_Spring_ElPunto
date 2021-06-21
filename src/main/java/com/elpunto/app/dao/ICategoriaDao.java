package com.elpunto.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elpunto.app.model.Categoria;

public interface ICategoriaDao extends JpaRepository<Categoria, Integer>{

}
