package com.example.hospitalNuevo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hospitalNuevo.models.Medico;
import java.util.Optional;




@Repository
public interface MedicoRepository extends JpaRepository<Medico, String>{
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    Optional<Medico> findByDocumentoIdentidad(String documentoIdentidad);
    Optional<Medico> findByCorreo(String correo);
}
