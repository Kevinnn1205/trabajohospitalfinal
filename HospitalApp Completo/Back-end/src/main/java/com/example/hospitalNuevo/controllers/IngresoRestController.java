package com.example.hospitalNuevo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospitalNuevo.dto.IngresoDto;
import com.example.hospitalNuevo.models.Ingreso;
import com.example.hospitalNuevo.services.IngresoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ingresos")
public class IngresoRestController {

    @Autowired
    IngresoService ingresoService;

    @PostMapping(value = "/", headers = "Accept=application/json")
    public Object create(@RequestBody @Valid IngresoDto ingresoDto) {
        return ingresoService.create(ingresoDto);
    }

    @GetMapping(value = "/", headers = "Accept=application/json")
    public Object findAll() {
        Map<String, Object> object = new HashMap<>();
        List<Ingreso> ingresos = ingresoService.findAll();
        if (ingresos == null) {
            object.put("message", "No se encontraron resultados");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
        object.put("message", null);
        object.put("data", ingresos);
        object.put("statusCode", HttpStatus.OK);
        return object;
    }

    @PutMapping(value = "/darDeBaja/{idIngreso}", headers = "Accept=application/json")
    public Object darDeBaja(@PathVariable String idIngreso) {
        Map<String, Object> object = new HashMap<>();
        Ingreso isUpdated = ingresoService.darDeBaja(idIngreso);
        if (isUpdated == null) {
            object.put("message", "Ocurrió un problema al dar de baja, intenta nuevamente.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.CONFLICT);
            return object;
        } else {
            object.put("message", "Se ha dado de baja correctamente.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.OK);
            return object;
        }
    }

}
