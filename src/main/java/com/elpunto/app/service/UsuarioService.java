package com.elpunto.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elpunto.app.dao.IUsuarioDao;
import com.elpunto.app.interfaceService.IUsuarioService;
import com.elpunto.app.model.Usuario;

@Service
public class UsuarioService implements IUsuarioService{

	@Autowired
	IUsuarioDao usuarioDao;
	
	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioDao.findAll();
	}

	@Override
	public Usuario guardarUsuario(Usuario u) {
		return usuarioDao.save(u);
	}

	@Override
	public void eliminarUsuario(int id) {
		usuarioDao.deleteById(id);
		
	}

	@Override
	public Usuario buscarUsuario(int id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public Usuario login(String email, String password) {
		return usuarioDao.findByEmailAndPassword(email, password).orElse(null);
	}
	
}
