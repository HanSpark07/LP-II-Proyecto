package com.zapateria.zapateria.service.Impl;

import com.zapateria.zapateria.model.Usuario;
import com.zapateria.zapateria.repository.IUsuarioRepository;
import com.zapateria.zapateria.service.IUsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServicesImpl implements IUsuarioServices {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
