package com.example.hospitalNuevo.models;

import com.example.hospitalNuevo.constants.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tbl_camas")
public class Cama {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_cama")
    private String idCama;

    @NotNull(message = "Por favor ingresa el nombre")
    @NotBlank(message = "El nombre de la habitación no puede ser vacía.")
    @Column(name = "nombre", nullable = false, length = 36)
    private String nombre;

    @Column(length = 10, columnDefinition = "varchar(10) default 'Inactivo'")
	@Enumerated(EnumType.STRING)
	private Estado estado = Estado.Inactivo;
}
