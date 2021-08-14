package com.elpunto.app.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.FileTypeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
			if (cat == null) {
				response.put("mensaje", "Esta categoría no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(cat, HttpStatus.OK);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/foto/{id}")
	public ResponseEntity<?> obtenerImagenCategoria(@PathVariable Integer id) throws IOException {
		Categoria c = null;
		String foto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			c = categoriaService.buscarCategoria(id);
			if (c == null) {
				response.put("mensaje", "La categpría con id " + id.toString() + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			} else {
				foto = c.getFoto_cat();
				if (foto == null) {
					response.put("mensaje", "La categoría que seleccionó no cuenta con foto.");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
				} else {
					File img = new File("fotos/categorias/" + foto);
					return ResponseEntity.ok()
							.contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img)))
							.body(Files.readAllBytes(img.toPath()));
				}
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/registro")
	public ResponseEntity<?> crearCategoria(@RequestBody Categoria c) {
		Categoria categoriaNueva = null;
		Map<String, Object> response = new HashMap<>();
		try {
			if (c.getNombre_cat().replace(" ", "").length() > 0) {
				categoriaNueva = categoriaService.guardarCategoria(c);
				response.put("mensaje", "La categoría fue creada con éxito");
				response.put("categoria", categoriaNueva);
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
			} else {
				response.put("mensaje", "No pueden haber campos vacíos.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
