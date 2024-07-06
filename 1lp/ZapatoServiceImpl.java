package com.curso.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.repository.IZapatoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	private IZapatoRepository zapatoRepository;

	@Override
	public Zapato save(Zapato zapato) {return zapatoRepository.save(zapato);
	}

	@Override
	public Optional<Zapato> get(Integer id) {
		return zapatoRepository.findById(id);
	}

	@Override
	public void update(Zapato zapato) {
		zapatoRepository.save(producto);
	}

	@Override
	public void delete(Integer id) {
		zapatoRepository.deleteById(id);
	}

	@Override
	public List<Zapato> findAll() {
		return zapatoRepository.findAll();
	}

}
