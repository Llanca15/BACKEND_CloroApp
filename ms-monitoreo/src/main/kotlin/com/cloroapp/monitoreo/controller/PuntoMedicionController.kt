package com.cloroapp.monitoreo.controller

import com.cloroapp.monitoreo.dto.ActualizarPuntoRequest
import com.cloroapp.monitoreo.dto.CrearPuntoRequest
import com.cloroapp.monitoreo.dto.PuntoMedicionResponse
import com.cloroapp.monitoreo.service.PuntoMedicionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/puntos")
@Tag(name = "Puntos de Medición", description = "Endpoints para gestión CRUD de puntos de monitoreo de cloro en granjas")
class PuntoMedicionController(
    private val puntoService: PuntoMedicionService
) {

    @GetMapping
    @Operation(summary = "Listar puntos de medición", description = "Retorna todos los puntos, opcionalmente filtrados por granja")
    fun listar(
        @RequestParam(required = false) granja: String?
    ): ResponseEntity<List<PuntoMedicionResponse>> =
        ResponseEntity.ok(puntoService.listar(granja))

    @GetMapping("/{codigo}")
    @Operation(summary = "Obtener un punto de medición", description = "Retorna los detalles de un punto según su código único")
    fun obtenerPorCodigo(
        @PathVariable codigo: String
    ): ResponseEntity<PuntoMedicionResponse> =
        ResponseEntity.ok(puntoService.obtenerPorCodigo(codigo))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo punto de medición", description = "Registra un nuevo punto con su galpón y ubicación")
    fun crear(
        @Valid @RequestBody request: CrearPuntoRequest
    ): ResponseEntity<PuntoMedicionResponse> {
        val nuevo = puntoService.crear(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo)
    }

    @PutMapping("/{codigo}")
    @Operation(summary = "Actualizar punto de medición", description = "Modifica los atributos de un punto de medición existente")
    fun actualizar(
        @PathVariable codigo: String,
        @Valid @RequestBody request: ActualizarPuntoRequest
    ): ResponseEntity<PuntoMedicionResponse> =
        ResponseEntity.ok(puntoService.actualizar(codigo, request))

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar punto de medición", description = "Elimina el punto y todas sus mediciones registradas")
    fun eliminar(
        @PathVariable codigo: String
    ): ResponseEntity<Unit> {
        puntoService.eliminar(codigo)
        return ResponseEntity.noContent().build()
    }
}
