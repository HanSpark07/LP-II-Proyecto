package com.zapateria.zapateria.service.Impl;

import java.util.List;
import java.util.Optional;

import com.zapateria.zapateria.repository.IZapatoRepository;
import com.zapateria.zapateria.service.IZapatoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zapateria.zapateria.model.Zapato;

@Service
public class ZapatoServicesImpl implements IZapatoServices {

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
