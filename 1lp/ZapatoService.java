package com.curso.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.curso.ecommerce.model.Producto;

public interface ZapatoService {
	public Zapato save( Zapato zapato);
	public Optional<Zapato> get(Integer id);
	public void update(Zapato zapato);
	public void delete(Integer id);
	public List<Zapato> findAll();

}
