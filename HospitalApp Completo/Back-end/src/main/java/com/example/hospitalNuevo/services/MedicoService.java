package com.example.hospitalNuevo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hospitalNuevo.constants.Estado;
import com.example.hospitalNuevo.models.Medico;
import com.example.hospitalNuevo.repositories.MedicoRepository;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    // Método para crear médicos
    public Medico create(Medico medico) {
        Medico existingMedicoByCorreo = this.findByCorreo(medico.getCorreo());
        Medico existingMedicoByNumberDocument = this
                .findByDocumentoIdentidad(medico.getDocumentoIdentidad());
        if (existingMedicoByCorreo == null || existingMedicoByNumberDocument == null) {
            return medicoRepository.save(medico);
        } else
            return null;
    }

    // Método para listar todos los médicos
    public List<Medico> findAll() {
        List<Medico> medicos = medicoRepository.findAll();
        if (medicos.isEmpty()) {
            return null;
        } else
            return medicos;
    }

    // Método para buscar un médico por id
    @SuppressWarnings("null")
    public Medico findById(String idMedico) {
        Medico isExistMedico = medicoRepository.findById(idMedico).orElse(null);
        if (isExistMedico == null) {
            return null;
        }else return isExistMedico;
    }

    // Método para actualizar un médico
    public Medico update(Medico medico) {
        Medico isExistMedico = this.findById(medico.getIdMedico());
        if (isExistMedico != null) {
            return medicoRepository.save(medico);
        } else {
            return null;
        }
    }

    // Método para actualizar estado del médico
    public Boolean updateEstado(String idMedico) {
        Medico medico = this.findById(idMedico);
        if (medico != null) {
            if (medico.getEstado() == Estado.Activo) {
                medico.setEstado(Estado.Inactivo);
            } else {
                medico.setEstado(Estado.Activo);
            }
            medicoRepository.save(medico);
            return true;
        } else {
            return false;
        }
    }

    // Método para eliminar un médico por id
    @SuppressWarnings("null")
    public Boolean delete(String idMedico) {
        Medico isExistMedico = this.findById(idMedico);
        if (isExistMedico != null) {
            medicoRepository.deleteById(isExistMedico.getIdMedico());
            return true;
        } else
            return false;
    }

    // Métodos IMedicosRepository
    public Medico findByDocumentoIdentidad(String numeroDocumento) {
        Medico medico = medicoRepository.findByDocumentoIdentidad(numeroDocumento).orElse(null);
        if (medico == null) {
            return null;
        } else
            return medico;
    }

    public Medico findByCorreo(String correo) {
        Medico medico = medicoRepository.findByCorreo(correo).orElse(null);
        if (medico == null) {
            return null;
        } else
            return medico;
    }
}