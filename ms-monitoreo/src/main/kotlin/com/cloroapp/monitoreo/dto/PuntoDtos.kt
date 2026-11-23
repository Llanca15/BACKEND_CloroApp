package com.cloroapp.monitoreo.dto

import com.cloroapp.monitoreo.model.PuntoMedicion
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class PuntoMedicionResponse(
    val codigo: String,
    val granja: String,
    val ubicacionGranja: String,
    val galpon: String,
    val ubicacion: String,
    val tipo: String
)

data class CrearPuntoRequest(
    @field:NotBlank(message = "El código del punto es obligatorio")
    @field:Size(max = 32, message = "El código no puede superar 32 caracteres")
    val codigo: String,

    @field:NotBlank(message = "La granja es obligatoria")
    @field:Size(max = 120, message = "La granja no puede superar 120 caracteres")
    val granja: String,

    @field:Size(max = 160, message = "La ubicación de la granja no puede superar 160 caracteres")
    val ubicacionGranja: String = "",

    @field:NotBlank(message = "El galpón es obligatorio")
    @field:Size(max = 80, message = "El galpón no puede superar 80 caracteres")
    val galpon: String,

    @field:NotBlank(message = "La ubicación es obligatoria")
    @field:Size(max = 160, message = "La ubicación no puede superar 160 caracteres")
    val ubicacion: String,

    @field:NotBlank(message = "El tipo es obligatorio")
    @field:Size(max = 60, message = "El tipo no puede superar 60 caracteres")
    val tipo: String
)

data class ActualizarPuntoRequest(
    @field:Size(max = 120, message = "La granja no puede superar 120 caracteres")
    val granja: String? = null,

    @field:Size(max = 160, message = "La ubicación de la granja no puede superar 160 caracteres")
    val ubicacionGranja: String? = null,

    @field:Size(max = 80, message = "El galpón no puede superar 80 caracteres")
    val galpon: String? = null,

    @field:Size(max = 160, message = "La ubicación no puede superar 160 caracteres")
    val ubicacion: String? = null,

    @field:Size(max = 60, message = "El tipo no puede superar 60 caracteres")
    val tipo: String? = null
)

fun PuntoMedicion.toResponse(): PuntoMedicionResponse = PuntoMedicionResponse(
    codigo = codigo,
    granja = granja,
    ubicacionGranja = ubicacionGranja,
    galpon = galpon,
    ubicacion = ubicacion,
    tipo = tipo
)
