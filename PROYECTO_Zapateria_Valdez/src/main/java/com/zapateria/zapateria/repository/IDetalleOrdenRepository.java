package com.zapateria.zapateria.repository;

import com.zapateria.zapateria.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer> {
}
