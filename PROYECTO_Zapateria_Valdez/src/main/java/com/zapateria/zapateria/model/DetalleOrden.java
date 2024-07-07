package com.zapateria.zapateria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity

public class DetalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private double cantidad;
    private double precio;
    private double total;

    @ManyToOne
    private Orden orden;

    @ManyToOne
    private Zapato zapato;

    public DetalleOrden(int id, String nombre, double cantidad, double precio, double total) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    public String toString() {
        return "DetalleOrden [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio
                + ", total=" + total + "]";
    }
}
