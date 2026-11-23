package com.cloroapp.alertas.dto

import com.cloroapp.alertas.model.Alerta
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AlertaResponse(
    val id: Long,
    val codigoPunto: String,
    val valorPpm: Double,
    val tipo: String,
    val severidad: String,
    val estado: String,
    val fechaHoraMillis: Long,
    val cerradaPor: String?,
    val fechaCierreMillis: Long?,
    val observacionCierre: String?
)

data class CrearAlertaRequest(
    @field:NotBlank(message = "El código del punto es obligatorio")
    @field:Size(max = 32, message = "El código de punto no puede superar 32 caracteres")
    val codigoPunto: String,

    @field:NotNull(message = "El valor en ppm es obligatorio")
    @field:DecimalMin(value = "0.0", message = "El valor en ppm no puede ser negativo")
    @field:DecimalMax(value = "50.0", message = "El valor en ppm excede el límite permitido")
    val valorPpm: Double,

    @field:NotBlank(message = "El tipo de desviación es obligatorio (INSUFICIENTE o EXCESIVO)")
    val tipo: String,

    @field:NotBlank(message = "La severidad es obligatoria (ADVERTENCIA o CRITICO)")
    val severidad: String,

    val fechaHoraMillis: Long? = null
)

data class CerrarAlertaRequest(
    @field:NotBlank(message = "El usuario que cierra la alerta es obligatorio")
    @field:Size(max = 100, message = "El nombre del usuario no puede superar 100 caracteres")
    val cerradaPor: String,

    @field:Size(max = 500, message = "La observación de cierre no puede superar 500 caracteres")
    val observacionCierre: String? = null
)

fun Alerta.toResponse(): AlertaResponse = AlertaResponse(
    id = requireNotNull(id) { "La alerta persistida debe tener un ID" },
    codigoPunto = codigoPunto,
    valorPpm = valorPpm,
    tipo = tipo,
    severidad = severidad,
    estado = estado,
    fechaHoraMillis = fechaHoraMillis,
    cerradaPor = cerradaPor,
    fechaCierreMillis = fechaCierreMillis,
    observacionCierre = observacionCierre
)
