package com.zapateria.zapateria.service.Impl;

import com.zapateria.zapateria.model.DetalleOrden;

import com.zapateria.zapateria.service.IDetalleOrdenServices;
import com.zapateria.zapateria.repository.IDetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleServicesImpl implements IDetalleOrdenServices {

    @Autowired
    private IDetalleOrdenRepository detalleOrdenRepository;

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }
}
