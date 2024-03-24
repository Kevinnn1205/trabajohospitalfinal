package com.example.hospitalNuevo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hospitalNuevo.models.Ingreso;
import java.util.List;
import com.example.hospitalNuevo.constants.Estado;





@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, String> {
    List<Ingreso> findByPacienteIdPacienteAndEstado(String IdPaciente, Estado estado);
}
