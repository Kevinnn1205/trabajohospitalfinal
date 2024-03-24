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

import com.example.hospitalNuevo.models.Habitacion;
import com.example.hospitalNuevo.services.HabitacionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/habitaciones")
public class HabitacionRestController {

    @Autowired
    HabitacionService habitacionService;

    @PostMapping(value = "/", headers = "Accept=application/json")
    public Object create(@RequestBody @Valid Habitacion habitacion) {
        Map<String, Object> object = new HashMap<>();
        Habitacion isCreatedHabitacion = habitacionService.create(habitacion);
        if (isCreatedHabitacion == null) {
            object.put("message",
                    "Ya hay una habitación con él nombre: " + habitacion.getNombre() + " intenta nuevamente.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        object.put("message", null);
        object.put("data", isCreatedHabitacion);
        object.put("statusCode", HttpStatus.CREATED);
        return object;
    }

    @GetMapping(value = "/", headers = "Accept=application/json")
    public Object findAll() {
        Map<String, Object> object = new HashMap<>();
        List<Habitacion> habitaciones = habitacionService.findAll();
        if (habitaciones == null) {
            object.put("message", "No se encontraron resultados");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", null);
        object.put("data", habitaciones);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @GetMapping(value = "/{idHabitacion}", headers = "Accept=application/json")
    public Object findById(@PathVariable String idHabitacion) {
        Map<String, Object> object = new HashMap<>();
        Habitacion isExistHabitacion = habitacionService.findById(idHabitacion);
        if (isExistHabitacion == null) {
            object.put("message", "No se encontraron resultados");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", null);
        object.put("data", isExistHabitacion);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @PutMapping(value = "/", headers = "Accept=application/json")
    public Object update(@RequestBody Habitacion habitacion) {
        Map<String, Object> object = new HashMap<>();
        Habitacion isUpdated = habitacionService.update(habitacion);
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

    @DeleteMapping(value = "/{idHabitacion}", headers = "Accept=application/json")
    public Object delete(@PathVariable String idHabitacion) {
        Map<String, Object> object = new HashMap<>();
        Boolean isDeleted = habitacionService.delete(idHabitacion);
        if (isDeleted) {
            object.put("message", "Se ha eliminado la habitación correctamente.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", "La habitación con id: " + idHabitacion + " no existe.");
        object.put("data", null);
        object.put("statusCode", HttpStatus.NOT_FOUND);
        return object;
    }

    @GetMapping(value = "/nombre/{nombre}", headers = "Accept=application/json")
    public Object findByNombre(@PathVariable String nombre) {
        Map<String, Object> object = new HashMap<>();
        Habitacion isExistHabitacion = habitacionService.findByNombre(nombre);
        if (isExistHabitacion == null) {
            object.put("message", "No se encontraron resultados con el nombre: " + nombre);
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        object.put("message", null);
        object.put("data", isExistHabitacion);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }
}
