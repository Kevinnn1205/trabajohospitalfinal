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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
// https://www.baeldung.com/intro-to-project-lombok
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity()
@Table(name = "tbl_medicos")
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id_medico")
	private String idMedico;

	@NotNull(message = "Por favor ingresa el documentoIdentidad")
    @NotBlank(message = "Este campo no puede estar vacío")
	@Size(min = 4, max = 11, message = "El número de documento debe de tener mínimo 4 y máximo 11 carácteres")
    @Column(name = "documento_identidad", nullable = false, length = 11, unique = true)
    private String documentoIdentidad;

    @NotNull(message = "Por favor ingresa el primerNombre")
    @NotBlank(message = "Este campo no puede estar vacío")
    @Column(name = "primer_nombre", nullable = false, length = 36)
    private String primerNombre;

    @NotNull(message = "Por favor ingresa el segundoNombre")
    @NotBlank(message = "Este campo no puede estar vacío")
    @Column(name = "segundo_nombre", nullable = false, length = 36)
    private String segundoNombre;

    @NotNull(message = "Por favor ingresa el primerApellido")
    @NotBlank(message = "Este campo no puede estar vacío")
    @Column(name = "primer_apellido", nullable = false, length = 36)
    private String primerApellido;

    @NotNull(message = "Por favor ingresa el segundoApellido")
    @NotBlank(message = "Este campo no puede estar vacío")
    @Column(name = "segundo_apellido", nullable = false, length = 36)
    private String segundoApellido;

    @NotNull(message = "Por favor ingresa el celular")
    @Size(min = 10, max = 13, message = "El número de celular debe de tener mínimo 10 y máximo 13 carácteres")
    @Column(name = "celular", nullable = false, length = 13)
    private String celular;

    @NotNull(message = "Por favor ingresa el correo")
    @Email(message = "Ingresa un correo válido")
    @Column(name = "correo", nullable = false, length = 100, unique = true)
    private String correo;

	@Column(length = 10, columnDefinition = "varchar(10) default 'Activo'")
	@Enumerated(EnumType.STRING)
	private Estado estado = Estado.Activo;

	public Medico() {
		super();
	}

	public Medico(String idMedico, String documentoIdentidad, String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido, String celular, String correo, Estado estado) {
		this.idMedico = idMedico;
		this.documentoIdentidad = documentoIdentidad;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.celular = celular;
		this.correo = correo;
		this.estado = estado;
	}

}
