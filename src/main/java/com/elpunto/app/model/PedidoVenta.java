package com.elpunto.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedido_venta")
public class PedidoVenta implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pdovta;

    private String tipo_pago;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @PrePersist
    public void prePersist(){
        fecha = new Date();
    }

    private String estado;
    private Boolean cancelado;
    private String direccion;

    //Relación con tabla usuario(Rol Cliente)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler","pedidos"})
    private Usuario usuario;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pedido")
    private List<DetallePedidoVenta> detalleVenta;

    public Double getTotal(){
        Double total = 0.0;
        for (DetallePedidoVenta dVenta : detalleVenta) {
            total += dVenta.getImporte();
        }
        return total;
    }

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
