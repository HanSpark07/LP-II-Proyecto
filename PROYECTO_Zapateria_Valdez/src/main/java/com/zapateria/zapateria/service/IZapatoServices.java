package com.zapateria.zapateria.service;

import java.util.List;
import java.util.Optional;

import com.zapateria.zapateria.model.Zapato;

public interface IZapatoServices {
    public Zapato save(Zapato zapato);
    public Optional<Zapato> get(Integer id);
    public void update(Zapato zapato);
    public void delete(Integer id);
    public List<Zapato> findAll();
}
