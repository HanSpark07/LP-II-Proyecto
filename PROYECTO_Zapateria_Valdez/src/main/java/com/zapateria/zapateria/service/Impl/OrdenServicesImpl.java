package com.zapateria.zapateria.service.Impl;

import com.zapateria.zapateria.model.Orden;
import com.zapateria.zapateria.model.Usuario;
import com.zapateria.zapateria.repository.IOrdenRepository;
import com.zapateria.zapateria.service.IOrdenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenServicesImpl implements IOrdenServices {

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    public String generarNumeroOrden(){
        int numero=0;
        String numeroConcat="";

        List<Orden> ordenes = findAll();

        List<Integer> numeros= new ArrayList<Integer>();

        ordenes.stream().forEach(o -> numeros.add( Integer.parseInt( o.getNumero())));

        if (ordenes.isEmpty()) {
            numero=1;
        }else {
            numero= numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if (numero<10) { //0000001000
            numeroConcat="000000000"+String.valueOf(numero);
        }else if(numero<100) {
            numeroConcat="00000000"+String.valueOf(numero);
        }else if(numero<1000) {
            numeroConcat="0000000"+String.valueOf(numero);
        }

        return numeroConcat;
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
        return ordenRepository.findByUsuario(usuario);
    }

    @Override
    public Optional<Orden> findById(Integer id) {
        return ordenRepository.findById(id);
    }
}
