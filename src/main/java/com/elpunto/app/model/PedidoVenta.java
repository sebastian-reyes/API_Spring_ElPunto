package com.elpunto.app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedido_venta")
public class PedidoVenta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pdovta;

    private String tipo_pago;
    private Date fecha;
    private String estado;
    private String cancelado;
    private String direccion;

    //Relación con tabla usuario(Rol Cliente)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler","pedidos"})
    private Usuario usuario;

}
