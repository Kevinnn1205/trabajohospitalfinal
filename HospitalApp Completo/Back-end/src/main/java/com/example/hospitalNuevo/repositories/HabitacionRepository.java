package com.example.hospitalNuevo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hospitalNuevo.models.Habitacion;
import java.util.Optional;



@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, String> {
    Optional<Habitacion> findByNombre(String nombre);
}
