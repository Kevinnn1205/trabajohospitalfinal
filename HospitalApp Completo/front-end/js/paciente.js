// URL de la API para pacientes
const url = "http://localhost:8080/api/v1/pacientes/";
$(document).ready(function () {
    listarPaciente();

    $('#pacienteForm').on('submit', (event) => {
        event.preventDefault();
        crearPaciente();
    })

});

function crearPaciente() {
    var formData = {
        documentoIdentidad: $("#documentoIdentidad").val(),
        primerNombre: $("#primerNombre").val(),
        segundoNombre: $("#segundoNombre").val(),
        primerApellido: $("#primerApellido").val(),
        segundoApellido: $("#segundoApellido").val(),
        celular: $("#celular").val(),
        correo: $("#correo").val(),
        nombrePersonaContacto: $("#nombrePersonaContacto").val(),
        telefonoPersonaContacto: $("#telefonoPersonaContacto").val(),
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(formData),
        dataType: "json",
        success: function (result) {
            alert("Registro exitoso");
            // Limpiar el formulario después de un registro exitoso
            limpiar();
            // Actualizar la lista de médicos después del registro
            listarPaciente();
        },
        error: function (error) {
            // Mostrar mensaje de error detallado
            alert(error.responseText);
        }
    });
}

// Función para listar pacientes
function listarPaciente() {
    $.ajax({
        url: url,
        type: "GET",
        success: function (result) {
            let cuerpoTabla = document.getElementById("pacienteTable").getElementsByTagName("tbody")[0];
            cuerpoTabla.innerHTML = ""; // Limpiar el cuerpo de la tabla
            if (result.data !== null) {
                let tbody = '<tbody>';
                result.data.forEach(paciente => {
                    tbody += `<tr>
                        <td>${paciente.documentoIdentidad}</td>
                        <td>${paciente.primerNombre}</td>
                        <td>${paciente.segundoNombre}</td>
                        <td>${paciente.primerApellido}</td>
                        <td>${paciente.segundoApellido}</td>
                        <td>${paciente.celular}</td>
                        <td>${paciente.correo}</td>
                        <td>${paciente.nombrePersonaContacto}</td>
                        <td>${paciente.telefonoPersonaContacto}</td>
                        <td>${paciente.estado}</td>
                        <td>
                            <div class="d-flex">
                                <button type="button" class="btn btn-secondary mx-1" onClick="deshabilitarPaciente('${paciente.idPaciente}')"><i class="fas fa-user-slash"></i></button>
                                <button type="button" class="btn btn-success mx-1"><i class="fas fa-pen-alt"></i></button>
                                <button type="button" class="btn btn-danger mx-1" onClick="eliminarPaciente('${paciente.idPaciente}')"><i class="fas fa-trash"></i></button>
                            </div>
                        </td>
                    </tr>`;
                });
                tbody += '</tr>';
                cuerpoTabla.innerHTML = tbody;
                new DataTable('#pacienteTable', {
                    search: {
                        return: true
                    }
                });
            } else {
                let trPaciente = document.createElement("tr");
                let celda = document.createElement("td");
                celda.colSpan = 11;
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

function deshabilitarPaciente(idPaciente) {
    console.log('deshabilitar', idPaciente)
    $.ajax({
        url: url + `cambiarEstado/${idPaciente}`,
        type: "PUT",
        success: function (result) {
            alert(result.message)
            listarPaciente();
        },
        error: function (error) {
            alert(error.responseText);
        }
    })
}

function eliminarPaciente(idPaciente) {
    console.log('deshabilitar', idPaciente)
    $.ajax({
        url: url + `${idPaciente}`,
        type: "DELETE",
        success: function (result) {
            alert(result.message)
            listarPaciente();
        },
        error: function (error) {
            alert(error.responseText);
        }
    })
}

// Función para limpiar los campos del formulario de registro de paciente
function limpiar() {
    document.getElementById("documentoIdentidad").value = "";
    document.getElementById("primerNombre").value = "";
    document.getElementById("segundoNombre").value = "";
    document.getElementById("primerApellido").value = "";
    document.getElementById("segundoApellido").value = "";
    document.getElementById("celular").value = "";
    document.getElementById("correo").value = "";
    document.getElementById("nombrePersonaContacto").value = "";
    document.getElementById("telefonoPersonaContacto").value = "";
}