package com.cloroapp.alertas.controller

import com.cloroapp.alertas.dto.AlertaResponse
import com.cloroapp.alertas.dto.CerrarAlertaRequest
import com.cloroapp.alertas.dto.CrearAlertaRequest
import com.cloroapp.alertas.service.AlertaService
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
@RequestMapping("/api/alertas")
@Tag(name = "Alertas de Cloro", description = "Endpoints para monitoreo y ciclo de vida de alertas por desviaciones de cloro")
class AlertaController(
    private val alertaService: AlertaService
) {

    @GetMapping
    @Operation(summary = "Listar alertas", description = "Retorna alertas registradas, opcionalmente filtradas por estado (ABIERTA, CERRADA)")
    fun listar(
        @RequestParam(required = false) estado: String?
    ): ResponseEntity<List<AlertaResponse>> =
        ResponseEntity.ok(alertaService.listar(estado))

    @GetMapping("/{id}")
    @Operation(summary = "Obtener alerta por ID", description = "Retorna el detalle completo de una alerta")
    fun obtenerPorId(
        @PathVariable id: Long
    ): ResponseEntity<AlertaResponse> =
        ResponseEntity.ok(alertaService.obtenerPorId(id))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Generar nueva alerta", description = "Crea una alerta ante lecturas críticas o advertencias de cloro")
    fun crear(
        @Valid @RequestBody request: CrearAlertaRequest
    ): ResponseEntity<AlertaResponse> {
        val nueva = alertaService.crear(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva)
    }

    @PutMapping("/{id}/cerrar")
    @Operation(summary = "Cerrar y validar alerta", description = "Cierra una alerta validada por un supervisor con sus observaciones")
    fun cerrar(
        @PathVariable id: Long,
        @Valid @RequestBody request: CerrarAlertaRequest
    ): ResponseEntity<AlertaResponse> =
        ResponseEntity.ok(alertaService.cerrar(id, request))

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar alerta", description = "Elimina una alerta y sus acciones correctivas asociadas")
    fun eliminar(
        @PathVariable id: Long
    ): ResponseEntity<Unit> {
        alertaService.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}
