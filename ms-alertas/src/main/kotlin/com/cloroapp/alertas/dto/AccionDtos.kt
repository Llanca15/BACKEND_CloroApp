package com.cloroapp.alertas.dto

import com.cloroapp.alertas.model.AccionCorrectiva
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AccionResponse(
    val id: Long,
    val alertaId: Long,
    val codigoPunto: String,
    val descripcion: String,
    val responsable: String,
    val fechaHoraMillis: Long,
    val estadoResolucion: String,
    val fotoRuta: String?
)

data class CrearAccionRequest(
    @field:NotNull(message = "El ID de la alerta es obligatorio")
    val alertaId: Long,

    @field:NotBlank(message = "El código del punto es obligatorio")
    @field:Size(max = 32, message = "El código de punto no puede superar 32 caracteres")
    val codigoPunto: String,

    @field:NotBlank(message = "La descripción de la acción es obligatoria")
    @field:Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    val descripcion: String,

    @field:NotBlank(message = "El responsable de la acción es obligatorio")
    @field:Size(max = 100, message = "El responsable no puede superar 100 caracteres")
    val responsable: String,

    val fechaHoraMillis: Long? = null,

    @field:Size(max = 30, message = "El estado no puede superar 30 caracteres")
    val estadoResolucion: String? = "REGISTRADA",

    @field:Size(max = 300, message = "La ruta de la foto no puede superar 300 caracteres")
    val fotoRuta: String? = null
)

fun AccionCorrectiva.toResponse(): AccionResponse = AccionResponse(
    id = requireNotNull(id) { "La acción persistida debe tener un ID" },
    alertaId = alertaId,
    codigoPunto = codigoPunto,
    descripcion = descripcion,
    responsable = responsable,
    fechaHoraMillis = fechaHoraMillis,
    estadoResolucion = estadoResolucion,
    fotoRuta = fotoRuta
)
