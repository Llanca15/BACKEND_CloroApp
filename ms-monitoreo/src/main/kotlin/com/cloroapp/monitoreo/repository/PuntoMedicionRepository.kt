package com.cloroapp.monitoreo.repository

import com.cloroapp.monitoreo.model.PuntoMedicion
import org.springframework.data.jpa.repository.JpaRepository

interface PuntoMedicionRepository : JpaRepository<PuntoMedicion, String> {
    fun findAllByOrderByCodigoAsc(): List<PuntoMedicion>
    fun findByGranjaIgnoreCaseOrderByCodigoAsc(granja: String): List<PuntoMedicion>
}
