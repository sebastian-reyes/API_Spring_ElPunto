package com.elpunto.app.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.activation.FileTypeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
			if (p == null) {
				response.put("mensaje", "El producto con id " + id.toString() + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(p, HttpStatus.OK);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Mostar Foto
	@GetMapping("/foto/{id}")
	public ResponseEntity<?> obtenerImagenProducto(@PathVariable Integer id) throws IOException {
		Producto p = null;
		String foto = null;
		Map<String, Object> response = new HashMap<>();

		try {
			p = productoService.buscarProducto(id);
			if (p == null) {
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
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/registro")
	public ResponseEntity<?> registrarProducto(@RequestBody Producto p) {
		Producto nuevoProducto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			nuevoProducto = productoService.guardarProducto(p);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("producto", nuevoProducto);
		response.put("mensaje", "El producto fue creado correctamente.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/upload")
	public ResponseEntity<?> subirFotoProducto(@RequestParam("foto") MultipartFile foto,
			@RequestParam("id") Integer id) {
		Producto producto = productoService.buscarProducto(id);
		Map<String, Object> response = new HashMap<>();
		if (!foto.isEmpty()) {
			String nombreFoto = foto.getOriginalFilename().replace(" ", "");
			Path rutaFoto = Paths.get("fotos\\productos").resolve(nombreFoto).toAbsolutePath();
			try {
				Files.copy(foto.getInputStream(), rutaFoto);
			} catch (Exception e) {
				response.put("mensaje", "Error al subir la imagen");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			String nombreFotoAnterior = producto.getFoto();
			if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
				Path rutaFotoAnterior = Paths.get("fotos\\productos").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			producto.setFoto(nombreFoto);
			productoService.guardarProducto(producto);
			response.put("producto", producto);
			response.put("mensaje", "Ha subido correctamente la imagen" + nombreFoto);
		} else {
			response.put("mensaje", "El campo foto no puede estar vacío");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<?> eliminarProducto(@PathVariable Integer id) {
		Producto p = null;
		Map<String, Object> response = new HashMap<>();
		try {
			p = productoService.buscarProducto(id);
			if (p == null) {
				response.put("mensaje", "El producto con id " + id.toString() + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			} else {
				String nombreFoto = p.getFoto();
				if (nombreFoto != null && nombreFoto.length() > 0) {
					Path rutaFoto = Paths.get("fotos\\productos").resolve(nombreFoto).toAbsolutePath();
					File archivoFoto = rutaFoto.toFile();
					if (archivoFoto.exists() && archivoFoto.canRead()) {
						archivoFoto.delete();
					}
				}
				productoService.eliminarProducto(id);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la eliminación del registro.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Producto eliminado correctamente");
		response.put("Producto eliminado", p);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
