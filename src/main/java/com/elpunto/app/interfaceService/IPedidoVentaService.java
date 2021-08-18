package com.elpunto.app.interfaceService;

import java.util.List;

import com.elpunto.app.model.PedidoVenta;

public interface IPedidoVentaService {
    public List<PedidoVenta> listarPedidos();
    public PedidoVenta guardPedidoVenta(PedidoVenta pv);
    public PedidoVenta buscarPedidoVenta(int id);
}
