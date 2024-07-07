package com.zapateria.zapateria.repository;

import com.zapateria.zapateria.model.Orden;
import com.zapateria.zapateria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden,Integer> {
    List<Orden> findByUsuario(Usuario usuario);
}
