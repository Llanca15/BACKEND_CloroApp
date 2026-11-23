package com.cloroapp.monitoreo.repository

import com.cloroapp.monitoreo.model.Medicion
import org.springframework.data.jpa.repository.JpaRepository

interface MedicionRepository : JpaRepository<Medicion, Long> {
    fun findAllByOrderByFechaHoraMillisDescIdDesc(): List<Medicion>
    fun findByCodigoPuntoOrderByFechaHoraMillisDescIdDesc(codigoPunto: String): List<Medicion>
    fun findTopByCodigoPuntoOrderByFechaHoraMillisDescIdDesc(codigoPunto: String): Medicion?
    fun deleteByCodigoPunto(codigoPunto: String)
}
