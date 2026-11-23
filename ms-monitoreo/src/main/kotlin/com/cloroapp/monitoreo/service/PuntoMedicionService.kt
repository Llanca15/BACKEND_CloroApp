package com.cloroapp.monitoreo.service

import com.cloroapp.monitoreo.dto.ActualizarPuntoRequest
import com.cloroapp.monitoreo.dto.CrearPuntoRequest
import com.cloroapp.monitoreo.dto.PuntoMedicionResponse
import com.cloroapp.monitoreo.dto.toResponse
import com.cloroapp.monitoreo.exception.DuplicateResourceException
import com.cloroapp.monitoreo.exception.ResourceNotFoundException
import com.cloroapp.monitoreo.model.PuntoMedicion
import com.cloroapp.monitoreo.repository.MedicionRepository
import com.cloroapp.monitoreo.repository.PuntoMedicionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PuntoMedicionService(
    private val puntoRepository: PuntoMedicionRepository,
    private val medicionRepository: MedicionRepository
) {

    @Transactional(readOnly = true)
    fun listar(granja: String?): List<PuntoMedicionResponse> {
        val puntos = if (granja.isNullOrBlank()) {
            puntoRepository.findAllByOrderByCodigoAsc()
        } else {
            puntoRepository.findByGranjaIgnoreCaseOrderByCodigoAsc(granja.trim())
        }
        return puntos.map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun obtenerPorCodigo(codigo: String): PuntoMedicionResponse {
        val punto = puntoRepository.findById(codigo.trim().uppercase())
            .orElseThrow { ResourceNotFoundException("Punto de medición no encontrado con código: $codigo") }
        return punto.toResponse()
    }

    fun crear(request: CrearPuntoRequest): PuntoMedicionResponse {
        val codigoNormalizado = request.codigo.trim().uppercase()
        if (puntoRepository.existsById(codigoNormalizado)) {
            throw DuplicateResourceException("Ya existe un punto de medición con el código: $codigoNormalizado")
        }

        val entidad = PuntoMedicion(
            codigo = codigoNormalizado,
            granja = request.granja.trim(),
            ubicacionGranja = request.ubicacionGranja.trim(),
            galpon = request.galpon.trim(),
            ubicacion = request.ubicacion.trim(),
            tipo = request.tipo.trim().uppercase()
        )

        return puntoRepository.save(entidad).toResponse()
    }

    fun actualizar(codigo: String, request: ActualizarPuntoRequest): PuntoMedicionResponse {
        val punto = puntoRepository.findById(codigo.trim().uppercase())
            .orElseThrow { ResourceNotFoundException("Punto de medición no encontrado con código: $codigo") }

        request.granja?.takeIf { it.isNotBlank() }?.let { punto.granja = it.trim() }
        request.ubicacionGranja?.let { punto.ubicacionGranja = it.trim() }
        request.galpon?.takeIf { it.isNotBlank() }?.let { punto.galpon = it.trim() }
        request.ubicacion?.takeIf { it.isNotBlank() }?.let { punto.ubicacion = it.trim() }
        request.tipo?.takeIf { it.isNotBlank() }?.let { punto.tipo = it.trim().uppercase() }

        return puntoRepository.save(punto).toResponse()
    }

    fun eliminar(codigo: String) {
        val codigoNormalizado = codigo.trim().uppercase()
        if (!puntoRepository.existsById(codigoNormalizado)) {
            throw ResourceNotFoundException("Punto de medición no encontrado con código: $codigo")
        }
        medicionRepository.deleteByCodigoPunto(codigoNormalizado)
        puntoRepository.deleteById(codigoNormalizado)
    }
}
