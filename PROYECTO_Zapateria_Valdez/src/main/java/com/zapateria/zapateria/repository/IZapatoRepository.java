package com.zapateria.zapateria.repository;

import com.zapateria.zapateria.model.Zapato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IZapatoRepository extends JpaRepository<Zapato, Integer> {

}
