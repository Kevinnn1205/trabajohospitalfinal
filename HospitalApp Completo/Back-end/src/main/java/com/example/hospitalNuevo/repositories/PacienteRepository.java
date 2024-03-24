package com.example.hospitalNuevo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hospitalNuevo.models.Paciente;




@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {
    Optional<Paciente> findByIdPaciente(String idPaciente);
    Optional<Paciente> findByDocumentoIdentidad(String documentoIdentidad);
    Optional<Paciente> findByCorreo(String correo);
}
