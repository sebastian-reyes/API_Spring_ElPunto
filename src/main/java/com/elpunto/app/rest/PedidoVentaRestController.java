package com.elpunto.app.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elpunto.app.interfaceService.IPedidoVentaService;
import com.elpunto.app.model.PedidoVenta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoVentaRestController {

    @Autowired
    IPedidoVentaService service;

    @GetMapping
    public ResponseEntity<?> listarPedidos() {
        Map<String, Object> response = new HashMap<>();
        List<PedidoVenta> lstPedidos = service.listarPedidos();
        response.put("pedidos", lstPedidos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPedidosId(@PathVariable Integer id) {
        PedidoVenta pvta = null;
        Map<String, Object> response = new HashMap<>();
        try {
            pvta = service.buscarPedidoVenta(id);
            if (pvta == null) {
                response.put("mensaje", "Este pedido de venta no existe en la base de datos");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(pvta, HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
