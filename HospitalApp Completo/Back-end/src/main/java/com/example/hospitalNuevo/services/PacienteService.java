package com.example.hospitalNuevo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hospitalNuevo.constants.Estado;
import com.example.hospitalNuevo.models.Paciente;
import com.example.hospitalNuevo.repositories.PacienteRepository;

@Service
public class PacienteService {

    @Autowired
    PacienteRepository pacienteRepository;

    // Método para crear pacientes
    public Paciente create(Paciente paciente) {
        Paciente existingPacienteByCorreo = this.findByCorreo(paciente.getCorreo());
        Paciente existingPacienteByNumberDocument = this
                .findByDocumentoIdentidad(paciente.getDocumentoIdentidad());
        if (existingPacienteByCorreo == null && existingPacienteByNumberDocument == null) {
            return pacienteRepository.save(paciente);
        } else
            return null;
    }

    // Método para listar todos los médicos
    public List<Paciente> findAll() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        if (pacientes.isEmpty()) {
            return null;
        } else
            return pacientes;
    }

    // Método para buscar un paciente por id
    @SuppressWarnings("null")
    public Paciente findById(String idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente).orElse(null);
        if (paciente == null) {
            return null;
        }
        return paciente;
    }

    // Método para actualizar un médico
    public Paciente update(Paciente paciente) {
        Paciente isExistPatient = this.findById(paciente.getIdPaciente());
        if (isExistPatient == null) {
            return null;
        } else
            return pacienteRepository.save(paciente);
    }

    // Método para actualizar estado del médico
    public Boolean updateEstado(String idPaciente) {
        Paciente paciente = this.findById(idPaciente);
        if (paciente != null) {
            if (paciente.getEstado() == Estado.Activo) {
                paciente.setEstado(Estado.Inactivo);
            } else {
                paciente.setEstado(Estado.Activo);
            }
            pacienteRepository.save(paciente);
            return true;
        } else {
            return false;
        }
    }

    // Método para eliminar un médico por id
    @SuppressWarnings("null")
    public Boolean delete(String idPaciente) {
        Paciente isExistPaciente = this.findById(idPaciente);
        if (isExistPaciente != null) {
            pacienteRepository.deleteById(isExistPaciente.getIdPaciente());
            return true;
        } else
            return false;
    }

    // Métodos IMedicosRepository
    public Paciente findByDocumentoIdentidad(String numeroDocumento) {
        Paciente isExistNumeroDocumento = pacienteRepository.findByDocumentoIdentidad(numeroDocumento).orElse(null);
        if (isExistNumeroDocumento == null) {
            return null;
        } else
            return isExistNumeroDocumento;
    }

    public Paciente findByCorreo(String correo) {
        Paciente isExistCorreo = pacienteRepository.findByCorreo(correo).orElse(null);
        if (isExistCorreo == null) {
            return null;
        } else
            return isExistCorreo;
    }
}
