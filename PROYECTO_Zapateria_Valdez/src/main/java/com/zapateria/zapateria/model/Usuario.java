package com.zapateria.zapateria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String username;
    private String email;
    private String telefono;
    private String tipo;
    private String password;

    @OneToMany(mappedBy = "usuario")
    private List<Zapato> zapatos;

    @OneToMany(mappedBy = "usuario")
    private List<Orden> ordenes;

    public Usuario(int id, String nombre, String username, String email, String telefono, String tipo, String password) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.telefono = telefono;
        this.tipo = tipo;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", username=" + username + ", email=" + email
                + ", telefono=" + telefono + ", tipo=" + tipo + ", password=" + password + "]";
    }
}