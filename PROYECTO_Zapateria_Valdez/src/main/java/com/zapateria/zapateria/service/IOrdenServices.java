package com.zapateria.zapateria.service;

import com.zapateria.zapateria.model.Orden;
import com.zapateria.zapateria.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IOrdenServices {
    List<Orden> findAll();
    Orden save(Orden orden);
    String generarNumeroOrden();
    List<Orden> findByUsuario(Usuario usuario);
    Optional<Orden> findById(Integer id);
}
