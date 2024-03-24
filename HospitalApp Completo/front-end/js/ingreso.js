// URL de la API para pacientes
const url = "http://localhost:8080/api/v1/";
$(document).ready(function () {
    listarIngreso();
    obtenerPacienteSelect();
    obtenerMedicoSelect();
    obtenerHabitacionSelect();
    obtenerCamaSelect();

    $('#ingresoForm').on('submit', (event) => {
        event.preventDefault();
        crearIngreso();
    })

    $('#habitacionForm').on('submit', (event) => {
        event.preventDefault();
        crearHabitacion();
    })

    $('#camaForm').on('submit', (event) => {
        event.preventDefault();
        crearCama();
    })

});

function listarIngreso() {
    $.ajax({
        url: url + 'ingresos/',
        type: "GET",
        success: function (result) {
            let cuerpoTabla = document.getElementById("ingresoTable").getElementsByTagName("tbody")[0];
            cuerpoTabla.innerHTML = ""; // Limpiar el cuerpo de la tabla
            if (result.data !== null) {
                let tbody = '<tbody>';
                result.data.forEach(ingreso => {
                    tbody += `<tr>
                        <td>${ingreso.paciente.primerNombre} ${ingreso.paciente.segundoNombre}</td>
                        <td>${ingreso.medico.primerNombre} ${ingreso.medico.segundoNombre}</td>
                        <td>${ingreso.habitacion.nombre}</td>
                        <td>${ingreso.cama.nombre}</td>
                        <td>${ingreso.fechaIngreso != null ? [new Date(ingreso.fechaIngreso).getMonth() + 1,
                        new Date(ingreso.fechaIngreso).getDate(),
                        new Date(ingreso.fechaIngreso).getFullYear()].join('-') + ' ' +
                            [new Date(ingreso.fechaIngreso).getHours(),
                            new Date(ingreso.fechaIngreso).getMinutes(),
                            new Date(ingreso.fechaIngreso).getSeconds()].join(':') : 'No registra'}</td>
                        <td>${ingreso.fechaSalida != null ? [new Date(ingreso.fechaSalida).getMonth() + 1,
                        new Date(ingreso.fechaSalida).getDate(),
                        new Date(ingreso.fechaSalida).getFullYear()].join('-') + ' ' +
                            [new Date(ingreso.fechaSalida).getHours(),
                            new Date(ingreso.fechaSalida).getMinutes(),
                            new Date(ingreso.fechaSalida).getSeconds()].join(':') : 'No registra'}</td>
                        <td>${ingreso.estadoIngreso}</td>
                        <td>${ingreso.estado}</td>
                        <td>
                            <div class="d-flex">
                                <button type="button" class="btn btn-secondary mx-1" onClick="darDeBaja('${ingreso.idIngreso}')" ${ingreso.estado === 'Inactivo' && 'disabled'}><i class="fas fa-user-slash"></i></button>
                            </div>
                        </td>
                    </tr>`;
                });
                tbody += '</tr>';
                cuerpoTabla.innerHTML = tbody;
                new DataTable('#ingresoTable', {
                    search: {
                        return: true
                    }
                });
            } else {
                let trPaciente = document.createElement("tr");
                let celda = document.createElement("td");
                celda.colSpan = 8;
                celda.textContent = result.message;
                trPaciente.style.cssText = "text-align: center;"
                trPaciente.appendChild(celda);
                cuerpoTabla.appendChild(trPaciente);
            }
        },
        error: function (error) {
            alert(error.responseText);
        }
    });
}

//se almacenan los valores
function crearIngreso() {
    var formData = {
        idPaciente: $("#idPaciente").val(),
        idMedico: $("#idMedico").val(),
        idHabitacion: $("#idHabitacion").val(),
        idCama: $("#idCama").val(),
        estadoIngreso: $("#estadoIngreso").val()
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url + 'ingresos/',
        data: JSON.stringify(formData),
        dataType: "json",
        success: function (result) {
            alert("Registro exitoso");
            // Limpiar el formulario después de un registro exitoso
            limpiar();
            // Actualizar la lista de médicos después del registro
            listarIngreso();
            obtenerPacienteSelect();
            obtenerMedicoSelect();
            obtenerHabitacionSelect();
            obtenerCamaSelect();
        },
        error: function (error) {
            // Mostrar mensaje de error detallado
            alert(error.responseText);
        }
    });
}

