package com.example.hospitalNuevo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hospitalNuevo.constants.Estado;
import com.example.hospitalNuevo.models.Cama;
import com.example.hospitalNuevo.repositories.CamaRepository;

@Service
public class CamaService {

    @Autowired
    CamaRepository camaRepository;

    // Método para crear una cama
    public Cama create(Cama cama) {
        Cama existingCamaByName = this.findByNombre(cama.getNombre());
        if (existingCamaByName == null) {
            return camaRepository.save(cama);
        } else
            return null;
    }

    // Método para listar todos las habitaciones
    public List<Cama> findAll() {
        List<Cama> camas = camaRepository.findAll();
        if (camas.isEmpty()) {
            return null;
        } else
            return camas;
    }

    // Método para buscar una habitación por id
    @SuppressWarnings("null")
    public Cama findById(String idCama) {
        Cama isExistCama = camaRepository.findById(idCama).orElse(null);
        if (isExistCama == null) {
            return null;
        } else
            return isExistCama;
    }

    // Método para actualizar una habitacion
    public Cama update(Cama cama) {
        Cama isExistCama = this.findById(cama.getIdCama());
        if (isExistCama != null) {
            return camaRepository.save(cama);
        } else {
            return null;
        }
    }

    // Método para actualizar estado de la cama
    public Boolean updateEstado(String idCama) {
        Cama cama = this.findById(idCama);
        if (cama != null) {
            if (cama.getEstado() == Estado.Activo) {
                cama.setEstado(Estado.Inactivo);
            } else {
                cama.setEstado(Estado.Activo);
            }
            camaRepository.save(cama);
            return true;
        } else {
            return false;
        }
    }

    // Método para eliminar una habitación por id
    @SuppressWarnings("null")
    public Boolean delete(String idCama) {
        Cama isExistCama = this.findById(idCama);
        if (isExistCama != null) {
            camaRepository.deleteById(isExistCama.getIdCama());
            return true;
        } else
            return false;
    }

    // Buscar una cama por nombre
    public Cama findByNombre(String nombre) {
        Cama cama = camaRepository.findByNombre(nombre).orElse(null);
        if (cama == null) {
            return null;
        } else
            return cama;
    }
}
