package com.example.hospitalNuevo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hospitalNuevo.models.Cama;
import java.util.Optional;


@Repository
public interface CamaRepository  extends JpaRepository<Cama, String> {
    Optional<Cama> findByNombre(String nombre);
}
