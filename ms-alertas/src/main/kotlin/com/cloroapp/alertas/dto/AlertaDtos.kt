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

    val estado: String? = "ABIERTA",

    val fechaHoraMillis: Long? = null
)

data class ActualizarAlertaRequest(
    val codigoPunto: String? = null,
    val valorPpm: Double? = null,
    val tipo: String? = null,
    val severidad: String? = null,
    val estado: String? = null,
    val fechaHoraMillis: Long? = null,
    val cerradaPor: String? = null,
    val fechaCierreMillis: Long? = null,
    val observacionCierre: String? = null
)

data class CerrarAlertaRequest(
    val cerradaPor: String? = "Supervisor",
    val fechaCierreMillis: Long? = null,
    val observacionCierre: String? = null,
    val estado: String? = "CERRADA"
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
