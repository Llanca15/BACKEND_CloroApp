package com.cloroapp.alertas.service

import com.cloroapp.alertas.dto.ActualizarAlertaRequest
import com.cloroapp.alertas.dto.AlertaResponse
import com.cloroapp.alertas.dto.CerrarAlertaRequest
import com.cloroapp.alertas.dto.CrearAlertaRequest
import com.cloroapp.alertas.dto.toResponse
import com.cloroapp.alertas.exception.ResourceNotFoundException
import com.cloroapp.alertas.model.Alerta
import com.cloroapp.alertas.repository.AccionCorrectivaRepository
import com.cloroapp.alertas.repository.AlertaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AlertaService(
    private val alertaRepository: AlertaRepository,
    private val accionRepository: AccionCorrectivaRepository
) {

    @Transactional(readOnly = true)
    fun listar(estado: String?): List<AlertaResponse> {
        val alertas = if (estado.isNullOrBlank()) {
            alertaRepository.findAllByOrderByFechaHoraMillisDesc()
        } else {
            alertaRepository.findByEstadoIgnoreCaseOrderByFechaHoraMillisDesc(estado.trim())
        }
        return alertas.map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun obtenerPorId(id: Long): AlertaResponse {
        val alerta = alertaRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Alerta no encontrada con ID: $id") }
        return alerta.toResponse()
    }

    fun crear(request: CrearAlertaRequest): AlertaResponse {
        val entidad = Alerta(
            codigoPunto = request.codigoPunto.trim().uppercase(),
            valorPpm = request.valorPpm,
            tipo = request.tipo.trim().uppercase(),
            severidad = request.severidad.trim().uppercase(),
            estado = request.estado?.trim()?.uppercase() ?: "ABIERTA",
            fechaHoraMillis = request.fechaHoraMillis ?: System.currentTimeMillis()
        )
        return alertaRepository.save(entidad).toResponse()
    }

    fun actualizar(id: Long, request: ActualizarAlertaRequest): AlertaResponse {
        val alerta = alertaRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Alerta no encontrada con ID: $id") }

        request.codigoPunto?.takeIf { it.isNotBlank() }?.let { alerta.codigoPunto = it.trim().uppercase() }
        request.valorPpm?.let { alerta.valorPpm = it }
        request.tipo?.takeIf { it.isNotBlank() }?.let { alerta.tipo = it.trim().uppercase() }
        request.severidad?.takeIf { it.isNotBlank() }?.let { alerta.severidad = it.trim().uppercase() }
        request.estado?.takeIf { it.isNotBlank() }?.let { alerta.estado = it.trim().uppercase() }
        request.fechaHoraMillis?.let { alerta.fechaHoraMillis = it }
        request.cerradaPor?.let { alerta.cerradaPor = it.trim() }
        request.fechaCierreMillis?.let { alerta.fechaCierreMillis = it }
        request.observacionCierre?.let { alerta.observacionCierre = it.trim() }

        return alertaRepository.save(alerta).toResponse()
    }

    fun cerrar(id: Long, request: CerrarAlertaRequest): AlertaResponse {
        val alerta = alertaRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Alerta no encontrada con ID: $id") }

        alerta.estado = "CERRADA"
        alerta.cerradaPor = request.cerradaPor?.trim()?.takeIf { it.isNotBlank() } ?: "Supervisor"
        alerta.fechaCierreMillis = request.fechaCierreMillis ?: System.currentTimeMillis()
        alerta.observacionCierre = request.observacionCierre?.trim().orEmpty()

        return alertaRepository.save(alerta).toResponse()
    }

    fun eliminar(id: Long) {
        if (!alertaRepository.existsById(id)) {
            throw ResourceNotFoundException("Alerta no encontrada con ID: $id")
        }
        val acciones = accionRepository.findByAlertaIdOrderByFechaHoraMillisDesc(id)
        accionRepository.deleteAll(acciones)
        alertaRepository.deleteById(id)
    }
}
