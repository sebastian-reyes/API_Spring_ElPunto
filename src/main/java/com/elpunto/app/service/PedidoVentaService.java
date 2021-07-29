package com.elpunto.app.service;

import java.util.List;

import com.elpunto.app.dao.IPedidoVentaDao;
import com.elpunto.app.interfaceService.IPedidoVentaService;
import com.elpunto.app.model.PedidoVenta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoVentaService implements IPedidoVentaService{

    @Autowired
    IPedidoVentaDao dao;

    @Override
    public List<PedidoVenta> listarPedidos() {
        return (List<PedidoVenta>)dao.findAll();
    }

    @Override
    public PedidoVenta guardPedidoVenta(PedidoVenta pv) {
        return dao.save(pv);
    }

    @Override
    public void anularPedido(int id) {
        //Método aún por implementar...
    }

    @Override
    public PedidoVenta buscarPedidoVenta(int id) {
        return dao.findById(id).orElse(null);
    }
    
}
