package com.example.hospitalNuevo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospitalNuevo.models.Medico;
import com.example.hospitalNuevo.services.MedicoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/medicos")
public class MedicoRestController {
    @Autowired
    private MedicoService medicoService;

    @PostMapping(value = "/", headers = "Accept=application/json")
    public Object create(@RequestBody @Valid Medico medico) {
        Map<String, Object> object = new HashMap<>();
        Medico isCreatedMedico = medicoService.create(medico);
        if (isCreatedMedico == null) {
            object.put("message",
                    "Ya hay un médico con él correo: " + medico.getCorreo() + " o número de documento: "
                            + medico.getDocumentoIdentidad());
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        object.put("message", null);
        object.put("data", isCreatedMedico);
        object.put("statusCode", HttpStatus.CREATED);
        return object;
    }

    @GetMapping(value = "/", headers = "Accept=application/json")
    public Object findAll() {
        Map<String, Object> object = new HashMap<>();
        List<Medico> medicos = medicoService.findAll();
        if (medicos == null) {
            object.put("message", "No se encontraron resultados");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", null);
        object.put("data", medicos);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @GetMapping(value = "/{idMedico}", headers = "Accept=application/json")
    public Object findById(@PathVariable String idMedico) {
        Map<String, Object> object = new HashMap<>();
        Medico isExistMedico = medicoService.findById(idMedico);
        if (isExistMedico == null) {
            object.put("message", "No se encontraron resultados");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", null);
        object.put("data", isExistMedico);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @PutMapping(value = "/", headers = "Accept=application/json")
    public Object update(@RequestBody Medico medico) {
        Map<String, Object> object = new HashMap<>();
        Medico isUpdated = medicoService.update(medico);
        if (isUpdated == null) {
            object.put("message", "No se pudo realizar la actualización.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", "Se ha realizado la actualización con éxito.");
        object.put("data", null);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @PutMapping(value = "/cambiarEstado/{idMedico}", headers = "Accept=application/json")
    public Object updateEstado(@PathVariable String idMedico) {
        Map<String, Object> object = new HashMap<>();
        Boolean isUpdated = medicoService.updateEstado(idMedico);
        if (isUpdated) {
            object.put("message", "Se ha actualizado el estado del médico correctamente");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", "Ocurrió un problema al actualizar el estado, intenta nuevamente.");
        object.put("data", null);
        object.put("statusCode", HttpStatus.CONFLICT);
        return object;
    }

    @DeleteMapping(value = "/{idMedico}", headers = "Accept=application/json")
    public Object delete(@PathVariable String idMedico) {
        Map<String, Object> object = new HashMap<>();
        Boolean isDeleted = medicoService.delete(idMedico);
        if (isDeleted) {
            object.put("message", "Se ha eliminado el médico correctamente.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", "El médico con id: " + idMedico + " no existe.");
        object.put("data", null);
        object.put("statusCode", HttpStatus.NOT_FOUND);
        return object;
    }

    @GetMapping(value = "/documentoIdentidad/{numeroDocumento}", headers = "Accept=application/json")
    public Object findByDocumentoIdentidad(@PathVariable String numeroDocumento) {
        Map<String, Object> object = new HashMap<>();
        Medico isExistMedico = medicoService.findByDocumentoIdentidad(numeroDocumento);
        if (isExistMedico == null) {
            object.put("message", "No existe el médico con él documento de identidad:" + numeroDocumento);
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        object.put("message", null);
        object.put("data", isExistMedico);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @GetMapping(value = "/correo/{correo}", headers = "Accept=application/json")
    public Object findByCorreo(@PathVariable String correo) {
        Map<String, Object> object = new HashMap<>();
        Medico isExistMedico = medicoService.findByCorreo(correo);
        if (isExistMedico == null) {
            object.put("message", "No existe el médico con él correo:" + correo);
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        object.put("message", null);
        object.put("data", isExistMedico);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

}
