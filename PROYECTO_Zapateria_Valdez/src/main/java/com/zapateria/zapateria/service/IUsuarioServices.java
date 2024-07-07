package com.zapateria.zapateria.service;

import com.zapateria.zapateria.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioServices {
    Optional<Usuario> findById(Integer id);
    Usuario save(Usuario usuario);
    Optional<Usuario> findByEmail(String email);
}
