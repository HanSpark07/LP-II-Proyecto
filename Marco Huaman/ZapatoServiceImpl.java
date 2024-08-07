package com.zapataeria.zapeteria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zapataeria.zapeteria.model.Zapato;
import com.zapataeria.zapeteria.repository.IZapatoRepository;

@Service
public class ZapatoServiceImpl implements ZapatoService {

	@Autowired
	private IZapatoRepository zapatoRepository;

	@Override
	public Zapato save(Zapato zapato) {
		return zapatoRepository.save(zapato);
	}

	@Override
	public Optional<Zapato> get(Integer id) {
		return zapatoRepository.findById(id);
	}

	@Override
	public void update(Zapato zapato) {
		zapatoRepository.save(zapato);
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

