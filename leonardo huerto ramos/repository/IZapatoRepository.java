package com.curso.ecommerce.repository;

import com.curso.ecommerce.model.Zapato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.model.Zapato;

@Repository
public interface IZapatoRepository extends JpaRepository<Zapato, Integer> {

}
