// se almacena la url de la API
let url = "http://localhost:8080/api/v1/medicos/";

$(document).ready(function () {
    listarMedico();

    $('#medicoForm').on('submit', (event) => {
        event.preventDefault();
        registrarMedico();
    })

});

// se almacenan los valores
function registrarMedico() {
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
            listarMedico();
        },
        error: function (error) {
            // Mostrar mensaje de error detallado
            alert(error.responseText);
        }
    });
}

function listarMedico() {
    // método para alistar los médicos
    // se crea la petición AJAX
    $.ajax({
        url: url,
        type: "GET",
        success: function (result) {
            let cuerpoTabla = document.getElementById("medicoTabla").getElementsByTagName("tbody")[0];
            cuerpoTabla.innerHTML = ""; // Limpiar el cuerpo de la tabla
            if (result.data !== null) {
                let tbody = '<tbody>';
                result.data.forEach(medico => {
                    tbody += `<tr>
                        <td>${medico.documentoIdentidad}</td>
                        <td>${medico.primerNombre}</td>
                        <td>${medico.segundoNombre}</td>
                        <td>${medico.primerApellido}</td>
                        <td>${medico.segundoApellido}</td>
                        <td>${medico.celular}</td>
                        <td>${medico.correo}</td>
                        <td>${medico.estado}</td>
                        <td>
                            <div class="d-flex">
                                <button type="button" class="btn btn-secondary mx-1" onClick="deshabilitarMedico('${medico.idMedico}')"><i class="fas fa-user-slash"></i></button>
                                <button type="button" class="btn btn-success mx-1"><i class="fas fa-pen-alt"></i></button>
                                <button type="button" class="btn btn-danger mx-1" onClick="eliminarMedico('${medico.idMedico}')"><i class="fas fa-trash"></i></button>
                            </div>
                        </td>
                    </tr>`;
                });
                tbody += '</tr>';
                cuerpoTabla.innerHTML = tbody;
                new DataTable('#medicoTabla', {
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

function deshabilitarMedico(idMedico) {
    console.log('deshabilitar', idMedico)
    $.ajax({
        url: url + `cambiarEstado/${idMedico}`,
        type: "PUT",
        success: function (result) {
            alert(result.message)
            listarMedico();
        },
        error: function (error) {
            alert(error.responseText);
        }
    })
}

function eliminarMedico(idMedico) {
    console.log('deshabilitar', idMedico)
    $.ajax({
        url: url + `${idMedico}`,
        type: "DELETE",
        success: function (result) {
            alert(result.message)
            listarMedico();
        },
        error: function (error) {
            alert(error.responseText);
        }
    })
}

function validarDocumento_identidad(cuadroNumero) {
    let valor = cuadroNumero.value;
    let valido = true;
    if (valor.length <= 1 || valor.length > 11) {
        valido = false;
    }

    if (valido) {
        // cuadro de texto cumple
        // se modifica la clase del cuadro de texto
        cuadroNumero.className = "form-control is-valid";
    } else {
        // cuadro de texto no cumple
        cuadroNumero.className = "form-control is-invalid"
    }
    return valido;
}

function limpiar() {
    document.getElementById("documentoIdentidad").value = "";
    document.getElementById("primerNombre").value = "";
    document.getElementById("segundoNombre").value = "";
    document.getElementById("primerApellido").value = "";
    document.getElementById("segundoApellido").value = "";
    document.getElementById("celular").value = "";
    document.getElementById("correo").value = "";
}
