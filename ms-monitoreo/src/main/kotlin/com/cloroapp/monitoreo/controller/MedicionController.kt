package com.cloroapp.monitoreo.controller

import com.cloroapp.monitoreo.dto.ActualizarMedicionRequest
import com.cloroapp.monitoreo.dto.CrearMedicionRequest
import com.cloroapp.monitoreo.dto.MedicionResponse
import com.cloroapp.monitoreo.service.MedicionService
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
@RequestMapping("/api/mediciones")
@Tag(name = "Mediciones de Cloro", description = "Endpoints para registro y consulta de niveles de cloro libre (ppm)")
class MedicionController(
    private val medicionService: MedicionService
) {

    @GetMapping
    @Operation(summary = "Listar mediciones", description = "Retorna el historial de mediciones, opcionalmente filtrado por punto")
    fun listar(
        @RequestParam(required = false) codigoPunto: String?
    ): ResponseEntity<List<MedicionResponse>> =
        ResponseEntity.ok(medicionService.listar(codigoPunto))

    @GetMapping("/ultimas")
    @Operation(summary = "Obtener últimas mediciones por punto", description = "Retorna la medición más reciente de cada punto de monitoreo")
    fun obtenerUltimas(): ResponseEntity<List<MedicionResponse>> =
        ResponseEntity.ok(medicionService.obtenerUltimasPorPunto())

    @GetMapping("/{id}")
    @Operation(summary = "Obtener medición por ID", description = "Retorna el detalle de una medición específica")
    fun obtenerPorId(
        @PathVariable id: Long
    ): ResponseEntity<MedicionResponse> =
        ResponseEntity.ok(medicionService.obtenerPorId(id))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar nueva medición de cloro", description = "Almacena una lectura de cloro en ppm asociada a un punto")
    fun registrar(
        @Valid @RequestBody request: CrearMedicionRequest
    ): ResponseEntity<MedicionResponse> {
        val nueva = medicionService.crear(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar medición", description = "Modifica los datos de una medición registrada previamente")
    fun actualizar(
        @PathVariable id: Long,
        @Valid @RequestBody request: ActualizarMedicionRequest
    ): ResponseEntity<MedicionResponse> =
        ResponseEntity.ok(medicionService.actualizar(id, request))

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar medición", description = "Elimina un registro de medición por su ID")
    fun eliminar(
        @PathVariable id: Long
    ): ResponseEntity<Unit> {
        medicionService.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}
