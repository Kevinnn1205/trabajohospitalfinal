package com.example.hospitalNuevo.models;

import java.util.Date;

import com.example.hospitalNuevo.constants.Estado;
import com.example.hospitalNuevo.constants.EstadoIngreso;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity()
@Table(name = "tbl_ingresos")
// Las validaciones de este modelo quedaron dentro de la carpeta dto y en su archivo ingresoDto
public class Ingreso {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_ingreso")
    private String idIngreso;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitacion habitacion;

    @ManyToOne
    @JoinColumn(name = "id_cama")
    private Cama cama;

    @Column(name = "fecha_ingreso", nullable = false)
    private Date fechaIngreso;

    @Column(name = "fecha_salida", nullable = true)
    private Date fechaSalida;

    @Column(name = "estado_ingreso")
    @Enumerated(EnumType.STRING)
    private EstadoIngreso estadoIngreso;

    @Column(length = 10, columnDefinition = "varchar(10) default 'Activo'")
	@Enumerated(EnumType.STRING)
	private Estado estado = Estado.Activo;

    public Ingreso() {
        super();
    }

    public Ingreso(String idIngreso, Paciente paciente, Medico medico, Habitacion habitacion, Date fechaIngreso,
            Date fechaSalida, EstadoIngreso estadoIngreso, Estado estado) {
        this.idIngreso = idIngreso;
        this.paciente = paciente;
        this.medico = medico;
        this.habitacion = habitacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.estadoIngreso = estadoIngreso;
        this.estado = estado;
    }

}
