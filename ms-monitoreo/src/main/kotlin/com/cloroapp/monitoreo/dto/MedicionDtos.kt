package com.cloroapp.monitoreo.dto

import com.cloroapp.monitoreo.model.Medicion
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class MedicionResponse(
    val id: Long,
    val codigoPunto: String,
    val valorPpm: Double,
    val fechaHoraMillis: Long,
    val observacion: String,
    val registradoPor: String
)

data class CrearMedicionRequest(
    @field:NotBlank(message = "El código de punto es obligatorio")
    @field:Size(max = 32, message = "El código de punto no puede superar 32 caracteres")
    val codigoPunto: String,

    @field:NotNull(message = "El valor en ppm es obligatorio")
    @field:DecimalMin(value = "0.0", message = "El valor en ppm no puede ser negativo")
    @field:DecimalMax(value = "50.0", message = "El valor en ppm excede el límite permitido (50.0)")
    val valorPpm: Double,

    val fechaHoraMillis: Long? = null,

    @field:Size(max = 500, message = "La observación no puede superar 500 caracteres")
    val observacion: String? = "",

    @field:Size(max = 100, message = "El campo registradoPor no puede superar 100 caracteres")
    val registradoPor: String? = "Operador"
)

data class ActualizarMedicionRequest(
    @field:DecimalMin(value = "0.0", message = "El valor en ppm no puede ser negativo")
    @field:DecimalMax(value = "50.0", message = "El valor en ppm excede el límite permitido (50.0)")
    val valorPpm: Double? = null,

    val fechaHoraMillis: Long? = null,

    @field:Size(max = 500, message = "La observación no puede superar 500 caracteres")
    val observacion: String? = null,

    @field:Size(max = 100, message = "El campo registradoPor no puede superar 100 caracteres")
    val registradoPor: String? = null
)

fun Medicion.toResponse(): MedicionResponse = MedicionResponse(
    id = requireNotNull(id) { "La medición persistida debe tener un ID" },
    codigoPunto = codigoPunto,
    valorPpm = valorPpm,
    fechaHoraMillis = fechaHoraMillis,
    observacion = observacion,
    registradoPor = registradoPor
)
