package com.elpunto.app.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<Map<String, Object>> listarUsuarios() {
		Map<String, Object> response = new HashMap<>();
		List<Usuario> lstUsuarios = usuarioService.listarUsuarios();
		response.put("usuarios", lstUsuarios);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuario = usuarioService.buscarUsuario(id);
			if (usuario == null) {
				response.put("mensaje", "El usuario con id: " + id.toString() + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(usuario, HttpStatus.OK);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/registro")
	public ResponseEntity<?> crearUsuario(@RequestBody Usuario u) {
		Usuario nuevoUsuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			nuevoUsuario = usuarioService.guardarUsuario(u);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario fue creado con éxito");
		response.put("usuario", nuevoUsuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario u, @PathVariable Integer id) {
		Usuario usuarioActual = usuarioService.buscarUsuario(id);
		Usuario usuarioActualizado = null;
		Map<String, Object> response = new HashMap<>();
		if (usuarioActual == null) {
			response.put("mensaje",
					"No se pudo editar, el usuario con el id " + id.toString() + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			try {
				usuarioActual.setEmail(u.getEmail());
				usuarioActual.setPassword(u.getPassword());
				usuarioActual.setDni(u.getDni());
				usuarioActual.setNombres(u.getNombres());
				usuarioActual.setApellidos(u.getApellidos());
				usuarioActual.setTelefono(u.getTelefono());
				usuarioActual.setRol(u.getRol());
				
				usuarioActualizado = usuarioService.guardarUsuario(usuarioActual);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar el registro a la base de datos.");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El usuario fue actualizado con éxito");
			response.put("usuario", usuarioActualizado);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
	}
}
