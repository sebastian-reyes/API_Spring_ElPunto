package com.elpunto.app.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elpunto.app.interfaceService.IPedidoVentaService;
import com.elpunto.app.interfaceService.IProductoService;
import com.elpunto.app.model.DetallePedidoVenta;
import com.elpunto.app.model.PedidoVenta;
import com.elpunto.app.model.Producto;

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

@RestController
@RequestMapping("/api/pedidos")
public class PedidoVentaRestController {

    @Autowired
    IPedidoVentaService service;

    @Autowired
    IProductoService productoService;

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

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedidoVenta(@RequestBody PedidoVenta pv) {
        Map<String, Object> response = new HashMap<>();
        pv.setEstado("EN ENTREGA");
        List<DetallePedidoVenta> lstDetallePedidoVentas = pv.getDetalleVenta();
        for (DetallePedidoVenta objDetallePedidoVenta : lstDetallePedidoVentas) {
            Producto pRequest = objDetallePedidoVenta.getProducto();
            int cantidad = objDetallePedidoVenta.getCantidad();
            int idProd = pRequest.getId_producto();
            Producto pReal = productoService.buscarProducto(idProd);
            if (pReal.getStock_min() >= pReal.getStock_act()) {
                response.put("mensaje", "El producto " + pReal.getNombre() + " no se encuentra en stock");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else if (cantidad > pReal.getStock_act()) {
                response.put("mensaje",
                        "El producto " + pReal.getNombre() + " no cuenta con la cantidad que usted desea.");
                response.put("cantidad_actual", pReal.getStock_act());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                int pStockReal = pReal.getStock_act() - cantidad;
                pReal.setStock_act(pStockReal);
                productoService.guardarProducto(pReal);
            }
        }
        service.guardPedidoVenta(pv);
        response.put("mensaje", "Pedido registrado satisfactoriamente.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<?> anularPedido(@PathVariable Integer id) {
        PedidoVenta pAnulado = null;
        PedidoVenta pVenta = service.buscarPedidoVenta(id);
        Map<String, Object> response = new HashMap<>();
        if (pVenta == null) {
            response.put("mensaje", "El pedido de venta con id: " + id.toString() + " no existe en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        } else {
            try {
                if (pVenta.getEstado().toLowerCase().equals("entregado")
                        || pVenta.getEstado().toLowerCase().equals("anulado")) {
                    response.put("mensaje", "No se puede anular este pedido de venta.");
                    response.put("estado_pedido", pVenta.getEstado());
                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
                } else {
                    pVenta.setEstado("ANULADO");
                    pAnulado = service.guardPedidoVenta(pVenta);
                }
            } catch (DataAccessException e) {
                response.put("mensaje", "Error al anular el pedido.");
                response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        response.put("mensaje", "El pedido fue anulado.");
        response.put("pedido_anulado", pAnulado);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
