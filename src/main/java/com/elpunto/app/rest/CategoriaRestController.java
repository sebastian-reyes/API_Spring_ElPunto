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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elpunto.app.interfaceService.ICategoriaService;
import com.elpunto.app.model.Categoria;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaRestController {

	@Autowired
	ICategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<?> listarCategorias() {
		Map<String, Object> response = new HashMap<>();
		List<Categoria> lstCategorias = categoriaService.listarCategorias();
		response.put("categorias", lstCategorias);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> listarCategoriaProductos(@PathVariable Integer id) {
		Categoria cat = null;
		Map<String, Object> response = new HashMap<>();
		try {
			cat = categoriaService.buscarCategoria(id);
			if(cat == null) {
				response.put("mensaje", "Esta categoría no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity<>(cat,HttpStatus.OK);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
