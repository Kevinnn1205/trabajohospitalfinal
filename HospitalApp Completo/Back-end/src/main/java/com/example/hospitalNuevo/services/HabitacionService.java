package com.example.hospitalNuevo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hospitalNuevo.models.Habitacion;
import com.example.hospitalNuevo.repositories.HabitacionRepository;

@Service
public class HabitacionService {

    @Autowired
    HabitacionRepository habitacionRepository;

    // Método para crear habitacion
    public Habitacion create(Habitacion habitacion) {
        Habitacion existingHabitacionByName = this.findByNombre(habitacion.getNombre());
        if (existingHabitacionByName == null) {
            return habitacionRepository.save(habitacion);
        } else
            return null;
    }

    // Método para listar todos las habitaciones
    public List<Habitacion> findAll() {
        List<Habitacion> habitaciones = habitacionRepository.findAll();
        if (habitaciones.isEmpty()) {
            return null;
        } else
            return habitaciones;
    }

    // Método para buscar una habitación por id
    @SuppressWarnings("null")
    public Habitacion findById(String idHabitacion) {
        Habitacion isExistHabitacion = habitacionRepository.findById(idHabitacion).orElse(null);
        if (isExistHabitacion == null) {
            return null;
        } else
            return isExistHabitacion;
    }

    // Método para actualizar una habitacion
    public Habitacion update(Habitacion habitacion) {
        Habitacion isExistHabitacion = this.findById(habitacion.getIdHabitacion());
        if (isExistHabitacion != null) {
            return habitacionRepository.save(habitacion);
        } else {
            return null;
        }
    }

    // Método para eliminar una habitación por id
    @SuppressWarnings("null")
    public Boolean delete(String idHabitacion) {
        Habitacion isExistHabitacion = this.findById(idHabitacion);
        if (isExistHabitacion != null) {
            habitacionRepository.deleteById(isExistHabitacion.getIdHabitacion());
            return true;
        } else
            return false;
    }

    // Buscar una habitación por nombre
    public Habitacion findByNombre(String nombre) {
        Habitacion habitacion = habitacionRepository.findByNombre(nombre).orElse(null);
        if (habitacion == null) {
            return null;
        } else
            return habitacion;
    }
}
