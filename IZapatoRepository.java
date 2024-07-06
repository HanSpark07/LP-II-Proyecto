package com.zapataeria.zapeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zapataeria.zapeteria.model.Zapato;

@Repository
public interface IZapatoRepository extends JpaRepository<Zapato, Integer> {

}
