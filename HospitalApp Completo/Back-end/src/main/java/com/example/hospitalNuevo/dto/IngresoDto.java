package com.example.hospitalNuevo.dto;

import com.example.hospitalNuevo.constants.EstadoIngreso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngresoDto {
    @NotNull(message = "Por favor ingresa el idPaciente")
    @NotBlank(message = "Este campo no puede estar vacío")
    private String idPaciente;
    @NotNull(message = "Por favor ingresa el idMedico")
    @NotBlank(message = "Este campo no puede estar vacío")
    private String idMedico;
    @NotNull(message = "Por favor ingresa el idHabitacion")
    @NotBlank(message = "Este campo no puede estar vacío")
    private String idHabitacion;
    @NotNull(message = "Por favor ingresa el idCama")
    @NotBlank(message = "Este campo no puede estar vacío")
    private String idCama;
    @NotNull(message = "Por favor ingresa el estadoIngreso")
    private EstadoIngreso estadoIngreso;

    public IngresoDto() {
        super();
    }


    public IngresoDto(String idPaciente, String idMedico, String idHabitacion, String idCama, EstadoIngreso estadoIngreso) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idHabitacion = idHabitacion;
        this.idCama = idCama;
        this.estadoIngreso = estadoIngreso;
    }
    

}
