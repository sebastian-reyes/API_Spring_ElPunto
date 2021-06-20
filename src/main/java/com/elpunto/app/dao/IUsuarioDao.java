package com.elpunto.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elpunto.app.model.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Integer>{
	public Optional<Usuario> findByEmailAndPassword(String email, String password);
}
