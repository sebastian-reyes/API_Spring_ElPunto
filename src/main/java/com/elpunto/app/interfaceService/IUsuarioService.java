package com.elpunto.app.interfaceService;

import java.util.List;

import com.elpunto.app.model.Usuario;

public interface IUsuarioService {
	public List<Usuario> listarUsuarios();
	public Usuario guardarUsuario(Usuario u);
	public void eliminarUsuario(int id);
	public Usuario buscarUsuario(int id);
	public Usuario login(String email, String password);
}
