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

public class Zapato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private double precio;
    private int cantidad;

    @ManyToOne
    private Usuario usuario;

    @Override
    public String toString() {
        return "Zapato [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", imagen=" + imagen + ", precio=" + precio + ", cantidad=" + cantidad + "]";
    }
}
