package com.cloroapp.alertas.service

import com.cloroapp.alertas.dto.AccionResponse
import com.cloroapp.alertas.dto.ActualizarAccionRequest
import com.cloroapp.alertas.dto.CrearAccionRequest
import com.cloroapp.alertas.dto.toResponse
import com.cloroapp.alertas.exception.ResourceNotFoundException
import com.cloroapp.alertas.model.AccionCorrectiva
import com.cloroapp.alertas.repository.AccionCorrectivaRepository
import com.cloroapp.alertas.repository.AlertaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccionCorrectivaService(
    private val accionRepository: AccionCorrectivaRepository,
    private val alertaRepository: AlertaRepository
) {

    @Transactional(readOnly = true)
    fun listar(alertaId: Long?): List<AccionResponse> {
        val acciones = if (alertaId == null) {
            accionRepository.findAllByOrderByFechaHoraMillisDesc()
        } else {
            accionRepository.findByAlertaIdOrderByFechaHoraMillisDesc(alertaId)
        }
        return acciones.map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun obtenerPorId(id: Long): AccionResponse {
        val accion = accionRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Acción correctiva no encontrada con ID: $id") }
        return accion.toResponse()
    }

    fun crear(request: CrearAccionRequest): AccionResponse {
        val alerta = alertaRepository.findById(request.alertaId)
            .orElseThrow { ResourceNotFoundException("No se puede registrar la acción. La alerta con ID ${request.alertaId} no existe") }

        val codigoPuntoFinal = request.codigoPunto?.trim()?.takeIf { it.isNotBlank() } ?: alerta.codigoPunto

        val entidad = AccionCorrectiva(
            alertaId = request.alertaId,
            codigoPunto = codigoPuntoFinal.uppercase(),
            descripcion = request.descripcion.trim(),
            responsable = request.responsable.trim(),
            fechaHoraMillis = request.fechaHoraMillis ?: System.currentTimeMillis(),
            estadoResolucion = request.estadoResolucion?.trim()?.uppercase() ?: "REGISTRADA",
            fotoRuta = request.fotoRuta?.trim()
        )

        return accionRepository.save(entidad).toResponse()
    }

    fun actualizar(id: Long, request: ActualizarAccionRequest): AccionResponse {
        val accion = accionRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Acción correctiva no encontrada con ID: $id") }

        request.descripcion?.takeIf { it.isNotBlank() }?.let { accion.descripcion = it.trim() }
        request.responsable?.takeIf { it.isNotBlank() }?.let { accion.responsable = it.trim() }
        request.fechaHoraMillis?.let { accion.fechaHoraMillis = it }
        request.estadoResolucion?.takeIf { it.isNotBlank() }?.let { accion.estadoResolucion = it.trim().uppercase() }
        request.fotoRuta?.let { accion.fotoRuta = it.trim() }

        return accionRepository.save(accion).toResponse()
    }

    fun eliminar(id: Long) {
        if (!accionRepository.existsById(id)) {
            throw ResourceNotFoundException("Acción correctiva no encontrada con ID: $id")
        }
        accionRepository.deleteById(id)
    }
}
