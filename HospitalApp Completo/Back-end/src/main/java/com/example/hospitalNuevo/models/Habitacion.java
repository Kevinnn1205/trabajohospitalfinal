package com.example.hospitalNuevo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity()
@Table(name = "tbl_habitaciones")
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_habitacion")
    private String idHabitacion;

    @NotNull(message = "Por favor ingresa el nombre")
    @NotBlank(message = "El nombre de la habitación no puede ser vacía.")
    @Column(name = "nombre", nullable = false, length = 36)
	private String nombre;
}
