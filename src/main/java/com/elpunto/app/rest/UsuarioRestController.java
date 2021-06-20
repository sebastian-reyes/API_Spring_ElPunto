package com.elpunto.app.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elpunto.app.interfaceService.IUsuarioService;
import com.elpunto.app.model.Usuario;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

	@Autowired
	IUsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<Map<String,Object>> listarUsuarios(){
		Map<String,Object> response = new HashMap<>();
		List<Usuario> lstUsuarios = usuarioService.listarUsuarios();
		response.put("usuarios", lstUsuarios);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
