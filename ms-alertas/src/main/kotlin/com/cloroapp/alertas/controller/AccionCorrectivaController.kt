package com.cloroapp.alertas.controller

import com.cloroapp.alertas.dto.AccionResponse
import com.cloroapp.alertas.dto.ActualizarAccionRequest
import com.cloroapp.alertas.dto.CrearAccionRequest
import com.cloroapp.alertas.service.AccionCorrectivaService
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
@RequestMapping("/api/acciones")
@Tag(name = "Acciones Correctivas", description = "Endpoints para registro de acciones tomadas ante alertas de cloro")
class AccionCorrectivaController(
    private val accionService: AccionCorrectivaService
) {

    @GetMapping
    @Operation(summary = "Listar acciones correctivas", description = "Retorna las acciones registradas, opcionalmente filtradas por alertaId")
    fun listar(
        @RequestParam(required = false) alertaId: Long?
    ): ResponseEntity<List<AccionResponse>> =
        ResponseEntity.ok(accionService.listar(alertaId))

    @GetMapping("/{id}")
    @Operation(summary = "Obtener acción correctiva por ID", description = "Retorna el detalle de una acción correctiva")
    fun obtenerPorId(
        @PathVariable id: Long
    ): ResponseEntity<AccionResponse> =
        ResponseEntity.ok(accionService.obtenerPorId(id))

    @PostMapping
    @Operation(summary = "Registrar acción correctiva", description = "Asocia una nueva medida correctiva a una alerta existente")
    fun crear(
        @Valid @RequestBody request: CrearAccionRequest
    ): ResponseEntity<AccionResponse> {
        val nueva = accionService.crear(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar acción correctiva", description = "Modifica los datos o estado de resolución de una acción correctiva")
    fun actualizar(
        @PathVariable id: Long,
        @RequestBody request: ActualizarAccionRequest
    ): ResponseEntity<AccionResponse> =
        ResponseEntity.ok(accionService.actualizar(id, request))

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar acción correctiva", description = "Elimina un registro de acción correctiva por ID")
    fun eliminar(
        @PathVariable id: Long
    ): ResponseEntity<Unit> {
        accionService.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}
