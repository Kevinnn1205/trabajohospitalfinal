package com.example.hospitalNuevo.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.hospitalNuevo.dto.IngresoDto;
import com.example.hospitalNuevo.constants.Estado;
import com.example.hospitalNuevo.models.Cama;
import com.example.hospitalNuevo.models.Habitacion;
import com.example.hospitalNuevo.models.Ingreso;
import com.example.hospitalNuevo.models.Medico;
import com.example.hospitalNuevo.models.Paciente;
import com.example.hospitalNuevo.repositories.IngresoRepository;

@Service
public class IngresoService {

    @Autowired
    IngresoRepository ingresoRepository;
    @Autowired
    MedicoService medicoService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    HabitacionService habitacionService;
    @Autowired
    CamaService camaService;

    // Método para crear un ingreso
    public Object create(IngresoDto ingresoDto) {
        Map<String, Object> object = new HashMap<>();
        // Validaciones del médico
        Medico isExistMedico = medicoService.findById(ingresoDto.getIdMedico());
        if (isExistMedico == null) {
            object.put("message", "Él médico ingresado no existe.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        if (isExistMedico != null && isExistMedico.getEstado() == Estado.Inactivo) {
            object.put("message", "No se puede crear el ingreso por qué el estado del médico es inactivo.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.CONFLICT);
            return object;
        }
        // Validaciones paciente
        Paciente isExistPaciente = pacienteService.findById(ingresoDto.getIdPaciente());
        if (isExistPaciente == null) {
            object.put("message", "El paciente ingresado no existe.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.NOT_FOUND);
            return object;
        }
        if (isExistMedico != null && isExistPaciente.getEstado() == Estado.Inactivo) {
            object.put("message", "No se puede crear el ingreso por qué el estado del paciente es inactivo.");
            object.put("data", null);
            object.put("statusCode", HttpStatus.CONFLICT);
            return object;
        }
        // Validar si el paciente tiene un ingreso
        List<Ingreso> ingresosPaciente = ingresoRepository
                .findByPacienteIdPacienteAndEstado(isExistPaciente.getIdPaciente(), Estado.Activo);
        if (ingresosPaciente.isEmpty()) {
            // Valido la habitación seleccionada...
            Habitacion isExistHabitacion = habitacionService.findById(ingresoDto.getIdHabitacion());
            if (isExistHabitacion == null) {
                object.put("message", "La habitación ingresada no existe.");
                object.put("data", null);
                object.put("statusCode", HttpStatus.NOT_FOUND);
                return object;
            }
            // Valido si la cama existe...
            Cama isExistCama = camaService.findById(ingresoDto.getIdCama());
            if (isExistCama == null) {
                object.put("message", "La cama seleccionada no existe.");
                object.put("data", null);
                object.put("statusCode", HttpStatus.NOT_FOUND);
                return object;
            }
            // Valido que el estado de la cama no esté en activo
            if (isExistCama.getEstado() == Estado.Activo) {
                object.put("message",
                        "La cama seleccionada ya se encuentra ocupada por otro paciente, selecciona otra.");
                object.put("data", null);
                object.put("statusCode", HttpStatus.CONFLICT);
                return object;
            } else {
                Date fechaActual = new Date();
                // Una vez validado todos los datos, activo la cama usada para el paciente
                isExistCama.setEstado(Estado.Activo);
                // Procedo a crear el ingreso
                Ingreso crearIngreso = new Ingreso();
                crearIngreso.setPaciente(isExistPaciente);
                crearIngreso.setMedico(isExistMedico);
                crearIngreso.setHabitacion(isExistHabitacion);
                crearIngreso.setCama(isExistCama);
                crearIngreso.setFechaIngreso(fechaActual);
                crearIngreso.setEstadoIngreso(ingresoDto.getEstadoIngreso());
                // Guardo la informacion en la base de datos
                ingresoRepository.save(crearIngreso);
                object.put("message", null);
                object.put("data", crearIngreso);
                object.put("statusCode", HttpStatus.CREATED);
                return object;
            }
        } else
            return "El paciente ya tiene un ingreso registrado, intenta nuevamente.";

    }

    // Método para obtener los ingresos y sus médicos
    public List<Ingreso> findAll() {
        List<Ingreso> pacientes = ingresoRepository.findAll();
        if (pacientes.isEmpty()) {
            return null;
        } else
            return pacientes;
    }

    // Método para buscar una habitación por id
    @SuppressWarnings("null")
    public Ingreso findById(String idIngreso) {
        Ingreso isExistIngreso = ingresoRepository.findById(idIngreso).orElse(null);
        if (isExistIngreso == null) {
            return null;
        } else
            return isExistIngreso;
    }

    // Método para generar salida del paciente
    public Ingreso darDeBaja(String idIngreso) {
        // Valido si existe el ingreso
        Ingreso isExistIngreso = this.findById(idIngreso);
        if (isExistIngreso == null) {
            return null;
        }
        // Genero los cambios en sus estados para generar la salida del paciente
        Date fechaActual = new Date();
        isExistIngreso.setEstado(Estado.Inactivo);
        isExistIngreso.setFechaSalida(fechaActual);
        isExistIngreso.getCama().setEstado(Estado.Inactivo);
        return ingresoRepository.save(isExistIngreso);
    }

}
