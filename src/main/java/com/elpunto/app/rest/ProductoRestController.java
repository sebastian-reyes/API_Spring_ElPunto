package com.elpunto.app.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import javax.activation.FileTypeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elpunto.app.interfaceService.IProductoService;
import com.elpunto.app.model.Producto;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

	@Autowired
	IProductoService productoService;

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerProducto(@PathVariable Integer id) {
		Producto p = null;
		Map<String, Object> response = new HashMap<>();
		try {
			p = productoService.buscarProducto(id);
			if(p == null) {
				response.put("mensaje", "El producto con id " + id.toString() + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity<>(p,HttpStatus.OK);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Mostar Foto
	@GetMapping("/foto/{id}")
	public ResponseEntity<?> obtenerImagenProducto(@PathVariable Integer id) throws IOException {
		Producto p = null;
		String foto = null;
		Map<String, Object> response = new HashMap<>();

		try {
			p = productoService.buscarProducto(id);
			if(p == null) {
				response.put("mensaje", "El producto con id " + id.toString() + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			} else {
				foto = p.getFoto();
				if (foto == null) {
					response.put("mensaje", "El producto que seleccionó no cuenta con foto.");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
				} else {
					File img = new File("fotos/productos/" + foto);
					return ResponseEntity.ok()
							.contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img)))
							.body(Files.readAllBytes(img.toPath()));
				}
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta del registro.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
