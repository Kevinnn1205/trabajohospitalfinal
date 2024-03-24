package com.example.hospitalNuevo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospitalNuevo.models.Paciente;
import com.example.hospitalNuevo.services.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteRestController {

    @Autowired
    PacienteService pacienteService;

    @PostMapping(value = "/", headers = "Accept=application/json")
    @ResponseBody
    public Object create(@RequestBody @Valid Paciente paciente) {
        Map<String, Object> object = new HashMap<>();
        Paciente isCreatedPaciente = pacienteService.create(paciente);
        if (isCreatedPaciente == null) {
            object.put("message",
                    "Ya hay un paciente con él correo: " + paciente.getCorreo() + " o número de documento: "
                            + paciente.getDocumentoIdentidad());
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        object.put("message", null);
        object.put("data", isCreatedPaciente);
        object.put("statusCode", HttpStatus.CREATED);
        return object;
    }

    @GetMapping(value = "/", headers = "Accept=application/json")
    @ResponseBody
    public Object findAll() {
        Map<String, Object> object = new HashMap<>();
        List<Paciente> pacientes = pacienteService.findAll();
        if (pacientes == null) {
            object.put("message", "No se encontraron resultados");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", null);
        object.put("data", pacientes);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @GetMapping(value = "/{idPaciente}", headers = "Accept=application/json")
    public Object findById(@PathVariable String idPaciente) {
        Map<String, Object> object = new HashMap<>();
        Paciente isExistPaciente = pacienteService.findById(idPaciente);
        if (isExistPaciente == null) {
            object.put("message", "No se encontraron resultados");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", null);
        object.put("data", isExistPaciente);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @PutMapping(value = "/", headers = "Accept=application/json")
    public Object update(@RequestBody Paciente paciente) {
        Map<String, Object> object = new HashMap<>();
        pacienteService.update(paciente);
        object.put("message", "Se ha realizado la actualización con éxito.");
        object.put("data", null);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @PutMapping(value = "/cambiarEstado/{idPaciente}", headers = "Accept=application/json")
    public Object updateEstado(@PathVariable String idPaciente) {
        Map<String, Object> object = new HashMap<>();
        Boolean isUpdated = pacienteService.updateEstado(idPaciente);
        if (isUpdated) {
            object.put("message", "Se ha actualizado el estado del paciente correctamente");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", "Ocurrió un problema al actualizar el estado, intenta nuevamente.");
        object.put("data", null);
        object.put("statusCode", HttpStatus.CONFLICT);
        return object;
    }

    @DeleteMapping(value = "/{idPaciente}", headers = "Accept=application/json")
    public Object delete(@PathVariable String idPaciente) {
        Map<String, Object> object = new HashMap<>();
        Boolean isDeleted = pacienteService.delete(idPaciente);
        if (isDeleted) {
            object.put("message", "Se ha eliminado el paciente correctamente.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", "El paciente con id: " + idPaciente + " no existe.");
        object.put("data", null);
        object.put("statusCode", HttpStatus.NOT_FOUND);
        return object;
    }

    @GetMapping(value = "/documentoIdentidad/{numeroDocumento}", headers = "Accept=application/json")
    public Object findByDocumentoIdentidad(@PathVariable String numeroDocumento) {
        Map<String, Object> object = new HashMap<>();
        Paciente isExistPaciente = pacienteService.findByDocumentoIdentidad(numeroDocumento);
        if (isExistPaciente == null) {
            object.put("message", "No existe el paciente con él documento de identidad:" + numeroDocumento);
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        object.put("message", null);
        object.put("data", isExistPaciente);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @GetMapping(value = "/correo/{correo}", headers = "Accept=application/json")
    public Object findByCorreo(@PathVariable String correo) {
        Map<String, Object> object = new HashMap<>();
        Paciente isExistPaciente = pacienteService.findByCorreo(correo);
        if (isExistPaciente == null) {
            object.put("message", "No existe el paciente con él correo:" + correo);
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        object.put("message", null);
        object.put("data", isExistPaciente);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }
}
