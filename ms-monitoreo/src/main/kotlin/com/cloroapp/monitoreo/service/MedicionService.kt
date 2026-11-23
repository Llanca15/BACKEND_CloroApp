package com.cloroapp.monitoreo.service

import com.cloroapp.monitoreo.dto.ActualizarMedicionRequest
import com.cloroapp.monitoreo.dto.CrearMedicionRequest
import com.cloroapp.monitoreo.dto.MedicionResponse
import com.cloroapp.monitoreo.dto.toResponse
import com.cloroapp.monitoreo.exception.ResourceNotFoundException
import com.cloroapp.monitoreo.model.Medicion
import com.cloroapp.monitoreo.repository.MedicionRepository
import com.cloroapp.monitoreo.repository.PuntoMedicionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MedicionService(
    private val medicionRepository: MedicionRepository,
    private val puntoRepository: PuntoMedicionRepository
) {

    @Transactional(readOnly = true)
    fun listar(codigoPunto: String?): List<MedicionResponse> {
        val mediciones = if (codigoPunto.isNullOrBlank()) {
            medicionRepository.findAllByOrderByFechaHoraMillisDescIdDesc()
        } else {
            medicionRepository.findByCodigoPuntoOrderByFechaHoraMillisDescIdDesc(codigoPunto.trim().uppercase())
        }
        return mediciones.map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun obtenerUltimasPorPunto(): List<MedicionResponse> {
        val puntos = puntoRepository.findAllByOrderByCodigoAsc()
        return puntos.mapNotNull { punto ->
            medicionRepository.findTopByCodigoPuntoOrderByFechaHoraMillisDescIdDesc(punto.codigo)?.toResponse()
        }
    }

    @Transactional(readOnly = true)
    fun obtenerPorId(id: Long): MedicionResponse {
        val medicion = medicionRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Medición no encontrada con ID: $id") }
        return medicion.toResponse()
    }

    fun crear(request: CrearMedicionRequest): MedicionResponse {
        val codigoNormalizado = request.codigoPunto.trim().uppercase()
        if (!puntoRepository.existsById(codigoNormalizado)) {
            throw ResourceNotFoundException("No se puede registrar la medición. El punto '$codigoNormalizado' no existe")
        }

        val entidad = Medicion(
            codigoPunto = codigoNormalizado,
            valorPpm = request.valorPpm,
            fechaHoraMillis = request.fechaHoraMillis ?: System.currentTimeMillis(),
            observacion = request.observacion?.trim().orEmpty(),
            registradoPor = request.registradoPor?.trim()?.takeIf { it.isNotBlank() } ?: "Operador"
        )

        return medicionRepository.save(entidad).toResponse()
    }

    fun actualizar(id: Long, request: ActualizarMedicionRequest): MedicionResponse {
        val medicion = medicionRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Medición no encontrada con ID: $id") }

        request.valorPpm?.let { medicion.valorPpm = it }
        request.fechaHoraMillis?.let { medicion.fechaHoraMillis = it }
        request.observacion?.let { medicion.observacion = it.trim() }
        request.registradoPor?.takeIf { it.isNotBlank() }?.let { medicion.registradoPor = it.trim() }

        return medicionRepository.save(medicion).toResponse()
    }

    fun eliminar(id: Long) {
        if (!medicionRepository.existsById(id)) {
            throw ResourceNotFoundException("Medición no encontrada con ID: $id")
        }
        medicionRepository.deleteById(id)
    }
}
