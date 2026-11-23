package com.cloroapp.alertas.service

import com.cloroapp.alertas.dto.AccionResponse
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
        if (!alertaRepository.existsById(request.alertaId)) {
            throw ResourceNotFoundException("No se puede registrar la acción. La alerta con ID ${request.alertaId} no existe")
        }

        val entidad = AccionCorrectiva(
            alertaId = request.alertaId,
            codigoPunto = request.codigoPunto.trim().uppercase(),
            descripcion = request.descripcion.trim(),
            responsable = request.responsable.trim(),
            fechaHoraMillis = request.fechaHoraMillis ?: System.currentTimeMillis(),
            estadoResolucion = request.estadoResolucion?.trim()?.uppercase() ?: "REGISTRADA",
            fotoRuta = request.fotoRuta?.trim()
        )

        return accionRepository.save(entidad).toResponse()
    }

    fun eliminar(id: Long) {
        if (!accionRepository.existsById(id)) {
            throw ResourceNotFoundException("Acción correctiva no encontrada con ID: $id")
        }
        accionRepository.deleteById(id)
    }
}
