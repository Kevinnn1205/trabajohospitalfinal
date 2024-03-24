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
import org.springframework.web.bind.annotation.RestController;

import com.example.hospitalNuevo.models.Cama;
import com.example.hospitalNuevo.services.CamaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/camas")
public class CamaRestController {

    @Autowired
    CamaService camaService;

    @PostMapping(value = "/", headers = "Accept=application/json")
    public Object create(@RequestBody @Valid Cama cama) {
        Map<String, Object> object = new HashMap<>();
        Cama isCreatedCama = camaService.create(cama);
        if (isCreatedCama == null) {
            object.put("message",
                    "Ya hay una cama con él nombre: " + cama.getNombre() + " intenta nuevamente.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        object.put("message", null);
        object.put("data", isCreatedCama);
        object.put("statusCode", HttpStatus.CREATED);
        return object;
    }

    @GetMapping(value = "/", headers = "Accept=application/json")
    public Object findAll() {
        Map<String, Object> object = new HashMap<>();
        List<Cama> camas = camaService.findAll();
        if (camas == null) {
            object.put("message", "No se encontraron resultados");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", null);
        object.put("data", camas);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @GetMapping(value = "/{idCama}", headers = "Accept=application/json")
    public Object findById(@PathVariable String idCama) {
        Map<String, Object> object = new HashMap<>();
        Cama isExistCama = camaService.findById(idCama);
        if (isExistCama == null) {
            object.put("message", "No se encontraron resultados");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", null);
        object.put("data", isExistCama);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @PutMapping(value = "/", headers = "Accept=application/json")
    public Object update(@RequestBody Cama cama) {
        Map<String, Object> object = new HashMap<>();
        Cama isUpdated = camaService.update(cama);
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

    @PutMapping(value = "/cambiarEstado/{idCama}", headers = "Accept=application/json")
    public Object updateEstado(@PathVariable String idCama) {
        Boolean isUpdated = camaService.updateEstado(idCama);
        if (isUpdated) {
            return "Se ha actualizado el estado de la cama correctamente";
        } else {
            return "Ocurrió un problema al actualizar el estado, intenta nuevamente.";
        }
    }

    @DeleteMapping(value = "/{idCama}", headers = "Accept=application/json")
    public Object delete(@PathVariable String idCama) {
        Map<String, Object> object = new HashMap<>();
        Boolean isDeleted = camaService.delete(idCama);
        if (isDeleted) {
            object.put("message", "Se ha eliminado la cama correctamente.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", "La cama con id: " + idCama + " no existe.");
        object.put("data", null);
        object.put("statusCode", HttpStatus.NOT_FOUND);
        return object;
    }

    @GetMapping(value = "/nombre/{nombre}", headers = "Accept=application/json")
    public Object findByNombre(@PathVariable String nombre) {
        Map<String, Object> object = new HashMap<>();
        Cama isExistCama = camaService.findByNombre(nombre);
        if (isExistCama == null) {
            object.put("message", "No se encontraron resultados con el nombre: " + nombre);
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        object.put("message", null);
        object.put("data", isExistCama);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }
}