function darDeBaja(idIngreso) {
    console.log('dar de baja', idIngreso)
    $.ajax({
        url: url + `ingresos/darDeBaja/${idIngreso}`,
        type: "PUT",
        success: function (result) {
            if (result.statusCode === 'OK') {
                listarIngreso();
                obtenerPacienteSelect();
                obtenerMedicoSelect();
                obtenerHabitacionSelect();
                obtenerCamaSelect();
            } else {
                alert(result.message)
            }
        },
        error: function (error) {
            alert(error.responseText);
        }
    })
}

function obtenerPacienteSelect() {
    const pacienteSelect = document.getElementById('idPaciente')
    $.ajax({
        url: url + 'pacientes/',
        type: "GET",
        success: function (result) {
            if (result.data !== null) {
                let options = '<option value="">Seleccione una opción</option>'
                result.data.forEach(paciente => {
                    options += `<option value="${paciente.idPaciente}" ${paciente.estado === 'Inactivo' && 'disabled'}>${paciente.primerNombre} ${paciente.segundoNombre} - ${paciente.documentoIdentidad}</option>`
                })
                pacienteSelect.innerHTML = options;
            } else {
                pacienteSelect.innerHTML = '<option value="">No se encontraron resultados</option>'
            }
        },
        error: function (error) {
            alert(error.responseText);
        }
    });
}

function obtenerMedicoSelect() {
    const medicoSelect = document.getElementById('idMedico')
    $.ajax({
        url: url + 'medicos/',
        type: "GET",
        success: function (result) {
            if (result.data !== null) {
                let options = '<option value="">Seleccione una opción</option>'
                result.data.forEach(medico => {
                    options += `<option value="${medico.idMedico}" ${medico.estado === 'Inactivo' && 'disabled'}>${medico.primerNombre} ${medico.segundoNombre} - ${medico.documentoIdentidad}</option>`
                })
                medicoSelect.innerHTML = options;
            } else {
                medicoSelect.innerHTML = '<option value="">No se encontraron resultados</option>'
            }
        },
        error: function (error) {
            alert(error.responseText);
        }
    });
}

function obtenerHabitacionSelect() {
    const habitacionSelect = document.getElementById('idHabitacion')
    $.ajax({
        url: url + 'habitaciones/',
        type: "GET",
        success: function (result) {
            if (result.data !== null) {
                let options = '<option value="">Seleccione una opción</option>'
                result.data.forEach(habitacion => {
                    options += `<option value="${habitacion.idHabitacion}">${habitacion.nombre}</option>`
                })
                habitacionSelect.innerHTML = options;
            } else {
                habitacionSelect.innerHTML = '<option value="">No se encontraron resultados</option>'
            }
        },
        error: function (error) {
            alert(error.responseText);
        }
    });
}

function obtenerCamaSelect() {
    const camaSelect = document.getElementById('idCama')
    $.ajax({
        url: url + 'camas/',
        type: "GET",
        success: function (result) {
            if (result.data !== null) {
                let options = '<option value="">Seleccione una opción</option>'
                result.data.forEach(cama => {
                    console.log(cama.estado === 'Activo' ? true : false)
                    options += `<option value="${cama.idCama}" ${cama.estado === 'Activo' && 'disabled'}>${cama.nombre}</option>`
                })
                camaSelect.innerHTML = options;
            } else {
                camaSelect.innerHTML = '<option value="">No se encontraron resultados</option>'
            }
        },
        error: function (error) {
            alert(error.responseText);
        }
    });
}

function crearHabitacion() {
    var formData = {
        nombre: $("#nombreHabitacion").val(),
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url + 'habitaciones/',
        data: JSON.stringify(formData),
        dataType: "json",
        success: function (result) {
            if (result.statusCode === 'NOT_FOUND') {
                alert(result.message)
            } else {
                alert("Registro exitoso");
                limpiar();
                // Actualizar la lista de habitaciones del select
                obtenerHabitacionSelect();
            }
        },
        error: function (error) {
            // Mostrar mensaje de error detallado
            alert(error.responseText);
        }
    });
}

function crearCama() {
    var formData = {
        nombre: $("#nombreCama").val(),
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url + 'camas/',
        data: JSON.stringify(formData),
        dataType: "json",
        success: function (result) {
            if (result.statusCode === 'NOT_FOUND') {
                alert(result.message)
            } else {
                alert("Registro exitoso");
                limpiar();
                // Actualizar la lista de habitaciones del select
                obtenerCamaSelect();
            }
        },
        error: function (error) {
            // Mostrar mensaje de error detallado
            alert(error.responseText);
        }
    });
}

function limpiar() {
    document.getElementById("idPaciente").value = "";
    document.getElementById("idMedico").value = "";
    document.getElementById("idHabitacion").value = "";
    document.getElementById("idCama").value = "";
    document.getElementById("estadoIngreso").value = "";
}

